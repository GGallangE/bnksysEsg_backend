package com.bnksys.esg.data;

import lombok.Data;

@Data
public class apiResult {
    private int apilistid;
    private String apinm;
    private String prvorg;
    private int apiview;
    private char apirgdt;
    private int apiapplyid;

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public String getApinm() {
        return apinm;
    }

    public void setApinm(String apinm) {
        this.apinm = apinm;
    }

    public String getPrvorg() {
        return prvorg;
    }

    public void setPrvorg(String prvorg) {
        this.prvorg = prvorg;
    }

    public int getApiview() {
        return apiview;
    }

    public void setApiview(int apiview) {
        this.apiview = apiview;
    }

    public char getApirgdt() {
        return apirgdt;
    }

    public void setApirgdt(char apirgdt) {
        this.apirgdt = apirgdt;
    }

    public int getApiapplyid() {
        return apiapplyid;
    }

    public void setApiapplyid(int apiapplyid) {
        this.apiapplyid = apiapplyid;
    }
}
