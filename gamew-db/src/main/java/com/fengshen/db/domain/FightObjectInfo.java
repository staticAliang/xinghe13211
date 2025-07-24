package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "fight_object_info")
public class FightObjectInfo {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private String type;

	private String name;

	private Integer level;

	private String showName;

	private Integer life;

	private Integer mana;

	private Integer phyAttack;

	private Integer magAttack;

	private String polar;

	private Integer speed;

	private Integer def;

	private Integer icon;

	private Integer daohang;

	private Integer petMartial;

	private String skill;

	private String petTianshu;

	private Integer allResistPolar;

	private Integer resistMetal;

	private Integer resistWood;

	private Integer resistWater;

	private Integer resistFire;

	private Integer resistEarth;

	private Integer doubleHitRate;

	private Integer doubleHit;

	private Integer mstuntRate;

	private Date addTime;

	private Date updateTime;

	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getLife() {
		return life;
	}

	public void setLife(Integer life) {
		this.life = life;
	}

	public Integer getMana() {
		return mana;
	}

	public void setMana(Integer mana) {
		this.mana = mana;
	}

	public Integer getPhyAttack() {
		return phyAttack;
	}

	public void setPhyAttack(Integer phyAttack) {
		this.phyAttack = phyAttack;
	}

	public Integer getMagAttack() {
		return magAttack;
	}

	public void setMagAttack(Integer magAttack) {
		this.magAttack = magAttack;
	}

	public String getPolar() {
		return polar;
	}

	public void setPolar(String polar) {
		this.polar = polar == null ? null : polar.trim();
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}

	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public Integer getDaohang() {
		return daohang;
	}

	public void setDaohang(Integer daohang) {
		this.daohang = daohang;
	}

	public Integer getPetMartial() {
		return petMartial;
	}

	public void setPetMartial(Integer petMartial) {
		this.petMartial = petMartial;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill == null ? null : skill.trim();
	}

	public String getPetTianshu() {
		return petTianshu;
	}

	public void setPetTianshu(String petTianshu) {
		this.petTianshu = petTianshu == null ? null : petTianshu.trim();
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

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getAllResistPolar() {
		return allResistPolar;
	}

	public void setAllResistPolar(Integer allResistPolar) {
		this.allResistPolar = allResistPolar;
	}

	public Integer getResistMetal() {
		return resistMetal;
	}

	public void setResistMetal(Integer resistMetal) {
		this.resistMetal = resistMetal;
	}

	public Integer getResistWood() {
		return resistWood;
	}

	public void setResistWood(Integer resistWood) {
		this.resistWood = resistWood;
	}

	public Integer getResistWater() {
		return resistWater;
	}

	public void setResistWater(Integer resistWater) {
		this.resistWater = resistWater;
	}

	public Integer getResistFire() {
		return resistFire;
	}

	public void setResistFire(Integer resistFire) {
		this.resistFire = resistFire;
	}

	public Integer getResistEarth() {
		return resistEarth;
	}

	public void setResistEarth(Integer resistEarth) {
		this.resistEarth = resistEarth;
	}

	public Integer getDoubleHitRate() {
		return doubleHitRate;
	}

	public void setDoubleHitRate(Integer doubleHitRate) {
		this.doubleHitRate = doubleHitRate;
	}

	public Integer getDoubleHit() {
		return doubleHit;
	}

	public void setDoubleHit(Integer doubleHit) {
		this.doubleHit = doubleHit;
	}

	public Integer getMstuntRate() {
		return mstuntRate;
	}

	public void setMstuntRate(Integer mstuntRate) {
		this.mstuntRate = mstuntRate;
	}
}