package com.project.instagramclone.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyValue;
    @Value("${jwt.access-expiry}")
    private long accessTokenExpiry;
    @Value("${jwt.refresh-expiry}")
    private long refreshTokenExpiry;
    // JWT 를 서명하고 검증할 때 사용하는 암호화 키 객체
    // application.yaml 에서 비밀문자열
    // jwt:
    //  config:
    //    secret: springboard-jwt-secret-key-2026-change-this-please!!!
    // 을 바로 직접적인 문자열로 사용하는 것이아니라 SecretKey 타입으로 변환해야 JJWT 라이브러리 사용 가능
    // yaml 값이 노출될 수 있다.
    // spring 자체에서 한 번 더 암호화 처리
    // 개발자가 yaml 파일을 깃허브나 올라가면 안되는 공개된 공간에 올릴 경우를 대비해서 변환

    // 에러 발생!!!!
    // private byte[] a = secretKeyValue.getBytes();
    // private SecretKey key = Keys.hmacShaKeyFor(a);

    private SecretKey key;

    // js async await 비슷한 효과를 가진 어노테이션
    @PostConstruct
    public void init() {
        // this.key =  private SecretKey key;
        //          secretKeyValue 를 yaml 에서 가져온다음 데이터 변환을 하고 SecretKey key 넣는다는 것이구나.
        // 변환한 데이터를 this 이곳에 있는 .key 키라는 변수이름에 데이터를 보관하겠다.
        this.key = Keys.hmacShaKeyFor(secretKeyValue.getBytes());
    }

    public String createAccessToken(String email) {
        return buildToken(email, accessTokenExpiry);
    }

    public String createRefreshToken(String email) {
        return buildToken(email, refreshTokenExpiry);
    }

    // 만약에 메서드 내부 () 내부에 어떤 자료형을 넣어야하는지 모르겠다면
    // ctrl + 마우스로 .기능이름() 을 클릭하면 해당 기능이 기술되어 있는 페이지로 이동할 것이고,
    // .기능이름() 소괄호 내부에 작성된 자료형을 확인 후 맞추어 작성하면 된다.
    public String buildToken(String email, long expiryMs) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiryMs))
                .signWith(key)
                .compact()
                ;
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Token validation failed : {}", e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}