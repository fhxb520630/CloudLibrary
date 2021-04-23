package com.example.cloudlibrary.util;

import android.os.Message;
import android.webkit.JavascriptInterface;

import com.example.cloudlibrary.CLApplication;

public final class JavaScriptObj {
    private CLApplication clApplication;

    public JavaScriptObj(CLApplication c){
        clApplication=c;
    }

    @JavascriptInterface
    public void onSignout(String html){
        clApplication.setNowInfo(Power.POWER_VISITOR);
        clApplication.setUserName("未登录");
        clApplication.setStudentInfo(null);
        if(clApplication.getHandler() != null){
            Message msg=new Message();
            msg.what=10;
            clApplication.getHandler().sendMessage(msg);
        }
    }

    @JavascriptInterface
    public void onSignin(String html){
        html=html.substring(html.indexOf("<ul>"));
        html=html.substring(html.indexOf("email")+7);
        String email=html.substring(0,html.indexOf('<'));
        String stuNumber=email.substring(0,email.indexOf('@'));
        html=html.substring(html.indexOf("department")+12);
        String faculty=html.substring(0,html.indexOf('<'));
        clApplication.setNowInfo(Power.POWER_STUDENT);
        clApplication.setUserName(stuNumber);
        clApplication.setStudentInfo(new StudentInfo(email,stuNumber,faculty));
        if(clApplication.getHandler() != null){
            Message msg=new Message();
            msg.what=10;
            clApplication.getHandler().sendMessage(msg);
        }
    }
}
