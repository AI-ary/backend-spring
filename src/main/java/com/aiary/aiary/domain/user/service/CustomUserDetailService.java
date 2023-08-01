package com.aiary.aiary.domain.user.service;

import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User principal = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetail(principal);
    }
}
