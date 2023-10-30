package com.bnksys.esg.response;

import java.util.List;
import java.util.Map;

public class TokenResponse {
    private Map<String, String> data;
    private List<String> errors;

    public TokenResponse() {
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}


