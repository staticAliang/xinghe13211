package com.fengshen.server.data.write.fuling;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA.FU_SHEN_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SOUL_FULINGZHEN_DATA extends BaseWrite<Vo_SOUL_FULINGZHEN_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SOUL_FULINGZHEN_DATA object) {
		Chara chara = object.getChara();
		int exp = chara.zhenlingExp;
		int zhenlingStage = chara.zhenlingStage;
		int level = chara.zhenlingLevel;
		GameWriteTool.writeInt(buff, exp ^ ((0x11 + zhenlingStage) * 16 * 16 + (0x11 + level))); // 经验
		GameWriteTool.writeByte(buff, zhenlingStage ^ 34); // 阶数
		GameWriteTool.writeByte(buff, level ^ zhenlingStage);// 灵阵级数
		GameWriteTool.writeByte(buff, object.getNextItemNum());// 铸灵石个数
		GameWriteTool.writeByte(buff, chara.qinglongZhenlingLevel == 0?1:chara.qinglongZhenlingLevel); // 青龙等级
		GameWriteTool.writeByte(buff, chara.baihuhenlingLevel== 0?1:chara.baihuhenlingLevel); // 白虎等级
		GameWriteTool.writeByte(buff, chara.zhuqueZhenlingLevel== 0?1:chara.zhuqueZhenlingLevel); // 朱雀等级
		GameWriteTool.writeByte(buff, chara.xuanwuZhenlingLevel== 0?1:chara.xuanwuZhenlingLevel); // 玄武等级
		
		//附身信息
		List<FU_SHEN_INFO> fushenInfos = new ArrayList<>();
		//计算真灵附身数量
		if(chara.zhenlingType != 0) {
			fushenInfos.add(new FU_SHEN_INFO(1,chara.uuid,chara.zhenlingType));
		}
		for(Petbeibao petbeibao:chara.pets) {
			PetShuXing petShuXing = petbeibao.petShuXing.get(0);
			if(petShuXing.zhenlingType != 0) {
				fushenInfos.add(new FU_SHEN_INFO(2,petShuXing.auto_fight,petShuXing.zhenlingType));
			}
		}
		GameWriteTool.writeShort(buff, fushenInfos.size());
		for(FU_SHEN_INFO info:fushenInfos) {
			GameWriteTool.writeByte(buff, info.getType());
			GameWriteTool.writeString(buff, info.getId());
			GameWriteTool.writeByte(buff, info.getZhenlingType());
		}
		GameWriteTool.writeByte(buff, 0);
	}

	@Override
	public int cmd() {
		return 0xD36D;
	}

}
