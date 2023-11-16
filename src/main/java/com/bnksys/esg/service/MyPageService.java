package com.bnksys.esg.service;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.batchListDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.mapper.MainMapper;
import com.bnksys.esg.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    MyPageMapper myPageMapper;
    MainMapper mainMapper;

    public MyPageService(MyPageMapper myPageMapper, MainMapper mainMapper){
        this.myPageMapper = myPageMapper;
        this.mainMapper = mainMapper;
    }

    public List<apiResultDto> findIntrsApi(String email) {
        return myPageMapper.findIntrsApi(email);
    }

    public List<apiResultDto> findRecentUseApi(String email) {
        return myPageMapper.findRecentUseApi(email);
    }

    public List<inQuiryDto> findInQuiry(String email) {
        return myPageMapper.findInQuiry(email);
    }

    public List<inQuiryDto> findAnswerInQuiry(String email, int inquiryid) {
        return myPageMapper.findAnswerInQuiry(email, inquiryid);
    }

    public List<apiApplyDto> findApiApply(String email) {
        return myPageMapper.findApiApply(email);
    }

    public List<apiApplyDto> findDetailApiApply(String email, int apiapplyid) {
        return myPageMapper.findDetailApiApply(email,apiapplyid);
    }

    public List<batchListDto> findApiSchedule(String email){
        int userid = mainMapper.findbyemail(email);
        return myPageMapper.findApiSchedule(userid);
    }
}
