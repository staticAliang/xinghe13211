package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M45670_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
	}

	@Override
	public int cmd() {
		return 45670;
	}
}
