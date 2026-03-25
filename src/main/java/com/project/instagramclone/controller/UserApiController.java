package com.project.instagramclone.controller;

import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private  final UserService userService;

    @PostMapping("/api/send-code")
    public ResponseEntity<?> 인증번호발송(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        userService.인증번호발송(email);
        return ResponseEntity.ok(Map.of("message", "인증번호가 발송되었습니다."));
    }

    @PostMapping("/api/verify-code")
    public ResponseEntity<?> 인증번호확인(@RequestBody Map<String, String> body) {
        boolean 성공 = userService.인증번호검증(           // TODO B-1: 서비스 메서드명
                body.get("email"),                      // TODO B-2: 이메일 key
                body.get("code")                       // TODO B-3: 코드 key
        );
        if (!성공) {                               // TODO B-4: 실패 조건
            return ResponseEntity.badRequest()           // TODO B-5: 실패 상태 메서드
                    .body(Map.of("message", "인증번호가 올바르지 않습니다."));
        }
        return ResponseEntity.ok(Map.of("message", "인증 성공"));  // TODO B-6: key
    }

    // TODO C : 회원가입 API
    @PostMapping ("/api/register")                                    // TODO C-1: 어노테이션, 경로
    public ResponseEntity<?> 회원가입(@RequestBody User user) { // TODO C-2: 어노테이션, 자료형
        boolean 성공 = userService.회원가입(user);         // TODO C-3: 서비스 메서드, 인자
        if (!성공) {                                       // TODO C-4: 실패 조건
            return ResponseEntity.badRequest()                   // TODO C-5: 실패 상태 메서드
                    .body(Map.of("message", "이미 사용중인 이메일입니다."));
        }
        return ResponseEntity.ok(Map.of("message", "회원가입 완료")); // TODO C-6: 성공 메서드
    }
}