package com.bnksys.esg.controller;


import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spring/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @GetMapping("/result/{apilistid}")
    public ResponseEntity<Map<String, List<apiResult>>> getResultByapilistId(@PathVariable int apilistid){
        List<apiResult> result = connectionService.getResultByapilistId(apilistid);

        Map<String, List<apiResult>> response = new HashMap<>();
        response.put("data", result);
        return ResponseEntity.ok(response);
    }


}
