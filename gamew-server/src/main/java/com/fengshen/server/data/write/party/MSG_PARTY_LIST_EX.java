package com.fengshen.server.data.write.party;

import org.springframework.stereotype.Component;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_LIST_EX;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Component
public class MSG_PARTY_LIST_EX extends BaseWrite<Vo_PARTY_LIST_EX> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PARTY_LIST_EX vo) {
		GameWriteTool.writeString(buff, vo.getType());
		GameWriteTool.writeShort(buff, vo.getPartys().size());
		for(Party item:vo.getPartys()) {
			GameWriteTool.writeString(buff, item.getPartyId());
			GameWriteTool.writeString(buff, item.getPartyName());
			GameWriteTool.writeShort(buff, item.getPartyLevel());
			GameWriteTool.writeShort(buff, item.getPopulation());
			GameWriteTool.writeInt(buff, item.getConstruct());
		}
		
	}

	@Override
	public int cmd() {
		return 0xA011;
	}

}
