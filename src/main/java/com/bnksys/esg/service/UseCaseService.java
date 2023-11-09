package com.bnksys.esg.service;

import com.bnksys.esg.data.apiResultDto;
import com.bnksys.esg.data.getuseCaseDto;
import com.bnksys.esg.data.useCaseDto;
import com.bnksys.esg.mapper.MainMapper;
import com.bnksys.esg.mapper.UseCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseCaseService {

    UseCaseMapper useCaseMapper;

    MainMapper mainMapper;

    @Autowired
    public UseCaseService(UseCaseMapper useCaseMapper, MainMapper mainMapper){
        this.useCaseMapper = useCaseMapper;
        this.mainMapper = mainMapper;
    }

    public List<getuseCaseDto> findUseCase_apiDetail(int apilistid){return useCaseMapper.findUseCase_apiDetail(apilistid); }

    public List<getuseCaseDto> findUseCase_usecaseDetail(int usecaseid){return useCaseMapper.findUseCase_usecaseDetail(usecaseid); }

    public List<apiResultDto> findUseCase_usecaseDetail_apiList(int usecaseid){return useCaseMapper.findUseCase_usecaseDetail_apiList(usecaseid); }


    public List<getuseCaseDto> findUseCase_usecaseMain(String searchname){return useCaseMapper.findUseCase_usecaseMain(searchname); }

    public void saveUseCase(String email, useCaseDto usecaseDto){
        int userId = mainMapper.findbyemail(email);
        int usecaseId = useCaseMapper.maxUseCaseId();
        useCaseMapper.saveUseCase(userId,usecaseId,usecaseDto);

        if(null != usecaseDto.getApilistid()){
            int[] apilistIds = usecaseDto.getApilistid();

            for(int apilistid : apilistIds) {
                useCaseMapper.saveUseCaseApiList(usecaseId, apilistid);
            }
        }
    }
}
