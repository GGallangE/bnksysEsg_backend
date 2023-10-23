package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainMapper {
    List<Member> getAllUser();

    List<userboard> getUserWithBoards();

    List<apiResult> getApiList(@Param("name") String name);

//    List<apiResult> getApiList();
}
