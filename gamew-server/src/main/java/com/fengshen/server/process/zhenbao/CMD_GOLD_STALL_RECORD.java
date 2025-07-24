package com.fengshen.server.process.zhenbao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StallRecord;
import com.fengshen.server.data.constant.StallRecordType;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_RECORD;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_RECORD_BASE;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_RECORD;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a珍宝交易记录
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_RECORD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("珍宝交易记录");
		
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar != null) {
			Chara chara = gameObjectChar.chara;
			List<StallRecord> stallRecords = GameData.that.stallRecordService.getStallRecordByStallRecordType(chara.id, StallRecordType.getValue("珍宝"));
			List<Vo_GOLD_STALL_RECORD_BASE> buy = new ArrayList<>();
			List<Vo_GOLD_STALL_RECORD_BASE> sell = new ArrayList<>();
			for(StallRecord sr:stallRecords) {
				Vo_GOLD_STALL_RECORD_BASE record = new Vo_GOLD_STALL_RECORD_BASE();
				record.setName(sr.getGoodsName());
				record.setLevel(sr.getLevel());
				record.setTime((int) (sr.getAddTime().getTime()/1000L));
				record.setEndTime((int) (sr.getEndTime().getTime()/1000L));
				record.setPrice(sr.getPrice());
				record.setStatus(sr.getStatus());
				record.setReqLevel(sr.getReqLevel());
				record.setItemPolar(sr.getItemPolar());
				record.setStallItemType(sr.getItemType());
				record.setRecordId(sr.getGoodsUuid());
				record.setBuyType(sr.getBuyType());
				//出售记录
				if(sr.getType() == 0) {
					sell.add(record);
				}else if(sr.getType() == 1) {
					//购买记录
					buy.add(record);
				}
			}
			Vo_GOLD_STALL_RECORD record = new Vo_GOLD_STALL_RECORD();
			record.setBuyList(buy);
			record.setSellCout(sell);
			gameObjectChar.sendOne(new MSG_GOLD_STALL_RECORD(), record);
			
		}
	}

	@Override
	public int cmd() {
		return 0x810B;
	}

}
