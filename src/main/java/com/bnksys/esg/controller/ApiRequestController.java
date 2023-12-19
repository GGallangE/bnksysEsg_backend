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
        String methodType = "GET";

        try {
            String response;
            if ("POST".equals(methodType)) {
                response = apiRequestService.postRequestApi(apirequestDto.getApilistid(), apirequestDto.getParams());
            } else if ("GET".equals(methodType)) {
                response = apiRequestService.getRequestApi(apirequestDto.getApilistid(), apirequestDto.getParams());
            } else {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Unsupported HTTP method");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during API request");
        }
    }

    @GetMapping("/getrequired_items")
    public ResponseEntity<List<requiredItemDto>> getRequired_Items(@RequestParam int apilistid) {
        List<requiredItemDto> items = apiRequestService.getRequired_Items(apilistid);
        return ResponseEntity.ok(items);
    }
}
