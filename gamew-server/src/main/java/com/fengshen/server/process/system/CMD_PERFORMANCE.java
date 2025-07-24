package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端性能数据上报
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_PERFORMANCE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//帧率
		int fr = GameReadTool.readByte(buff);
		//每帧消耗时间
		int spf = GameReadTool.readInt(buff);
		//所在地图
		int mapId = GameReadTool.readInt(buff);
		//当前位置
		String pos = GameReadTool.readString(buff);
		//当前动作
		int act = GameReadTool.readByte(buff);
		//当前状态(1:战斗,2:观战,0:其他)
		int state = GameReadTool.readByte(buff);
		//可用内存
		int am = GameReadTool.readInt(buff);
		//总内存
		int tm = GameReadTool.readInt(buff);
		//是否在后台
		int bg = GameReadTool.readByte(buff);
		log.info("性能数据上报, 帧率={},每帧={},地图={},位置={},动作={},状态:{},可用内存={},总内存={},后台={}",fr,spf,mapId,pos,act,state==1?"战斗中":state,am,tm,bg);
		if(gameObjectChar.flag.equals("reconnect")) {
			//如果有队伍
//			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
//				// 我的队伍
//				GameUtil.a4119(gameObjectChar.gameTeam.duiwu);
//				// 更新右侧组队信息
//				GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
//			}else {
//				//没有队伍了
//				gameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());
//				gameObjectChar.sendOne(new M4119_0(), new ArrayList<Vo_4121_0>());
//			}
		}
	}

	@Override
	public int cmd() {
		return 0x8098;
	}

}
