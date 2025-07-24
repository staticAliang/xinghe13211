package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.AccessibilityMap;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.util.GameConfig;

import io.netty.util.internal.ThreadLocalRandom;

// 游戏攻城的类
public class GameGongCheng {
	public ConcurrentHashMap<Integer, Vo_APPEAR> haidaoGuaiwu;

	// 继续战神的数量和海盗对象
	public int zhanshenNum; // 设置战神的最大数量上限
	public ConcurrentHashMap<Integer, Vo_APPEAR> zhanshenGuaiwu;
	public int shuaGuaiNum;
	public int shuaGuaizhuangtai;
	public long shuaGuaiTime;
	public static Map<Integer, Integer> mapId;
	public List<Vo_APPEAR> shuaGuai;
	public Map<Integer, Vo_APPEAR> gongchengBoss;
	public static List<Vo_APPEAR> dengdaishuaGuai;
	//地图
	public static final String mapnames = "五龙窟一层,五龙窟二层,五龙窟三层,五龙窟四层,五龙窟五层,轩辕坟二层,轩辕坟三层,轩辕坟一层,百花谷一,百花谷二,百花谷三,百花谷四,百花谷五,百花谷六,百花谷七,五龙山,终南山,凤凰山,乾元山,骷髅山,热砂荒漠,北海沙滩,雪域冰原,蓬莱岛,桃柳林,海底迷宫,官道南,昆仑云海,绝人阵,绝仙阵,地绝阵,天绝阵,卧龙坡,官道北,迷境花树,十里坡,幽冥涧,方丈岛,弑神殿,断魂窟";
	//刷新信息
	private final static String gongchengs = "百年黑熊精:轩辕坟一层、十里坡、轩辕坟二层:120:6203,百年狂狮怪:五龙山、乾元山、终南山:120:6209,百年刺猬精:凤凰山、骷髅山、轩辕坟三层:120:6210,百年猪妖:五龙窟一层、五龙窟二层、五龙窟三层:120:6208,百年象精:五龙窟四层、蓬莱岛、五龙窟五层:120:6207,百花羞:幽冥涧、百花谷一、百花谷二:120:6241,牛魔王:百花谷三、百花谷四、百花谷五:120:6259,夜叉王:百花谷六、百花谷七、绝人阵 :120:6277,罗刹王:绝仙阵、地绝阵、天绝阵:120:6278,白骨精:水云间、迷境花树:130:6280,孔雀妖姬:水云间、迷境花树:140:6287";
	
	public GameGongCheng() {
		this.shuaGuaizhuangtai = 0;
		this.shuaGuaiTime = System.currentTimeMillis();
		this.shuaGuai = new LinkedList<Vo_APPEAR>();

		this.haidaoGuaiwu = new ConcurrentHashMap<Integer, Vo_APPEAR>();
		this.gongchengBoss = new HashMap<Integer, Vo_APPEAR>();
		this.zhanshenNum = 25;
		this.zhanshenGuaiwu = new ConcurrentHashMap<Integer, Vo_APPEAR>();
	}

	//刷地狱
	public static void sendDiyu(GameGongCheng gameShuaGuai, String name)
	{
		Vo_16383_0 vo_16383_5 = new Vo_16383_0();
		vo_16383_5.channel = 6;
		vo_16383_5.id = 0;
		vo_16383_5.name = "";
		vo_16383_5.msg = "#Y"+name+"突破十八层地狱#n活动开始了，请各位道友前往#R五大门派山门#n击杀地狱麒麟！#R奖励颇丰#n！！！";
		vo_16383_5.time = (int) (System.currentTimeMillis() / 1000L);
		vo_16383_5.privilege = 0;
		vo_16383_5.server_name = GameConfig.lineName;
		vo_16383_5.show_extra = 1;
		vo_16383_5.compress = 0;
		vo_16383_5.orgLength = 65535;
		vo_16383_5.cardCount = 0;
		vo_16383_5.voiceTime = 0;
		vo_16383_5.token = "";
		vo_16383_5.checksum = 0;
		GameObjectCharMng.sendAll(new M16383_0(), vo_16383_5);
		List<RenwuMonster> renwuMonsters = GameData.that.baseRenwuMonsterService.findByName("地狱麒麟");
		List<Integer> ids = new ArrayList<>();
		for (int i = 0; i < 1; ++i) {
			Random random = new Random();
			if (renwuMonsters.size() == 0) {
				continue;
			}
			RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
			com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());
			List<Pet> findByName = (List<Pet>) GameData.that.basePetService.findByName(renwuMonster.getName());

			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			int id = getCard();
			ids.add(id);
			vo_65529_0.id = id;
			vo_65529_0.name = findByName.get(0).getName();
			vo_65529_0.leixing = random.nextInt(5) + 1;
			vo_65529_0.mapid = map.getMapId();
			vo_65529_0.x = renwuMonster.getX();
			vo_65529_0.y = renwuMonster.getY();
			vo_65529_0.dir = 1;
			vo_65529_0.icon = renwuMonster.getIcon();
			vo_65529_0.org_icon = renwuMonster.getIcon();
			vo_65529_0.portrait = renwuMonster.getIcon();
			GameLine.gameGongCheng.zhanshenGuaiwu.put(id, vo_65529_0);
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, map.getMapId());
		}
	}

	// 刷战神
	public static void sendZhanshen(GameGongCheng gameShuaGuai) {
		Vo_16383_0 vo_16383_5 = new Vo_16383_0();
		vo_16383_5.channel = 6;
		vo_16383_5.id = 0;
		vo_16383_5.name = "";
		vo_16383_5.msg = "#Y战神下凡#n活动开始了，请各位道友前往#R五大门派山门#n击杀战神！#R奖励颇丰#n，需要#Y三人#n才能挑战！！！";
		vo_16383_5.time = (int) (System.currentTimeMillis() / 1000L);
		vo_16383_5.privilege = 0;
		vo_16383_5.server_name = GameConfig.lineName;
		vo_16383_5.show_extra = 1;
		vo_16383_5.compress = 0;
		vo_16383_5.orgLength = 65535;
		vo_16383_5.cardCount = 0;
		vo_16383_5.voiceTime = 0;
		vo_16383_5.token = "";
		vo_16383_5.checksum = 0;
		GameObjectCharMng.sendAll(new M16383_0(), vo_16383_5);
		List<RenwuMonster> renwuMonsters = GameData.that.baseRenwuMonsterService.findByName("战神");
		List<Integer> ids = new ArrayList<>();
		for (int i = 0; i < 30; ++i) {
			Random random = new Random();
			if (renwuMonsters.size() == 0) {
				continue;
			}
			RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
			com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());
			List<Pet> findByName = (List<Pet>) GameData.that.basePetService.findByName(renwuMonster.getName());

			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			int id = getCard();
			ids.add(id);
			vo_65529_0.id = id;
			vo_65529_0.name = findByName.get(0).getName();
			vo_65529_0.leixing = random.nextInt(5) + 1;
			vo_65529_0.mapid = map.getMapId();
			vo_65529_0.x = renwuMonster.getX();
			vo_65529_0.y = renwuMonster.getY();
			vo_65529_0.dir = 1;
			vo_65529_0.icon = renwuMonster.getIcon();
			vo_65529_0.org_icon = renwuMonster.getIcon();
			vo_65529_0.portrait = renwuMonster.getIcon();
			GameLine.gameGongCheng.zhanshenGuaiwu.put(id, vo_65529_0);
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, map.getMapId());
		}
		//40分钟后清除所有战神
		GameData.that.redisUtils.set("CLEAR_ZHANSHEN="+JSONObject.toJSONString(ids), "清除战神标识", 60*40);
	}
	// 刷战神
	public static void sendZhanshen2(GameGongCheng gameShuaGuai,Integer num) {
		Vo_16383_0 vo_16383_5 = new Vo_16383_0();
		vo_16383_5.channel = 6;
		vo_16383_5.id = 0;
		vo_16383_5.name = "";
		vo_16383_5.msg = "#Y战神下凡#n活动开始了，请各位道友前往#R五大门派山门#n击杀战神！#R奖励颇丰#n，需要#Y三人#n才能挑战！！！";
		vo_16383_5.time = (int) (System.currentTimeMillis() / 1000L);
		vo_16383_5.privilege = 0;
		vo_16383_5.server_name = GameConfig.lineName;
		vo_16383_5.show_extra = 1;
		vo_16383_5.compress = 0;
		vo_16383_5.orgLength = 65535;
		vo_16383_5.cardCount = 0;
		vo_16383_5.voiceTime = 0;
		vo_16383_5.token = "";
		vo_16383_5.checksum = 0;
		GameObjectCharMng.sendAll(new M16383_0(), vo_16383_5);
		List<RenwuMonster> renwuMonsters = GameData.that.baseRenwuMonsterService.findByName("战神");
		List<Integer> ids = new ArrayList<>();
		for (int i = 0; i < num; ++i) {
			Random random = new Random();
			if (renwuMonsters.size() == 0) {
				continue;
			}
			RenwuMonster renwuMonster = renwuMonsters.get(random.nextInt(renwuMonsters.size()));
			com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());
			List<Pet> findByName = (List<Pet>) GameData.that.basePetService.findByName(renwuMonster.getName());

			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			int id = getCard();
			ids.add(id);
			vo_65529_0.id = id;
			vo_65529_0.name = findByName.get(0).getName();
			vo_65529_0.leixing = random.nextInt(5) + 1;
			vo_65529_0.mapid = map.getMapId();
			vo_65529_0.x = renwuMonster.getX();
			vo_65529_0.y = renwuMonster.getY();
			vo_65529_0.dir = 1;
			vo_65529_0.icon = renwuMonster.getIcon();
			vo_65529_0.org_icon = renwuMonster.getIcon();
			vo_65529_0.portrait = renwuMonster.getIcon();
			GameLine.gameGongCheng.zhanshenGuaiwu.put(id, vo_65529_0);
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, map.getMapId());
		}
		//40分钟后清除所有战神
		GameData.that.redisUtils.set("CLEAR_ZHANSHEN="+JSONObject.toJSONString(ids), "清除战神标识", 60*40);
	}


	// 刷新海盗
	public static void sendHaidao(GameGongCheng gameShuaGuai) {

		GameUtil.sendYaoYan("#Y海盗入侵#n活动已经开始了，请各位道友前往#R#Z东海渔村#Z#n消灭海盗！该活动奖励丰富,请积极参与！！！");
		com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("东海渔村");
		String[] haidaoPosition = GameConfig.haidaoPosition;
		for (int i = 0; i < GameConfig.config.getHaidao().getHaidaoNum(); ++i) {
			Random random = new Random();
			int haidaoXY = random.nextInt(haidaoPosition.length);
			String xyStr = haidaoPosition[haidaoXY];
			String[] xyArr = xyStr.split("#");
			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			int id = getCard();
			vo_65529_0.id = id;
			vo_65529_0.name = "海盗";
			vo_65529_0.leixing = random.nextInt(5) + 1;
			vo_65529_0.mapid = map.getMapId();
			vo_65529_0.x = Integer.valueOf(xyArr[0]);
			vo_65529_0.y = Integer.valueOf(xyArr[1]);
			vo_65529_0.dir = 1;
			vo_65529_0.icon = 6230;
			vo_65529_0.org_icon = 6230;
			vo_65529_0.portrait = 6230;
			GameLine.gameGongCheng.haidaoGuaiwu.put(vo_65529_0.id, vo_65529_0);
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, map.getMapId());
		}
		// 2小时后把海盗给清除了.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				GameGongCheng.cleanHaiDao();
				timer.cancel();
			}
		}, 120 * 60 * 1000);
	}

	// 怪物攻城，注意：type=10、13、12分别对应攻城BOSS、海盗和战神,11,12是上古和万年
	public static void sendShuaGuai(int count) {
		for (int i = 0; i < count; ++i) {
			String[] info = gongchengs.split(",");
			int nextInt = ThreadLocalRandom.current().nextInt(info.length);
			String string = info[nextInt];
			String[] split = string.split("\\:");
			String bossName = split[0];
			// 获取道刷新地图的集合
			String[] mapNameArr = split[1].split("、");
			//等级
			int level = Integer.valueOf(split[2]);
			int icon = Integer.valueOf(split[3]);
			// 随机获取一个星星刷新的地图
			String flushMapName = mapNameArr[(int) (Math.random() * mapNameArr.length)];
			GameMap map = GameLine.getGameMap(1, flushMapName);
			if (map == null) {
				return;
			}
			// 随机获取一个坐标位置
			List<AccessibilityMap> result = GameData.that.accessibilityMapService.findByMapName(flushMapName);
			if (result == null || result.isEmpty()) {
				return;
			}
			int index = ThreadLocalRandom.current().nextInt(result.size());
			AccessibilityMap am = result.get(index);
			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			int id = getCard();
			vo_65529_0.id = id;
			vo_65529_0.name = bossName;
			vo_65529_0.mapid = map.id;
			vo_65529_0.x = am.getX();
			vo_65529_0.y = am.getY();
			vo_65529_0.dir = 1;
			vo_65529_0.level = level;
			vo_65529_0.icon = icon;
			vo_65529_0.org_icon =icon;
			vo_65529_0.portrait = icon;
			vo_65529_0.uuid = "gongchengBoss";
			GameLine.gameGongCheng.gongchengBoss.put(id,vo_65529_0);
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, map.id);
			
			GameUtil.sendYaoYan(StringUtils.join("听闻#Y",bossName,"#n带着一群小妖，准备登陆","#Z",flushMapName,"#Z","抢掠民财，请中洲大陆各路英雄前来为民除害。"));
		}
		//45分钟未杀清除
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {
			@Override
			public void run() {
				for(Map.Entry<Integer, Vo_APPEAR> v:GameLine.gameGongCheng.gongchengBoss.entrySet()) {
					GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), v.getKey(), v.getValue().mapid);
				}
				GameLine.gameGongCheng.gongchengBoss.clear();
				timer2.cancel();
			}
		}, 45 * 60 * 1000);
	}

	public static int getCard() {
		return GameCommonUtil.generateBossId();
	}

	/**
	 * 清除地图上所有海盗
	 */
	public static void cleanHaiDao() {
		for (Map.Entry<Integer, Vo_APPEAR> m : GameLine.gameGongCheng.haidaoGuaiwu.entrySet()) {
			Vo_APPEAR i = m.getValue();
			i.isHide = 1;
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), i, i.mapid);
		}
		Vo_APPEAR vo_65529_02 = new Vo_APPEAR();
		for (Map.Entry<Integer, Integer> m : GameGongCheng.mapId.entrySet()) {
			vo_65529_02.id = m.getKey();
			vo_65529_02.isHide = 1;
			vo_65529_02.name = "海盗";
			GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_02, 11000);
		}
		// 清空之前的海盗.
		GameLine.gameGongCheng.haidaoGuaiwu.clear();
		GameConfig.canzhanBoos.clear();
	}

	static {
		GameGongCheng.mapId = new HashMap<Integer, Integer>();
		GameGongCheng.dengdaishuaGuai = new LinkedList<Vo_APPEAR>();
	}
}
