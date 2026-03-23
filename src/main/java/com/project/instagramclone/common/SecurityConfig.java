package com.project.instagramclone.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * 회사마다 필수로 존재하는 환경설정
 * yaml 보다 세부적인 설정이 필요하여, @Configration 어노테이션 붙여서 사용
 */

@Configuration
@EnableWebSecurity //기본적으로 스프링부트에서 제공하는 환경설정 대신에 개발자가 만들어놓은 보안 환경설정 활성화!
//스프링부트에서 만든 보안 환경설정을 사용하겠다. 하면 @EnableWebSecurity 주석 제거한다.
@RequiredArgsConstructor
public class SecurityConfig  {
    //private final

}
