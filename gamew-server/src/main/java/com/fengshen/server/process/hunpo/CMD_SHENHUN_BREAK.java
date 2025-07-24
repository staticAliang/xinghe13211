package com.fengshen.server.process.hunpo;

import java.util.Map;

import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求突破
 * 
 *
 */
@Service
@Slf4j
public class CMD_SHENHUN_BREAK implements GameHandler {
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		log.info("魂魄--请求突破");
		 Chara chara = GameObjectChar.getGameObjectChar().chara;
		 //判断积分是否有积分
		 int subJiFen = GameConfig.shenHunConfig.getData().get(String.valueOf(chara.shenHunDataSate)).getIntValue("jifen");
		 if(chara.chargeScore-subJiFen<0) {
		 	GameUtil.sendMeTips("积分不足无法突破");
		 	return;
		 }
		 //判断是否达到等级
		 if(chara.shenHunIsTop == 1) {
		 	GameUtil.sendMeTips("神魂已是最高阶段.");
		 	return;
		 }
		 if(chara.shenHunDataLaye>10) {
		 	chara.shenHunDataLaye = 0;
		 }
		 chara.shenHunDataLaye+=1;
		// //玩家加成
		Map<String, JSONObject> attri = GameConfig.shenHunConfig.getAttri();
		JSONObject jsonObject = attri.get(String.valueOf(chara.shenHunDataLaye));
		//获取这层的配置信息
		int value = jsonObject.getIntValue("value")*chara.shenHunDataSate;
		String name = jsonObject.getString("name");
		switch (name) {
			case "phy_power":
				chara.shenHunPhyPower+=value;
				break;
			case "def":
				chara.shenHunDef+=value;
				break;
			case "max_life":
				chara.shenHunmaxLife+=value;
				break;
			case "mag_power":
				chara.shenHunMagPower+=value;
				break;
			case "speed":
				chara.shenHunSpeed+=value;
				break;
		}
		 log.info("当前层数:{}",chara.shenHunDataLaye);
		 if(chara.shenHunDataSate == 10 && chara.shenHunDataLaye ==10) {
		 	//第十个阶段不做任何事情
		 	chara.shenHunIsTop = 1;
		 }else {
		 	//每次层数到达10级就让阶数+1...
		 	if(chara.shenHunDataLaye==10) {
		 		chara.shenHunDataSate+=1;
		 		chara.shenHunDataLaye = 0;
		 	}
		 }
		 chara.chargeScore-=subJiFen;
			GameUtilRenWu.refshPointTask(chara);

		 GameUtil.sendMeTips("恭喜你突破成功，消耗了#R"+subJiFen+"#n积分");
		// processShenHunAttr(chara);
		 GameUtil.sendUpdate(chara);
		 //刷新数据
		 GameCommonUtil.refreShenHun(chara);
	
	
//		  CMD_SHENHUN_BREAK.log.info("魂魄--请求突破");
//        final Chara chara = GameObjectChar.getGameObjectChar().chara;
//        final int subJiFen = GameConfig.shenHunConfig.getData().get(String.valueOf(chara.shenHunDataSate)).getIntValue("jifen");
//        if (chara.shenHunDataExp - subJiFen < 0) {
//            GameUtil.sendMeTips("阴德不足无法突破");
//            return;
//        }
//        if (chara.shenHunIsTop == 1) {
//            GameUtil.sendMeTips("神魂已是最高阶段.");
//            return;
//        }
//       // chara.shenHunDataExp -= subJiFen;
//        ++chara.shenHunDataLaye;
//        GameUtil.sendMeTips("恭喜你突破成功，消耗了#R" + subJiFen + "#n阴德");
//        processShenHunAttr(chara);
//        GameUtil.sendUpdate(chara);
//        GameCommonUtil.refreShenHun(chara);
	}

	 public static void processShenHunAttr(Chara chara) {
        int shenhunUpLevel = chara.getShenhunUpLevel();
        final Map<String, JSONObject> attri = GameConfig.shenHunConfig.getAttri();
        final JSONObject jsonObject = attri.get(String.valueOf(chara.shenHunDataLaye));
        final int value = jsonObject.getIntValue("value") * chara.shenHunDataSate;
        final String name = jsonObject.getString("name");
        int shenHunPhyPower = (int) (value * ( 1+shenhunUpLevel/ 100.0));
        switch (name) {
            case "phy_power": {
                chara.shenHunPhyPower += shenHunPhyPower;
                break;
            }
            case "def": {
                chara.shenHunDef += shenHunPhyPower;
                break;
            }
            case "speed": {
                chara.shenHunSpeed += shenHunPhyPower;
                break;
            }
            case "max_life": {
                chara.shenHunmaxLife += shenHunPhyPower;
                break;
            }
            case "mag_power": {
                chara.shenHunMagPower += shenHunPhyPower;
                break;
            }
            default:
                break;
        }
        CMD_SHENHUN_BREAK.log.info("当前层数:{}", chara.shenHunDataLaye);
        if (chara.shenHunDataSate >= 10 && chara.shenHunDataLaye >= 10) {
            chara.shenHunIsTop = 1;
        } else if (chara.shenHunDataLaye >= 10) {
            ++chara.shenHunDataSate;
            chara.shenHunDataLaye = 0;
            chara.shenHunIsTop = 0;
        }
    }


	@Override
	public int cmd() {
		return 0x5302;
	}

}
