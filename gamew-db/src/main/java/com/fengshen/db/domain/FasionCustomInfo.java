package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="fasion_custom_info")
public class FasionCustomInfo {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer equipPos;

    private Integer fasionPart;

    private Integer fasionDye;

    private String name;

    private Integer gift;

    private Integer icon;

    private Integer goodsPrice;

    private Integer sex;

    private Integer position;

    private Integer category;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipPos() {
		return equipPos;
	}

	public void setEquipPos(Integer equipPos) {
		this.equipPos = equipPos;
	}

	public Integer getFasionPart() {
		return fasionPart;
	}

	public void setFasionPart(Integer fasionPart) {
		this.fasionPart = fasionPart;
	}

	public Integer getFasionDye() {
		return fasionDye;
	}

	public void setFasionDye(Integer fasionDye) {
		this.fasionDye = fasionDye;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGift() {
        return gift;
    }

    public void setGift(Integer gift) {
        this.gift = gift;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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