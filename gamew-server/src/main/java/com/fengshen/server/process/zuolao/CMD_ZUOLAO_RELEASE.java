package com.fengshen.server.process.zuolao;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 释放坐牢人员
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_ZUOLAO_RELEASE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		String gid = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		log.info("释放坐牢人员，gid={},name={}",gid,name);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//计算有多少个小时
		GameObjectChar releaseGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(releaseGameObjectChar == null) {
			GameUtil.sendMeTips("保释人员不在线！");
			return;
		}
		//坐牢时间，秒
		long crimeTime = releaseGameObjectChar.chara.crimeTime;
		long hour = crimeTime/60/60;
		//不满一小时默认100积分
		int releasePrice = GameConfig.forcePkConfig.getReleasePrice();
		if(hour>1) {
			releasePrice = (int) (hour*GameConfig.forcePkConfig.getReleasePrice());
		}
		gameObjectChar.confirmData = new Object[] {gid,releasePrice};
		GameUtil.confirm(chara, "需要保释#Y"+name+"#n，需要缴纳#R"+releasePrice+"#n积分保释金，是否确定缴纳？", "zuolao_release", 30);
	}

	@Override
	public int cmd() {
		return 0xB0AF;
	}

}
