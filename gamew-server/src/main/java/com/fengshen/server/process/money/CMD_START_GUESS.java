package com.fengshen.server.process.money;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_40995_0;
import com.fengshen.server.data.write.M40995_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.domain.Chara;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 五行开始
 * 
 *
 */
@Service
@Slf4j
public class CMD_START_GUESS implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int amount = GameReadTool.readInt(buff);
		int choice = GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		amount  = Math.abs(amount);
		//五行竞猜配置信息
		ConfigInfo wuxingConfig = GameData.that.configInfoService.getOneByKeyName("wuxing_config");
		com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(wuxingConfig.getData());
		String type = parseObject.getString("type");
		if(parseObject == null || parseObject.getIntValue("status") == 0) {
			Vo_40995_0 vo_40995_1 = new Vo_40995_0();
			vo_40995_1.flag = 0;
			vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
			vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
			GameObjectChar.send(new M40995_0(), vo_40995_1);
			GameUtil.sendMeTips("GM关闭了五行竞猜");
			return;
		}
		//如果五行竞猜的次数不足
		if(chara.wuxingCount+1>parseObject.getIntValue("maxCount")) {
			Vo_40995_0 vo_40995_1 = new Vo_40995_0();
			vo_40995_1.flag = 0;
			vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
			vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
			GameObjectChar.send(new M40995_0(), vo_40995_1);
			GameUtil.sendMeTips("你今天的机会已经用完了，还是明天再来吧！");
			return;
		}
		//单次最大数额
		if(amount>parseObject.getIntValue("maxMoney")) {
			Vo_40995_0 vo_40995_1 = new Vo_40995_0();
			vo_40995_1.flag = 0;
			vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
			vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
			GameObjectChar.send(new M40995_0(), vo_40995_1);
			GameUtil.sendMeTips("单次最大金额为#R"+parseObject.getIntValue("maxMoney")+"#n"+type);
			return;
		}
		if("积分".equals(type)) {
			if(chara.chargeScore<amount) {
				Vo_40995_0 vo_40995_1 = new Vo_40995_0();
				vo_40995_1.flag = 0;
				vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
				vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
				GameObjectChar.send(new M40995_0(), vo_40995_1);
				GameUtil.sendMeTips("积分不足");
				return;
			}
			chara.chargeScore-=amount;
			GameUtilRenWu.refshPointTask(chara);

		}else if("金钱".equals(type)){
			if(chara.cash<amount) {
				Vo_40995_0 vo_40995_1 = new Vo_40995_0();
				vo_40995_1.flag = 0;
				vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
				vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
				GameObjectChar.send(new M40995_0(), vo_40995_1);
				GameUtil.sendMeTips("金钱不足");
				return;
			}
			chara.cash -= amount;
		}else if("金元宝".equals(type)) {
			if(chara.goldCoin<amount) {
				GameUtil.sendMeTips("金元宝不足");
				Vo_40995_0 vo_40995_1 = new Vo_40995_0();
				vo_40995_1.flag = 0;
				vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
				vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
				GameObjectChar.send(new M40995_0(), vo_40995_1);
				return;
			}
			chara.goldCoin-=amount;
		}
		ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
		int n12 = parseObject.getIntValue("n60");
		int n5 = parseObject.getIntValue("n5");
		int[] arr = prize(12,5);
		int orginPrize = Integer.valueOf(arr[0]+""+arr[1]);
		if (choice == orginPrize) {
			log.info("---------------------中奖60倍---------------------");
		} else if (choice / 10 == orginPrize / 10) {
			log.info("---------------------中奖5倍---------------------");
		} else if (choice % 10 == orginPrize % 10) {
			log.info("---------------------中奖12倍---------------------");
		}
		log.info("客户端码={},中奖码={}",choice,arr);
		//生肖中奖概率,随机数大于概率标识生肖未中奖则修改成随机的生肖
		if(ThreadLocalRandom.current().nextInt(100) > n12 && n12>0) {
			List<Integer> list = Stream.iterate(1, item -> item+1).limit(12).collect(Collectors.toList());
			int shengxiao = choice / 10;
			list.remove(Integer.valueOf(shengxiao));
			log.info("生肖list:{}",list);
			//从这里随机取一个出来
			arr[0] = list.get(ThreadLocalRandom.current().nextInt(list.size()));
		}
		//五行中奖概率
		if(ThreadLocalRandom.current().nextInt(100) > n5  && n5 > 0) {
			List<Integer> list = Stream.iterate(1, item -> item+1).limit(5).collect(Collectors.toList());
			int wuxing = choice%10;
			list.remove(Integer.valueOf(wuxing));
			log.info("五行list:{}",list);
			//从这里随机取一个出来
			arr[1] = list.get(ThreadLocalRandom.current().nextInt(list.size()));
		}
		log.info("五行系统修改后={}",arr);
		int prize = Integer.valueOf(arr[0]+""+arr[1]);
		
		
		int money = 0;
		if (choice == prize) {
			money = amount * 60;
			//爆出谣言
			GameUtil.sendYaoYan("#45m这是什么好运气，#Y"+chara.name+"#n竟然在五行竞猜中爆出了60倍的超级大奖，获得了#R"+money+"#n"+type+"奖励#n真是可喜可贺啊！");
		} else if (choice / 10 == prize / 10) {
			money = amount * 12;
		} else if (choice % 10 == prize % 10) {
			money = amount * 5;
		}
		chara.wuxingBalance += money;
		if (chara.wuxingBalance < 0) {
			chara.wuxingBalance = 200000000;
		}
		chara.wuxingCount++;
		Vo_40995_0 vo_40995_1 = new Vo_40995_0();
		vo_40995_1.flag = 1;
		vo_40995_1.money = money;
		vo_40995_1.surlus = String.valueOf(chara.wuxingBalance);
		vo_40995_1.overflow = "0";
		vo_40995_1.amount = amount;
		vo_40995_1.choice = choice;
		vo_40995_1.prize = prize;
		vo_40995_1.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
		GameObjectChar.send(new M40995_0(), vo_40995_1);
		
		GameUtil.sendMeTips("你花费了#R" + amount +"#n"+type+"#n进行五行竞猜。");
		log.info("五行开始");
	}

	@Override
	public int cmd() {
		return 40993;
	}
	

	public static int[] prize(int shengxiao, int wuxing) {
		Random random = new Random();
		//生肖
		int i2 = random.nextInt(shengxiao) + 1;
		//五行
		int i = random.nextInt(wuxing) + 1;
		return new int[] {i2,i};
	}
}