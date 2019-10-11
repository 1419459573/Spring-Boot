package com.example.api.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<Student> allList = new ArrayList<Student>();

        Student st1 = new Student("小王",26,1,"计算机");
        allList.add(st1);
        Student st2 = new Student("小张",21,1,"电气");
        allList.add(st2);
        Student st3 = new Student("小红",22,1,"人文");
        allList.add(st3);
        Student st4 = new Student("小李",23,1,"计算机");
        allList.add(st4);

        System.out.println("**************************目的：只取专业为计算机行业的集合*************************");
        allList = allList.stream().filter(Student -> Student.getProfessional().equals("计算机")).collect(Collectors.toList());
        allList.forEach(Student -> {
            System.out.println(Student.getName());
        });
//        System.out.println("**************************目的：取专业为计算机和人文专业的集合*************************");
//        List<String> str=new ArrayList<>();
//        str.add("计算机");
//        str.add("人文");
//        allList = allList.stream().filter(Student -> str.contains(Student.getProfessional())).collect(Collectors.toList());
//        allList.forEach(Student -> {
//            System.out.println(Student.getName());
//        });

    }
}
