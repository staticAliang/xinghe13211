package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.party.VO_PARTY_ICON;
import com.fengshen.server.data.write.party.MSG_PARTY_ICON;
import com.fengshen.server.data.write.system.MSG_SET_SETTING;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统设置
 * 
 *
 */
@Service
@Slf4j
public class CMD_SET_SETTING implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String key = GameReadTool.readString(buff);
		//0开 1关 
		//如果是共通的话,0关，1掠阵，2坐骑
		int value = GameReadTool.readShort(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		chara.getSettings().put(key, value);
		if("refuse_party_image".equals(key)) {
			//刷新图标
			VO_PARTY_ICON icon = new VO_PARTY_ICON();
			icon.setId(chara.id);
			icon.setMd5Value("");
			GameObjectChar.getGameObjectChar().gameMap.send(new MSG_PARTY_ICON(), icon);
		}else if("award_supply_pet".equals(key)) {
			int petId = 0;
			//设置宠物共通
			if(value == 1) {
				//找出掠阵宠物id
				petId = chara.chongwuluezhenId;
			}else if(value == 2) {
				//坐骑
				petId = chara.zuoqiId;
			}
			chara.awardSupplyPetId = petId;
		}
		log.info("系统设置: key={},value={}", key,value);
		//发送设置消息
		GameObjectChar.send(new MSG_SET_SETTING(), chara.getSettings());
	}

	@Override
	public int cmd() {
		return 0x2094;
	}

}
