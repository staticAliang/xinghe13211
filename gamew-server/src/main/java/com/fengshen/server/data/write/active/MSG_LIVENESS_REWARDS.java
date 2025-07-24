package com.fengshen.server.data.write.active;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 活跃度领取奖励
 * 
 *
 */
public class MSG_LIVENESS_REWARDS extends BaseWrite<Object>{

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
		@SuppressWarnings("unchecked")
		List<Integer[]> datas = (List<Integer[]>) object;
		//activityRewardCount
		GameWriteTool.writeByte(buff, datas.size());
		for(Integer[] i:datas) {
			//activity
			GameWriteTool.writeShort(buff, i[0]);
			//status
			GameWriteTool.writeByte(buff, i[1]);
		}
	}

	@Override
	public int cmd() {
		return 0xA200;
	}

}
