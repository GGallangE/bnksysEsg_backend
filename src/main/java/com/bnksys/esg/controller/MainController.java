package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.noticeDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spring/main")
public class MainController {
    @Autowired
    MainService mainService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, List<apiResultDto>>> getApiList(@RequestParam String name, @RequestParam String sortBy) {

        List<apiResultDto> apiResults = mainService.getApiList(name, sortBy);
        Map<String, List<apiResultDto>> response = new HashMap<>();
        response.put("data", apiResults);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice")
    public ResponseEntity<ListResponse<noticeDto>> getNoticeList(){
        ListResponse<noticeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<noticeDto> noticeList = mainService.getNoticeList();

        response.setSuccess(true);
        response.getData().put("data", noticeList);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice/detail")
    public ResponseEntity<ListResponse<noticeDto>> getNoticeDetail(@RequestParam int noticeid){
        ListResponse<noticeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<noticeDto> noticeList = mainService.getNoticeDetail(noticeid);

        response.setSuccess(true);
        response.getData().put("data", noticeList);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notice/detailatchfile")
    public ResponseEntity<ListResponse<atchDetailFileDto>> getNoticeDetail_Atchfile(@RequestParam int atchfileid){
        ListResponse<atchDetailFileDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<atchDetailFileDto> atchfileList = mainService.getNoticeDetail_Atchfile(atchfileid);

        response.setSuccess(true);
        response.getData().put("data", atchfileList);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view_recent/top5")
    public ResponseEntity<ListResponse<apiResultDto>> getapiList_Top5(@RequestParam String sort){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiResultDto> apilist_top5 = mainService.getapiList_Top5(sort);

        response.setSuccess(true);
        response.getData().put("data", apilist_top5);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

}
