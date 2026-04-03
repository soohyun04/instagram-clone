package com.project.instagramclone.controller;


import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.service.KakaoService;
import com.project.instagramclone.model.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoApiController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/api/kakao/login")
    public void 카카오로그인(HttpServletResponse response) throws IOException {
        String 주소 = kakaoService.카카오로그인주소();
        response.sendRedirect(주소);
    }

    @GetMapping("/api/kakao/callback")
    public void 카카오콜백(@RequestParam String code, HttpServletResponse response) throws IOException {
        try {
            kakaoService.카카오로그인(code, response);
            response.sendRedirect("/");
        } catch (IOException e) {
            log.error("카카오 로그인 실패 : {}", e.getMessage());
            response.sendRedirect("/login?error=kakao_fail");
        }
    }

    /*********** 추가해야 한다. 카카오톡 신규 회원 가입 처리 kaka0-register.jsp 에서 가입하기 버튼 클릭시 호출 *************/
    // 카카오 신규 회원가입 처리
    // kakao-register.jsp 에서 가입하기 버튼 클릭 시 호출
    @PostMapping("/api/kakao/register")
    public ResponseEntity<?> 카카오회원가입(@RequestBody User 유저,
                                     HttpServletResponse response) {
        // TODO 1 : 카카오 유저는 비밀번호 없이 회원가입 처리
        userService.카카오회원가입(유저);
        // TODO 2 : 회원가입 완료 후 바로 JWT 발급 및 쿠키 저장
        kakaoService.JWT발급후쿠키저장(유저.getEmail(), response);
        // TODO 3 : 200 OK 응답 반환
        return ResponseEntity.ok(Map.of("message", "카카오 회원가입 완료"));
    }
}