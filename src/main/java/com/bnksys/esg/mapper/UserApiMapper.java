package com.bnksys.esg.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserApiMapper {

    int findbyemail(String email);

    void saveApiUses(int userId,int apilistid);
}
