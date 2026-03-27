package com.project.instagramclone.controller;


import com.project.instagramclone.model.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoApiController {

    private final KakaoService kakaoService;

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
            response.sendRedirect("/user/login?error=kakao_fail");
        }
    }
}