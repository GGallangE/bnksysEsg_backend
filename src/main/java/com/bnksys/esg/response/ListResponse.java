package com.bnksys.esg.response;

import com.bnksys.esg.data.apiResultDto;

import java.util.List;
import java.util.Map;

public class ListResponse<T> {
    private Map<String, List<T>> data;
    private boolean success;
    private List<String> messages;

    public ListResponse(Map<String, List<T>> data, boolean success, List<String> messages) {
        this.data = data;
        this.success = success;
        this.messages = messages;
    }

    public Map<String, List<T>> getData() {
        return data;
    }

    public void setData(Map<String, List<T>> data) {
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
