package com.fengshen.server.process.pet;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65527_3;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用宠物经验丹
 * 
 *
 */
@Service
@Slf4j
public class CMD_APPLY_CHONGWU_JINGYANDAN implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff);
		int num1 = GameReadTool.readShort(buff);
		int num2 = GameReadTool.readShort(buff);
		log.info("使用宠物经验丹，no={},num1={},num2={}",no,num1,num2);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		int id = 0;
		int pot = 0;
		int resist_poison = 0;
		int i = 0;
		while (i < chara.pets.size()) {
			if (chara.pets.get(i).no == no) {
				id = chara.pets.get(i).id;
				if(chara.pets.get(i).petShuXing.get(0).skill>=194) {
					GameUtil.sendMeTips("等级已达极限");
					return;
				}
				GameUtil.addpetjingyan(chara.pets.get(i), num1 * 500000, chara);
				pot = chara.pets.get(i).petShuXing.get(0).pot;
				resist_poison = chara.pets.get(i).petShuXing.get(0).resist_poison;
				break;
			} else {
				++i;
			}
		}
		GameUtil.removemunber(chara, "宠物经验丹", num1);
		Vo_20481_0 vo_20481_2 = new Vo_20481_0();
		vo_20481_2.msg = "你使用了#R" + num1 + "#n颗宠物经验丹。";
		vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_2);
		List<Object> list = new LinkedList<>();
		list.add(id);
		list.add(pot);
		list.add(resist_poison);
		GameObjectChar.send(new M65527_3(), list);
	}

	@Override
	public int cmd() {
		return 41117;
	}
}