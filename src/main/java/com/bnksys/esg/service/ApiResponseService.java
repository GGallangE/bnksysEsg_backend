package com.bnksys.esg.service;

import com.bnksys.esg.data.batchDetailArgsDto;
import com.bnksys.esg.mapper.BatchListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiResponseService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gonggongdata.serviceKey}")
    private String serviceKey;

    @Value("${apilist_Business.url}")
    private String apilist_BusinessUrl;

    BatchListMapper batchListMapper;

    public ApiResponseService(BatchListMapper batchListMapper){
        this.batchListMapper = batchListMapper;
    }


    public void apilist_Business(String email) {
        List<batchDetailArgsDto> apiArgs = batchListMapper.findAll_ApiArgs(6);
        List<String> extractedArg1List = apiArgs.stream()
                .map(batchDetailArgsDto::getArg1)
                .map(bNo -> "\"" + bNo + "\"")
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Add the serviceKey to the URL
        String urlWithServiceKey = apilist_BusinessUrl + "?serviceKey=" + UriUtils.encode(serviceKey, "UTF-8");

        String requestBody = "{\"b_no\": [" + String.join(",", extractedArg1List) + "]}";

        try {
            // Send the POST request
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(new URI(urlWithServiceKey), new HttpEntity<>(requestBody, headers), String.class);
            String response = responseEntity.getBody();

            // Handle the response as needed
            System.out.println(response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
