package com.bnksys.esg.service;


import com.bnksys.esg.mapper.ApiRequestMapper;
import com.bnksys.esg.mapper.BatchListMapper;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiBatchService {

    @Autowired MailService mailService;

    @Autowired ApiResponseService apiResponseService;

    MainMapper mainMapper;

    ApiRequestMapper apiRequestMapper;

    BatchListMapper batchListMapper;

    public ApiBatchService(MainMapper mainMapper, ApiRequestMapper apiRequestMapper, BatchListMapper batchListMapper){
        this.mainMapper = mainMapper;
        this.apiRequestMapper = apiRequestMapper;
        this.batchListMapper = batchListMapper;
    }

    public void apirequest (int batchlistid,int apilistid, int userid) throws Exception {
        String apiformat = apiRequestMapper.findApiFormat(batchlistid);
        String email = mainMapper.findbyuserid(userid);
        apiResponseService.request(email, batchlistid, apilistid, userid, apiformat);
    }
}
