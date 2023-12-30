package com.bnksys.esg.data;

public class requiredItemDto {
    private int apirqrditemsid;
    private int apilistid;
    private String rqrditemnm;
    private String rqrdrqstnm;
    private String itemexpl;
    private int sort;

    public int getApirqrditemsid() {
        return apirqrditemsid;
    }

    public void setApirqrditemsid(int apirqrditemsid) {
        this.apirqrditemsid = apirqrditemsid;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public String getRqrditemnm() {
        return rqrditemnm;
    }

    public void setRqrditemnm(String rqrditemnm) {
        this.rqrditemnm = rqrditemnm;
    }

    public String getRqrdrqstnm() {
        return rqrdrqstnm;
    }

    public void setRqrdrqstnm(String rqrdrqstnm) {
        this.rqrdrqstnm = rqrdrqstnm;
    }

    public String getItemexpl() {
        return itemexpl;
    }

    public void setItemexpl(String itemexpl) {
        this.itemexpl = itemexpl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
