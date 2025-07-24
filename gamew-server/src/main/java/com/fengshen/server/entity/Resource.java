package com.fengshen.server.entity;

public class Resource
{
    private Object data;
    private int status;
    private String msg;
    private Long count;
    private int pageNo;
    private int last;
    private int pageSize;
    
    public Object getData() {
        return this.data;
    }
    
    public void setData(final Object data) {
        this.data = data;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public Long getCount() {
        return this.count;
    }
    
    public void setCount(final Long count) {
        this.count = count;
    }
    
    public int getPageNo() {
        return this.pageNo;
    }
    
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
    }
    
    public int getLast() {
        return this.last;
    }
    
    public void setLast(final int last) {
        this.last = last;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }
}
