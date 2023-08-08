package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.InValidPasswordException;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
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

import javax.transaction.Transactional;

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

        //redis에 username을 key로 저장
        redisTemplate.opsForValue().set("ID: " + user.getEmail(), jwt.getAccessToken(), jwtTokenProvider.getExpiration(jwt.getAccessToken()));

        return jwt;
    }

    public void logout(JwtToken jwtToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken.getAccessToken());
        if (redisTemplate.opsForValue().get("ID: " + authentication.getName()) != null) {
            redisTemplate.delete("ID: " + authentication.getName()); //Token 삭제
        }
    }
}
