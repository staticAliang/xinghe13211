package com.fengshen.server.data.write.look;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.fight.c.MSG_C_END_COMBAT;

@Service
public class MSG_LC_END_LOOKON extends MSG_C_END_COMBAT {

	@Override
	public int cmd() {
		return 0x09FD;
	}
	
}
