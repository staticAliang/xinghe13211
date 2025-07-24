package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

// 游戏线路对象
@Service("glllbawsdfawelllll")
@Scope("prototype")
public class GameLine {
	public int lineNum;
	public String lineName;
	private List<GameMap> gameRoomList;
	// 地图名称和地图的映射
	private Map<String, GameMap> gameRoomNameMap;
	public static GameShuaGuai gameShuaGuai;
	public static GameGongCheng gameGongCheng;
	private List<GameZone> gameZoneList = new ArrayList<>();
	public GameLine() {
		this.gameRoomList = new ArrayList<>();
		this.gameRoomNameMap = new HashMap<>();
	}

	// 初始化线路，首先初始化地图
	public void init() {
		final List<com.fengshen.db.domain.Map> all = GameData.that.baseMapService.findAll();
		for (final com.fengshen.db.domain.Map map : all) {
			final GameMap gameMap = GameCore.getBean("gmmmasdfasdfmmmm", GameMap.class);
			gameMap.id = map.getMapId();
			gameMap.name = map.getName();
			gameMap.x = map.getX();
			gameMap.y = map.getY();
			this.gameRoomList.add(gameMap);
			this.gameRoomNameMap.put(gameMap.name, gameMap);
		}
	}

	public static GameMap getGameMapname(final int line, final String mapidname) {
		final GameLine gameLine = GameCore.getGameLine(line);
		for (final GameMap gameMap : gameLine.gameRoomList) {
			if (gameMap.name.equals(mapidname)) {
				return gameMap;
			}
		}
		return null;
	}

	public static GameMap getGameMap(final int line, final int mapid) {
		final GameLine gameLine = GameCore.getGameLine(line);
		for (final GameMap gameMap : gameLine.gameRoomList) {
			if (gameMap.id == mapid) {
				return gameMap;
			}
		}
		return null;
	}

	public static GameMap getGameMap(final int line, final String mapName) {
		final GameLine gameLine = GameCore.getGameLine(line);
		return gameLine.gameRoomNameMap.get(mapName);
	}

	static {
		GameLine.gameShuaGuai = new GameShuaGuai();
		GameLine.gameGongCheng = new GameGongCheng();
	}

	// 创建动态地图，需要创建者保存uid,主动删除（动态地图没人的时候也会自动删除）
	public static GameZone createGameZone(int line, int mapID) {
		GameLine gameLine = GameCore.getGameLine(line);
		GameZone gameZone = new GameZone();
		for (GameMap gameMap : gameLine.gameRoomList) {
			if (gameMap.id == mapID) {
				gameZone.id = mapID;
				gameZone.name = gameMap.name;
				gameZone.x = gameMap.x;
				gameZone.y = gameMap.y;
				break;
			}
		}
		if (gameZone.id == 0) {
			return null;
		}

		gameZone.uid = UUID.randomUUID().toString();
		gameLine.gameZoneList.add(gameZone);

		return gameZone;
	}
	
	/**
	 * 进入帮派总坛
	 * @param line 当前线路
	 * @param mapid 地图id
	 * @param uuid 帮派名称
	 * @return
	 */
	public static GameZone enterPartyMap(int mapid, String uuid) {
		GameLine gameLine = GameCore.getGameLine(1);
		//如果存在则不创建
		for(GameZone g:gameLine.gameZoneList) {
			if(g.uid.equals(uuid)) {
				return g;
			}
		}
		GameZone gameZone = new GameZone();
		for (GameMap gameMap : gameLine.gameRoomList) {
			if (gameMap.id == mapid) {
				gameZone.id = mapid;
				gameZone.name = gameMap.name;
				gameZone.x = gameMap.x;
				gameZone.y = gameMap.y;
				break;
			}
		}
		if (gameZone.id == 0) {
			return null;
		}
		gameZone.uid = uuid;
		gameLine.gameZoneList.add(gameZone);
		return gameZone;
	}
	
	/**
	 * 获取动态地图
	 * @param line 线路
	 * @param uuid 
	 * @return
	 */
	public static List<GameObjectChar> getZoneGameMapSessionList(int line, String uuid) {
		GameLine gameLine = GameCore.getGameLine(line);
		for (GameZone gameZone : gameLine.gameZoneList) {
			if (gameZone.uid.equals(uuid)) {
				return gameZone.sessionList;
			}
		}
		return new ArrayList<>();
	}

	public static void deleteZoneGameMap(int line, String uuid) {
		GameLine gameLine = GameCore.getGameLine(line);
		for (GameZone gameZone : gameLine.gameZoneList) {
			if (gameZone.uid.equals(uuid)) {
				gameLine.gameZoneList.remove(gameZone);
				return;
			}
		}
	}
	
	/**
	 * 创建试道地图.
	 * @param line
	 * @param mapID
	 * @param chara
	 * @return
	 */
	public static GameZone createShidaoZoneGameMap(int line, int mapId) {
		GameLine gameLine = GameCore.getGameLine(line);
		GameZone gameZone = new GameZone();
		if(gameLine != null) {
			for (GameMap gameMap : gameLine.gameRoomList) {
				if (gameMap.id == mapId) {
					gameZone.id = mapId;
					gameZone.name = gameMap.name;
					gameZone.x = gameMap.x;
					gameZone.y = gameMap.y;
					gameZone.uid = GameCommonUtil.UUID();
					break;
				}
			}
		}
		if (gameZone.id == 0) {
			return null;
		}
		return gameZone;
	}
}
