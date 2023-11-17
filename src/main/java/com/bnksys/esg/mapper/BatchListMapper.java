package com.bnksys.esg.mapper;

import com.bnksys.esg.data.batchListDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BatchListMapper {
    List<batchListDto> getAllBatchList();
    // 기타 필요한 메서드 추가
}
