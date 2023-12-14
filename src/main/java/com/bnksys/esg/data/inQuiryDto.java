package com.bnksys.esg.data;

public class inQuiryDto {
    private int inquiryid;

    private String inquirynm;

    private String inquirycntn;

    private String regdt;

    private int parentid;

    private int userid;

    private String username;

    private int replycount;

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReplycount() {
        return replycount;
    }

    public void setReplycount(int replycount) {
        this.replycount = replycount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(int inquiryid) {
        this.inquiryid = inquiryid;
    }

    public String getInquirynm() {
        return inquirynm;
    }

    public void setInquirynm(String inquirynm) {
        this.inquirynm = inquirynm;
    }

    public String getInquirycntn() {
        return inquirycntn;
    }

    public void setInquirycntn(String inquirycntn) {
        this.inquirycntn = inquirycntn;
    }

    public String getRegdt() {
        return regdt;
    }

    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
