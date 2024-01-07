package com.bnksys.esg.controller;

import com.bnksys.esg.data.*;
import com.bnksys.esg.response.ListResponse;
import com.bnksys.esg.response.Response;
import com.bnksys.esg.service.AdminService;
import com.bnksys.esg.service.AtchFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/spring/admin")
public class AdminController {

    @Autowired
    AdminService adminService;  // adminService 의존성 주입(필드 주입)

    @Autowired
    AtchFileService atchFileService;  // atchfileService 의존성 주입(필드 주입)

    @GetMapping("/apiapplylist")
    /* Api 신청현황 조희를 위한 메서드 */
    public ResponseEntity<ListResponse<apiApplyDto>> findApi_ApplyLIST(@RequestParam(value = "apiapplyid", required = false) Integer apiapplyid
            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){

        // ListResponse 객체를 생성하여 초기화
        ListResponse<apiApplyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // adminService로 API 신청 현황을 조회하고 결과를 리스트로 받아옴
        List<apiApplyDto> apiapplyresult = adminService.findApi_ApplyLIST(apiapplyid, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", apiapplyresult);
        response.getMessages().add("API 신청 현황 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/apiapplylist_byname")
    public ResponseEntity<ListResponse<apiApplyDto>> findApi_ApplyList_ByName(@RequestParam String applynm){

        // ListResponse 객체를 생성하여 초기화
        ListResponse<apiApplyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // adminService로 API 신청 현황을 조회하고 결과를 리스트로 받아옴
        List<apiApplyDto> apiapplyresult = adminService.findApi_ApplyList_ByName(applynm);

        response.setSuccess(true);
        response.getData().put("data", apiapplyresult);
        response.getMessages().add("API 신청 현황 조회 완료");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/apiapply/confirm")
    /* 사용자들이 신청한 API 지원에 대한 승인 여부를 지정하기 위한 메서드 */
    public ResponseEntity<Response> updateApi_ApplyStatus(@RequestBody apiApplyDto apiapplyDto){
        Response response = new Response();

        try{
            adminService.updateApi_ApplyStatus(apiapplyDto);  // API 지원에 대한 상태값 변경

            response.setSuccess(true);
            response.getMessages().add("API 지원현황 상태 변경 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/apilist")
    /* 현재 관리중인 API 목록들을 조회 할 수있는 메서드 */
    public ResponseEntity<ListResponse<apiResultDto>> findApiList(@RequestParam(value = "apilistid", required = false) Integer apilistid
                ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int pageSize){
        // apilistid를 넘겨 주지않으면 전체 List를, 넘겨주면 각각 상세 List에 대한 정보를 넘겨준다.

        // ListResponse 객체를 생성하여 초기화
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // adminService에서 List를 찾아서 반환
        List<apiResultDto> apiresult = adminService.findApiList(apilistid, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", apiresult);
        response.getMessages().add("API LIST 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/apilist_search")
    /* 현재 관리중인 API 목록들을 조회 할 수있는 메서드 */
    public ResponseEntity<ListResponse<apiResultDto>> findApiList_Search(@RequestParam(value = "string", required = false) String string
            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int pageSize){
        // apilistid를 넘겨 주지않으면 전체 List를, 넘겨주면 각각 상세 List에 대한 정보를 넘겨준다.

        // ListResponse 객체를 생성하여 초기화
        ListResponse<apiResultDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // adminService에서 List를 찾아서 반환
        List<apiResultDto> apiresult = adminService.findApiList_Search(string, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", apiresult);
        response.getMessages().add("API LIST 검색 완료");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/apilist/save")
    /* 관리자가 새로운 APILIST 생성하기 위한 메서드 */
    public ResponseEntity<Response> saveApiList(@RequestBody apiResultDto apiresultDto, Authentication authentication){
        Response response = new Response();

        try{
            String email = authentication.getName();
            // 신규등록시
            if(0 == apiresultDto.getApilistid()){
                int apilistid = adminService.maxApiListId(); // 현재 API_LIST TABLE의 PK값 + 1 받아옴.
                apiresultDto.setApilistid(apilistid);
                adminService.saveApiList(apiresultDto,email);
                response.getMessages().add("등록 완료");
            }
            // 수정시
            else{
                adminService.updateApiList(apiresultDto,email);
                response.getMessages().add("수정 완료");
            }

            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            // 비정상적인 에러
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/inquiry/list")
    /* 전체 문의 List를 조회하기 위한 메서드, inquiryid를 전달해 주면 상세 문의 내용을 볼 수 있다. */
    public ResponseEntity<ListResponse<inQuiryDto>> findinQuiry(@RequestParam(value = "inquiryid", required = false) Integer inquiryid
            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // 문의사항 List를 adminService를 통해 받아옴.
        List<inQuiryDto> inquiryList = adminService.findinQuiry(inquiryid, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", inquiryList); // inquiryList 넣어줌
        response.getMessages().add("문의 사항 조회 완료");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/inquiry/list/answer")
    /* 각 문의사항 마다 답변을 조회하기 위한 메서드 */
    public ResponseEntity<ListResponse<inQuiryDto>> findinQuiryAnswer(@Param("inquiryid") int inquiryid){
        ListResponse<inQuiryDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        // adminService를 통해 문의사항 답변 List를 조회
        List<inQuiryDto> inquiryAnswer = adminService.findinQuiryAnswer(inquiryid);

        response.setSuccess(true);
        response.getData().put("data", inquiryAnswer);
        response.getMessages().add("문의 사항 답변 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/inquiry/answer")
    /* 관리자가 문의사항에 대한 답변을 달 수 있게 해주는 메서드 */
    public ResponseEntity<Response> saveinquiry_Answer(@RequestBody inQuiryDto inquiryDto){
        Response response = new Response();

        try{
            // 로그인 된 유저의 email을 찾음
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // 문의 답변 신규등록 시
            if(adminService.findAnswerCount(inquiryDto.getParentid()) == 0){
                adminService.saveinquiry_Answer(email, inquiryDto);
                response.getMessages().add("등록 완료");
            }
            // 문의 답변 수정 시
            else{
                adminService.updateinquiry_Answer(email, inquiryDto);
                response.getMessages().add("수정 완료");
            }

            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/notice/create")
    /* 관리자가 공지사항 작성하는 메서드 */
    public ResponseEntity<Response> savenotice(@RequestBody noticeDto noticedto, Authentication authentication){
        // 첨부파일은 선택, 공지사항 관련 변수들은 필수

        Response response = new Response();
        try{
            String email = authentication.getName();
            // adminService엥서 공지사항 저장, 첨부파일 있을시 첨부파일도 함께 저장
            adminService.saveNotice(email, noticedto.getNoticenm(), noticedto.getNoticecntn(), noticedto.getAtchfileid());

            response.setSuccess(true);
            response.getMessages().add("공지사항 등록 완료");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/api/key")
    public ResponseEntity<ListResponse<apikeyDto>> findApiKey(@RequestParam(value = "apikeyid", required = false) Integer apikeyid
        ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<apikeyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apikeyDto> apikeyList = adminService.findApiKey(apikeyid, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", apikeyList);
        response.getMessages().add("api key 조회 완료");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/key_search")
    public ResponseEntity<ListResponse<apikeyDto>> findApiKey_Search(@RequestParam(value = "name", required = false) String name
            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<apikeyDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<apikeyDto> apikeyList = adminService.findApiKey_Search(name, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", apikeyList);
        response.getMessages().add("api key 검색 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/key")
    public ResponseEntity<Response> saveapikey(@RequestBody apikeyDto apikeydto){
        Response response = new Response();
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(apikeydto.getApikeyid() == 0){
                adminService.saveapikey(apikeydto,email);
            }else{
                adminService.updateapikey(apikeydto,email);
            }

            response.setSuccess(true);
            response.getMessages().add("Api Key 등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/api/comcode_search")
    public ResponseEntity<ListResponse<comCodeDto>> findComCode_Search(@RequestParam(value = "codevalue", required = false) String codevalue,
                                                                       @RequestParam(value = "code", required = false) String code){
        ListResponse<comCodeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<comCodeDto> comCodeList = adminService.findComCode_Search(codevalue, code);

        response.setSuccess(true);
        response.getData().put("data", comCodeList);
        response.getMessages().add("공통 코드 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin_comcode_search")
    public ResponseEntity<ListResponse<comCodeDto>> findAdmin_ComCode_Search(@RequestParam(value = "code", required = false) String code
            ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<comCodeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<comCodeDto> comCodeList = adminService.findAdmin_ComCode_Search(code,page,pageSize);

        response.setSuccess(true);
        response.getData().put("data", comCodeList);
        response.getMessages().add("공통 코드 조회 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/comcode")
    public ResponseEntity<ListResponse<comCodeDto>> findComCode(@RequestParam(value = "id", required = false) Integer id
        ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<comCodeDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        List<comCodeDto> comCodeList = adminService.findComCode(id, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", comCodeList);
        response.getMessages().add("공통 코드 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/comcode")
    public ResponseEntity<Response> save_comcode(@RequestBody comCodeDto comcodeDto){
        Response response = new Response();
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(comcodeDto.getId() == 0){
                adminService.save_comcode(comcodeDto,email);
            }else{
                adminService.update_comcode(comcodeDto,email);
            }

            response.setSuccess(true);
            response.getMessages().add("공통코드 등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/api/need_request")
    public ResponseEntity<ListResponse<apiNeedRequestDto>> findNeed_Request(@RequestParam(value = "apirqrditemsid", required = false) Integer apirqrditemsid,
                                                                            @RequestParam(value = "apinm", required = false) String apinm,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<apiNeedRequestDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());
        if(apinm == null || apinm =="") apinm = null;
        List<apiNeedRequestDto> NeedRequestList = adminService.findNeed_Request(apirqrditemsid, apinm, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", NeedRequestList);
        response.getMessages().add("API 요청시 필수 항목 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/need_request")
    public ResponseEntity<Response> save_needrequest(@RequestBody apiNeedRequestDto apineedRequestDto){
        Response response = new Response();
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(apineedRequestDto.getApirqrditemsid() == 0){
                adminService.save_needrequest(apineedRequestDto,email);
            }else{
                adminService.update_needrequest(apineedRequestDto,email);
            }

            response.setSuccess(true);
            response.getMessages().add("API 요청시 필수 항목 등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/api/need_response")
    public ResponseEntity<ListResponse<apiNeedResponseDto>> findNeed_Response(@RequestParam(value = "apirsqeitemsid", required = false) Integer apirsqeitemsid
                                                                              ,@RequestParam(value = "apinm", required = false) String apinm
        ,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        ListResponse<apiNeedResponseDto> response = new ListResponse<>(new HashMap<>(), false, new ArrayList<>());

        if(apinm == null || apinm =="") apinm = null;
        List<apiNeedResponseDto> NeedResponseList = adminService.findNeed_Response(apirsqeitemsid, apinm, page, pageSize);

        response.setSuccess(true);
        response.getData().put("data", NeedResponseList);
        response.getMessages().add("API 반환시 필수 항목 조회 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/need_response")
    public ResponseEntity<Response> save_needresponse(@RequestBody apiNeedResponseDto apineedResponseDto){
        Response response = new Response();
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if(apineedResponseDto.getApirsqeitemsid() == 0){
                adminService.save_needresponse(apineedResponseDto,email);
            }else{
                adminService.update_needresponse(apineedResponseDto,email);
            }

            response.setSuccess(true);
            response.getMessages().add("API 반환시 필수 항목 등록 완료");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.getMessages().add("비정상적인 에러 발생: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
