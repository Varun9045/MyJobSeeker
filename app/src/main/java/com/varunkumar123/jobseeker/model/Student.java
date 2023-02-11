package com.varunkumar123.jobseeker.model;

public class Student {
    private String studentid;
    private String Name;
    private String mobile;
    private String dateofbirth;
    String student_location;
    private String experience;
    private String qualification;
    private String yearofpassing;
    private String Email;
    private String profileImg;
    private String skills;

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }



    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Student(String studentid, String name, String mobile, String dateofbirth,String student_location, String experience, String qualification, String yearofpassing, String email,String profileImg,String skills) {
        this.studentid = studentid;
        Name = name;
        this.mobile = mobile;
        this.dateofbirth = dateofbirth;
        this.experience = experience;
        this.qualification = qualification;
        this.yearofpassing = yearofpassing;
        Email = email;
        this.profileImg = profileImg;
        this.skills = skills;
        this.student_location = student_location;
    }

    public Student() {

    }



    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getYearofpassing() {
        return yearofpassing;
    }

    public void setYearofpassing(String yearofpassing) {
        this.yearofpassing = yearofpassing;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getStudent_location() {
        return student_location;
    }

    public void setStudent_location(String student_location) {
        this.student_location = student_location;
    }
}
