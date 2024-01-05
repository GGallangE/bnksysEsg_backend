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
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMailwithAttachment(String apiname,String to, byte[] data, String fileName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String subject = apiname;
            String message = to + "님께 API 전송 결과를 전송하였습니다.";

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            ByteArrayResource file = new ByteArrayResource(data);
            mimeMessageHelper.addAttachment(fileName, file);

            javaMailSender.send(mimeMessage);
            System.out.println("Success - 메일 전송 성공"+fileName);
        } catch (MessagingException e) {
            System.out.println("Fail - 메일 전송 실패"+fileName);
            throw new RuntimeException(e);
        }finally {
            deleteFile(fileName);
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

    private void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}