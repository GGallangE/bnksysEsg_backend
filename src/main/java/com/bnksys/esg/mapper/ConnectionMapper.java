package com.bnksys.esg.mapper;


import com.bnksys.esg.data.apiResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConnectionMapper {

    List<apiResultDto> getResultByapilistId(@Param("email") String email, @Param("apilistid") int apilistid);

    void increseViewCount(@Param("apilistid") int apilistid);
}
