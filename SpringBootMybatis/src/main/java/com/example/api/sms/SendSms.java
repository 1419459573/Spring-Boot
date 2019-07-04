package com.example.api.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
@Component
public class SendSms {

    @Value("${msg.accessKeyId}")
    private String accessKeyId;

    @Value("${msg.accessKeySecret}")
    private String accessKeySecret;

    @Value("${msg.signName}")
    private String signName;

    @Value("${msg.templateCode}")
    private String templateCode;

    @Value("${msg.regionId}")
    private String regionId;

    @Value("${msg.domain}")
    private String domain;

    @Value("${msg.version}")
    private String version;

    @Value("${msg.action}")
    private String action;

    public Map<String,Object> sendSms(String phoneNum) throws ClientException {
        Map map = new HashMap();
        String appCode = getAppCode();
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam",packageAppCode("1234"));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return map;
    }


    private String packageAppCode(String code) {
        return "{\"code\":\"" + (code.toString()) + "\"}";
    }

    /**
     * 获取随机4位验证码
     * @return
     */
    public static String getAppCode() {
        int n = 4;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }
}
