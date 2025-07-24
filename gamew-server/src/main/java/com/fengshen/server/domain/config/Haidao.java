package com.fengshen.server.domain.config;

/**
 * 海盗设置
 *
 */
public class Haidao {

	//状态
	private int status;
	//经验是否开启
	private int jingyan;
	//qianne
	private int qianneng;
	//道行
	private int daohang;
	//金钱
	private int jinbi;
	//未鉴定
	private int weijianding;
	//海盗次数
	private int count;
	//道具列表
	private String[] daoju;
	//队伍人数
	private int teamNumber;
	//海盗数量
	private int haidaoNum;
	//开启时间
	private String[] times;
	

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public int getJingyan() {
		return jingyan;
	}

	public void setJingyan(int jingyan) {
		this.jingyan = jingyan;
	}

	public int getQianneng() {
		return qianneng;
	}

	public void setQianneng(int qianneng) {
		this.qianneng = qianneng;
	}

	public int getJinbi() {
		return jinbi;
	}

	public void setJinbi(int jinbi) {
		this.jinbi = jinbi;
	}

	public int getWeijianding() {
		return weijianding;
	}

	public void setWeijianding(int weijianding) {
		this.weijianding = weijianding;
	}

	public String[] getDaoju() {
		return daoju;
	}

	public void setDaoju(String[] daoju) {
		this.daoju = daoju;
	}

	public int getDaohang() {
		return daohang;
	}

	public void setDaohang(int daohang) {
		this.daohang = daohang;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}

	public int getHaidaoNum() {
		return haidaoNum;
	}

	public void setHaidaoNum(int haidaoNum) {
		this.haidaoNum = haidaoNum;
	}

	public String[] getTimes() {
		return times;
	}

	public void setTimes(String[] times) {
		this.times = times;
	}
}