package com.bnksys.esg.service;

import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.requiredItemDto;
import com.bnksys.esg.mapper.ApiRequestMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApiRequestService {
    ApiRequestMapper apiRequestMapper;

    public ApiRequestService(ApiRequestMapper apiRequestMapper) {
        this.apiRequestMapper = apiRequestMapper;
    }

    public String postRequestApi(int apilistid, Map<String, String> params){
        return "Aa";
    }

    public String getRequestApi(int apilistid, Map<String, String> params){
        return "Aa";
    }

    public List<requiredItemDto> getRequired_Items(int apilistid){ return apiRequestMapper.getRequired_Items(apilistid); }

}
