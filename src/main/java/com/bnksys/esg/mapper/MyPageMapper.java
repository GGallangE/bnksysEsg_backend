package com.bnksys.esg.mapper;


import com.bnksys.esg.data.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyPageMapper {

    List<apiResultDto> findIntrsApi(@Param("email")String email);

    List<apiResultDto> findRecentUseApi(@Param("email")String email);

    List<inQuiryDto> findInQuiry(@Param("email")String email);

    List<inQuiryDto> findAnswerInQuiry(@Param("email") String email, @Param("inquiryid") int inquiryid);

    List<apiApplyDto> findApiApply(@Param("email") String email);

    List<apiApplyDto> findDetailApiApply(@Param("email") String email, @Param("apiapplyid") int apiapplyid);

    List<batchListDto> findApiSchedule(@Param("userid") int userid, @Param("batchlistid") Integer batchlistid);

    List<alarmDto> findAlarm(@Param("userid") int userid, @Param("alarmid") Integer alarmid);

    int getNotReadAlarmCount(@Param("userid") int userid);

    void deleteApiSchedule(@Param("userid") int userid, @Param("batchlistid") int batchlistid);

    void updateApiScheduleTime(@Param("userid") int userid, @Param("batchlistDto") batchListDto batchlistDto);

    void update_readAlarm(@Param("userid") int userid, @Param("alarmid") int alarmid);

}
