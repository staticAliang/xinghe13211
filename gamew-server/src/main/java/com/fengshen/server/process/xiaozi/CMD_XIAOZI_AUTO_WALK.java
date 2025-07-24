package com.fengshen.server.process.xiaozi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.data.write.M16431_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.process.CommonCmd;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理问道小子自动寻路
 * 
 */
@Service
@Slf4j
public class CMD_XIAOZI_AUTO_WALK implements GameHandler{

	//问道小子自动任务
	public static Timer xiaoziAutoTimer;
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		String name = GameReadTool.readString(buff);
		String mapName = GameReadTool.readString(buff);
		String xy = GameReadTool.readString(buff);
		String menuItem = GameReadTool.readString(buff);
		//GameReadTool.readString(buff);
		log.info("这里是问道小子自动任务：name:"+name+"mapName:"+mapName+"xy:"+xy+"menuItem:"+menuItem);
		GameMap gameMap = GameLine.getGameMap(gameObjectChar.chara.line, mapName);
		if(gameMap == null) {
			return;
		}
		if(gameObjectChar.gameMap.id != gameMap.id) {
			gameObjectChar.chara.x = gameMap.x;
			gameObjectChar.chara.y = gameMap.y;
			//加入到地图
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				for(Chara chara:gameObjectChar.gameTeam.duiwu) {
					GameObjectChar toGameObject = GameObjectCharMng.getGameObjectChar(chara.id);
					if(chara.id != gameObjectChar.chara.id) {
						toGameObject.chara.x = gameMap.x;
						toGameObject.chara.y = gameMap.y;
					}
					gameMap.join(toGameObject);
				}
			}else {
				gameMap.join(gameObjectChar);
			}
			
		}
		Vo_16431_0 vo_16431_0 = new Vo_16431_0();
		vo_16431_0.id = gameObjectChar.chara.id;
		if(!StringUtils.isNullOrEmpty(xy)) {
			//指定了坐标
			String[] split = xy.split(",");
			vo_16431_0.x = Integer.valueOf(split[0]);
			vo_16431_0.y = Integer.valueOf(split[1]);
		}else {
			vo_16431_0.x = gameMap.x;
			vo_16431_0.y = gameMap.y;
		}
		gameObjectChar.chara.x = vo_16431_0.x;
		gameObjectChar.chara.y = vo_16431_0.y;
		gameObjectChar.gameMap.send(new M16431_0(), vo_16431_0);
		
		if(gameMap.x > gameObjectChar.chara.x) {
			gameObjectChar.chara.dir = 3;
		}else {
			gameObjectChar.chara.dir = 1;
		}
		if(gameMap.y > gameObjectChar.chara.y) {
			gameObjectChar.chara.dir = 2;
		}else {
			gameObjectChar.chara.dir = 4;
		}
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//84,61  100,76
				String typeX = "";
				String typeY = "";
				if(gameObjectChar.chara.dir == 4) {
					typeX = "X-";
					typeY = "Y+";
				}else if(gameObjectChar.chara.dir == 3) {
					typeX = "X+";
					typeY = "Y-";
				}else if(gameObjectChar.chara.dir == 2) {
					typeX = "X+";
					typeY = "Y+";
				}else if(gameObjectChar.chara.dir == 1) {
					typeX = "X-";
					typeY = "Y-";
				}
				if (gameObjectChar.gameTeam != null
						&& gameObjectChar.gameTeam.duiwu != null
						&& gameObjectChar.gameTeam.duiwu.size() > 0) {
					for (int j = 0; j < gameObjectChar.gameTeam.duiwu.size(); ++j) {
						Chara chara2 = gameObjectChar.gameTeam.duiwu.get(j);
						if(chara2.id != gameObjectChar.chara.id) {
							Vo_16431_0 vo_16431_0 = new Vo_16431_0();
							vo_16431_0.id = chara2.id;
							if("X+".equals(typeX)) {
								vo_16431_0.x = gameObjectChar.chara.x+(j+1);
							}else {
								vo_16431_0.x = gameObjectChar.chara.x-(j+1);
							}
							if("Y+".equals(typeY)) {
								vo_16431_0.y = gameObjectChar.chara.y+(j+1);
							}else {
								vo_16431_0.y = gameObjectChar.chara.y-(j+1);
							}
						}
						GameObjectCharMng.getGameObjectChar(chara2.id).gameMap.send(new M16431_0(), vo_16431_0);
					}
				}
				GameCommonUtil.showBoss(gameObjectChar.chara,gameMap.id);
			}
			
		}, 800);
		
		//如果有菜单
		if(!StringUtils.isNullOrEmpty(menuItem)) {
			//根据坐标值来决定延迟,1个坐标点100毫秒
			int time = 5000;
			//这个比较远
			if("通灵道人".equals(name)) {
				time = 8000;
			}
			xiaoziAutoTimer = new Timer();
			xiaoziAutoTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					Map<String,Object> map = new LinkedHashMap<String, Object>();
					//这里试试挖宝的菜单
					map.put("type:int", 20023);
					map.put("para1:str", "para1");
					map.put("para2:str", "para2");
					gameObjectChar.sendOne(new CommonCmd(63752), map);
					log.info("问道小子延迟点击菜单.");
				}
			}, time);
		}
	}

	@Override
	public int cmd() {
		return 9888;
	}

}
