package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sale_good")
public class SaleGood {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String goodsId;

    private String name;
    
    private String alias;

    private Integer price;

    private Integer reqLevel;

    private String gid;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private Integer level;

    private Integer type;

    private String extra;

    private Integer status;

    private Integer startTime;

    private Integer endTime;

    private String goods;
    
    private Integer icon;
    
    private Integer unidentified;
    
    private Integer itemPolar;
    
    private Integer cgPriceCount;
    
    private Integer sgId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public Integer getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(Integer reqLevel) {
        this.reqLevel = reqLevel;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public Integer getUnidentified() {
		return unidentified;
	}

	public void setUnidentified(Integer unidentified) {
		this.unidentified = unidentified;
	}

	public Integer getItemPolar() {
		return itemPolar;
	}

	public void setItemPolar(Integer itemPolar) {
		this.itemPolar = itemPolar;
	}

	public Integer getCgPriceCount() {
		return cgPriceCount;
	}

	public void setCgPriceCount(Integer cgPriceCount) {
		this.cgPriceCount = cgPriceCount;
	}

	public Integer getSgId() {
		return sgId;
	}

	public void setSgId(Integer sgId) {
		this.sgId = sgId;
	}
	
}