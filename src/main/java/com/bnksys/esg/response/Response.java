package com.bnksys.esg.response;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private boolean success;
    private List<String> messages;

    public Response() {
        this.messages = new ArrayList<>();
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
