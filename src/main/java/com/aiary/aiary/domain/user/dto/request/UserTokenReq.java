package com.aiary.aiary.domain.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserTokenReq {
    @NotBlank(message = "accessToken 을 입력해주세요.")
    private String accessToken;

    @NotBlank(message = "refreshToken 을 입력해주세요.")
    private String refreshToken;
}
