package com.bnksys.esg.jwt;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtils.resolveToken(request);
        System.out.println(token);
        if (token != null && jwtUtils.validateJwtToken(token)) {
            Authentication authentication = jwtUtils.getAuthentication(token);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                String refreshToken = jwtUtils.resolveRefreshToken(request);
                if (refreshToken != null && jwtUtils.validateRefreshToken(refreshToken)) {
                    String username = jwtUtils.getUserNameFromRefreshToken(refreshToken);
                    String newAccessToken = jwtUtils.generateJwtToken(username);
                    // 클라이언트에게 새로운 access 토큰 전달
                    response.setHeader("New-Access-Token", newAccessToken);
                }
            }
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
