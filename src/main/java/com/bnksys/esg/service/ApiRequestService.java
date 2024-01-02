package com.bnksys.esg.service;

import com.bnksys.esg.data.apiurlAndkeyDto;
import com.bnksys.esg.data.requiredItemDto;
import com.bnksys.esg.mapper.ApiRequestMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class ApiRequestService {
    ApiRequestMapper apiRequestMapper;

    public ApiRequestService(ApiRequestMapper apiRequestMapper) {
        this.apiRequestMapper = apiRequestMapper;
    }

    public String getRequestApi(int apilistid, List<Map<String, String>> paramsList, String type) throws IOException {
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

        if ("excel".equals(type)) {
            String filePath = "C:\\Users\\busan\\Downloads\\output1.xlsx";
            generateExcel_Get(combinedResponse, filePath);
            return "ok";
        }else if("json".equals(type)){
            return "ok";
        } else if ("xml".equals(type)) {
            return "ok";
        } else {
            return combinedResponse.toString();
        }
    }

    public String postRequestApi(int apilistid, List<Map<String, String>> paramsList, String type) throws Exception {
        apiurlAndkeyDto apiInfo = apiRequestMapper.findurlAndkey(apilistid);
        String url = apiInfo.getApiUrl() + "?serviceKey=" + URLEncoder.encode(apiInfo.getApiKey(), "UTF-8");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        StringJoiner combinedResponse = new StringJoiner(", ", "[", "]");

        String filePath = "C:\\Users\\busan\\Downloads\\output.xlsx";
        List<String> excelFilePaths = new ArrayList<>();

        String downloadFolderPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;

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
        String formattedResponse;
        if ("json".equals(type)) {
            formattedResponse = convertXmlToJson(combinedResponse.toString());
            String jsonFilePath = "C:\\Users\\busan\\Downloads\\output.json";
            saveToFile(formattedResponse, jsonFilePath);
            return "ok";
        } else if ("xml".equals(type)) {
            String xmlFilePath = "C:\\Users\\busan\\Downloads\\output.xml";
            saveToFile(combinedResponse.toString(), xmlFilePath);
            return "ok";
        } else if ("excel".equals(type)) {
            generateExcel_Post(combinedResponse, filePath);
            excelFilePaths.add(filePath);
            return "ok";
        } else {
            return combinedResponse.toString();
        }
//        return combinedResponse.toString();

    }

    private void saveToFile(String content, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateExcel_Post(StringJoiner combinedResponse, String filePath) {
        List<Map<String, String>> dataList = convertStringJoinerToList_Post(combinedResponse);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            Row headerRow = sheet.createRow(0);
            int cellIndex = 0;
            for (String key : dataList.get(0).keySet()) {
                Cell cell = headerRow.createCell(cellIndex++);
                cell.setCellValue(key);
            }

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, String> data = dataList.get(i);

                Row row = sheet.createRow(i + 1);
                cellIndex = 0;
                for (String value : data.values()) {
                    Cell cell = row.createCell(cellIndex++);
                    cell.setCellValue(value);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, String>> convertStringJoinerToList_Post(StringJoiner combinedResponse) {
        List<Map<String, String>> dataList = new ArrayList<>();

        String jsonArray = combinedResponse.toString();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonArray);

            for (JsonNode response : jsonNode) {
                JsonNode dataArray = response.path("data");

                for (JsonNode dataNode : dataArray) {
                    Iterator<Map.Entry<String, JsonNode>> fields = dataNode.fields();
                    Map<String, String> dataMap = new HashMap<>();

                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        String key = entry.getKey();
                        String value = entry.getValue().asText();
                        dataMap.put(key, value);
                    }

                    dataList.add(dataMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private void generateExcel_Get(StringJoiner combinedResponse, String filePath) {
        List<Map<String, String>> dataList = convertList_Get(combinedResponse);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, String> data = dataList.get(i);

                if (i == 0) {
                    Row headerRow = sheet.createRow(0);
                    int cellIndex = 0;
                    for (String key : data.keySet()) {
                        Cell cell = headerRow.createCell(cellIndex++);
                        cell.setCellValue(key);
                    }
                }

                Row row = sheet.createRow(i + 1);
                int cellIndex = 0;
                for (String value : data.values()) {
                    Cell cell = row.createCell(cellIndex++);
                    cell.setCellValue(value);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, String>> convertList_Get(StringJoiner combinedResponse) {
        List<Map<String, String>> dataList = new ArrayList<>();

        String[] responses = combinedResponse.toString().split(", ");
        for (String response : responses) {
            try {
                String trimmedResponse = response.trim();
                if (trimmedResponse.startsWith("[")) {
                    trimmedResponse = trimmedResponse.substring(1);
                }
                if (trimmedResponse.endsWith("]")) {
                    trimmedResponse = trimmedResponse.substring(0, trimmedResponse.length() - 1);
                }

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(trimmedResponse)));

                NodeList itemList = document.getElementsByTagName("item");
                for (int i = 0; i < itemList.getLength(); i++) {
                    Node item = itemList.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        NodeList fieldList = item.getChildNodes();
                        Map<String, String> dataMap = new HashMap<>();

                        for (int j = 0; j < fieldList.getLength(); j++) {
                            Node field = fieldList.item(j);
                            if (field.getNodeType() == Node.ELEMENT_NODE) {
                                String key = field.getNodeName();
                                String value = field.getTextContent();
                                dataMap.put(key, value);
                            }
                        }

                        dataList.add(dataMap);
                    }
                }
            } catch (ParserConfigurationException | org.xml.sax.SAXException | IOException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    private String convertXmlToJson(String inputString) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(inputString);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to convert XML to JSON.\"}";
        }
    }

    private String convertEnglishToKorean(String englishResponse, List<Map<String, String>> mappingList) {
        for (Map<String, String> mapping : mappingList) {
            String englishName = mapping.get("EN_NM");
            String koreanName = mapping.get("KR_NM");

            englishResponse = englishResponse.replaceAll("\\b" + englishName + "\\b", koreanName);
        }

        return englishResponse;
    }

    public List<requiredItemDto> getRequired_Items(int apilistid){ return apiRequestMapper.getRequired_Items(apilistid); }

    public String findMethod_Type(int apilistid){return apiRequestMapper.findMethod_Type(apilistid); }

}
