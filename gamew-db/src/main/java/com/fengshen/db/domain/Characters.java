package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "characters")
public class Characters {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer polar;

	private String name;

	private Integer sex;

	private Integer chargeScore;

	private Integer mapId;

	private String mapName;

	private Integer x;
	
	private Integer y;
	
	private Integer level;
	
	private Integer goldCoin;

	private Integer accountId;

	private Date addTime;

	private Date updateTime;

	private Boolean deleted;

	private String gid;

	private Integer online;

	private String data;

	private String cangku;

	private String texiao;

	private String genchong;

	private String backpack;

	// 宠物仓库
	private String petStore;

	private String listshouhu;

	private String shizhuang;

	private String cardStore;

	private String customShizhuang;
	//太阴之气
	private String tyzqStore;

	private Integer lastLoginTime;

	// 封号
	private Integer block;
	// 问道小子
	private Integer xiaozi;

	private String lastLoginIp;
	
	private Integer portrait;
	
	private Integer monthTao;
	
	private Integer shut;
	
	private String fixedTeamName;
	
	private Integer ctDataScore;
	
	private Integer ctDataTopRank;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPolar() {
		return polar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCangku() {
		return cangku;
	}

	public void setCangku(String cangku) {
		this.cangku = cangku;
	}

	public String getTexiao() {
		return texiao;
	}

	public void setTexiao(String texiao) {
		this.texiao = texiao;
	}

	public String getGenchong() {
		return genchong;
	}

	public void setGenchong(String genchong) {
		this.genchong = genchong;
	}

	public String getBackpack() {
		return backpack;
	}

	public void setBackpack(String backpack) {
		this.backpack = backpack;
	}

	public String getPetStore() {
		return petStore;
	}

	public void setPetStore(String petStore) {
		this.petStore = petStore;
	}

	public void setPolar(Integer polar) {
		this.polar = polar;
	}

	public String getListshouhu() {
		return listshouhu;
	}

	public void setListshouhu(String listshouhu) {
		this.listshouhu = listshouhu;
	}

	public String getShizhuang() {
		return shizhuang;
	}

	public void setShizhuang(String shizhuang) {
		this.shizhuang = shizhuang;
	}

	public String getCardStore() {
		return cardStore;
	}

	public void setCardStore(String cardStore) {
		this.cardStore = cardStore;
	}

	public Integer getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getBlock() {
		return block;
	}

	public void setBlock(Integer block) {
		this.block = block;
	}

	public Integer getXiaozi() {
		return xiaozi;
	}

	public void setXiaozi(Integer xiaozi) {
		this.xiaozi = xiaozi;
	}

	public String getCustomShizhuang() {
		return customShizhuang;
	}

	public void setCustomShizhuang(String customShizhuang) {
		this.customShizhuang = customShizhuang;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	

	public Integer getChargeScore() {
		return chargeScore;
	}

	public void setChargeScore(Integer chargeScore) {
		this.chargeScore = chargeScore;
	}

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getGoldCoin() {
		return goldCoin;
	}

	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
	}

	public Integer getPortrait() {
		return portrait;
	}

	public void setPortrait(Integer portrait) {
		this.portrait = portrait;
	}

	public Integer getMonthTao() {
		return monthTao;
	}

	public void setMonthTao(Integer monthTao) {
		this.monthTao = monthTao;
	}

	public Integer getShut() {
		return shut;
	}

	public void setShut(Integer shut) {
		this.shut = shut;
	}

	public String getTyzqStore() {
		return tyzqStore;
	}

	public void setTyzqStore(String tyzqStore) {
		this.tyzqStore = tyzqStore;
	}

	public String getFixedTeamName() {
		return fixedTeamName;
	}

	public void setFixedTeamName(String fixedTeamName) {
		this.fixedTeamName = fixedTeamName;
	}

	public Integer getCtDataScore() {
		return ctDataScore;
	}

	public void setCtDataScore(Integer ctDataScore) {
		this.ctDataScore = ctDataScore;
	}

	public Integer getCtDataTopRank() {
		return ctDataTopRank;
	}

	public void setCtDataTopRank(Integer ctDataTopRank) {
		this.ctDataTopRank = ctDataTopRank;
	}
}