package com.bnksys.esg.data;

import lombok.Data;

@Data
public class apiNeedResponseDto {

    private int apirsqeitemsid;

    private int apilistid;

    private String apinm;

    private String krnm;

    private String ennm;

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getApinm() {
        return apinm;
    }

    public void setApinm(String apinm) {
        this.apinm = apinm;
    }

    public int getApirsqeitemsid() {
        return apirsqeitemsid;
    }

    public void setApirsqeitemsid(int apirsqeitemsid) {
        this.apirsqeitemsid = apirsqeitemsid;
    }

    public int getApilistid() {
        return apilistid;
    }

    public void setApilistid(int apilistid) {
        this.apilistid = apilistid;
    }

    public String getKrnm() {
        return krnm;
    }

    public void setKrnm(String krnm) {
        this.krnm = krnm;
    }

    public String getEnnm() {
        return ennm;
    }

    public void setEnnm(String ennm) {
        this.ennm = ennm;
    }
}
