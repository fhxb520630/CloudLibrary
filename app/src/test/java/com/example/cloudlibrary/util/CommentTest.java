package com.example.cloudlibrary.util;

import com.example.cloudlibrary.CLApplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentTest {
    Comment comment;
    String tmp;

    @Before
    public void setUp() {
        comment = new Comment();
        tmp = "this is a title";
    }

    @Test
    public void setTitle() {
        comment.setTitle("test");
        assertEquals("test",comment.getTitle());
    }

    @Test
    public void setAuthor() {
        comment.setAuthor("test");
        assertEquals("test",comment.getAuthor());
    }

    @Test
    public void setDate() {
        comment.setDate("test");
        assertEquals("test",comment.getDate());
    }

    @Test
    public void setPower() {
        comment.setPower(1);
        assertEquals(1,comment.getPower());
    }

    @Test
    public void setVisited() {
        comment.setVisited(1);
        assertEquals(1,comment.getVisited());
    }

    @Test
    public void constructor1(){
        comment = new Comment(tmp);
        assertEquals(tmp,comment.getTitle());
        assertEquals("unknown",comment.getAuthor());
    }

    @Test
    public void constructor2(){
        comment = new Comment(tmp,new CLApplication());
    }
}