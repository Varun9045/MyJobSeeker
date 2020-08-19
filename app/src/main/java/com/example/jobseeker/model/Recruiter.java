package com.example.jobseeker.model;

public class Recruiter {
    private String name;
    private String email;
    private String phoneNo;
    private String user_id;
    private String address;
    private String profile_img;
    private String profile_title;
    private String company;
    private String fuctional_area;



    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getProfile_title() {
        return profile_title;
    }

    public void setProfile_title(String profile_title) {
        this.profile_title = profile_title;
    }

    public String getFuctional_area() {
        return fuctional_area;
    }

    public void setFuctional_area(String fuctional_area) {
        this.fuctional_area = fuctional_area;
    }

    public Recruiter(String name, String email, String phoneNo, String user_id, String address, String profile_img, String profile_title, String company, String fuctional_area) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.user_id = user_id;
        this.address = address;
        this.profile_img = profile_img;
        this.profile_title = profile_title;
        this.company = company;
        this.fuctional_area = fuctional_area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

