package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserThemeReq;
import com.aiary.aiary.domain.user.dto.response.UserProfileRes;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.mapper.UserMapper;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public boolean isDuplicatedEmail(String email) {
        return userRepository.existsByEmail(email);
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

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
