package com.example.cloudlibrary;

import android.app.Application;
import com.example.cloudlibrary.util.StudentInfo;

import android.os.Handler;

public class CLApplication extends Application {
    private int nowInfo;
    private String userName;
    private StudentInfo studentInfo;
    private Handler handler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        nowInfo=0;
        userName="未登录";
        studentInfo=null;
    }

    public int getNowInfo(){
        return nowInfo;
    }
    public String getUserName(){
        return userName;
    }
    public StudentInfo getStudentInfo(){
        return studentInfo;
    }
    public Handler getHandler(){
        return handler;
    }
    public void setNowInfo(int n){
        nowInfo = n;
    }
    public void setUserName(String u){
        userName = u;
    }
    public void setStudentInfo(StudentInfo s){
        studentInfo = s;
    }
    public void setHandler(Handler h){
        handler = h;
    }

}
