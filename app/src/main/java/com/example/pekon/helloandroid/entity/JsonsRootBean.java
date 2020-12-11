package com.example.pekon.helloandroid.entity;

import com.google.gson.annotations.SerializedName;
/**
 * Auto-generated: 2020-12-11 17:49:3
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class JsonsRootBean {

    private Data data;
    @SerializedName("errorCode")
    private int errorcode;
    @SerializedName("errorMsg")
    private String errormsg;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setErrorcode(int errorcode) {
         this.errorcode = errorcode;
     }
     public int getErrorcode() {
         return errorcode;
     }

    public void setErrormsg(String errormsg) {
         this.errormsg = errormsg;
     }
     public String getErrormsg() {
         return errormsg;
     }

}