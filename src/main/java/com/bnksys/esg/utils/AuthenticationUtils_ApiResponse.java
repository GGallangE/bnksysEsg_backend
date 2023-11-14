package com.bnksys.esg.utils;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.response.ListResponse;
import org.springframework.security.core.Authentication;

public class AuthenticationUtils_ApiResponse {
    public static<T> ListResponse<T> checkAuthentication(Authentication authentication, ListResponse<T> response) {
        if (authentication == null) {
            response.getMessages().add("토큰이 만료되었거나 없습니다. 다시 로그인을 진행해 주세요.");
        }else{
            response.setSuccess(true);
        }

        return response;
    }
}
