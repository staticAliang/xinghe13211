package com.fengshen.server.process.dari;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.constant.RedisKeyConstant;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 请求boos排名
 */
@Service
@Slf4j
public class CMD_WORLD_BOSS_RANK implements GameHandler {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
		vo_9129_2.notify = ClientButtonIdConst.NOTIFY_OPEN_DLG;
		vo_9129_2.para = "WorldBossRankDlg";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2, chara.id);
		String rankLeft = redisUtils.get(RedisKeyConstant.RANK_LEFT);
		List<rank_role> rankList = Lists.newArrayList();
		Map<String, JSONObject> mapRank = (Map) JSON.parseObject(rankLeft, Map.class);
		if (mapRank == null) {
			mapRank = Maps.newHashMap();
		}
		Iterator var65 = ((Map)mapRank).values().iterator();

		while(var65.hasNext()) {
			com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject)var65.next();
			rank_role rankRole = (rank_role)JSON.parseObject(jsonObject.toJSONString(), rank_role.class);
			rankList.add(rankRole);
		}
		rank_role jiaren = new rank_role();
		if(rankList.size()>0){
			for(rank_role list:rankList){
				if(list.getName().equals(chara.getName())){
					jiaren.setName(list.getName());
					jiaren.setDamage(list.getDamage());
					jiaren.setRank(list.getRank());
				}
			}
		}
		rankList.sort(Comparator.comparingLong(rank_role::getDamage).reversed());
		rankList.add(jiaren);
		gameObjectChar.sendOne(new MSG_WORLD_BOSS_RANK(), rankList);
	}

	@Override
	public int cmd() {
		return 33008;
	}

}
