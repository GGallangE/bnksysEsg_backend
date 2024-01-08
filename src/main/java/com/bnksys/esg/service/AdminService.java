package com.bnksys.esg.service;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiNeedRequestDto;
import com.bnksys.esg.data.apiNeedResponseDto;
import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.apikeyDto;
import com.bnksys.esg.data.comCodeDto;
import com.bnksys.esg.data.inQuiryDto;
import com.bnksys.esg.mapper.AdminMapper;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    AdminMapper adminMapper;
    MainMapper mainMapper;

    public AdminService(AdminMapper adminMapper, MainMapper mainMapper) {
        this.adminMapper = adminMapper;
        this.mainMapper = mainMapper;
    }

    public int findAnswerCount(int parentid){
        return adminMapper.findAnswerCount(parentid);
    }

    public int maxApiListId(){
        return adminMapper.maxApiListId();
    }

    public List<apiApplyDto> findApi_ApplyLIST(Integer apiapplyid, int page, int pageSize) {
        int offset =  page * pageSize;
        return adminMapper.findApi_ApplyLIST(apiapplyid, offset, pageSize);
    }

    public List<apiApplyDto> findApi_ApplyList_ByName(String applynm){return adminMapper.findApi_ApplyList_ByName(applynm); }
    public List<inQuiryDto> findinQuiry(Integer inquiryid, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findinQuiry(inquiryid, offset, pageSize);
    }

    public List<inQuiryDto> findinQuiryAnswer(int inquiryid) {
        return adminMapper.findinQuiryAnswer(inquiryid);

    }
    public List<apiResultDto> findApiList(Integer apilistid, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findApiList(apilistid, offset, pageSize);
    }

    public List<apiResultDto> findApiList_Search(String string, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findApiList_Search(string, offset, pageSize);
    }


    public List<apikeyDto> findApiKey(Integer apikeyid, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findApiKey(apikeyid, offset, pageSize);
    }

    public List<apikeyDto> findApiKey_Search(String name, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findApiKey_Search(name, offset, pageSize);
    }


    public List<comCodeDto> findComCode(Integer id, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findComCode(id, offset, pageSize);
    }

    public List<comCodeDto> findComCode_Search(String codevalue, String code) {
        return adminMapper.findComCode_Search(codevalue, code);
    }

    public List<comCodeDto> findAdmin_ComCode_Search(String code, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findAdmin_ComCode_Search(code, offset, pageSize);
    }

    public List<apiNeedRequestDto> findNeed_Request(Integer apirqrditemsid, String apinm, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findNeed_Request(apirqrditemsid, apinm, offset, pageSize);
    }

    public List<apiNeedResponseDto> findNeed_Response(Integer apirsqeitemsid, String apinm, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findNeed_Response(apirsqeitemsid, apinm, offset, pageSize);
    }


    public void saveinquiry_Answer(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveinquiry_Answer(userid, inquiryDto);
    }

    public void saveApiList(apiResultDto apiresultDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveApiList(apiresultDto,userid);
    }

    public void saveNotice(String email, String noticenm, String noticecntn, int atchfileid){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveNotice(userid, noticenm, noticecntn, atchfileid);
    }

    public void saveapikey(apikeyDto apikeydto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveapikey(apikeydto,userid);
    }


    public void save_comcode(comCodeDto comcodeDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.save_comcode(comcodeDto,userid);
    }

    public void save_needrequest(apiNeedRequestDto apineedRequestDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.save_needrequest(apineedRequestDto,userid);
    }

    public void save_needresponse(apiNeedResponseDto apineedResponseDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.save_needresponse(apineedResponseDto,userid);
    }

    public void updateApi_ApplyStatus(apiApplyDto apiapplyDto){
        adminMapper.updateApi_ApplyStatus(apiapplyDto);
    }

    public void updateApiList(apiResultDto apiresultDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.updateApiList(apiresultDto,userid);
    }

    public void updateinquiry_Answer(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        adminMapper.updateinquiry_Answer(userid, inquiryDto);
    }

    public void updateapikey(apikeyDto apikeydto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.updateapikey(apikeydto,userid);
    }

    public void update_comcode(comCodeDto comcodeDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.update_comcode(comcodeDto,userid);
    }
    public void update_needrequest(apiNeedRequestDto apineedRequestDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.update_needrequest(apineedRequestDto,userid);
    }

    public void update_needresponse(apiNeedResponseDto apineedResponseDto, String email){
        int userid = mainMapper.findbyemail(email);
        adminMapper.update_needresponse(apineedResponseDto,userid);
    }

}
