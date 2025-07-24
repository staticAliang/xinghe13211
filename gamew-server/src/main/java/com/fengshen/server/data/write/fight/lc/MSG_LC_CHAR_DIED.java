package com.fengshen.server.data.write.fight.lc;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.fight.c.MSG_C_CHAR_DIED;

@Service
public class MSG_LC_CHAR_DIED extends MSG_C_CHAR_DIED {

	@Override
	public int cmd() {
		return 0x29F5;
	}

}
