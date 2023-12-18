package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.inQuiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    int findAnswerCount(@Param("parentid") int parentid);

    int maxApiListId();

    List<apiApplyDto> findApi_ApplyLIST(@Param("apiapplyid") Integer apiapplyid , @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiApplyDto> findApi_ApplyList_ByName(@Param("applynm") String applynm);

    List<apiResultDto> findApiList(@Param("apilistid") Integer apilistid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findinQuiry(@Param("inquiryid") Integer inquiryid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findinQuiryAnswer(@Param("inquiryid") int inquiryid);

    void updateApi_ApplyStatus(@Param("apiapplyDto") apiApplyDto apiapplyDto);

    void updateinquiry_Answer(@Param("userid") int userid, @Param("inquiryDto")inQuiryDto inquiryDto);

    void saveNotice(@Param("noticenm") String noticenm, @Param("noticecntn") String noticecntn, @Param("atchfileid") int atchfileid);

    void saveApiList(@Param("apiresultDto") apiResultDto apiresultDto);

    void updateApiList(@Param("apiresultDto") apiResultDto apiresultDto);

    void saveinquiry_Answer(@Param("userid") int userid, @Param("inquiryDto")inQuiryDto inquiryDto);
}
