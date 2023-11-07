package com.bnksys.esg.controller;


import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/userapi")
public class UserApiController {

    @Autowired
    UserApiService userApiService;
    @PostMapping("/useapi")
    public ResponseEntity<Response> saveApiUses(Authentication authentication, @RequestBody apiResult apiresult){
        Response response = new Response();

        if (authentication == null) {
            response.setSuccess(false);
            response.getMessages().add("토큰이 만료되었거나 없습니다. 다시 로그인을 진행해 주세요.");
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        userApiService.saveApiUses(email,apiresult.getApilistid());

        response.setSuccess(true);
        response.getMessages().add("사용 완료");
        return ResponseEntity.ok(response);
    }
}
