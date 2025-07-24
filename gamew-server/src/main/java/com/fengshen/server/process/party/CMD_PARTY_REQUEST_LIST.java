package com.fengshen.server.process.party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Dialog;
import com.fengshen.server.data.vo.Vo_61591_0;
import com.fengshen.server.data.vo.party.Vo_PARTY_DIALOG;
import com.fengshen.server.data.vo.party.Vo_PARTY_DIALOG.Vo_PARTY_DIALOG_Item;
import com.fengshen.server.data.write.M61591_0;
import com.fengshen.server.data.write.party.MSG_DIALOG_PARTY;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 帮派请求列表
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_PARTY_REQUEST_LIST implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar game = GameObjectChar.getGameObjectChar();
		Chara chara = game.chara;
		
		log.info("请求帮派申请列表");
		//查询帮派申请信息
		Example example = new Example(Dialog.class);
		example.createCriteria().andEqualTo("askType", "party")
		.andEqualTo("peerName", chara.getPartyName());
		List<Dialog> requestJoins = GameData.that.dialogService.selectByExample(example);
		for(Dialog d:requestJoins) {
			String extJson = d.getExtJson();
			Vo_PARTY_DIALOG_Item parseObject = JSONObject.parseObject(extJson, Vo_PARTY_DIALOG_Item.class);
			Vo_PARTY_DIALOG vo = new Vo_PARTY_DIALOG();
			vo.ask_type = d.getAskType();
			vo.peer_name = parseObject.getName();
			vo.setItem(parseObject);
			GameObjectChar.send(new MSG_DIALOG_PARTY(), vo);
		}
		Vo_61591_0 vo_61591_0 = new Vo_61591_0();
		vo_61591_0.name = "";
		vo_61591_0.ask_type = "party";
		GameObjectChar.send(new M61591_0(), vo_61591_0);
	}

	@Override
	public int cmd() {
		return 0x10B0;
	}

}
