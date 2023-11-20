package com.bnksys.esg.service;

import com.bnksys.esg.data.batchDetailArgsDto;
import com.bnksys.esg.mapper.BatchListMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiResponseService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MailService mailService;

    @Autowired
    private DataForExcelService dataForExcelService;

    @Autowired
    private SchNtfService schNtfService;

    @Value("${gonggongdata.serviceKey}")
    private String serviceKey;

    @Value("${apilist_Business.url}")
    private String apilist_BusinessUrl;

    BatchListMapper batchListMapper;

    public ApiResponseService(BatchListMapper batchListMapper){
        this.batchListMapper = batchListMapper;
    }


    public void apilist_Business(String email, int batchlistid, int apilistid) {
        /* 국세청_사업자등록정보 상태조회 서비스 */
        List<batchDetailArgsDto> apiArgs = batchListMapper.findAll_ApiArgs(batchlistid);
        List<String> extractedArg1List = apiArgs.stream()
                .map(batchDetailArgsDto::getArg1)
                .map(bNo -> "\"" + bNo + "\"")
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = apilist_BusinessUrl + "?serviceKey=" + UriUtils.encode(serviceKey, "UTF-8");

        String requestBody = "{\"b_no\": [" + String.join(",", extractedArg1List) + "]}";

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(new URI(url), new HttpEntity<>(requestBody, headers), String.class);
            String response = responseEntity.getBody();
            List<Map<String, String>> responseData = dataForExcelService.apilist_Business(response);

            String filePath = "사업자등록정보 상태조회 서비스";

            byte[] excelData = dataForExcelService.convertToExcel(responseData,filePath);

            mailService.sendMailwithExcel(email, excelData, filePath);
            String title = "API 결과 메일 전송 완료";
            String p_content = "에 대한 API 결과 메일이 전송 완료 되었습니다";
            schNtfService.save_Alarm_Complete_Schedule(email, title, p_content, apilistid);
            System.out.println("도착 완료");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
