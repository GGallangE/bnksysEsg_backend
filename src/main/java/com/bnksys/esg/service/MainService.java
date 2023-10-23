package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MainService {

    @Value("${elc.url}")
    private String url;

    @Value("${elc.serviceKey}")
    private String serviceKey;
    MainMapper mainMapper;

    @Autowired
    public MainService(MainMapper mainMapper){
        this.mainMapper = mainMapper;
    }

    public List<Member> getAllUser() {
        return mainMapper.getAllUser();
    }

    public List<userboard> getUserWithBoards(){
        return mainMapper.getUserWithBoards();
    }

    public List<apiResult> getApiList(String name) {
        return mainMapper.getApiList(name);
    }

//    public List<apiResult> getApiList() {
//        return mainMapper.getApiList();
//    }

}
