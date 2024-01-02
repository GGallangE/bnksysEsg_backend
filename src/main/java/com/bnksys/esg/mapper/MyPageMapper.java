package com.bnksys.esg.mapper;


import com.bnksys.esg.data.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyPageMapper {

    List<apiResultDto> findIntrsApi(@Param("email")String email, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiResultDto> findRecentUseApi(@Param("email")String email, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findInQuiry(@Param("email")String email, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findAnswerInQuiry(@Param("email") String email, @Param("inquiryid") int inquiryid);

    List<apiApplyDto> findApiApply(@Param("email") String email, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiApplyDto> findDetailApiApply(@Param("email") String email, @Param("apiapplyid") int apiapplyid);

    List<batchListDto> findApiSchedule(@Param("userid") int userid, @Param("batchlistid") Integer batchlistid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<alarmDto> findAlarm(@Param("userid") int userid, @Param("alarmid") Integer alarmid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<batchDetailArgsDto> findDetailList(@Param("batchlistid") Integer batchlistid);

    int isSameApiScheduleUser(@Param("email") String email,@Param("batchlistid") int batchlistid);

    int getNotReadAlarmCount(@Param("userid") int userid);

    void deleteApiSchedule(@Param("userid") int userid, @Param("batchlistid") int batchlistid);

    void save_BatchDetail(@Param("detail") batchDetailArgsDto detail, @Param("userid") int userid);

    void updateApiScheduleTime(@Param("userid") int userid, @Param("batchlistDto") batchListDto batchlistDto);

    void update_readAlarm(@Param("userid") int userid, @Param("alarmid") int alarmid);

    void update_BatchDetail(@Param("detail") batchDetailArgsDto detail, @Param("userid") int userid);
}
