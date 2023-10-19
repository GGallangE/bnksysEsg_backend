package com.bnksys.esg.service;

import com.bnksys.esg.data.Member;
import com.bnksys.esg.data.userboard;
import com.bnksys.esg.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class MainService {

    @Value("${elc.url}")
    private String url;

    @Value("${elc.serviceKey}")
    private String serviceKey;
    MainMapper mainMapper;

    @Autowired
    public MainService(MainMapper mainMapper){
        this.mainMapper = mainMapper;
    }

    public List<Member> getAllUser() {
        return mainMapper.getAllUser();
    }

    public List<userboard> getUserWithBoards(){
        return mainMapper.getUserWithBoards();
    }

    public String electronicApi(String sigunguCd, String bjdongCd, String bun, String ji, String useYm, String numOfRows, String pageNo) {
        // url직접 설정, 만들어 주는거 사용 시 incoding문제로 url이 달라짐
        String fullUrl = url + "?serviceKey=" + serviceKey
                + "&sigunguCd=" + sigunguCd
                + "&bjdongCd=" + bjdongCd
                + "&bun=" + bun
                + "&ji=" + ji
                + "&useYm=" + useYm;

        // 선택 값들은 값이 있을시 url에 추가
        if (numOfRows != null) {
            fullUrl += "&numOfRows=" + numOfRows;
        }

        if (pageNo != null) {
            fullUrl += "&pageNo=" + pageNo;
        }

        // 공공 데이터 url 문제로 한번 더 거쳐줌
        RestTemplate restTemplate = new RestTemplate();
        URI fullUrl2;
        try {
            fullUrl2 = new URI(fullUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "Error: URI 생성 오류";
        }

        String apiResponse = restTemplate.getForObject(fullUrl2, String.class);
        return apiResponse;
    }

}
