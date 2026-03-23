package com.project.instagramclone.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKetValue;
    @Value("${jwt.access-expiry}")
    private String accessTokenExpiry;
    @Value("${jwt.refresh-expiry}")
    private String refreshTokenExpiry;


    private SecretKey secretKey;

    public void init(){};

    public String createAccessToken(String email){return "";};
    public String createRefreshToken(String email){return "";};
    public String buildToken(String email, long expiryMs){return "";};
    public String getEmail(String token){return "";};
    public boolean isValidToken(String token){return true;};
}
