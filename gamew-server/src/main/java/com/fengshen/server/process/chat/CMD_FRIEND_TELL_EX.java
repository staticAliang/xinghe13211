package com.fengshen.server.process.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Charge;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.Friend;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_24505_0;
import com.fengshen.server.data.vo.Vo_53569_0;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M24505_0;
import com.fengshen.server.data.write.M53569_0;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.Mingan;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.SensitiveWordInit;
import com.fengshen.server.util.SensitivewordFilter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 好友聊天频道
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_FRIEND_TELL_EX implements GameHandler {
	private static HashMap<Integer,List<Characters>> gmJueSe = new HashMap<>();
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int flag = GameReadTool.readShort(buff);
		String name = GameReadTool.readString(buff);
		int compress = GameReadTool.readShort(buff);
		int orgLength = GameReadTool.readShort(buff);
		String msg = GameReadTool.readString2(buff);
		int cardCount = GameReadTool.readShort(buff);
		String cardParam = "";
		if (cardCount != 0) {
			cardParam = GameReadTool.readString(buff);
		}
		int voiceTime = GameReadTool.readInt(buff);
		String token = GameReadTool.readString2(buff);
		String receive_gid = GameReadTool.readString(buff);
		log.info("好友聊天频道,flg={},compress={},orgLength={},cardParam={},voiceTime={},token{},receive_gid{}", flag,
				compress, orgLength, cardParam, voiceTime, token, receive_gid);

		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Chara chara2 = null;
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(name);
		if (gameObjectChar == null) {
//			Characters characters = GameData.that.characterService.findOneByNameSelectProperties(name, "data");
//			if (characters == null) {
//				return;
//			}
//			String data = characters.getData();
//			chara2 = JSONObject.parseObject(data, Chara.class);
			GameUtil.sendMeTips("暂不支持留言聊天！");
			return;
		} else {
			chara2 = gameObjectChar.chara;
		}
		// 排除gm
		if (GameObjectChar.getGameObjectChar().privilege != 1000) {
			// 判断该角色是否被禁言
			if (chara.shut != 0) {
				GameUtil.sendMeTips("你已被禁言");
				return;
			}
			// 判断全服是否禁言
			if (GameConfig.config.getAllJinyan() != 0) {
				GameUtil.sendMeTips("gm关闭了聊天.");
				return;
			}
		}
		if (GameConfig.config.getBaseConfig().getIsChargeSpeak() == 1) {
			if (GameObjectChar.getGameObjectChar().privilege == 0) {
				Characters characters = GameData.that.characterService.findById(chara.id);
				Accounts accounts = GameData.that.baseAccountsService.findById(characters.getAccountId());
				List<Charge> chargeList = (List<Charge>) GameData.that.baseChargeService
						.findByAccountname(accounts.getName());
				if (chargeList == null || chargeList.isEmpty()) {
					// 查找是否有充值记录
					GameUtil.sendMeTips("充值任意金额,即可说话。");
					return;
				}
			}
		}
		// 判断是否为对方好友
		Example friendExample = new Example(Friend.class);
		friendExample.createCriteria().andEqualTo("friendGid", chara.uuid).andEqualTo("gid", chara2.uuid);
		Friend isFriend = GameData.that.friendService.selectOneByExample(friendExample);
		if (isFriend == null) {
			if (GameObjectCharMng.getGameObjectChar(chara2.id) != null) {
				// 获取对方设置
				if (chara2.getSettings().get("refuse_stranger_msg") != null
						&& chara2.getSettings().get("refuse_stranger_msg") == 1) {
					// 判断拒绝陌生人的等级
					int level = chara2.settingrefuse_stranger_level;
					if (chara.level < level) {
						GameCommonUtil.dialogOk(String.join("", "对方拒绝#Y", String.valueOf(level), "#W级以下的陌生人消息。"),
								chara.id);
						return;
					}
				}
			}
		}
		// 判断是否满足等级
		if (chara.level < GameConfig.config.getBaseConfig().getMinSpeakLevel()) {
			GameUtil.sendMeTips("升至#R" + GameConfig.config.getBaseConfig().getMinSpeakLevel() + "#n级方可发言！");
			return;
		}
		// 过滤敏感词
		Mingan mingan = GameConfig.config.getMingan();
		if (mingan != null && mingan.getStatus() != 0) {
			// 如果有名片的话采用拼接的方式
			StringBuilder message = new StringBuilder();
			String cardMsg = "";
			// 名片不参与过滤
			if (cardCount > 0) {
				// 剪切到名片开头
				message.append(msg.substring(0, msg.indexOf("{")));
				// 名片信息
				cardMsg = msg.substring(msg.indexOf("{") + 1, msg.indexOf("}"));
				message.append("{\t%s}");
				message.append(msg.substring(msg.indexOf("}") + 1, msg.length()));
				msg = message.toString();
			} else {
				message.append(msg);
			}
			// 敏感词库
			List<String> datas = SensitiveWordInit.readSensitiveWord();
			// 过滤后的
			msg = SensitivewordFilter.replaceSensitiveWord(datas, msg, 1, "*");
			msg = String.format(msg, cardMsg);
		}

		if (cardCount != 0) {
			// 集市和珍宝名片暂时不处理
			if (msg.indexOf("集市=") == -1 && msg.indexOf("珍宝=") == -1) {
				if (msg.indexOf("今日统计") != -1) {
					String m = "今日统计:" + msg;
					msg = m;
				}
				Map<String, Object> data = new HashMap<>();
				data.put("id", chara.id);
				data.put("time", System.currentTimeMillis());
				msg = msg.replace("}", "|" + JSONObject.toJSONString(data));
				// 这个需要手动设置下
				if (msg.indexOf("结婚纪念册") != -1) {
					msg = "{\t" + chara.name + "的结婚纪念册=" + msg.substring(msg.indexOf("=") + 1, msg.length());
				}
			}
		}
		
		//称谓
		ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
		Chengwei chengwei = chengweiService.getChengweiByName(chara.chenhao);
		if(chengwei != null) {
			//在前面在上颜色
			msg = chengwei.getColor()+msg;
		}
		
		Vo_24505_0 vo_24505_0 = GameUtil.a24505(chara2);
		GameObjectChar.send(new M24505_0(), vo_24505_0);
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = GameUtil.a61545(chara2);
		GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		Vo_16383_0 a16383 = GameUtil.a16383(chara, msg, 9, chara2);
		//开启了语音
		if(GameConfig.config.getBaseConfig().getVoiceStatus() == 1) {
			a16383.token = token;
			a16383.voiceTime = voiceTime;
			a16383.orgLength = orgLength;
		}
		GameObjectChar.send(new M16383_0(), a16383);
		if (GameObjectCharMng.getGameObjectChar(chara2.id) != null) {
			a16383 = GameUtil.a16383(chara, msg, 9);
			GameObjectCharMng.getGameObjectChar(chara2.id).sendOne(new M16383_0(), a16383);
			//GM聊天监控
			this.setGmSex(msg,chara,chara2);
			Vo_53569_0 vo_53569_0 = new Vo_53569_0();
			vo_53569_0.gid = chara2.uuid;
			vo_53569_0.online = 1;
			GameObjectChar.send(new M53569_0(), vo_53569_0);
		} else {
			Vo_53569_0 vo_53569_0 = new Vo_53569_0();
			vo_53569_0.gid = chara2.uuid;
			vo_53569_0.online = 0;
			GameObjectChar.send(new M53569_0(), vo_53569_0);
		}
		// 获取接受对象是否有设置自动回复
		if (chara2.getSettings().get("auto_reply_msg") != null && chara2.getSettings().get("auto_reply_msg") == 1) {
			if (isFriend != null) {
				// 双方是好友关系，开始处理自动回复
				String autoReplayMsg = chara2.settingauto_reply_msg;
				if (!StringUtil.isNullOrEmpty(autoReplayMsg)) {
					a16383 = GameUtil.a16383(chara2, autoReplayMsg, 9);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M16383_0(), a16383);
				}
			}
		}
	}
	public static void setGmSex(String msg,Chara chara,Chara chara2){
		List<Characters>  charas = CMD_FRIEND_TELL_EX.getGM();
		for (Characters chara1 : charas) {
			String message = "对#R"+chara2.name+"#n私聊："+msg;
			Vo_16383_0 a16383 = GameUtil.a16383(chara, message, 1);
			GameObjectCharMng.getGameObjectChar(chara1.getId()).sendOne(new M16383_0(), a16383);
		}
	}
	public static void setGmSex(String msg,Chara chara,Integer channel){
		String partyName = chara.getPartyName();
		List<Characters>  charas = CMD_FRIEND_TELL_EX.getGM();
		for (Characters chara1 : charas) {
			String message = "";
			if(channel ==5){
				message = "在#R"+partyName+"#n帮派频道说："+msg;
			}else if(channel ==4){
				message = "在#R队伍频道#n说："+msg;
			}else if(channel ==1){
				message = "在#R当前频道#n说："+msg;
			}
			Vo_16383_0 a16383 = GameUtil.a16383(chara, message, 1);
			GameObjectCharMng.getGameObjectChar(chara1.getId()).sendOne(new M16383_0(), a16383);
		}
	}


	/**
	 * 设置gm状态
	 *
	 * @param
	 */
	public static List<Characters> getGM() {
		List<Characters> charas = new ArrayList<>();
		// 设置权限
		List<Integer> gmId = new ArrayList<>();

		List<Accounts> accounts = GameData.that.baseAccountsService.findAllManage(null,1000);
		for (Accounts account : accounts) {
			List<Characters> charactersList = GameData.that.characterService.findByAccountIdManage(Integer.valueOf(account.getId()));
			for (Characters characters : charactersList) {
				//gmId.add(characters.getId());
				GameObjectChar gameObjectChar2 = GameObjectCharMng
						.getGameObjectChar(characters.getId());
				if(gameObjectChar2 != null){
					if (gameObjectChar2.isOnline()) {
						charas.add(characters);
					}
				}
			}
		}
		log.info("在线GM角色为:"+ JSON.toJSON(gmId));

		// add tzhang 添加对象是否在线的判断
//		List<GameObjectChar> all = GameObjectCharMng.getAll();
//		for (GameObjectChar gameSession : all) {
//			Integer wanjiaId = gameSession.characters.getId();
//			if(gmId.contains(wanjiaId) ){
//				charas.add(gameSession.characters);
//			}
//		}
		return charas;
	}
	@Override
	public int cmd() {
		return 20590;
	}
}
