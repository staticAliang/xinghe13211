package com.fengshen.server.process.party;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_MEMBER;
import com.fengshen.server.data.vo.party.Vo_PARTY_MEMBER.PartyMembers;
import com.fengshen.server.data.write.party.MSG_PARTY_MEMBERS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 帮派成员
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_MEMBERS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int page = GameReadTool.readShort(buff);
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		log.info("帮派成员列表, page={},name={},gid={}", page, name, gid);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if (page > 0) {
			Example example = new Example(PartyMember.class);
			example.orderBy("active").desc().orderBy("currWeekActive").desc().orderBy("createTime").desc();
			example.createCriteria().andEqualTo("partyId", GameCore.partyMap.get(chara.getPartyName()).getPartyId());
			List<PartyMember> selectByExample = GameData.that.partyMemberService.selectByExample(example);
			Vo_PARTY_MEMBER member = new Vo_PARTY_MEMBER();
			member.setPage(page);
			List<PartyMembers> partyMembers = new ArrayList<>();
			int online = 0;
			for (PartyMember p : selectByExample) {
				PartyMembers ms = new PartyMembers();
				Chara toChara = null;
				GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
				if (gameObject == null) {
					// 数据库查询
					Characters ch = null; 
					if(!StringUtils.isNullOrEmpty(p.getCharaGid())) {
						ch = GameData.that.baseCharactersService.findOneByGidSelectProperties(p.getCharaGid(),"gid","sex","level","portrait","data");
					}
					if(ch == null) {
						continue;
					}
					toChara = JSONObject.parseObject(ch.getData(), Chara.class);
					toChara.setSex(ch.getSex());
					toChara.setLevel(ch.getLevel());
					toChara.setWaiguan(ch.getPortrait());
					online = 0;
				} else {
					toChara = gameObject.chara;
					online = 1;
				}
				ms.setOnline(online);
				ms.setContrib(toChara.contrib);
				ms.setCurWarTimes(0);
				ms.setWarTimes(0);
				ms.setFamily("");
				ms.setGender(toChara.sex);
				ms.setLevel(toChara.level);
				ms.setPortrait(toChara.waiguan);
				ms.setTao(toChara.tao + toChara.taoPoint);
				ms.setPartyMember(p);
				partyMembers.add(ms);
			}
			member.setPartyMembers(partyMembers);
			GameObjectChar.send(new MSG_PARTY_MEMBERS(), member);
		}
		// 点击查询某个成员
		if (!StringUtils.isNullOrEmpty(gid) && !StringUtils.isNullOrEmpty(name)) {
			GamePartyUtil.queryPartyMember(gid);
		}
	}
	

	@Override
	public int cmd() {
		return 0x20B8;
	}

}
