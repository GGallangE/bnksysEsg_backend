package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.inQuiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    List<apiApplyDto> findApi_ApplyLIST();

    void updateApi_ApplyStatus(@Param("apiapplyDto") apiApplyDto apiapplyDto);

    void saveinquiry_Answer(@Param("userid") int userid, @Param("inquiryDto")inQuiryDto inquiryDto);

    void saveNotice(@Param("noticenm") String noticenm, @Param("noticecntn") String noticecntn, @Param("atchfileid") int atchfileid);

}
