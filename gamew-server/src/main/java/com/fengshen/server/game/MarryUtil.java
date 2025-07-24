package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.WeddingList;
import com.fengshen.db.service.chara.WeddingListService;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST.Item;
import com.fengshen.server.data.vo.system.Vo_BANNER;
import com.fengshen.server.data.vo.user.Vo_ANIMATE_IN_CHAR;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.vo.weddingBook.Vo_WB_HOME_INFO;
import com.fengshen.server.data.vo.weddingBook.Vo_WB_HOME_PIC;
import com.fengshen.server.data.write.M16431_0;
import com.fengshen.server.data.write.M4121_0;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.animate.MSG_ANIMATE_IN_UI;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.jiehun.MSG_WEDDING_ALL_LIST;
import com.fengshen.server.data.write.jiehun.MSG_WEDDING_NOW;
import com.fengshen.server.data.write.system.MSG_BANNER;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.weddingBook.MSG_WB_HOME_INFO;
import com.fengshen.server.data.write.weddingBook.MSG_WB_HOME_PIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.util.GameConfig;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 结婚工具类
 * 
 *
 */
@Slf4j
public class MarryUtil {
	//参与结婚的队伍
	public static Map<String,Vo_APPEAR> teams = new LinkedHashMap<String,Vo_APPEAR>();
	//花车
	private Vo_APPEAR leader;
	//礼单
	private List<WeddingList> weddingList;
	//特效
	private List<Vo_ANIMATE_IN_CHAR> effects;
	//全服特效 
	private List<Vo_ANIMATE_IN_CHAR> allEffects;
	//祝词
	private List<Vo_ANIMATE_IN_CHAR> zhuci;
	//队伍移动信息
	private String movedInfos = "{ \"startX\":183, \"startY\":117, \"startDir\":1, \"dir1\": { \"花轿\":{ \"x\":7, \"y\":5 }, \"丫鬟1\": { \"x\": 1, \"y\": 3 }, \"丫鬟2\": { \"x\": 7, \"y\": 0 }, \"喇叭手\": { \"x\": 4, \"y\": 5 }, \"敲锣手\": { \"x\": 10, \"y\": 2 }, \"护牌手1\": { \"x\": 7, \"y\": 7 }, \"护牌手2\": { \"x\": 13, \"y\": 4 } }, \"dir2\": { \"花轿\":{ \"x\":-9, \"y\":6 }, \"丫鬟1\": { \"x\": -8, \"y\": -1 }, \"丫鬟2\": { \"x\": -1, \"y\": 3 }, \"喇叭手\": { \"x\": -12, \"y\": 1 }, \"敲锣手\": { \"x\": -4, \"y\": 5 }, \"护牌手1\": { \"x\": -15, \"y\": 3 }, \"护牌手2\": { \"x\": -7, \"y\": 7 } }, \"dir4\": { \"花轿\":{ \"x\":-9, \"y\":-5 }, \"丫鬟1\": { \"x\": -5, \"y\": -5, }, \"丫鬟2\": { \"x\": -9, \"y\": -2 }, \"喇叭手\": { \"x\": -9, \"y\": -8 }, \"敲锣手\": { \"x\": -13, \"y\": -5 }, \"护牌手1\": { \"x\": -14, \"y\": -10 }, \"护牌手2\": { \"x\": -18, \"y\": -7 } }, \"dir6\": { \"花轿\":{ \"x\":8, \"y\":-6 }, \"丫鬟1\": { \"x\": 6, \"y\": 0 }, \"丫鬟2\": { \"x\": -4, \"y\": -4 }, \"喇叭手\": { \"x\": 9, \"y\": -2 }, \"敲锣手\": { \"x\": -1, \"y\": -6 }, \"护牌手1\": { \"x\": 12, \"y\": -4 }, \"护牌手2\": { \"x\": -3, \"y\": -8 } }, \"raod\": [ { \"x\":133, \"y\":90, \"pause_time\": 10, \"dir\": 1, \"nextDir\":2, \"orderTime\":100 }, { \"x\":191, \"y\":57, \"pause_time\": 10, \"dir\": 2, \"nextDir\":1, \"orderTime\":100 }, { \"x\":164, \"y\":35, \"pause_time\": 10, \"dir\": 1, \"nextDir\":6, \"isSendNitce\":1, \"orderTime\":100 }, { \"x\":134, \"y\":52, \"pause_time\": 10, \"dir\": 6, \"nextDir\":6, \"orderTime\":100 }, { \"x\":102, \"y\":68, \"pause_time\": 10, \"dir\": 6, \"nextDir\":1, \"orderTime\":100 }, { \"x\":63, \"y\":43, \"pause_time\": 10, \"dir\": 1, \"nextDir\":4, \"orderTime\":100 }, { \"x\":88, \"y\":59, \"pause_time\": 10, \"dir\": 4, \"nextDir\":4, \"orderTime\":100 }, { \"x\":134, \"y\":91, \"pause_time\": 10, \"dir\": 4, \"nextDir\":4, \"orderTime\":100 }, { \"x\":169, \"y\":110, \"pause_time\": 10, \"dir\": 4, \"nextDir\":1, \"orderTime\":100 }, { \"x\":131, \"y\":89, \"pause_time\": 10, \"dir\": 1, \"nextDir\":1, \"orderTime\":100, \"return\":2 }, { \"x\":103, \"y\":71, \"pause_time\": 10, \"dir\": 1, \"nextDir\":2, \"orderTime\":100, \"return\":1 }, { \"x\":34, \"y\":25, \"pause_time\": 10, \"dir\": 2, \"nextDir\":2, \"orderTime\":100 }, { \"x\":38, \"y\":22, \"pause_time\": 5, \"dir\": 2, \"nextDir\":2, \"orderTime\":100 }, { \"x\":43, \"y\":20, \"pause_time\": 5, \"dir\": 2, \"nextDir\":2, \"orderTime\":100 } ] }";
	
	//开始时间
	public static long startTime;
	
	private JSONObject parseObject = null;
	
	
	/**
	 * a创建婚礼
	 * @param weddingLists 礼单列表
	 * @param startTime 开始时间
	 */
	public MarryUtil(List<WeddingList> weddingLists, long startTime) {
		if(weddingLists == null) {
			return;
		}
		MarryUtil.startTime = startTime;
		this.weddingList = weddingLists;
		if(parseObject == null) {
			parseObject = JSONObject.parseObject(movedInfos);
		}
		//默认参与的对象 183 117
		int defaultX = parseObject.getIntValue("startX");
		int defaultY = parseObject.getIntValue("startY");
		int startDir = parseObject.getIntValue("startDir");
		JSONObject dirInfo = parseObject.getJSONObject("dir"+startDir);
		//找出花车
		for(WeddingList wl:weddingLists) {
			if("花车".equals(wl.getType())) {
				String ledaerName = "花车";
				if("4人抬花轿".equals(wl.getName())) {
					//这个花车需要单独添加
					teams.put("花轿", createNpc(88888880,defaultX+dirInfo.getJSONObject("花轿").getIntValue("x"),defaultY+dirInfo.getJSONObject("花轿").getIntValue("y"),42104,startDir,"花轿"));
					ledaerName = "宝马";
				}
				leader = createNpc(66666661,defaultX,defaultY,wl.getIcon(),startDir, ledaerName);
				break;
			}
		}
		teams.put("丫鬟1", createNpc(88888881,defaultX+dirInfo.getJSONObject("丫鬟1").getIntValue("x"),defaultY+dirInfo.getJSONObject("丫鬟1").getIntValue("y"),42401,startDir,"丫鬟1"));
		teams.put("丫鬟2", createNpc(88888882,defaultX+dirInfo.getJSONObject("丫鬟2").getIntValue("x"),defaultY+dirInfo.getJSONObject("丫鬟2").getIntValue("y"),42401,startDir,"丫鬟2"));
		teams.put("喇叭手", createNpc(999999,defaultX+dirInfo.getJSONObject("喇叭手").getIntValue("x"),defaultY+dirInfo.getJSONObject("喇叭手").getIntValue("y"),42402,startDir,"喇叭手"));
		teams.put("敲锣手", createNpc(888888,defaultX+dirInfo.getJSONObject("敲锣手").getIntValue("x"),defaultY+dirInfo.getJSONObject("敲锣手").getIntValue("y"),42405,startDir,"敲锣手"));
		teams.put("护牌手1", createNpc(777777,defaultX+dirInfo.getJSONObject("护牌手1").getIntValue("x"),defaultY+dirInfo.getJSONObject("护牌手1").getIntValue("y"),42404,startDir,"护牌手1"));
		teams.put("护牌手2", createNpc(666666,defaultX+dirInfo.getJSONObject("护牌手2").getIntValue("x"),defaultY+dirInfo.getJSONObject("护牌手2").getIntValue("y"),42404,startDir,"护牌手2"));
		this.allEffects = new ArrayList<>();
		this.effects = new ArrayList<>();
		this.zhuci = new ArrayList<>();
		
		Iterator<WeddingList> iterator = weddingList.iterator();
		//全服特效
		while(iterator.hasNext()) {
			WeddingList wl = iterator.next();
			Vo_ANIMATE_IN_CHAR obj = new Vo_ANIMATE_IN_CHAR();
			obj.setEffectNo(wl.getIcon());
			obj.setOrder(1);
			obj.setLocate(1);
			obj.setLoops(1);
			obj.setInterval(1);
			obj.setDuring(wl.getPlayTime());
			if(wl.getType().equals("祝词")) {
				zhuci.add(obj);
			}
		}
		Vo_ANIMATE_IN_CHAR obj = new Vo_ANIMATE_IN_CHAR();
		obj.setEffectNo(1471);
		obj.setOrder(1);
		obj.setLocate(1);
		obj.setLoops(1);
		obj.setInterval(1);
		obj.setDuring(5);
		allEffects.add(obj);
		obj = new Vo_ANIMATE_IN_CHAR();
		obj.setEffectNo(8262);
		obj.setOrder(1);
		obj.setLocate(1);
		obj.setLoops(1);
		obj.setInterval(1);
		obj.setDuring(5);
		effects.add(obj);
	}
	
	/**
	 * 创建npc
	 * @param id id
	 * @param x 坐标
	 * @param y 坐标
	 * @param icon 外观
	 * @param dir 方向
	 * @param name 名字
	 * @return
	 */
	public Vo_APPEAR createNpc(int id, int x, int y, int icon, int dir, String name) {
		Vo_APPEAR npc = new Vo_APPEAR();
		npc.x = x;
		npc.y = y;
		npc.id = id;
		npc.type = 0x0200;
		npc.icon = icon;
		npc.dir = dir;
		npc.name = name;
		return npc;
	}

	/**
	 * a开始婚礼
	 * @param gameObjectChar 新郎
	 */
	public void startMarry(GameObjectChar gameObjectChar) {
		if(!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam) || gameObjectChar.gameTeam.duiwu.size()<1) {
			GameCommonUtil.sendTips("不符合条件，无法开始婚礼", gameObjectChar);
			return;
		}
		//新郎对象
		Chara chara = gameObjectChar.chara;
		if(chara.taskMap.get("预定婚礼") == null && gameObjectChar.privilege == 0) {
			GameUtil.sendMeTips("你还未预定婚礼呢？请到月老处预定。");
			return;
		}
		//删除预定任务
		GameUtilRenWu.removeTask("预定婚礼", chara);
		//新娘
		GameObjectChar womenGameObject = GameObjectCharMng.getGameObjectCharByUUid(gameObjectChar.gameTeam.duiwu.get(1).uuid);
		Chara womenChara = womenGameObject.chara;
		womenGameObject.flag = "marry";
		//天墉城指定地点举行婚礼
		chara.x = 183;
		chara.y = 117;
		gameObjectChar.flag = "marry";
		GameLine.getGameMap(chara.getLine(), 5000).join(gameObjectChar);
		womenChara.x = 183;
		womenChara.y = 117;
		GameLine.getGameMap(chara.getLine(), 5000).join(womenGameObject);
		//开始婚礼
		gameObjectChar.sendOne(new MSG_WEDDING_NOW(), new Integer[] {1,1});
		womenGameObject.sendOne(new MSG_WEDDING_NOW(), new Integer[] {1,1});
		//删除预定婚礼任务
		GameUtilRenWu.removeTask("预定婚礼", chara);
		//全服公告
		String notice = "新郎#Y"+chara.name+"#n与新娘#Y"+womenChara.name+"#n的盛大婚礼开始啦，大家快来#R"+GameConfig.lineName+"1线天墉城#n祝福他们吧！";
		GameUtil.sendSystemMessage(19, notice);
		//全服横幅通知
		Vo_BANNER ban = new Vo_BANNER();
		ban.setTime(10);
		ban.setOrder(1);
		ban.setTitle("结婚盛典");
		ban.setContent("恭喜新郎#Y"+chara.name+"#n与新娘#Y"+womenChara.name+"#n喜结良缘\n大家快来"+GameConfig.lineName+"1线天墉城祝福他们吧！");
		ban.setType(3);
		GameObjectCharMng.sendAll(new MSG_BANNER(), ban);
		//新郎信息
		Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(chara);
		a61661.isHide = 1;
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
		//新娘信息
		Vo_UPDATE_APPEARANCE womenVo = GameUtil.a61661(womenChara);
		womenVo.isHide = 1;
		womenGameObject.gameMap.send(new MSG_UPDATE_APPEARANCE(), womenVo);
		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
			@Override
			public void run() {
				try {
					//所有路径
					JSONArray raods = parseObject.getJSONArray("raod");	
					JSONObject dirInfo = parseObject.getJSONObject("dir1");
					gameObjectChar.gameMap.send(new M65529_0(), leader);
					//初始化队伍信息
					for(Map.Entry<String, Vo_APPEAR> vo:teams.entrySet()) {
						gameObjectChar.gameMap.send(new M65529_0(), vo.getValue());
					}
					int zhuciIndex = 0;
					for (int i = 0; i < raods.size(); i++) {
						JSONObject raod = raods.getJSONObject(i);
						//目的地x
						int x = raod.getIntValue("x");
						//目的地y
						int y = raod.getIntValue("y");
						//暂停时间
						int pauseTime = raod.getIntValue("pause_time");
						//轮流走动时间
						int orderTime = raod.getIntValue("order_time");
						//切换地图
						int returnMap = raod.getIntValue("return");
						//方向
						int dir = raod.getIntValue("dir");
						//获取方向信息
						dirInfo = parseObject.getJSONObject("dir"+dir);
						if(dirInfo == null) {
							continue;
						}
						int intValue = raod.getIntValue("nextDir");
						//领队
						Vo_16431_0 leaderVo = new Vo_16431_0();
						leaderVo.id = leader.id;
						leaderVo.x = x;
						leaderVo.y = y;
						gameObjectChar.gameMap.send(new M16431_0(), leaderVo);
						for(Map.Entry<String, Vo_APPEAR> vo:teams.entrySet()) {
							Thread.sleep(orderTime);
							JSONObject jsonObject = dirInfo.getJSONObject(vo.getKey());
							Vo_16431_0 vo_16431_02 = new Vo_16431_0();
							vo_16431_02.id = vo.getValue().id;
							vo_16431_02.x = x+jsonObject.getIntValue("x");
							vo_16431_02.y = y+jsonObject.getIntValue("y");
							gameObjectChar.gameMap.send(new M16431_0(), vo_16431_02);
						}
						if(zhuci != null && !zhuci.isEmpty()) {
							//祝词
							Vo_ANIMATE_IN_CHAR zc = zhuci.get(zhuciIndex);
							zc.setId(chara.id);
							gameObjectChar.sendOne(new MSG_ANIMATE_IN_UI(), zc);
							zc.setId(womenChara.id);
							womenGameObject.sendOne(new MSG_ANIMATE_IN_UI(), zc);
							zhuciIndex++;
							if(zhuciIndex>zhuci.size()-1) {
								zhuciIndex = 0;
							}
						}
						//特效
						for(Vo_ANIMATE_IN_CHAR v:effects) {
							if(i%2==0 && ThreadLocalRandom.current().nextBoolean()) {
								v.setId(chara.id);
								gameObjectChar.sendOne(new MSG_ANIMATE_IN_UI(), v);
								v.setId(womenChara.id);
								womenGameObject.sendOne(new MSG_ANIMATE_IN_UI(), v);
							}
						}
						//全服特效
						if(!allEffects.isEmpty()) {
							allEffects.get(0).setId(chara.id);
							for(GameObjectChar c:GameObjectCharMng.getAll()) {
								//除了自己
								if(c.chara.id == chara.id || c.chara.id == womenChara.id) {
									continue;
								}
								c.sendOne(new MSG_ANIMATE_IN_UI(), allEffects.get(0));
							}
						}
						//把队伍暂时隐藏,为了能跟随花车移动
						List<Vo_4121_0> vo_4121_0List2 = new ArrayList<Vo_4121_0>();
						gameObjectChar.sendOne(new M4121_0(), vo_4121_0List2);
						//把新郎设置成无法移动
						Vo_TITLE vo_61671_0 = new Vo_TITLE();
						vo_61671_0.id = chara.id;
						vo_61671_0.list.add(5);
						gameObjectChar.sendOne(new MSG_TITLE(), vo_61671_0);
						
						//让新郎跟随花车移动
						Vo_16431_0 manMovedInfo = new Vo_16431_0();
						manMovedInfo.id = chara.id;
						manMovedInfo.x = x;
						manMovedInfo.y = y;
				        gameObjectChar.gameMap.send(new M16431_0(), manMovedInfo);
				        //让新娘跟随花车移动
				        Vo_16431_0 womenMovedInfo = new Vo_16431_0();
				        womenMovedInfo.id = womenChara.id;
				        womenMovedInfo.x = x;
				        womenMovedInfo.y = y;
				        womenGameObject.gameMap.send(new M16431_0(), womenMovedInfo);
				        //是否再次公告
						Integer isSendNitce = raod.getInteger("isSendNitce");
						if(isSendNitce != null && isSendNitce == 1 && i>0) {
							GameUtil.sendSystemMessage(19, notice);
						}
						if(i>0) {
							//显示祝福
							gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("丫鬟1", "#R▆█▆ ▆█▆\n▆█▆ ▆█▆\n█▆█ █▆█\n▆█▆ ▆█▆\n▆█▆ ▆█▆\n█▆█ █▆█\n#56m #56m", 88888881, 42401, 1));
							gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("丫鬟2", "#R▆█▆ ▆█▆\n▆█▆ ▆█▆\n█▆█ █▆█\n▆█▆ ▆█▆\n▆█▆ ▆█▆\n█▆█ █▆█\n#56m #56m", 88888882, 42401, 1));
						}
						//即将要回风月谷进行了
						if(returnMap == 2) {
							gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("喇叭手", "我们马上就要去#R风月谷#n继续进行花车巡游啦，欢迎大家的到来！", 999999, 42402, 1));
							gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("敲锣手", "我们马上就要去#R风月谷#n继续进行花车巡游啦，欢迎大家的到来！", 888888, 42402, 1));
						}
						//太累了休息一下在继续
						Thread.sleep(pauseTime*1000);
						dirInfo = parseObject.getJSONObject("dir"+intValue);
						//如果已经到达最后一段路,则把新郎新娘带回风月谷
						if(returnMap == 1) {
							chara.x = 22;
							chara.y = 30;
							womenChara.x = 22;
							womenChara.y = 30;
							GameLine.getGameMap(chara.line, 7000).join(gameObjectChar);
							GameLine.getGameMap(chara.line, 7000).join(womenGameObject);
							x = 22;
							y = 30;
						}
						//车头
						leader.x = x;
						leader.y = y;
						leader.dir = intValue;
						gameObjectChar.gameMap.send(new M65529_0(), leader);
						for(Map.Entry<String, Vo_APPEAR> vo:teams.entrySet()) {
							Thread.sleep(100);
							Vo_APPEAR npc = vo.getValue();
							JSONObject nextInfo = dirInfo.getJSONObject(npc.name);
							npc.x = x+nextInfo.getIntValue("x");
							npc.y = y+nextInfo.getIntValue("y");
							npc.dir = intValue;
							gameObjectChar.gameMap.send(new M65529_0(), npc);
						}
					}
				}catch (Exception e) {
					log.error("{}", e);
				} finally {
					//删除预定任务
					GameUtilRenWu.removeTask("预定婚礼", chara);
					leader = null;
					teams.clear();
					if(weddingList != null) {
						weddingList.clear();
					}
					//婚礼结束
					gameObjectChar.flag = "";
					womenGameObject.flag = "";
					gameObjectChar.sendOne(new MSG_WEDDING_NOW(), new Integer[] {0,0});
					womenGameObject.sendOne(new MSG_WEDDING_NOW(), new Integer[] {0,0});
					a61661.isHide = 0;
					gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
					womenVo.isHide = 0;
					womenGameObject.gameMap.send(new MSG_UPDATE_APPEARANCE(), womenVo);
					//添加视野
					Vo_APPEAR a65529 = GameUtil.a65529(womenGameObject.chara);
					a65529.x = 41;
					a65529.y = 19;
					gameObjectChar.gameMap.send(new M65529_0(), a65529);
					//添加视野
					a65529 = GameUtil.a65529(gameObjectChar.chara);
					a65529.x = 41;
					a65529.y = 19;
					gameObjectChar.gameMap.send(new M65529_0(), a65529);
					
					//恢复队伍信息
					GameUtil.updateRightTeamInfos(chara);
					//恢复可以移动和队长状态
					Vo_TITLE vo_61671_0 = new Vo_TITLE();
					vo_61671_0.id = chara.id;
					vo_61671_0.list.add(2);
					vo_61671_0.list.add(3);
					gameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
					MarryUtil.startTime = 0;
					gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("喇叭手", "婚车巡礼即将结束啦，衷心的祝福#Y"+chara.name+"#n与#Y"+womenChara.name+"#n喜结连理，白头到老#50m", 999999, 42402, 1));
					gameObjectChar.gameMap.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("敲锣手", "婚车巡礼即将结束啦，衷心的祝福#Y"+chara.name+"#n与#Y"+womenChara.name+"#n喜结连理，白头到老#50m", 888888, 42402, 1));
					try {
						//这里暂停个3秒钟在消失
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						log.error("{}", e);
					}
					//所有的都消失
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 88888880);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 66666661);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 88888881);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 88888882);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 999999);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 888888);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 777777);
					gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), 666666);
					//系统消息
					GameUtil.sendSystemMessage(7, "新郎#Y"+chara.name+"#n与新娘#Y"+womenChara.name+"#n的盛大婚礼圆满结束了，感谢大家的参与！");
					GameCommonUtil.sendTips("花车巡游到此结束。",gameObjectChar);
					GameCommonUtil.sendTips("花车巡游到此结束。", womenGameObject);
				}
			}
		});
	}
	
	/**
	 * a结婚操作
	 * @param gameObjectChar
	 * @param id npcId
	 */
	public static void jiehun(GameObjectChar gameObjectChar, int id) {
		Chara chara = gameObjectChar.chara;
		if(chara.marriageMarryId != 0) {
			GameUtil.changeNpcSession(id, 6060, "月老", "你已婚了[离开]");
			return;
		}
		Vo_61553_0 tiqin = chara.getTaskMap().get("提亲");
		if(tiqin == null || chara.sex != 1 || !GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
				|| gameObjectChar.gameTeam.duiwu.size() != 2 
				|| !gameObjectChar.gameTeam.duiwu.get(1).uuid.equals(tiqin.task_extra_para)) {
			//增加提亲菜单
			GameUtil.changeNpcSession(id, 6060, "月老", "前往#R天墉城#n找#Y红娘#n完成提亲任务再来找我吧！[离开]");
			return;
		}
		Chara women = gameObjectChar.gameTeam.duiwu.get(1);
		GameCommonUtil.sendTips("恭喜你和#Y"+women.name+"#n喜结良缘，在接下来的#R24小时#n内，你的形象变为#R新郎#n！", gameObjectChar);
		GameCommonUtil.sendTips("恭喜你和#Y"+chara.name+"#n喜结良缘，在接下来的#R24小时#n内，你的形象变为#R新娘#n！", women.id);
		//创建定时任务
		GameData.that.redisUtils.set("longFengTimeOut_"+chara.id, "", 60*60*24);
		GameData.that.redisUtils.set("longFengTimeOut_"+women.id, "", 60*60*24);
		//发送称谓
		String manChengWei = women.name+"的相公";
		String womenChengWei = chara.name+"的娘子";
		//女方
		GameCommonUtil.changeTitle(GameObjectCharMng.getGameObjectChar(women.id), womenChengWei);
		//男方
		GameCommonUtil.changeTitle(gameObjectChar, manChengWei);
		GameUtil.chenghaoxiaoxi(chara, "结婚", manChengWei);
		GameUtil.chenghaoxiaoxi(women, "结婚", womenChengWei);
		chara.chenhao = manChengWei;
		women.chenhao = womenChengWei;
		chara.marriageMarryId = women.id;
		chara.marriageName = women.name;
		chara.marriageTime = System.currentTimeMillis();
		women.marriageMarryId = chara.id;
		women.marriageName = chara.name;
		women.marriageTime = chara.marriageTime;
		
		//龙凤呈祥服·新郎
		chara.special_icon = 42101;
		Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
		//龙凤呈祥服·新娘
		women.special_icon = 42102;
		a61661 = GameUtil.a61661(women);
		GameObjectCharMng.getGameObjectChar(women.id).gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
		//移除提亲任务
		GameUtilRenWu.removeTask("提亲", chara);
		//获取纪念册
		GameUtil.huodedaoju(gameObjectChar.chara, "结婚纪念册", 1);
		GameUtil.huodedaoju(women, "结婚纪念册", 1);
		GameUtil.sendMeTips("你获得了与#Y"+women.name+"#n的结婚纪念册");
		
		getLongFengChengXiang(gameObjectChar);
		getLongFengChengXiang(GameObjectCharMng.getGameObjectChar(women.id));
		GameCommonUtil.sendTips("你获得了与#Y"+chara.name+"#n的结婚纪念册", women.id);
	}
	
	/**
	 * a初始化婚礼清单
	 * @param gameObjectChar
	 */
	public static void initWeddingListChoseDlg(GameObjectChar gameObjectChar) {
		//打开结婚礼包选择界面
		Vo_WEDDING_ALL_LIST vo = new Vo_WEDDING_ALL_LIST("元宝",10000);
		List<Item> items = new ArrayList<>();
		
		WeddingListService weddingListService = SpringBeanUtils.getBean(WeddingListService.class);
		List<WeddingList> weddingLists = weddingListService.selectAll();
		for(WeddingList wd:weddingLists) {
			items.add(new Vo_WEDDING_ALL_LIST.Item(wd.getName(),wd.getPrice()));
		}
		
		items.add(new Vo_WEDDING_ALL_LIST.Item("朱雀护卫8名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("疆良护卫8名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("玄武护卫8名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("东山神灵护卫8名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("朱雀护卫4名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("疆良护卫4名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("玄武护卫4名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("东山神灵护卫4名",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("夫妻拜拜",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("夫妻抱抱",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("夫妻交杯",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("夫妻亲亲",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("30天洞房效果",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("夫妻同心光效",1));
		items.add(new Vo_WEDDING_ALL_LIST.Item("鸾凤宝玉",1));
		vo.setItems(items);
		gameObjectChar.sendOne(new MSG_WEDDING_ALL_LIST(), vo);
		
	}
	
	/**
	 * a获取龙凤呈祥
	 * @param gameObjectChar
	 * @param name
	 * @param icon
	 * @param pos
	 * @return
	 */
	public static Goods getLongFengChengXiang(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		String name = "绛染容华服·新郎";
		int icon = 9208;
		if(chara.sex == 2) {
			//新娘
			name = "绛染容华服·新娘";
			icon = 9205;
		}
		Goods goods = getNewGoods(chara, name, icon);
		//添加到其他库存去
		boolean isFind = false;
		for(Goods g:chara.shizhuang) {
			//如果存在就不添加
			if(g.pos == 4000) {
				isFind = true;
				break;
			}
		}
		if(!isFind) {
			chara.shizhuang.add(goods);
		}
		GameCommonUtil.sendTips("你获得了1件#R"+name+"#n。", gameObjectChar);
		return goods;
	}
	
	public static Goods getNewGoods(Chara chara, String name, int icon) {
		Goods goods = new Goods();
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.value = 2097924;
		goods.goodsInfo.quality = "金色";
		goods.goodsInfo.alias = name;
		goods.goodsInfo.amount = 16;
		goods.pos = 4001;
		goods.goodsInfo.food_num = 1;
		goods.goodsInfo.master = chara.sex;
		goods.goodsInfo.recognize_recognized = 0;
		goods.goodsInfo.type = icon;
		goods.goodsInfo.total_score = 25;
		goods.goodsInfo.damage_sel_rate = 1842075;
		goods.goodsInfo.str = name;
		goods.goodsInfo.metal = chara.polar;
		goods.goodsInfo.durability = 8;
		goods.goodsInfo.rebuild_level = 500;
		goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
		return goods;
	}
	
	/**
	 * a打开纪念册
	 * @param gameObjectChar
	 */
	public static void openWeddingBook(GameObjectChar gameObjectChar) {
		Vo_WB_HOME_INFO homeInfo = new Vo_WB_HOME_INFO();
		homeInfo.setBookId("AA15545545125");
		homeInfo.setWedding_start_ti((int) (gameObjectChar.chara.marriageTime/1000L));
		homeInfo.setWedding_end_ti(0);
		homeInfo.setHus_name(gameObjectChar.chara.name);
		homeInfo.setWife_name(gameObjectChar.chara.marriageName);
		homeInfo.setHome_img("");
		gameObjectChar.sendOne(new MSG_WB_HOME_INFO(), homeInfo);
		
		gameObjectChar.sendOne(new MSG_WB_HOME_PIC(), new Vo_WB_HOME_PIC("AA15545545125", 1));
	}
	
	/**
	 * a离婚
	 * @param chara 玩家
	 * @param flag  强制，普通
	 */
	public static void lihun(Chara chara, boolean flag) {
		//找到另一半
		GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectChar(chara.marriageMarryId);
		if(toGameObjectChar == null) {
			//不在线
			Characters toChar = GameData.that.baseCharactersService.
					findOneByIdSelectProperties(chara.marriageMarryId, "data","id");
			if(toChar != null) {
				Chara toChara = com.alibaba.fastjson.JSONObject.parseObject(toChar.getData(), Chara.class);
				toChara.marriageMarryId = 0;
				toChara.marriageName = "";
				//删除称谓
				if(toChara.chenhao.equals(toChara.chenghao.get("结婚"))) {
					toChara.chenhao = "";
				}
				toChara.chenghao.remove("结婚");
				//更新数据
				Characters update = new Characters();
				update.setId(toChar.getId());
				update.setData(com.alibaba.fastjson.JSONObject.toJSONString(toChara));
				GameData.that.baseCharactersService.updateByPrimaryKeySelective(update);
			}
		}else {
			//在线状态
			toGameObjectChar.chara.marriageMarryId = 0;
			toGameObjectChar.chara.marriageName = "";
			GameCommonUtil.sendTips("你的伴侣和你离婚了！", toGameObjectChar);
			//删除称谓
			if(toGameObjectChar.chara.chenhao.equals(toGameObjectChar.chara.chenghao.get("结婚"))) {
				toGameObjectChar.chara.chenhao = "";
			}
			toGameObjectChar.chara.chenghao.remove("结婚");
			Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(toGameObjectChar.chara);
			toGameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
			//称号消息
			GameUtil.chenghaoxiaoxi(toGameObjectChar.chara, null, null);
			GameUtil.removemunber(toGameObjectChar.chara, "结婚纪念册", 1);
		}
		//恢复默认
		chara.marriageMarryId = 0;
		chara.marriageName = "";
		//删除称谓
		if(chara.chenhao.equals(chara.chenghao.get("结婚"))) {
			chara.chenhao = "";
		}
		chara.chenghao.remove("结婚");
		Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
		//称号消息
		GameUtil.chenghaoxiaoxi(chara, null, null);
		
		GameUtil.sendMeTips("离婚成功");
		//强制离婚
		if(flag) {
			//需要扣除好友度
			Example example = new Example(Friend.class);
			example.createCriteria().andEqualTo("friendGid", toGameObjectChar.chara.uuid).andEqualTo("gid", chara.uuid);
			Friend friend = new Friend();
			friend.setFriendScore(0);
			friend.setUpdateTime(new Date());
			GameData.that.friendService.updateByExampleSelective(friend, example);
		}
		//删除结婚纪念册
		GameUtil.removemunber(chara, "结婚纪念册", 1);
		//如果未来有居所的话...
	}
}