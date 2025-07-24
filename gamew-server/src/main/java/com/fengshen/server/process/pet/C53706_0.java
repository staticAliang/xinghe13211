package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65527_5;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// 使用超级神兽丹后给宠物增加亲密度
@Service
public class C53706_0 implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff);
		int num = GameReadTool.readShort(buff);
		int flag = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if (flag == 1) {
			for (int i = 0; i < chara.pets.size(); ++i) {
				if (chara.pets.get(i).no == no) {
					if (GameCommonUtil.getGoodsNum(chara, "超级神兽丹") < num) {
						GameUtil.sendMeTips("超级神兽丹数量不足,无法加亲密");
						return;
					}
					PetShuXing petShuXing = chara.pets.get(i).petShuXing.get(0);
					if(petShuXing.penetrate == 1) {
						GameUtil.sendMeTips("野生宠物无法吃亲密丹");
						return;
					}
					int maxShape = 5000000;
					if (((petShuXing.penetrate == 1 || petShuXing.penetrate == 2) && petShuXing.shape >= maxShape)
							|| ((petShuXing.penetrate == 3 || petShuXing.penetrate == 4)
									&& petShuXing.shape >= maxShape)) {
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "达到亲密度上限，额外属性加成将不再增加";
						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);
					} else {
						petShuXing.shape += 2000 * num;
						if (petShuXing.shape > maxShape) {
							petShuXing.shape = maxShape;
						}
						// 根据不同的亲密度，给宠物增加不同的伤害
						GameCommonUtil.addQimMiShangHai(chara.pets.get(i));
					}
					List<Petbeibao> list = new ArrayList<>();
					list.add(chara.pets.get(i));
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					List<Object> list2 = new LinkedList<>();
					list2.add(chara.pets.get(i).id);
					list2.add(chara.pets.get(i).petShuXing.get(0).shape);
					GameObjectChar.send(new M65527_5(), list2);
					// 发送杂项
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "增加#R" + 2000 * num + "点#n亲密度。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					//删除神兽丹
					GameUtil.removemunber(chara, "超级神兽丹", num);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 53706;
	}
}