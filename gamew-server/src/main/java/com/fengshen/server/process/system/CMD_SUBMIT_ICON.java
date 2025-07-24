package com.fengshen.server.process.system;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.party.MSG_PARTY_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GamePartyUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 提交图标
 * 
 *
 */
@Service
@Slf4j
public class CMD_SUBMIT_ICON implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int operType = GameReadTool.readByte(buff);
		String md5Value = GameReadTool.readString(buff);
		ByteBuf fileData = GameReadTool.readLenBuffer2(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Example example = new Example(Party.class);
		example.createCriteria().andEqualTo("partyName", chara.getPartyName());
		Party party = GameData.that.partyService.selectOneByExample(example);
		if(party == null) {
			return;
		}
		if(operType == 1) {
			//自定义
			byte[] bi = new byte[fileData.readableBytes()];
			fileData.readBytes(bi);
			party.setIconMd5(GameCommonUtil.UUID());
			party.setReviewIconMd5(bi);
		}else if(operType == 2) {
			//系统内设
			party.setIconMd5(md5Value);
			party.setReviewIconMd5(null);
		}
		//扣除帮派建设度
		party.setConstruct(party.getConstruct()-10000);
		//刷新该帮派信息
		GameObjectChar.send(new MSG_PARTY_INFO(), party);
		//更新数据库信息
		GameData.that.partyService.updateByPrimaryKey(party);
		//重新缓存该帮派信息
		GameCore.partyMap.put(party.getPartyName(), party);
		//刷新图标
		GamePartyUtil.partyIcon(chara);
		GameCommonUtil.dialogOk("帮派图标设置成功。");
		log.info("提交图标");
	}

	@Override
	public int cmd() {
		return 0x5036;
	}

}
