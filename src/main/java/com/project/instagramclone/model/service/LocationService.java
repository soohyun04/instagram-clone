package com.project.instagramclone.model.service;

import com.project.instagramclone.model.dto.Location;
import com.project.instagramclone.model.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationMapper locationMapper;

    public List<Location> 장소목록가져오기(){
        return locationMapper.장소목록조회();
    }
}
