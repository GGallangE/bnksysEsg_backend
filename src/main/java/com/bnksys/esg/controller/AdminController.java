package com.bnksys.esg.controller;

import com.bnksys.esg.data.*;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.AdminService;
import com.bnksys.esg.service.AtchFileService;
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
