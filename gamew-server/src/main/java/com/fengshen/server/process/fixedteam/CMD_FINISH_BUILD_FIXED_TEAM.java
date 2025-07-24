package com.fengshen.server.process.fixedteam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fengshen.db.domain.Characters;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA.Member;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_FINISH_DATA;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_FINISH_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 完成缔结固定队
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_FINISH_BUILD_FIXED_TEAM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("完成缔结团队");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
			GameUtil.sendMeTips("你已有固定队");
			return;
		}
		if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)){
			//获取队长
			GameObjectChar leader = GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(0).id);
			Chara leaderChara = leader.chara;
			Object confirmData = leader.confirmData;
			if(confirmData != null && confirmData instanceof Vo_FIXED_TEAM_CHECK_DATA) {
				int successNum = 0;
				//转换成固定队伍信息
				Vo_FIXED_TEAM_CHECK_DATA data = (Vo_FIXED_TEAM_CHECK_DATA) confirmData;
				//完成缔结信息
				Vo_FIXED_TEAM_FINISH_DATA fdata = new Vo_FIXED_TEAM_FINISH_DATA();
				fdata.setTeamName(data.getTeanName());
				//成员信息
				List<Map<String,Object>> infos = new ArrayList<>();
				for(Member m:data.getMembers()) {
					Map<String,Object> in = new HashMap<>();
					in.put("gid", m.getGid());
					in.put("joinTime",System.currentTimeMillis()/1000L);
					infos.add(in);
					if(m.getGid().equals(chara.uuid)) {
						//设置为确定
						m.setHasConfirm(1);
					}
					if(m.getHasConfirm() == 1) {
						successNum++;
						fdata.getMembers().add(m);
					}
				}
				if(successNum == gameObjectChar.gameTeam.duiwu.size()) {
					//完成缔结界面
					GameObjectChar.sendduiwu(new MSG_FIXED_TEAM_FINISH_DATA(), fdata, leaderChara.id);
					if(!StringUtils.isNullOrEmpty(leaderChara.fixedTeamName)) {
						//查询是否有固定队
						Example example = new Example(FixedTeam.class);
						example.createCriteria().andEqualTo("uid", leaderChara.fixedTeamName);
						FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
						//如果满人了
						JSONArray parseArray = JSONObject.parseArray(fixedTeam.getMembers());
						if(parseArray.size()>=5) {
							GameUtil.sendMeTips("固定队已满无法缔结");
							return;
						}else if(parseArray.size()+infos.size()>5) {
							GameUtil.sendMeTips("固定队超出人数限制，无法缔结");
							return;
						}
						for(Map<String,Object> m:infos) {
							//把新的成员添加进去,队长排除在外
							if(!m.get("gid").equals(leaderChara.uuid)) {
								parseArray.add(m);
							}
						}
						//给所有成员设置固定队伍
						for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
							//同时保存到数据库
							teamChara.fixedTeamName = fixedTeam.getUid();
							Characters characters = GameData.that.baseCharactersService.findOneByGid2(teamChara.uuid);
							characters.setFixedTeamName(teamChara.fixedTeamName);
							GameData.that.baseCharactersService.updateById(characters);
							//设置固定队自动同意
							teamChara.getSettings().put("ft_inv_team",1);
							teamChara.getSettings().put("ft_lead_team",1);
							teamChara.getSettings().put("ft_dun_yb",1);
							teamChara.getSettings().put("ft_change_look",1);
							teamChara.getSettings().put("ft_req_team",1);
							teamChara.getSettings().put("ft_recruit",1);
							teamChara.getSettings().put("ft_use_item",1);
							teamChara.getSettings().put("ft_change_team_seq",1);
						}
						//更新固定队
						fixedTeam.setMembers(parseArray.toJSONString());
						GameData.that.fixedTeamService.updateByPrimaryKeySelective(fixedTeam);
					}else {
						//固定信息记录到数据库
						FixedTeam fixedTeam = new FixedTeam();
						//队长
						fixedTeam.setLeaderUid(gameObjectChar.gameTeam.duiwu.get(0).uuid);
						fixedTeam.setAddTime(new Date());
						fixedTeam.setLevel(8);
						fixedTeam.setName(data.getTeanName());
						fixedTeam.setUid(GameCommonUtil.UUID());
						fixedTeam.setMembers(JSONObject.toJSONString(infos));
						GameData.that.fixedTeamService.insertSelective(fixedTeam);

						//给所有成员设置固定队伍
						for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
							teamChara.fixedTeamName = fixedTeam.getUid();
							Characters characters = GameData.that.baseCharactersService.findOneByGid2(teamChara.uuid);
							characters.setFixedTeamName(teamChara.fixedTeamName);
							GameData.that.baseCharactersService.updateById(characters);
							//设置固定队自动同意
							teamChara.getSettings().put("ft_inv_team",1);
							teamChara.getSettings().put("ft_lead_team",1);
							teamChara.getSettings().put("ft_dun_yb",1);
							teamChara.getSettings().put("ft_change_look",1);
							teamChara.getSettings().put("ft_req_team",1);
							teamChara.getSettings().put("ft_recruit",1);
							teamChara.getSettings().put("ft_use_item",1);
							teamChara.getSettings().put("ft_change_team_seq",1);
						}
					}
					//消息提示
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "固定队伍缔结成功";
					vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
					GameObjectChar.sendduiwu(new M20481_0(), vo_20481_0, leaderChara.id);
					//全队员同意缔结固定队伍
					leader.confirmData = null;
				}else {
					//重新设置数据
					leader.confirmData = data;
					//再次发送这个信息
					GameObjectChar.sendduiwu(new MSG_FIXED_TEAM_CHECK_DATA(), data, leaderChara.id);
				}
			}
		}
				
	}

	@Override
	public int cmd() {
		return 0xD202;
	}

}
