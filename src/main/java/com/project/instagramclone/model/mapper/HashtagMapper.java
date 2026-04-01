package com.project.instagramclone.model.mapper;

import com.project.instagramclone.model.dto.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HashtagMapper {
    //popular hashtag list
    List<HashTag> 인기해시태그조회();

    //hashatg search to keyword
    List<HashTag> 해시태그검색(@Param("keyword")String keyword);

}

