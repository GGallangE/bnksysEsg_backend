package com.bnksys.esg.service;

import com.bnksys.esg.data.apiurlAndkeyDto;
import com.bnksys.esg.data.requiredItemDto;
import com.bnksys.esg.mapper.ApiRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringJoiner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiRequestService {
    ApiRequestMapper apiRequestMapper;

    public ApiRequestService(ApiRequestMapper apiRequestMapper) {
        this.apiRequestMapper = apiRequestMapper;
    }

    public String getRequestApi(int apilistid, List<Map<String, String>> paramsList) throws IOException {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);
        String baseUrl = apiInfo.getApiUrl();

        StringJoiner combinedResponse = new StringJoiner(", ", "[", "]");

        for (Map<String, String> userParams : paramsList) {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(apiInfo.getApiKey(), "UTF-8"));

            for (Map.Entry<String, String> entry : userParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                urlBuilder.append("&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

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

            List<Map<String, String>> mappingList = apiRequestMapper.findKor_Eng(apilistid);

            if (mappingList.isEmpty()) {
                combinedResponse.add(sb.toString());
            } else {
                String koreanResponse = convertEnglishToKorean(sb.toString(), mappingList);
                combinedResponse.add(koreanResponse);
            }
        }

        return combinedResponse.toString();
    }

    public String postRequestApi(int apilistid, List<Map<String, String>> paramsList) throws Exception {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);
        String url = apiInfo.getApiUrl() + "?serviceKey=" + URLEncoder.encode(apiInfo.getApiKey(), "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        StringJoiner combinedResponse = new StringJoiner(", ", "[", "]");

        for (Map<String, String> userParams : paramsList) {
            ObjectNode jsonNode = objectMapper.createObjectNode();

            for (Map.Entry<String, String> entry : userParams.entrySet()) {
                ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.add(entry.getValue());
                jsonNode.set(entry.getKey(), arrayNode);
            }

            String jsonBody = objectMapper.writeValueAsString(jsonNode);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(new URI(url), entity, String.class);

            List<Map<String, String>> mappingList = apiRequestMapper.findKor_Eng(apilistid);

            if (mappingList.isEmpty()) {
                combinedResponse.add(responseEntity.getBody());
            } else {
                String koreanResponse = convertEnglishToKorean(responseEntity.getBody(), mappingList);
                combinedResponse.add(koreanResponse);
            }
        }

        return combinedResponse.toString();
    }


    private String convertEnglishToKorean(String englishResponse, List<Map<String, String>> mappingList) {
        for (Map<String, String> mapping : mappingList) {
            String englishName = mapping.get("EN_NM");
            String koreanName = mapping.get("KR_NM");

            englishResponse = englishResponse.replaceAll(englishName, koreanName);
        }

        return englishResponse;
    }

    public List<requiredItemDto> getRequired_Items(int apilistid){ return apiRequestMapper.getRequired_Items(apilistid); }

    public String findMethod_Type(int apilistid){return apiRequestMapper.findMethod_Type(apilistid); }

}
