package com.bnksys.esg.controller;

import com.bnksys.esg.data.alarmDto;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.ApiResponseService;
import com.bnksys.esg.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring/test")
public class TestController {

    @Autowired
    MailService mailService;

    @Autowired
    ApiResponseService apiResponseService;

    @GetMapping("/admin/page")
    // 관리자 테스트
    public ResponseEntity<String> adminPage() {
        return ResponseEntity.ok("Welcome to the admin page!");
    }

    // Mail - SMTP Test
    @PostMapping("/send-mail")
    public ResponseEntity<Response> sendMail(){
        Response response = new Response();

        try{
            mailService.sendMail("ehrbs2997@naver.com");
            response.setSuccess(true);
            response.getMessages().add("메일 전송 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("메일 전송 실패");
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/checkapi")
    public ResponseEntity<Response> checkapi(){
        Response response = new Response();
        try{
            apiResponseService.apilist_Business("ehrbs2997@naver.com");
            response.setSuccess(true);
            response.getMessages().add("수정 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
