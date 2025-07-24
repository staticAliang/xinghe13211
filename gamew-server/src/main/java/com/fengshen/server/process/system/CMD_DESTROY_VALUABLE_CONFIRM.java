package com.fengshen.server.process.system;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.system.Vo_DESTROY_VALUABLE_LIST;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.system.MSG_DESTROY_VALUABLE_LIST;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 确认销毁该道具或宠物
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_DESTROY_VALUABLE_CONFIRM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int num = GameReadTool.readInt(buff);
		log.info("确认销毁该道具或宠物， num={}",num);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(gameObjectChar.currentConfirmItem.equals("destory_valuable")) {
			@SuppressWarnings("unchecked")
			Map<String,Integer> confirmData = (Map<String, Integer>) gameObjectChar.confirmData;
			if(confirmData == null) {
				GameUtil.sendMeTips("额，你想干什么！");
				return;
			}
			Integer code = confirmData.get("code");
			Integer type = confirmData.get("type");
			Integer id = confirmData.get("id");
			if(code != num) {
				GameUtil.sendMeTips("验证码错误或失效");
				return;
			}
			try {
				if(type == 1) {
					//宠物
					for(Petbeibao petbeibao:chara.pets) {
						if(petbeibao.id == id) {
							if(petbeibao.id == chara.chongwuchanzhanId) {
								GameUtil.sendMeTips("参战宠物无法销毁");
								return;
							}else if(petbeibao.id == chara.zuoqiId) {
								GameUtil.sendMeTips("骑乘坐骑无法销毁");
								return;
							}else if(petbeibao.id == chara.flyPetID) {
								GameUtil.sendMeTips("飞升中宠物无法销毁");
								return;
							}else if(petbeibao.id == chara.chongwuluezhenId) {
								GameUtil.sendMeTips("掠阵宠物无法销毁");
								return;
							}
							GameUtil.sendMeTips("销毁成功");
							Vo_12269_0 vo_12269_0 = new Vo_12269_0();
							vo_12269_0.id = id;
							vo_12269_0.owner_id = 0;
							GameObjectChar.send(new M12269_0(), vo_12269_0);
							chara.pets.remove(petbeibao);
							GameData.that.charaPetService.deleteByPrimaryKey(id);
							if(petbeibao.petShuXing.get(0).penetrate == 3) {
								Integer recoveryBianYiScore = GameConfig.config.getBaseConfig().getRecoveryBianYiScore();
								if(recoveryBianYiScore > 0) {
									GameUtil.addchargeScore(gameObjectChar, recoveryBianYiScore, "回收大使");
								}
							}else if(petbeibao.petShuXing.get(0).penetrate == 4) {
								Integer recoveryShenShouScore = GameConfig.config.getBaseConfig().getRecoveryShenShouScore();
								if(recoveryShenShouScore > 0) {
									GameUtil.addchargeScore(gameObjectChar, recoveryShenShouScore, "回收大使");
								}
							}
							//关闭页面
							GameUtil.closeDlg("SubmitPetDlg");
							//重新加载
							StringBuilder sb = new StringBuilder();
							for (int l = 0; l < chara.pets.size(); ++l) {
								if (chara.pets.get(l).id != chara.flyPetID) {
									sb.append(chara.pets.get(l).id).append("|");
								}
							}
							if(!sb.toString().isEmpty()) {
								Vo_DESTROY_VALUABLE_LIST info = new Vo_DESTROY_VALUABLE_LIST();
								info.setId_str(sb.toString());
								info.setType(1);
								GameObjectChar.send(new MSG_DESTROY_VALUABLE_LIST(), info);
							}
							break;
						}
					}
				}else if(type ==2) {
					//装备道具
					Iterator<Goods> iterator = chara.backpack.iterator();
					while(iterator.hasNext()) {
						Goods next = iterator.next();
						if(next.pos == id) {
							if(next.goodsInfo.color == 12) {
								//判断是否设置过装备回收
								Integer recoveryEquipScore = GameConfig.config.getBaseConfig().getRecoveryEquipScore();
								if(recoveryEquipScore > 0) {
									GameUtil.addchargeScore(gameObjectChar, recoveryEquipScore, "回收大使");
								}
							}
							iterator.remove();
							GameObjectChar.send(new MSG_INVENTORY_REMOVE(), next.pos);
							break;
						}
					}
					//刷新
					iterator = chara.backpack.iterator();
					StringBuilder sb = new StringBuilder();
					while(iterator.hasNext()) {
						Goods next = iterator.next();
						if(next.goodsInfo.color == 12) {
							sb.append(next.pos).append("|");
						}
					}
					GameUtil.closeDlg("SubmitEquipDlg");
					if(!StringUtils.isNullOrEmpty(sb.toString())) {
						Vo_DESTROY_VALUABLE_LIST info = new Vo_DESTROY_VALUABLE_LIST();
						info.setId_str(sb.toString());
						info.setType(2);
						GameObjectChar.send(new MSG_DESTROY_VALUABLE_LIST(), info);
					}
				}
			} finally {
				gameObjectChar.confirmData = null;
				gameObjectChar.currentConfirmItem = "";
			}
		}
	}

	@Override
	public int cmd() {
		return 0x8096;
	}

}
