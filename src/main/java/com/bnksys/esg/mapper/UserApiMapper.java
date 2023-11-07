package com.bnksys.esg.mapper;


import com.bnksys.esg.data.IntrsApiDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserApiMapper {

    int findbyemail(String email);

    void saveApiUses(int userId,int apilistid);

    int countIntrsApi(int userId, int apilistid);

    void saveIntrsApi(int userId, IntrsApiDto intrsApiDto);

    void updateIntrsApi(int userId, IntrsApiDto intrsApiDto);
}
