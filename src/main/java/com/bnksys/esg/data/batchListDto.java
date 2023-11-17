package com.bnksys.esg.data;

import java.util.List;

public class batchListDto {
    private int batchlistid;
    private int userid;
    private int apilistid;
    private String batchtime;
    private String frequency;
    private String time;
    private String dayofweek;
    private String dayofmonth;
    private String stcd;
    private String apinm;

    private List<batchDetailArgsDto> batchDetailargsDto;

    public List<batchDetailArgsDto> getBatchDetailargsDto() {
        return batchDetailargsDto;
    }

    public void setBatchDetailargsDto(List<batchDetailArgsDto> batchDetailargsDto) {
        this.batchDetailargsDto = batchDetailargsDto;
    }

    public String getApinm() {
        return apinm;
    }

    public void setApinm(String apinm) {
        this.apinm = apinm;
    }

    public int getBatchlistid() {
        return batchlistid;
    }

    public void setBatchlistid(int batchlistid) {
        this.batchlistid = batchlistid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public String getBatchtime() {
        return batchtime;
    }

    public void setBatchtime(String batchtime) {
        this.batchtime = batchtime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getDayofmonth() {
        return dayofmonth;
    }

    public void setDayofmonth(String dayofmonth) {
        this.dayofmonth = dayofmonth;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }
}
