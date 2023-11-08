package com.bnksys.esg.data;

public class useCaseDto {
    private String title;

    private String content;

    private int[] apilistid;

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

    public int[] getApilistid() {
        return apilistid;
    }

    public void setApilistid(int[] apilistid) {
        this.apilistid = apilistid;
    }
}
