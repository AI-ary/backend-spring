package com.aiary.aiary.global.jwt;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long refreshTokenExpirationTime;
}
