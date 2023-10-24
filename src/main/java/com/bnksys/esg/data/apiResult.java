package com.bnksys.esg.data;

import lombok.Data;

@Data
public class apiResult {
    private int apilistid;
    private String apiname;
    private String prvorg;
    private int view;
    private int nbruses;

    public String getPrvorg() {
        return prvorg;
    }

    public void setPrvorg(String prvorg) {
        this.prvorg = prvorg;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getNbruses() {
        return nbruses;
    }

    public void setNbruses(int nbruses) {
        this.nbruses = nbruses;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }
}
