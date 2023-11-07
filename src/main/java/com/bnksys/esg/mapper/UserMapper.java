package com.bnksys.esg.mapper;

import com.bnksys.esg.data.userDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void saveUser(userDto userdto);

    int countUsernameDuplicate(String username);

    int countNicknameDuplicate(String nickname);

    int countEmailDuplicate(String email);

    userDto findByEmail(String email);

}
