package com.bnksys.esg.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class apiurlAndkeyDto {

    private String apiurl;

    private String apikey;

    public String getApiUrl() {
        return apiurl;
    }

    public void setApiUrl(String apiurl) {
        this.apiurl = apiurl;
    }


    public String getApiKey() {
        return apikey;
    }

    public void setApiKey(String apikey) {
        this.apikey = apikey;
    }
}
