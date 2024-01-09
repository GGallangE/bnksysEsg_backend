package com.bnksys.esg.service;

import com.bnksys.esg.data.atchDetailFileDto;
import com.bnksys.esg.mapper.AtchFileMapper;
import com.bnksys.esg.mapper.MainMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AtchFileService {

    private static final String FILE_UPLOAD_PATH = "C:/dev/file/";
    AtchFileMapper atchFileMapper;

    MainMapper mainMapper;

    public AtchFileService(AtchFileMapper atchFileMapper, MainMapper mainMapper){
        this.atchFileMapper = atchFileMapper;
        this.mainMapper = mainMapper;
    }

    @Transactional
    public int saveAtchFile(String email, MultipartFile[] files) throws IOException {

        File directory = new File(FILE_UPLOAD_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int userid = mainMapper.findbyemail(email);

        int atchfileid = atchFileMapper.maxAtchFileId();
        atchFileMapper.saveAtchFile(userid, atchfileid);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String currentTime = LocalDateTime.now().format(formatter);

        for (MultipartFile file : files) {
            int count = 1;
            String filePath = FILE_UPLOAD_PATH;
            String fileName = currentTime + count;
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileSize = String.valueOf(file.getSize());
            String orgFileName = FilenameUtils.getBaseName(file.getOriginalFilename());

            while (new File(filePath + fileName).exists()) {
                count++;
                fileName = currentTime + count;
            }

            File destFile = new File(filePath + fileName);
            file.transferTo(destFile);

            atchDetailFileDto atchDetailFiledto = new atchDetailFileDto();
            atchDetailFiledto.setAtchfileid(atchfileid);
            atchDetailFiledto.setAtchfilepath(filePath);
            atchDetailFiledto.setAtchfilename(fileName);
            atchDetailFiledto.setAtchfileext(fileExt);
            atchDetailFiledto.setAtchfilesize(fileSize);
            atchDetailFiledto.setOrgfilename(orgFileName);

            atchFileMapper.saveAtchDetailFile(atchDetailFiledto); // 매퍼를 통해 DB에 저장
        }
        return atchfileid;
    }

    public Resource loadFileAsResource(String filePath) {
        try {
            Path file = Paths.get(filePath);
            UrlResource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                return null; // 파일을 찾을 수 없거나 읽을 수 없는 경우 null 반환
            }
        } catch (MalformedURLException e) {
            return null; // 파일 경로가 잘못된 경우 null 반환
        }
    }


    public atchDetailFileDto findAtchDetailFile(int atchDetailFileId){
        return atchFileMapper.findAtchDetailFile(atchDetailFileId);
    }
}
