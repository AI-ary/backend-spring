package com.aiary.aiary.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserThemeReq {

    @NotBlank(message = "사용자 테마는 필수 입니다.")
    private String theme;

}
