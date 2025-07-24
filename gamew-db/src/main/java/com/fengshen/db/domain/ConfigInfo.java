package com.fengshen.db.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="config_info")
public class ConfigInfo {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String uuid;

    private String keyName;
    
    private String aliasName;

    private Date addTime;

    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public ConfigInfo(Integer id,String key, Date addTime, String data) {
		super();
		this.id = id;
		this.uuid = UUID.randomUUID().toString().replace("-", "");
		this.keyName = key;
		this.addTime = addTime;
		this.data = data;
	}

	public ConfigInfo() {
		super();
	}
}