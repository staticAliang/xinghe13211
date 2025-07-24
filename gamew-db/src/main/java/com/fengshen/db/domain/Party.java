package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="party")
public class Party {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String partyId;

    private String partyName;

    private String partyBaseInfo;

    private String partyAnnounce;

    private Integer rights;

    private Integer construct;

    private Integer money;

    private Integer salary;

    private Integer autoAcceptLevel;
    
    private Integer minTao;

    private String creator;

    private Integer population;

    private Integer partyLevel;

    private String heir;

    private Integer state;
    
    private Date createTime;

    private String iconMd5;

    private byte[] reviewIconMd5;

    private String leader;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyBaseInfo() {
        return partyBaseInfo;
    }

    public void setPartyBaseInfo(String partyBaseInfo) {
        this.partyBaseInfo = partyBaseInfo;
    }

    public String getPartyAnnounce() {
        return partyAnnounce;
    }

    public void setPartyAnnounce(String partyAnnounce) {
        this.partyAnnounce = partyAnnounce;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public Integer getConstruct() {
        return construct;
    }

    public void setConstruct(Integer construct) {
        this.construct = construct;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getAutoAcceptLevel() {
        return autoAcceptLevel;
    }

    public void setAutoAcceptLevel(Integer autoAcceptLevel) {
        this.autoAcceptLevel = autoAcceptLevel;
    }

    public Integer getMinTao() {
		return minTao;
	}

	public void setMinTao(Integer minTao) {
		this.minTao = minTao;
	}

	public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getPartyLevel() {
        return partyLevel;
    }

    public void setPartyLevel(Integer partyLevel) {
        this.partyLevel = partyLevel;
    }

    public String getHeir() {
        return heir;
    }

    public void setHeir(String heir) {
        this.heir = heir;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIconMd5() {
        return iconMd5;
    }

    public void setIconMd5(String iconMd5) {
        this.iconMd5 = iconMd5;
    }

    public byte[] getReviewIconMd5() {
		return reviewIconMd5;
	}

	public void setReviewIconMd5(byte[] reviewIconMd5) {
		this.reviewIconMd5 = reviewIconMd5;
	}

	public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}