package com.fengshen.db.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="mailbox_refresh")
public class MailboxRefresh {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer count;

    private String gid;
    
    private String toGid;

    private Integer type;

    private String sender;

    private String title;

    private String msg;

    private String attachment;

    private Integer status;
    
    private Integer isGetReward;

    private Integer createTime;

    private Integer expiredTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getToGid() {
		return toGid;
	}

	public void setToGid(String toGid) {
		this.toGid = toGid;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

	public Integer getIsGetReward() {
		return isGetReward;
	}

	public void setIsGetReward(Integer isGetReward) {
		this.isGetReward = isGetReward;
	}
}