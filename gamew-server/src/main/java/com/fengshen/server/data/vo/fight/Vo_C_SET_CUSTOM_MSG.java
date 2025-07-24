package com.fengshen.server.data.vo.fight;

public class Vo_C_SET_CUSTOM_MSG {

	private Integer id;
	
	private Integer channel;
	
	private String serverName;
	
	private String msg;
	
	private Integer showTime;
	
	private Integer vipType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getVipType() {
		return vipType;
	}

	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}
}