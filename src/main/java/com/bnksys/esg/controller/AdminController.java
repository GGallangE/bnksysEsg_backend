package com.bnksys.esg.controller;

import com.bnksys.esg.data.*;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.AdminService;
import com.bnksys.esg.service.AtchFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AtchFileService atchFileService;

    @GetMapping("/apiapplylist")
    public ResponseEntity<ListResponse<apiApplyDto>> findApi_ApplyLIST(@RequestParam(value = "apiapplyid", required = false) Integer apiapplyid){

        ListResponse<apiApplyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiApplyDto> apiapplyresult = adminService.findApi_ApplyLIST(apiapplyid);

        response.setSuccess(true);
        response.getData().put("data", apiapplyresult);
        response.getMessages().add("API 신청 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apiapply/confirm")
    public ResponseEntity<Response> updateApi_ApplyStatus(@RequestBody apiApplyDto apiapplyDto){
        Response response = new Response();

        try{
//            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            adminService.updateApi_ApplyStatus(apiapplyDto);

            response.setSuccess(true);
            response.getMessages().add("등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/apilist")
    public ResponseEntity<ListResponse<apiResultDto>> findApiList(@RequestParam(value = "apilistid", required = false) Integer apilistid){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiResultDto> apiresult = adminService.findApiList(apilistid);

        response.setSuccess(true);
        response.getData().put("data", apiresult);
        response.getMessages().add("API LIST 조회 완료");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/apilist/save")
    public ResponseEntity<Response> saveApiList(@RequestBody apiResultDto apiresultDto){
        Response response = new Response();

        try{
            if(0 == apiresultDto.getApilistid()){
                int apilistid = adminService.maxApiListId();
                apiresultDto.setApilistid(apilistid);
                adminService.saveApiList(apiresultDto);
                response.getMessages().add("등록 완료");
            }else{
                adminService.updateApiList(apiresultDto);
                response.getMessages().add("수정 완료");
            }

            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/inquiry/list")
    public ResponseEntity<ListResponse<inQuiryDto>> findinQuiry(@RequestParam(value = "inquiryid", required = false) Integer inquiryid){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<inQuiryDto> inquiryList = adminService.findinQuiry(inquiryid);

        response.setSuccess(true);
        response.getData().put("data", inquiryList);
        response.getMessages().add("문의 사항 조회 완료");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/inquiry/list/answer")
    public ResponseEntity<ListResponse<inQuiryDto>> findinQuiryAnswer(@Param("inquiryid") int inquiryid){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<inQuiryDto> inquiryAnswer = adminService.findinQuiryAnswer(inquiryid);

        response.setSuccess(true);
        response.getData().put("data", inquiryAnswer);
        response.getMessages().add("문의 사항 답변 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/inquiry/answer")
    public ResponseEntity<Response> saveinquiry_Answer(@RequestBody inQuiryDto inquiryDto){
        Response response = new Response();

        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(adminService.findAnswerCount(inquiryDto.getParentid()) == 0){
                adminService.saveinquiry_Answer(email, inquiryDto);
                response.getMessages().add("등록 완료");
            }else{
                adminService.updateinquiry_Answer(email, inquiryDto);
                response.getMessages().add("수정 완료");
            }

            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/notice/create")
    public ResponseEntity<Response> savenotice(@RequestPart(value = "files", required = false) MultipartFile[] files,
                                               @RequestPart noticeDto noticedto){
        Response response = new Response();
        int atchfileid = 0;
        try{
            if(files != null && files.length > 0){
                atchfileid = atchFileService.saveAtchFile(files);
            }

            adminService.saveNotice(noticedto.getNoticenm(), noticedto.getNoticecntn(), atchfileid);

            response.setSuccess(true);
            response.getMessages().add("공지사항 등록 완료");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
