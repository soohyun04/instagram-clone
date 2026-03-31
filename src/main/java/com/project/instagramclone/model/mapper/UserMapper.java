package com.project.instagramclone.model.mapper;

import com.project.instagramclone.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper{
    void 회원가입(User user);

    int 이메일중복체크(String email); //인증번호 보내기 전에 DB에 존재하는 이메일인가? 체크

    List<User> 모든회원조회();
}
