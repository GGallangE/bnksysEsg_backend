package com.bnksys.esg.mapper;

import com.bnksys.esg.data.batchDetailArgsDto;
import com.bnksys.esg.data.batchListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BatchListMapper {
    List<batchListDto> getAllBatchList();

    List<batchDetailArgsDto> findAll_ApiArgs(@Param("batchlistid") int batchlistid);

}
