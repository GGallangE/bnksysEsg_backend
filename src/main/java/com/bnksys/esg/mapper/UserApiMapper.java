package com.bnksys.esg.mapper;


import com.bnksys.esg.data.IntrsApiDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.useCaseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

}