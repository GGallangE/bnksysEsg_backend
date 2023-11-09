package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.data.useCaseDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.UseCaseService;
import com.bnksys.esg.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/usecase")
public class UseCaseController {

    @Autowired
    UseCaseService useCaseService;

    @PostMapping("/usecase")
    public ResponseEntity<Response> saveUseCase(Authentication authentication, @RequestBody useCaseDto usecaseDto){
        Response response = new Response();
        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }

            String email = authentication.getName();
            useCaseService.saveUseCase(email,usecaseDto);

            response.setSuccess(true);
            response.getMessages().add("등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/apidetail")
    // api_detail에서 활용사례 불러오기
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_apiDetail(@RequestParam int apilistid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_apiDetail(apilistid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/usecasedetail")
    // 활용사례 상세 불러오기
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_usecaseDetail(@RequestParam int usecaseid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_usecaseDetail(usecaseid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usecasedetail_apilist")
    public ResponseEntity<ListResponse<apiResultDto>> findUseCase_usecaseDetail_apiList(@RequestParam int usecaseid){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiResultDto> apilist = useCaseService.findUseCase_usecaseDetail_apiList(usecaseid);

        response.setSuccess(true);
        response.getData().put("data", apilist);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mainusecase")
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_usecaseMain(@RequestParam String searchname){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_usecaseMain(searchname);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }
    // usecasddetail mapper.xml 작성 후 , 조회시 id없어지거나 하면 return 먹여주고 list pathvariable되있는거 param로 바꾸기
}
