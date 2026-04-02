package com.project.instagramclone.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * yaml 에 다 작성하지 못한 세부 환경설정
 * 실제 회사 컴퓨터에 위치한 파일의 경로와
 * 유저들에게 제공되어 지는 파일의 위치 경로를
 * 이 경로가 사실은 이 경로에 있는 파일이다 와 같은 매핑 처리
 *
 * ==> 무조건 필요한 것은 아니며, 파일에 관련된 데이터를 서버에 저장하고,
 * 저장된 파일을 브라우저에서 사용해야할 때 필요
 *
 * 이미지, 동영상, 문서 관련 회사가 아니라면 필요없는 환경설정
 */
// ctrl + i
// 프로그램에서는 개발자의 허락없이는 프로젝트에 만들어진 모든 파일에 접근할 수 있는 권한 없다.
// Controller로 주소를 작성하고 주소 내부에 작성한 확장자 .jsp나 .html 파일 이외는
// 모두 접근해도 된다는 권한 허용 거쳐야 한다.

// static 에서 만든 스타일 기능을 소비자들이 사용하하고 있고,
// static_version_2 를 만들어서 스타일 기능 개발을 하고 있는데,
// 소비자들에게 개발중인 스타일 기능 사용되거나 보여지면 사고!
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    //만약 @RequiredArgsConstructor 를 사용하지 않는다면
    //1. @Autowired 사용해서 loginInterceptor 호출 -> 생성자를 따로 만들어야한다.
    // LoginInterceptor loginInterceptor 를 작성할 때 final을 넣을 수 없다.
    //this.loginInterceptor와 같은 생성자 생성을 해야하기 때문에 @RequiredArgsConstructor 사용한다.

    //2. @RequiredArgsConstructor @Autowired 둘 다 사용하지 않는다면
    // LoginInterceptor loginInterceptor = new LoginInterceptor();
    //를 만들어서 사용하나 레거시한 방법으로 스프링부트에서는  @RequiredArgsConstructor 사용하여
    // 코드 작성을 단축한다.
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/api/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // 프로필 이미지 경로 매핑
        // 게시물 이미지 경로 매핑
    }
}