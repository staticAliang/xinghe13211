package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "wedding_list")
public class WeddingList {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private Integer price;

    private String type;
    
    private Integer icon;

    private Date addTime;
    
    private Integer playTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}
}