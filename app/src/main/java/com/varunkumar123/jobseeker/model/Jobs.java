package com.varunkumar123.jobseeker.model;

public class Jobs {
    private String jobid;
    private String jobTitle;
    private String experience;
    private String company;
    private String education;
    private String location;
    private String recruiterid;
    private String salary;
    private String status;
    private String jobskills;
    private String phoneNo;
    private String recruiterName;

    public Jobs() {

    }

    public String getJobskills() {
        return jobskills;
    }

    public void setJobskills(String jobskills) {
        this.jobskills = jobskills;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }

    public Jobs(String jobid, String jobTitle, String experience, String company, String education, String location, String recruiterid, String salary, String jobskills, String phoneNo, String recruiterName) {
        this.jobid = jobid;
        this.jobTitle = jobTitle;
        this.experience = experience;
        this.company = company;
        this.education = education;
        this.location = location;
        this.recruiterid = recruiterid;
        this.salary = salary;
        this.jobskills = jobskills;
        this.phoneNo = phoneNo;
        this.recruiterName = recruiterName;

    }


    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }



    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecruiterid() {
        return recruiterid;
    }

    public void setRecruiterid(String recruiterid) {
        this.recruiterid = recruiterid;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
