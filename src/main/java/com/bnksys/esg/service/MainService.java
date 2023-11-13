package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.noticeDto;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MainService {

    MainMapper mainMapper;

    @Autowired
    public MainService(MainMapper mainMapper){
        this.mainMapper = mainMapper;
    }

    public List<apiResultDto> getApiList(String name, String sortBy) {
        return mainMapper.getApiList(name, sortBy);
    }

    public List<noticeDto> getNoticeList(String mainsort){ return mainMapper.getNoticeList(mainsort); }

    public List<noticeDto> getNoticeDetail(int noticeid){ return mainMapper.getNoticeDetail(noticeid); }

    public List<atchDetailFileDto> getNoticeDetail_Atchfile(int atchfileid){ return mainMapper.getNoticeDetail_Atchfile(atchfileid); }

    public List<apiResultDto> getapiList_Top5(String sort) {
        return mainMapper.getapiList_Top5(sort);
    }

}
