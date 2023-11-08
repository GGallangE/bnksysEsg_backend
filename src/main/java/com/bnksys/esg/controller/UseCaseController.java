package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.service.UseCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/usecase")
public class UseCaseController {

    @Autowired
    UseCaseService useCaseService;

    @GetMapping("/apidetail")
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_apiDetail(@RequestParam int apilistid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_apiDetail(apilistid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/usecasedetail")
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_usecaseDetail(@RequestParam int usecaseid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_usecaseDetail(usecaseid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }
    // usecasddetail mapper.xml 작성 후 , 조회시 id없어지거나 하면 return 먹여주고 list pathvariable되있는거 param로 바꾸기
}
