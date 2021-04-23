package com.example.cloudlibrary.util;

public class ButtonInfo {
    private double posX;
    private double posY;
    private String content;
    private String order;

    public ButtonInfo(double posX, double posY, String content,String order) {
        this.posX = posX;
        this.posY = posY;
        this.content = content;
        this.order = order;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public String getContent() {
        return content;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
