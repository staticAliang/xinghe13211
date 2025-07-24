package com.fengshen.server.data.write.party;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import tk.mybatis.mapper.entity.Example;

public class MSG_PARTY_INFO extends BaseWrite<Party> {

	@Override
	protected void writeO(ByteBuf buff, Party object) {
		GameWriteTool.writeString(buff, object.getPartyId());
		GameWriteTool.writeString(buff, object.getPartyName());
		
		GameWriteTool.writeString(buff, object.getPartyBaseInfo());
		GameWriteTool.writeString2(buff, object.getPartyAnnounce());
		GameWriteTool.writeShort(buff, object.getRights());
		GameWriteTool.writeInt(buff, object.getConstruct());
		GameWriteTool.writeInt(buff, object.getMoney());
		GameWriteTool.writeInt(buff, (int) (object.getCreateTime().getTime()/1000L));
		GameWriteTool.writeInt(buff, object.getSalary());
		//AutoAcceptLevel
		GameWriteTool.writeInt(buff, object.getAutoAcceptLevel());
		//autoMinTao  --2.60版本
		GameWriteTool.writeInt(buff, object.getMinTao());
		
		GameWriteTool.writeString(buff, object.getCreator());
		
		//添加默认技能
		Map<Integer, PartySkill> defaultPartySkill = new HashMap<>();
		//这是默认技能
		PartySkill ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("如意圈");
		ps1.setNo(254);
		defaultPartySkill.put(254,ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("神龙罩");
		ps1.setNo(260);
		defaultPartySkill.put(260,ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("乾坤罩");
		ps1.setNo(259);
		defaultPartySkill.put(259,ps1);
		
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("天生神力");
		ps1.setNo(31);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);;
		ps1.setName("拔苗助长");
		ps1.setNo(81);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("防微杜渐");
		ps1.setNo(131);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("十万火急");
		ps1.setNo(181);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("鞭长莫及");
		ps1.setNo(231);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("翻转乾坤");
		ps1.setNo(252);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("神圣之光");
		ps1.setNo(253);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("游说之舌");
		ps1.setNo(255);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("漫天血舞");
		ps1.setNo(257);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("舍命一击");
		ps1.setNo(258);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("死亡缠绵");
		ps1.setNo(261);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("五色光环");
		ps1.setNo(264);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("法力护盾");
		ps1.setNo(262);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("移花接木");
		ps1.setNo(263);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		ps1 = new PartySkill();
		ps1.setCurrentScore(0);
		ps1.setLevel(0);
		ps1.setLevelupScore(255);
		ps1.setName("舍身取义");
		ps1.setNo(265);
		defaultPartySkill.put(ps1.getNo(),ps1);
		
		//帮派技能
		List<PartySkill> partySkills = GameData.that.partySkill.getPartySkillByPartyId(object.getPartyId());
		for(PartySkill skill:partySkills) {
			if(defaultPartySkill.get(skill.getNo()) != null) {
				defaultPartySkill.remove(skill.getNo());
			}
		}
		//添加默认技能
		if(!defaultPartySkill.isEmpty()) {
			for(Map.Entry<Integer, PartySkill> s:defaultPartySkill.entrySet()) {
				partySkills.add(s.getValue());
			}
		}
		GameWriteTool.writeShort(buff, partySkills.size());
		for(PartySkill skill:partySkills) {
			GameWriteTool.writeString(buff, skill.getName());
			GameWriteTool.writeShort(buff, skill.getNo());
			GameWriteTool.writeShort(buff, skill.getLevel());
			GameWriteTool.writeInt(buff, skill.getCurrentScore());
			GameWriteTool.writeInt(buff, skill.getLevelupScore());
		}
		//该帮派在线人数
		Example example = new Example(PartyMember.class);
		example.createCriteria().andEqualTo("partyId", object.getPartyId());
		int online = 0;
		List<PartyMember> partyMembers = GameData.that.partyMemberService.selectByExample(example);
		for(PartyMember p:partyMembers) {
			if(GameObjectCharMng.getGameObjectChar(p.getCharaId()) != null) {
				online++;
			}
		}
		GameWriteTool.writeShort(buff, object.getPopulation());
		GameWriteTool.writeShort(buff, online);
		GameWriteTool.writeShort(buff, object.getPartyLevel());
		//partyMap
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeString(buff, object.getHeir());
		//lastAutoJoinTime
		GameWriteTool.writeInt(buff, 1);
		GameWriteTool.writeString(buff, object.getIconMd5());
		GameWriteTool.writeString(buff, "");
		//帮派职位
		if(!StringUtils.isEmpty(object.getLeader())) {
			JSONArray objs = JSONObject.parseArray(object.getLeader());
			GameWriteTool.writeShort(buff, objs.size());
			for(int i=0;i<objs.size();i++) {
				JSONObject jsonObject = objs.getJSONObject(i);
				GameWriteTool.writeString(buff, jsonObject.getString("job"));
				GameWriteTool.writeString(buff, jsonObject.getString("name"));
			}
		}
	}

	@Override
	public int cmd() {
		return 0xF0A1;
	}

}
