package com.fengshen.server.process.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.vo.Vo_4163_0;
import com.fengshen.server.data.vo.Vo_8425_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.data.write.M4163_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.M8425_0;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.job.SaveCharaTimes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 切换元婴、血婴、真身
 */
@Service
public class CMD_CHANGE_CHAR_UPGRADE_STATE implements GameHandler {

	public static Map<Integer, Long> times = new ConcurrentHashMap<Integer, Long>();

	@Override
	public void process(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
		int state = GameReadTool.readByte(paramByteBuf);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (chara.mapName.equals("试道场")) {
			GameUtil.sendMeTips("此地图不允许切换真身或元血婴");
			return;
		}
		if (gameObjectChar.privilege == 0 && times.get(chara.id) != null
				&& times.get(chara.id) + 8000L > System.currentTimeMillis()) {
			GameCommonUtil.dialogOk("切换太频繁,请稍后");
			return;
		}
		times.put(chara.id, System.currentTimeMillis());
		//切换之前要保存一次信息
		CharaBaseInfo setInfo = SaveCharaTimes.setInfo(chara);
		if(chara.upgrade_state == 0) {
			chara.charaRealInfo = setInfo;
		}else {
			chara.charaYuanyingInfo = setInfo;
		}
		//设置状态
		chara.upgrade_state = state;
		// 把之前的宠物设置休息
		Vo_4163_0 vo_4163_0 = new Vo_4163_0();
		vo_4163_0.id = chara.chongwuchanzhanId;
		vo_4163_0.b = 0;
		GameObjectChar.send(new M4163_0(), vo_4163_0);
		if (chara.chongwuluezhenId != 0) {
			vo_4163_0 = new Vo_4163_0();
			vo_4163_0.id = chara.chongwuluezhenId;
			vo_4163_0.b = 0;
			GameObjectChar.send(new M4163_0(), vo_4163_0);
		}
		//把当前状态的技能清空
		List<JiNeng> clearJns = chara.jiNengList;
		List<Vo_32747_0> clearJn = new ArrayList<>();
		for(JiNeng jiNeng:clearJns) {
			Vo_32747_0 vo_32747_0 = new Vo_32747_0();
			vo_32747_0.id = chara.id;
			vo_32747_0.skill_no = jiNeng.skill_no;
			clearJn.add(vo_32747_0);
		}
		GameObjectChar.send(new M32747_0(), clearJn);
		// 切换
		GameObjectChar.switchChara(chara, false);
		// 设置切换后的宠物参战和掠阵
		vo_4163_0 = new Vo_4163_0();
		vo_4163_0.id = chara.chongwuchanzhanId;
		vo_4163_0.b = 1;
		GameObjectChar.send(new M4163_0(), vo_4163_0);
		if (chara.chongwuluezhenId != 0) {
			vo_4163_0 = new Vo_4163_0();
			vo_4163_0.id = chara.chongwuluezhenId;
			vo_4163_0.b = 2;
			GameObjectChar.send(new M4163_0(), vo_4163_0);
		}
		// 设置坐骑
		Vo_8425_0 vo_8425_0 = new Vo_8425_0();
		vo_8425_0.id = chara.zuoqiId;
		gameObjectChar.sendOne(new M8425_0(), vo_8425_0);
		// 移动速度
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = chara.id;
		vo_45177_0.moveSpeedPercent = chara.yidongsudu;
		gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);

		// 更新队伍信息.
		if (GameObjectCharMng.getGameObjectChar(chara.id).gameTeam != null) {
			GameUtil.updateRightTeamInfos(chara);
		}
		int listSize = chara.otherGoods.size();
		int perSize = 100;
		for (int beginIndex = 0; beginIndex < listSize; beginIndex += perSize) {
			int endIndex = Math.min(beginIndex + perSize, listSize);
			GameObjectChar.send(new M65525_0(), chara.otherGoods.subList(beginIndex, endIndex));
		}
		// MSG_UPDATE_IMPROVEMENT
		GameUtil.a65511(gameObjectChar);
		GameUtil.sendUpdate(chara, "openUserTab_yuanying");
		// 更新人物数据
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		GameObjectChar.send(new M65529_0(), vo_65529_0);
		// 更新人物外观数据
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		//重新刷新技能
		GameObjectChar.send(new M32747_0(), GameUtil.a32747(chara));
		
		if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, gameObjectChar.chara)) {
			for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
				GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
				GameCommonUtil.flyInit(teamGameObjectChar);
				vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
				teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			}
		}else {
			GameCommonUtil.flyInit(gameObjectChar);
			vo_61661_0 = GameUtil.a61661(chara);
			gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
	}

	@Override
	public int cmd() {
		return 0x5051;
	}
}
