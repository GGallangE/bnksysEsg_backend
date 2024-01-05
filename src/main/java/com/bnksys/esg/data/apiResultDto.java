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
    private String apikeyword;
    private String dataformat;
    private String sitedvcd;
    private String sitenm;
    private String usedvcd;
    private String methodtype;
    private String apiurl;
    private int apiapplyid;
    private String apiapplynm;
    private int countapiuses;
    private int favorite;
    private int total;

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getSitenm() {
        return sitenm;
    }

    public void setSitenm(String sitenm) {
        this.sitenm = sitenm;
    }

    public String getMethodtype() {
        return methodtype;
    }

    public void setMethodtype(String methodtype) {
        this.methodtype = methodtype;
    }

    public String getSitedvcd() {
        return sitedvcd;
    }

    public void setSitedvcd(String sitedvcd) {
        this.sitedvcd = sitedvcd;
    }

    public String getUsedvcd() {
        return usedvcd;
    }

    public void setUsedvcd(String usedvcd) {
        this.usedvcd = usedvcd;
    }

    public String getApikeyword() {
        return apikeyword;
    }

    public void setApikeyword(String apikeyword) {
        this.apikeyword = apikeyword;
    }

    public String getDataformat() {
        return dataformat;
    }

    public void setDataformat(String dataformat) {
        this.dataformat = dataformat;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
