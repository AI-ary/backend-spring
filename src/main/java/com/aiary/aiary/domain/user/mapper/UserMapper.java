package com.aiary.aiary.domain.user.mapper;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.entity.Role;
import com.aiary.aiary.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserJoinReq userJoinReq) {
        return User.builder()
                .email(userJoinReq.getEmail())
                .password(userJoinReq.getPassword())
                .nickname(userJoinReq.getNickname())
                .role(Role.USER)
                .build();
    }
}
