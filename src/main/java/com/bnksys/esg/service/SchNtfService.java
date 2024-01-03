package com.bnksys.esg.service;

import com.bnksys.esg.data.batchListDto;
import com.bnksys.esg.mapper.SchNtfMapper;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.stereotype.Service;

@Service
public class SchNtfService {
    SchNtfMapper schNtfMapper;
    MainMapper mainMapper;


    public SchNtfService(SchNtfMapper schNtfMapper, MainMapper mainMapper){
        this.schNtfMapper = schNtfMapper;
        this.mainMapper = mainMapper;
    }

    public int save_BatchSchedule(String email,batchListDto batchlistDto){
        int userid = mainMapper.findbyemail(email);
        int batchlistid = schNtfMapper.MaxbatchlistId();
        batchlistDto.setBatchlistid(batchlistid);
        schNtfMapper.save_BatchSchedule(userid, batchlistDto);
        schNtfMapper.save_BatchDetailArgs(userid,batchlistid,batchlistDto.getBatchDetailargsDto());
        return batchlistid;
    }

    public void save_Alarm_Complete_Schedule(String email, String title, String p_content, int apilistid){
        int senduser = mainMapper.findbyemail("admin");
        int rcvuser = mainMapper.findbyemail(email);
        String apiname = schNtfMapper.getApiName(apilistid);
        String content = apiname + p_content;
        schNtfMapper.save_Alarm(title,content,senduser,rcvuser);
    }

    public String convertToCron(String frequency, String dayOfWeek, String dayOfMonth, String time){
        if(frequency == null || frequency.isEmpty()){
            throw new IllegalArgumentException("배치 주기를 선택하세요.");
        }
        if(time == null || time.isEmpty()){
            throw new IllegalArgumentException("시간을 선택하세요.");
        }

        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = 0;
        if("00" != timeParts[1]) minute = Integer.parseInt(timeParts[1]);

        switch (frequency){
            case "daily":
                return String.format("0 %d %d ? * *", minute, hour);
            case "weekly":
                if(null == dayOfWeek || dayOfWeek.isEmpty()) throw new IllegalArgumentException("매주를 선택하셨습니다. 요일을 정해주세요");
                return String.format("0 %d %d ? * %s", minute, hour, dayOfWeek);
            case "monthly":
                if(null == dayOfMonth || dayOfMonth.isEmpty()) throw new IllegalArgumentException("매월을 선택하셨습니다. 날짜를 정해주세요");
                int dayofMonth = Integer.parseInt(dayOfMonth);
                return String.format("0 %d %d %d * ?", minute, hour, dayofMonth);
            default:
                throw new IllegalArgumentException("Unsupported frequency: " + frequency);
        }
    }
}
