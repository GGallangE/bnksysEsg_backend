package com.bnksys.esg.data;

public class alarmDto {
    private int alarmid;
    private String title;
    private String content;
    private String regdt;
    private String readdt;
    private int senduser;
    private int rcvuser;

    public int getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(int alarmid) {
        this.alarmid = alarmid;
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

    public String getRegdt() {
        return regdt;
    }

    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }

    public String getReaddt() {
        return readdt;
    }

    public void setReaddt(String readdt) {
        this.readdt = readdt;
    }

    public int getSenduser() {
        return senduser;
    }

    public void setSenduser(int senduser) {
        this.senduser = senduser;
    }

    public int getRcvuser() {
        return rcvuser;
    }

    public void setRcvuser(int rcvuser) {
        this.rcvuser = rcvuser;
    }
}
