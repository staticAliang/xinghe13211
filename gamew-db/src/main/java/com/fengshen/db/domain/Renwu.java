package com.fengshen.db.domain;

import java.util.Date;

public class Renwu {
    private Integer id;

    private String uncontent;

    private String npcName;

    private String currentTask;

    private String showName;

    private String taskPrompt;

    private String reward;

    private String showReward;

    private String taskDesc;

    private Integer attrib;

    private Integer taskEndTime;

    private String taskState;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUncontent() {
        return uncontent;
    }

    public void setUncontent(String uncontent) {
        this.uncontent = uncontent;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public String getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getTaskPrompt() {
        return taskPrompt;
    }

    public void setTaskPrompt(String taskPrompt) {
        this.taskPrompt = taskPrompt;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getShowReward() {
        return showReward;
    }

    public void setShowReward(String showReward) {
        this.showReward = showReward;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Integer getAttrib() {
        return attrib;
    }

    public void setAttrib(Integer attrib) {
        this.attrib = attrib;
    }

    public Integer getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Integer taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
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
}