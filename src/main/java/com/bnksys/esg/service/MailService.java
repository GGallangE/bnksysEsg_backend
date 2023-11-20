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

    public void sendMailwithExcel(String to, byte[] excelData, String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String subject = "BNKSYS_API 중계 시스템 예약 전송";
            String message = to + "님께 API 전송 결과를 전송하였습니다.";

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            ByteArrayResource file = new ByteArrayResource(excelData);
            mimeMessageHelper.addAttachment(filePath + ".xlsx", file);

            javaMailSender.send(mimeMessage);
            System.out.println("Success - 메일 전송 성공(엑셀)");
        } catch (MessagingException e) {
            System.out.println("Fail - 메일 전송 실패(엑셀)");
            throw new RuntimeException(e);
        }
    }

    public void sendMail(String to) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String subject = "BNKSYS_API 중계 시스템 예약 전송";
            String message = to + "님께 API 전송 결과를 전송하였습니다.";

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            javaMailSender.send(mimeMessage);
            System.out.println("Success - 메일 전송 성공");
        } catch (MessagingException e) {
            System.out.println("Fail - 메일 전송 실패");
            throw new RuntimeException(e);
        }
    }
}