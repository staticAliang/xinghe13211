package com.fengshen.server.game;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GameObjectCharMng {
	private static List<GameObjectChar> gameObjectCharList;

	private static Int2ObjectMap<GameObjectChar> gameObjectCharMap = new Int2ObjectOpenHashMap<>();

	public static Collection<GameObjectChar> getGameObjectCharMap() {
		return gameObjectCharMap.values();
	}

	public static void add(GameObjectChar gameObjectChar) {
		if (GameObjectCharMng.gameObjectCharList.contains(gameObjectChar)) {
			for (GameObjectChar gameSession : GameObjectCharMng.gameObjectCharList) {
				if (gameObjectChar.chara.id == gameSession.chara.id) {
					gameSession.ctx.disconnect();
					gameObjectChar.gameTeam = gameSession.gameTeam;
					if (gameSession.gameTeam != null && gameSession.gameTeam.duiwu != null
							&& gameSession.gameTeam.duiwu.size() > 0) {
						for (int i = 0; i < gameSession.gameTeam.duiwu.size(); ++i) {
							if (gameObjectChar.chara.id == gameSession.gameTeam.duiwu.get(i).id) {
								gameSession.gameTeam.duiwu.set(i, gameObjectChar.chara);
								break;
							}
						}
					}
					GameObjectCharMng.gameObjectCharList.remove(gameSession);
				}
			}
		}
		GameObjectCharMng.gameObjectCharList.add(gameObjectChar);
	}

	// 在世界里面喊话
	
	public static void sendAll(BaseWrite baseWrite, Object obj) {
		for (int i = 0; i < GameObjectCharMng.gameObjectCharList.size(); ++i) {
			GameObjectChar session = GameObjectCharMng.gameObjectCharList.get(i);
			ByteBuf write = baseWrite.write(obj);
			// 给单个session发送消息
			session.ctx.writeAndFlush(write);
		}
	}

	public static List<GameObjectChar> getGameObjectCharList() {
		return GameObjectCharMng.gameObjectCharList;
	}

	public static void sendAllmap(BaseWrite baseWrite, Object obj, int mapid) {
		for (int i = 0; i < GameObjectCharMng.gameObjectCharList.size(); ++i) {
			GameObjectChar gameObjectChar = GameObjectCharMng.gameObjectCharList.get(i);
			if (gameObjectChar.gameMap.id == mapid) {
				ByteBuf write = baseWrite.write(obj);
				gameObjectChar.ctx.writeAndFlush(write);
			}
		}
	}

	public static void sendAllmapname(BaseWrite baseWrite, Object obj, String mapname) {
		for (int i = 0; i < GameObjectCharMng.gameObjectCharList.size(); ++i) {
			GameObjectChar gameObjectChar = GameObjectCharMng.gameObjectCharList.get(i);
			if (gameObjectChar.gameMap.name.equals(mapname)) {
				ByteBuf write = baseWrite.write(obj);
				gameObjectChar.ctx.writeAndFlush(write);
			}
		}
	}

	/**
	 * 根据id获取玩家总控对象
	 * 
	 * @param id
	 * @return
	 */
	public static GameObjectChar getGameObjectChar(int id) {
		for (GameObjectChar gameObjectChar : GameObjectCharMng.gameObjectCharList) {
			if (gameObjectChar.chara.id == id) {
				return gameObjectChar;
			}
		}
		return null;
	}

	/**
	 * 根据名称获取玩家总控对象
	 * 
	 * @param name
	 * @return
	 */
	public static GameObjectChar getGameObjectChar(String name) {
		for (GameObjectChar gameObjectChar : GameObjectCharMng.gameObjectCharList) {
			if (gameObjectChar.chara.name.equals(name)) {
				return gameObjectChar;
			}
		}
		return null;
	}
	
	/**
	 * 根据连接的客户端id
	 * @param id
	 * @return
	 */
	public static GameObjectChar getGameObjectCharByChannelId(String id) {
		for (GameObjectChar gameObjectChar : GameObjectCharMng.gameObjectCharList) {
			if (gameObjectChar.channelId.equals(id)) {
				return gameObjectChar;
			}
		}
		return null;
	}

	/**
	 * 根据uuid获取玩家总控对象
	 * 
	 * @param uuid
	 * @return
	 */
	public static GameObjectChar getGameObjectCharByUUid(String uuid) {
		for (GameObjectChar gameObjectChar : GameObjectCharMng.gameObjectCharList) {
			if (gameObjectChar.chara.uuid.equals(uuid)) {
				return gameObjectChar;
			}
		}
		return null;
	}

	public static List<GameObjectChar> getAll() {
		return GameObjectCharMng.gameObjectCharList;
	}

	public static void remove(GameObjectChar gameObjectChar) {
		gameObjectChar.chara.updatetime = System.currentTimeMillis();
		save(gameObjectChar);
	}

	public static void save(GameObjectChar gameObjectChar) {
		gameObjectChar.characters.setData(JSONObject.toJSONString(gameObjectChar.chara));
		GameData.that.baseCharactersService.updateById(gameObjectChar.characters);
	}

	public static void del(GameObjectChar gameObjectChar) {
		GameObjectCharMng.gameObjectCharList.remove(gameObjectChar);
	}

	static {
		gameObjectCharList = new CopyOnWriteArrayList<>();
	}
}
