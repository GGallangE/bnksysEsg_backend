package com.bnksys.esg.mapper;

import com.bnksys.esg.data.apiNeedRequestDto;
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

    String find_apiformat(@Param("apilistid") int apilistid);

    List<batchDetailArgsDto> find_batchdetaillist(@Param("batchlistid") int batchlistid);

    List<apiNeedRequestDto> find_requesttlist(@Param("apilistid") int apilistid);

}
