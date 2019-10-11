package com.example.api.test;

public class Student {

    private String name;//姓名
    private Integer age;//年龄
    private int sex;//性别
    private String professional;//专业

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getProfessional() {
        return professional;
    }
    public void setProfessional(String professional) {
        this.professional = professional;
    }
    public Student(String name, Integer age, int sex, String professional) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.professional = professional;
    }


}