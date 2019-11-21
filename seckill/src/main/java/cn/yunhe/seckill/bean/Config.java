package cn.yunhe.seckill.bean;

import java.io.Serializable;


/**
 *
 *
 *
 */
@SuppressWarnings("serial")
public class Config implements Serializable {

    /**
     *
     */
    private String key;

    /**
     *
     */
    private String value;

    /**
     *
     */
    private java.util.Date createTime;

    /**
     *
     */
    private java.util.Date updateTime;


    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
    }

    public void setUpdateTime(java.util.Date updateTime){
        this.updateTime = updateTime;
    }

    public java.util.Date getUpdateTime(){
        return this.updateTime;
    }

    @Override
    public String toString (){
        return "key:"+(key == null ? "空" : key)+"，value:"+(value == null ? "空" : value)+"，createTime:"+(createTime == null ? "空" : createTime)+"，updateTime:"+(updateTime == null ? "空" : updateTime);
    }
}