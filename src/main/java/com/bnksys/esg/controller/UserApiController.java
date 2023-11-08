package com.bnksys.esg.controller;


import com.bnksys.esg.data.IntrsApiDto;
import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.response.ApiListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.UserApiService;
import com.bnksys.esg.utils.AuthenticationUtils;
import com.bnksys.esg.utils.AuthenticationUtils_ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/userapi")
public class UserApiController {

    @Autowired
    UserApiService userApiService;
    @PostMapping("/useapi")
    // api 사용 혹은 다운 시 사용 이력 ++
    public ResponseEntity<Response> saveApiUses(Authentication authentication, @RequestBody apiResult apiresult){
        Response response = new Response();

        if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        userApiService.saveApiUses(email,apiresult.getApilistid());

        response.setSuccess(true);
        response.getMessages().add("사용 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/interestapi")
    public ResponseEntity<Response> saveIntrsApi(Authentication authentication, @RequestBody IntrsApiDto intrsApiDto){
        Response response = new Response();

        if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        userApiService.saveIntrsApi(email,intrsApiDto);

        response.setSuccess(true);
        response.getMessages().add("등록 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinterestapi")
    public ResponseEntity<ApiListResponse> findIntrsApi(Authentication authentication){
        ApiListResponse response = new ApiListResponse(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<apiResult> apiResults = userApiService.findIntrsApi(email);

        response.setSuccess(true);
        response.getData().put("data", apiResults);
        response.getMessages().add("등록 완료");
        return ResponseEntity.ok(response);
    }
}
