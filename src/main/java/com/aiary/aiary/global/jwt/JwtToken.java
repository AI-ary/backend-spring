package com.aiary.aiary.global.jwt;

import com.aiary.aiary.domain.user.entity.Theme;
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
    private Theme theme;
}
