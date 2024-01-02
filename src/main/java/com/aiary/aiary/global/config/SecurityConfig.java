package com.aiary.aiary.global.config;

import com.aiary.aiary.global.jwt.JwtAuthenticationFilter;
import com.aiary.aiary.global.jwt.JwtTokenProvider;
import com.aiary.aiary.global.jwt.exception.JwtAccessDeniedHandler;
import com.aiary.aiary.global.jwt.exception.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 403 (권한이 없는 사용자)
            .accessDeniedHandler(jwtAccessDeniedHandler)            // 401 (인증되지 않은 사용자)
            .and()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/users/join", "/users/login", "/users/reissue")
            .permitAll() // 회원가입, 로그인, 토큰 재발급 API는 인증 없이 허용
            .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**")
            .permitAll() // swagger 인증 없이 허용
            .antMatchers("/actuator/**").permitAll()  // Spring Actuator 인증 없이 허용
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
