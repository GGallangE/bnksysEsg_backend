package com.bnksys.esg.controller;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.data.useCaseDto;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.UseCaseService;
import com.bnksys.esg.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/spring/usecase")
public class UseCaseController {

    @Autowired
    UseCaseService useCaseService;

    private static final String UPLOAD_DIR = "C:/dev/img";

    @PostMapping("/usecase")
    public ResponseEntity<Response> saveUseCase(Authentication authentication, @RequestBody useCaseDto usecaseDto){
        Response response = new Response();
        try{
            if (!AuthenticationUtils.checkAuthentication(authentication, response)) {
                return ResponseEntity.badRequest().body(response);
            }

            String email = authentication.getName();
            useCaseService.saveUseCase(email,usecaseDto);

            response.setSuccess(true);
            response.getMessages().add("등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/apidetail")
    // api_detail에서 활용사례 불러오기
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_apiDetail(@RequestParam int apilistid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_apiDetail(apilistid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/usecasedetail")
    // 활용사례 상세 불러오기
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_usecaseDetail(@RequestParam int usecaseid){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_usecaseDetail(usecaseid);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usecasedetail_apilist")
    public ResponseEntity<ListResponse<apiResultDto>> findUseCase_usecaseDetail_apiList(@RequestParam int usecaseid){
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apiResultDto> apilist = useCaseService.findUseCase_usecaseDetail_apiList(usecaseid);

        response.setSuccess(true);
        response.getData().put("data", apilist);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mainusecase")
    public ResponseEntity<ListResponse<getuseCaseDto>> findUseCase_usecaseMain(@RequestParam String searchname
                            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<getuseCaseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<getuseCaseDto> usecase = useCaseService.findUseCase_usecaseMain(searchname, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", usecase);
        response.getMessages().add("조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/url")
    public ResponseEntity<Response> change(@RequestParam MultipartFile[] img) {
        Response response = new Response();

        if (img != null && img.length > 0) {
            try {
                // 이미지를 저장할 디렉토리 생성
                createUploadDir();

                // 현재 날짜를 기반으로 연도/월/일 폴더 생성
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String subDirectoryDate = dateFormat.format(new Date());
                String subDirectoryPath = UPLOAD_DIR + "/" + subDirectoryDate;
                createSubDirectory(subDirectoryPath);

                // UUID 랜덤값을 사용하여 서브 폴더 생성
                String subDirectoryUuid = UUID.randomUUID().toString();
                String subDirectoryUuidPath = subDirectoryPath + "/" + subDirectoryUuid;
                createSubDirectory(subDirectoryUuidPath);

                // 각 파일에 대한 처리
                for (MultipartFile file : img) {
                    String fileName = file.getOriginalFilename();
                    Path filePath = Path.of(subDirectoryUuidPath, fileName);

                    // 파일 저장
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // 파일 URL 생성 및 응답에 추가
                    String fileUrl = "/" + subDirectoryDate + "/" + subDirectoryUuid + "/" + fileName;
                    response.getMessages().add(fileUrl);
                }

                response.setSuccess(true);
            } catch (IOException e) {
                response.setSuccess(false);
                response.getMessages().add("이미지 업로드 에러: " + e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            response.setSuccess(false);
            response.getMessages().add("변수로 제공된 이미지가 없습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    private void createUploadDir() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    private void createSubDirectory(String subDirectoryPath) {
        File subDir = new File(subDirectoryPath);
        if (!subDir.exists()) {
            subDir.mkdirs();
        }
    }

}
