package com.bnksys.esg.service;

import com.bnksys.esg.data.apiApplyDto;
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

    public List<apiApplyDto> findApi_ApplyLIST() {
        return adminMapper.findApi_ApplyLIST();
    }

    public void updateApi_ApplyStatus(apiApplyDto apiapplyDto){
        adminMapper.updateApi_ApplyStatus(apiapplyDto);
    }

    public void saveinquiry_Answer(String email, inQuiryDto inquiryDto){
        int userid = mainMapper.findbyemail(email);
        adminMapper.saveinquiry_Answer(userid, inquiryDto);
    }

    public void saveNotice(String noticenm, String noticecntn, int atchfileid){
        adminMapper.saveNotice(noticenm, noticecntn, atchfileid);
    }
}
