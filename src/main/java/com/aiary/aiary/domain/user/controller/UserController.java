package com.aiary.aiary.domain.user.controller;

import com.aiary.aiary.domain.user.dto.request.*;
import com.aiary.aiary.domain.user.dto.response.UserProfileRes;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.service.AuthService;
import com.aiary.aiary.domain.user.service.UserService;
import com.aiary.aiary.domain.user.validator.UserValidator;
import com.aiary.aiary.global.jwt.JwtToken;
import com.aiary.aiary.global.result.ResultResponse;
import com.aiary.aiary.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;

import static com.aiary.aiary.global.result.ResultCode.*;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final UserValidator userValidator;
    private final S3Service s3Service;


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> signup(
            @RequestBody @Valid UserJoinReq userJoinReq) {
        userValidator.isDuplicatedUser(userJoinReq);
        userService.register(userJoinReq);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS));
    }

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

    @Operation(summary = "테마 변경")
    @PostMapping("/theme")
    public ResponseEntity<ResultResponse> updateTheme(@AuthenticationPrincipal UserDetail user,
                                                      @RequestBody @Valid UserThemeReq userThemeReq){
        userService.updateTheme(user.getUser(), userThemeReq);
        return ResponseEntity.ok(ResultResponse.of(USER_UPDATE_THEME_SUCCESS));
    }

    @Operation(summary = "프로필 변경")
    @PostMapping("/profile")
    public ResponseEntity<ResultResponse> updateProfile(@AuthenticationPrincipal UserDetail user,
                                                        @RequestPart("file") MultipartFile multipartFile) throws IOException {
        if (user.getUser().getProfileImage() != null) { // 기존 이미지가 있었다면 S3 이미지 삭제
            s3Service.deleteFile(user.getUser().getProfileImage());
        }
        String drawingUrl = s3Service.saveFile(multipartFile, "profile", user.getUsername());
        userService.updateProfileImage(user.getUser(), drawingUrl);
        return ResponseEntity.ok(ResultResponse.of(USER_UPDATE_PROFILE_IMAGE_SUCCESS));
    }

    @Operation(summary = "사용자 프로필 조회")
    @GetMapping("/profile")
    public ResponseEntity<ResultResponse> findUserProfile(@AuthenticationPrincipal UserDetail user){
        UserProfileRes userProfile = userService.findUserProfile(user.getUser());
        return ResponseEntity.ok(ResultResponse.of(USER_PROFILE_SUCCESS, userProfile));
    }
}
