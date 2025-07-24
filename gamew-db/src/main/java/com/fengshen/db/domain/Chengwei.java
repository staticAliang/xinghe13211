package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="chengwei")
public class Chengwei {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;
	
	private String name;

    private Integer money;

    private Integer time;
    
    private String color;

    private Date addTime;

    private String attr;

    private Integer icon;

    private String remake;

    private String chtype;

    public String getChtype() {
        return chtype;
    }

    public void setChtype(String chtype) {
        this.chtype = chtype;
    }

    public Integer getIcon()
    {
        return icon;
    }

    public void setIcon(Integer i)
    {
        icon = i;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
}