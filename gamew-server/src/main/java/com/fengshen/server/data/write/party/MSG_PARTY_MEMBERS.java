package com.fengshen.server.data.write.party;

import com.fengshen.db.domain.PartyMember;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_MEMBER;
import com.fengshen.server.data.vo.party.Vo_PARTY_MEMBER.PartyMembers;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 帮派成员
 * 
 *
 */
public class MSG_PARTY_MEMBERS extends BaseWrite<Vo_PARTY_MEMBER>{

	@Override
	protected void writeO(ByteBuf buff, Vo_PARTY_MEMBER object) {
		
		GameWriteTool.writeShort(buff, object.getPage());
		GameWriteTool.writeShort(buff, object.getTail());
		GameWriteTool.writeShort(buff, object.getPartyMembers().size());
		//成员列表
		for(PartyMembers p:object.getPartyMembers()) {
			PartyMember partyMember = p.getPartyMember();
			GameWriteTool.writeString(buff, partyMember.getCharaGid());
			GameWriteTool.writeString(buff, partyMember.getName());
			GameWriteTool.writeShort(buff, p.getOnline());
			GameWriteTool.writeShort(buff, p.getPortrait());
			GameWriteTool.writeString(buff, partyMember.getJob());
			GameWriteTool.writeShort(buff, p.getLevel());
			GameWriteTool.writeString(buff, p.getFamily());
			GameWriteTool.writeInt(buff, p.getContrib());
			GameWriteTool.writeInt(buff, partyMember.getActive());
			GameWriteTool.writeShort(buff, partyMember.getPolar());
			GameWriteTool.writeShort(buff, p.getGender());
			GameWriteTool.writeInt(buff, partyMember.getLastWeekActive());
			GameWriteTool.writeInt(buff, partyMember.getCurrWeekActive());
			GameWriteTool.writeInt(buff, (int) (partyMember.getCreateTime().getTime()/1000L));
			GameWriteTool.writeInt(buff, p.getTao());
			GameWriteTool.writeShort(buff, p.getWarTimes());
			if(partyMember.getLogoutTime() == null) {
				GameWriteTool.writeInt(buff, 0);
			}else {
				GameWriteTool.writeInt(buff,(int) (partyMember.getLogoutTime().getTime()/1000L));
			}
			GameWriteTool.writeInt(buff, p.getCurWarTimes());
		}
	}

	@Override
	public int cmd() {
		return 0xF0A3;
	}

}
