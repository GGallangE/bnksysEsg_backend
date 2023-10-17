package com.bnksys.esg.mapper;

import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainMapper {
    List<Member> getAllUser();

    List<userboard> getUserWithBoards();
}
