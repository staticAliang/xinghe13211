package com.fengshen.server.process.dari;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 请求boos血量
 */
@Slf4j
@Service
public class MSG_WORLD_BOSS_LIFE extends BaseWrite<vo_boos_life> {

	@Override
	protected void writeO(ByteBuf buff, vo_boos_life life) {
		GameWriteTool.writeString(buff, life.getLife_str());
		GameWriteTool.writeString(buff, life.getMax_life_str());
	}

	@Override
	public int cmd() {
		return 33013;
	}

}
