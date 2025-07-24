package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**
 * 分配仙魔点
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_ASSIGN_XMD implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameUtil.sendNotify(ClientButtonIdConst.NOTIFY_ASSIGN_XMD, "1");
		int xian = GameReadTool.readShort(buff);
		int mo = GameReadTool.readShort(buff);
		int goldCoin = 0;
		if (xian > 3000) {
			xian -= 65536;
			if(chara.upgrade_immortal-Math.abs(xian)<0) {
				GameUtil.sendMeTips("可供洗点仙道点不足");
				return;
			}else if(chara.upgrade_immortal-Math.abs(xian)<Math.max(chara.realLevel-119, 0)) {
				GameUtil.sendMeTips("洗点已达最低极限");
				return;
			}
			goldCoin+=Math.abs(xian)*328;
		}
		if (mo > 3000) {
			mo -= 65536;
			int minPoint = Math.max(chara.realLevel-119, 0);
			if(chara.upgrade_magic-Math.abs(mo)<0) {
				GameUtil.sendMeTips("可供洗点魔道点不足");
				return;
			}else if(chara.upgrade_magic-Math.abs(mo)<minPoint) {
				GameUtil.sendMeTips("洗点已达最低极限");
				return;
			}
			goldCoin+=Math.abs(mo)*328;
		}
		log.info("分配仙魔点， 仙={},魔={},洗点消耗元宝:{}",xian,mo,goldCoin);
		if(chara.goldCoin<goldCoin) {
			GameUtil.sendMeTips("元宝不足无法洗点");
			return;
		}
		//如果没有飞升
		if(chara.upgrade_type<2 || chara.upgrade_level<120) {
			GameUtil.sendMeTips("你还未完成仙魔飞升");
			return;
		}
		//在真身情况下
		if(chara.upgrade_state != 0) {
			GameUtil.sendMeTips("只有在真身下才允许加点");
			return;
		}
		//计算当前最大值_ass
		int maxPoint = (chara.realLevel-110);
		if(mo+xian>maxPoint) {
			GameUtil.sendMeTips("加点超限无法完成加点");
			return;
		}
		if(mo+xian>chara.upgrade_total) {
			GameUtil.sendMeTips("剩余点数不足无法完成加点");
			return;
		}
		//开始加点
		chara.upgrade_immortal+=xian;
		chara.upgrade_magic+=mo;
		chara.upgrade_total-=xian+mo;
		chara.goldCoin -= goldCoin;
		GameUtil.sendUpdate(chara);
	}

	@Override
	public int cmd() {
		return 0xD14A;
	}

}
