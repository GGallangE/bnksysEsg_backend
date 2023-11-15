package com.bnksys.esg.controller;

import com.bnksys.esg.data.userDto;
import com.bnksys.esg.jwt.JwtUtils;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.response.TokenResponse;
import com.bnksys.esg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/spring/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<Response> registerUser(@RequestBody userDto userdto) {
        Response response = new Response();
        try {
            if (userService.checkeduserDto(userdto, response)) {
                response.setSuccess(false);
                return ResponseEntity.badRequest().body(response);
            }
            if (userService.isNicknameDuplicate(userdto.getNickname())) {
                response.setSuccess(false);
                response.getMessages().add("nickname이 이미 존재합니다.");
                return ResponseEntity.badRequest().body(response);
            }
            if (userService.isEmailDuplicate(userdto.getEmail())) {
                response.setSuccess(false);
                response.getMessages().add("email이 이미 존재합니다.");
                return ResponseEntity.badRequest().body(response);
            }
            if (userService.isCheckedPassword(userdto.getPassword(), userdto.getPasswordCheck())){
                response.setSuccess(false);
                response.getMessages().add("비밀번호와 비밀번호(확인)이 서로 다릅니다.");
                return ResponseEntity.badRequest().body(response);
            }
            userService.saveUser(userdto);
            response.setSuccess(true);
            response.getMessages().add("회원가입 완료");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.getMessages().add("회원가입 실패");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody userDto userdto) {
        TokenResponse response = new TokenResponse();
        Map<String, String> data = new HashMap<>();

        if (userService.login(userdto.getEmail(), userdto.getPassword())) {
            String accessToken = jwtUtils.generateJwtToken(userdto.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(userdto.getEmail());

            data.put("accessToken", accessToken);
            data.put("refreshToken", refreshToken);

            response.setData(data);
            response.setSuccess(true);
        } else {
            response.setErrors(Arrays.asList("아이디와 패스워드가 일치하지 않습니다."));
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

}
