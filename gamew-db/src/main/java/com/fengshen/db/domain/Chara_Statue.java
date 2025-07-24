package com.fengshen.db.domain;

import java.util.Date;

public class Chara_Statue {
    private Integer id;

    private String serverid;

    private String npcName;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String data;
    
    public Chara_Statue(Integer id, String serverid, String npcName, Date addTime, Date updateTime, Boolean deleted) {
        this.id = id;
        this.serverid = serverid;
        this.npcName = npcName;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }

    public Chara_Statue(Integer id, String serverid, String npcName, Date addTime, Date updateTime, Boolean deleted, String data) {
        this.id = id;
        this.serverid = serverid;
        this.npcName = npcName;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
        this.data = data;
    }

    public Chara_Statue() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid == null ? null : serverid.trim();
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName == null ? null : npcName.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}