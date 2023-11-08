package com.bnksys.esg.service;

import com.bnksys.esg.data.IntrsApiDto;
import com.bnksys.esg.data.USERROLE;
import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.data.userDto;
import com.bnksys.esg.mapper.UserApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApiService {

    UserApiMapper userapiMapper;

    @Autowired
    public UserApiService(UserApiMapper userapiMapper){
        this.userapiMapper = userapiMapper;
    }
    public void saveApiUses(String email,int apilistid){
        int userId = userapiMapper.findbyemail(email);
        userapiMapper.saveApiUses(userId,apilistid);
    };

    public void saveIntrsApi(String email, IntrsApiDto intrsApiDto){
        int userId = userapiMapper.findbyemail(email);
        if(userapiMapper.countIntrsApi(userId,intrsApiDto.getApilistid()) == 0){
            userapiMapper.saveIntrsApi(userId,intrsApiDto);
        }else{
            userapiMapper.updateIntrsApi(userId,intrsApiDto);
        }
    }

    public List<apiResult> findIntrsApi(String email) {
        return userapiMapper.findIntrsApi(email);
    }
}
