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

    List<apiResultDto> getApiList(@Param("name") String name, @Param("sortBy") String sortBy);

    List<apiResultDto> getapiList_Top5(@Param("sort") String sort);

    int findbyemail(String email);

    List<noticeDto> getNoticeList();

    List<noticeDto> getNoticeDetail(@Param("noticeid") int noticeid);

    List<atchDetailFileDto> getNoticeDetail_Atchfile(@Param("atchfileid") int atchfileid);
}
