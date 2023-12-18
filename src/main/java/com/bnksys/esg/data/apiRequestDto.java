package com.bnksys.esg.data;

import lombok.Data;

import java.util.Map;

@Data
public class apiRequestDto {
    private int apilistid;
    private Map<String, String> params;


    public apiRequestDto() {
    }

    public apiRequestDto(int apilistid, Map<String, String> params) {
        this.apilistid = apilistid;
        this.params = params;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
