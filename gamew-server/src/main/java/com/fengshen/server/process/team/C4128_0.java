package com.fengshen.server.process.team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_4119_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M4119_0;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.MSG_UPDATE_MOVE_SPEED;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 队伍暂离
 * 
 *
 */
@Service
public class C4128_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (chara.mapid == 38004) {
			GameUtil.sendMeTips("当前地图不支持此操作.");
			return;
		}
		List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
		
		boolean isDelete = false;
		Iterator<Chara> iterator = duiwu.iterator();
		while(iterator.hasNext()) {
			Chara next = iterator.next();
			//把暂离队伍人员删除出队伍列表
			if(next.id == chara.id) {
				iterator.remove();
				isDelete = true;
			}
		}
		//不在队伍中直接返回
		if(!isDelete) {
			GameUtil.sendMeTips("你已暂离队伍，请勿重复操作！");
			return;
		}
		
		Vo_TITLE vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = chara.id;
		vo_61671_0.count = 0;
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		
		GameUtil.a4119(duiwu);
		
		List<Vo_4119_0> object1 = new ArrayList<Vo_4119_0>();
		GameObjectChar.send(new M4119_0(), object1);
		for (int j = 0; j < gameObjectChar.gameTeam.zhanliduiyuan.size(); ++j) {
			if (chara.id == gameObjectChar.gameTeam.zhanliduiyuan.get(j).id) {
				gameObjectChar.gameTeam.zhanliduiyuan.get(j).memberteam_status = 2;
			}
		}
		GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
		for (int j = 0; j < duiwu.size(); ++j) {
			GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(duiwu.get(j).id);
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(duiwu.get(j));
			teamGame.sendOne(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			teamGame.sendOne(new M20568_0(), vo_20568_0);
			//删除这人的信息
			teamGame.moveIds.remove(chara.id);
		}
		GameCommonUtil.sendTips(chara.name + "暂离了队伍。", duiwu.get(0).id);
		
		GameUtil.sendMeTips("你暂时离开了#Y#<" + duiwu.get(0).name + "#>#n的队伍。");
		
		// 设置状态
		vo_61671_0 = new Vo_TITLE();
		vo_61671_0.id = chara.id;
		if (chara.isFight) {
			vo_61671_0.list.add(1);
		} else {
			vo_61671_0.list.add(0);
		}
		// 红名
		if (chara.isNameRed == 1) {
			vo_61671_0.list.add(7);
		}
		gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
		//速度恢复默认
		Vo_UPDATE_MOVE_SPEED vo_45177_0 = new Vo_UPDATE_MOVE_SPEED();
		vo_45177_0.id = chara.id;
		vo_45177_0.moveSpeedPercent = chara.yidongsudu;
		gameObjectChar.gameMap.send(new MSG_UPDATE_MOVE_SPEED(), vo_45177_0);
		GameCommonUtil.flyInit(gameObjectChar);
		for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
			GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.zhanliduiyuan.get(i).id);
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(teamGameObjectChar.chara);
			teamGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(gameObjectChar.chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
	}

	@Override
	public int cmd() {
		return 4128;
	}
}