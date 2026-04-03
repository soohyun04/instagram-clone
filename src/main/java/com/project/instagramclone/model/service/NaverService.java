package com.project.instagramclone.model.service;

import com.project.instagramclone.common.CookieUtil;
import com.project.instagramclone.common.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverService {

    // TODO 1 : 네이버 개발자 센터에서 발급받은 client-id yaml 에서 가져오기
    @Value("${naver.client-id}")
    private String clientId;

    // TODO 2 : 네이버 개발자 센터에서 발급받은 client-secret yaml 에서 가져오기
    @Value("${naver.client-secret}")
    private String clientSecret;

    // TODO 3 : 네이버 개발자 센터에서 등록한 redirect-uri yaml 에서 가져오기
    @Value("${naver.redirect-uri}")
    private String redirectUri;

    // TODO 4 : 필요한 서비스 / 유틸 주입 (카카오 참고)
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    // ────────────────────────────────────────
    // 1. 네이버 로그인 URL 생성
    // ────────────────────────────────────────
    public String 네이버로그인주소() {
        // TODO 5 : 네이버 OAuth 로그인 URL 생성 후 반환
        // https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=...&redirect_uri=...&state=...
        return "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + clientId + "&redirect_uri="+ redirectUri + "&response_type=code";
    }

    // ────────────────────────────────────────
    // 2. 네이버 로그인 메인 흐름
    //    인가코드 -> 엑세스토큰 -> 유저정보 -> DB확인 -> 기존회원이면 JWT발급 / 신규회원이면 회원가입 페이지로 이동
    // ────────────────────────────────────────
    public void 네이버로그인(String 인가코드, String state, HttpServletResponse response) throws IOException {
        // TODO 6 : 인가코드로 네이버 엑세스토큰 발급
        String 네이버토큰 = 엑세스토큰발급(인가코드, state);

        // TODO 7 : 엑세스토큰으로 유저정보 조회 (이메일, 닉네임)
        Map<String, String> 유저정보 = 유저정보조회(네이버토큰);
        String 이메일 = 유저정보.get("email");
        String 닉네임 = 유저정보.get("nickname");

        // TODO 8 : 이메일이 null 이면 예외 발생
        if(이메일 == null) throw new RuntimeException("네이버 계정 이메일 정보가 없습니다.");

        // TODO 9 : DB에 이미 가입된 유저인지 이메일 중복체크
        boolean 기존회원유무 = userService.이메일중복체크기능(이메일);

        // TODO 10 : 기존회원이면 JWT 발급 후 쿠키 저장 -> 메인페이지로 이동
        if(기존회원유무){
            JWT발급후쿠키저장(이메일, response);
            response.sendRedirect("/");
        }else{
            String 이동주소 = "/user/naver-register?email="+이메일+"&name="+(닉네임 != null ? 닉네임 : "");
            response.sendRedirect(이동주소);
            log.info("카카오 신규회원 -> 회원가입 페이지로 이동 : {}", 이메일);
        }

        // TODO 11 : 신규회원이면 네이버 회원가입 페이지로 리다이렉트
        // /user/naver-register.jsp?email=...&name=...
    }

    // ────────────────────────────────────────
    // 3. JWT 발급 후 쿠키 저장
    // ────────────────────────────────────────
    public void JWT발급후쿠키저장(String 이메일, HttpServletResponse response) {
        // TODO 12 : 이메일로 access_token 생성
        String 엑세스토큰 = jwtUtil.createAccessToken(이메일);

        // TODO 13 : 이메일로 refresh_token 생성
        String 리프레시토큰 = jwtUtil.createRefreshToken(이메일);

        // TODO 14 : access_token 쿠키에 저장 (만료 30분)
        cookieUtil.add(response, "access_token", 엑세스토큰, 60 * 30);

        // TODO 15 : refresh_token 쿠키에 저장 (만료 7일)
        cookieUtil.add(response, "refresh_token", 리프레시토큰, 60 * 60 * 24 * 7);
    }

    // ────────────────────────────────────────
    // 4. 인가코드 -> 네이버 엑세스토큰 발급
    // ────────────────────────────────────────
    private String 엑세스토큰발급(String 인가코드, String state) {
        // TODO 16 : 요청 헤더 설정 (Content-Type: application/x-www-form-urlencoded)
        HttpHeaders 헤더 = new HttpHeaders();
        헤더.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // TODO 17 : 요청 파라미터 설정
        // grant_type, client_id, client_secret, redirect_uri, code, state
        MultiValueMap<String, String> 파라미터 = new LinkedMultiValueMap<>();
        파라미터.add("grant_type", "authorization_code");
        파라미터.add("client_id", clientId);
        파라미터.add("client_secret", clientSecret);
        파라미터.add("redirect_uri", redirectUri);
        파라미터.add("code", 인가코드);
        파라미터.add("state", state);


        // TODO 18 : https://nid.naver.com/oauth2.0/token 으로 POST 요청
        HttpEntity<MultiValueMap<String, String>> 요청 = new HttpEntity<>(파라미터, 헤더);
        ResponseEntity<Map> 응답 = restTemplate.postForEntity(
                "https://nid.naver.com/oauth2.0/token", 요청, Map.class
        );

        // TODO 19 : 응답에서 access_token 꺼내서 반환
        if (응답.getStatusCode() != HttpStatus.OK || 응답.getBody() == null) {
            throw new RuntimeException("네이버 토큰 발급 실패");
        }
        return (String) 응답.getBody().get("access_token");
    }

    // ────────────────────────────────────────
    // 5. 네이버 엑세스토큰 -> 이메일 / 닉네임 조회
    // ────────────────────────────────────────
    private Map<String, String> 유저정보조회(String 네이버토큰) {
        // TODO 20 : 요청 헤더에 Bearer 토큰 설정
        HttpHeaders 헤더 = new HttpHeaders();
        헤더.setBearerAuth(네이버토큰);
        //헤더.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // TODO 21 : https://openapi.naver.com/v1/nid/me 로 GET 요청
        HttpEntity<Void> 요청 = new HttpEntity<>(헤더);
        ResponseEntity<Map> 응답 = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                요청,
                Map.class
        );

        if (응답.getStatusCode() != HttpStatus.OK || 응답.getBody() == null) {
            // 잘못된 팔찌여서 팔찌 내에 존재하는 유저정보를 확인할 수 없습니다.
            throw new RuntimeException("네이버 사용자 정보 조회 실패로 인하여 유저 정보를 세부적으로 확인할 수 없습니다.");
        }

        // TODO 22 : 응답에서 이메일, 닉네임 꺼내서 Map 으로 반환
        // 네이버는 응답구조가 카카오와 다름
        // { "response": { "email": "...", "nickname": "..." } }
        Map<String, Object> 네이버계정 = (Map<String, Object>) 응답.getBody().get("response");

        Map<String, String> 결과 = new HashMap<>();
        if (네이버계정 != null) {
            결과.put("email", (String) 네이버계정.get("email"));
            결과.put("name", (String) 네이버계정.get("nickname"));
        }
        return 결과;
    }
}