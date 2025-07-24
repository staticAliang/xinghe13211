package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="victory_die_reward")
public class VictoryDieReward {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

	private String name;
	
    private Integer score;

    private Integer exp;

    private Integer tao;

    private Integer goldCoin;

    private Integer silverCoin;

    private String daoju;

    private String equipAttr;

    private String shoushi;

    private String pet;

    private Integer type;

    private Date addTime;
    
    private Integer deleted;
    
    private Integer wuxue;

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

	public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getTao() {
        return tao;
    }

    public void setTao(Integer tao) {
        this.tao = tao;
    }

    public Integer getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(Integer goldCoin) {
        this.goldCoin = goldCoin;
    }

    public Integer getSilverCoin() {
        return silverCoin;
    }

    public void setSilverCoin(Integer silverCoin) {
        this.silverCoin = silverCoin;
    }

    public String getDaoju() {
        return daoju;
    }

    public void setDaoju(String daoju) {
        this.daoju = daoju;
    }

    public String getEquipAttr() {
        return equipAttr;
    }

    public void setEquipAttr(String equipAttr) {
        this.equipAttr = equipAttr;
    }

    public String getShoushi() {
        return shoushi;
    }

    public void setShoushi(String shoushi) {
        this.shoushi = shoushi;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getWuxue() {
		return wuxue;
	}

	public void setWuxue(Integer wuxue) {
		this.wuxue = wuxue;
	}
}