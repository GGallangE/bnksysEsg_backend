package com.bnksys.esg.mapper;

import com.bnksys.esg.data.batchListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SchNtfMapper {

    int MaxbatchlistId();

    String getApiName(@Param("apilistid") int apilistid);

    void save_BatchSchedule(@Param("userid") int userid, @Param("batchlistDto") batchListDto batchlistDto);

    void save_Alarm(@Param("title") String title, @Param("content") String content, @Param("senduser") int senduser, @Param("rcvuser") int rcvuser);
}
