package com.project.instagramclone.model.service;

import com.project.instagramclone.model.dto.HashTag;
import com.project.instagramclone.model.mapper.HashtagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//@Autowired 절대 금지!!!!!!!
@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashtagMapper hashtagMapper;

    public List<HashTag> 인기해시태그가져오기(){
        return hashtagMapper.인기해시태그조회();
    }

    public List<HashTag> 해시태그검색(String keyword){
        return hashtagMapper.해시태그검색(keyword);
    }
}
