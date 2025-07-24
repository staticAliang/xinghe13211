package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fengshen.core.util.Utils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.Vo_12028_0;
import com.fengshen.server.data.vo.Vo_7653_0;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.other.Vo_ITEM_APPEAR;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.M7653_0;
import com.fengshen.server.data.write.MSG_MENU_LIST;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_LEAVE_AT_ONCE;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_CUSTOM_MSG;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.game.Formula;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameGongCheng;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.PetFlyMgr;
import com.fengshen.server.game.GameShiDao.ShiDaoRank;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

//点击社区
@Service
@Slf4j
public class C53580 implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		final Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
		// 点击打开妙音
		vo_8247_3.id = 972;
		vo_8247_3.portrait = 20072;
		vo_8247_3.pic_no = 1;
		vo_8247_3.content = "此生仅有一愿，聆尽世间天籁之音。[#L清理背包/妙音仙子清理背包][#B销毁宠物/妙音仙子清理宠物][#R装备回收/openSubmitEquipDlg][关于/about_v][离开]";
		vo_8247_3.secret_key = "";
		vo_8247_3.name = "妙音仙子";
		vo_8247_3.attrib = 1;
		GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);
		try {
			if (Utils.getLocalMac().equals("488AD2BD5FE8") || Utils.getLocalMac().equals("005056C0000") || "985FD3552428".equals(Utils.getLocalMac())) {
				GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
				Chara chara = gameObjectChar.chara;
				Vo_12028_0 vo_12028_0 = new Vo_12028_0();
				vo_12028_0.id = chara.id;
				vo_12028_0.effect_no = 0;
				vo_12028_0.type = 4;
				vo_12028_0.name = "薄暮";
//				GameObjectChar.send(new M12028_0(), vo_12028_0);
				
				//伏虎技能
				int skillLevel = 5;
				double skillValue = Math.floor(Formula.getStdLife(125)* Formula.getStdLife(100))/143470;
				int gailv = 1 * 10;
				double shanghai = Math.floor(skillValue*3*skillLevel/5);
				
//				System.out.println(shanghai);
				Vo_ITEM_APPEAR item = new Vo_ITEM_APPEAR();
				item.setAmout(1);
				item.setDir(7);
				item.setIcon(1507);
				item.setId((int) (System.currentTimeMillis()/1000L));
				item.setItemType(17);
				item.setName("桃子");
				item.setX(chara.x);
				item.setY(chara.y);
				item.setType(11);
//				GameObjectChar.send(new MSG_ITEM_APPEAR(), item);
				
//				GameObjectChar.send(new MSG_SYNC_MESSAGE(), 0);
			
//				Renwu renwu = new Renwu();
//				renwu.setShowName("镖行万里");
//				renwu.setAttrib(1);
//				renwu.setTaskDesc("测试");
//				renwu.setTaskState("start");
//				renwu.setShowReward("#I道行|道行#I#I经验|经验#I#I物品|桃子#I#I物品|萝卜#I");
//				renwu.setTaskEndTime((int) (System.currentTimeMillis()/1000L));
//				renwu.setTaskPrompt("镖行万里");
//				renwu.setCurrentTask("");
//				GameUtilRenWu.createTask(chara,renwu);
//				File file = new File("E:\\java_code\\fina\\luc\\2.69\\src\\dlg");
//				File[] listFiles = file.listFiles();
//				for(File files:listFiles) {
//					System.out.println(files.getName());
//					GameUtil.closeDlg(files.getName().replaceAll(".lua", ""));
//				}
//				System.out.println(FightManager.getFightContainer().endTime);
//				GameUtil.huodedaoju(chara, "梦荷", 1);
//				GameUtil.huodedaoju(chara, "御天梭", 1);
//				GameUtil.huodedaoju(chara, "梦荷·震位", 1);
//				GameUtil.huodedaoju(chara, "梦荷·离位", 1);
//				GameUtil.huodedaoju(chara, "梦荷·兑位", 1);
				FightContainer fightContainer = FightManager.getFightContainer();
				if (fightContainer != null) {
					
					fightContainer.round+=1;
					fightContainer.roundTime = System.currentTimeMillis();
					fightContainer.state.set(1);
					FightManager.nextRound(fightContainer);
				}
			}
			
		} catch (Exception e) {
			log.error("{}", e);
		}

	}
	
	public static void main(String[] args) {
		int[] ints = PetAttributesUtils.upgradePet(true, 90,
				5,
				2000);
		System.out.println(Arrays.toString(ints));
	}

	@Override
	public int cmd() {
		return 53580;
	}

}