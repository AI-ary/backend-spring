package com.aiary.aiary.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenReq {
    @NotBlank(message = "accessToken 을 입력해주세요.")
    private String accessToken;

    @NotBlank(message = "refreshToken 을 입력해주세요.")
    private String refreshToken;
}
