package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.RequestService;
import com.bnksys.esg.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("/apiapply")
    public ResponseEntity<Response> saveApplyApi(Authentication authentication, @RequestBody apiApplyDto apiapplyDto){
        Response response = new Response();

        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }

            String email = authentication.getName();
            requestService.saveApplyApi(email, apiapplyDto);

            response.setSuccess(true);
            response.getMessages().add("등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/inquiry")
    public ResponseEntity<Response> saveInquiry(Authentication authentication, @RequestBody inQuiryDto inquiryDto){
        Response response = new Response();

        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }

            String email = authentication.getName();
            requestService.saveInquiry(email, inquiryDto);

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
