package com.project.instagramclone.model.dto;

import lombok.*;

// @Data = @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor 가 포함되어 있다.
// Constructor 제외하고 자주 사용하는 lombok 어노테이션들은 @Data 로 뭉쳐서 사용할 수 있다.
// @Getter @Setter @ToString
@Data @AllArgsConstructor @NoArgsConstructor
public class Location {
    private Integer id; //null 일 경우 대비하여 int 대신 Integer, int는 null을 0으로 치환
    private String name;
    private String description;
    private Double lat;
    private Double lng;
    private String icon;
    private String color;
}
