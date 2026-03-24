package com.project.instagramclone.common;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 회사마다 필수로 존재하는 환경설정
 * yaml 보다 세부적인 설정이 필요하여, @Configration 어노테이션 붙여서 사용
 * 우리 회사는 보안해야할게 없다.. 오로지 회사 소개만 있다 하면 SecurityConfig 가 필요할 일은 없다.
 */

@Configuration
@EnableWebSecurity // 기본적으로 스프링부트에서 제공하는 환경설정 대신에 개발자가 만들어놓은 보안 환경설정 활성화!
// 스프링부터에서 만든 보안 환경설정을 사용하겠다. 하면 @EnableWebSecurity 주석 제거한다.
@RequiredArgsConstructor
public class SecurityConfig {
    //   private final  JwtFilter 필터 적용!
    // 기존 스프링시큐리티가 만든 암호화 방식에 몇가지만 우리회사가 만든 방식으로 보안 설정
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain 보안설정(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 처음 켰을 때 우리가 만들지 않은 로그인 창 안뜸
                // JWT 방식으로 로그인관리 진행하겠다.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 어디어디 롤에 따른 접근권한 세팅
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/static/**").permitAll()
                        .anyRequest().permitAll())
                // .requestMatchers().permitAll()
                // .auth.anyRequest().permitAll()
                // .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
