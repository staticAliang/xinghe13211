package com.fengshen.db.domain.sys;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_user")
public class SysUser {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private String userName;

	private String password;

	private Integer userType;

	private String headImgUrl;

	private Integer sex;

	private String nickName;

	private Integer state;

	private Integer firstLoginUpdatePasswordFlag;

	private Long createUserId;

	private Date createTime;
	
	private Date lastLoginTime;
	
	private String lastLoginIp;
	
	private String allowLoginIp;
	
	private String mobilePhone;
	
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getFirstLoginUpdatePasswordFlag() {
		return firstLoginUpdatePasswordFlag;
	}

	public void setFirstLoginUpdatePasswordFlag(Integer firstLoginUpdatePasswordFlag) {
		this.firstLoginUpdatePasswordFlag = firstLoginUpdatePasswordFlag;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAllowLoginIp() {
		return allowLoginIp;
	}

	public void setAllowLoginIp(String allowLoginIp) {
		this.allowLoginIp = allowLoginIp;
	}
	
}