package com.example.cloudlibrary.util;

public class StudentInfo {
    private String email;
    private String stuNumber;
    private String faculty;

    public StudentInfo(String email, String stuNumber, String faculty) {
        this.email = email;
        this.stuNumber = stuNumber;
        this.faculty = faculty;
    }

    public String getEmail() {
        return email;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
