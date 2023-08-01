package com.aiary.aiary.domain.user.controller;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserLoginReq;
import com.aiary.aiary.domain.user.exception.UserDuplicatedException;
import com.aiary.aiary.domain.user.mapper.UserMapper;
import com.aiary.aiary.domain.user.service.LoginService;
import com.aiary.aiary.domain.user.service.UserService;
import com.aiary.aiary.global.jwt.JwtToken;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aiary.aiary.global.result.ResultCode.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;


    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        if (userService.isDuplicatedEmail(userJoinReq.getEmail())) {
            throw new UserDuplicatedException();
        }
        userService.register(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }

    @GetMapping("/duplicated/{email}")
    public ResponseEntity<ResultResponse> isDuplicatedEmail(@PathVariable String email) {
        boolean isDuplicated = userService.isDuplicatedEmail(email);

        if (isDuplicated) {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_EMAIL_DUPLICATED, true));
        }
        return ResponseEntity.ok(ResultResponse.of(USER_EMAIL_NOT_DUPLICATED, false));
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody UserLoginReq userLoginReq) {
        JwtToken token = loginService.login(userLoginReq.getEmail(), userLoginReq.getPassword());
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS, token));
    }
}
