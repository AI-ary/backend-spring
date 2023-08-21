package com.aiary.aiary.domain.user.controller;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserLoginReq;
import com.aiary.aiary.domain.user.dto.request.UserTokenReq;
import com.aiary.aiary.domain.user.exception.UserDuplicatedException;
import com.aiary.aiary.domain.user.service.LoginService;
import com.aiary.aiary.domain.user.service.UserService;
import com.aiary.aiary.global.jwt.JwtToken;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.aiary.aiary.global.result.ResultCode.*;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final LoginService loginService;


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        if (userService.isDuplicatedEmail(userJoinReq.getEmail())) {
            throw new UserDuplicatedException();
        }
        userService.register(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }

    @Operation(summary = "이메일 중복확인")
    @GetMapping("/duplicated/{email}")
    public ResponseEntity<ResultResponse> checkEmail(@PathVariable String email) {
        boolean isDuplicated = userService.isDuplicatedEmail(email);

        if (isDuplicated) {
            return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_EMAIL_DUPLICATED, true));
        }
        return ResponseEntity.ok(ResultResponse.of(USER_EMAIL_NOT_DUPLICATED, false));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody UserLoginReq userLoginReq) {
        JwtToken token = loginService.login(userLoginReq);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS, token));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@RequestBody UserTokenReq userTokenReq) {
        loginService.logout(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGOUT_SUCCESS));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reissue(@RequestBody UserTokenReq userTokenReq) {
        JwtToken newToken = loginService.reissue(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REISSUE_SUCCESS, newToken));
    }
}
