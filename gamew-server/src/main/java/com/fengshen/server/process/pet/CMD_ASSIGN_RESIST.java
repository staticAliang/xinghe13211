package com.fengshen.server.process.pet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.system.MSG_ASSIGN_RESIST;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 分配抗性点
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_ASSIGN_RESIST implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		int id = GameReadTool.readInt(buff);
		String attribValues = GameReadTool.readString(buff);
		log.info("分配抗性点:id={},attribValues={}",id,attribValues);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//对抗性点进行切割，顺序为->抗金、木、水、火、土，抗遗忘、中毒、冰冻、昏睡、混乱
		String[] split = attribValues.split(";");
		int[] pointArr = new int[split.length];
		//客户端传来的值
		int pointValue = 0;
		//消耗元宝
		int costGoldCoin = 0;
		//装到int数组中
		for (int i = 0; i < split.length; i++) {
			Integer valueOf = Integer.valueOf(split[i]);
			if(valueOf<0) {
				costGoldCoin+=Math.abs(valueOf)*50;
			}
			pointArr[i] = valueOf;
			pointValue+=valueOf;
		}
		if(costGoldCoin>0) {
			Map<String, Object> dataMap = new HashMap<>();
			//优先采用银元宝洗点
			if(chara.silverCoin>costGoldCoin) {
				chara.silverCoin-=costGoldCoin;
				dataMap.put("silver_coin", chara.goldCoin);
			}else {
				//银元宝不足用金元宝
				//金元宝是否充足
				if(costGoldCoin<chara.goldCoin) {
					GameUtil.sendMeTips("元宝不足,无法洗点");
					return;
				}
				chara.goldCoin-=costGoldCoin;
				dataMap.put("gold_coin", chara.goldCoin);
			}
			GameUtil.sendMeTips("你花费了#R"+costGoldCoin+"#n元宝进行洗点");
			//刷新信息
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, dataMap));
		}
		for(Petbeibao pet:chara.pets) {
			if(pet.id == id) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				//剩余点数是否足够
				if(petShuXing.resist_point2<pointValue) {
					GameUtil.sendMeTips("可用抗性点不足");
					return;
				}
				int level = petShuXing.skill;
				//当前最大抗性点
				int maxPoint = 0;
				if(level>60) {
					maxPoint = 30+(level-60);
				}else {
					maxPoint = level/2;
				}
				//当前最大抗性点
				int currMaxPoint = petShuXing.resist_metal+petShuXing.resist_wood+petShuXing.resist_water+
						petShuXing.resist_fire+petShuXing.resist_earth;
				currMaxPoint+=petShuXing.resist_poison2+petShuXing.resist_frozen+petShuXing.resist_sleep+
						petShuXing.resist_forgotten+petShuXing.resist_confusion;
				//如果超出最大值直接返回
				if(currMaxPoint+pointValue>maxPoint) {
					GameUtil.sendMeTips("抗性点超限");
					return;
				}
				//检测单个属性点是否超过最大值
				if(petShuXing.resist_metal+pointArr[0] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_wood+pointArr[1] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_water+pointArr[2] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_fire+pointArr[3] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_earth+pointArr[4] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}
				
				if(petShuXing.resist_forgotten+pointArr[5] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_poison2+pointArr[6] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_frozen+pointArr[7] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_sleep+pointArr[8] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}else if(petShuXing.resist_confusion+pointArr[9] > 30) {
					GameUtil.sendMeTips("抗性点已无法在增加");
					return;
				}
				//是否可以洗点
				if(petShuXing.resist_metal-Math.abs(pointArr[0])<0 && pointArr[0] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_wood-Math.abs(pointArr[1])<0 && pointArr[1] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_water-Math.abs(pointArr[2])<0 && pointArr[2] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_fire-Math.abs(pointArr[3])<0 && pointArr[3] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_earth-Math.abs(pointArr[4])<0 && pointArr[4] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}
				
				if(petShuXing.resist_forgotten-Math.abs(pointArr[5])<0 && pointArr[5] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_poison2-Math.abs(pointArr[6])<0 && pointArr[6] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_frozen-Math.abs(pointArr[7])<0 && pointArr[7] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_sleep-Math.abs(pointArr[8])<0 && pointArr[8] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}else if(petShuXing.resist_confusion-Math.abs(pointArr[9])<0 && pointArr[9] < 0) {
					GameUtil.sendMeTips("可洗点不足");
					return;
				}
				//开始加点
				petShuXing.resist_metal+=pointArr[0];
				petShuXing.resist_wood+=pointArr[1];
				petShuXing.resist_water+=pointArr[2];
				petShuXing.resist_fire+=pointArr[3];
				petShuXing.resist_earth+=pointArr[4];
				
				petShuXing.resist_forgotten+=pointArr[5];
				petShuXing.resist_poison2+=pointArr[6];
				petShuXing.resist_frozen+=pointArr[7];
				petShuXing.resist_sleep+=pointArr[8];
				petShuXing.resist_confusion+=pointArr[9];
				//逐个减去抗性点
				for (int i = 0; i < pointArr.length; i++) {
					petShuXing.resist_point2-=pointArr[i];
				}
				//加点成功
				gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
				gameObjectChar.sendOne(new MSG_ASSIGN_RESIST(), id);
				return;
			}
		}
	}

	@Override
	public int cmd() {
		return 0x108E;
	}

}
