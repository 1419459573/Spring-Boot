package com.example.api.sms;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class testSms {

    @Autowired
    private SendSms sendSms;


    @RequestMapping("/config")
    public Map<String,Object> testConfig() {
        String  ph = "18437931075";
        Map<String,Object> ma=new HashMap<>();
        try {
            ma=sendSms.sendSms(ph);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return ma;

    }
}
