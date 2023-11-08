package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.mapper.UseCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseCaseService {

    UseCaseMapper useCaseMapper;

    @Autowired
    public UseCaseService(UseCaseMapper useCaseMapper){
        this.useCaseMapper = useCaseMapper;
    }

    public List<getuseCaseDto> findUseCase_apiDetail(int apilistid){return useCaseMapper.findUseCase_apiDetail(apilistid); }

    public List<getuseCaseDto> findUseCase_usecaseDetail(int usecaseid){return useCaseMapper.findUseCase_usecaseDetail(usecaseid); }
}
