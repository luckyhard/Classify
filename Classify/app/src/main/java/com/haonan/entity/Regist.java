package com.haonan.entity;

/**
 * Created by lenovo on 2017/10/12.
 */

public class Regist {
    private String name;
    private String pwd;
    private String email;
    private String major;
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }

    public String getMajor() {
        return major;
    }

    public String getAddress() {
        return address;
    }
}
