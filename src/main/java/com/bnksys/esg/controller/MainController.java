package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.noticeDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.MailService;
import com.bnksys.esg.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spring/main")
public class MainController {
    @Autowired
    MainService mainService;
    @Autowired
    MailService mailService;

    @GetMapping("/search")
    /* 메인화면에 API LIST 검색을 통한 API 목록을 나타내기 위한 메서드 */
    public ResponseEntity<Map<String, List<apiResultDto>>> getApiList(@RequestParam String name, @RequestParam String sortBy) {
        List<apiResultDto> apiResults;

        // 로그인이 되어있다면 나의 관심 API도 함께 출력, 로그인이 되어있지 않으면 annoymousUser라고 뜸.
        if("anonymousUser" != SecurityContextHolder.getContext().getAuthentication().getName()){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();    // 로그인된 정보로 이메일 추출
            apiResults = mainService.getApiList_auth(name, sortBy, email);
        }
        // 로그인이 되어잇지 않은 상태라면 그냥 API 목록만 출력
        else{
            apiResults = mainService.getApiList(name, sortBy);
        }
        Map<String, List<apiResultDto>> response = new HashMap<>();
        response.put("data", apiResults);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice")
    /* 공지사항 Top 5를 뽑아주는 메서드 */
    public ResponseEntity<ListResponse<noticeDto>> getNoticeList(@RequestParam(value = "mainsort", required = false) String mainsort){
        ListResponse<noticeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<noticeDto> noticeList = mainService.getNoticeList(mainsort);

        response.setSuccess(true);
        response.getData().put("data", noticeList);
        response.getMessages().add("공지사항 LIST 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice/detail")
    public ResponseEntity<ListResponse<noticeDto>> getNoticeDetail(@RequestParam int noticeid){
        ListResponse<noticeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<noticeDto> noticeList = mainService.getNoticeDetail(noticeid);

        response.setSuccess(true);
        response.getData().put("data", noticeList);
        response.getMessages().add("공지사항 상세 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice/detailatchfile")
    public ResponseEntity<ListResponse<atchDetailFileDto>> getNoticeDetail_Atchfile(@RequestParam int atchfileid){
        ListResponse<atchDetailFileDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<atchDetailFileDto> atchfileList = mainService.getNoticeDetail_Atchfile(atchfileid);

        response.setSuccess(true);
        response.getData().put("data", atchfileList);
        response.getMessages().add("공지사항 첨부파일 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view_recent/top5")
    public ResponseEntity<ListResponse<apiResultDto>> getapiList_Top5(@RequestParam String sort){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiResultDto> apilist_top5 = mainService.getapiList_Top5(sort);

        response.setSuccess(true);
        response.getData().put("data", apilist_top5);
        response.getMessages().add("Top 5 조회 완료");
        return ResponseEntity.ok(response);
    }
}
