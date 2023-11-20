package com.bnksys.esg.service;


import com.bnksys.esg.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiBatchService {

    @Autowired MailService mailService;

    @Autowired ApiResponseService apiResponseService;

    MainMapper mainMapper;

    public ApiBatchService(MainMapper mainMapper){this.mainMapper = mainMapper;}

    public void apilist_Business(int batchlistid,int apilistid, int userid){
        /* 국세청_사업자등록정보 상태조회 서비스 */
        String email = mainMapper.findbyuserid(userid);
        apiResponseService.apilist_Business(email, batchlistid, apilistid);
        System.out.println("1번쨰" + userid);
    }
    public void apilist_Electronic(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
        System.out.println("2번쨰" + userid);
    }
    public void apilist_Business2(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
        System.out.println("3번쨰" + userid);
    }
    public void apilist_Test(int batchlistid,int apilistid, int userid){
        String email = mainMapper.findbyuserid(userid);
        System.out.println("4번째" + userid);
    }
}
