package com.fengshen.server.process.user;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.constant.RedisKeyConstant;
import com.fengshen.server.domain.config.Gm;
import com.fengshen.server.process.dari.rank_role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.CharaNickname;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_61537_0;
import com.fengshen.server.data.vo.Vo_61537_0;
import com.fengshen.server.data.vo.Vo_8285_0;
import com.fengshen.server.data.write.M61537_0;
import com.fengshen.server.data.write.M8285_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 创建角色
 * 
 *
 */
@Service
@Slf4j
public class CMD_CREATE_NEW_CHAR implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String char_name = GameReadTool.readString(buff); // 角色名
		int gender = GameReadTool.readShort(buff); // 性别
		int polar = GameReadTool.readShort(buff); // 派系
		log.info("创建角色");
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		if (GameConfig.config.getBaseConfig().getStopServer() == 1) {
			if (session.account.getPrivilege() == 0) {
				GameObjectChar.send(new MSG_KICK_OFF(), "区组维护。");
				ctx.close();
				return;
			}
		}
		if(com.mysql.jdbc.StringUtils.isNullOrEmpty(session.getAccount().getPassword()) || "default_pwd".equals(session.getAccount().getPassword())) {
			GameObjectChar.send(new MSG_KICK_OFF(), "请初始化登录密码，否则无法进入游戏！");
			ctx.close();
			return;
		}
		Integer xianZhiZhuCe = GameConfig.config.getXianZhiZhuCe();
		if(xianZhiZhuCe == 1){
			Characters oneByAccount = GameData.that.characterService.findOneByAccountId(session.accountid);
			if(oneByAccount != null){
				String a = "本区限制一个账号一个角色，请勿注册多个角色!";
				GameCommonUtil.dialogOk("#R" + a + "#W ");
				return;
			}
		}


		List<Characters> onLineChar = GameData.that.characterService.getOnLineChar(session.accountid,"name","id");
		if(!onLineChar.isEmpty()) {
			Accounts account = session.getAccount();
			InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
			String clientIp = ipSocket.getAddress().getHostAddress();
			//该账号已有角色在线
			Characters onlineCharacters = onLineChar.get(0);
			//找到这个人
			GameObjectChar oldSession = GameObjectCharMng.getGameObjectChar(onlineCharacters.getId());
			Map<String,Object> data = new HashMap<>();
			data.put("gameObjectChara", oldSession);
			data.put("char_name", onlineCharacters.getName());
			data.put("clientIp", clientIp);
			data.put("lastLoginIp", account.getLastLoginIp());
			session.confirmData = data;
			GameUtil.confirm(session, StringUtils.join("当前账号下角色#Y",onlineCharacters.getName(),"#n正在游戏中，无法使用其他角色登录，确定要执行顶号操作登录#Y",onlineCharacters.getName(),"#n吗？"), "topLogin");
			return;
		}
		
		char_name = char_name.trim();
		Pattern p = Pattern.compile(GameCommonUtil.filterStr);
		Matcher m = p.matcher(char_name);
		if (m.find()) {
			GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
			return;
		}else if(char_name.length()<2) {
			GameCommonUtil.dialogOk("昵称应在2-12个字符");
			return;
		}else if(char_name.contains("�")) {
			GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
			return;
		}else {
			for(char ch:char_name.toCharArray()) {
				if(org.apache.commons.lang3.StringUtils.isBlank(ch+"")) {
					GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
					return;
				}
			}
		}
		//判断是否为gm
		if(char_name.toUpperCase().indexOf("GM")!=-1) {
			if(session.privilege == 0) {
				GameCommonUtil.dialogOk("违规昵称！");
				return;
			}
		}
		String filterText = GameConfig.config.getMingan().getSettings().getFilterNickText();
		if(!filterText.isEmpty()) {
			for(String ft:filterText.split("、")) {
				String name = char_name.toUpperCase();
				Pattern p2 = Pattern.compile(".*"+ft+".*");
				Matcher m2 = p2.matcher(name);
				boolean isValid = m2.matches();
				if(isValid) {
					GameCommonUtil.dialogOk("该昵称被系统禁用");
					return;
				}
			}
		}
		char_name = char_name.replaceAll("\\s*", "");
		// 角色名重复
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", char_name);
		if (GameData.that.characterService.selectCountByExample(example) > 0) {
			GameCommonUtil.dialogOk("该名字已被使用");
			return;
		}
		if (!Utils.getLocalMac().equals("488AD2BD5FE8") && !Utils.getLocalMac().equals("005056C00001")) {
			//删除昵称库这个名称
			Example random = new Example(CharaNickname.class);
			random.createCriteria().andEqualTo("name", char_name);
			GameData.that.charaNicknameService.deleteByExample(random);
		}
		String uuid = GameCommonUtil.UUID();
		Vo_8285_0 vo_8285_0 = new Vo_8285_0();
		vo_8285_0.name = char_name;
		vo_8285_0.gid = uuid;
		Chara chara = new Chara(char_name, gender, polar, uuid);
		this.addbackpack(chara);
		session.chara = chara;
		GameUtil.zhuangbeiValue(session);
		chara.max_mana = chara.zbAttribute.dex + chara.dex; // dex法力
		chara.max_life = chara.zbAttribute.def + chara.def; // def气血
		chara.mapid = 1000; // 角色的位置

		Characters characters = new Characters();
		characters.setName(char_name);
		characters.setPolar(chara.polar);
		characters.setGid(uuid);
		characters.setXiaozi(0);
		characters.setBlock(0);
		characters.setLevel(chara.level);
		characters.setSex(chara.sex);
		characters.setMapId(chara.mapid);
		characters.setMapName(chara.getMapName());
		characters.setChargeScore(chara.getChargeScore());
		characters.setGoldCoin(chara.getGoldCoin());
		characters.setPortrait(chara.waiguan);
		characters.setX(chara.x);
		characters.setY(chara.y);
		characters.setMonthTao(chara.getMonthTao());
		characters.setShut(chara.shut);
		// 把角色需要的信息复制到这个对象中
		SaveChara saveChara = new SaveChara();
		BeanUtils.copyProperties(chara, saveChara);
		String jsonString = JSONObject.toJSONString(saveChara);
		characters.setData(jsonString);
		// 以下信息自4.8.1之后转换到redis中 start
		characters.setCangku(JSONObject.toJSONString(chara.cangku));
		characters.setBackpack(JSONObject.toJSONString(chara.backpack));
		characters.setPetStore(JSONObject.toJSONString(chara.pets));
		characters.setCardStore(JSONObject.toJSONString(chara.cardStore));
		// 以下信息自4.8.1之后转换到redis中 start
		
		characters.setShizhuang(JSONObject.toJSONString(chara.shizhuang));
		characters.setTexiao(JSONObject.toJSONString(chara.texiao));
		characters.setGenchong(JSONObject.toJSONString(chara.genchong));
		characters.setListshouhu(JSONObject.toJSONString(chara.listshouhu));
		characters.setCustomShizhuang(JSONObject.toJSONString(chara.customShizhuang));
		characters.setTyzqStore(JSONObject.toJSONString(chara.tyzqStore));
		characters.setAccountId(session.accountid); // 角色账号
		// 将当前角色添加到全局角色对象中
		GameData.that.characterService.add(characters);
		chara.id = characters.getId();
		chara.allId = chara.id * 100000;
		session.init(characters);

		RedisUtils redisUtils = GameData.that.redisUtils;
		String rankString = redisUtils.get(RedisKeyConstant.RANK_LEFT);
		rank_role rank = new rank_role();
		rank.setRank((short) 30002);
		rank.setName(char_name);
		rank.setDamage(0);
		if(rankString == null) {
			rankString = "{}";
		}
		Map<String, rank_role> map = JSON.parseObject(rankString, Map.class);
		map.put(char_name, rank);
		redisUtils.set(RedisKeyConstant.RANK_LEFT, map);

		Characters where = new Characters();
		where.setAccountId(session.accountid);
		List<Characters> charactersList = GameData.that.characterService.findByObjSelectProperties(where, "lastLoginTime", "online","gid","name", "level", "polar", "portrait");
		ListVo_61537_0 listvo_61537_0 = listjiaose(charactersList);
		GameUtil.sendMeTips("恭喜你角色创建成功。请稍等正在进入游戏...");
		ByteBuf write = new M8285_0().write(vo_8285_0);
		ctx.writeAndFlush(write);
		ByteBuf write2 = new M61537_0().write(listvo_61537_0);
		ctx.writeAndFlush(write2);
		GameUtil.openDlg("WaitDlg");
	}

	@Override
	public int cmd() {
		return 8284;
	}

	// 初始化角色的装备栏
	public void addbackpack(Chara chara) {
		ZhuangbeiInfo zhuangb = new ZhuangbeiInfo();
		List<ZhuangbeiInfo> byAttrib = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService.findByAttrib(1);
		for (int i = 0; i < byAttrib.size(); ++i) {
			if (byAttrib.get(i).getMaster() == chara.sex && byAttrib.get(i).getAmount() == 3) {
				zhuangb = byAttrib.get(i);
				Goods goods = new Goods();
				goods.pos = 3;
				goods.goodsCreate(zhuangb);
				chara.backpack.add(goods);
			}
			if (byAttrib.get(i).getMaster() == chara.sex && byAttrib.get(i).getAmount() == 2) {
				zhuangb = byAttrib.get(i);
				Goods goods = new Goods();
				goods.pos = 2;
				goods.goodsCreate(zhuangb);
				chara.backpack.add(goods);
			}
		}
		zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("麻鞋");
		Goods goods2 = new Goods();
		goods2.pos = 10;
		goods2.goodsCreate(zhuangb);
		chara.backpack.add(goods2);
	}

	public static ListVo_61537_0 listjiaose(List<Characters> charactersList) {
		ListVo_61537_0 listvo_61537_0 = new ListVo_61537_0();
		try {
			listvo_61537_0.severState = 1;
			listvo_61537_0.count = charactersList.size();
			listvo_61537_0.openServerTime = (int) (System.currentTimeMillis()/1000L);
			listvo_61537_0.account_online = 0;
			for (Characters character : charactersList) {
				Vo_61537_0 v61537 = new Vo_61537_0();
				v61537.passive_mode = character.getPortrait();
				v61537.metal = character.getPolar();
				v61537.str = character.getName();
				v61537.iid_str = character.getGid();
				v61537.skill = character.getLevel();
				v61537.type = character.getPortrait();
				v61537.last_login_time = 1;
				//开启开局动画
				if(GameConfig.config.getBaseConfig().getCloseStartAnimation() == 1) {
					v61537.last_login_time = character.getLastLoginTime();
				}
				v61537.char_online_state = character.getOnline();
				listvo_61537_0.vo_61537_0.add(v61537);
			}
		} catch (Exception e) {
			log.error("{}", e);
		}
		listvo_61537_0.count = listvo_61537_0.vo_61537_0.size();
		return listvo_61537_0;
	}
}