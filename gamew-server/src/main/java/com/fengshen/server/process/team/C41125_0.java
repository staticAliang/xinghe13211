package com.fengshen.server.process.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.write.M20568_0;
import com.fengshen.server.data.write.M53741_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Duiyuan;
import com.fengshen.server.domain.LieBiao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 请求刷新队伍信息
 * 
 * 
 *
 */
@Service
public class C41125_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String ask_type = GameReadTool.readString(buff);
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		if (ask_type.equals("invite_join")) {
			List<LieBiao> lieBiaoList2 = new ArrayList<LieBiao>();
			HashMap<Integer, Chara> invitationCharas = session.invitationCharas;
			LieBiao lieBiao2 = new LieBiao();
			lieBiao2.ask_type = "request_join";
			lieBiao2.peer_name = "";
			if (invitationCharas != null && !invitationCharas.isEmpty()) {
				for (Chara chara : invitationCharas.values()) {
					Duiyuan duiyuan = new Duiyuan();
					duiyuan.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
					duiyuan.iid_str = chara.uuid;
					duiyuan.str = chara.name;
					duiyuan.skill = chara.level;
					duiyuan.master = chara.sex;
					duiyuan.metal = chara.polar;
					duiyuan.req_str = "";
					duiyuan.passive_mode = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
					duiyuan.party_contrib = chara.getPartyName() == null?"":chara.getPartyName();
					duiyuan.mapteamMembersCount = invitationCharas.size();
					duiyuan.mapcomeback_flag = 0;
					lieBiao2.duiyuanList.add(duiyuan);
				}
				lieBiaoList2.add(lieBiao2);
				GameObjectChar.send(new M53741_0(), lieBiaoList2);
			}
		}
		if (ask_type.equals("request_join")) {
			// add tzhang 添加空指针判断
			if (GameObjectChar.getGameObjectChar().gameTeam == null
					|| GameObjectChar.getGameObjectChar().gameTeam.zhanliduiyuan == null)
				return;
			// add:e
			GameUtil.a4121(GameObjectChar.getGameObjectChar().gameTeam.zhanliduiyuan);
			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);
			List<LieBiao> lieBiaoList2 = new ArrayList<LieBiao>();
			if (GameObjectChar.getGameObjectChar().gameTeam != null
					&& GameObjectChar.getGameObjectChar().gameTeam.liebiao != null
					&& !GameObjectChar.getGameObjectChar().gameTeam.liebiao.isEmpty()
					&& GameObjectChar.getGameObjectChar().gameTeam.liebiao.size() > 0) {
				GameTeam gameTeam = GameObjectChar.getGameObjectChar().gameTeam;
				for (int j = 0; j < gameTeam.liebiao.size(); ++j) {
					List<Chara> list = GameObjectChar.getGameObjectChar().gameTeam.liebiao.get(j);
					if(list != null && !list.isEmpty()) {
						LieBiao lieBiao2 = new LieBiao();
						lieBiao2.ask_type = "request_join";
						lieBiao2.peer_name = list.get(0).name;
						for (int k = 0; k < list.size(); ++k) {
							Duiyuan duiyuan2 = new Duiyuan();
							Chara chara2 = list.get(k);
							duiyuan2.org_icon = GameUtil.getWaiguan(chara2.polar, chara2.sex, chara2);
							duiyuan2.iid_str = chara2.uuid;
							duiyuan2.str = chara2.name;
							duiyuan2.skill = chara2.level;
							duiyuan2.master = chara2.sex;
							duiyuan2.metal = chara2.polar;
							duiyuan2.req_str = "";
							duiyuan2.passive_mode = GameUtil.getWaiguan(chara2.polar, chara2.sex, chara2);
							duiyuan2.party_contrib = chara2.getPartyName() == null?"":chara2.getPartyName();
							duiyuan2.mapteamMembersCount = 1;
							duiyuan2.mapcomeback_flag = 0;
							if (chara2.upgrade_state != 0) {
								duiyuan2.skill = chara2.upgrade_level;
							}
							lieBiao2.duiyuanList.add(duiyuan2);
						}
						lieBiaoList2.add(lieBiao2);
					}
				}
				if(!lieBiaoList2.isEmpty()) {
					GameObjectChar.send(new M53741_0(), lieBiaoList2);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 41125;
	}
}
