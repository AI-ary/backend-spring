package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.dto.request.UserTokenReq;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.*;
import com.aiary.aiary.domain.user.repository.UserRepository;
import com.aiary.aiary.global.jwt.JwtToken;
import com.aiary.aiary.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;

    public JwtToken login(String email, String password) {
        // 로그인 시  일치하면 유저 정보 가져오기
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new InValidPasswordException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwt = jwtTokenProvider.generateToken(authentication);

        //redis 에 email 을 key 로 저장, refresh 토큰을 value 값으로 저장
        redisTemplate.opsForValue().set("RT:" + user.getEmail(), jwt.getRefreshToken(), jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return jwt;
    }

    public void logout(UserTokenReq userTokenReq) {
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!jwtTokenProvider.validateToken(userTokenReq.getAccessToken())){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access 토큰에서 User 정보를 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(userTokenReq.getAccessToken());

        // redis 에서 해당 User username 으로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null){
            // Refresh 토큰 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList 에 저장
        Long expiration = jwtTokenProvider.getExpiration(userTokenReq.getAccessToken());
        redisTemplate.opsForValue().set(userTokenReq.getAccessToken(),"logout", expiration, TimeUnit.MILLISECONDS);
    }

    public JwtToken reissue(UserTokenReq userTokenReq) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(userTokenReq.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh Token 정보가 유효하지 않습니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(userTokenReq.getAccessToken());

        // redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            throw new InValidAccessException();
        }
        if(!refreshToken.equals(userTokenReq.getRefreshToken())) {
            throw new UnAuthorizedAccessException();
        }

        // 새로운 토큰 생성
        JwtToken newJwt = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), newJwt.getRefreshToken(), newJwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return newJwt;
    }
}
