package com.fengshen.server.data.vo.fight;

import java.util.HashMap;
import java.util.Map;

public class Vo_COMBAT_STATUS_INFO {

	private Integer objId;
	
	private String statusType;
	
	private Map<String,Object> buildFields;
	
	private Integer isCanUseHYJJ;
	
	private Integer zhenfaPolar;
	
	

	public Vo_COMBAT_STATUS_INFO() {
		this.buildFields = new HashMap<>();
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public Map<String, Object> getBuildFields() {
		return buildFields;
	}

	public void setBuildFields(Map<String, Object> buildFields) {
		this.buildFields = buildFields;
	}

	public Integer getIsCanUseHYJJ() {
		return isCanUseHYJJ;
	}

	public void setIsCanUseHYJJ(Integer isCanUseHYJJ) {
		this.isCanUseHYJJ = isCanUseHYJJ;
	}

	public Integer getZhenfaPolar() {
		return zhenfaPolar;
	}

	public void setZhenfaPolar(Integer zhenfaPolar) {
		this.zhenfaPolar = zhenfaPolar;
	}
	
	
}