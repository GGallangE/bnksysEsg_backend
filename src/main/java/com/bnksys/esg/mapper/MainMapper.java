package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainMapper {

    List<apiResultDto> getApiList(@Param("name") String name, @Param("sortBy") String sortBy);
}
