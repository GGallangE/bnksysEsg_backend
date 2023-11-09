package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/apiapplylist")
    // api_detail에서 활용사례 불러오기
    public ResponseEntity<ListResponse<apiApplyDto>> findApi_ApplyLIST(){

        ListResponse<apiApplyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiApplyDto> apiapplyresult = adminService.findApi_ApplyLIST();

        response.setSuccess(true);
        response.getData().put("data", apiapplyresult);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apiapply/confirm")
    public ResponseEntity<Response> updateApi_ApplyStatus(@RequestBody apiApplyDto apiapplyDto){
        Response response = new Response();

        try{
//            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            adminService.updateApi_ApplyStatus(apiapplyDto);

            response.setSuccess(true);
            response.getMessages().add("변경 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/inquiry/answer")
    public ResponseEntity<Response> saveinquiry_Answer(@RequestBody inQuiryDto inquiryDto){
        Response response = new Response();

        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            adminService.saveinquiry_Answer(email, inquiryDto);

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
