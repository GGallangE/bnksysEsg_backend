package com.bnksys.esg.service;

import com.bnksys.esg.data.*;
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

    public List<apiResultDto> findIntrsApi(String email, int page, int pageSize) {
        int offset = page * pageSize;
        return myPageMapper.findIntrsApi(email, offset, pageSize);
    }

    public List<apiResultDto> findRecentUseApi(String email, int page, int pageSize) {
        int offset = page * pageSize;
        return myPageMapper.findRecentUseApi(email, offset, pageSize);
    }

    public List<inQuiryDto> findInQuiry(String email, int page, int pageSize) {
        int offset = page * pageSize;
        return myPageMapper.findInQuiry(email, offset, pageSize);
    }

    public List<inQuiryDto> findAnswerInQuiry(String email, int inquiryid) {
        return myPageMapper.findAnswerInQuiry(email,inquiryid);
    }

    public List<apiApplyDto> findApiApply(String email, int page, int pageSize) {
        int offset = page * pageSize;
        return myPageMapper.findApiApply(email, offset, pageSize);
    }

    public List<apiApplyDto> findDetailApiApply(String email, int apiapplyid) {
        return myPageMapper.findDetailApiApply(email,apiapplyid);
    }

    public List<batchListDto> findApiSchedule(String email, Integer batchlistid){
        int userid = mainMapper.findbyemail(email);
        return myPageMapper.findApiSchedule(userid, batchlistid);
    }

    public List<alarmDto> findAlarm(String email, Integer alarmid){
        int userid = mainMapper.findbyemail(email);
        return myPageMapper.findAlarm(userid, alarmid);
    }

    public int getNotReadAlarmCount(String email){
        int userid = mainMapper.findbyemail(email);
        return myPageMapper.getNotReadAlarmCount(userid);
    }

    public void deleteApiSchedule(String email, int batchlistid){
        int userid = mainMapper.findbyemail(email);
        myPageMapper.deleteApiSchedule(userid, batchlistid);
    }

    public void updateApiScheduleTime(String email, batchListDto batchlistDto){
        int userid = mainMapper.findbyemail(email);
        myPageMapper.updateApiScheduleTime(userid, batchlistDto);
    }

    public void update_readAlarm(String email, int alarmid){
        int userid = mainMapper.findbyemail(email);
        myPageMapper.update_readAlarm(userid, alarmid);
    }

    public boolean isSameApiScheduleUser(String email, int batchlistid){
        return myPageMapper.isSameApiScheduleUser(email,batchlistid) > 0;
    }
}
