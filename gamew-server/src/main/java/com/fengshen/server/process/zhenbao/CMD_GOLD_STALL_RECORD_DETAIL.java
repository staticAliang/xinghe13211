package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StallRecord;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.StallRecordType;
import com.fengshen.server.data.vo.zhenbao.Vo_STALL_RECORD_DETAIL;
import com.fengshen.server.data.write.zhenbao.MSG_STALL_RECORD_DETAIL;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * a请求珍宝交易记录详细信息
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_RECORD_DETAIL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String record = GameReadTool.readString(buff);
		log.info("请求珍宝交易记录详细信息");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		Example example = new Example(StallRecord.class);
		example.createCriteria().andEqualTo("cid", chara.id).andEqualTo("goodsUuid", record).andEqualTo("stallRecordType", StallRecordType.getValue("珍宝"));
		//交易记录商品详情
		StallRecord recordInfo = GameData.that.stallRecordService.selectOneByExample(example);
		if(record == null) {
			GameUtil.sendMeTips("该商品不存在！");
			return;
		}
		Vo_STALL_RECORD_DETAIL recordVo = new Vo_STALL_RECORD_DETAIL();
		recordVo.setData(recordInfo.getData());
		recordVo.setGoodsType(recordInfo.getItemType());
		recordVo.setOwnerName(recordInfo.getOwnerName());
		recordVo.setRecordId(record);
		gameObjectChar.sendOne(new MSG_STALL_RECORD_DETAIL(), recordVo);
	}

	@Override
	public int cmd() {
		return 0x8128;
	}

}
