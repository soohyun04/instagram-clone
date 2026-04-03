package com.project.instagramclone.controller;


import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.service.NaverService;
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
public class NaverApiController {
    private final NaverService naverService;
    private final UserService userService;

    @GetMapping("/api/naver/login")
    public void 네이버로그인(HttpServletResponse response) throws IOException{
        String 주소 = naverService.네이버로그인주소();
        response.sendRedirect(주소);
    }

    @GetMapping("/api/naver/callback")
    public void 네이버콜백(@RequestParam String code, @RequestParam(required = false)String state, HttpServletResponse response) throws IOException {
        try {
            naverService.네이버로그인(code, state, response);
            //response.sendRedirect("/");
        } catch (IOException e) {
            log.error("네이버 로그인 실패 : {}", e.getMessage());
            response.sendRedirect("/login?error=naver_fail");
        }
    }

    @PostMapping("/api/naver/register")
    public ResponseEntity<?> 네이버회원가입(@RequestBody User 유저,
                                     HttpServletResponse response){
        userService.네이버회원가입(유저);
        naverService.JWT발급후쿠키저장(유저.getEmail(), response);
        return ResponseEntity.ok(Map.of("message", "네이버 회원가입 완료"));
    }

}
