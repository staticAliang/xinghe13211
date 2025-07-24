package com.fengshen.db.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *
 */
@Table(name="shidao_history_team")
public class ShidaoHistoryteam {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer shidaoHistoryId;

    private String name;

    private Integer level;

    private Integer family;

    private String gid;

    private Integer icon;
    
    private Integer tao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShidaoHistoryId() {
        return shidaoHistoryId;
    }

    public void setShidaoHistoryId(Integer shidaoHistoryId) {
        this.shidaoHistoryId = shidaoHistoryId;
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

    public Integer getFamily() {
        return family;
    }

    public void setFamily(Integer family) {
        this.family = family;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

	public Integer getTao() {
		return tao;
	}

	public void setTao(Integer tao) {
		this.tao = tao;
	}
    
}