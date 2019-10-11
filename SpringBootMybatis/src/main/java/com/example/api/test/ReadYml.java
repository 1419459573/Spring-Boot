package com.example.api.test;

import org.springframework.beans.factory.annotation.Value;

public class ReadYml {

    @Value("${appStoreList}")
    private String appStoreList;

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
