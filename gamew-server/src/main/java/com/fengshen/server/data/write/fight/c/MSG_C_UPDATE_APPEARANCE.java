package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;

@Service
public class MSG_C_UPDATE_APPEARANCE extends MSG_UPDATE_APPEARANCE{

	@Override
	public int cmd() {
		return 0xF0FF;
	}
}