package com.bnksys.esg.controller;


import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.service.ConnectionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<Map<String, List<apiResult>>> getResultByapilistId(
            @PathVariable int apilistid, HttpServletRequest request, HttpServletResponse response){

        if (!hasViewedToday(request, apilistid)) {
            increseViewCount(apilistid);
            setViewedTodayCookie(response, apilistid);
        }


        List<apiResult> result = connectionService.getResultByapilistId(apilistid);

        Map<String, List<apiResult>> response_api = new HashMap<>();
        response_api.put("data", result);
        return ResponseEntity.ok(response_api);
    }

    private boolean hasViewedToday(HttpServletRequest request, int apilistid) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("apiViewed_" + apilistid)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void increseViewCount(int apilistid) {
        connectionService.increseViewCount(apilistid);
    }

    private void setViewedTodayCookie(HttpServletResponse response, int apilistid) {

        Cookie cookie = new Cookie("apiViewed_" + apilistid, "true");
//        cookie.setMaxAge(86400);
        cookie.setMaxAge(60);
        response.addCookie(cookie);
    }


}
