package com.bnksys.esg.controller;

import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import com.bnksys.esg.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<userboard> getUserWithBoards(){
        List<userboard> board = mainService.getUserWithBoards();
        return  board;
    }
}
