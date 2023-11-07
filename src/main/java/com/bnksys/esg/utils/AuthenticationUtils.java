package com.bnksys.esg.utils;

import com.bnksys.esg.response.Response;
import org.springframework.security.core.Authentication;

public class AuthenticationUtils {
    public static boolean checkAuthentication(Authentication authentication, Response response) {
        if (authentication == null) {
            response.setSuccess(false);
            response.getMessages().add("토큰이 만료되었거나 없습니다. 다시 로그인을 진행해 주세요.");
            return false;
        }
        return true;
    }
}
