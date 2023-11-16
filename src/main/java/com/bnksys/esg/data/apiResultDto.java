package com.bnksys.esg.data;

import lombok.Data;

@Data
public class apiResultDto {
    private int apilistid;
    private String apinm;
    private String prvorg;
    private int apiview;
    private String apirgdt;
    private String apiexpl;
    private int apiapplyid;
    private String apiapplynm;
    private int countapiuses;
    private int favorite;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getApiapplynm() {
        return apiapplynm;
    }

    public void setApiapplynm(String apiapplynm) {
        this.apiapplynm = apiapplynm;
    }

    public String getApiexpl() {
        return apiexpl;
    }

    public void setApiexpl(String apiexpl) {
        this.apiexpl = apiexpl;
    }

    public int getCountapiuses() {
        return countapiuses;
    }

    public void setCountapiuses(int countapiuses) {
        this.countapiuses = countapiuses;
    }

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

    public String getApirgdt() {
        return apirgdt;
    }

    public void setApirgdt(String apirgdt) {
        this.apirgdt = apirgdt;
    }

    public int getApiapplyid() {
        return apiapplyid;
    }

    public void setApiapplyid(int apiapplyid) {
        this.apiapplyid = apiapplyid;
    }
}
