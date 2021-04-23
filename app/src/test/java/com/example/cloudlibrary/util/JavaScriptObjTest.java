package com.example.cloudlibrary.util;

import com.example.cloudlibrary.CLApplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JavaScriptObjTest {
    JavaScriptObj javaScriptObj;
    CLApplication clApplication;

    @Before
    public void setUp() {
        clApplication = new CLApplication();
        javaScriptObj = new JavaScriptObj(clApplication);
    }

    @Test
    public void onSignout() {
        javaScriptObj.onSignout("");
        assertEquals("未登录",clApplication.getUserName());
        assertEquals(Power.POWER_VISITOR,clApplication.getNowInfo());
        assertNull(clApplication.getStudentInfo());
    }

    @Test
    public void onSignin() {
        String html = "<ul><li>email: 2018011284@secoder.net</li><li>department: 电脑系</li>";
        javaScriptObj.onSignin(html);
        assertEquals("2018011284",clApplication.getUserName());
        assertEquals(Power.POWER_STUDENT,clApplication.getNowInfo());
        StudentInfo s = clApplication.getStudentInfo();
        assertEquals("2018011284@secoder.net",s.getEmail());
        assertEquals("2018011284",s.getStuNumber());
        assertEquals("电脑系",s.getFaculty());
    }
}