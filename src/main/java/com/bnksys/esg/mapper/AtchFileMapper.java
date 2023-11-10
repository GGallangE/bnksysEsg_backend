package com.bnksys.esg.mapper;

import com.bnksys.esg.data.atchDetailFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AtchFileMapper {

    void saveAtchDetailFile(@Param("atchDetail") atchDetailFileDto atchDetailFiledto);

    int maxAtchFileId();

    void saveAtchFile(@Param("atchfileid") int atchfileid);

    atchDetailFileDto findAtchDetailFile(@Param("atchDetailFileId") int atchDetailFileId);
}
