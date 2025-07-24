package com.fengshen.server.data.write.fight.lc;

import com.fengshen.server.data.write.fight.c.MSG_C_LIFE_DELTA;

public class MSG_LC_LIFE_DELTA extends MSG_C_LIFE_DELTA {

	@Override
	public int cmd() {
		return 0x3DF1;
	}
	
}
