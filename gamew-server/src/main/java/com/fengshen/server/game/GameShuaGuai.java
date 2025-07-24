package com.fengshen.server.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;

public class GameShuaGuai {
	public int shuaXingzhuangtai;
	public long shuaXingTime;
	public List<Vo_APPEAR> shuaXing;
	public static HashMap<Integer, Vo_APPEAR> shanggu = new HashMap<Integer, Vo_APPEAR>();
	public static HashMap<Integer, Vo_APPEAR> wannian = new HashMap<Integer, Vo_APPEAR>();
	public static HashMap<Integer, Vo_APPEAR> guiguai = new HashMap<Integer, Vo_APPEAR>();
	public static List<Vo_APPEAR> dengdaishuaXing;
	// 悬赏
	public static HashMap<Integer, Vo_APPEAR> xuanshang = new HashMap<>();

	public GameShuaGuai() {
		this.shuaXingzhuangtai = 0;
		this.shuaXingTime = System.currentTimeMillis();
		this.shuaXing = new LinkedList<Vo_APPEAR>();
	}

	// 生成上古妖王时按照角色等级生成
	public static String shangguWithLevel(String username, int dengji) {
		Random random = new Random();

		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.level = dengji;
		vo_65529_0.type = 2;
		vo_65529_0.leixing = random.nextInt(5) + 1;

		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService.findByType(11);
		RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
		Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());

		vo_65529_0.id = GameCommonUtil.generateBossId();
		vo_65529_0.name = renwuMonster.getName();
		vo_65529_0.mapid = map.getMapId();
		vo_65529_0.x = renwuMonster.getX();
		vo_65529_0.y = renwuMonster.getY();
		vo_65529_0.dir = 1;
		vo_65529_0.icon = renwuMonster.getIcon();
		vo_65529_0.portrait = vo_65529_0.icon;
		String msg = "大事不好了！#Y%s#n挖掘宝藏时，不小心将#Y神龙真人#n禁锢的#Y上古妖王#n(#R%d级#n)放了出来，据说那妖王现正在#R%s#n#P|%s(%d,%d)::上古妖王|E=挑战上古妖王#P为害世人，正道人士请速往降服！";
		msg = String.format(msg, username, dengji, renwuMonster.getMapName(), renwuMonster.getMapName(),
				renwuMonster.getX(), renwuMonster.getY());
		GameUtil.sendYaoYan(msg);

		GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, vo_65529_0.mapid);
		GameShuaGuai.shanggu.put(vo_65529_0.id, vo_65529_0);
		GameData.that.redisUtils.set(StringUtils.join("BOSS_WAIT_" , vo_65529_0.id),StringUtils.join(String.valueOf(System.currentTimeMillis() + 60 * 1000 * 3) + ":"
						, GameObjectChar.getGameObjectChar().chara.name),
				60 * 3);
		//保留10分钟
		GameData.that.redisUtils.set(StringUtils.join("REMOVE_SHANGGU:",vo_65529_0.id,":",vo_65529_0.mapid), 60*10, 60*10);
		return msg;
	}

	// 生成万年妖王时按照角色等级生成
	public static String wannianWithLevel(String username, int dengji) {
		Random random = new Random();

		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.level = dengji;
		vo_65529_0.type = 2;
		vo_65529_0.leixing = random.nextInt(5) + 1;

		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService.findByType(12);
		RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
		Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());

		vo_65529_0.id = GameCommonUtil.generateBossId();
		vo_65529_0.name = renwuMonster.getName();
		vo_65529_0.mapid = map.getMapId();
		vo_65529_0.x = renwuMonster.getX();
		vo_65529_0.y = renwuMonster.getY();
		vo_65529_0.dir = 1;
		vo_65529_0.icon = renwuMonster.getIcon();
		vo_65529_0.portrait = vo_65529_0.icon;
		String msg = "大事不好了！#Y%s#n挖掘宝藏时，不小心将#Y神龙真人#n禁锢的#Y万年妖王#n(#R%d级#n)放了出来，据说那妖王现正在#R%s#n#P|%s(%d,%d)::万年妖王|E=挑战万年妖王#P为害世人，正道人士请速往降服！";
		msg = String.format(msg, username, dengji, renwuMonster.getMapName(), renwuMonster.getMapName(),
				renwuMonster.getX(), renwuMonster.getY());
		GameUtil.sendYaoYan(msg);

		GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, vo_65529_0.mapid);
		wannian.put(vo_65529_0.id, vo_65529_0);
		GameData.that.redisUtils.set(StringUtils.join("BOSS_WAIT_",vo_65529_0.id), StringUtils.join(
				String.valueOf(System.currentTimeMillis() + 60 * 1000 * 3),":",GameObjectChar.getGameObjectChar().chara.name),
				60 * 3);
		//保留10分钟
		GameData.that.redisUtils.set(StringUtils.join("REMOVE_WANNIAN:",vo_65529_0.id,":",vo_65529_0.mapid), 60*10, 60*10);
		return msg;
	}

	// 生成鬼怪时按照角色等级生成
	public static String guiguaiWithLevel(String username, int dengji) {
		Random random = new Random();

		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.level = dengji;
		vo_65529_0.type = 2;
		vo_65529_0.leixing = random.nextInt(5) + 1;

		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService.findByType(12);
		RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
		Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());

		vo_65529_0.id = GameCommonUtil.generateBossId();
		vo_65529_0.name = "僵尸王";
		vo_65529_0.mapid = map.getMapId();
		vo_65529_0.x = renwuMonster.getX();
		vo_65529_0.y = renwuMonster.getY();
		vo_65529_0.dir = 1;
		vo_65529_0.icon = 6113;
		vo_65529_0.portrait = vo_65529_0.icon;
		String msg = "大事不好了！#Y%s#n挖掘宝藏时，不小心将#Y神龙真人#n禁锢的#Y鬼怪#n(#R%d级#n)放了出来，目前这群鬼怪已逃到#R%s#n#P|%s(%d,%d)::僵尸王|E=挑战鬼怪#P为害世人，正道人士请速往降服！";
		msg = String.format(msg, username, dengji, renwuMonster.getMapName(), renwuMonster.getMapName(),
				renwuMonster.getX(), renwuMonster.getY());
		GameUtil.sendYaoYan(msg);	

		GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, vo_65529_0.mapid);
		guiguai.put(vo_65529_0.id, vo_65529_0);
		GameData.that.redisUtils.set(StringUtils.join("BOSS_WAIT_",vo_65529_0.id),StringUtils.join(
				String.valueOf(System.currentTimeMillis()+60*1000*3),":",GameObjectChar.getGameObjectChar().chara.name),
				60*3);
		//保留10分钟
		GameData.that.redisUtils.set(StringUtils.join("REMOVE_GUIGUAI:",vo_65529_0.id,":",vo_65529_0.mapid), 60*10, 60*10);
		return msg;
	}

	// 挖宝放出了上古妖王
	public static String shanggu(String username) {
		Random random = new Random();
		Integer[] aaa = { 170, 160, 150, 140, 130, 120, 110, 90, 100, 80, 70 };
		int level = aaa[random.nextInt(aaa.length)];

		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.level = level + random.nextInt(10) + 1;
		vo_65529_0.type = 2;
		vo_65529_0.leixing = random.nextInt(5) + 1;

		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService.findByType(11);
		RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
		Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());

		vo_65529_0.id = GameCommonUtil.generateBossId();
		vo_65529_0.name = renwuMonster.getName();
		vo_65529_0.mapid = map.getMapId();
		vo_65529_0.x = renwuMonster.getX();
		vo_65529_0.y = renwuMonster.getY();
		vo_65529_0.dir = 1;
		vo_65529_0.icon = renwuMonster.getIcon();
		vo_65529_0.portrait = vo_65529_0.icon;

		String msg = "大事不好了！#Y%s#n挖掘宝藏时，不小心将#Y神龙真人#n禁锢的#Y上古妖王#n(#R%d级#n)放了出来，据说那妖王现正在#R%s#n#P|%s(%d,%d)::上古妖王|E=挑战上古妖王#P为害世人，正道人士请速往降服！";
		msg = String.format(msg, username, level, renwuMonster.getMapName(), renwuMonster.getMapName(),
				renwuMonster.getX(), renwuMonster.getY());
		GameUtil.sendYaoYan(msg);

		GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, vo_65529_0.mapid);
		GameShuaGuai.shanggu.put(vo_65529_0.id, vo_65529_0);
		GameData.that.redisUtils.set(StringUtils.join("BOSS_WAIT_" , vo_65529_0.id),StringUtils.join(String.valueOf(System.currentTimeMillis() + 60 * 1000)
				,":",GameObjectChar.getGameObjectChar().chara.name),60);
		//保留10分钟
		GameData.that.redisUtils.set(StringUtils.join("REMOVE_SHANGGU:",vo_65529_0.id,":",vo_65529_0.mapid), 60*10, 60*10);
		return msg;
	}

	public static int waiguan(int polar, int sex) {
		int waiguan = 0;
		if (polar == 1 && sex == 1) {
			waiguan = 6001;
		}
		if (polar == 2 && sex == 1) {
			waiguan = 7002;
		}
		if (polar == 3 && sex == 1) {
			waiguan = 7003;
		}
		if (polar == 4 && sex == 1) {
			waiguan = 6004;
		}
		if (polar == 5 && sex == 1) {
			waiguan = 6005;
		}
		if (polar == 1 && sex == 2) {
			waiguan = 7001;
		}
		if (polar == 2 && sex == 2) {
			waiguan = 6002;
		}
		if (polar == 3 && sex == 2) {
			waiguan = 6003;
		}
		if (polar == 4 && sex == 2) {
			waiguan = 7004;
		}
		if (polar == 5 && sex == 2) {
			waiguan = 7005;
		}
		return waiguan;
	}
}