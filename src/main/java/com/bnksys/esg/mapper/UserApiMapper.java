package com.bnksys.esg.mapper;


import com.bnksys.esg.data.IntrsApiDto;
import com.bnksys.esg.data.apiResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserApiMapper {

    int findbyemail(String email);

    void saveApiUses(int userId, int apilistid);

    int countIntrsApi(int userId, int apilistid);

    void saveIntrsApi(int userId, IntrsApiDto intrsApiDto);

    void updateIntrsApi(int userId, IntrsApiDto intrsApiDto);

    List<apiResult> findIntrsApi(String email);
}