package com.project.instagramclone.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * yaml 에 다 적성하지 못한 세부 환경설정
 * 실제 회사 컴퓨터에 위치한 파일의 경로와
 * 유저들에게 제공되어지는 파일의 위치 경로를
 * 이 경로와 사실은 이 경로에 있는 파일이다 와 같은 매핑 처리
 *
 * ==> 무조건 필요한 것은 아니며, 파일에 관련된 데이터를 서버에 저장하고,
 * 저장된 파일을 브라우저에서 사용해야할 때 필요
 *
 * 이미지, 동영상, 문서 관련 회사가 아니라면 필요없는 환경설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
}
