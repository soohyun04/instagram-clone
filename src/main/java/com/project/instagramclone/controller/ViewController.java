package com.project.instagramclone.controller;

import com.project.instagramclone.model.dto.Location;
import com.project.instagramclone.model.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 확장자별 파일 명칭
 * 파스칼케이스(시작하는 단어 기준 대문자) : .java
 * java 파일의 경우 카멜케이스 형태로 표기   카멜케이스 : .java 내부에 있는 변수 명칭에서 사용
 * jsp 파일의 경우     _       형태로 표기   스네이트케이스 : .jsp .html
 * png 파일의 경우     -       형태로 표기   케밥케이스 : .css  .js  .xml  폴더명 클래스와 아이디, name 명칭
 *
 * 모든 단어를 대문자로 사용 -> 상수 처럼 변하는 데이터가 없을 때만 사용
 *
 * javascript 내부는 변수 명칭에서 카멜케이스 or 케밥케이스 둘 중 하나로 변수 이름을 작성
 */

@Controller
@RequiredArgsConstructor
public class ViewController {
    //민수가 회원가입 맡아서 매번 회원가입사이트 들어가서 개발 너무 귀찮다...
    //본인이 하는 회원가입 페이지를 /임시로 변경하고 해야지~
    private final LocationService locationService;

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/login")
    public String loginView() {
        return "user/login";
    }

    @GetMapping("/user/register")
    public String registerView() {
        return "user/register";
    }

    @GetMapping("/user/mypage")
    public String myPageView(){
        return "user/mypage";
    }

    @GetMapping("/map")
    public String kakaoMapView(Model model){
        List<Location> 장소목록 = locationService.장소목록가져오기();
        model.addAttribute("locations", 장소목록);
        return "kakao-map";
    }

    @GetMapping("/board/list")
    public String listView(Model model) {
        return "board/list";
    }

    @GetMapping("/board/detail" )
    public String detailView(int board_no, Model model) {
        return  "board/detail";
    }

    @GetMapping("/board/write")
    public String writeView() {
        return "board/write";
    }

    @GetMapping("/board/edit" )
    public String editView(int board_no, Model model) {
        return "board/edit";
    }

    @GetMapping("/users/list")
    public String allUserView(){
        return "user/user-list";
    }
}