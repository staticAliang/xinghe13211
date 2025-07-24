package com.fengshen.server.data.write.user;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 今日数据统计
 * 
 *
 */
public class MSG_DAILY_STATS extends BaseWrite<Chara> {

	@Override
	protected void writeO(ByteBuf buff, Chara chara) {
		
		Map<String, Integer> dayDataCount = GameCommonUtil.toDayDataCount(chara);
		GameWriteTool.writeInt(buff, dayDataCount.get("exp")); //exp
		GameWriteTool.writeInt(buff, dayDataCount.get("tao")); //tao
		GameWriteTool.writeInt(buff, 0); //tao_point
		GameWriteTool.writeInt(buff, dayDataCount.get("monTao")); //mon_tao
		GameWriteTool.writeInt(buff, 0); //mon_tao_ex
		GameWriteTool.writeInt(buff, dayDataCount.get("pot")); //pot
		GameWriteTool.writeInt(buff, dayDataCount.get("death")); //death
		GameWriteTool.writeInt(buff, (int)(chara.online_time / 1000L + (System.currentTimeMillis() - chara.uptime) / 1000L)); //onLine_time
		GameWriteTool.writeInt(buff, dayDataCount.get("shuadaoTimes")); //shuadaoTimes
		GameWriteTool.writeInt(buff, chara.getWaiguan()); //org_icon
		GameWriteTool.writeShort(buff, chara.getLevel()); //level
		GameWriteTool.writeString(buff, chara.getName());
		GameWriteTool.writeString(buff, chara.getPartyName());
		
		GameWriteTool.writeInt(buff, 0); //double_point
		GameWriteTool.writeInt(buff, chara.enable_double_points); //double_point_max
		GameWriteTool.writeInt(buff, 0); //chongfx_point
		GameWriteTool.writeInt(buff, chara.shuadaochongfeng_san); //chongfx_point_max
		GameWriteTool.writeInt(buff, 0); //jiji_point
		GameWriteTool.writeInt(buff, chara.jijirulvling); //jiji_point_max
		GameWriteTool.writeInt(buff, 0); //zqhm_point
		GameWriteTool.writeInt(buff, chara.ziqihongmeng); //zqhm_point_max
	}

	@Override
	public int cmd() {
		return 0xD06F;
	}

}
