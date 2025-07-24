package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="shidao_history")
public class ShidaoHistory {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String leader;

    private String leaderUuid;
    
    private Integer level;
    
    private Integer rank;

    private Integer score;

    private Integer totalTao;

    private Integer shidaoTime;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderUuid() {
        return leaderUuid;
    }

    public void setLeaderUuid(String leaderUuid) {
        this.leaderUuid = leaderUuid;
    }

    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalTao() {
        return totalTao;
    }

    public void setTotalTao(Integer totalTao) {
        this.totalTao = totalTao;
    }

    public Integer getShidaoTime() {
        return shidaoTime;
    }

    public void setShidaoTime(Integer shidaoTime) {
        this.shidaoTime = shidaoTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}