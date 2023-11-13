package com.bnksys.esg.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String to) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String subject = "BNKSYS_API 중계 시스템 예약 전송";
            String message = to + "님께 API 전송 결과를 전송하였습니다.";

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            byte[] excelData = createExcelFile();

            ByteArrayResource file = new ByteArrayResource(excelData);
            mimeMessageHelper.addAttachment("example.xlsx", file);

            javaMailSender.send(mimeMessage);
            System.out.println("Success - Email with Excel attachment");
        } catch (MessagingException e) {
            System.out.println("Fail - Email with Excel attachment");
            throw new RuntimeException(e);
        }
    }

    private byte[] createExcelFile() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Hello, this is an example Excel file!");

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating Excel file", e);
        }
    }
}