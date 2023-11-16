package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.batchListDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.service.MyPageService;
import com.bnksys.esg.utils.AuthenticationUtils_ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/mypage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    @GetMapping("/myinterestapi")
    public ResponseEntity<ListResponse<apiResultDto>> findIntrsApi(Authentication authentication){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<apiResultDto> apiResults = myPageService.findIntrsApi(email);

        response.setSuccess(true);
        response.getData().put("data", apiResults);
        response.getMessages().add("나의 관심 API 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myrecentuseapi")
    public ResponseEntity<ListResponse<apiResultDto>> findRecentUseApi(Authentication authentication){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<apiResultDto> apiResults = myPageService.findRecentUseApi(email);

        response.setSuccess(true);
        response.getData().put("data", apiResults);
        response.getMessages().add("최근 사용 API 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinquiry")
    public ResponseEntity<ListResponse<inQuiryDto>> findInQuiry(Authentication authentication){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<inQuiryDto> inquiryResults = myPageService.findInQuiry(email);

        response.setSuccess(true);
        response.getData().put("data", inquiryResults);
        response.getMessages().add("나의 문의 사항 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinquiry_answer")
    public ResponseEntity<ListResponse<inQuiryDto>> findAnswerInQuiry(Authentication authentication, @RequestParam int inquiryid){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<inQuiryDto> inquiryResults = myPageService.findAnswerInQuiry(email, inquiryid);

        response.setSuccess(true);
        response.getData().put("data", inquiryResults);
        response.getMessages().add("나의 문의 사항 답변 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myapiapply")
    public ResponseEntity<ListResponse<apiApplyDto>> findApiApply(Authentication authentication, @RequestParam(value = "apiapplyid", required = false) Integer apiapplyid){
        ListResponse<apiApplyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        String email = authentication.getName();
        List<apiApplyDto> apiApply;
        if(null == apiapplyid){
            apiApply = myPageService.findApiApply(email);
        }else{
            apiApply = myPageService.findDetailApiApply(email, apiapplyid);
        }

        response.setSuccess(true);
        response.getData().put("data", apiApply);
        response.getMessages().add("API 신청 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myapischedule")
    public ResponseEntity<ListResponse<batchListDto>> findApiSchedule(Authentication authentication){
        ListResponse<batchListDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        String email = authentication.getName();
        List<batchListDto> batchList = myPageService.findApiSchedule(email);

        response.setSuccess(true);
        response.getData().put("data", batchList);
        response.getMessages().add("API 예약 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

}
