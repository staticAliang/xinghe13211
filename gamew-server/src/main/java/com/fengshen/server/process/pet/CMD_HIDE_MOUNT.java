package com.fengshen.server.process.pet;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_53411_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M53411_0;
import com.fengshen.server.data.write.M65527_4;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 通知是否隐藏坐骑
 * 
 *
 */
@Service
public class CMD_HIDE_MOUNT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int petId = GameReadTool.readInt(buff);
		int isHide = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		List<Integer> list = new LinkedList<>();
		list.add(petId);
		list.add(isHide);
		GameObjectChar.send(new M65527_4(), list);
		if (isHide == 1) {
			chara.zuowaiguan = 0;
			chara.zuoqiwaiguan = 0;
		}
		if (isHide == 0) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				if (petId == chara.pets.get(i).id) {
					chara.zuoqiwaiguan = chara.pets.get(i).petShuXing.get(0).type + 1000;
					if (chara.upgrade_state != 0) {
						chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
					} else {
						chara.zuowaiguan = CMD_SELECT_CURRENT_MOUNT.typeMounts(chara.zuoqiwaiguan, chara.polar, chara.sex - 1);
					}
				}
			}
		}
		Vo_53411_0 vo_53411_0 = new Vo_53411_0();
		vo_53411_0.petId = petId;
		vo_53411_0.isHide = isHide;
		GameObjectChar.send(new M53411_0(), vo_53411_0);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
	}

	@Override
	public int cmd() {
		return 53412;
	}

}
