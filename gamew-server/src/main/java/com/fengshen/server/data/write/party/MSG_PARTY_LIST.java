package com.fengshen.server.data.write.party;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_INFO;
import com.fengshen.server.data.vo.party.Vo_PARTY_INFO.Leader;
import com.fengshen.server.data.vo.party.Vo_PARTY_INFO.Skill;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 帮派列表
 * 
 *
 */
public class MSG_PARTY_LIST extends BaseWrite<List<Vo_PARTY_INFO>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_PARTY_INFO> o) {
		
		GameWriteTool.writeShort(buff, o.size());
		System.out.println(o.size());
		for(Vo_PARTY_INFO object:o) {
			GameWriteTool.writeString(buff, object.getPartyId());
			GameWriteTool.writeString(buff, object.getPartyName());
			GameWriteTool.writeString(buff, object.getPartyBaseInfo());
			GameWriteTool.writeString2(buff, object.getPartyAnnounce());
			GameWriteTool.writeShort(buff, object.getRights());
			GameWriteTool.writeInt(buff, object.getConstruct());
			GameWriteTool.writeInt(buff, object.getMoney());
			GameWriteTool.writeInt(buff, object.getCreateTime());
			GameWriteTool.writeInt(buff, object.getSalary());
			GameWriteTool.writeInt(buff, object.getAutoAcceptLevel());
			
			GameWriteTool.writeString(buff, object.getCreator());
			
			//技能信息
			GameWriteTool.writeShort(buff, object.getSkills().size());
			for(Skill sk:object.getSkills()) {
				GameWriteTool.writeString(buff, sk.getName());
				GameWriteTool.writeShort(buff, sk.getNo());
				GameWriteTool.writeShort(buff, sk.getLevel());
				GameWriteTool.writeInt(buff, sk.getCurrentSocre());
				GameWriteTool.writeInt(buff, sk.getLevelupScore());
			}
			
			GameWriteTool.writeShort(buff, object.getPopulation());
			GameWriteTool.writeShort(buff, object.getOnLineCount());
			GameWriteTool.writeShort(buff, object.getPartyLevel());
			GameWriteTool.writeShort(buff, object.getPartyMap());
			GameWriteTool.writeString(buff, object.getHeir());
			GameWriteTool.writeInt(buff, object.getLastAutoJoinTime());
			GameWriteTool.writeString(buff, object.getIcon_md5());
			GameWriteTool.writeString(buff, object.getReview_icon_md5());
			//帮派信息
			GameWriteTool.writeShort(buff, object.getLeaders().size());
			for(Leader l:object.getLeaders()) {
				GameWriteTool.writeString(buff, l.getJob());
				GameWriteTool.writeString(buff, l.getName());
			}
		}
		
	}

	@Override
	public int cmd() {
		return 0xA011;
	}

}
