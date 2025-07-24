package com.fengshen.server.process.system;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Dialog;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61591_0;
import com.fengshen.server.data.write.M61591_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeam;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 拒绝某个人的申请
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_REJECT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String peerName = GameReadTool.readString(buff);
		String askType = GameReadTool.readString(buff);
		Chara thisChara = GameObjectChar.getGameObjectChar().chara;
		GameObjectChar gameObjectChara = GameObjectChar.getGameObjectChar();
		Vo_61591_0 vo_61591_0 = new Vo_61591_0();
		vo_61591_0.name = peerName;
		vo_61591_0.ask_type = askType;
		GameObjectChar.send(new M61591_0(), vo_61591_0);
		
		if("invite_join".equals(askType) ) {
			//清除自己的邀请找出这个人
			for(Entry<Integer, Chara> invitation:gameObjectChara.invitationCharas.entrySet()) {
				if(invitation.getValue().name.equals(peerName)) {
					gameObjectChara.invitationCharas.remove(invitation.getValue().id);
					break;
				}
			}
			GameObjectChar toGameObjectChara = GameObjectCharMng.getGameObjectChar(peerName);
			if(toGameObjectChara != null) {
				gameObjectChara.invitationCharas.remove(toGameObjectChara.chara.id);
				//如果全部清空的话就设置为null
				if(gameObjectChara.invitationCharas.size() == 0) {
					gameObjectChara.invitationCharas = null;
				}
				GameCommonUtil.dialogOk("#Y"+thisChara.name+"#n拒绝了你的邀请！", toGameObjectChara.chara.id);
			}
		}else if("request_join".equals(askType)) {
			//删除请求列表
			Integer toCharaId = null;
			//发送提示
			GameTeam gameTeam = GameObjectChar.getGameObjectChar().gameTeam;
			if(gameTeam != null) {
				List<List<Chara>> list = gameTeam.liebiao;
				for(List<Chara> charas:list) {
					Iterator<Chara> iterator = charas.iterator();
					while(iterator.hasNext()) {
						Chara chara2 = iterator.next();
						if(chara2.getName().equals(peerName)) {
							toCharaId = chara2.id;
							iterator.remove();
							break;
						}
					}
				}
				//如果全部清空的话就设置为null
				if(gameTeam.liebiao.size() == 0) {
					gameTeam.liebiao.clear();
				}
				if(toCharaId != null) {
					GameCommonUtil.dialogOk("#Y"+thisChara.name+"#n拒绝了你的申请！", toCharaId);
				}
			}
		}else if("party_remote".equals(askType) 
				|| "party_invite".equals(askType) 
				|| "party".equals(askType)) {
			//删除记录
			Characters findOneByName = GameData.that.baseCharactersService.findOneByName(peerName);
			String gid = findOneByName.getGid();
			Example example = new Example(Dialog.class);
			example.createCriteria().andEqualTo("applyGid", gid).andEqualTo("peerName", thisChara.getPartyName())
			.andEqualTo("askType", "party");
			GameData.that.dialogService.deleteByExample(example);
		}
		log.info("拒绝某个人的申请:{},{}",peerName,askType);
		
	}

	@Override
	public int cmd() {
		return 0x1026;
	}

}
