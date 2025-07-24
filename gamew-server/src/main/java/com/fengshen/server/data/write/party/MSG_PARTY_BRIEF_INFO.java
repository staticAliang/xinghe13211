package com.fengshen.server.data.write.party;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.game.GameData;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PARTY_BRIEF_INFO extends BaseWrite<Party>{

	@Override
	protected void writeO(ByteBuf buff, Party object) {
		GameWriteTool.writeString(buff, object.getPartyId());
		GameWriteTool.writeString(buff, object.getPartyName());
		GameWriteTool.writeString(buff, object.getCreator());
		GameWriteTool.writeShort(buff, object.getPartyLevel());
		GameWriteTool.writeShort(buff, object.getPopulation());
		GameWriteTool.writeInt(buff, object.getConstruct());
		GameWriteTool.writeString(buff, object.getIconMd5());
		GameWriteTool.writeInt(buff, object.getMoney());
		GameWriteTool.writeInt(buff, (int) (object.getCreateTime().getTime()/1000L));
		GameWriteTool.writeString2(buff, object.getPartyAnnounce());
		//帮派技能
		List<PartySkill> partySkills = GameData.that.partySkill.getPartySkillByPartyId(object.getPartyId());
		GameWriteTool.writeShort(buff, partySkills.size());
		for(PartySkill skill:partySkills) {
			GameWriteTool.writeString(buff, skill.getName());
			GameWriteTool.writeShort(buff, skill.getNo());
			GameWriteTool.writeShort(buff, skill.getLevel());
			GameWriteTool.writeInt(buff, skill.getCurrentScore());
			GameWriteTool.writeInt(buff, skill.getLevelupScore());
		}
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
		return 0xA013;
	}

}
