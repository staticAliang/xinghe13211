package com.fengshen.server.data.write.tongtianta;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通天塔任务信息
 * 
 *
 */
public class MSG_TONGTIANTA_INFO extends BaseWrite<Vo_TONGTIANTA_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_TONGTIANTA_INFO object) {
		GameWriteTool.writeShort(buff, object.getCurLayer());
		GameWriteTool.writeShort(buff, object.getBreakLayer());
		GameWriteTool.writeByte(buff, object.getCurType());
		GameWriteTool.writeInt(buff, object.getTopLayer());
		GameWriteTool.writeString(buff, object.getNpc());
		GameWriteTool.writeByte(buff, object.getChallengeCount());
		GameWriteTool.writeString(buff, object.getBonusType());
		GameWriteTool.writeByte(buff, object.getHasNotCompletedSmfj());
		
	}

	@Override
	public int cmd() {
		return 0xC003;
	}

}
