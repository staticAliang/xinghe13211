package com.fengshen.server.process.party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.PartyType;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.data.vo.party.VO_PARTY_ICON;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.party.MSG_PARTY_ICON;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 修改成员
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_MODIFY_MEMBER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		String partyDesc = GameReadTool.readString(buff);
		int job = GameReadTool.readShort(buff);
		int changeBangZhu = GameReadTool.readShort(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("修改帮派成员：----name={},partyDesc={},job={},changeBangZhu={}",name,partyDesc,job,changeBangZhu);
		if(changeBangZhu == 1) {
			GameCommonUtil.dialogOk("暂不支持传位。");
			return;
		}
		//自己退出帮派
		if(job == 0) {
			//如果当前佩戴的是帮派的称谓.则去掉
			if(chara.chenhao.indexOf(chara.getPartyName()) != -1) {
				chara.chenhao = "";
			}
			chara.upPartyName = chara.getPartyName();
			chara.chenghao.remove(chara.getPartyName()+"帮"+chara.getPartyJob());
			//先扣除一半的帮贡
			chara.contrib/=2;
			//刷新称号

			GameUtil.refreshChengHao(chara);
			//发送消息通知
			GameCommonUtil.sendTips("你已成功退出#Y" +chara.getPartyName() + "#n帮。", chara.id);
			//更新信息
			GameUtil.sendUpdate(chara);
			//如果当前这个人在帮派总坛
			if(chara.mapid == 26000) {
				chara.x = 95;
				chara.y = 64;
	            GameLine.getGameMapname(chara.line, "天墉城").join(gameObjectChar);
			}
			chara.setPartyJob("");
			chara.setPartyName("");
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
			gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
			//刷新图标
			VO_PARTY_ICON icon = new VO_PARTY_ICON();
			icon.setId(chara.id);
			icon.setMd5Value("");
			gameObjectChar.gameMap.send(new MSG_PARTY_ICON(), icon);
			//删除这个成员
			Example example = new Example(PartyMember.class);
			example.createCriteria().andEqualTo("charaGid", chara.uuid);
			GameData.that.partyMemberService.deleteByExample(example);
			//更新帮派信息
			Party findByPartyName = GameData.that.partyService.findByPartyName(chara.upPartyName);
			if(job != 100 && job != 150) {
				//如果是副帮主的话.
				String leader = findByPartyName.getLeader();
				JSONArray parseArray = JSONObject.parseArray(leader);
				for (int i = 0; i < parseArray.size(); i++) {
					JSONObject json = parseArray.getJSONObject(i);
					if(json.getString("job").equals(PartyType.getValueByKey(job))) {
						parseArray.remove(i);
						break;
					}
				}
				//重新设置职位信息
				findByPartyName.setLeader(JSONObject.toJSONString(parseArray));
			}
			findByPartyName.setPopulation(findByPartyName.getPopulation()-1);
			GameData.that.partyService.updateByPrimaryKeySelective(findByPartyName);
			GameCore.partyMap.put(findByPartyName.getPartyName(), findByPartyName);
			return;
		}
		int online = 0;
		Chara toChara = null;
		if(GameObjectCharMng.getGameObjectCharByUUid(gid) != null) {
			online = 1;
			toChara = GameObjectCharMng.getGameObjectCharByUUid(gid).chara;
		}else {
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGid2(gid);
			if(findOneByGid2 != null) {
				toChara = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
				toChara.uuid = findOneByGid2.getGid();
			}
		}
		if(toChara == null) {
			return;
		}
		if(job == 65534) {
			//判断权限是否可以开除,如果是帮主的话.没有任何人可以开除
			if(toChara.partyJob.equals("帮主")) {
				GameCommonUtil.dialogOk("本帮帮主岂能说开除就开除。");
				return;
			}
			//被帮派管理人员开除,帮贡不会有任何变化
			Example example = new Example(PartyMember.class);
			example.createCriteria().andEqualTo("charaGid", gid);
			GameData.that.partyMemberService.deleteByExample(example);
			//查找到帮派信息
			Party findByPartyName = GameData.that.partyService.findByPartyName(chara.getPartyName());
			if(job != 100 && job != 150) {
				//如果是副帮主的话.
				String leader = findByPartyName.getLeader();
				JSONArray parseArray = JSONObject.parseArray(leader);
				for (int i = 0; i < parseArray.size(); i++) {
					JSONObject json = parseArray.getJSONObject(i);
					if(json.getString("job").equals(PartyType.getValueByKey(job))) {
						parseArray.remove(i);
						break;
					}
				}
				//重新设置职位信息
				findByPartyName.setLeader(JSONObject.toJSONString(parseArray));
			}
			//发送帮派通知
			GamePartyUtil.notifyPartyMsg(toChara.getPartyName(), "#Y"+toChara.getName()+"#n被帮派开除了");
			//被开除,不扣除帮贡
			toChara.upPartyName = "";
			toChara.chenghao.remove(toChara.getPartyName()+"帮"+toChara.getPartyJob());
			toChara.setPartyJob("");
			toChara.setPartyName("");
			if(toChara.chenhao.indexOf(chara.getPartyName()) != -1) {
				//如果当前佩戴的是帮派的称谓.则去掉
				toChara.chenhao = "";
			}
			//删除称号
			if(online == 1) {
				GameObjectChar toGameObjectChara = GameObjectCharMng.getGameObjectChar(toChara.id);
				//刷新称号
				GameUtil.refreshChengHao(chara);
				//发送消息通知
				GameCommonUtil.sendTips("你被#Y" +chara.getPartyName() + "#n帮#n开除了。", toChara.id);
				//更新信息
				GameUtil.sendUpdate(toChara);
				//如果当前这个人在帮派总坛
				if(toChara.mapid == 26000) {
					toChara.x = 95;
					toChara.y = 64;
		            GameLine.getGameMapname(toChara.line, "天墉城").join(toGameObjectChara);
				}
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(toChara);
				toGameObjectChara.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				//刷新图标
				VO_PARTY_ICON icon = new VO_PARTY_ICON();
				icon.setId(toChara.id);
				icon.setMd5Value("");
				toGameObjectChara.gameMap.send(new MSG_PARTY_ICON(), icon);
				GameUtilRenWu.removeTask("帮派任务", toChara);
				GameUtilRenWu.removeTask("帮派日常挑战", toChara);
			}else {
				//不在线更新数据库
				Characters ch = new Characters();
				//如果当前这个人在帮派总坛
				if(toChara.mapid == 26000) {
					toChara.x = 95;
					toChara.y = 64;
				}
				toChara.commonTaskMap.remove("帮派任务");
				toChara.commonTaskMap.remove("帮派日常挑战");
				ch.setId(toChara.id);
				ch.setData(JSONObject.toJSONString(toChara));
				GameData.that.baseCharactersService.updateById(ch);
				//发送开除邮件
				GameCommonUtil.sendSystemEmail(toChara, "你被#Y"+chara.getPartyName()+"#n帮开除了.", "帮派开除通知", "帮派");
			}
			findByPartyName.setPopulation(findByPartyName.getPopulation()-1);
			GameData.that.partyService.updateByPrimaryKeySelective(findByPartyName);
			//刷新缓存
			GameCore.partyMap.put(findByPartyName.getPartyName(), findByPartyName);
		}else {
			//任命
			String value = PartyType.getValueByKey(job);
			if(!"".equals(value)) {
				String msg = "";
				//判断任命类型
				if(job<PartyType.getKeyByValue(toChara.getPartyJob())) {
					//降级呜呜呜。。。
					msg = String.join("", "非常遗憾#Y",toChara.name,"#n被撤销本帮#R",toChara.getPartyJob(),"#n的职位被降级为#R",value,"#91m");
				}else {
					//升职加薪哈哈哈。。。。
					msg =  String.join("", "恭喜恭喜#Y",toChara.name,"#n被任命为本帮#R",value,"#50m");
				}
				//发送这个任命消息到帮会
				Example examplePartyMember = new Example(PartyMember.class);
				examplePartyMember.createCriteria().andEqualTo("partyId", GameCore.partyMap.get(chara.getPartyName()).getPartyId());
				List<PartyMember> partyMemerbs = GameData.that.partyMemberService.selectByExample(examplePartyMember);
				Vo_MESSAGE npcMessage = GameCommonUtil.npcMessage("帮派总管", msg, 0, 6036, 5);
				for(PartyMember p:partyMemerbs) {
					GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
					if(gameObjectCharByUUid != null) {
						gameObjectCharByUUid.sendOne(new MSG_MESSAGE(), npcMessage);
					}
				}
				//删除原来的帮派称号
				toChara.chenghao.remove(chara.getPartyName()+"帮"+toChara.partyJob);
				//更新称号
				toChara.chenghao.put(chara.getPartyName()+"帮"+value, chara.getPartyName()+"帮"+value);
				//设置角色新的帮派职位
				toChara.setPartyJob(value);
				if(toChara.chenhao.indexOf(chara.getPartyName()) != -1) {
					if(online == 1) {
						GameCommonUtil.changeTitle(GameObjectCharMng.getGameObjectChar(toChara.id), chara.getPartyName()+"帮"+value);
					}else {
						//如果当前装备的是帮派称谓就换成新的帮派称谓
						toChara.chenhao = chara.getPartyName()+"帮"+value;
					}
				}
				//添加新得称号
				if(online == 1) {
					//刷新称号
					GameUtil.refreshChengHao(chara);
					GameCommonUtil.sendTips("你被#Y" +chara.getPartyName() + "#n帮#n任命为#R"+value, toChara.id);
					//更新信息
					GameUtil.sendUpdate(toChara);
					//更新人物
					GameObjectCharMng.getGameObjectChar(toChara.id).gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(toChara));
				}else {
					//不在线更新数据库
					Characters ch = new Characters();
					ch.setId(toChara.id);
					ch.setData(JSONObject.toJSONString(toChara));
					GameData.that.baseCharactersService.updateById(ch);
					//发送任命邮件
					GameCommonUtil.sendSystemEmail(toChara, "你被#Y" +chara.getPartyName() + "#n帮#n任命为#R"+value, "帮派任命通知", "帮派");
				}
				//更新帮派成员表
				Example example = new Example(PartyMember.class);
				example.createCriteria().andEqualTo("charaGid", gid);
				PartyMember partyMember = new PartyMember();
				partyMember.setCharaGid(gid);
				String jobName = PartyType.getValueByKey(job);
				partyMember.setJob(String.join("", jobName,":",String.valueOf(PartyType.getKeyByValue(jobName))));
				GameData.that.partyMemberService.updateByExampleSelective(partyMember, example);
				//查找到帮派信息
				Party findByPartyName = GameData.that.partyService.findByPartyName(chara.getPartyName());
				//更新帮派职位信息
				boolean isFind = false;
				String leader = findByPartyName.getLeader();
				JSONArray parseArray = JSONObject.parseArray(leader);
				for (int i = 0; i < parseArray.size(); i++) {
					JSONObject json = parseArray.getJSONObject(i);
					//找到他原来的职位
					if(json.getString("gid").equals(toChara.uuid)) {
						if(job == 100) {
							//移除帮派领导职位
							parseArray.remove(i);
						}else {
							//更新职位
							json.put("job", value);
						}
						isFind = true;
						break;
					}
				}
				//如果没有找到就是新的任命
				if(!isFind) {
					if(job != 100) {
						JSONObject jobParty = new JSONObject();
						jobParty.put("gid", toChara.uuid);
						jobParty.put("name", toChara.name);
						jobParty.put("job", value);
						parseArray.add(jobParty);
					}
				}
				findByPartyName.setLeader(JSONObject.toJSONString(parseArray));
				GameData.that.partyService.updateByPrimaryKeySelective(findByPartyName);
				//刷新这个成员的信息
				GamePartyUtil.queryPartyMember(gid);
			}
		}
	}
	
	@Override
	public int cmd() {
		return 0x40BA;
	}

}
