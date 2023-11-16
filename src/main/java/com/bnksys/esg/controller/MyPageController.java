package com.bnksys.esg.controller;

import com.bnksys.esg.data.*;
import com.bnksys.esg.response.CountResponse;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.MyPageService;
import com.bnksys.esg.service.SchNtfService;
import com.bnksys.esg.utils.AuthenticationUtils_ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/mypage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    @Autowired
    SchNtfService schNtfService;

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
    public ResponseEntity<ListResponse<batchListDto>> findApiSchedule(Authentication authentication, @RequestParam(value = "batchlistid", required = false) Integer batchlistid){
        ListResponse<batchListDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        String email = authentication.getName();
        List<batchListDto> batchList = myPageService.findApiSchedule(email, batchlistid);

        response.setSuccess(true);
        response.getData().put("data", batchList);
        response.getMessages().add("API 예약 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/myapischedule/delete")
    public ResponseEntity<Response> deleteApiSchedule(Authentication authentication, @RequestBody batchListDto batchlistDto){
        Response response = new Response();
        try{
            String email = authentication.getName();
            myPageService.deleteApiSchedule(email,batchlistDto.getBatchlistid());

            response.setSuccess(true);
            response.getMessages().add("삭제 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/myapischedule/updatetime")
    public ResponseEntity<Response> updateApiScheduleTime(Authentication authentication, @RequestBody batchListDto batchlistDto){
        Response response = new Response();
        try{
            String email = authentication.getName();
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
            myPageService.updateApiScheduleTime(email,batchlistDto);
            String title = "API 전송 예약 시간 변경";
            schNtfService.save_Alarm_Complete_Schedule(email, title, batchlistDto);

            response.setSuccess(true);
            response.getMessages().add("수정 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/myalarm")
    public ResponseEntity<ListResponse<alarmDto>> findAlarm(Authentication authentication, @RequestParam(value = "alarmid", required = false) Integer alarmid){
        ListResponse<alarmDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if (!AuthenticationUtils_ApiResponse.checkAuthentication(authentication, response).isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        String email = authentication.getName();
        List<alarmDto> alamrList = myPageService.findAlarm(email, alarmid);

        response.setSuccess(true);
        response.getData().put("data", alamrList);
        response.getMessages().add("ALARM 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myalarm/notread_count")
    public ResponseEntity<CountResponse> getNotReadAlarmCount(Authentication authentication) {
        CountResponse response = new CountResponse();

        if (authentication == null) {
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String email = authentication.getName();
            int count = myPageService.getNotReadAlarmCount(email);

            response.setSuccess(true);
            response.setCount(count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/myalarm/read")
    public ResponseEntity<Response> update_readAlarm(Authentication authentication, @RequestBody alarmDto alarmdto){
        Response response = new Response();
        try{
            String email = authentication.getName();
            myPageService.update_readAlarm(email, alarmdto.getAlarmid());

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
