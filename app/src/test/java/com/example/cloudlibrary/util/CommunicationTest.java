package com.example.cloudlibrary.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class CommunicationTest {
    Communication communication;
    String musicLibrary = "音乐图书馆";
    String c = "context";

    private Comment getFirstComment(){
        JSONArray jsonArray = new Communication().getIndex(0,1);
        Comment temp = null;
        try {
            temp = new Comment(jsonArray.getJSONObject(0).get("text").toString());
            temp.setDate(jsonArray.getJSONObject(0).get("time").toString().replace("T"," "));
            temp.setAuthor(jsonArray.getJSONObject(0).get("name").toString());
        } catch (JSONException e) {
            Log.d(c, String.valueOf(e));
        }
        return temp;
    }

    @Before
    public void setUp() {
        communication = new Communication();
    }

    @Test
    public void getTestCode() {
        assertEquals(204,communication.getTestCode());
    }

    @Test
    public void addComment() {
        Comment comment = new Comment();
        comment.setTitle("a comment for testing");
        comment.setAuthor("Unit_Test");
        communication.addComment(comment);
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void deleteComment(){
        addComment();
        communication.deleteComment(getFirstComment());
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void addReply(){
        addComment();
        Comment comment = getFirstComment();
        Comment reply = new Comment("a reply for testing");
        reply.setAuthor("Unit_Test's_best_friend");
        communication.addReply(comment,reply,"0");
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void deleteReply(){
        addReply();
        Comment reply = new Comment("a reply for testing");
        reply.setAuthor("Unit_Test's_best_friend");
        communication.deleteReply(reply);
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void confirmAdmin(){
        //成功的情况
        boolean result = communication.confirmAdmin("1@1.com","2000214cxz");
        if(communication.getTestCode() != 204 && result){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
        //失败的情况
        result = communication.confirmAdmin("swsderf","defnriftg");
        if(communication.getTestCode() != 204 && !result){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getAuthorReply(){
        addComment();
        Comment comment = getFirstComment();
        Comment reply = new Comment("来自管理员的回复");
        reply.setAuthor("管理员大人");
        communication.addReply(comment,reply,"1");
        JSONArray jsonArray = communication.getAuthorReply("Unit_Test");
        try {
            Comment temp = new Comment(jsonArray.getJSONObject(0).get("text").toString());
            temp.setDate(jsonArray.getJSONObject(0).get("time").toString().replace("T"," "));
            temp.setAuthor(jsonArray.getJSONObject(0).get("name").toString());
            assertEquals("管理员大人",temp.getAuthor());
            assertEquals("来自管理员的回复",temp.getTitle());
        } catch (JSONException e) {
            Log.d(c, String.valueOf(e));
        }
    }

    @Test
    public void getIndexNews(){
        JSONArray j = communication.getIndexNews(0,1);
        assertNotNull(j);
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getVisited(){
        communication.getVisited(musicLibrary);
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getNewsUrl(){
        communication.getNewsUrl("调查问卷");
        if(communication.getTestCode() != 204){
            assertEquals(1,1);
        }else{
            assertEquals(1,2);
        }
    }

    @Test
    public void getInfomation(){
        try{
            communication.getInformation(musicLibrary);
        }catch (Exception e){
            Log.d(c, String.valueOf(e));
        }
        assertNotEquals(204,communication.getTestCode());
    }

    @Test
    public void getFloorPoint(){
        communication.getFloorPoint("1","西馆");
        assertNotEquals(204,communication.getTestCode());
    }

    @Test
    public void getFloorImage(){
        String url = null;
        try{
            url = communication.getFloorImage("5","北馆");
        }catch (Exception e){
            Log.d(c, String.valueOf(e));
        }
        assertNotEquals(204,communication.getTestCode());
        assertNotNull(url);
        assertNotEquals("",url);
    }

    @Test
    public void getFloorFullImage(){
        String url = null;
        try{
            url = communication.getFloorFullImage("北馆","5");
        }catch (Exception e){
            Log.d(c, String.valueOf(e));
        }
        assertNotEquals(204,communication.getTestCode());
        assertNotNull(url);
        assertNotEquals("",url);
    }

    @Test
    public void getWelcomeImage(){
        String url = null;
        try{
            url = communication.getWelcomeImage();
        }catch (Exception e){
            Log.d(c, String.valueOf(e));
        }
        assertNotEquals(204,communication.getTestCode());
        assertNotNull(url);
        assertNotEquals("",url);
    }

    @Test
    public void addGrade(){
        communication.addGrade("5.0");
        assertNotEquals(204,communication.getTestCode());
    }

    @Test
    public void addVisited(){
        communication.addVisited(musicLibrary);
        assertNotEquals(204,communication.getTestCode());
    }

    @Test
    public void getGrade(){
        JSONArray j = communication.getGrade();
        assertNotEquals(204,communication.getTestCode());
        assertNotNull(j);
    }
}