package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.dto.mapper.UserMapper;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void register(UserJoinReq userJoinReq) {
        User user = userMapper.toEntity(userJoinReq);
        user.setEncryptedPassword(passwordEncoder);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
