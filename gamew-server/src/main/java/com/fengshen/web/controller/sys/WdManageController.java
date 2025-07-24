package com.fengshen.web.controller.sys;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import com.fengshen.server.domain.*;
import com.fengshen.server.domain.config.*;
import com.fengshen.server.game.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.DesUtil;
import com.fengshen.core.util.ErrorCode;
import com.fengshen.core.util.FieldFilterUtil;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.BlackList;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.FightObjectInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.domain.sys.SysUser;
import com.fengshen.db.service.base.FightObjectInfoService;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.process.CommonCmd;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.SensitivewordFilter;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import com.qiniu.util.Md5;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RequestMapping("/sys/wd")
@RestController
public class WdManageController extends BaseController {

	@Autowired
	private FightObjectInfoService fo;
	
	/**
	 * 获取在线用户
	 * @return
	 */
	@PostMapping("/getOnLineUsers")
	public ResponseView getOnLineUsers(Page<Characters> page, String name, String id, Integer flag) {
		
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("online desc");
		PageInfo<Characters> pageInfo = null;
		if(!StringUtils.isNullOrEmpty(id)) {
			pageInfo = new PageInfo<Characters>(GameData.that.characterService.findByAccountIdManage(Integer.valueOf(id)));
		}else {
			Example example = new Example(Characters.class);
			example.selectProperties("gid","online","block","level","goldCoin","polar","sex","portrait","name","chargeScore","x","y","mapName","accountId","shut","addTime");
			example.orderBy("online").desc();
			Criteria createCriteria = example.createCriteria();
			if(!StringUtils.isNullOrEmpty(name)) {
				createCriteria.andLike("name", "%"+name+"%");
	    	}
			if(flag == null) {
				createCriteria.andEqualTo("xiaozi", 0);
			}
			pageInfo = new PageInfo<Characters>(GameData.that.baseCharactersService.selectByExample(example));
		}
		//在线玩家
		long online = GameData.that.baseCharactersService.getOnlines();
		List<Map<String,Object>> chara = new ArrayList<>();
		List<Integer> onLineIds = new ArrayList<>();
		for(Characters c2:pageInfo.getList()) {
			Map<String,Object> map = new HashMap<>();
			map.put("id", c2.getId());
			map.put("uuid", c2.getGid());
			map.put("name", c2.getName());
			map.put("sex", c2.getSex()== 1?"男":"女");
			map.put("mapName", c2.getMapName());
			map.put("x", c2.getX());
			map.put("y", c2.getY());
			map.put("goldCoin", c2.getGoldCoin());
			map.put("polar", c2.getPolar());
			map.put("level", c2.getLevel());
			map.put("chargeScore", c2.getChargeScore());
			map.put("isEnable",  c2.getBlock());
			map.put("onLine",  c2.getOnline()==0?"离线":"在线");
			map.put("accountid",  c2.getAccountId());
			map.put("shut",  c2.getShut());
			map.put("addTime",  c2.getAddTime());
			chara.add(map);
			onLineIds.add(c2.getId());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", pageInfo.getTotal());
		resultMap.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getList() == null) {
			resultMap.put("resultData", new ArrayList<Object>());
		} else {
			resultMap.put("resultData", chara);
		}
		//查询在线玩家记录
		resultMap.put("onlines", online);
		resultMap.put("currentPageNum", pageInfo.getPageNum());
		return ResponseView.ok(resultMap);
	}
	
	/**
	 * 发送装备到用户角色中.
	 * @param data
	 * @return
	 */
	@PostMapping("/attrSendToChara")
	public ResponseView attrSendToChara(@RequestParam()Map<String,Object> data) {
		
		if(data.get("name") == null) {
			ResponseView.fail(ErrorCode.E16001);
		}
		Chara thisChara = getChara((String)data.get("name"));
		if(thisChara == null)
			ResponseView.fail("该角色不存在,或不在线");
		 //绿色.
        Object xiangxing = data.get("xiangxing");
		//用户在线.
        int pos2 = GameUtil.packPoint(thisChara);
		if(pos2 == -1) {
			return ResponseView.fail("玩家背包不足");
		}
		ZhuangbeiInfo zhuangb =  GameData.that.baseZhuangbeiInfoService.findOneByStr((String)data.get("zb_name"));
		Goods goods = new Goods();
        goods.pos = pos2;
        //绿色炼化和绿色共鸣
        if(xiangxing != null) {
    	   final List<Hashtable<String, Integer>> hashtables3 = ForgingEquipmentUtils.appraisalGreenEquipment(zhuangb.getAmount(), zhuangb.getAttrib(), Integer.valueOf((String)xiangxing));
           for (final Hashtable<String, Integer> maps4 : hashtables3) {
               if (maps4.get("groupNo") == 12) {
                   maps4.put("groupType", 2);
                   GoodsLvSe goodsLvSe = (GoodsLvSe)JSONObject.parseObject(JSONObject.toJSONString((Object)maps4), GoodsLvSe.class);
                   if (goodsLvSe == null) {
                       goodsLvSe = new GoodsLvSe();
                   }
                   goods.goodsLvSe = goodsLvSe;
               }
               if (maps4.get("groupNo") == 8) {
                   maps4.put("groupType", 2);
                   final GoodsLvSeGongMing goodsLvSeGongMing2 = (GoodsLvSeGongMing)JSONObject.parseObject(JSONObject.toJSONString((Object)maps4), GoodsLvSeGongMing.class);
                   goods.goodsLvSeGongMing = goodsLvSeGongMing2;
               }
           }
        }
        //蓝属性
        GoodsLanSe lanse = new GoodsLanSe();
        Object lanse1 = data.get("lanse1");
        Object lanse2 = data.get("lanse2");
        Object lanse3 = data.get("lanse3");
        if(lanse1 != "") {
        	Integer lanse1Val = Integer.valueOf((String)data.get("lanse1Val"));
        	try {
        		lanse.getClass().getField((String)lanse1).set(lanse, lanse1Val);
			} catch (Exception e) {
			} 
        }
        if(lanse2 != "") {
        	Integer val = Integer.valueOf((String)data.get("lanse2Val"));
        	try {
        		lanse.getClass().getField((String)lanse2).set(lanse, val);
			} catch (Exception e) {
			} 
        	
        }
        if(lanse3 != "") {
        	Integer val = Integer.valueOf((String)data.get("lanse3Val"));
        	try {
        		lanse.getClass().getField((String)lanse3).set(lanse, val);
			} catch (Exception e) {
			} 
        }
        goods.goodsLanSe = lanse;
        //粉属性
        Object fense = data.get("fense");
        if(fense != "") {
        	zhuangb.setQuality("粉色");
        	Integer fenseVal = Integer.valueOf((String)data.get("fenseVal"));
        	GoodsFenSe gf = new GoodsFenSe();
        	try {
				gf.getClass().getField((String)fense).set(gf, fenseVal);
				goods.goodsFenSe = gf;
			} catch (Exception e) {
			} 
        }
        //黄属性
        Object huangse = data.get("huangse");
        if(huangse != "") {
        	zhuangb.setQuality("黄色");
        	Integer huangseVal = Integer.valueOf((String)data.get("huangseVal"));
        	GoodsHuangSe hs = new GoodsHuangSe();
        	try {
        		hs.getClass().getField((String)huangse).set(hs, huangseVal);
				goods.goodsHuangSe = hs;
			} catch (Exception e) {
			} 
        }
        if(xiangxing != null) {
        	//产生绿色属性
        	zhuangb.setQuality("绿色");
        }
        goods.goodsCreate(zhuangb);
        if(xiangxing != null) {
        	//绿化相性
        	goods.goodsInfo.suit_enabled = Integer.valueOf((String)xiangxing);
        }
      //设置基本信息.
       if(data.get("gaizao_number") != "" && data.get("gaizao_number") != "0") {
        	int gaizao = Integer.valueOf((String)data.get("gaizao_number"));
        	goods.goodsInfo.color = gaizao;
        	List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment((String)data.get("zb_name"),
	        		zhuangb.getAmount(), zhuangb.getAttrib(), gaizao);
        	 for (final Hashtable<String, Integer> maps2 : hashtables2) {
                 if (maps2.get("groupNo") == 10) {
                     maps2.put("groupType", 2);
                     final GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZao.class);
                     goods.goodsGaiZao = goodsGaiZao;
                 }
             }
        }
        goods.goodsInfo.owner_id = 1;
        //是否未鉴定
        goods.goodsInfo.degree_32 = 0;
        GameUtil.addwupin(goods, thisChara);
        GameObjectCharMng.getGameObjectChar(thisChara.id).sendOne(new M65525_0(), thisChara.backpack);
        GameUtil.notifyPrompt(thisChara.id,  "获得了#R" + goods.goodsInfo.str + "");
		return ResponseView.ok();
	}
	
	@PostMapping("/sendCharInfo")
	public ResponseView sendCharInfo(String name, String type, Integer value, Integer sendAllChara) {
		List<Chara> charas = new ArrayList<>();
		if(sendAllChara == 1) {
			//全服发送
			for(GameObjectChar gameObject:GameObjectCharMng.getAll()) {
				charas.add(gameObject.chara);
			}
		}else {
			Chara chara = getChara(name);
			if(chara == null) {
				ResponseView.fail("角色不在线，或不存在！");
			}
			charas = Lists.newArrayList(chara);
		}
		for(Chara chara:charas) {
			//1类型 2扣除还是减去
			String[] split = type.split("-");
			switch(split[0]) {
			case "经验":
				GameUtil.addjingyanToManage(chara, value);
				break;
			case "潜能":
				GameUtil.addQianNeng(chara, value);
				break;
			case "积分":
				if("2".equals(split[1])) {
					//扣除
					chara.chargeScore -= value;
			GameUtilRenWu.refshPointTask(chara);

					if(chara.chargeScore < 0) {
						chara.chargeScore = 0;
					}
					GameUtil.notifyPrompt(chara.id,  "GM扣除了你#R" + value + "#W积分");
				}else {
					GameUtil.addchargeScore(GameObjectCharMng.getGameObjectChar(chara.id), value, "GM后台","GM给你发送了#R" + value + "#n积分");
				}
				break;
			case "抽奖":
				chara.shadow_self+=value;
				GameUtil.notifyPrompt(chara.id,  "GM给你发送了#R" + value + "#Wn抽奖");
				break;
			case "道行":
				GameUtil.adddaohang(chara, value);
				break;
			case "金元宝":
				if("2".equals(split[1])) {
					//扣除
					chara.goldCoin-=value;
					if(chara.goldCoin<0) {
						chara.goldCoin = 0;
					}
					GameUtil.notifyPrompt(chara.id,  "你GM被扣除了#R" + value + "#n个金元宝");
				}else {
					GameUtil.addJinYuanBao(GameObjectCharMng.getGameObjectChar(chara.id), value, "GM后台","GM给你发送了#R" + value + "#n个金元宝");
				}
				break;
			case "银元宝":
				if("2".equals(split[1])) {
					//扣除
					chara.silverCoin-=value;
					if(chara.silverCoin<0) {
						chara.silverCoin = 0;
					}
					GameUtil.notifyPrompt(chara.id,  "你GM被扣除了#R" + value + "#n个银元宝");
				}else {
					GameUtil.addYinYuanBao(GameObjectCharMng.getGameObjectChar(chara.id), value, "GM后台","GM给你发送了#R" + value + "#n个金元宝");
				}
				break;
			case "帮贡":
				if(StringUtils.isNullOrEmpty(chara.getPartyName())) {
					ResponseView.fail("别开玩笑，他还未加入帮派呢！");
				}
				if("2".equals(split[1])) {
					//扣除
					chara.contrib-=value;
					if(chara.contrib<0) {
						chara.contrib = 0;
					}
					GameUtil.notifyPrompt(chara.id,  "你GM被扣除了#R" + value + "#n点帮贡");
				}else {
					chara.contrib+=value;
					GameUtil.notifyPrompt(chara.id,  "GM给你发送了#R" + value + "#n点帮贡");
				}
				break;
				default:
					ResponseView.fail("指令不存在");
					break;
			}
			
			//刷新界面
			final ListVo_65527_0 vo_65527_2 = GameUtil.a65527(chara);
	        GameObjectChar.send(new M65527_0(), vo_65527_2, chara.id);
		}
		return ResponseView.ok();
	}
	
	/**
	 * 发送消息
	 * @param channel 频道
	 * @param content 消息内容
	 * @return
	 */
	@PostMapping("/sendMsg")
	public ResponseView sendMsg(Integer channel, String content) {
		
		switch(channel) {
		case 6:
			GameUtil.sendSystemMessage(channel, content);
			break;
		case 7:
			GameUtil.sendSystemMessage(channel, content);
			break;
		case 19:
			content = content.replaceAll("\r|\n", " ");
			GameUtil.sendSystemMessage(channel, content);
			break;
		case 30:
			GameUtil.sendSystemMessage(channel, content);
			break;
		}
		return ResponseView.ok();
	}
	
	/**
	 * 获取配置信息
	 * @param configName 配置名称
	 * @return
	 */
	@PostMapping("/getConfig")
	public ResponseView getConfig(String configName) throws Exception {
		if(!StringUtils.isNullOrEmpty(configName)) {
			if("shidao".equals(configName)) {
				return ResponseView.ok(GameConfig.config.getShidao());
			}
		}
		return ResponseView.ok(GameConfig.config);
	}
	
	/**
	 * 修改配置
	 * @param cf 配置对象
	 * @return
	 */
	@PostMapping("/setConfig")
	public ResponseView setConfig(String name, Integer status, String value) {
		if("jinyan".equals(name)) {
			GameConfig.config.setAllJinyan(status);
		}else if("xianZhiZhuCe".equals(name)) {
			GameConfig.config.setXianZhiZhuCe(status);
		}else if("pk".equals(name)) {
			GameConfig.config.setPkLock(status);
		}else if("mingan".equals(name)) {
			GameConfig.config.getMingan().setStatus(status);
		}else if("fb".equals(name)) {
			GameConfig.config.getFb().setStatus(status);
		}else if("gmset".equals(value)) {
			//gm指令配置
			Gm gm = GameConfig.config.getGm();
			try {
				Field f = gm.getClass().getDeclaredField(name);
				f.setAccessible(true);
				f.set(gm, status);
			} catch (Exception e) {
				ResponseView.fail("{}");
			}
		}else if("minganText".equals(name)) {
			//刷新敏感词
			SensitivewordFilter.initSensitiveWord(Lists.newArrayList(value.split("、")));
			GameConfig.config.getMingan().getSettings().setFilterText(value);
		}else if("chargeLink".equals(name)) {
			GameConfig.config.getBaseConfig().setChargeLink(value);
		}else if("wpe".equals(name)) {
			GameConfig.config.getWpeConfig().setStatus(status);
		}else if("filterNickText".equals(name)) {
			GameConfig.config.getMingan().getSettings().setFilterNickText(value);
		}
		//刷新配置
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * 根据Id获取战斗对象信息
	 * @param id 
	 * @return
	 */
	@PostMapping("/getTobjectById")
	public ResponseView getTobjectById(Integer id) {
		if(id == null) {
			ResponseView.fail(ErrorCode.E1004);
		}
		FightObjectInfo findByID = fo.findOneByID(id);
		FieldFilterUtil.includeField(findByID, "daohang","skill","magAttack","phyAttack","life","name");
		return ResponseView.ok(findByID);
	}
	
	/**
	 * 更新战斗对象
	 * @param t
	 * @return
	 */
	@PostMapping("/updateTobjectById")
	public ResponseView getTobjectById(FightObjectInfo t) {
		t.setUpdateTime(new Date());
		return ResponseView.ok(fo.updateById(t));
	}
	
	/**
	 * 禁言
	 * @param uuid
	 * @return
	 */
	@PostMapping("/charChatsDisable")
	public ResponseView charChatsDisable(String uuid) {
		
		return ResponseView.ok();
	}

	/**
	 * 获取配置信息
	 * @param configName 配置名称
	 * @return
	 */
	@PostMapping("/getDariConfig")
	public ResponseView getDariConfig(String configName) throws Exception {
		if(!StringUtils.isNullOrEmpty(configName)) {
			if("dari".equals(configName)) {
				return ResponseView.ok(GameConfig.config.getDari());
			}
		}
		return ResponseView.ok(GameConfig.config);
	}


	/**
	 * 大日金乌设置
	 * @param data
	 * @return
	 */
	@PostMapping("/dariSet")
	public ResponseView dariSet(@RequestParam Map<String,String> data) {
		Dari dari = GameConfig.config.getDari();
		if("huoyanzhiling_left".equals(data.get("type"))) {
			String val = data.get("value");
			int n = Integer.valueOf(val);
			dari.setHuoyanzhiling_left(n);
		}else if("jingwuzhiling_left".equals(data.get("type"))){
			String val = data.get("value");
			int n = Integer.valueOf(val);
			dari.setJingwuzhiling_left(n);
		}else if("huoshishou_left".equals(data.get("type"))){
			String val = data.get("value");
			int n = Integer.valueOf(val);
			dari.setHuoshishou_left(n);
		}else if("darijinwu_left".equals(data.get("type"))){
			String val = data.get("value");
			int n = Integer.valueOf(val);
			dari.setDarijinwu_left(n);
		}else if("dari_life_str".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_life_str(val);
		}else if("dari_max_life_str".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_max_life_str(val);
		}else if("dari_1_1_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_1_reward(val);
		}else if("dari_1_2_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_2_reward(val);
		}else if("dari_1_3_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_3_reward(val);
		}else if("dari_1_4_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_4_reward(val);
		}else if("dari_1_5_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_5_reward(val);
		}else if("dari_1_6_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_6_reward(val);
		}else if("dari_1_7_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_7_reward(val);
		}else if("dari_1_8_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_8_reward(val);
		}else if("dari_1_9_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_9_reward(val);
		}else if("dari_1_10_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_10_reward(val);
		}else if("dari_1_11_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_11_reward(val);
		}else if("dari_1_12_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_12_reward(val);
		}else if("dari_1_13_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_13_reward(val);
		}else if("dari_1_14_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_14_reward(val);
		}else if("dari_1_15_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_15_reward(val);
		}else if("dari_1_16_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_16_reward(val);
		}else if("dari_1_17_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_17_reward(val);
		}else if("dari_1_18_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_18_reward(val);
		}else if("dari_1_19_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_19_reward(val);
		}else if("dari_1_20_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_1_20_reward(val);
		}else if("dari_20_30002_reward".equals(data.get("type"))){
			String val = data.get("value");
			dari.setDari_20_30002_reward(val);
		}
		flushConfig();
		return ResponseView.ok();
	}



	/**
	 * 获取配置信息
	 * @param configName 配置名称
	 * @return
	 */
	@PostMapping("/getTouDingChengHaoConfig")
	public ResponseView getTouDingChengHaoConfig(String configName) throws Exception {
		if(!StringUtils.isNullOrEmpty(configName)) {
			if("TouDingChengHao".equals(configName)) {
				return ResponseView.ok(GameConfig.config.getTouDingChengHao());
			}
		}
		return ResponseView.ok(GameConfig.config);
	}

	/**
	 * 自定义头顶称号
	 * @param data
	 * @return
	 */
	@PostMapping("/touDingChengHaoSet")
	public ResponseView touDingChengHaoSet(@RequestParam Map<String,String> data) {
		TouDingChengHao touDingChengHao = GameConfig.config.getTouDingChengHao();
		if("chenghao".equals(data.get("type"))) {
			String val = data.get("value");
			String[] split = val.split(",");
			touDingChengHao.setChenghao(split);
		}
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * 角色操作
	 * @param uuid
	 * @return
	 */
	@PostMapping("/charOperations")
	public ResponseView charOperations(@RequestParam Map<String,Object> data) {

		//uuid
		String uuid = (String) data.get("uuid");
		//操作类型
		String type = (String) data.get("type");
		//具体值
		String value = (String) data.get("value");
		//重置某个人的日常任务
		if("resetOneTask".equals(type)) {
			//重置任务
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(uuid);
			if(gameObjectCharByUUid == null) {
				Characters select = GameData.that.characterService.findOneByGidSelectProperties(uuid,"data","id");
				Chara chara = JSONObject.parseObject(select.getData(),Chara.class);
				GameUtil.resetRenwuByChara(chara);
				Characters update = new Characters();
				update.setId(chara.getId());
				update.setData(JSONObject.toJSONString(chara));
				GameData.that.characterService.updateById(update);
			}else {
				//当前用户在线
				Chara chara = gameObjectCharByUUid.chara;
				GameUtil.resetRenwuByChara(chara);
			}
			return ResponseView.ok();
		}
		//所有在线玩家.
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		boolean isOnline = false;
		for(GameObjectChar g:all) {
			if(g.characters.getGid().equals(uuid)) {
				Chara chara = g.chara;
				isOnline = true;
				if("disable".equals(type)) {
					//禁言
					chara.shut = Integer.valueOf(value);
					if(value.equals("1")) {
						GameCommonUtil.dialogOk("你已被GM禁言",chara.id);
						GameUtil.sendSystemMessage(7, "#Y"+chara.name+"#n被#RGM#n禁言");
					}else {
						GameCommonUtil.dialogOk("禁言解除",chara.id);
					}
					Characters c = new Characters();
					c.setId(g.characters.getId());
					c.setShut(chara.shut);
					GameData.that.baseCharactersService.updateById(c);
				}else if("del".equals(type)) {
					//先提示5秒钟后强制下线.
					if(value.equals("1")) {
						GameUtil.sendSystemMessage(7, g.chara.name+"玩家被#RGM#n封号");
		            	g.sendOne(new MSG_KICK_OFF(), "对不起您违反了游戏的公平,角色已被封。");
		            	g.offline();
		                GameObjectCharMng.getGameObjectCharList().remove(g);
					}
					g.characters.setBlock(Integer.valueOf(value));
					//更新账号
					Characters c = new Characters();
					c.setId(g.characters.getId());
					c.setBlock(Integer.valueOf(value));
					c.setOnline(0);
					GameData.that.baseCharactersService.updateById(c);
				}else if("down".equals(type)) {
					g.sendOne(new MSG_KICK_OFF(), "对不起您已被强制下线.");
					//先提示5秒钟后强制下线.
	            	g.offline();
	                GameObjectCharMng.getGameObjectCharList().remove(g);
	                Characters c = new Characters();
					c.setId(g.characters.getId());
					c.setOnline(0);
					GameData.that.baseCharactersService.updateById(c);
				}
				break;
			}
		}
		if(!isOnline) {
			//当用户不在线的话.无需发送通知.
			Example example = new Example(Characters.class);
			example.selectProperties("data","id","accountId");
			example.createCriteria().andEqualTo("gid", uuid);
			Characters characters = GameData.that.baseCharactersService.selectOneByExample(example);
			Chara chara = JSONObject.parseObject(characters.getData(), Chara.class);
			if("disable".equals(type)) {
				//禁言
				chara.shut = Integer.valueOf(value);
				characters.setShut(chara.shut);
				characters.setUpdateTime(new Date());
				GameData.that.baseCharactersService.updateById(characters);
			}else if("del".equals(type)) {
				//更新账号
				Characters c = new Characters();
				c.setId(characters.getId());
				c.setBlock(Integer.valueOf(value));
				GameData.that.baseCharactersService.updateById(c);
				//解封
				if(Integer.valueOf(value) == 0) {
					Accounts account = GameData.that.baseAccountsService.findById(characters.getAccountId());
					if(account != null) {
						ArrayList<Object> list = new ArrayList<>(); 
						if(account.getMac() != null) {
							list.add(account.getMac());
						}
						if(account.getLastLoginIp() != null) {
							list.add(account.getLastLoginIp());
						}
						if(account.getRegisterIp() != null) {
							list.add(account.getRegisterIp());
						}
						if(!list.isEmpty()) {
							Example deletExample = new Example(BlackList.class);
							deletExample.createCriteria().andIn("data", list);
							GameData.that.blackListService.deleteByExample(deletExample);
						}
						//把账号设为正常状态
						account.setDeleted(false);
						account.setUpdateTime(LocalDateTime.now());
						GameData.that.baseAccountsService.updateById(account);
					}
				}
			}else if("down".equals(type)) {
                Characters c = new Characters();
                c.setGid(uuid);
				c.setOnline(0);
				example = new Example(Characters.class);
				example.createCriteria().andEqualTo("gid", uuid);
				GameData.that.baseCharactersService.updateByExampleSelective(c, example);
			}
		}
		return ResponseView.ok();
	}
	
	/**
	 * 停服维护
	 * @return
	 */
	@PostMapping("/stopServer")
	public ResponseView stopServer(Integer status) {
		//设置状态
		GameConfig.config.getBaseConfig().setStopServer(status);
		flushConfig();
		if(status == 1) {
			//所有在线玩家.
			List<GameObjectChar> all = GameObjectCharMng.getAll();
			for(GameObjectChar g:all) {
				Chara chara = g.chara;
				GameUtil.notifyPrompt(chara.id,  "服务器维护,1分钟后你将被强制下线,请保存好东西.");
			}
			if(timer == null) {
				timer = new Timer();
			}
			timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	for(GameObjectChar g:all) {
	    				Chara chara = g.chara;
	    				//如果在战斗则直接退出战斗
	    				if(chara.isFight) {
	    					FightContainer fightContainer = FightManager.getFightContainer(chara.id);
	    					if (fightContainer != null) {
	    						FightManager.sendOver(fightContainer, true);
	    						FightManager.listFight.remove(fightContainer);
	    					}
	    				}
	    				g.sendOne(new MSG_KICK_OFF(), "服务器维护");
		            	g.offline();
	    			}
	            	Iterator<Integer> iterator = GameCore.luoboTaoziCids.iterator();
					while(iterator.hasNext()) {
						Integer id = iterator.next();
						GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
						if(gameObjectChar != null) {
							//删除任务
							gameObjectChar.chara.taskMap.remove("萝卜桃子大收集");
							gameObjectChar.lbtzTaskCount = 1;
						}else {
							//数据库查询
							Characters ch = GameData.that.baseCharactersService.findOneByIdSelectProperties(id, "id","data");
							Chara chara = JSONObject.parseObject(ch.getData(),Chara.class);
							chara.taskMap.remove("萝卜桃子大收集");
							ch.setData(JSONObject.toJSONString(chara));
							//保存
							GameData.that.baseCharactersService.updateByPrimaryKeySelective(ch);
						}
					}
	            	//停机维护，则手动调用gc
	            	System.gc();
	            	timer = null;
	            }
			 }, 60000); 
		}else {
			//关闭维护
			if(timer != null) {
				timer.cancel();
				timer = null;
			}
		}
		return ResponseView.ok();
		
	}
	
	
	/**
	 * 发送法宝和宠物
	 * @param sw 操作类型
	 * @param type 发送类型
	 * @param name 名字
	 * @param level 等级
	 * @param xiangxin 相性,只有当类型为法宝时才有
	 * @return
	 */
	@PostMapping("/getFabaoAndPets")
	public ResponseView getFabaoAndPets(String charName, String sw, String type, String name, Integer level, Integer xiangxin) {
		if(StringUtils.isNullOrEmpty(charName)) {
			ResponseView.fail("请输入角色名");
		}else if(StringUtils.isNullOrEmpty(sw)) {
			ResponseView.fail("请选择操作类型");
		}else if(!sw.equals("pet") && !sw.equals("fabao") && !sw.equals("item")) {
			ResponseView.fail("类型错误");
		}else if(StringUtils.isNullOrEmpty(type)) {
			ResponseView.fail("请选择类型");
		}
		Chara chara = getChara(charName);
		if(chara != null) {
			if("pet".equals(sw)) {
				if("坐骑".equals(type)) {
					GameUtil.huodezuoji(chara, name, "GM后台");
				}else {
					//获取宠物
					GameUtil.huodechongwu(chara, name, Integer.valueOf(type), "GM后台");
				}
			}else if("fabao".equals(sw)) {
				if(level == null) {
					level = 1;
				}
				if(xiangxin == null) {
					GameUtil.huodefabao(chara, name, level, "GM后台");
				}else {
					//获取法宝
					GameUtil.huodefabao(chara, name, level, "GM后台", xiangxin);
				}
			}else if("item".equals(sw)) {
				GameUtil.huodedaoju(chara, name, level);
				GameCommonUtil.sendTips("你获得了#R"+name+"#n。",chara.id);
			}
		}
		return ResponseView.ok();
	}
	
	/**
	 * 获取首饰
	 * @param charaName 角色名
	 * @param type 类型 手镯、玉佩、项链
	 * @param name 首饰名称
	 * @param shuxing 首饰属性
	 * @return
	 */
	@PostMapping("/getShoushi")
	public ResponseView getShoushi(String charaName, String type, String name, String shuxing) {
		
		
		return ResponseView.ok();
	}
	
	/**
	 * 海盗设置
	 * @param data
	 * @return
	 */
	@PostMapping("/haidaoSet")
	public ResponseView haidaoSet(@RequestParam Map<String,String> data) {
		//刷新海盗
		Haidao haidao = GameConfig.config.getHaidao();
		if("flush".equals(data.get("type"))) {
			//清除之前的海盗
			GameGongCheng.cleanHaiDao();
			//再次刷新海盗
			GameGongCheng.sendHaidao(GameLine.gameGongCheng);
		}else if("clean".equals(data.get("type"))){
			GameGongCheng.cleanHaiDao();
		}else if("jingyan".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setJingyan(n);
		}else if("qianneng".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setQianneng(n);
		}else if("jinbi".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setJinbi(n);
		}else if("zhuangbei".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setWeijianding(n);
		}else if("daoju".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value").trim();
			if(StringUtils.isNullOrEmpty(val)) {
				haidao.setDaoju(new String[] {});
			}else {
				String[] split = val.split(",");
				haidao.setDaoju(split);
			}
		}else if("daohang".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setDaohang(n);
		}else if("count".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setCount(n);
		}else if("teamNumber".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setTeamNumber(n);
		}else if("haidaoNum".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			int n = Integer.valueOf(val);
			haidao.setHaidaoNum(n);
		}else if("times".equals(data.get("type"))) {
			//获取具体值.
			String val = data.get("value");
			if(!StringUtils.isNullOrEmpty(val)) {
				String[] split = val.split(",");
				haidao.setTimes(split);
			}else {
				haidao.setTimes(new String[] {});
			}
		}else if("getTimes".equals(data.get("type"))){
			//获取时间.
			return ResponseView.ok(haidao.getTimes());
		}else if("cleanXingXing".equals(data.get("type"))) {
			
		}
			
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * 刷新排行榜
	 * @return
	 */
	@PostMapping("/rankRefresh")
	public ResponseView rankRefresh() {
		try {
			GameData.that.rj.refreshRank();
		} catch (JsonProcessingException e) {
			ResponseView.fail("刷新失败");
		}
		return ResponseView.ok();
	}
	
	/**
	 * 新手配置
	 * @param data
	 * @return
	 */
	@PostMapping("/newCharaSettings")
	public ResponseView newCharaSettings(@RequestParam Map<String,String> data) {
		if("getNewCharas".equals(data.get("type"))) {
			//获取
			return ResponseView.ok(GameConfig.config.getNewChara());
		}if("charSet".equals(data.get("type"))) {
			//设置
			GameConfig.config.getNewChara().setJinyuanbao(Integer.valueOf(data.get("jinyuanbao")));
			GameConfig.config.getNewChara().setYinyuanbao(Integer.valueOf(data.get("yinyuanbao")));
			GameConfig.config.getNewChara().setMoney(Integer.valueOf(data.get("money")));
			GameConfig.config.getNewChara().setJifen(Integer.valueOf(data.get("jifen")));
			GameConfig.config.getNewChara().setChoujiang(Integer.valueOf(data.get("choujiang")));
			GameConfig.config.getNewChara().setQianneng(Integer.valueOf(data.get("qianneng")));
			GameConfig.config.getNewChara().setChenghao(data.get("chenghao"));
			flushConfig();
		}
		return ResponseView.ok();
	}
	
	
	/**
	 * 试道配置
	 * @param data 数据
	 * @return
	 */
	@PostMapping("/shidaoSet")
	public ResponseView shidaoSet(@RequestParam Map<String,String> data) {
		ShiDao shidao = GameConfig.config.getShidao();
		if("count".equals(data.get("type"))) {
			//元魔数量
			shidao.setCount(Integer.valueOf(data.get("value")));
		}else if("team".equals(data.get("type"))) {
			//队伍人数
			shidao.setTeamNumber(Integer.valueOf(data.get("value")));
		}else if("no1".equals(data.get("type") )) {
			//第一名配置
			shidao.setNo1(getNoMap(data));
			
		}else if("no2".equals(data.get("type") )) {
			//第二名配置
			shidao.setNo2(getNoMap(data));
		}else if("no3".equals(data.get("type") )) {
			//第三名配置
			shidao.setNo3(getNoMap(data));
		}else if("timeAndWeek".equals(data.get("type"))) {
			//开启时间
			String startTime = data.get("time1");
			String endTime = data.get("time2");
			long min = DateUtil.getMin(startTime, endTime);
			if((min/1000/60)<0) {
				ResponseView.fail("请重新选择,结束时间不能小于开始时间");
			}
			shidao.setTimes(new String[] {startTime,endTime});
			//日期.
			if(data.get("week") != "") {
				shidao.setWeek(data.get("week").split(","));
			}else {
				shidao.setWeek(new String[] {});
			}
			GameShiDao.statzhuangtai = 0;
			//踢出所有人
			for (int i = 0; i < GameShiDao.shidaolevel.length; ++i) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(GameShiDao.shidaolevel[i]);
				if (gameZone == null) {
					continue;
				}
				for(GameMap gameMap:gameZone) {
					// 清除所有元魔
					gameMap.gameShiDao.shidaoyuanmo.clear();
					// 获取地图人数.
					if (gameMap != null && gameMap.sessionList != null
							&& gameMap.sessionList.size()>0) {
						for (GameObjectChar g : gameMap.sessionList) {
							// 把当前地图的全部送回城，人数不足结束
							Chara ch = g.chara;
							// 全部带回城里
							ch.x = 128;
							ch.y = 52;
							GameLine.getGameMapname(ch.line, "天墉城").join(GameObjectCharMng.getGameObjectChar(ch.id));
							// 清空该阶段所有人员
							GameShiDao.getShiDaoSession(GameShiDao.shidaolevel[i]).clear();
							gameMap.sessionList.clear();
						}
					}
				}
			}
			GameShiDao.maps = null;
			GameShiDao.cleanShidaoSession();
		}else if("f1".equals(data.get("type"))) {
			//元魔时间
			int intValue = MapUtils.getIntValue(data, "value");
			if(intValue == 0) {
				ResponseView.fail("请输入正确的时间");
			}
			shidao.setF1(intValue*60*1000);
		}else if("f2".equals(data.get("type"))) {
			//pk时间
			int intValue = MapUtils.getIntValue(data, "value");
			if(intValue == 0) {
				ResponseView.fail("请输入正确的时间");
			}
			shidao.setF2(intValue*60*1000);
		}else if("minOneTeamNum".equals(data.get("type"))) {
			//单个队伍最少人数
			 String string = MapUtils.getString(data, "value");
			 String[] split = string.split("-");
			 if(split.length<2) {
				 ResponseView.fail("格式错误");
			 }
			 //判断是否为数字
			 if(!Utils.isNumber(split[0]) || !Utils.isNumber(split[1])) {
				 ResponseView.fail("请输入数字");
			 }
			 //判断第一个是否比第一个小
			 int n1 = Integer.valueOf(split[0]);
			 int n2 = Integer.valueOf(split[1]);
			 if(n1>n2) {
				 ResponseView.fail("第一个数字大于第二个数字");
			 }
			 shidao.setMinOneTeamNum(string);
		}else if("freeTime".equals(data.get("type"))) {
			//休息时间
			int intValue = MapUtils.getIntValue(data, "value");
			shidao.setFreeTime(intValue);
		}else if("isCloseActivePk".equals(data.get("type"))) {
			//是否关闭主动PK
			int intValue = MapUtils.getIntValue(data, "value");
			shidao.setIsCloseActivePk(intValue);
		}else if("maxRound".equals(data.get("type"))) {
			int intValue = MapUtils.getIntValue(data, "value");
			shidao.setMaxRound(intValue);
		}
		
		//刷新配置
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * 设置奖励
	 * @param data
	 * @return
	 */
	private Map<String,Object> getNoMap(Map<String,String> data) {
		Integer jinyuanbao = Integer.valueOf(data.get("jinyuanbao"));
		Integer yinyuanbao = Integer.valueOf(data.get("yinyuanbao"));
		Integer jifen = Integer.valueOf(data.get("jifen"));
		String chengwei = data.get("chengwei");
		String chongwu = data.get("chongwu");
		String daoju = data.get("daoju");
		Map<String,Object> no = new HashMap<>();
		no.put("jinyuanbao", jinyuanbao);
		no.put("yinyuanbao", yinyuanbao);
		no.put("jifen", jifen);
		no.put("chengwei", chengwei);
		no.put("chongwu", chongwu);
		no.put("daoju", daoju);
		return no;
	}
	
	/**
	 * 移动用户到指定地图和地点
	 * @param name 玩家名称
	 * @param mapId 地图id
	 * @param x 
	 * @param y
	 * @return
	 */
	@PostMapping("/moveChara")
	public ResponseView moveChara(String name, Integer mapId, Integer x, Integer y) {
		Chara chara = getChara("小伟");
		if(chara != null) {
			chara.x = 20;
			chara.y = 20;
			GameLine.getGameMap(chara.line, 38004).join(GameObjectCharMng.getGameObjectChar(chara.id));
		}
		
		return ResponseView.ok();
	}
	
	/**
	 * 设置基础设置
	 * @param bc
	 * @return
	 */
	@PostMapping("/setBaseConfig")
	public ResponseView setBaseConfig(BaseConfig bc, String cron, Integer count) {
		BaseConfig b = GameConfig.config.getBaseConfig();
		if(cron != null) {
			b.getShuaxing().put("cron", cron);
		}
		if(count != null) {
			b.getShuaxing().put("count", count);
		}
		if(cron == null && count == null) {
			GameConfig.config.setBaseConfig(bc);
			bc.setShuaxing(b.getShuaxing());
		}
		flushConfig();
		return ResponseView.ok();
	}
	
	@PostMapping("/refreshXing")
	public ResponseView refreshXing(int size) {
		for(int i=0;i<size;i++) {
			GameBossTianDiXing.shuaxing(null);
		}
		return ResponseView.ok();
	}
	@PostMapping("/refreshZhanShen")
	public ResponseView refreshZhanShen(int size) {
		GameGongCheng.sendZhanshen2(GameLine.gameGongCheng,size);
		return ResponseView.ok();
	}
	@PostMapping("/testBtn")
	public ResponseView testBtn() {
		
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar("我很好啊");
		
		ChannelFuture cf = gameObjectChar.ctx.write(new CommonCmd(9999).write(null));
		cf.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				boolean success = future.isSuccess();
				System.out.println(success);
			}
		});
		return ResponseView.ok();
	}
	
	/**
	 * 设置幸运大使几率
	 * @param config
	 * @param count
	 * @return
	 */
	@PostMapping("/setLuckNpcConfig")
	public ResponseView setLuckNpcConfig(LuckDrawNpcConfig config, String count) {
		LuckDrawNpcConfig luckDrawNpcConfig = GameConfig.config.getLuckDrawNpcConfig();
		if(config.getJilv() != null) {
			luckDrawNpcConfig.setJilv(config.getJilv());
		}
		if(config.getNo1Jilv() != null) {
			luckDrawNpcConfig.setNo1Jilv(config.getNo1Jilv());
		}
		if(config.getNo2Jilv() != null) {
			luckDrawNpcConfig.setNo2Jilv(config.getNo2Jilv());
		}
		if(config.getNo3Jilv() != null) {
			luckDrawNpcConfig.setNo3Jilv(config.getNo3Jilv());
		}
		if(config.getNo4Jilv() != null) {
			luckDrawNpcConfig.setNo4Jilv(config.getNo4Jilv());
		}
		if(config.getNo5Jilv() != null) {
			luckDrawNpcConfig.setNo5Jilv(config.getNo5Jilv());
		}
		if(config.getNo6Jilv() != null) {
			luckDrawNpcConfig.setNo6Jilv(config.getNo6Jilv());
		}
		flushConfig();
		return ResponseView.ok();
	}
	
	
	
	/**
	 * 设置是否关闭开局
	 * @param status 0关闭1打开
	 * @return
	 */
	@PostMapping("/setCloseStartAnimation")
	public ResponseView setCloseStartAnimation(Integer status) {
		BaseConfig b = GameConfig.config.getBaseConfig();
		if(status == null) {
			status = 0;
		}
		b.setCloseStartAnimation(status);
		flushConfig();
		return ResponseView.ok();
	}
	/**
	 * 设置语音状态
	 * @param status 0关闭1打开
	 * @return
	 */
	@PostMapping("/setVoiceStatus")
	public ResponseView setVoiceStatus(Integer status) {
		BaseConfig b = GameConfig.config.getBaseConfig();
		if(status == null) {
			status = 0;
		}
		b.setVoiceStatus(status);
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * 结束战斗
	 * @param name 玩家姓名
	 * @return
	 */
	@PostMapping("/endCombat")
	public ResponseView endCombat(String name) {
		Chara chara = getChara(name);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		//如果当前对象正在强制PK,或者被PK中
		FightContainer fightContainer = FightManager.getFightContainer(chara.id);
		List<GameObjectChar> charas = new ArrayList<>();
		if(fightContainer != null) {
			FightManager.listFight.remove(fightContainer);
			List<FightTeam> fightTeams = fightContainer.teamList;
			for(FightTeam team:fightTeams) {
				List<FightObject> fightObjectList = team.fightObjectList;
				for(FightObject fightObject:fightObjectList) {
					if(fightObject.type == 1) {
						GameObjectChar obj = GameObjectCharMng.getGameObjectChar(fightObject.id);
						if(obj != null) {
							charas.add(obj);
						}
					}
				}
			}
		}else {
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				boolean find = false;
				for(Chara team:gameObjectChar.gameTeam.duiwu) {
					//如果队伍里面有他
					if(team.id == chara.id) {
						find = true;
						break;
					}
				}
				if(!find) {
					GameCommonUtil.endCombat(Lists.newArrayList(gameObjectChar),fightContainer, null);
					return ResponseView.ok();
				}
				for(Chara team:gameObjectChar.gameTeam.duiwu) {
					//如果队伍里面有他
					charas.add(GameObjectCharMng.getGameObjectChar(team.id));
				}
			}else {
				charas.add(gameObjectChar);
			}
		}
		GameCommonUtil.endCombat(charas,fightContainer, null);
		return ResponseView.ok();
	}
	
	/**
	 * 清除redis里面的缓存
	 * @return
	 */
	@PostMapping("/clearDatabase")
	public ResponseView clearDatabase(HttpSession session, String pwd) {
		//校验当前密码
		//pwd = DesUtil.encrypt(Md5.md5(pwd.getBytes()),"fswendao");
		SysUser user = (SysUser) session.getAttribute("user");
		if(!user.getPassword().equals(pwd)) {
			ResponseView.fail("密码错误");
		}
		GameData.that.redisUtils.deleteAll();
		return ResponseView.ok();
	}
}