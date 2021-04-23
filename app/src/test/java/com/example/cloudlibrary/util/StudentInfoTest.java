package com.example.cloudlibrary.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentInfoTest {
    StudentInfo studentInfo;

    @Before
    public void setUp() throws Exception {
        studentInfo = new StudentInfo("1@1.com","12345678","电脑系");
    }

    @Test
    public void getEmail() {
        assertEquals("1@1.com",studentInfo.getEmail());
    }

    @Test
    public void getStuNumber() {
        assertEquals("12345678",studentInfo.getStuNumber());
    }

    @Test
    public void getFaculty() {
        assertEquals("电脑系",studentInfo.getFaculty());
    }

    @Test
    public void setEmail() {
        String test = "2@2.com";
        studentInfo.setEmail(test);
        assertEquals(test,studentInfo.getEmail());
    }

    @Test
    public void setStuNumber() {
        String test = "87654321";
        studentInfo.setStuNumber(test);
        assertEquals(test,studentInfo.getStuNumber());
    }

    @Test
    public void setFaculty() {
        String test = "数学系";
        studentInfo.setFaculty(test);
        assertEquals(test,studentInfo.getFaculty());
    }
}