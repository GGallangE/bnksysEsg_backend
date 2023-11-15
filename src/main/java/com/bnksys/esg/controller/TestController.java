package com.bnksys.esg.controller;

import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/test")
public class TestController {

    @Autowired
    MailService mailService;

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
}
