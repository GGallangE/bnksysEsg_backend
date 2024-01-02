package com.bnksys.esg.data;

import java.util.List;
import lombok.Data;

import java.util.Map;

@Data
public class apiRequestDto {
    private int apilistid;
    private List<Map<String, String>> params;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public apiRequestDto() {
    }

    public apiRequestDto(int apilistid, List<Map<String, String>> params) {
        this.apilistid = apilistid;
        this.params = params;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public List<Map<String, String>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }
}
