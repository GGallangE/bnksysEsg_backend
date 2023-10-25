package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResult;
import com.bnksys.esg.mapper.ConnectionMapper;
import com.bnksys.esg.mapper.MainMapper;
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

    public List<apiResult> getResultByapilistId(int apilistid) {
        return connectionMapper.getResultByapilistId(apilistid);
    }

    public void increseViewCount(int apilistid){
        connectionMapper.increseViewCount(apilistid);
    }


}
