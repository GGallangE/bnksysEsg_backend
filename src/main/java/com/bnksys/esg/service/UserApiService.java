package com.bnksys.esg.service;

import com.bnksys.esg.data.*;
import com.bnksys.esg.mapper.MainMapper;
import com.bnksys.esg.mapper.UserApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApiService {

    UserApiMapper userapiMapper;
    MainMapper mainMapper;

    @Autowired
    public UserApiService(UserApiMapper userapiMapper, MainMapper mainMapper){
        this.userapiMapper = userapiMapper;
        this.mainMapper = mainMapper;
    }
    public void saveApiUses(String email,int apilistid){
        int userId = mainMapper.findbyemail(email);
        userapiMapper.saveApiUses(userId,apilistid);
    };

    public void saveIntrsApi(String email, IntrsApiDto intrsApiDto){
        int userId = mainMapper.findbyemail(email);
        if(userapiMapper.countIntrsApi(userId,intrsApiDto.getApilistid()) == 0){
            userapiMapper.saveIntrsApi(userId,intrsApiDto);
        }else{
            userapiMapper.updateIntrsApi(userId,intrsApiDto);
        }
    }

}
