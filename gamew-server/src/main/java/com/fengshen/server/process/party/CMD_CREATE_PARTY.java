package com.fengshen.server.process.party;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.PartyType;
import com.fengshen.server.data.vo.party.Vo_PARTY_INFO.Leader;
import com.fengshen.server.data.write.party.MSG_CREATE_PARTY_SUCC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 创建帮派
 * 
 *
 */
@Service
@Slf4j
public class CMD_CREATE_PARTY implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx,ByteBuf buff) {
		
		String name = GameReadTool.readString(buff);
		String announce = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//查询帮派是否存在
		Example example = new Example(Party.class);
		example.createCriteria().andEqualTo("partyName", name);
		int isExist = GameData.that.partyService.selectCountByExample(example);
		if(isExist > 0) {
			GameCommonUtil.dialogOk("该帮派名字已存在。");
			return;
		}
		String regEx = "[ _`~!@#$%^&*()--+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);
		if (m.find()) {
			GameCommonUtil.dialogOk("只允许数字、中文、字母");
			return;
		}else if(name.length()<3) {
			GameCommonUtil.dialogOk("帮派名最少3个字符");
			return;
		}
		//开始创建帮派
		Party party = new Party();
		//前四位占位符
		party.setPartyId("FFFF"+GameCommonUtil.UUID().toUpperCase());
		party.setCreateTime(new Date());
		party.setCreator(chara.name);
		party.setPartyName(name);
		party.setPartyAnnounce("暂无宗旨");
		party.setIconMd5("");
		party.setState(0);
		Leader leader = new Leader();
		leader.setJob("帮主");
		leader.setName(chara.name);
		leader.setGid(chara.uuid);
		party.setLeader(JSONObject.toJSONString(Lists.newArrayList(leader)));
		GameData.that.partyService.insertSelective(party);
		//帮主也是帮派成员
		PartyMember partyMember = new PartyMember();
		partyMember.setCharaId(chara.id);
		partyMember.setName(chara.name);
		partyMember.setJob("帮主"+":"+PartyType.getKeyByValue("帮主"));
		partyMember.setPolar(chara.polar);
		partyMember.setPartyId(party.getPartyId());
		partyMember.setCharaGid(chara.uuid);
		partyMember.setCreateTime(new Date());
		GameData.that.partyMemberService.insertSelective(partyMember);
		//扣除用户金额
		chara.cash-=10000000;
		GameUtil.sendMeTips("你花费#Y"+ new DecimalFormat("#,###").format(10000000)+"#n创建了帮派");
		chara.setPartyName(name);
		chara.setPartyJob("帮主");
		chara.upPartyName = "";
		GameUtil.sendUpdate(chara);
		//设置称号
		String key = name+"帮帮主";
		GameUtil.chenghaoxiaoxi(chara, key, key);
		//帮派创建成功
		GameObjectChar.send(new MSG_CREATE_PARTY_SUCC(), name);
		GameCore.partyMap.put(name, party);
		log.info("创建帮派----name={},announce={}",name,announce);
	}

	@Override
	public int cmd() {
		return 32780;
	}
}