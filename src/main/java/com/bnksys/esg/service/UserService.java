package com.bnksys.esg.service;

import com.bnksys.esg.data.userDto;
import com.bnksys.esg.jwt.JwtUtils;
import com.bnksys.esg.mapper.UserMapper;
import com.bnksys.esg.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(userDto userdto){
        String encodedPassword = passwordEncoder.encode(userdto.getPassword());
        userdto.setPassword(encodedPassword);
        userMapper.saveUser(userdto);
    }

    public boolean checkeduserDto(userDto userdto, Response response){
        boolean hasErrors = false;

        if (userdto.getUsername() == null || userdto.getUsername().isEmpty()) {
            response.getMessages().add("이름은 필수 항목입니다.");
            hasErrors = true;
        }

        if (userdto.getNickname() == null || userdto.getNickname().isEmpty()) {
            response.getMessages().add("닉네임은 필수 항목입니다.");
            hasErrors = true;
        }

        if (userdto.getEmail() == null || userdto.getEmail().isEmpty()) {
            response.getMessages().add("이메일은 필수 항목입니다.");
            hasErrors = true;
        }

        if (userdto.getPassword() == null || userdto.getPassword().isEmpty()) {
            response.getMessages().add("비밀번호는 필수 항목입니다.");
            hasErrors = true;
        }

        if (userdto.getPasswordCheck() == null || userdto.getPasswordCheck().isEmpty()) {
            response.getMessages().add("비밀번호(확인)은 필수 항목입니다.");
            hasErrors = true;
        }

        return hasErrors;
    }
    public boolean isUsernameDuplicate(String username) {
        return userMapper.countUsernameDuplicate(username) > 0;
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userMapper.countNicknameDuplicate(nickname) > 0;
    }

    public boolean isEmailDuplicate(String email) {
        return userMapper.countEmailDuplicate(email) > 0;
    }

    public boolean isCheckedPassword(String password, String passwordCheck){
        return !password.equals(passwordCheck);
    }

    public boolean login(String email, String password){
        if(null == email || null == password){
            return false;
        }

        userDto user = userMapper.findByEmail(email);
        if (user != null && passwordEncoder.matches(password,user.getPassword())){
            return true;
        }else{
            return false;
        }
    }


}
