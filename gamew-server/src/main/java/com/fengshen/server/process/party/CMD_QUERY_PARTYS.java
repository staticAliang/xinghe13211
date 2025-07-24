package com.fengshen.server.process.party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Party;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_LIST_EX;
import com.fengshen.server.data.write.party.MSG_PARTY_LIST_EX;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 查询帮派列表
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_QUERY_PARTYS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String type = GameReadTool.readString(buff);
	    String para = GameReadTool.readString(buff);
	    log.info("查询帮派列表,type={},para={}",type,para);
	    Example example = new Example(Party.class);
	    example.createCriteria().andEqualTo("state",0);
	    example.orderBy("partyLevel").desc().orderBy("population").desc().orderBy("createTime").desc();
		List<Party> partys = GameData.that.partyService.selectByExample(example);
		Vo_PARTY_LIST_EX party = new Vo_PARTY_LIST_EX();
		party.setType(type);
		party.setPartys(partys);
		GameObjectChar.send(new MSG_PARTY_LIST_EX(), party);
	}

	@Override
	public int cmd() {
		return 0x800E;
	}

}
