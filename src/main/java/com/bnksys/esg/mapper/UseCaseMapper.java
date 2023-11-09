package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UseCaseMapper {

    List<getuseCaseDto> findUseCase_apiDetail(@Param("apilistid") int apilistid);

    List<getuseCaseDto> findUseCase_usecaseDetail(@Param("usecaseid") int usecaseid);

    List<apiResultDto> findUseCase_usecaseDetail_apiList(@Param("usecaseid") int usecaseid);

    List<getuseCaseDto> findUseCase_usecaseMain(@Param("searchname") String searchname);
}
