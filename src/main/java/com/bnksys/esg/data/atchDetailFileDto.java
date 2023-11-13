package com.bnksys.esg.data;

public class atchDetailFileDto {
    private int atchdetailfileid;

    private int atchfileid;

    private String atchfilepath;

    private String atchfilename;

    private String atchfileext;

    private String atchfilesize;

    private String orgfilename;

    public String getAtchfilesize() {
        return atchfilesize;
    }

    public void setAtchfilesize(String atchfilesize) {
        this.atchfilesize = atchfilesize;
    }

    public int getAtchdetailfileid() {
        return atchdetailfileid;
    }

    public void setAtchdetailfileid(int atchdetailfileid) {
        this.atchdetailfileid = atchdetailfileid;
    }

    public int getAtchfileid() {
        return atchfileid;
    }

    public void setAtchfileid(int atchfileid) {
        this.atchfileid = atchfileid;
    }

    public String getAtchfilepath() {
        return atchfilepath;
    }

    public void setAtchfilepath(String atchfilepath) {
        this.atchfilepath = atchfilepath;
    }

    public String getAtchfilename() {
        return atchfilename;
    }

    public void setAtchfilename(String atchfilename) {
        this.atchfilename = atchfilename;
    }

    public String getAtchfileext() {
        return atchfileext;
    }

    public void setAtchfileext(String atchfileext) {
        this.atchfileext = atchfileext;
    }

    public String getOrgfilename() {
        return orgfilename;
    }

    public void setOrgfilename(String orgfilename) {
        this.orgfilename = orgfilename;
    }
}
