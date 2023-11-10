package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
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

//        List<noticeDto> noticeList = mainService.getNoticeList();

        response.setSuccess(true);
//        response.getData().put("data", noticeList);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

}
