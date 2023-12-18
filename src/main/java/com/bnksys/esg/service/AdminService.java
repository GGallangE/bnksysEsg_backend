package com.bnksys.esg.service;

import com.bnksys.esg.data.apiApplyDto;
import com.bnksys.esg.data.apiResultDto;
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

    }public List<apiResultDto> findApiList(Integer apilistid, int page, int pageSize) {
        int offset = page * pageSize;
        return adminMapper.findApiList(apilistid, offset, pageSize);
    }

    public void updateApi_ApplyStatus(apiApplyDto apiapplyDto){
        adminMapper.updateApi_ApplyStatus(apiapplyDto);
    }

    public void saveinquiry_Answer(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveinquiry_Answer(userid, inquiryDto);
    }

    public void saveApiList(apiResultDto apiresultDto){
        adminMapper.saveApiList(apiresultDto);
    }

    public void updateApiList(apiResultDto apiresultDto){
        adminMapper.updateApiList(apiresultDto);
    }

    public void updateinquiry_Answer(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        adminMapper.updateinquiry_Answer(userid, inquiryDto);
    }

    public void saveNotice(String noticenm, String noticecntn, int atchfileid){
        adminMapper.saveNotice(noticenm, noticecntn, atchfileid);
    }
}
