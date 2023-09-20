package com.aiary.aiary.support.fixture;

import com.aiary.aiary.domain.user.entity.Role;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;

public class UserFixture {

    public static final User TEST_USER = User.builder()
            .email("test@gmail.com")
            .password("Test012@")
            .nickname("테스트유저")
            .role(Role.USER)
            .build();

    public static final UserDetail TEST_USERDETAIL = UserDetail.builder()
            .user(TEST_USER)
            .build();


}
