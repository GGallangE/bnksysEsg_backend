package com.bnksys.esg.service;

import com.bnksys.esg.data.batchListDto;
import com.bnksys.esg.mapper.BatchListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchListService {

    @Autowired
    private BatchListMapper batchListMapper;

    public List<batchListDto> getAllBatchList() {
        return batchListMapper.getAllBatchList();
    }

}
