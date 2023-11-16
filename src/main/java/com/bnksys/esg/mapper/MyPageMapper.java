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

    List<batchListDto> findApiSchedule(@Param("userid") int userid);

}
