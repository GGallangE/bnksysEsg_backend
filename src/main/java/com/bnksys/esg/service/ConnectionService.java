package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.mapper.ConnectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    ConnectionMapper connectionMapper;

    @Autowired
    public ConnectionService(ConnectionMapper connectionMapper){
        this.connectionMapper = connectionMapper;
    }

    public List<apiResultDto> getResultByapilistId(int apilistid) {
        return connectionMapper.getResultByapilistId(apilistid);
    }

    public void increseViewCount(int apilistid){
        connectionMapper.increseViewCount(apilistid);
    }


}
