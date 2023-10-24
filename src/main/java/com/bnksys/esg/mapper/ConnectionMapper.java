package com.bnksys.esg.mapper;


import com.bnksys.esg.data.apiResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConnectionMapper {

    List<apiResult> getResultByapilistId(@Param("apilistid") int apilistid);
}
