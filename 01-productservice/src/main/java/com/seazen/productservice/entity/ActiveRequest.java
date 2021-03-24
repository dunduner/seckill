package com.seazen.productservice.entity;


/**
 * Created by lfx on 2018/12/19.
 */
public class ActiveRequest {

    private String sid;

    private String biz;

    private String text;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ActiveRequest{" +
                "sid='" + sid + '\'' +
                ", biz='" + biz + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}