package com.fengshen.server.data.write;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;

/**
 * 初始化前端线路
 *
 */
public class MSG_REQUEST_SERVER_STATUS extends BaseWrite<List<GameLine>> {

	@Override
	protected void writeO(ByteBuf writeBuf, List<GameLine> gameLines) {
		String ip = GameConfig.serverIp;
		GameWriteTool.writeShort(writeBuf, gameLines.size());
		for (GameLine gameLine : gameLines) {
			GameWriteTool.writeShort(writeBuf, gameLine.lineNum);
			GameWriteTool.writeString(writeBuf, gameLine.lineName + gameLine.lineNum + "线");
			GameWriteTool.writeString(writeBuf, ip);
			GameWriteTool.writeShort(writeBuf, 2);
		}
	}

	@Override
	public int cmd() {
		return 61663;
	}
}