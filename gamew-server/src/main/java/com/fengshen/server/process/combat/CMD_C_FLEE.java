package com.fengshen.server.process.combat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fight.Vo_C_SANDGLASS;
import com.fengshen.server.data.write.fight.c.MSG_C_SANDGLASS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.process.CommonCmd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 逃跑
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_C_FLEE implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("逃跑");
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		Chara chara = session.chara;
		int action = 7;
		FightContainer fightContainer = FightManager.getFightContainer();
		if(fightContainer == null) {
			return;
		}
		FightObject fightObject = FightManager.getFightObject(fightContainer, chara.id);
		if(fightObject == null) {
			return;
		}
		// 如果用户在试道战场
		if (fightObject.type != 2 && GameShiDao.statzhuangtai >= 2) {
			if (chara.mapid == 38004) {
				Map<String,Object> obj = new LinkedHashMap<>();
				obj.put("int:id", chara.id);
				obj.put("short:result", 0);
				GameObjectChar.send(new CommonCmd(0x2DD3), obj);
				GameCommonUtil.dialogOk("当前#R阶段#n不允许逃跑");
				return;
			}
		}
		FightRequest fr = new FightRequest();
		fr.id = chara.id;
		fr.action = action;
		fr.vid = chara.id;
		// PK主动发起者
		if (session.action.equals("activeForcePk")) {
			Map<String,Object> obj = new LinkedHashMap<>();
			obj.put("int:id", chara.id);
			obj.put("short:result", 0);
			GameObjectChar.send(new CommonCmd(0x2DD3), obj);
			GameCommonUtil.dialogOk("#R强制PK#n发起者不允许逃跑");
			return;
		}
		if (chara.getTongtiantaTask() != null && chara.getTongtiantaTask().getChallengeCount() > 0
				&& chara.mapName.equals("通天塔")) {
			GameUtil.confirm(chara, "通天塔#R突破阶段#n逃跑的话，算作死亡并扣除一次机会，确定要逃跑？", "tttGoRun");
			return;
		}
		if (fightObject.type == 1) {
			//如果他的宠物还在的话
			FightObject fightObjectPet = FightManager.getFightObjectPet(fightContainer, fightObject);
			//如果为空或者是已经死亡
			if(fightObjectPet == null || fightObjectPet.isDead()) {
				FightManager.send(fightContainer,new MSG_C_SANDGLASS(), new Vo_C_SANDGLASS(chara.id, 0));
			}
		}
		FightManager.addRequest(fightContainer, fr);
	}

	@Override
	public int cmd() {
		return 518;
	}
}
