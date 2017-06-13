package com.example.kanxuan.baidumap.Http;

/**
 * Created by kanxuan on 2017/6/9.
 */

public class UserLoginResponse {
    private int id;
    private String role;
    private String company;

    public void setCompany(String company) {
        this.company = company;
    }

    void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getCompany() {
        return company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
