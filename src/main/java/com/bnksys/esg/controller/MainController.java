package com.bnksys.esg.controller;

import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import com.bnksys.esg.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {
    @Autowired
    MainService mainService;

    @GetMapping("/User")
    public List<Member> getAllUsers(){
        List<Member> users = mainService.getAllUser();
        return users;
    }

    @GetMapping("/boardid")
    public ResponseEntity<Map<String, List<userboard>>> getUserWithBoards() {
        List<userboard> board = mainService.getUserWithBoards();

        Map<String, List<userboard>> response = new HashMap<>();
        response.put("data", board);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetchData")
    public String fetchData(
            @RequestParam String sigunguCd,
            @RequestParam String bjdongCd,
            @RequestParam String bun,
            @RequestParam String ji,
            @RequestParam String useYm,
            @RequestParam(required = false) String numOfRows,
            @RequestParam(required = false) String pageNo){
        return sigunguCd;
    }
}
