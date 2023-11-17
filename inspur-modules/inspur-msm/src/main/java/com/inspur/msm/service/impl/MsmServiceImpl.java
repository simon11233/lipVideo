package com.inspur.msm.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.JsonObject;
import com.inspur.core.utils.StringUtils;
import com.inspur.msm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phoneNumber, String templeCode, Map<String, Object> params) {
        if(StringUtils.isEmpty(phoneNumber)) {
            return false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI5tEUs31wUpgVujdLCFmw",
                "sRSuMGmjWgcCOj6gae4igIUIdIKlz2");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("sendSms");

        request.putQueryParameter("PhoneNumbers",phoneNumber);
        request.putQueryParameter("SignName","Simon在线教育网站");
        request.putQueryParameter("TemplateCode",templeCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
