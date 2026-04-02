package com.project.instagramclone.common;

import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final  JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserMapper userMapper;
    // ctrl + i

    /**
     * preHandle         인터셉트의 중요 메서드, 컨트롤러 실행 전에 자동으로 호출되는 기능
     * @param 요청       브라우저에서 서버로 보내는 HTTP요청 (쿠키, 헤더, URL 등 포함)
     * @param 응답       서버에서 브라우저로 돌려보내는 HTTP 응답 (리다이렉트 등에 사용)
     * @param 핸들러     실행 예정인 컨트롤러 메서드 정보
     * @return           true -> 컨트롤러로 통과 /false -> 컨트롤러 진입 차단
     * @throws Exception 필터 처리 중 발생할 수 있는 예외
     */
    @Override
    public boolean preHandle(HttpServletRequest 요청, HttpServletResponse 응답, Object 핸들러) throws Exception {
        //쿠키에서 access_token 꺼내기
        String 토큰 = cookieUtil.get(요청, "access_token");

        //토큰이 있고, 유효하면 -> 로그인 유저를 request에 담아두기
        if(토큰 != null && jwtUtil.isValidToken(토큰)){
            String 이메일 = jwtUtil.getEmail(토큰);
            User 로그인유저 = userMapper.이메일로회원찾기(이메일);
            //JSP 에서 ${loginUser}로 접근할 수 있도록 request 에 세팅
            요청.setAttribute("loginUser", 로그인유저);
        }
        //다음 단계로 진행(false이면 요청 차단)
        return true;
    }
}
