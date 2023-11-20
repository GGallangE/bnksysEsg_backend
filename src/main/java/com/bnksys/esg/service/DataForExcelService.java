package com.bnksys.esg.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class DataForExcelService {

    public List<Map<String, String>> apilist_Business(String jsonResponse) {
        List<Map<String, String>> responseData = new ArrayList<>();

        try {
            JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);
            JsonNode dataNode = rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode entryNode : dataNode) {
                    LinkedHashMap<String, String> entryMap = new LinkedHashMap<>();
                    entryMap.put("사업자 등록번호", entryNode.get("b_no").asText());
                    entryMap.put("납세자 명칭", entryNode.get("b_stt").asText());
                    entryMap.put("과세유형 메세지", entryNode.get("tax_type").asText());
                    entryMap.put("폐업일", entryNode.get("end_dt").asText());
                    entryMap.put("단위과세전환 폐업여부", entryNode.get("utcc_yn").asText());
                    entryMap.put("최근과세유형 전환일자 ", entryNode.get("tax_type_change_dt").asText());
                    entryMap.put("세금계산서적용일자 ", entryNode.get("invoice_apply_dt").asText());
                    entryMap.put("직전과세유형메세지", entryNode.get("rbf_tax_type").asText());
                    responseData.add(entryMap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData;
    }


    public byte[] convertToExcel(List<Map<String, String>> data, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(filePath);

        if (data.isEmpty()) {
            System.out.println("데이터가 없어 파일생성을 하지 않습니다.");
            return new byte[0];
        }

        Row headerRow = sheet.createRow(0);
        Map<String, String> firstRowData = data.get(0);

        int colIdx = 0;
        for (String header : firstRowData.keySet()) {
            Cell cell = headerRow.createCell(colIdx++);
            cell.setCellValue(header);
        }

        int rowIdx = 1;
        for (Map<String, String> rowData : data) {
            Row dataRow = sheet.createRow(rowIdx++);

            colIdx = 0;
            for (String header : rowData.keySet()) {
                Cell cell = dataRow.createCell(colIdx++);
                cell.setCellValue(rowData.get(header));
            }
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
