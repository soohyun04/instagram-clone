package com.project.instagramclone.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String profileImg;
    /*
    프로젝트를 진행할 때 각 변수명칭 또한 어떤 테이블에서 어떻게 쓰이는 변수인지 상세히 명칭 작성
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private LocalDateTime usercreatedAt;
    private String userProfileImg;
    */
}
