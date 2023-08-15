package com.aiary.aiary.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenReq {
    @NotBlank(message = "accessToken 을 입력해주세요.")
    private String accessToken;

    @NotBlank(message = "refreshToken 을 입력해주세요.")
    private String refreshToken;
}
