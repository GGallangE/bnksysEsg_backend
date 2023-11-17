package com.bnksys.esg.data;

public class alarmDto {
    private int alarmid;
    private String title;
    private String content;
    private String regdt;
    private boolean readCheck;
    private int senduser;
    private int rcvuser;
    private String sendusername;
    private String rcvusername;

    public String getSendusername() {
        return sendusername;
    }

    public void setSendusername(String sendusername) {
        this.sendusername = sendusername;
    }

    public String getRcvusername() {
        return rcvusername;
    }

    public void setRcvusername(String rcvusername) {
        this.rcvusername = rcvusername;
    }

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

    public boolean isReadCheck() {
        return readCheck;
    }

    public void setReadCheck(boolean readCheck) {
        this.readCheck = readCheck;
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
