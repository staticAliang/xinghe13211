package com.fengshen.server.process.pet;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.vo.Vo_8425_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M8425_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 选择和取消当前坐骑
 * 
 *
 */
@Service
@Slf4j
public class CMD_SELECT_CURRENT_MOUNT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pet_id = GameReadTool.readInt(buff);
		log.info("选择和取消当前坐骑, pet_id={}",pet_id);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		chara.yidongsudu = 0;
		chara.zuoqiId = 0;
		chara.zuoqiwaiguan = 0;
		chara.zuowaiguan = 0;
		for (int i = 0; i < chara.pets.size(); ++i) {
			if (chara.pets.get(i).id == pet_id) {
				for (int j = 0; j < chara.pets.get(i).petShuXing.size(); ++j) {
					if (chara.pets.get(i).petShuXing.get(j).no == 23) {
						chara.yidongsudu = chara.pets.get(i).petShuXing.get(0).capacity_level;
					}
				}
				// 如果注入了彩凤之魂，就变换角色外观
				if (chara.pets.get(i).petShuXing.get(0).zhuruCaifeng == 1 && chara.upgrade_state == 0) {
					// 彩凤坐着的外观
					chara.zuoqiwaiguan = 31501; // 彩凤之魂特效
					// 如果是元血婴的状态
					if (chara.upgrade_state != 0) {
						chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
					} else {
						int zuowaiguan = typeMounts(chara.pets.get(i).petShuXing.get(0).type + 1000, chara.polar,
								chara.sex - 1);
						chara.zuowaiguan = zuowaiguan;
					}
					chara.zuoqiId = chara.pets.get(i).id;
				}
				// 如果没有彩凤之魂特效，就默认的
				else {
					int zuoqiwaiguan = chara.pets.get(i).petShuXing.get(0).type + 1000;
					chara.zuoqiwaiguan = zuoqiwaiguan;
					if (chara.upgrade_state != 0) {
						chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
					} else {
						int zuowaiguan = typeMounts(chara.pets.get(i).petShuXing.get(0).type + 1000, chara.polar,
								chara.sex - 1);
						chara.zuowaiguan = zuowaiguan;
					}
					chara.zuoqiId = chara.pets.get(i).id;
				}
			}
		}
		if (pet_id != 0) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "坐骑包裹已开启。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			chara.isDownZuoQi = 1;
		}
		// 当角色点击坐骑休息的时候
		else {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "坐骑包裹已关闭。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			chara.isDownZuoQi = 0;
		}

		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);

		Vo_8425_0 vo_8425_0 = new Vo_8425_0();
		vo_8425_0.id = pet_id;
		GameObjectChar.send(new M8425_0(), vo_8425_0);

		GameUtil.a65511(gameObjectChar);
		//人物移动速度
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = chara.id;
		vo_45177_0.moveSpeedPercent = chara.yidongsudu;
		gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
	}

	@Override
	public int cmd() {
		return 4382;
	}

	//站立姿势
	final static int[][] type_1 = {{760011, 770012, 770013, 760014, 760015, 760010}, {770011, 760012, 760013, 770014, 770015, 770010}};
	//侧坐姿势
	final static int[][] type_2 = {{760021, 770022, 770023, 760024, 760025, 760020}, {770021, 760022, 760023, 770024, 770025, 770020}};
	//骑坐姿势
	final static int[][] type_3 = {{760031, 770032, 770033, 760034, 760035, 760030}, {770021, 760022, 760023, 770024, 770025, 770030}};
	// 根据传入的zuoqiwaiguan的值，设置它对应的姿势
	public static int typeMounts(int type, int polar, int sex) {
		// 坐姿（女性都是侧坐）在这里添加彩凤的骑乘效果(31501),770021是女性，770031是男性
		//站立姿势
		if (type == 31006 || type == 31008 || type == 31010 || type == 31026) {
			return type_1[sex][polar - 1];
		}
		//侧坐姿势
		if (type == 31011 || type == 31012 || type == 31013) {
			return type_2[sex][polar - 1];
		}
		//骑坐姿势
		return type_3[sex][polar - 1];
	}
}