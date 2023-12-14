package com.bnksys.esg.data;

public class apiApplyDto {
    private int apiapplyid;

    private int userid;

    private String applynm;

    private String applycntn;

    private String applydate;

    private String rplydate;

    private String applydvcd;

    private String username;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getApiapplyid() {
        return apiapplyid;
    }

    public void setApiapplyid(int apiapplyid) {
        this.apiapplyid = apiapplyid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getApplynm() {
        return applynm;
    }

    public void setApplynm(String applynm) {
        this.applynm = applynm;
    }

    public String getApplycntn() {
        return applycntn;
    }

    public void setApplycntn(String applycntn) {
        this.applycntn = applycntn;
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
    }

    public String getRplydate() {
        return rplydate;
    }

    public void setRplydate(String rplydate) {
        this.rplydate = rplydate;
    }

    public String getApplydvcd() {
        return applydvcd;
    }

    public void setApplydvcd(String applydvcd) {
        this.applydvcd = applydvcd;
    }
}
