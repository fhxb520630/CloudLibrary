package com.example.cloudlibrary.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ButtonInfoTest {
    ButtonInfo buttonInfo;

    @Before
    public void setUp() {
        buttonInfo = new ButtonInfo(0.5,0.6,"test","0");
    }

    @Test
    public void getPosX() {
        if(Math.abs(0.5-buttonInfo.getPosX())<1e-5){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getPosY() {
        if(Math.abs(0.6-buttonInfo.getPosY())<1e-5){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getContent() {
        assertEquals("test",buttonInfo.getContent());
    }

    @Test
    public void setPosX() {
        buttonInfo.setPosX(0.3);
        if(Math.abs(0.3-buttonInfo.getPosX())<1e-5){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void setPosY() {
        buttonInfo.setPosY(0.4);
        if(Math.abs(0.4-buttonInfo.getPosY())<1e-5){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void setContent() {
        buttonInfo.setContent("test2");
        assertEquals("test2",buttonInfo.getContent());
    }

    @Test
    public void getOrder() {
        assertEquals("0",buttonInfo.getOrder());
    }

    @Test
    public void setOrder() {
        buttonInfo.setOrder("10");
        assertEquals("10",buttonInfo.getOrder());
    }
}