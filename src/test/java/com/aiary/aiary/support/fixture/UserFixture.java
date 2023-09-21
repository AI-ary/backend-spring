package com.aiary.aiary.support.fixture;

import com.aiary.aiary.domain.user.entity.Role;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;

public class UserFixture {

    public static final User DIARY_CREATE_USER = User.builder()
            .email("Create@gmail.com")
            .password("Test012@")
            .nickname("테스트유저")
            .role(Role.USER)
            .build();

    public static final UserDetail DIARY_CREATE_USERDETAIL = UserDetail.builder()
            .user(DIARY_CREATE_USER)
            .build();

    public static final User DIARY_DELETE_USER = User.builder()
            .email("Delete@gmail.com")
            .password("Test012@")
            .nickname("테스트유저")
            .role(Role.USER)
            .build();

    public static final UserDetail DIAEY_DELETE_USERDETAIL = UserDetail.builder()
            .user(DIARY_DELETE_USER)
            .build();

    public static final User DIARY_FIND_MONTH_USER = User.builder()
            .email("Find@gmail.com")
            .password("Test012@")
            .nickname("테스트유저")
            .role(Role.USER)
            .build();

    public static final UserDetail DIARY_FIND_MONTH_USERDETAIL = UserDetail.builder()
            .user(DIARY_FIND_MONTH_USER)
            .build();
}
