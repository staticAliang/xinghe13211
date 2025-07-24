package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_DATA;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_DATA.Member;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 请求固定队信息
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_FIXED_TEAM_REQUEST_DATA implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info(chara.name+"请求固定队伍信息"+chara.fixedTeamName);
		//查询这人固定队伍信息
		Example example = new Example(FixedTeam.class);
		example.createCriteria().andEqualTo("uid", chara.fixedTeamName==null?"":chara.fixedTeamName);
		FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
		Vo_FIXED_TEAM_DATA data = new Vo_FIXED_TEAM_DATA();
		if(fixedTeam != null) {
			data.setName(fixedTeam.getName());
			data.setLevel(fixedTeam.getLevel());
			data.setIntimacy(fixedTeam.getIntimacy());
			data.setMaxIntimacy((fixedTeam.getLevel()+1)*1000);
			JSONArray parseArray = JSONObject.parseArray(fixedTeam.getMembers());
			for (int i = 0; i < parseArray.size(); i++) {
				JSONObject jsonObject = parseArray.getJSONObject(i);
				String gid = jsonObject.getString("gid");
				Member m = new Member();
				GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
				m.setGid(gid);
				if(teamGameObjectChar != null) {
					//在线状态
					m.setName(teamGameObjectChar.chara.name);
					m.setLevel(teamGameObjectChar.chara.level);
					m.setIcon(teamGameObjectChar.chara.waiguan);
					m.setTao(teamGameObjectChar.chara.tao);
					m.setLastLogoutTime(0);
					m.setJoinTime(jsonObject.getIntValue("joinTime"));
				}else {
					//离线状态
					Characters characters = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "name","level","portrait","updateTime");
					m.setName(characters.getName());
					m.setLevel(characters.getLevel());
					m.setIcon(characters.getPortrait());
					m.setTao(0);
					m.setLastLogoutTime((int) (characters.getUpdateTime().getTime()/1000L));
					m.setJoinTime(jsonObject.getIntValue("joinTime"));
				}
				data.getMembers().add(m);
			}
		}
		GameObjectChar.send(new MSG_FIXED_TEAM_DATA(), data);
	}

	@Override
	public int cmd() {
		return 0xD20C;
	}

}
