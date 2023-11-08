package com.bnksys.esg.response;

import com.bnksys.esg.data.apiResult;

import java.util.List;
import java.util.Map;

public class ApiListResponse {
    private Map<String, List<apiResult>> data;
    private boolean success;
    private List<String> messages;

    public ApiListResponse(Map<String, List<apiResult>> data, boolean success, List<String> messages) {
        this.data = data;
        this.success = success;
        this.messages = messages;
    }

    public Map<String, List<apiResult>> getData() {
        return data;
    }

    public void setData(Map<String, List<apiResult>> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
