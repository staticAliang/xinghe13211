package com.fengshen.server.process.party;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.party.MSG_PARTY_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 技能研发
 * 
 *
 */
@Service
@Slf4j
public class CMD_DEVELOP_SKILL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		int point = GameReadTool.readInt(buff);
		int skillNo = GameReadTool.readShort(buff);
		
		if(skillNo != 254 && skillNo != 259 && skillNo != 260 && skillNo != 31
				&& skillNo != 81 && skillNo != 131 && skillNo != 181 && skillNo != 231) {
			GameUtil.sendMeTips("客官，技能正在研发中！");
			return;
		}
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		//点数必须大于0
		if (!StringUtils.isNullOrEmpty(chara.getPartyName())) {
			//如果不是帮主的话.
			if(!"帮主".equals(chara.getPartyJob())) {
				GameUtil.sendMeTips("只有帮主才能研发技能！");
				return;
			}
			Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
			//判断贵帮资金和建设度是否可用
			int canUseConstuByLevel = GamePartyUtil.getCanUseConstuByLevel(party.getPartyLevel(), party.getConstruct());
			int canUseMoneyByLevel = GamePartyUtil.getCanUseMoneyByLevel(party.getPartyLevel(), party.getMoney());
			//本次研发消耗金钱
			int costMoney = point*74;
			//本次研发消耗建设
			int costConstu = point;
			if(canUseConstuByLevel == 0 && canUseMoneyByLevel ==0) {
				GameUtil.sendMeTips("所需资金和建设不足！");
				return;
			}else if(canUseMoneyByLevel == 0 || canUseMoneyByLevel<costMoney) {
				GameUtil.sendMeTips("所需资金不足！");
				return;
			}else if(canUseConstuByLevel == 0 || canUseConstuByLevel<costConstu) {
				GameUtil.sendMeTips("所需建设不足！");
				return;
			}
			int baseNum = 255;
			Example example = new Example(PartySkill.class);
			example.createCriteria().andEqualTo("partyId", party.getPartyId()).
			andEqualTo("no", skillNo);
			PartySkill partySkill = GameData.that.partySkill.selectOneByExample(example);
			String name = "如意圈";
			if(skillNo == 259) {
				name = "乾坤罩";
			}else if(skillNo == 260){
				name = "神龙罩";
			}else if(skillNo == 31){
				name = "天生神力";
			}else if(skillNo == 81){
				name = "拔苗助长";
			}else if(skillNo == 131){
				name = "防微杜渐";
			}else if(skillNo == 181){
				name = "十万火急";
			}else if(skillNo == 231){
				name = "鞭长莫及";
			}
			if(point>0) {
				String msg = "";
				if(partySkill != null) {
					//判断技能等级是否超过限制
					if(partySkill.getLevel()+1>206) {
						GameUtil.sendMeTips("已达当前开放等级上限，无法研发！");
						return;
					}
					baseNum = GamePartyUtil.getNextSkillUpLevelSocreBase(partySkill);
					//如果进度当于当前升级进度.则直接升级
					if((point+partySkill.getCurrentScore())>=partySkill.getLevelupScore()) {
						//在原基础上+1;
						partySkill.setLevel(partySkill.getLevel()+1);
						partySkill.setLevelupScore(baseNum);
						partySkill.setCurrentScore(0);
						msg = "本次花费#R%d#n资金和#R%d#n建设,成功的将#Y"+partySkill.getName()+"#n提升到了#R"+partySkill.getLevel()+"#n级。";
					}else {
						//更新进度
						partySkill.setCurrentScore(partySkill.getCurrentScore()+point);
						msg = "本次花费#R%d#n资金和#R%d#n建设将#Y"+partySkill.getName()+"#n进度提升了#R"+partySkill.getCurrentScore()+"#n点。";
					}
					//更新
					GameData.that.partySkill.updateByPrimaryKeySelective(partySkill);
				}else {
					//给这个帮派创建新的技能
					partySkill = new PartySkill();
					partySkill.setNo(skillNo);
					partySkill.setName(name);
					partySkill.setPartyId(party.getPartyId());
					partySkill.setLevel(0);
					//如果进度当于当前升级进度.则直接升级
					if(point>=baseNum) {
						//在原基础上+1;
						partySkill.setLevel(partySkill.getLevel()+1);
						partySkill.setLevelupScore(baseNum);
						partySkill.setCurrentScore(0);
						msg = "本次花费#R%d#n资金和#R%d#n建设,成功的将#Y"+partySkill.getName()+"#n提升到了#R"+partySkill.getLevel()+"#n级。";
					}else {
						//更新进度
						partySkill.setCurrentScore(point);
						partySkill.setLevelupScore(baseNum);
						msg = "本次花费#R%d#n资金和#R%d#n建设将#Y"+name+"#n进度提升到了#R"+partySkill.getCurrentScore()+"#n点。";
					}
					GameData.that.partySkill.insertSelective(partySkill);
				}
				//弹出提示信息
				GameUtil.sendMeTips(String.format(msg, costMoney, costConstu));
				party.setMoney(party.getMoney()-costMoney);
				party.setConstruct(party.getConstruct()-costConstu);
				//更新帮派信息
				GameData.that.partyService.updateByPrimaryKeySelective(party);
				//刷新帮派信息
				GameObjectChar.send(new MSG_PARTY_INFO(), party);
				//重新缓存该帮派信息
				GameCore.partyMap.put(party.getPartyName(), party);
			}else {
				String msg = "";
				//升级技能
				int upLevel = Math.abs(point);
				int[] batchPartySkillInfo = null;
				//存在技能
				if(partySkill == null) {
					partySkill = new PartySkill();
					partySkill.setLevelupScore(255);
					partySkill.setPartyId(party.getPartyId());
					partySkill.setCurrentScore(0);
					partySkill.setNo(skillNo);
					partySkill.setLevel(upLevel);
					partySkill.setName(name);
					baseNum = GamePartyUtil.getNextSkillUpLevelSocreBase(partySkill);
					//设置下一级升级总分
					batchPartySkillInfo = GamePartyUtil.getBatchPartySkillInfo(0,partySkill.getCurrentScore(), partySkill.getLevelupScore(), upLevel);
					partySkill.setLevelupScore(baseNum);
					if(canUseMoneyByLevel<batchPartySkillInfo[0]) {
						GameUtil.sendMeTips("可用资金不足！");
						return;
					}else if(canUseConstuByLevel<batchPartySkillInfo[1]) {
						GameUtil.sendMeTips("可用建设不足！");
						return;
					}
					msg = "本次花费#R%d#n资金和#R%d#n建设,成功的将#Y"+partySkill.getName()+"#n提升到了#R"+partySkill.getLevel()+"#n级。";
					GameData.that.partySkill.insertSelective(partySkill);
				}else {
					if(partySkill.getLevel()+Math.abs(point)>206) {
						GameUtil.sendMeTips("已达当前开放等级上限，无法研发！");
						return;
					}
					batchPartySkillInfo = GamePartyUtil.getBatchPartySkillInfo(partySkill.getLevel(),partySkill.getCurrentScore(), partySkill.getLevelupScore(), upLevel);
					if(canUseMoneyByLevel<batchPartySkillInfo[0]) {
						GameUtil.sendMeTips("可用资金不足！");
						return;
					}else if(canUseConstuByLevel<batchPartySkillInfo[1]) {
						GameUtil.sendMeTips("可用建设不足！");
						return;
					}
					partySkill.setLevel(partySkill.getLevel()+upLevel);
					baseNum = GamePartyUtil.getNextSkillUpLevelSocreBase(partySkill);
					partySkill.setLevelupScore(baseNum);
					partySkill.setCurrentScore(0);
					//更新帮派技能
					GameData.that.partySkill.updateByPrimaryKeySelective(partySkill);
					msg = "本次花费#R%d#n资金和#R%d#n建设,成功的将#Y"+partySkill.getName()+"#n提升到了#R"+partySkill.getLevel()+"#n级。";
					
				}
				//弹出提示信息
				GameUtil.sendMeTips(String.format(msg, batchPartySkillInfo[0], batchPartySkillInfo[1]));
				//扣除信息
				party.setMoney(party.getMoney()-batchPartySkillInfo[0]);
				party.setConstruct(party.getConstruct()-batchPartySkillInfo[1]);
				//更新帮派信息
				GameData.that.partyService.updateByPrimaryKeySelective(party);
				//刷新帮派信息
				GameObjectChar.send(new MSG_PARTY_INFO(), party);
				//重新缓存该帮派信息
				GameCore.partyMap.put(party.getPartyName(), party);
				log.info("消耗情况:{}",Arrays.toString(batchPartySkillInfo));
			}
		}
		
		
		log.info("技能研发,point={},skillNo={}",point, skillNo);
	}

	@Override
	public int cmd() {
		return 0x20B0;
	}

}
