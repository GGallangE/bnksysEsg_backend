package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiRequestDto;
import com.bnksys.esg.data.requiredItemDto;
import com.bnksys.esg.service.ApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/api")
public class ApiRequestController {


    @Autowired
    private ApiRequestService apiRequestService;

    @PostMapping("/request")
    public ResponseEntity<String> requestApi(@RequestBody apiRequestDto apirequestDto) {
        String methodType = apiRequestService.findMethod_Type(apirequestDto.getApilistid());

        try {
            String response;
            if ("POST".equals(methodType)) {
                response = apiRequestService.postRequestApi(apirequestDto.getApilistid(), apirequestDto.getParams(), apirequestDto.getType());
            } else if ("GET".equals(methodType)) {
                response = apiRequestService.getRequestApi(apirequestDto.getApilistid(), apirequestDto.getParams(), apirequestDto.getType());
            } else {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("지원하지 않는 HTTP 방식입니다.");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Api 요청중 에러.");
        }
    }

    @GetMapping("/getrequired_items")
    public ResponseEntity<List<requiredItemDto>> getRequired_Items(@RequestParam int apilistid) {
        List<requiredItemDto> items = apiRequestService.getRequired_Items(apilistid);
        return ResponseEntity.ok(items);
    }
}
