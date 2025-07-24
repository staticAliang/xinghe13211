package com.fengshen.server.process.autotalk;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.AutoTalkVo;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.SensitiveWordInit;
import com.fengshen.server.util.SensitivewordFilter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 保存自动喊话信息
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_AUTO_TALK_SAVE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		String content = GameReadTool.readString2(buff);
		if(GameConfig.menuAuths.indexOf("autoTalk") == -1) {
			return;
		}
		//刷新信息
		log.info("保存自动喊话，{},{}",id,content);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//过滤敏感词
		List<String> datas = SensitiveWordInit.readSensitiveWord();
		content = SensitivewordFilter.replaceSensitiveWord(datas, content,1,"*"); 
		List<AutoTalkVo> autoTalks = JSONObject.parseArray(content,AutoTalkVo.class);
		Iterator<AutoTalkVo> iterator = autoTalks.iterator();
		while(iterator.hasNext()) {
			AutoTalkVo vo = iterator.next();
			//增加和修改
			if(vo.getOp_type() == 1 || vo.getOp_type() == 2) {
				int size = 0;
				try {
					size = vo.getMsg().getBytes("GBK").length;
				} catch (UnsupportedEncodingException e) {
					log.error("{}",e);
				}
				if(size>24) {
					GameUtil.sendMeTips("内容长度超限,保存失败");
					return;
				}
				GameUtil.sendMeTips("设置成功！");
			}else if(vo.getOp_type() == 3) { //删除
				iterator.remove();
				GameUtil.sendMeTips("删除成功！");
			}
		}
		
		for(Petbeibao pet:chara.pets) {
			//设置宠物的
			if(pet.id == id) {
				pet.autoTalk = autoTalks;
				return;
			}
		}
		//人物的
		chara.autoTalk = autoTalks;
	}

	@Override
	public int cmd() {
		return 0x8092;
	}

}
