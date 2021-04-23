package com.example.cloudlibrary;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import junit.framework.TestCase;

import java.io.IOException;

public class UiTest extends TestCase {

    public void testA() throws UiObjectNotFoundException {
        // 获取设备对象
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);
        try {
            uiDevice.executeShellCommand("pm grant com.example.cloudlibrary android.permission.READ_EXTERNAL_STORAGE");
            uiDevice.executeShellCommand("pm grant com.example.cloudlibrary android.permission.WRITE_EXTERNAL_STORAGE");
        } catch (IOException e) {
            Log.d("context", String.valueOf(e));
        }
        // 获取上下文
        Context context = instrumentation.getContext();

        // 启动测试App
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.example.cloudlibrary");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        String resourceId = "com.example.cloudlibrary:id/tv_welcome";
        UiObject tvWelcome = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        tvWelcome.click();
        uiDevice.pressBack();

        resourceId = "com.example.cloudlibrary:id/fragment_home_base_select0";
        UiObject fragmentHomeBaseSelect0 = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        fragmentHomeBaseSelect0.click();
        resourceId = "com.example.cloudlibrary:id/selector_30";
        UiObject selector30 = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        selector30.click();
        resourceId = "com.example.cloudlibrary:id/fragment_floor_full";
        UiObject buttonFloorFull = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        buttonFloorFull.click();
        uiDevice.pressBack();
        uiDevice.pressBack();
        uiDevice.pressBack();
        resourceId = "com.example.cloudlibrary:id/fragment_home_base_select1";
        UiObject fragmentHomeBaseSelect1 = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        fragmentHomeBaseSelect1.click();
        resourceId = "com.example.cloudlibrary:id/selector_61";
        UiObject selector61 = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        selector61.click();
        buttonFloorFull.click();
        uiDevice.pressBack();
        uiDevice.pressBack();
        uiDevice.pressBack();
        uiDevice.click(300,uiDevice.getDisplayHeight()-1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }
        uiDevice.click(300,uiDevice.getDisplayHeight()/2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }
        uiDevice.pressBack();
        resourceId = "com.example.cloudlibrary:id/news_list";
        UiObject newsList = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        newsList.swipeDown(10);
        newsList.swipeUp(10);
        uiDevice.click(700,uiDevice.getDisplayHeight()-1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }
        resourceId = "com.example.cloudlibrary:id/discussion_list";
        UiObject discussionList = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        discussionList.swipeDown(10);
        discussionList.swipeUp(10);
        uiDevice.click(1000,uiDevice.getDisplayHeight()-1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }
        resourceId = "com.example.cloudlibrary:id/fab8";
        UiObject fab8 = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        fab8.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.d("context", String.valueOf(e));
        }


        // 点击设备返回按钮
        uiDevice.pressBack();

    }

}
