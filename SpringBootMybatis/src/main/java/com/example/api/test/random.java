package com.example.api.test;

import org.apache.commons.lang3.RandomStringUtils;

public class random {

    public static void main(String[] args) {
        String filename= RandomStringUtils.randomAlphanumeric(6);
        System.out.println(filename);
    }
}
