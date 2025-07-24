package com.fengshen.server.data.write.shidao;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY.Vo_SHIDAO_HISTORY_TIMES;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY.Vo_SHIDAO_HISTORY_TIMES.Vo_SHIDAO_HISTORY_TIMES_MEMBERS;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 历届试道王者
 * 
 *
 */
public class MSG_SHIDAO_GLORY_HISTORY extends BaseWrite<List<Vo_SHIDAO_HISTORY>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_SHIDAO_HISTORY> object) {
		
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_SHIDAO_HISTORY shidao:object) {
			GameWriteTool.writeShort(buff, shidao.getLevelBuff());
			List<Vo_SHIDAO_HISTORY_TIMES> items = shidao.getItems();
			GameWriteTool.writeByte(buff, items.size());
			
			for(Vo_SHIDAO_HISTORY_TIMES item:items) {
				GameWriteTool.writeInt(buff, item.getTime());
				GameWriteTool.writeByte(buff, item.getIsMonth());
				List<Vo_SHIDAO_HISTORY_TIMES_MEMBERS> members = item.getMembers();
				//成员
				GameWriteTool.writeByte(buff, members.size());
				for(Vo_SHIDAO_HISTORY_TIMES_MEMBERS member:members) {
					GameWriteTool.writeByte(buff, member.getIsLeader());
					GameWriteTool.writeString(buff, member.getMemberName());
					GameWriteTool.writeShort(buff, member.getLevel());
					GameWriteTool.writeByte(buff, member.getFamily());
					GameWriteTool.writeString(buff, member.getGid());
					GameWriteTool.writeInt(buff, member.getIcon());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0xC017;
	}

}
