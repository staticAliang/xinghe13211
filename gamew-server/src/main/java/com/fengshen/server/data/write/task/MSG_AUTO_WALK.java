package com.fengshen.server.data.write.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 自动行走 [CHS[5000160]] = 1, -- 
 * 降妖 [CHS[5000161]] = 2, -- 
 * 伏魔 [CHS[5120005]] = 3,
 * -- 飞仙渡邪 [CHS[2200085]] = 4, -- 
 * 为民除暴 [CHS[2200089]] = 5, -- 
 * 修行 [CHS[2200088]]= 6
 * , -- 十绝阵 --
 *  7 为巡逻 
 *  [CHS[7100804]] = 8, -- 二阶降妖 
 *  [CHS[7100805]] = 9, -- 二阶伏魔
 * [CHS[7100806]] = 10, -- 二阶飞仙渡邪
 */
@Service
@Slf4j
public class MSG_AUTO_WALK extends BaseWrite<Vo_AUTO_WALK> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Vo_AUTO_WALK object) {
		GameWriteTool.writeString(writeBuf, object.task_prompt);
		if(!object.task_type.equals("")) {
			GameWriteTool.writeString(writeBuf, object.task_type);
		}
	}

	@Override
	public int cmd() {
		return 45063;
	}
}
