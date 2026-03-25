package com.project.instagramclone.model.service;

import com.project.instagramclone.common.EmailCodeService;
import com.project.instagramclone.model.dto.User;
import com.project.instagramclone.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailCodeService emailCodeService;

    public boolean 이메일중복체크기능(String email) {
        return userMapper.이메일중복체크(email) > 0;
    }

    public boolean 회원가입(User user) {

        if (이메일중복체크기능(user.getEmail())) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.회원가입(user);
        return true;
    }

    public void 인증번호발송(String email) {
        emailCodeService.인증번호발송(email);    // emailCodeService 의 기능 호출
    }

    public boolean 인증번호검증(String email, String code) {
        return emailCodeService.인증번호확인(email, code);
    }
}