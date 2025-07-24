package com.fengshen.server.data.vo.system;

public class Vo_BLOG_OSS_TOKEN {

	private Integer flag;
	
	private String ret;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Vo_BLOG_OSS_TOKEN(Integer flag, String ret) {
		super();
		this.flag = flag;
		this.ret = ret;
	}
}
