package com.project.instagramclone.model.service;

import com.project.instagramclone.common.EmailCodeService;
import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailCodeService emailCodeService;

    /**
     * 특정기능을 만든다 싶을 때는 우선 코드내 직접적으로 작성을 하고,
     * 한 번에 작성한 기능을 세부적으로 기능분리를 할 수 있는가?
     *
     * 에러/ 예외 상황이 발생하지 않아도, 개발팀 내에서 리팩토링이 필요하다 하는 파트의 기능을
     * 원활하게 수정하기 위하여 나누는 작업
     *
     * 혼자서 기능 세분화 코딩 해보고 싶다.
     *
     * 1. 어떤 기능을 만들고 싶은지 결과물에 작성
     * 2. 아무런 생각없이 결과물 만들기
     * 3. 결과물을 만든 다음에 기능세분화가 가능한 기능이 무엇이 있는지 찾아보기
     * 4. 기능 세분화 작업 진행해보기
     *
     */

    public boolean 이메일중복체크기능(String email) {
        return userMapper.이메일중복체크(email) > 0;
    }
    //만약에 핫메일이거나, 아웃룩 이메일일 경우 회원가입 못하므로 중복체크 자체를 할 일이 없으므로
    //돌려보내기
    // 이메일 중복 체크를 할 때 숫자로 시작하거나 특정 이메일로 시작하는 이메일은
    //우리회사에 가입을 못하게 했으므로 아예 중복체크에서 제외

    public boolean 회원가입(User user) {

       /*
       지금은 코드 한 줄로 이메일 중복체크를 하지만,
       나중에 이메일 중복체크를 진행할 때 -> outlook hotmail의 경우 회원가입을 못하게 하는 회사도 있고,
       이메일 중복 체크 자체를 핸드폰 번호 인등을 했는가 먼저 본 다음에 이메일 중복체크를 진행
       if (userMapper.이메일중복체크(user.getEmail()) > 0) return false;*/
        if (이메일중복체크기능(user.getEmail())) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.회원가입(user);
        return true;
    }

    /**
     * 카카오 소셜 로그인 전용 회원가입
     * - SNS 회원가입 할 때 email은 인증번호 발송 검증 건너뛰고, Social 측에서 인증 대신
     * - 인증번호 발송과 확인을 하지 않고, 대신에 jsp 에서 이메일 입력창을 disabled(=비활성화)처리
     * - 카카오 유저는 비밀번호 불필요 -> password -> nul
     * - 이미 있는 이메일이면 회원가입 무시
     */
    public void 카카오회원가입(User user){
        //비밀번호가 필요 없기 때문에 비밀번호 암호화 작업 필요 없다.
        // 웹 브라우저 -> jsp -> javascript -> Controller에서 service로 유저가 작성한
        // 유저 정보를 User user 라는 문서에 임시 보관이 되어 있는 상태
        // 이메일중복체크기능의 경우 유저가 프론트엔드부터 작성해놓은 유저정보에서 email을 어떻게 기입해서
        // 백엔드로 저장이 되었는지를 가져오는 것
        //user.getEmail() = 프론트엔드에서 유저가 작성한 이메일을 자바에서 user 라는 공간에서 임시 보관
        //                  임시보관된 이메일을 가져와서 이메일중복체크기능() 으로db에 이메일이 내장되어 있는가 확인
        if(이메일중복체크기능(user.getEmail()))return;
        userMapper.회원가입(user);

    }

    public void 네이버회원가입(User user){
        if(이메일중복체크기능(user.getEmail()))return;
        userMapper.회원가입(user);
    }

    public void 인증번호발송(String email) {
        emailCodeService.인증번호발송(email);    // emailCodeService 의 기능 호출
    }

    public boolean 인증번호검증(String email, String code) {
        return emailCodeService.인증번호확인(email, code);
    }

    /*
    필요한 타입 : int

    제공된 타입 : List

    만약 내가 db에서 가져온 총 개수가 궁금해 -> int
    만약 내가 db에서 가져온 통 데이터가 궁금해 -> List<User>

    이정도는 팀장님이 이렇게 했으면 좋겠다 가이드라인 제공
     */
    public List<User> 모든회원조회(){
        return userMapper.모든회원조회();
    }
}