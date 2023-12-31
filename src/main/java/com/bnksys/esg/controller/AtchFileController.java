package com.bnksys.esg.controller;


import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.AtchFileService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/spring/atchfile")
public class AtchFileController {

    @Autowired
    AtchFileService atchFileService;

    @PostMapping("/upload")
    public ResponseEntity<Response> saveAtchFile(@RequestParam("files")MultipartFile[] files){
        Response response = new Response();

        try {
            atchFileService.saveAtchFile(files);
            response.setSuccess(true);
            response.getMessages().add("변경 완료");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/download/{atchDetailFileId}")
    public ResponseEntity<Resource> findAtchDetailFile(@PathVariable int atchDetailFileId){
        atchDetailFileDto atchDetailFiledto = atchFileService.findAtchDetailFile(atchDetailFileId);

        if (atchDetailFiledto != null) {
            String filePath = atchDetailFiledto.getAtchfilepath() + atchDetailFiledto.getAtchfilename();

            try {
                Resource resource = atchFileService.loadFileAsResource(filePath);

                if (resource != null) {
                    String downloadFolderPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;

                    String downloadFilePath = downloadFolderPath + getUniqueFilename(downloadFolderPath, atchDetailFiledto.getOrgfilename(), atchDetailFiledto.getAtchfileext());

                    File downloadFile = new File(downloadFilePath);

                    Files.copy(resource.getInputStream(), downloadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    String sanitizedFilename = atchDetailFiledto.getOrgfilename().replaceAll("[^a-zA-Z0-9.-]", "_");

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + sanitizedFilename + "\"")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    private String getUniqueFilename(String downloadFolderPath, String orgFilename, String fileExt) {
        String uniqueFilename = orgFilename;
        int count = 1;

        while (new File(downloadFolderPath + uniqueFilename + "." + fileExt).exists()) {
            uniqueFilename = orgFilename + "(" + count + ")";
            count++;
        }

        return uniqueFilename + "." + fileExt;
    }
}
