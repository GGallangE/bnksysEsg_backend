package com.bnksys.esg.service;

import com.bnksys.esg.data.USERROLE;
import com.bnksys.esg.data.userDto;
import com.bnksys.esg.mapper.UserApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }
}
