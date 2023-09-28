package com.aiary.aiary.support.utils;

import com.aiary.aiary.domain.user.entity.Role;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String userEmail = annotation.userUuid();
        String role = annotation.role();

        User user = User.builder()
                .email(userEmail)
                .nickname("테스트 유저")
                .password("Test012@")
                .role(Role.valueOf(role))
                .build();
        UserDetail userDetail = UserDetail.builder().user(user).build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetail, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
