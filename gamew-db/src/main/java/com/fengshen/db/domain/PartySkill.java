package com.fengshen.db.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="party_skill")
public class PartySkill {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String partyId;

    private Integer no;

    private String name;

    private Integer level;

    private Integer currentScore;

    private Integer levelupScore;

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

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public Integer getLevelupScore() {
        return levelupScore;
    }

    public void setLevelupScore(Integer levelupScore) {
        this.levelupScore = levelupScore;
    }
}