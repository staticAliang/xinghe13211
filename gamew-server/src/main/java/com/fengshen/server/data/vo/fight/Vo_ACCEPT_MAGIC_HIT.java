package com.fengshen.server.data.vo.fight;

import java.util.ArrayList;
import java.util.List;

public class Vo_ACCEPT_MAGIC_HIT {
	
	//出手者
	public int hid;
	//损伤类型
	public int damageType;
	public List<Info> infos;

	public Vo_ACCEPT_MAGIC_HIT() {
		this.infos = new ArrayList<>();
	}
	
	public static class Info {
		//被打者的id
		public int id;
		//是否躲闪. 0:躲闪 1:不躲闪
		public int missed;

		public Info(int id, int missed) {
			this.id = id;
			this.missed = missed;
		}
	}
	
}
