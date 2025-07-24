package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="stall_record")
public class StallRecord {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

	private String gid;

    private Integer cid;
    
    private String ownerName;

    private Integer status;

    private Integer stallRecordType;

    private String goodsUuid;

    private String goodsName;

    private Integer itemType;

    private Date endTime;

    private Integer price;

    private Integer reqLevel;

    private Integer itemPolar;

    private Integer buyType;

    private Integer level;

    private Date addTime;

    private String data;
    
    private Integer type;
    
    private String buyName;
    
    private String buyGid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStallRecordType() {
		return stallRecordType;
	}

	public void setStallRecordType(Integer stallRecordType) {
		this.stallRecordType = stallRecordType;
	}

	public String getGoodsUuid() {
        return goodsUuid;
    }

    public void setGoodsUuid(String goodsUuid) {
        this.goodsUuid = goodsUuid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getItemPolar() {
        return itemPolar;
    }

    public void setItemPolar(Integer itemPolar) {
        this.itemPolar = itemPolar;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public String getBuyGid() {
		return buyGid;
	}

	public void setBuyGid(String buyGid) {
		this.buyGid = buyGid;
	}
	
}