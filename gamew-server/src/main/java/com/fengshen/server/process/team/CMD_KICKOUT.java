package com.fengshen.server.process.team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_4119_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_45124_0;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.vo.Vo_49189_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M4119_0;
import com.fengshen.server.data.write.M4121_0;
import com.fengshen.server.data.write.M45124_0;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M49189_0;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 将队员请离队伍
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_KICKOUT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String peer_name = GameReadTool.readString(buff);
		log.info("将队员请离队伍, peer_name={}",peer_name);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		
		GameTeam gameTeam = gameObjectChar.gameTeam;
		if(!GameCommonUtil.isNotGameTeam(gameTeam)) {
			GameUtil.sendMeTips("你还没有队伍呢");
			return;
		}
		// 如果被请离对象在某些地图
		if (chara.mapName.equals("瑶池") || chara.mapName.equals("桐柏山") || chara.mapName.equals("黑风洞一层")
				|| chara.mapName.equals("黑风洞二层") || chara.mapName.equals("黑风洞三层") || chara.mapName.equals("兰若寺后山")
				|| chara.mapName.equals("兰若寺") || chara.mapName.equals("烈火涧") || chara.mapName.equals("烈火涧西面")
				|| chara.mapName.equals("烈火涧北面") || chara.mapName.equals("烈火涧东面") || chara.mapName.equals("飘渺仙府")
				|| chara.mapName.equals("仙府秘境") || chara.mapName.equals("仙府大殿")) {
			GameUtil.sendMeTips("正在任务，无法请离！.");
			return;
		}
		List<Chara> duiwu = gameTeam.duiwu;
		//如果请求的人不是队长
		if(duiwu.get(0).id != gameObjectChar.chara.id) {
			GameUtil.sendMeTips("只有队长才能请离队员");
			return;
		}
		Chara kickOutChara = null;
		Iterator<Chara> duiwuIterator = duiwu.iterator();
		while(duiwuIterator.hasNext()) {
			Chara next = duiwuIterator.next();
			if (next.name.equals(peer_name)) {
				kickOutChara = next;
				//移除队伍
				duiwuIterator.remove();
				break;
			}
		}
		
		//删除暂离里面的人
		Iterator<Vo_4121_0> zanliIterator = gameTeam.zhanliduiyuan.iterator();
		while(zanliIterator.hasNext()) {
			Vo_4121_0 next = zanliIterator.next();
			if (next.str.equals(peer_name)) {
				//移除队伍
				if(kickOutChara == null) {
					kickOutChara = GameObjectCharMng.getGameObjectChar(next.id).chara;
				}
				zanliIterator.remove();
				break;
			}
		}
		
		List<Vo_4119_0> object1 = new ArrayList<Vo_4119_0>();
		GameObjectCharMng.getGameObjectChar(kickOutChara.id).sendOne(new M4119_0(), object1);
		List<Vo_4121_0> vo_4121_0List = new ArrayList<Vo_4121_0>();
		GameObjectCharMng.getGameObjectChar(kickOutChara.id).sendOne(new M4121_0(), vo_4121_0List);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		for (int j = 0; j < duiwu.size(); ++j) {
			Chara team = duiwu.get(j);
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(team);
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(team.id);
			teamGameObjectChar.sendOne(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = peer_name + "离开了队伍。。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			if (team.id != kickOutChara.id) {
				teamGameObjectChar.sendOne(new M20481_0(), vo_20481_0);
			}
		}
		
		Vo_20568_0 vo_20568_0 = new Vo_20568_0();
		vo_20568_0.gid = "";
		GameObjectChar.send(new M20568_0(), vo_20568_0);

		GameUtil.a4119(duiwu);
		GameUtil.a4121(gameTeam.zhanliduiyuan);
		
		GameObjectChar outGameObjectChar = GameObjectCharMng.getGameObjectChar(kickOutChara.id);
		Vo_49189_0 vo_49189_0 = new Vo_49189_0();
		outGameObjectChar.sendOne(new M49189_0(), vo_49189_0);

		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = "你被请离了队伍";
		vo_8165_0.active = 0;
		outGameObjectChar.sendOne(new M8165_0(), vo_8165_0);
		
		
		//删除队伍
		outGameObjectChar.gameTeam = null;

		Vo_45124_0 vo_45124_0 = new Vo_45124_0();
		GameObjectChar.send(new M45124_0(), vo_45124_0);
		// 刷新标识
		GameCommonUtil.setCharaTitleFlag(gameObjectChar.chara);
		
		//设置默认状态
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = kickOutChara.id;
		if(kickOutChara.isNameRed ==1) {
			vo_61671_0.list.add(7);
		}
		outGameObjectChar.sendOne(new MSG_TITLE(), vo_61671_0);
		//移动速度
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = outGameObjectChar.chara.id;
		vo_45177_0.moveSpeedPercent = outGameObjectChar.chara.yidongsudu;
		outGameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
		//信息
		for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
			//删除这个人的信息
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			if(teamGameObjectChar != null) {
				teamGameObjectChar.moveIds.remove(outGameObjectChar.chara.id);
			}
		}
		GameCommonUtil.flyInit(gameObjectChar);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(gameObjectChar.chara));
		//删除自己信息
		outGameObjectChar.moveIds.clear();
		//再次起飞
		GameCommonUtil.flyInit(outGameObjectChar);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(outGameObjectChar.chara);
		outGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		
		
	}

	@Override
	public int cmd() {
		return 4120;
	}
}