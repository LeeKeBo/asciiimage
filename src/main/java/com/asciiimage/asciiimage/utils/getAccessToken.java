package com.asciiimage.asciiimage.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class getAccessToken {
    @Value("${AppSecret}")
    private String appSecret;
    @Value("${AppID}")
    private String appId;
    private static String accessToken;


    /**
     * @throws Exception
     * 一个半小时执行一次获取 AccessToken
     */
    @Scheduled(fixedDelay = 1500*3600)
    public void getAccessTokenOperation() throws Exception {
        // 判断accessToken是否过期
        String urlToVisit = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appId + "&secret=" + appSecret;
        System.out.println(urlToVisit);
        RestTemplate restTemplate = new RestTemplate();
        AccessToken newAccessToken = restTemplate.getForObject(urlToVisit, AccessToken.class);
        if(newAccessToken.getErrcode() == null || newAccessToken.getErrcode().equals("0")){
            accessToken = newAccessToken.getAccess_token();
        }
        else{
            System.out.println("error:"+newAccessToken.getErrmsg());
        }
        System.out.println(accessToken);
    }

    public static String getAccessTokenFunc(){
        return accessToken;
    }


}
