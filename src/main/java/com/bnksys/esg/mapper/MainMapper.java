package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.noticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainMapper {

    List<apiResultDto> getApiList(@Param("name") String name, @Param("sortBy") String sortBy, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiResultDto> getApiList_auth(@Param("name") String name, @Param("sortBy") String sortBy, @Param("userid") int userid, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<apiResultDto> getapiList_Top5(@Param("sort") String sort);

    int findbyemail(String email);

    int isSameUser(String email);

    String findbyuserid(int userid);

    List<noticeDto> getNoticeList(String mainsort);

    List<noticeDto> getNoticeDetail(@Param("noticeid") int noticeid);

    List<atchDetailFileDto> getNoticeDetail_Atchfile(@Param("atchfileid") int atchfileid);
}
