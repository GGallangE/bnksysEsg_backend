package com.bnksys.esg.utils;

import com.bnksys.esg.response.ApiListResponse;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthenticationUtils_ApiResponse {
    public static ApiListResponse checkAuthentication(Authentication authentication, ApiListResponse response) {
        if (authentication == null) {
            response.getMessages().add("토큰이 만료되었거나 없습니다. 다시 로그인을 진행해 주세요.");
        }else{
            response.setSuccess(true);
        }

        return response;
    }
}
