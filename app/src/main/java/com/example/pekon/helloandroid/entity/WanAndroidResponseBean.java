/**
  * Copyright 2020 bejson.com 
  */
package com.example.pekon.helloandroid.entity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Auto-generated: 2020-12-11 16:48:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WanAndroidResponseBean {

    private List<Blog> data;
    private int errorCode;
    private String errorMsg;
    public void setData(List<Blog> data) {
         this.data = data;
     }
     public List<Blog> getData() {
         return data;
     }

    public void setErrorCode(int errorCode) {
         this.errorCode = errorCode;
     }
     public int getErrorCode() {
         return errorCode;
     }

    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
     }
     public String getErrorMsg() {
         return errorMsg;
     }


    @Override
    public String toString() {
        return "WanAndroidResponseBean{" +
                "data=" + new Gson().toJson(data) +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}