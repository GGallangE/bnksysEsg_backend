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

    public List<apiResultDto> getApiList(String name, String sortBy, int page, int pageSize) {
        int offset = page * pageSize;
        return mainMapper.getApiList(name, sortBy, offset, pageSize);
    }

    public List<apiResultDto> getApiList_auth(String name, String sortBy, String email, int page, int pageSize) {
        int offset = page * pageSize;
        int userid = mainMapper.findbyemail(email);
        return mainMapper.getApiList_auth(name, sortBy, userid, offset, pageSize);
    }

    public List<noticeDto> getNoticeList(String mainsort){ return mainMapper.getNoticeList(mainsort); }

    public List<noticeDto> getNoticeDetail(int noticeid){ return mainMapper.getNoticeDetail(noticeid); }

    public List<atchDetailFileDto> getNoticeDetail_Atchfile(int atchfileid){ return mainMapper.getNoticeDetail_Atchfile(atchfileid); }

    public List<apiResultDto> getapiList_Top5(String sort) {
        return mainMapper.getapiList_Top5(sort);
    }

    public boolean isSameUser(String email) {
        return mainMapper.isSameUser(email) > 0;
    }

    public int findUseridByEmail(String email){return mainMapper.findbyemail(email);}

}
