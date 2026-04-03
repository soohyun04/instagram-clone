package com.project.instagramclone.controller;

import com.project.instagramclone.common.CookieUtil;
import com.project.instagramclone.model.dto.HashTag;
import com.project.instagramclone.model.dto.Location;
import com.project.instagramclone.model.service.HashTagService;
import com.project.instagramclone.model.service.LocationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ViewController에서 데이터를
 *
 * import org.springframework.ui.Model;
 * Model model
 * model.addAttribute("jSP에서 사용할 변수 명칭", JSP로 전달할 데이터);
 *
 * 로 전달하는 데이터를 백엔드에서 제대로 호출해서 전달하고 있는게 맞는지 확인하기 위한 용도
 * 백엔드 개발자는 viewController를 만들 생각을 하지 않고
 * 보통은 APIController 와 같은 컨트롤러만 만들고, 프론트엔드에게
 * api/endpoint 를 전달한다.
 */
@RestController
@RequiredArgsConstructor
public class APIController {
    private final LocationService locationService;
    private final HashTagService hashTagService;
    private final CookieUtil cookieUtil;

    @GetMapping("/api/kakao-map-list")
    public List<Location> 전체데이터확인(){
        return locationService.장소목록가져오기();
    }

    @GetMapping("/api/hashtags/popular")
    public List<HashTag> 인기해시태그(){
        return hashTagService.인기해시태그가져오기();
    }

    @GetMapping("/api/hashtags/search")
    public List<HashTag> 해시태그검색(@RequestParam String keyword){
        return hashTagService.해시태그검색(keyword);
    }

    // 로그아웃
    @PostMapping("/api/logout")
    public ResponseEntity<?> 로그아웃(HttpServletResponse response) {
        // TODO 1 : access_token 쿠키 삭제
       cookieUtil.delete(response, "access_token");

        // TODO 2 : refresh_token 쿠키 삭제
        cookieUtil.delete(response, "refresh_token");

        // TODO 3 : 200 OK 응답 반환
        return ResponseEntity.ok(Map.of("message", "로그아웃 완료"));
    }

}
