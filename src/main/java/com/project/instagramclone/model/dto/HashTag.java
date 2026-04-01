package com.project.instagramclone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class HashTag {
    private Integer id;
    private String name; //해시태그 이름(# 제외, 예: #spring 이라면 spring 만!)
    private Integer count; //게시물 수
}
