package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;

@Service
public class M32853 extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		Object[] info = (Object[]) object;
		GameWriteTool.writeString(writeBuf, "是否进入充值页面");
		GameWriteTool.writeString(writeBuf, "关闭");
		GameWriteTool.writeString(writeBuf, "确定");
		String url = GameConfig.config.getBaseConfig().getChargeLink().replace("${account}", 
				String.valueOf(info[0])).replace("${money}", String.valueOf(info[1]));
		GameWriteTool.writeString(writeBuf, url);
	}

	@Override
	public int cmd() {
		return 32853;
	}
}
