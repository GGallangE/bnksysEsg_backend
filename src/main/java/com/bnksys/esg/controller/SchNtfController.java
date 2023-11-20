package com.bnksys.esg.controller;

import com.bnksys.esg.data.batchListDto;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.SchNtfService;
import com.bnksys.esg.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/reservation")
public class SchNtfController {
    @Autowired
    SchNtfService schNtfService;

    @PostMapping("/schedule")
    public ResponseEntity<Response> save_BatchSchedule(Authentication authentication, @RequestBody batchListDto batchlistDto){
        Response response = new Response();
        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }
            String cronExpression;
            try{
                cronExpression = schNtfService.convertToCron(batchlistDto.getFrequency()
                        , batchlistDto.getDayofweek(), batchlistDto.getDayofmonth(), batchlistDto.getTime());
            }catch(IllegalArgumentException e){
                response.setSuccess(false);
                response.getMessages().add("오류 발생 : " + e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
            batchlistDto.setBatchtime(cronExpression);
            String email = authentication.getName();
            schNtfService.save_BatchSchedule(email,batchlistDto);
            String title = "API 전송 예약 완료";
            String p_content = "에 대한 결과 메일 발송이 예약 되었습니다.";
            schNtfService.save_Alarm_Complete_Schedule(email, title, p_content, batchlistDto.getApilistid());
            response.setSuccess(true);
            response.getMessages().add("등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
