package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiNeedRequestDto;
import com.bnksys.esg.data.apiNeedResponseDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.apikeyDto;
import com.bnksys.esg.data.comCodeDto;
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

    List<apiResultDto> findApiList_Search(@Param("string") String string, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findinQuiry(@Param("inquiryid") Integer inquiryid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<inQuiryDto> findinQuiryAnswer(@Param("inquiryid") int inquiryid);

    List<apikeyDto> findApiKey(@Param("apikeyid") Integer apikeyid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apikeyDto> findApiKey_Search(@Param("name") String apikeyid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<comCodeDto> findComCode(@Param("id") Integer id, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<comCodeDto> findComCode_Search(@Param("codevalue") String codevalue, @Param("code") String code);

    List<comCodeDto> findAdmin_ComCode_Search(@Param("code") String code, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiNeedRequestDto> findNeed_Request(@Param("apirqrditemsid") Integer apirqrditemsid, @Param("apinm") String apinm, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiNeedResponseDto> findNeed_Response(@Param("apirsqeitemsid") Integer apirsqeitemsid, @Param("apinm") String apinm, @Param("offset") int offset, @Param("pageSize") int pageSize);

    void saveNotice(@Param("userid") int userid, @Param("noticenm") String noticenm, @Param("noticecntn") String noticecntn, @Param("atchfileid") int atchfileid);

    void saveApiList(@Param("apiresultDto") apiResultDto apiresultDto, @Param("userid") int userid);

    void saveinquiry_Answer(@Param("userid") int userid, @Param("inquiryDto")inQuiryDto inquiryDto);

    void saveapikey(@Param("apikeydto")apikeyDto apikeydto, @Param("userid") int userid);

    void save_comcode(@Param("comcodeDto") comCodeDto comcodeDto, @Param("userid") int userid);

    void save_needrequest(@Param("apineedRequestDto") apiNeedRequestDto apineedRequestDto, @Param("userid") int userid);

    void save_needresponse(@Param("apineedResponseDto") apiNeedResponseDto apineedResponseDto, @Param("userid") int userid);

    void updateApi_ApplyStatus(@Param("apiapplyDto") apiApplyDto apiapplyDto);

    void updateinquiry_Answer(@Param("userid") int userid, @Param("inquiryDto")inQuiryDto inquiryDto);

    void updateApiList(@Param("apiresultDto") apiResultDto apiresultDto, @Param("userid") int userid);

    void updateapikey(@Param("apikeydto")apikeyDto apikeydto, @Param("userid") int userid);

    void update_comcode(@Param("comcodeDto") comCodeDto comcodeDto, @Param("userid") int userid);

    void update_needrequest(@Param("apineedRequestDto") apiNeedRequestDto apineedRequestDto, @Param("userid") int userid);

    void update_needresponse(@Param("apineedResponseDto") apiNeedResponseDto apineedResponseDto, @Param("userid") int userid);

}
