package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="accessibility_map")
public class AccessibilityMap {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private String mapName;

    private Integer x;

    private Integer y;

    private Date createTime;

    private Integer delete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

	public AccessibilityMap(String mapName, Integer x) {
		super();
		this.mapName = mapName;
		this.x = x;
	}

	public AccessibilityMap() {
		super();
	}
}