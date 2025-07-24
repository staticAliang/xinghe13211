package com.fengshen.server.process.dari;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 请求boos血量
 */
@Slf4j
@Service
public class MSG_WORLD_BOSS_RESULT extends BaseWrite<vo_boos_result> {

	@Override
	protected void writeO(ByteBuf buff, vo_boos_result result) {
		GameWriteTool.writeShort(buff, (int) result.getOld_rank());
		GameWriteTool.writeShort(buff, (int) result.getNew_rank());
		GameWriteTool.writeShort(buff, (int) result.getInside_rank());
		GameWriteTool.writeInt(buff,  result.getAdd_damage());
		GameWriteTool.writeInt(buff, result.getNew_damage());
	}

	@Override
	public int cmd() {
		return 33011;
	}

}
