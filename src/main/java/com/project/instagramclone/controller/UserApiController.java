package com.project.instagramclone.controller;
/**
 * todoList 만들기 -> 개별적으로 진행하며, 어느정도 타이핑을 원활하게 칠 수 있는가?
 */

import com.project.instagramclone.common.CookieUtil;
import com.project.instagramclone.common.JwtUtil;
import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @PostMapping("/api/send-code")
    public ResponseEntity<?> 인증번호발송(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        userService.인증번호발송(email);
        return ResponseEntity.ok(Map.of("message", "인증번호가 발송되었습니다."));
    }

    @PostMapping("/api/verify-code")
    public ResponseEntity<?> 인증번호확인(@RequestBody Map<String, String> body) {
        boolean 성공 = userService.인증번호검증(
                body.get("email"),
                body.get("code")
        );
        if (!성공) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "인증번호가 올바르지 않습니다."));
        }
        return ResponseEntity.ok(Map.of("message", "인증 성공"));
    }

    @PostMapping("/api/register")                                    // TODO C-1: 어노테이션, 경로
    public ResponseEntity<?> 회원가입(@RequestBody User user) { // TODO C-2: 어노테이션, 자료형
        boolean 성공 = userService.회원가입(user);         // TODO C-3: 서비스 메서드, 인자
        if (!성공) {                                       // TODO C-4: 실패 조건
            return ResponseEntity.badRequest()                   // TODO C-5: 실패 상태 메서드
                    .body(Map.of("message", "이미 사용중인 이메일입니다."));
        }
        return ResponseEntity.ok(Map.of("message", "회원가입 완료")); // TODO C-6: 성공 메서드
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> 로그인(@RequestBody Map<String, String> body,
                                 HttpServletResponse response) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userService.로그인(email, password);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "이메일 또는 비밀번호가 올바르지 않습니다."));
        }

        String accessToken = jwtUtil.createAccessToken(user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

        cookieUtil.add(response, "access_token", accessToken, 60 * 60);
        cookieUtil.add(response, "refresh_token", refreshToken, 60 * 60 * 24 * 7);
        return ResponseEntity.ok(Map.of("message", "로그인 성공"));
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> 모든회원조회() {
        List<User> user = userService.모든회원조회();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/users/search")
    public ResponseEntity<?> 유저명검색(@RequestParam String keyword) {
        List<User> 결과 = userService.유저명검색(keyword);
        return ResponseEntity.ok(결과);
    }
}