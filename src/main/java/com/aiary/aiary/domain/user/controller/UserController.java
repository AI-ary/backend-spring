package com.aiary.aiary.domain.user.controller;

import com.aiary.aiary.domain.user.dto.request.UserJoinReq;
import com.aiary.aiary.domain.user.dto.request.UserLoginReq;
import com.aiary.aiary.domain.user.dto.request.UserTokenReq;
import com.aiary.aiary.domain.user.service.AuthService;
import com.aiary.aiary.domain.user.service.UserService;
import com.aiary.aiary.domain.user.validator.UserValidator;
import com.aiary.aiary.global.jwt.JwtToken;
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
    private final AuthService authService;
    private final UserValidator userValidator;


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> signup(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        userValidator.isDuplicatedUser(userJoinReq);
        userService.register(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }


//    @Operation(summary = "이메일 중복확인")
//    @GetMapping("/duplicated/{email}")
//    public ResponseEntity<ResultResponse> checkEmail(@PathVariable String email) {
//        boolean isDuplicated = userService.isDuplicatedEmail(email);
//
//        if (isDuplicated) {
//            return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_EMAIL_DUPLICATED, true));
//        }
//        return ResponseEntity.ok(ResultResponse.of(USER_EMAIL_NOT_DUPLICATED, false));
//    }


    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@RequestBody @Valid UserLoginReq userLoginReq) {
        JwtToken token = authService.login(userLoginReq);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGIN_SUCCESS, token));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@RequestBody @Valid UserTokenReq userTokenReq) {
        authService.logout(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGOUT_SUCCESS));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reissue(@RequestBody @Valid UserTokenReq userTokenReq) {
        JwtToken newToken = authService.reissue(userTokenReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REISSUE_SUCCESS, newToken));
    }
}
