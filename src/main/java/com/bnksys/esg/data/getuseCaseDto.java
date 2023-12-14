package com.bnksys.esg.data;

public class getuseCaseDto {

    int usecaseid;

    String regdt;

    String title;

    String content;

    String username;

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsecaseid() {
        return usecaseid;
    }

    public void setUsecaseid(int usecaseid) {
        this.usecaseid = usecaseid;
    }

    public String getRegdt() {
        return regdt;
    }

    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
