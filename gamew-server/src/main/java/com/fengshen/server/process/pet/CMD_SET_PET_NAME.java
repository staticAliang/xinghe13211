package com.fengshen.server.process.pet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 宠物改名
 * 
 *
 */
@Service
@Slf4j
public class CMD_SET_PET_NAME implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int no = GameReadTool.readByte(buff);
		String name = GameReadTool.readString(buff);
		log.info("设置宠物名字");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		Pattern p = Pattern.compile(GameCommonUtil.filterStr);
		Matcher m = p.matcher(name);
		if (m.find()) {
			GameCommonUtil.dialogOk("宠物名称只允许数字、中文、字母");
			return;
		} else if (name.length() < 2) {
			GameCommonUtil.dialogOk("宠物名称应在2-6个字符");
			return;
		}else if(name.contains("�")) {
			GameCommonUtil.dialogOk("宠物名称只允许数字、中文、字母");
			return;
		}
		String filterText = GameConfig.config.getMingan().getSettings().getFilterText();
		for(String ft:filterText.split("、")) {
			String name2 = name.toUpperCase();
			Pattern p2 = Pattern.compile(".*"+ft+".*");
			Matcher m2 = p2.matcher(name2);
			boolean isValid = m2.matches();
			if(isValid) {
				GameCommonUtil.dialogOk("违规宠物名称！");
				return;
			}
		}
		
		for(Petbeibao pet:chara.pets) {
			if(pet.no == no) {
				//如果宠物穿了时装
				PetShuXing petShuXing = pet.petShuXing.get(0);
				if(petShuXing.dye_icon != 0) {
					GameUtil.sendMeTips("宠物穿了时装，无法更改名称。");
					return;
				}
				if (petShuXing.str.equals(name)) 
				{
					GameUtil.sendMeTips("宠物名称相同了");
					return;
				}
				
			}
		}
		
		for(Petbeibao pet:chara.pets) {
			if(pet.no == no) {
				//如果宠物穿了时装
				PetShuXing petShuXing = pet.petShuXing.get(0);
				if(petShuXing.dye_icon != 0) {
					GameUtil.sendMeTips("宠物穿了时装，无法更改名称。");
					return;
				}
				petShuXing.str = name;
				gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
				GameUtil.sendMeTips("宠物更改名称成功！");
				return;
			}
		}
	}

	@Override
	public int cmd() {
		return 8272;
	}
}