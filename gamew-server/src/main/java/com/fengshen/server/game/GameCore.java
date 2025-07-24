package com.fengshen.server.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import com.fengshen.db.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.ChangeCard;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.Party;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.xls_config.XLSConfigMgr;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.domain.config.CtConfig;
import com.fengshen.server.domain.config.NeiDanConfig;
import com.fengshen.server.fight.BattleUtils;
import com.fengshen.server.fight.FightTianshuMap;
import com.fengshen.server.job.GameUtilJob;
import com.fengshen.server.netty.NettyServer;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.MapGuardianService;
import com.fengshen.server.util.GameConfig;

import tk.mybatis.mapper.entity.Example;

@Service
public class GameCore {

	public static String userName = "";
	public static String passWd = "";
	public static GameCore that;
	private static final Logger log;
	protected List<GameLine> gameLineList;
	@Autowired
	private ApplicationContext applicationContext;
//	@Autowired
//	private List<BaseWrite> baseWrites;
	public NettyServer server;
	public long currentTime;
	// 线路数量
	@Value("${lineNum}")
	private int lineNum;
	//游戏对象集合
	public static Map<Integer, GameHandler> gameHandlerMap;
	@Autowired
	private List<GameHandler> gameHandlers;
	//partyIcon
	public static Map<String, Party> partyMap;
	//魂器初始化
	public static Map<String,JSONObject> hunqiYang;
	public static Map<String,JSONObject> hunqiYin;
	//新版变身卡
	public static Map<String, ChangeCard> changeCardMap;
	//宠物变色价格清单
	public static Map<String, JSONObject> petColorScheme;
	//正在战斗的npc
	public static Map<Integer,Object> fightObject;
	//问道小子客户端
	public static Map<String,Map<String,Object>> xiaoziClientInfo;
	//问道小子托管定时任务， key问道小子uuid
	public static Map<String, Timer> xiaoziBackTimer;
	//授权是否到期
	public static final AtomicBoolean isExpire = new AtomicBoolean(false);
	//劫狱土匪
	public static Map<Integer,Vo_APPEAR> jieyuMonster;
	//其他boss
	public static Map<Integer,Vo_APPEAR> otherBoosMonster;
	//地图
	public static final String mapnames = "五龙窟一层,五龙窟二层,五龙窟三层,五龙窟四层,五龙窟五层,轩辕坟二层,轩辕坟三层,轩辕坟一层,百花谷一,百花谷二,百花谷三,百花谷四,百花谷五,百花谷六,百花谷七,七宝林,五龙山,终南山,凤凰山,乾元山,骷髅山,大罗宫,热砂荒漠,北海沙滩,雪域冰原,轩辕庙,东昆仑,蓬莱岛,桃柳林,海底迷宫,官道南,昆仑云海,绝人阵,绝仙阵,地绝阵,天绝阵,卧龙坡,官道北,迷境花树,十里坡,幽冥涧,方丈岛,弑神殿,断魂窟";
	//擂台配置
	public static CtConfig ctConfig;
	//桃子萝卜参与人员id
	public static HashSet<Integer> luoboTaoziCids;
	
	public GameCore() {
		this.gameLineList = new ArrayList<>();
		this.currentTime = 0L;
		GameCore.gameHandlerMap  = new HashMap<>();
		hunqiYang = new HashMap<>();
		hunqiYin = new HashMap<>();
		changeCardMap = new HashMap<>();
		petColorScheme = new HashMap<>();
		fightObject = new HashMap<>();
		jieyuMonster = new HashMap<>();
		xiaoziClientInfo = new ConcurrentHashMap<String, Map<String,Object>>();
		xiaoziBackTimer = new HashMap<String, Timer>();
		otherBoosMonster = new HashMap<>();
		luoboTaoziCids = new HashSet<Integer>();
	}

	// 初始化游戏
	public void init(final NettyServer server) {
		GameCore.log.info("开始初始化游戏...");
		//初始化大日金乌血量
		RedisUtils redisUtils = GameData.that.redisUtils;
		if(redisUtils.get("dari_max_life_str")==null){
			redisUtils.set("dari_max_life_str",GameConfig.config.getDari().getDari_max_life_str());
		}
		if(redisUtils.get("dari_life_str")==null){
			redisUtils.set("dari_life_str",GameConfig.config.getDari().getDari_life_str());
		}

		this.server = server;
		// 初始化游戏线路
		for (int i = 0; i < this.lineNum; ++i) {
			final GameLine gameLine = (GameLine) this.applicationContext.getBean("glllbawsdfawelllll", GameLine.class);
			gameLine.lineNum = i + 1;
			gameLine.lineName = GameConfig.lineName;
			this.gameLineList.add(gameLine);
			gameLine.init();
		}
		BuildFields.init(); // 初始化属性名
		BuildFields.add(); // 添加字段
		BuildFieldsNew.init(); // 添加字段
		BuildFieldsNew.add(); // 添加字段
		BattleUtils.init(); // 初始化一些技能
		FightTianshuMap.init();

		// ad tzhang
		// 初始化守护神信息
		MapGuardianService.init();
		// 初始化挑战雕像的数据库
		CharaStatueService.init(String.valueOf("1"));
		
		//游戏集合
		for(GameHandler g:gameHandlers) {
			gameHandlerMap.put(g.cmd(), g);
		}
		//启动刷星
		new Thread() {
			@SuppressWarnings("unchecked")
			public void run() {
				// 加载配置副本配置
				XLSConfigMgr.init();
				//缓存宠物信息
				GameConfig.petCache = GameData.that.basePetService.findAll();
				log.info("缓存宠物信息列表...");
				//初始化所有角色的信息
				Example example = new Example(Characters.class);
				Characters characters = new Characters();
				characters.setOnline(0);
				GameData.that.baseCharactersService.updateByExampleSelective(characters, example);
				partyMap = new HashMap<>();
				//缓存帮派信息
				for(Party p:GameData.that.partyService.selectAll()) {
					partyMap.put(p.getPartyName(), p);
				}
				//加载魂器配置信息
				hunqiYang = JSONObject.parseObject(readJson("static/HunqiYang.json"), Map.class);
				hunqiYin = JSONObject.parseObject(readJson("static/HunqiYin.json"), Map.class);
				//新版变身卡
				for(ChangeCard c:GameData.that.changeCardService.selectAll()) {
					changeCardMap.put(c.getName(), c);
				}
				//宠物染色
				petColorScheme = JSONObject.parseObject(readJson("static/iconColorScheme.json"), Map.class);
				//重新生成一次排行榜
//				GameRankJob rank = SpringBeanUtils.getBean(GameRankJob.class);
//				try {
//					rank.createRank();
//				} catch (JsonProcessingException e) {
//				}
				//初始化
				GameUtilJob.new_year_beast_time = GameData.that.configInfoService.getOneByKeyName("new_year_beast_time");
				//初始化定时器
				ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("gongcheng_boss_config");
				if(configInfo != null) {
					JSONObject parseObject = JSONObject.parseObject(configInfo.getData());
					GameData.that.redisUtils.set("GONGCHENG_BOSS", "", parseObject.getIntValue("time"), TimeUnit.MINUTES);
				}
				ConfigInfo neidan = GameData.that.configInfoService.getOneByUuid("c7608ca8c7af4a30b7f4f46e7ad47ad3");
			    if(neidan != null) {
			    	try {
						GameConfig.neiDanConfig = JSONObject.parseObject(neidan.getData(),NeiDanConfig.class);
					} catch (Exception e) {
						log.error("{}",e);
					}
			    }
			    //擂台配置
			    ConfigInfo ct_config = GameData.that.configInfoService.getOneByUuid("ct_config");
			    if(ct_config != null) {
			    	try {
			    		ctConfig = JSONObject.parseObject(ct_config.getData(),CtConfig.class);
			    	} catch (Exception e) {
			    		log.error("{}",e);
			    	}
			    }
			}
		}.start();
		
		//初始化ringbuff
//		GameQueue.start();
		GameCore.log.info("游戏初始化完成!");
	}

	@PostConstruct
	public void initAfter() {
		GameCore.that = this;
//		for (final BaseWrite baseWrite : this.baseWrites) {
//			GameCore.basewriteMap.put(baseWrite.cmd(), baseWrite);
//		}
	}

	public static <T> T getBean(final String name, final Class<T> cls) {
		return (T) GameCore.that.applicationContext.getBean(name, cls);
	}

//	protected static BaseWrite getBaseWrite(final int cmd) {
//		return GameCore.basewriteMap.get(cmd);
//	}

	public static GameLine getGameLine(final int line) {
		for (final GameLine gameLine : GameCore.that.gameLineList) {
			if (gameLine.lineNum == line) {
				return gameLine;
			}
		}
		return null;
	}

	public List<GameLine> getGameLineAll() {
		return this.gameLineList;
	}

	static {
		log = LoggerFactory.getLogger(GameCore.class);
//		basewriteMap = new HashMap<Integer, BaseWrite>();
	}
	
	/**
	 * 读取json
	 * @param path
	 * @return
	 */
	public static String readJson(String path) {
		PathMatchingResourcePatternResolver pr = new PathMatchingResourcePatternResolver();
		Resource resource = pr.getResource(path);
		BufferedReader br = null;
		try {
			InputStream inputStream = resource.getInputStream();
			InputStreamReader fr = new InputStreamReader(inputStream, "UTF-8");
			br = new BufferedReader(fr);
		} catch (IOException e) {
			log.error("加载:{}失败,{}",path,e);
			System.exit(0);
		}
		StringBuilder sb = new StringBuilder();
		br.lines().forEach((f) -> {
			sb.append(f);
		});
		return sb.toString();
	}
}