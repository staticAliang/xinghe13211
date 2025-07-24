package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="fixed_team")
public class FixedTeam {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;
    
    private String leaderUid;
    
    private String uid;

    private Integer level;

    private Integer intimacy;

    private Date addTime;

    private String members;

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
    
    public String getLeaderUid() {
		return leaderUid;
	}

	public void setLeaderUid(String leaderUid) {
		this.leaderUid = leaderUid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(Integer intimacy) {
        this.intimacy = intimacy;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}