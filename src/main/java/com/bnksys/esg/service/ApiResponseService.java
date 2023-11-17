package com.bnksys.esg.service;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiResponseService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gonggongdata.serviceKey}")
    private String serviceKey;

    @Value("${apilist_Business.url}")
    private String apilist_BusinessUrl;


    public void apilist_Business(String email) {
        String[] bNosArray = {"6028154897", "6028154897"};
        List<String> bNoList = Arrays.asList(bNosArray);

        // Convert the list to a list of quoted strings
        List<String> quotedBNoList = bNoList.stream()
                .map(bNo -> "\"" + bNo + "\"")
                .collect(Collectors.toList());

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Add the serviceKey to the URL
        String urlWithServiceKey = apilist_BusinessUrl + "?serviceKey=" + UriUtils.encode(serviceKey, "UTF-8");

        // Create the request body as a JSON array
        String requestBody = "{\"b_no\": [" + String.join(",", quotedBNoList) + "]}";

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
