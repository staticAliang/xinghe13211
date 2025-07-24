package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gold_stall_nine_goods")
public class GoldStallNineGoods {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

    private String name;

    private String goodsId;

    private Integer price;

    private Integer status;

    private String alias;

    private Integer reqLevel;

    private String gid;

    private Date updateTime;

    private Integer level;

    private Integer stallItemType;

    private String extra;

    private Integer itemPolar;

    private Integer cgPriceCount;

    private Integer unidentified;

    private Integer startTime;

    private Integer endTime;

    private Integer initPrice;

    private Integer flagNum;

    private Integer buyoutPrice;

    private Integer sellType;

    private String appointeeName;

    private Boolean deleted;

    private Date addTime;

    private String goods;
    
    private String master;

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStallItemType() {
        return stallItemType;
    }

    public void setStallItemType(Integer stallItemType) {
        this.stallItemType = stallItemType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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

    public Integer getUnidentified() {
        return unidentified;
    }

    public void setUnidentified(Integer unidentified) {
        this.unidentified = unidentified;
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

    public Integer getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Integer initPrice) {
        this.initPrice = initPrice;
    }

    public Integer getFlagNum() {
        return flagNum;
    }

    public void setFlagNum(Integer flagNum) {
        this.flagNum = flagNum;
    }

    public Integer getBuyoutPrice() {
        return buyoutPrice;
    }

    public void setBuyoutPrice(Integer buyoutPrice) {
        this.buyoutPrice = buyoutPrice;
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    public String getAppointeeName() {
        return appointeeName;
    }

    public void setAppointeeName(String appointeeName) {
        this.appointeeName = appointeeName;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
    
    
}