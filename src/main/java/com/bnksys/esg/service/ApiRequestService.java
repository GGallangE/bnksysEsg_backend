package com.bnksys.esg.service;

import com.bnksys.esg.data.apiurlAndkeyDto;
import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.data.requiredItemDto;
import com.bnksys.esg.mapper.ApiRequestMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApiRequestService {
    ApiRequestMapper apiRequestMapper;

    public ApiRequestService(ApiRequestMapper apiRequestMapper) {
        this.apiRequestMapper = apiRequestMapper;
    }

    public String postRequestApi(int apilistid, Map<String, String> params){
        return "Aa";
    }

    public String getRequestApi(int apilistid, Map<String, String> userParams) throws IOException {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);

        StringBuilder urlBuilder = new StringBuilder(apiInfo.getApiurl());
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(apiInfo.getApikey(), "UTF-8"));

        for (Map.Entry<String, String> entry : userParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            urlBuilder.append("&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return sb.toString();
    }

    public List<requiredItemDto> getRequired_Items(int apilistid){ return apiRequestMapper.getRequired_Items(apilistid); }

}
