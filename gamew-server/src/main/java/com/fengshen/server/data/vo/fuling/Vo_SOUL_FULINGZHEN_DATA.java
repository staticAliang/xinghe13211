package com.fengshen.server.data.vo.fuling;

import com.fengshen.server.domain.Chara;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SOUL_FULINGZHEN_DATA {

	private Chara chara;
	
	//当前升级灵石个数
	private Integer nextItemNum;

	public Vo_SOUL_FULINGZHEN_DATA(Chara chara, Integer nextItemNum) {
		super();
		this.chara = chara;
		this.nextItemNum = nextItemNum;
	}

	public Vo_SOUL_FULINGZHEN_DATA() {
	}
	
	@Getter
	@Setter
	public static class FU_SHEN_INFO {
		
		private Integer type;
		
		private String id;
		
		private Integer zhenlingType;

		public FU_SHEN_INFO(Integer type, String id, Integer zhenlingType) {
			super();
			this.type = type;
			this.id = id;
			this.zhenlingType = zhenlingType;
		}
	}
}