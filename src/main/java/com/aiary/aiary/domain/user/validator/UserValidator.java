package com.aiary.aiary.domain.user.validator;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserLoginReq;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.exception.InValidPasswordException;
import com.aiary.aiary.domain.user.exception.UserDuplicatedException;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void isDuplicatedEmail (UserJoinReq request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserDuplicatedException();
        }
    }

    public User loginUser (UserLoginReq request) {
        // 로그인 시  일치하면 유저 정보 가져오기
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InValidPasswordException();
        }
        return user;
    }
}
