package com.bnksys.esg.controller;


import com.bnksys.esg.data.IntrsApiDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.useCaseDto;
import com.bnksys.esg.response.ListResponse;
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
    public ResponseEntity<Response> saveApiUses(Authentication authentication, @RequestBody apiResultDto apiresult){
        Response response = new Response();

        if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
            return ResponseEntity.badRequest().body(response);
        }

        if (apiresult.getApilistid() == 0) {
            // apiresultid가 빈 값이면 에러 응답을 생성하고 반환
            response.setSuccess(false);
            response.getMessages().add("apiresultid는 필수 값입니다.");
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

        if (intrsApiDto.getApilistid() == 0 || intrsApiDto.getStcd() == null) {
            response.setSuccess(false);
            response.getMessages().add("apilistid와 stcd는 필수값 입니다.");
            return ResponseEntity.badRequest().body(response);
        }
        
        String email = authentication.getName();
        userApiService.saveIntrsApi(email,intrsApiDto);

        response.setSuccess(true);
        response.getMessages().add("등록 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinterestapi")
    public ResponseEntity<ListResponse<apiResultDto>> findIntrsApi(Authentication authentication){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<apiResultDto> apiResults = userApiService.findIntrsApi(email);

        response.setSuccess(true);
        response.getData().put("data", apiResults);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usecase")
    public ResponseEntity<Response> saveUseCase(Authentication authentication, @RequestBody useCaseDto usecaseDto){
        Response response = new Response();
        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }

            String email = authentication.getName();
            userApiService.saveUseCase(email,usecaseDto);

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
