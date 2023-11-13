package com.bnksys.esg.service;

import com.bnksys.esg.data.userDto;
import com.bnksys.esg.mapper.UserMapper;
import com.bnksys.esg.config.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MemberDetailsService implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public MemberDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        userDto user = userMapper.findByEmail(email);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if ("ROLE_ADMIN".equals(user.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println(authorities);
        return new MemberDetails(user.getEmail(), user.getPassword(), authorities);
    }
}