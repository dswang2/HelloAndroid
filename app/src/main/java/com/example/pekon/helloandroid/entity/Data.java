package com.example.pekon.helloandroid.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Auto-generated: 2020-12-11 17:49:3
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Data {

    @SerializedName("curPage")
    private long curpage;
    private List<Datas> datas;
    private long offset;
    private boolean over;
    @SerializedName("pageCount")
    private long pagecount;
    private long size;
    private long total;
    public void setCurpage(long curpage) {
         this.curpage = curpage;
     }
     public long getCurpage() {
         return curpage;
     }

    public void setDatas(List<Datas> datas) {
         this.datas = datas;
     }
     public List<Datas> getDatas() {
         return datas;
     }

    public void setOffset(long offset) {
         this.offset = offset;
     }
     public long getOffset() {
         return offset;
     }

    public void setOver(boolean over) {
         this.over = over;
     }
     public boolean getOver() {
         return over;
     }

    public void setPagecount(long pagecount) {
         this.pagecount = pagecount;
     }
     public long getPagecount() {
         return pagecount;
     }

    public void setSize(long size) {
         this.size = size;
     }
     public long getSize() {
         return size;
     }

    public void setTotal(long total) {
         this.total = total;
     }
     public long getTotal() {
         return total;
     }

}