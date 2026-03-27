package com.project.instagramclone.model.service;

import com.project.instagramclone.common.CookieUtil;
import com.project.instagramclone.common.JwtUtil;
import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.mapper.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 카카오 로그인 전체 비즈니스 로직 담당
 *
 * [컨트롤러가 할 일]  요청 받기 / 리다이렉트
 * [서비스가 할 일]    카카오 URL 생성 / 토큰 발급 / 유저 정보 조회 / 회원가입 / JWT 발급 / 쿠키 저장
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    private final RestTemplate restTemplate = new RestTemplate();

    // ─────────────────────────────────────────────
    // 1. 카카오 로그인 URL 생성
    // ─────────────────────────────────────────────
    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
    }

    // ─────────────────────────────────────────────
    // 2. 카카오 로그인 메인 흐름 (컨트롤러에서 호출)
    //    인가코드 → 액세스토큰 → 유저정보 → 회원가입 or 스킵 → JWT → 쿠키
    // ─────────────────────────────────────────────
    public void kakaoLogin(String code, HttpServletResponse response) {
        // 2-1. 인가코드 → 카카오 액세스토큰
        String kakaoAccessToken = getAccessToken(code);

        // 2-2. 카카오 액세스토큰 → 이메일, 닉네임
        Map<String, String> userInfo = getUserInfo(kakaoAccessToken);
        String email = userInfo.get("email");
        String name  = userInfo.get("name");

        if (email == null) {
            throw new RuntimeException("카카오 계정에 이메일 정보가 없습니다.");
        }

        // 2-3. DB에 없으면 자동 회원가입
        boolean 이미존재 = userMapper.이메일중복체크(email) > 0;
        if (!이미존재) {
            User newUser = new User();
            newUser.setName(name != null ? name : "카카오유저");
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode("KAKAO_OAUTH_" + email));
            userMapper.회원가입(newUser);
            log.info("카카오 신규 회원가입 완료: {}", email);
        }

        // 2-4. JWT 발급 후 쿠키 저장
        String accessToken  = jwtUtil.createAccessToken(email);
        String refreshToken = jwtUtil.createRefreshToken(email);

        cookieUtil.add(response, "access_token",  accessToken,  60 * 30);           // 30분
        cookieUtil.add(response, "refresh_token", refreshToken, 60 * 60 * 24 * 7);  // 7일

        log.info("카카오 로그인 성공: {}", email);
    }

    // ─────────────────────────────────────────────
    // 3. 인가코드 → 카카오 액세스토큰 발급
    // ─────────────────────────────────────────────
    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type",   "authorization_code");
        params.add("client_id",    clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code",         code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("카카오 토큰 발급 실패");
        }

        return (String) response.getBody().get("access_token");
    }

    // ─────────────────────────────────────────────
    // 4. 카카오 액세스토큰 → 사용자 이메일/닉네임 조회
    // ─────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private Map<String, String> getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoUrl, HttpMethod.GET, request, Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("카카오 사용자 정보 조회 실패");
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");

        Map<String, String> result = new HashMap<>();
        if (kakaoAccount != null) {
            result.put("email", (String) kakaoAccount.get("email"));
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null) {
                result.put("name", (String) profile.get("nickname"));
            }
        }

        return result;
    }
}