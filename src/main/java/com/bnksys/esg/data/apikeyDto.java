package com.bnksys.esg.data;

import lombok.Data;

@Data
public class apikeyDto {

    private int apikeyid;

    private String sitedvcd;

    private String apikey;

    private String strdt;

    private String edt;

    public int getApikeyid() {
        return apikeyid;
    }

    public void setApikeyid(int apikeyid) {
        this.apikeyid = apikeyid;
    }

    public String getSitedvcd() {
        return sitedvcd;
    }

    public void setSitedvcd(String sitedvcd) {
        this.sitedvcd = sitedvcd;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getStrdt() {
        return strdt;
    }

    public void setStrdt(String strdt) {
        this.strdt = strdt;
    }

    public String getEdt() {
        return edt;
    }

    public void setEdt(String edt) {
        this.edt = edt;
    }
}
