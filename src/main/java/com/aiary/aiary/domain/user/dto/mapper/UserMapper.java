package com.aiary.aiary.domain.user.dto.mapper;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.response.UserProfileRes;
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

    public UserProfileRes toUserProfile(User user){
        return UserProfileRes.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .theme(user.getTheme())
                .build();
    }
}
