package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiurlAndkeyDto;
import com.bnksys.esg.data.requiredItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ApiRequestMapper {

    List<requiredItemDto> getRequired_Items(@Param("apilistid") int apilistid);

    apiurlAndkeyDto findurlAndkey(@Param("apilistid") int apilistid);

    List<Map<String, String>> findKor_Eng(@Param("apilistid") int apilistid);

    String findMethod_Type(@Param("apilistid") int apilistid);
}
