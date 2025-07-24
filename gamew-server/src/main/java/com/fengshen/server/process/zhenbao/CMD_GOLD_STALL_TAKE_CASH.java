package com.fengshen.server.process.zhenbao;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 金元宝交易提取金元宝
 * 
 *
 */
@Service
public class CMD_GOLD_STALL_TAKE_CASH implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.sellCash<=0) {
			GameCommonUtil.dialogOk("账户余额为0，提款失败");
			return;
		}
		
		//查询出配置
		ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_type");
		String costType = "金元宝";
		if(configInfo == null || "金元宝".equals(configInfo.getData())) {
			//默认金元宝
			GameUtil.addJinYuanBao(gameObjectChar, chara.sellCash);
		}else if("积分".equals(configInfo.getData())) {
			costType = "积分";
			GameUtil.addchargeScore(gameObjectChar, chara.sellCash);
		}
		
		GameUtil.sendMeTips("成功提款了#Y" + chara.sellCash + "#n" +costType);
		chara.sellCash = 0;
		GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
		GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
		//播放动画
		Vo_40964_0 vo_40964_21 = new Vo_40964_0();
		vo_40964_21.type = 5;
		vo_40964_21.name = "金元宝";
		vo_40964_21.param = String.valueOf(chara.sellCash);
		vo_40964_21.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_21);
	}

	@Override
	public int cmd() {
		return 0x810D;
	}

}
