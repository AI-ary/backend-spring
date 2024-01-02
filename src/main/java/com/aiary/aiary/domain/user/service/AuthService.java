package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.dto.request.UserLoginReq;
import com.aiary.aiary.domain.user.dto.request.UserTokenReq;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.UnAuthorizedAccessException;
import com.aiary.aiary.domain.user.validator.UserValidator;
import com.aiary.aiary.global.jwt.JwtToken;
import com.aiary.aiary.global.jwt.JwtTokenProvider;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserValidator userValidator;

    public JwtToken login(UserLoginReq userLoginReq) {
        User user = userValidator.loginUser(userLoginReq);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userLoginReq.getEmail(), userLoginReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
        JwtToken jwt = jwtTokenProvider.generateToken(authentication);

        //redis 에 email 을 key 로 저장, refresh 토큰을 value 값으로 저장
        redisTemplate.opsForValue().set("RT:" + user.getEmail(), jwt.getRefreshToken(),
            jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return jwt;
    }

    public void logout(UserTokenReq userTokenReq) {
        // Access 토큰에서 User 정보를 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(
            userTokenReq.getAccessToken());

        // redis 에서 해당 User username 으로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh 토큰 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList 에 저장
        Long expiration = jwtTokenProvider.getExpiration(userTokenReq.getAccessToken());
        redisTemplate.opsForValue()
            .set(userTokenReq.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public JwtToken reissue(UserTokenReq userTokenReq) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(userTokenReq.getRefreshToken())) {
            throw new UnAuthorizedAccessException();
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(
            userTokenReq.getAccessToken());

        // 새로운 토큰 생성
        JwtToken newJwt = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
            .set("RT:" + authentication.getName(), newJwt.getRefreshToken(),
                newJwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return newJwt;
    }
}
