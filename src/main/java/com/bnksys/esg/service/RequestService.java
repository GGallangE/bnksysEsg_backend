package com.bnksys.esg.service;


import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.mapper.MainMapper;
import com.bnksys.esg.mapper.RequestMapper;
import com.bnksys.esg.mapper.UserApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    MainMapper mainMapper;
    RequestMapper requestMapper;

    @Autowired
    public RequestService(MainMapper mainMapper, RequestMapper requestMapper){
        this.mainMapper = mainMapper;
        this.requestMapper = requestMapper;
    }
    public void saveApplyApi(String email, apiApplyDto apiapplyDto){
        int userid = mainMapper.findbyemail(email);
        requestMapper.saveApplyApi(userid,apiapplyDto);
    }

    public void saveInquiry(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        requestMapper.saveInquiry(userid,inquiryDto);
    }
}
