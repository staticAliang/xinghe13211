package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.NpcPoint;
import com.fengshen.server.data.vo.Vo_45157_0;
import com.fengshen.server.data.vo.Vo_65505_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M12285_0;
import com.fengshen.server.data.write.M45157_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.M65531_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.MSG_ENTER_ROOM;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.system.M65529_npc;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.game.scene.INotifyHandler;
import com.fengshen.server.game.scene.SceneObj;
import com.fengshen.server.game.scene.SceneObjCollection;
import com.fengshen.server.game.scene.SceneObjType;
import com.fengshen.server.game.scene.ScenePlayer;
import com.fengshen.server.game.scene.VisionGrid;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.service.MapGuardianService;
import com.fengshen.server.service.ZhengDaoDianService;
import com.fengshen.server.util.NpcIds;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

// 地图
@Service("gmmmasdfasdfmmmm")
@Scope("prototype")
public class GameMap {
	public int id;
	public String name;
	public int x;
	public int y;
	public int map_type;

	public List<GameObjectChar> sessionList;
	public GameShiDao gameShiDao;
	private static int VISION_GRID_WIDTH = 30;
	private Int2ObjectMap<VisionGrid> visionGrids = new Int2ObjectOpenHashMap<>();
	@SuppressWarnings("rawtypes")
	private Map<SceneObjType, INotifyHandler> notifyHandlers = new EnumMap<SceneObjType, INotifyHandler>(
			SceneObjType.class);
	private SceneObjCollection sceneObjCollection = new SceneObjCollection();

	private static class Cache {
		private static ArrayList<VisionGrid> fromNeighborsCache = new ArrayList<>(9);
	}

	public GameMap() {
		this.sessionList = new CopyOnWriteArrayList<GameObjectChar>();
		this.gameShiDao = new GameShiDao();
	}

	public static void notifyNpcDisappear(Npc npc) {
		for (GameObjectChar gameObjectChar : GameObjectCharMng.getGameObjectCharMap()) {
			if (gameObjectChar.chara == null) {
				continue;
			}
			if (gameObjectChar.gameMap.id == npc.getMapId()) {
				GameObjectChar.getGameObjectChar().sendOne(new MSG_DISAPPEAR(), npc.getId());
			}
		}
	}

	public List<GameObjectChar> getSessionList() {
		return this.sessionList;
	}

	/**
	 * 是否是正道殿地图
	 */
	public boolean isZhengDaoDianMap() {
		return id == ZhengDaoDianService.MAP_ID;
	}

	// 加载地图数据
	public void join(GameObjectChar gameObjectChar) {
		if (gameObjectChar == null) {
			return;
		}
		gameObjectChar.gameMap.leave(gameObjectChar);
		this.sessionList.remove(gameObjectChar);
		this.sessionList.add(gameObjectChar);
		Chara chara = gameObjectChar.chara;
		//清除上个地图数据
		Vo_45157_0 vo_45157_0 = new Vo_45157_0();
		vo_45157_0.id = chara.id;
		vo_45157_0.mapId = chara.mapid;
		gameObjectChar.sendOne(new M45157_0(), vo_45157_0);
		// 这个地图的所有NPC
		gameObjectChar.gameMap = this;
		chara.mapid = this.id;
		chara.mapName = this.name;
		
		//进入地图
		Vo_65505_0 vo_65505_1 = GameUtil.a65505(chara);
		gameObjectChar.sendOne(new MSG_ENTER_ROOM(), vo_65505_1);
		List<Npc> npcList = (List<Npc>) GameData.that.baseNpcService.findByMapId(gameObjectChar.gameMap.id);
		for (Npc npc : npcList) {
			if (NpcIds.isMapGuardianNpc(npc.getId())) {
				CharaStatue statue = CharaStatueService.getCharStaure(npc.getName() + "_" + 0);
				if (statue != null && statue.chengHao.indexOf("守护神") != -1) {
					continue;
				}
			}
			gameObjectChar.sendOne(new M65529_npc(), npc);
		}
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		//先加载自己
		gameObjectChar.sendOne(new M65529_0(), vo_65529_0);
		//自己不是隐身的话才会通知其他玩家
//		if(gameObjectChar.isHide == 0) {
			this.sendNoMe(new M65529_0(), vo_65529_0, gameObjectChar);
//		}else {
//			//隐身了就显示自己
//			gameObjectChar.sendOne(new M65529_0(), vo_65529_0);
//		}
		for (GameObjectChar gameSession : this.sessionList) {
			if (gameSession.ctx != null && gameSession.chara != null) {
				if (!isCanSee(chara, gameSession.chara)) {
					gameSession.gameMap.leave(gameObjectChar);
					continue;
				}
				//如果当前在举行婚礼是看不到对方的.
				if("marry".equals(gameSession.flag)) {
					continue;
				}
				//这里不再重复加载自己
//					if(gameSession.isHide == 1) {
//						continue;
//					}
				vo_65529_0 = GameUtil.a65529(gameSession.chara);
				gameObjectChar.sendOne(new M65529_0(), vo_65529_0);
				if(gameSession.chara.genchong_icon != 0) {
					//加载地图人的跟宠
					Vo_APPEAR followPet = GameUtil.followPet(gameSession);
					if(followPet != null) {
						gameObjectChar.sendOne(new M65529_0(), followPet);
					}
				}
			}
		}
		//让地图人加载我的跟宠
		GameUtil.genchongfei(gameObjectChar);
		// 显示boss
		GameMap gameMap = gameObjectChar.gameMap;
		GameCommonUtil.showBoss(chara, gameMap.id);
		if(GameShiDao.statzhuangtai == 2 && gameMap.name.equals("试道场")) {
			//加载元魔
			for(Vo_APPEAR yuanmo:gameMap.gameShiDao.shidaoyuanmo) {
				gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), yuanmo);
			}
		}
		GameCommonUtil.setCharaTitleFlag(chara);
		if (isZhengDaoDianMap()) {
			ZhengDaoDianService.onEnterMap(gameObjectChar);
		}
		// 初始化守护神
		MapGuardianService.onEnterMap(gameObjectChar.gameMap.id, gameObjectChar);
		// 传送阵
		List<NpcPoint> list = (List<NpcPoint>) GameData.that.baseNpcPointService.findByMapname(gameObjectChar.gameMap.name);
		gameObjectChar.sendOne(new M65531_0(), list);
		//异步加载
//		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
//			@Override
//			public void run() {
//				
//			}
//		});
	}

	private boolean isCanSee(Chara chara1, Chara chara2) {
		if (isZhengDaoDianMap()) {
			return chara1.polar == chara2.polar;
		}
		return true;
	}

	private VisionGrid getVisionGrid(int visionX, int visionY) {
		int key = GameUtil.comTwoInt16((short) visionX, (short) visionY);
		VisionGrid visionGrid = visionGrids.get(key);
		if (null == visionGrid) {
			visionGrid = new VisionGrid(visionX, visionY);
			visionGrids.put(key, visionGrid);
//            log.info("添加一个视野格子！mapName:{}, visionX:{}, visionY:{}", name, visionX, visionY);
		}
		return visionGrid;
	}

	private VisionGrid getVisionGridByPosition(int x, int y) {
		int visionX = x / VISION_GRID_WIDTH + ((x % VISION_GRID_WIDTH) == 0 ? 0 : 1);
		int visionY = y / VISION_GRID_WIDTH + ((y % VISION_GRID_WIDTH) == 0 ? 0 : 1);
		return getVisionGrid(visionX, visionY);
	}

	private void initVision(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		VisionGrid visionGrid = getVisionGridByPosition(chara.x, chara.y);
		ScenePlayer scenePlayer = new ScenePlayer(chara.id, visionGrid);
		sceneObjCollection.addScenePlayer(scenePlayer);
		visionGrid.addScenePlayer(scenePlayer);

		Cache.fromNeighborsCache.clear();
		ArrayList<VisionGrid> needAddGrids = getNeighbors(Cache.fromNeighborsCache, visionGrid);
		try {
			@SuppressWarnings("unchecked")
			INotifyHandler<SceneObj> notifyHandler = notifyHandlers.get(scenePlayer.getType());
			notifyHandler.notifyObjOthersIn(scenePlayer.getId(), needAddGrids);
			notifyHandler.notifyOthersObjIn(needAddGrids, scenePlayer);
		} finally {
			Cache.fromNeighborsCache.clear();
		}
	}

	private ArrayList<VisionGrid> getNeighbors(ArrayList<VisionGrid> visionGrids, VisionGrid visionGrid) {
		int visionX = visionGrid.getVisionX();
		int visionY = visionGrid.getVisionY();

		for (int x = visionX - 1; x <= visionX + 1; ++x) {
			for (int y = visionY - 1; y <= visionY + 1; ++y) {
				if (x < 0 || y < 0) {
					continue;
				}
				VisionGrid tmp = getVisionGrid(x, y);
				visionGrids.add(tmp);
			}
		}
		return visionGrids;
	}

	public void joinEx(GameObjectChar gameObjectChar) {
		if (gameObjectChar == null) {
			return;
		}
		gameObjectChar.gameMap.leave(gameObjectChar);

		Chara chara = gameObjectChar.chara;
		gameObjectChar.gameMap = this;
		chara.mapid = this.id;
		chara.mapName = this.name;

		Vo_45157_0 vo_45157_0 = new Vo_45157_0();
		vo_45157_0.id = chara.id;
		vo_45157_0.mapId = chara.mapid;
		gameObjectChar.sendOne(new M45157_0(), vo_45157_0);

		Vo_65505_0 vo_65505_1 = GameUtil.a65505(chara);
		gameObjectChar.sendOne(new MSG_ENTER_ROOM(), vo_65505_1);
		initVision(gameObjectChar);
		List<NpcPoint> list = GameData.that.baseNpcPointService.findByMapname(this.name);
		gameObjectChar.sendOne(new M65531_0(), list);
		GameCommonUtil.setCharaTitleFlag(chara);
		if (isHeroPubMap()) {
			HeroPubService.onEnterMap(gameObjectChar);
		}

		MapGuardianService.onEnterMap(this.id, gameObjectChar);
	}

	/**
	 * 是否是英雄会地图
	 */
	public boolean isHeroPubMap() {
		return id == HeroPubService.MAP_ID;
	}

	public void joinduiyuan(GameObjectChar gameObjectChar, Chara charaduizhang) {
		if (gameObjectChar == null)
			return;
		gameObjectChar.gameMap.leave(gameObjectChar);
		this.sessionList.remove(gameObjectChar);
		this.sessionList.add(gameObjectChar);
		Chara chara = gameObjectChar.chara;
		List<Npc> npcList = (List<Npc>) GameData.that.baseNpcService.findByMapId(this.id);
		gameObjectChar.gameMap = this;
		chara.x = charaduizhang.x;
		chara.y = charaduizhang.y;
		chara.mapid = charaduizhang.mapid;
		chara.mapName = charaduizhang.mapName;
		Vo_45157_0 vo_45157_0 = new Vo_45157_0();
		vo_45157_0.id = chara.id;
		vo_45157_0.mapId = charaduizhang.mapid;
		gameObjectChar.sendOne(new M45157_0(), vo_45157_0);
		Vo_65505_0 vo_65505_1 = GameUtil.a65505(chara);
		gameObjectChar.sendOne(new MSG_ENTER_ROOM(), vo_65505_1);
		for (Npc npc : npcList) {
			if (NpcIds.isMapGuardianNpc(npc.getId())) {
				CharaStatue statue = CharaStatueService.getCharStaure(npc.getName() + "_" + 0);
				if (statue != null && statue.chengHao.indexOf("守护神") != -1) {
					continue;
				}
			}
			gameObjectChar.sendOne(new M65529_npc(), npc);
		}

		MapGuardianService.onEnterMap(this.id, gameObjectChar);

		List<NpcPoint> list = (List<NpcPoint>) GameData.that.baseNpcPointService.findByMapname(this.name);
		gameObjectChar.sendOne(new M65531_0(), list);
		Vo_APPEAR vo_65529_0 = GameUtil.a65529(chara);
		this.send(new M65529_0(), vo_65529_0);
		for (GameObjectChar gameSession : this.sessionList) {
			if (gameSession.ctx != null && gameSession.chara != null) {
				//如果当前在举行婚礼是看不到对方的.
				if("marry".equals(gameSession.flag)) {
					continue;
				}
				vo_65529_0 = GameUtil.a65529(gameSession.chara);
				gameObjectChar.sendOne(new M65529_0(), vo_65529_0);
				GameUtil.genchongfei(gameSession);
			}
		}
		GameCommonUtil.setCharaTitleFlag(chara);
		// 显示boss
		GameCommonUtil.showBoss(chara, this.id);
	}

	public void leave(GameObjectChar gameObjectChar) {
		if (gameObjectChar != null) {
			this.sendNoMe(new M12285_0(), gameObjectChar.chara.id, gameObjectChar);
			this.sendNoMe(new MSG_DISAPPEAR(), gameObjectChar.chara.genchong_icon, gameObjectChar);
			this.sessionList.remove(gameObjectChar);
		}
	}

	@SuppressWarnings("unchecked")
	public void send(@SuppressWarnings("rawtypes") BaseWrite baseWrite, Object obj) {
		GameObjectChar currSession = GameObjectChar.getGameObjectChar();
		ByteBuf buff = baseWrite.write(obj);
		boolean hasSend = false;
		for (GameObjectChar gameSession : this.sessionList) {
			if (gameSession.ctx != null) {
				//如果是这两个消息
//				if(currSession != null) {
//					if(baseWrite.cmd() == 61661 || baseWrite.cmd() == 65529) {
//						if(currSession.isHide == 1 && 
//								gameSession.chara.id != currSession.chara.id) {
//							continue;
//						}
//					}
//				}
				ByteBuf copy = buff.copy();
				gameSession.send0(copy);
				if (gameSession != currSession) {
					continue;
				}
				hasSend = true;
			}
		}
		if (!hasSend && currSession != null) {
			currSession.send0(buff.copy());
		}
	}


	@SuppressWarnings("rawtypes")
	public void sendNoMe(BaseWrite baseWrite, Object obj, GameObjectChar gameObjectChar) {
		@SuppressWarnings("unchecked")
		ByteBuf buff = baseWrite.write(obj);
		for (GameObjectChar gameSession : this.sessionList) {
			if (!gameObjectChar.equals(gameSession) && gameSession.ctx != null) {
				ByteBuf copy = buff.copy();
				gameSession.send0(copy);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendNoMeduiwu(BaseWrite baseWrite, Object obj, GameObjectChar gameObjectChar) {
		ByteBuf buff = baseWrite.write(obj);
		for (GameObjectChar gameSession : this.sessionList) {
			boolean has = false;
			if (gameSession.gameTeam != null && gameSession.gameTeam.duiwu != null) {
				for (int i = 0; i < gameSession.gameTeam.duiwu.size(); ++i) {
					if (gameSession.equals(GameObjectCharMng.getGameObjectChar(gameSession.gameTeam.duiwu.get(i).id))) {
						has = true;
					}
				}
				if (has || gameSession.ctx == null) {
					continue;
				}
				ByteBuf copy = buff.copy();
				gameSession.send0(copy);
			} else {
				if (gameObjectChar.equals(gameSession) || gameSession.ctx == null) {
					continue;
				}
				ByteBuf copy = buff.copy();
				gameSession.send0(copy);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendNoMeyoujian(BaseWrite baseWrite, Object obj, GameObjectChar gameObjectChar) {
		ByteBuf buff = baseWrite.write(obj);
		for (GameObjectChar gameSession : this.sessionList) {
			if (gameObjectChar.equals(gameSession) && gameSession.ctx != null) {
				ByteBuf copy = buff.copy();
				gameSession.send0(copy);
			}
		}
	}

	// 是否是动态地图
	public boolean isZone() {
		return this.map_type > 0;
	}

	// 是否是副本
	public boolean isDugeno() {
		return this.map_type > 1;
	}
}
