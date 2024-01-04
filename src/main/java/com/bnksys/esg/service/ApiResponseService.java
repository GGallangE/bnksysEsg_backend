package com.bnksys.esg.service;

import com.bnksys.esg.data.apiNeedRequestDto;
import com.bnksys.esg.data.apiurlAndkeyDto;
import com.bnksys.esg.data.batchDetailArgsDto;
import com.bnksys.esg.mapper.ApiRequestMapper;
import com.bnksys.esg.mapper.BatchListMapper;
import com.bnksys.esg.mapper.SchNtfMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
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

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ApiResponseService {

    @Autowired
    private MailService mailService;

    @Autowired
    private SchNtfService schNtfService;

    @Autowired
    private ApiRequestService apiRequestService;

    BatchListMapper batchListMapper;

    ApiRequestMapper apiRequestMapper;

    SchNtfMapper schNtfMapper;

    public ApiResponseService(BatchListMapper batchListMapper, ApiRequestMapper apiRequestMapper, SchNtfMapper schNtfMapper){
        this.batchListMapper = batchListMapper;
        this.apiRequestMapper = apiRequestMapper;
        this.schNtfMapper = schNtfMapper;
    }

    public void request_get(String email, int batchlistid, int apilistid, int userid, String apiformat) throws Exception {
        String methodType = apiRequestMapper.findMethod_Type(apilistid);
        List<batchDetailArgsDto> batchdetaillist = batchListMapper.find_batchdetaillist(batchlistid);
        List<apiNeedRequestDto> requestlist = batchListMapper.find_requesttlist(apilistid);
        String apiname = schNtfMapper.getApiName(apilistid);
        List<Map<String, String>> mappedResults = BatchDetails_RequestList(batchdetaillist, requestlist);
        String filePath;
        if ("GET".equals(methodType)){
            filePath = getRequestApi(apilistid, mappedResults, apiformat);
        }else{
            filePath = postRequestApi(apilistid, mappedResults, apiformat);
        }
        byte[] file = Files.readAllBytes(Paths.get(filePath));
        mailService.sendMailwithExcel(apiname,email, file, new File(filePath).getName());
        String title = "API 전송 예약 완료";
        String p_content = "에 대한 결과 메일 발송이 예약 되었습니다.";
        schNtfService.save_Alarm_Complete_Schedule(email, title, p_content, apilistid);
        System.out.println(mappedResults);
}

    public List<Map<String, String>> BatchDetails_RequestList(List<batchDetailArgsDto> batchdetaillist, List<apiNeedRequestDto> requestlist) {
        List<Map<String, String>> resultList = new ArrayList<>();

        for (batchDetailArgsDto batchDetail : batchdetaillist) {
            Map<String, String> map = new HashMap<>();

            for (apiNeedRequestDto request : requestlist) {
                String requestFieldName = request.getRqrdrqstnm();
                int argIndex = request.getSort();
                String argValue = ArgValueByIndex(batchDetail, argIndex);

                if (argValue != null) {
                    map.put(requestFieldName, argValue);
                }
            }

            resultList.add(map);
        }

        return resultList;
    }

    private String ArgValueByIndex(batchDetailArgsDto detail, int index) {
        try {
            Field field = detail.getClass().getDeclaredField("arg" + index);
            field.setAccessible(true);
            return (String) field.get(detail);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRequestApi(int apilistid, List<Map<String, String>> paramsList, String apiformat) throws Exception {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);
        String dataformat = apiRequestMapper.findDataFormat(apilistid);
        String apinm = apiRequestMapper.findApiNm(apilistid);
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

            if ("json".equals(apiformat) && dataformat.equals("XML,JSON")) {
                urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
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

            if (mappingList.isEmpty() || apilistid == 952) {
                combinedResponse.add(sb.toString());
            } else {
                String koreanResponse = convertEnglishToKorean(sb.toString(), mappingList);
                combinedResponse.add(koreanResponse);
            }
//            combinedResponse.add(sb.toString());
        }
        String sanitizedFileName = apinm.replace('/', '_');
        String filePath = sanitizedFileName;
        if ("excel".equals(apiformat)) {
            filePath += ".xlsx";
            apiRequestService.generateExcel_Get(combinedResponse, filePath);
            return filePath;
        } else if ("json".equals(apiformat)) {
            filePath += ".json";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        } else if ("xml".equals(apiformat)) {
            filePath += ".xml";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        } else {
            filePath += ".txt";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        }
    }

    public String postRequestApi(int apilistid, List<Map<String, String>> paramsList, String apiformat) throws Exception {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);
        String url = apiInfo.getApiUrl() + "?serviceKey=" + URLEncoder.encode(apiInfo.getApiKey(), "UTF-8");
        String apinm = apiRequestMapper.findApiNm(apilistid);
        String downloadFolderPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;

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
        String sanitizedFileName = apinm.replace('/', '_');
        String filePath = sanitizedFileName;
        if ("excel".equals(apiformat)) {
            filePath += ".xlsx";
            apiRequestService.generateExcel_Post(combinedResponse, filePath);
            return filePath;
        } else if ("json".equals(apiformat)) {
            filePath += ".json";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        } else if ("xml".equals(apiformat)) {
            filePath += ".xml";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        } else {
            filePath += ".txt";
            saveToFile(combinedResponse.toString(), filePath);
            return filePath;
        }
    }

    private String convertEnglishToKorean(String englishResponse, List<Map<String, String>> mappingList) {
        for (Map<String, String> mapping : mappingList) {
            String englishName = Pattern.quote(mapping.get("EN_NM"));
            String koreanName = Matcher.quoteReplacement(mapping.get("KR_NM"));

            englishResponse = englishResponse.replaceAll("\\b" + englishName + "\\b", koreanName);
        }

        return englishResponse;
    }

    private void saveToFile(String content, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}