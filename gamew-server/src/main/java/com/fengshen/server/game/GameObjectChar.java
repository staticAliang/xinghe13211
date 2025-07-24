package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.pet.Vo_PET_STORE;
import com.fengshen.server.data.vo.user.DAILY_STATS_INFO;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.job.SaveCharaTimes;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户在游戏中的总控对象
 */
@Getter
@Setter
@Slf4j
public class GameObjectChar {
	//账号id
	public int accountid;
	//账号令牌
	public String accountToken;
	//客户端连接
	public ChannelHandlerContext ctx;
	//客户端连接
	public static ThreadLocal<GameObjectChar> GAMEOBJECTCHAR_THREAD_LOCAL;
	//角色
	public Chara chara;
	//角色信息
	public Characters characters;
	//游戏地图
	public GameMap gameMap;
	//队伍
	public GameTeam gameTeam;
	// 邀请列表
	public HashMap<Integer, Chara> invitationCharas;
	//上个队长id
	public int upduizhangid;
	//心跳时间
	public long heartEcho;
	//权限
	public int privilege;
	//Gm是否隐藏
	public int isHide;
	// 游戏对象定时器
	public Map<String, Timer> timers;
//	private AtomicBoolean lock;
	// 游戏加速累计次数
	public int speedUpNum;
	// gm进入试道方式,是否使用了GM权限进入
	public String useGmAuth;
	// 当前动作
	public String action;
	// 攻击对象的id
	public int victimId;
	// 当前confirm参数
	public Object confirmData;
	//安全密码参数
	public Object safeConfirmData;
	// 当前的菜单
	public String currentConfirmItem;
	// 标识
	public String flag;
	//普通加速累计次数
	public AtomicInteger commonSpeedNum;
	//接收人id
	public int receiverId;
	//赠送类型pos
	public int givingPos;
	//赠送类型
	public int givingType;
	//试道状态
	public AtomicBoolean shiDaoFlag = new AtomicBoolean(false);
	//是否提前结算过
	public boolean shiDaoGetReward;
	//安全密码是否验证过
	public int relleaseLock;
	//安全密码
	public String safeLockPwd;
	//采集类型
	public String gatherType;
	//channelId
	public String channelId;
	//账号信息
	public Accounts account;
	//命令步数,用于校验消息是否正确
	public AtomicInteger tickCount;
	//是否回合结束
	public AtomicBoolean isEndRound = new AtomicBoolean(false);
	//是否退出后台
	public AtomicBoolean isBack = new AtomicBoolean(false);
	//当前观战人的id
	public int lookCharId;
	//观战状态
	public int isLook;
	//桃子萝卜次数
	public int lbtzTaskCount = 1;
	//桃子萝卜最后一次
	public long lbtzTaskTime;
	public int flyType;
	public int moveType;
	public Set<Integer> moveIds = new LinkedHashSet<>();
	//队伍标识
	public String askType;
	//上次結束回合時
	public long lastRoundEndTime;
	//是否开启七杀
	public int isOpenQiShaFlag;
	
//	public boolean lock() {
//		return this.lock.compareAndSet(false, true);
//	}
//
//	public void unlock() {
//		this.lock.set(false);
//	}

	public GameObjectChar(Accounts account, ChannelHandlerContext ctx) {
//		this.lock = new AtomicBoolean(false);
		this.accountid = account.getId();
		this.accountToken = account.getToken();
		this.ctx = ctx;
		this.timers = new HashMap<>();
		this.useGmAuth = "";
		this.action = "";
		this.invitationCharas = new HashMap<>();
		this.flag = "";
		commonSpeedNum = new AtomicInteger(0); 
		safeLockPwd = account.getKeyword();
		this.relleaseLock = 0;
		this.account = account;
		this.channelId = ctx.channel().id().asLongText();
		this.tickCount = new AtomicInteger(0);
		this.askType = "";
	}
	
	public GameObjectChar() {
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameObjectChar) {
			GameObjectChar gs = (GameObjectChar) obj;
			if (this.chara != null && gs.chara != null && this.chara.id == gs.chara.id) {
				return true;
			}
		}
		return false;
	}

	// 判断当前对象是否在线
	public boolean isOnline() {
		return null != ctx && chara != null;
	}

	// 创建该对象的队伍
	public void creator(GameTeam gameTeam) {
		this.gameTeam = gameTeam;
	}

	public static void main(String[] args) {
	}

	// 通过传入的角色信息，给角色创建一个角色管理对象，并加入到全局角色管理器中
	public void init(Characters characters) {
		this.isBack.set(false);
		this.isEndRound.set(true);
		String data = characters.getData();
		Chara chara = JSONObject.parseObject(data, Chara.class);
		chara.id = characters.getId();
		chara.uuid = characters.getGid();
		chara.name = characters.getName();
		chara.level = characters.getLevel();
		chara.chargeScore = characters.getChargeScore();
		chara.x = characters.getX();
		chara.y = characters.getY();
		chara.sex = characters.getSex();
		chara.goldCoin = characters.getGoldCoin();
		chara.waiguan = characters.getPortrait();
		chara.mapid = characters.getMapId();
		chara.mapName = characters.getMapName();
		chara.polar = characters.getPolar();
		chara.monthTao = characters.getMonthTao();
		chara.shut = characters.getShut();
		chara.fixedTeamName = characters.getFixedTeamName();
		if(chara.dayInfo == null) {
			chara.dayInfo = new DAILY_STATS_INFO();
		}
		if(chara.shudao == null ) {
			chara.shudao = new HashMap<>();
		}
		// 根据角色查询出他所有的背包信息.
		List<Goods> cangku = JSONObject.parseArray(characters.getCangku(), Goods.class);
		if(cangku == null) {
			cangku = new ArrayList<>();
		}
		chara.setCangku(cangku);
		List<Goods> cardStore = JSONObject.parseArray(characters.getCardStore(), Goods.class);
		if(cardStore == null) {
			cardStore = new ArrayList<>();
		}
		chara.setCardStore(cardStore);
		List<Goods> back = JSONObject.parseArray(characters.getBackpack(), Goods.class);
		if(back == null) {
			back = new ArrayList<>();
		}
		List<Goods> backpack = new ArrayList<>();
		List<Goods> otherGoods = new ArrayList<>();
		for(Goods goods:back) {
			if(goods == null) {
				continue;
			}
			if(goods.pos>=41 && goods.pos<=165) {
				backpack.add(goods);
			}else if(goods.pos>=1 && goods.pos<=40) {
				otherGoods.add(goods);
			}
			
		}
		chara.setBackpack(backpack);
		chara.setOtherGoods(otherGoods);
		List<Vo_PET_STORE> petStores = JSONObject.parseArray(characters.getPetStore(), Vo_PET_STORE.class);
		if(petStores == null) {
			petStores = new ArrayList<>();
		}
		chara.setPetStores(petStores);
		//4.8.1开始这些信息从缓存加载 end
		chara.setShizhuang(JSONObject.parseArray(characters.getShizhuang(), Goods.class));
		chara.setTexiao(JSONObject.parseArray(characters.getTexiao(), Goods.class));
		chara.setGenchong(JSONObject.parseArray(characters.getGenchong(), Goods.class));
		chara.setListshouhu(JSONObject.parseArray(characters.getListshouhu(), ShouHu.class));
		chara.setCustomShizhuang(JSONObject.parseArray(characters.getCustomShizhuang(), Goods.class));
		//太阴之气
		chara.setTyzqStore(JSONObject.parseArray(characters.getTyzqStore(), Goods.class));
		if(chara.tyzqStore == null) {
			chara.tyzqStore = new ArrayList<>();
		}
		
		// 查询出当前用户的宠物
		List<CharaPet> petsByCid = GameData.that.charaPetService.getPetsByCuid(characters.getGid());
		List<Petbeibao> petbeibaos = new ArrayList<>();
		for (CharaPet cp : petsByCid) {
			Petbeibao pet = JSONObject.parseObject(cp.getPet(), Petbeibao.class);
			pet.id = cp.getId();
			List<Vo_12023_0> tianshu = pet.tianshu;
			for(Vo_12023_0 ts:tianshu) {
				ts.id = pet.id;
				ts.owner_id = chara.id;
			}
			petbeibaos.add(pet);
		}
		chara.setPets(petbeibaos);
		// 判断是真身还是元婴
		switchChara(chara, true);
		chara.id = characters.getId();
		this.chara = chara;
		this.characters = characters;
		GameObjectCharMng.add(this);
		GameMap gameMap = GameLine.getGameMap(chara.line, chara.mapName);
		this.gameMap = gameMap;
		// 设置权限
		Accounts accounts = GameData.that.baseAccountsService.findById(characters.getAccountId());
		if (accounts != null) {
			this.privilege = accounts.getPrivilege() == null ? 0 : accounts.getPrivilege();
		} else {
			this.privilege = 0;
		}
		GameCommonUtil.flyInit(this);
	}

	public static void switchChara(Chara chara, boolean isInit) {
		CharaBaseInfo info = null;
		// 切换元婴
		if (chara.upgrade_state != 0) {
			info = chara.charaYuanyingInfo;
			chara.level = chara.upgrade_level;
		} else {
			// 切换真身
			info = chara.charaRealInfo;
			chara.level = chara.realLevel;
		}
		chara.phy_power = info.phy_power;
		chara.life = info.life;
		chara.speed = info.speed;
		chara.mag_power = info.mag_power;
		chara.accurate = info.accurate;
		chara.attribPoint = info.attribPoint;
		chara.def = info.def;
		chara.dex = info.dex;
		chara.wiz = info.wiz;
		chara.mana = info.mana;
		chara.parry = info.parry;
		chara.max_life = info.max_life;
		chara.max_mana = info.max_mana;

		chara.metal = info.metal;
		chara.wood = info.wood;
		chara.water = info.water;
		chara.fire = info.fire;
		chara.earth = info.earth;
		chara.polarPoint = info.polarPoint;
		
		chara.chongwuchanzhanId = info.chongwuchanzhanId;
		chara.chongwuluezhenId = info.chongwuluezhenId;
		chara.zuoqiId = info.zuoqiId;
		chara.yidongsudu = info.yidongsudu;
		chara.zuowaiguan = info.zuowaiguan;
		chara.zuoqiwaiguan = info.zuoqiwaiguan;
		chara.tao = info.tao;
		chara.taoPoint = info.taoPoint;
		chara.autofight_select = info.autofight_select;
		chara.autofight_skillaction = info.autofight_skillaction;
		chara.autofight_skillno = info.autofight_skillno;
		chara.userAutoAddPoint = info.userAutoAddPoint;
		chara.equipPage = info.equipPage;
		chara.jiNengList = info.jiNengList;

		// 初始化不做
		if (!isInit) {
			// 删除当前角色装备
			Iterator<Goods> iter = chara.otherGoods.iterator();
			while (iter.hasNext()) {
				Goods g = iter.next();
				if (g.pos >= 1 && g.pos <= 20) {
					iter.remove();
					Goods goods1 = new Goods();
					goods1.goodsBasics = null;
					goods1.goodsInfo = null;
					goods1.goodsLanSe = null;
					goods1.pos = g.pos;
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods1));
				}
				if(g.pos == 40) {
					iter.remove();
					Goods goods1 = new Goods();
					goods1.goodsBasics = null;
					goods1.goodsInfo = null;
					goods1.goodsLanSe = null;
					goods1.pos = g.pos;
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods1));
				}
			}
			chara.weapon_icon = 0;
			// 在添加进去
			for (Goods g : info.equip.values()) {
				chara.otherGoods.add(g);
				if (g.pos == 1) {
					chara.weapon_icon = g.goodsInfo.type;
				}
			}
		}
	}

	public static GameObjectChar getGameObjectChar() {
		GameObjectChar gameObjectChar = GameObjectChar.GAMEOBJECTCHAR_THREAD_LOCAL.get();
		return gameObjectChar;
	}
	
	public static int send(@SuppressWarnings("rawtypes") BaseWrite baseWrite, Object obj) {
		GameObjectChar gameObjectChar = GameObjectChar.GAMEOBJECTCHAR_THREAD_LOCAL.get();
		@SuppressWarnings("unchecked")
		ByteBuf write = baseWrite.write(obj);
		gameObjectChar.ctx.writeAndFlush(write);
		return write.readableBytes();
	}

	// 给角色发消息?
	@SuppressWarnings("unchecked")
	public static void send(@SuppressWarnings("rawtypes") BaseWrite baseWrite, Object obj, int id) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if (gameObjectChar == null) {
			return;
		}
		ByteBuf write = baseWrite.write(obj);
		gameObjectChar.ctx.writeAndFlush(write);
	}

	// 给队伍发消息?
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sendduiwu( BaseWrite baseWrite, Object obj, int duiyuanid) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(duiyuanid);
		// 如果是队长
		if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
				&& gameObjectChar.gameTeam.duiwu.get(0).id == duiyuanid) {
			for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
				GameObjectChar gameObjectChar2 = GameObjectCharMng
						.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(i).id);
				// add tzhang 添加对象是否在线的判断
				if (!gameObjectChar2.isOnline()) {
					continue;
				}
				// add:e
				ByteBuf write = baseWrite.write(obj);
				gameObjectChar2.ctx.writeAndFlush(write);
			}
		} else {
			ByteBuf write2 = baseWrite.write(obj);
			gameObjectChar.ctx.writeAndFlush((Object) write2);
		}
	}

	protected void send0(ByteBuf write) {
		this.ctx.writeAndFlush((Object) write);
	}

	/**
	 * 让玩家下线
	 * @param gameObjectCharas 玩家
	 */
	public void offline(Object... gameObjectCharas) {
		GameObjectChar thisGameObjectChar;
		if(gameObjectCharas == null || gameObjectCharas.length == 0) {
			thisGameObjectChar = this;
		}else {
			thisGameObjectChar = (GameObjectChar) gameObjectCharas[0];
		}
		try {
			//让这个人在地图上消失
			this.gameMap.send(new MSG_DISAPPEAR(), this.chara.id);
			this.gameMap.leave(this);
			//设置战斗信息
			int fightId = thisGameObjectChar.chara.zhandouId;
			GameCore.fightObject.remove(fightId);
			String zhandouInfo = chara.zhandouInfo;
			if(zhandouInfo != null) {
				GameConfig.canzhanBoos.remove(zhandouInfo);
			}
			chara.zhandouInfo = null;
			chara.isFight = false;
			chara.zhandouId = 0;
			//如果角色在试道场主动退出游戏，
			if(chara.mapName.equals("试道场")) {
				thisGameObjectChar.shiDaoFlag.set(false) ;
				//设置城里地图
				chara.x = 132;
				chara.y = 51;
				chara.mapid = 5000;
				chara.mapName = "天墉城";
			}else if(chara.mapName.equals("通天塔")) {
				chara.y = 16;
				chara.x = 114;
				chara.mapid = 5000;
				chara.mapName = "天墉城";
			}
			this.chara.updatetime = System.currentTimeMillis();
			this.chara.online_time += this.chara.updatetime - this.chara.uptime;
			// 把角色需要的信息复制到这个对象中
			CharaBaseInfo setInfo = SaveCharaTimes.setInfo(chara);
			if (chara.upgrade_state != 0) {
				chara.charaYuanyingInfo = setInfo;
				chara.level = chara.upgrade_level;
			} else {
				chara.charaRealInfo = setInfo;
				chara.realLevel = chara.level;
			}
			//下线
			GameCommonUtil.setGoodsDefaultValue(chara, false);
			SaveChara saveChara = new SaveChara();
			BeanUtils.copyProperties(chara, saveChara);
			String jsonString = JSONObject.toJSONString(saveChara);
			this.characters.setData(jsonString);
			// 以下设置其他信息
			this.characters.setShizhuang(JSONObject.toJSONString(chara.shizhuang));
			this.characters.setTexiao(JSONObject.toJSONString(chara.texiao));
			this.characters.setGenchong(JSONObject.toJSONString(chara.genchong));
			//组合
			List<Goods> goodsAll = new ArrayList<>();
			goodsAll.addAll(chara.backpack);
			goodsAll.addAll(chara.getOtherGoods());
			
			this.characters.setPetStore(JSONObject.toJSONString(chara.petStores));
			this.characters.setCangku(JSONObject.toJSONString(chara.cangku));
			this.characters.setBackpack(JSONObject.toJSONString(goodsAll));
			this.characters.setCardStore(JSONObject.toJSONString(chara.cardStore));
			this.characters.setTyzqStore(JSONObject.toJSONString(chara.tyzqStore));
			//4.8.1开始
			this.characters.setLevel(chara.getLevel());
			this.characters.setChargeScore(chara.getChargeScore());
			this.characters.setX(chara.x);
			this.characters.setY(chara.y);
			this.characters.setSex(chara.sex);
			this.characters.setGoldCoin(chara.goldCoin);
			this.characters.setPortrait(chara.waiguan);
			this.characters.setMapId(chara.mapid);
			this.characters.setMapName(chara.mapName);
			this.characters.setPolar(chara.polar);
			this.characters.setMonthTao(chara.monthTao);
			this.characters.setShut(chara.shut);
			log.info("chara.getFixedTeamName():"+chara.getFixedTeamName()+"chara.fixedTeamName:"+chara.fixedTeamName);
			this.characters.setFixedTeamName(chara.fixedTeamName);
			//4.8.1开始
			
			this.characters.setListshouhu(JSONObject.toJSONString(chara.listshouhu));
			this.characters.setOnline(0);
			GameData.that.baseCharactersService.updateById(this.characters);
			// 设置宠物信息
			List<CharaPet> charaPets = new ArrayList<>();
			for (Petbeibao p : chara.pets) {
				CharaPet cp = new CharaPet();
				cp.setId(p.id);
				cp.setPet(JSONObject.toJSONString(p));
				cp.setUpdateTime(new Date());
				charaPets.add(cp);
				GameData.that.charaPetService.updateByPrimaryKeySelective(cp);
			}
			//队伍信息
			GameTeamUtil.quitTeam(thisGameObjectChar);
			
			log.info("客户端下线。。。。。。。。。。{}",this.chara.name);
		} finally {
			this.ctx.disconnect();
			List<GameObjectChar> all = GameObjectCharMng.getAll();
			all.remove(this);
			thisGameObjectChar.gameTeam = null;
		}
	}

	public void outherLogin() {
		// 断开连接
		this.ctx.disconnect();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendOne(BaseWrite baseWrite, Object obj) {
		ByteBuf buff = baseWrite.write(obj);
		ByteBuf copy = buff.copy();
		this.send0(copy);
	}
	public List<Chara> getGameTemDuiwu() {
		if (this.getGameTeam() != null && this.getGameTeam().duiwu != null) {
			return this.getGameTeam().duiwu;
		}
		return null;
	}
	
	/**
	 * 发送提醒
	 * @param content
	 */
	public void sendTips(String content) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = content;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		this.sendOne(new M20481_0(), vo_20481_0);
	}

	static {
		GAMEOBJECTCHAR_THREAD_LOCAL = new ThreadLocal<GameObjectChar>();
	}
}