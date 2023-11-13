package com.bnksys.esg.data;

import java.util.List;

public class noticeDto {

    private int noticeid;

    private String noticenm;

    private String noticecntn;

    private String regdt;

    private int atchfileid;

    private int hit;

    public int getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(int noticeid) {
        this.noticeid = noticeid;
    }

    public String getNoticenm() {
        return noticenm;
    }

    public void setNoticenm(String noticenm) {
        this.noticenm = noticenm;
    }

    public String getNoticecntn() {
        return noticecntn;
    }

    public void setNoticecntn(String noticecntn) {
        this.noticecntn = noticecntn;
    }

    public String getRegdt() {
        return regdt;
    }

    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }

    public int getAtchfileid() {
        return atchfileid;
    }

    public void setAtchfileid(int atchfileid) {
        this.atchfileid = atchfileid;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
