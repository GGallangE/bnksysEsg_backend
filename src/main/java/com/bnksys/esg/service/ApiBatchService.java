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

    public void apilist_Business(int batchlistid,int apilistid, int userid){
        /* 국세청_사업자등록정보 상태조회 서비스 */
        String email = mainMapper.findbyuserid(userid);
        System.out.println("1번쨰 도착");
        apiResponseService.apilist_Business(email, batchlistid, apilistid);
        System.out.println("메일 완료~~~~~");
    }
    public void apilist_Electronic(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
    }
    public void apilist_Business2(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
    }
    public void apilist_Test(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
    }

    public void apirequest (int batchlistid,int apilistid, int userid){
        String methodType = apiRequestMapper.findMethod_Type(apilistid);
        String apiformat = batchListMapper.find_apiformat(batchlistid);
        String email = mainMapper.findbyuserid(userid);
        if(methodType.equals("POST")){
            apiResponseService.request_post(email, batchlistid, apilistid, userid, apiformat);
        }else{
            apiResponseService.request_get(email, batchlistid, apilistid, userid, apiformat);
        }

    }
}
