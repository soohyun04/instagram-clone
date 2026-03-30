package com.project.instagramclone.model.mapper;

import com.project.instagramclone.model.dto.Location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {
    List<Location> 장소목록조회();

}
