package com.bnksys.esg.data;

import lombok.Data;

@Data
public class comCodeDto {
    private int id;

    private String code;

    private String codelabel;

    private String codevalue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodelabel() {
        return codelabel;
    }

    public void setCodelabel(String codelabel) {
        this.codelabel = codelabel;
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }
}
