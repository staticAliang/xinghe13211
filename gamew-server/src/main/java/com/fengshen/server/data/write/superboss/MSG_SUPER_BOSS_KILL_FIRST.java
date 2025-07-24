package com.fengshen.server.data.write.superboss;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.superboss.Vo_SUPER_BOSS_KILL_FIRST;
import com.fengshen.server.data.vo.superboss.Vo_SUPER_BOSS_KILL_FIRST.Monster;
import com.fengshen.server.data.vo.superboss.Vo_SUPER_BOSS_KILL_FIRST.Monster.Player;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SUPER_BOSS_KILL_FIRST extends BaseWrite<Vo_SUPER_BOSS_KILL_FIRST>{

	@Override
	protected void writeO(ByteBuf buff, Vo_SUPER_BOSS_KILL_FIRST object) {
		GameWriteTool.writeByte(buff, object.getFlag());
		GameWriteTool.writeByte(buff, object.getMonster().size());
		
		for(Monster monster:object.getMonster()) {
			GameWriteTool.writeString(buff, monster.getName());
			GameWriteTool.writeInt(buff, monster.getKillTime());
			GameWriteTool.writeByte(buff, monster.getPlayers().size());
			//玩家
			for(Player player:monster.getPlayers()) {
				GameWriteTool.writeString(buff, player.getGid());
				GameWriteTool.writeString(buff, player.getName());
				GameWriteTool.writeShort(buff, player.getLevel());
				GameWriteTool.writeInt(buff, player.getIcon());
			}
		}
	}

	@Override
	public int cmd() {
		return 0xA057;
	}

}
