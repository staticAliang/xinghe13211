package com.fengshen.server.data.vo.party;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PARTY_DIALOG {
	public String caption;
	public String content;
	public String peer_name;
	public String ask_type;
	public Integer flag;
	
	public Vo_PARTY_DIALOG_Item item;

	@Getter
	@Setter
	public static class Vo_PARTY_DIALOG_Item{
		private String gid;
		private String name;
		private Integer level;
		private Integer polar;
		private Integer tao;
		private Integer gender;
	}
	
	public Vo_PARTY_DIALOG() {
		this.caption = "";
		this.content = "";
		this.flag = 1;
	}
}
