package com.bnksys.esg.mapper;


import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.inQuiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RequestMapper {

    void saveApplyApi(@Param("userid") int userid, @Param("apiapplyDto") apiApplyDto apiapplyDto);

    void saveInquiry(@Param("userid") int userid, @Param("inquiryDto") inQuiryDto inquiryDto);
}
