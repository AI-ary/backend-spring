package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserThemeReq;
import com.aiary.aiary.domain.user.dto.response.UserProfileRes;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.dto.mapper.UserMapper;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public boolean isDuplicatedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isDuplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void register(UserJoinReq userJoinReq) {
        User user = userMapper.toEntity(userJoinReq);
        user.setEncryptedPassword(passwordEncoder);
        userRepository.save(user);
    }

    public void updateTheme(User user, UserThemeReq userThemeReq){
        user.updateTheme(userThemeReq.getTheme());
        userRepository.save(user);
    }

    public UserProfileRes findUserProfile(User user) {
        return userMapper.toUserProfile(user);
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
