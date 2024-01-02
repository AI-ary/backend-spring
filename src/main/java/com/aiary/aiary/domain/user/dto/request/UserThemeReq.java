package com.aiary.aiary.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserThemeReq {

    @NotBlank(message = "사용자 테마는 필수 입니다.")
    private String theme;

}
