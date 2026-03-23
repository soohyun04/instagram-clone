package com.project.instagramclone.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component //service controller 처럼 이름을 규정지어 만든 것이 아니라 개발자가 필요로 해서 만든 자바 클래스 파일
public class CookieUtil {
    public void add(HttpServletResponse res, String name, String value, int maxAgeSeconds){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        res.addCookie(cookie);
    }

    public void delete(HttpServletResponse res, String name){
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }

    public String get(HttpServletResponse req, String name){
        Cookie[] cookies = req.getCookies();

        if (cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
