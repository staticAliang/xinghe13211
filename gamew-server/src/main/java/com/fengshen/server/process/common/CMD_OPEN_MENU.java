package com.fengshen.server.process.common;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Charge;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.NpcDialogueFrame;
import com.fengshen.db.domain.Renwu;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.MSG_MENU_LIST;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.domain.config.VipChargeConfig;
import com.fengshen.server.game.GameBossTianDiXing;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameShuaGuai;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameZone;
import com.fengshen.server.game.PetFlyMgr;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.service.MapGuardianService;
import com.fengshen.server.service.ZhengDaoDianService;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.NpcIds;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 打开菜单
 */
@Service
@Slf4j
public class CMD_OPEN_MENU implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int type = GameReadTool.readByte(buff);
		log.info("打开菜单,id={},type={}",id,type);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		GameMap gameMap2 = gameObjectChar.gameMap;
		if (chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢，不允许操作");
			return;
		}
		if(gameMap2.isDugeno() && ((GameZone) gameMap2).gameDugeon.meetNpc(chara, id)) {
			return;
		}
		
		Vo_APPEAR vo_APPEAR = GameCore.otherBoosMonster.get(id);
		if(vo_APPEAR != null) {
			if(vo_APPEAR.name.equals("桃树")) {
				GameUtil.changeNpcSession(id, vo_APPEAR.icon, "桃树", "树上的桃子简直让人流口水，快点摘下来尝尝看。#83m[摘桃子/getTaoZi][离开]");
				
			}else if(vo_APPEAR.name.equals("猎人头领")) {
				GameUtil.changeNpcSession(id, vo_APPEAR.icon, "猎人头领", "树上的桃子简直让人流口水，快点把它们给我全部连根拔起。#83m[赶走猎人/taoziluo_kill_out][离开]");
			}
			return;
		}
		
		//劫狱
		if(GameCore.jieyuMonster.get(id) != null) {
			Vo_APPEAR jieyu = GameCore.jieyuMonster.get(id);
			GameUtil.changeNpcSession(id, jieyu.org_icon, "劫道土匪", "牙崩半个不，管杀不管埋！\n#R战斗结束时死亡的角色会受到惩罚。[擒拿土匪/jieyu_qiuqing][离开]");
		}
		
		//月老
		if(id == 890) {
			if(chara.sex == 1 && chara.marriageMarryId != 0 && GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
					&& gameObjectChar.gameTeam.duiwu.size() == 2 
					&& gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId == chara.id) {
				//增加提亲菜单
				String yuding = "[预定婚礼时间]";
				if(chara.taskMap.get("预定婚礼") == null) {
					yuding = "";
				}
				if(chara.marriageMarryId != 0) {
					yuding+="[【离婚】我是来离婚的/lh][【离婚】强制离婚/qzlh]";
				}
				String memuStr = "我最喜欢给人绑红线，一旦绑上红线后就算是天南地北的有情男女也会终成眷属。你找我有什么事情？[【夫妻任务】领取任务][我们是来举办婚礼的]"+yuding+"[查看最近豪华婚礼][操作鸾凤宝玉、婚服和纪念册][没有什么事]";
				GameUtil.changeNpcSession(id, 6060, "月老", memuStr);
				return;
			}
			String op = "";
			if(chara.marriageMarryId != 0) {
				op="[【离婚】我是来离婚的/lh][【离婚】强制离婚/qzlh]";
			}
			GameUtil.changeNpcSession(id, 6060, "月老", "我最喜欢给人绑红线，一旦绑上红线后就算是天南地北的有情男女也会终成眷属。你找我有什么事情？"+op+"[【夫妻任务】领取任务][我们是来举办婚礼的][查看最近豪华婚礼][操作鸾凤宝玉、婚服和纪念册][没有什么事]");
			return;
		}
		// 在这里新增证道殿、英雄会、地图守护神等新模块
		if (NpcIds.isZhengDaoDianNpc(id)) {// 证道殿npc
			ZhengDaoDianService.openMenu(chara, id);
			return;
		}
		if (NpcIds.isHeroPubNpc(id)) {// 英雄会
			Npc npc = GameData.that.baseNpcService.findById(id);
			if (npc != null && npc.getName().indexOf("英雄会评议员") != -1) {
				HeroPubService.openMenu(chara, id);
				return;
			}
		}

		if (NpcIds.isMapGuardianNpc(id)) {// 地图守护神
			MapGuardianService.openMenu(chara, id);
			return;
		}

		// 天地星
		Vo_APPEAR xing = GameBossTianDiXing.xing.get(id);
		if (xing != null) {
			String isFight = "";
			if(GameCore.fightObject.get(id) != null) {
				isFight = "[隔岸关火/lookFight]";
			}
			GameUtil.changeNpcSession(id, xing.icon, xing.name,
					org.apache.commons.lang3.StringUtils.join(
					"我乃天界星官" , xing.name , "(" , xing.level , "级) , 巡游至此，你一介凡人,怎可挡我去路?#R\n高于星官29级以上将无法获得奖励。\n[我是来向你挑战的("
							, xing.level , "级)/我是来向你挑战的]\n" ,isFight, "[我是路过的/离开]"));
			return;
		}

		// 试道元魔
		if (GameShiDao.statzhuangtai == 2) {
			Integer[] shidaolevel = GameShiDao.shidaolevel;
			for (int k = 0; k < shidaolevel.length; ++k) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(shidaolevel[k]);
				for(GameMap gameMap:gameZone) {
					if (gameMap == null)
						break;
					boolean isFind = false;
					for (int i = 0; i < gameMap.gameShiDao.shidaoyuanmo.size(); ++i) {
						if (id == gameMap.gameShiDao.shidaoyuanmo.get(i).id) {
							Vo_MENU_LIST vo_8247_0 = new Vo_MENU_LIST();
							vo_8247_0.id = id;
							vo_8247_0.portrait = gameMap.gameShiDao.shidaoyuanmo.get(i).icon;
							vo_8247_0.pic_no = 1;
							vo_8247_0.content = "今天又可以活动活动筋骨了！真是开心呐！实力太弱的我可不陪他玩，如果#R20#n回合内没打败我，可是要被传出试道场外的！[让我试试你的厉害！/开始战斗][回头再说吧！/离开]"
									.replace("\\", "");
							vo_8247_0.secret_key = "";
							vo_8247_0.name = gameMap.gameShiDao.shidaoyuanmo.get(i).name;
							vo_8247_0.attrib = 0;
							GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_0);
							isFind = true;
							break;
						}
					}
					if(isFind) {
						break;
					}
				}
			}
		}

		//攻城boss
		Vo_APPEAR gongcheng = GameLine.gameGongCheng.gongchengBoss.get(id);
		if (gongcheng != null) {
			Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
			vo_8247_2.id = id;
			vo_8247_2.portrait = gongcheng.icon;
			vo_8247_2.pic_no = 1;
			vo_8247_2.content = "平日你们这些所谓的正道杀我同道无数，今日定要血洗此地，出一口恶气！[休要废话，妖孽受死吧]\n"
					+ "[果然厉害，还是回避一下吧]".replace("\\", "");
			vo_8247_2.secret_key = "";
			vo_8247_2.name = gongcheng.name;
			vo_8247_2.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
			return;
		}

		// 战神对话框
		if (GameLine.gameGongCheng.zhanshenGuaiwu != null && !GameLine.gameGongCheng.zhanshenGuaiwu.isEmpty()
				&& GameLine.gameGongCheng.zhanshenGuaiwu.get(id) != null) {
			// 添加战神的对话框
			Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
			vo_8247_2.id = id;
			vo_8247_2.portrait = GameLine.gameGongCheng.zhanshenGuaiwu.get(id).icon;
			vo_8247_2.pic_no = 1;
			vo_8247_2.content = "只有最勇敢的人，才可以得到最好的装备！[我要挑战/消灭战神]\n" + "[离开/离开]".replace("\\", "");
			vo_8247_2.secret_key = "";
			vo_8247_2.name = GameLine.gameGongCheng.zhanshenGuaiwu.get(id).name;
			vo_8247_2.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
			return;
		}

		// add tzhang 添加海盗的对话框
		if (GameLine.gameGongCheng.haidaoGuaiwu != null) {
			Vo_APPEAR haidao = GameLine.gameGongCheng.haidaoGuaiwu.get(id);
			if (haidao != null) {
				Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
				vo_8247_2.id = id;
				vo_8247_2.portrait = haidao.icon;
				vo_8247_2.pic_no = 1;
				vo_8247_2.content = "爷们刚想抢点东西，你就送上门来了！[为民除害/消灭海盗]\n" + "[糟了！遇到海盗了，赶紧逃命]".replace("\\", "");
				vo_8247_2.secret_key = "";
				vo_8247_2.name = haidao.name;
				vo_8247_2.attrib = 0;
				GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
				return;
			}
		}

		// 仙界神捕(悬赏)
		if (GameShuaGuai.xuanshang.size() > 0) {
			Vo_APPEAR vo = GameShuaGuai.xuanshang.get(id);
			if (vo != null) {
				Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
				vo_8247_2.id = vo.id;
				vo_8247_2.portrait = vo.icon;
				vo_8247_2.pic_no = 1;
				vo_8247_2.content = "那天杀的仙界臭捕,爷爷逃到凡人的地\n盘还穷追不舍!\n[追拿通缉犯]\n" + "[离开]".replace("\\", "");
				vo_8247_2.secret_key = "";
				vo_8247_2.name = vo.name;
				vo_8247_2.attrib = 0;
				GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
				return;
			}
		}
		for (int j = 0; j < chara.npcchubao.size(); ++j) {
			if (id == chara.npcchubao.get(j).id) {
				Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
				vo_8247_2.id = chara.npcchubao.get(j).id;
				vo_8247_2.portrait = chara.npcchubao.get(j).icon;
				vo_8247_2.pic_no = 1;
				vo_8247_2.content = "想抓我得先问问我手中的家伙答不答应。\n[就是来抓你的]\n" + "[我先准备准备]".replace("\\", "");
				vo_8247_2.secret_key = "";
				vo_8247_2.name = chara.npcchubao.get(j).name;
				vo_8247_2.attrib = 0;
				GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
				return;
			}
		}
		Vo_APPEAR shudao = chara.shudao.get(id);
		if(shudao != null) {
			Vo_MENU_LIST vo_8247_2 = new Vo_MENU_LIST();
			vo_8247_2.id = shudao.id;
			vo_8247_2.portrait = shudao.icon;
			vo_8247_2.pic_no = 1;
			vo_8247_2.content = "哈哈，送上们的肥肉。\n[今天我要为民除害]\n" + "[我先准备准备]".replace("\\", "");
			vo_8247_2.secret_key = "";
			vo_8247_2.name = shudao.name;
			vo_8247_2.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_2);
			return;
		}

		// 挑战上古妖王
		if (GameShuaGuai.shanggu.get(id) != null) {
			Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
			vo_8247_3.id = id;
			vo_8247_3.portrait = 6239;
			vo_8247_3.pic_no = 1;
			vo_8247_3.content = "[挑战上古妖王]我已经很久没有吃肉了，非常的饿！#R(妖王等级" + GameShuaGuai.shanggu.get(id).level + "级)[离开/离开]";
			vo_8247_3.secret_key = "";
			vo_8247_3.name = "上古妖王";
			vo_8247_3.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);
		}

		// 挑战万年妖王
		if (GameShuaGuai.wannian.get(id) != null) {
			Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
			vo_8247_3.id = id;
			vo_8247_3.portrait = 6258;
			vo_8247_3.pic_no = 1;
			vo_8247_3.content = "[挑战万年妖王]我已经很久没有吃肉了，非常的饿！#R(妖王等级" + GameShuaGuai.wannian.get(id).level + "级)[离开/离开]";
			vo_8247_3.secret_key = "";
			vo_8247_3.name = "万年妖王";
			vo_8247_3.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);
		}
		
		if (GameShuaGuai.guiguai.get(id) != null) {
			Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
			vo_8247_3.id = id;
			vo_8247_3.portrait = 6113;
			vo_8247_3.pic_no = 1;
			vo_8247_3.content = "[挑战鬼怪]我已经很久没有吃肉了，非常的饿！#R(鬼怪等级" + GameShuaGuai.guiguai.get(id).level + "级)[离开/离开]";
			vo_8247_3.secret_key = "";
			vo_8247_3.name = "鬼怪";
			vo_8247_3.attrib = 0;
			GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);
		}
		String content = "找我有什么事吗？[离开\\/离开]";
		Npc npc = GameData.that.baseNpcService.findById(id);
		
		if(npc != null) {
			//监狱牢头
			if(id == 1015) {
				GameUtil.changeNpcSession(npc, "你是来探监的吗？[查看在押犯人/zuolao_info][离开]");
				return;
			}
			//无意僧
			if(id == 868) {
				if(StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
					GameUtil.changeNpcSession(npc, "有因有缘集世间，有因有缘世间集；有因有缘灭世间，有因有缘世间灭。[我们想要结成固定队/fixedTeam][离开]");
				}else {
					GameUtil.changeNpcSession(npc, "有因有缘集世间，有因有缘世间集；有因有缘灭世间，有因有缘世间灭。[我们想要结成固定队/fixedTeam][退出固定队/quitFixedTeam][离开]");
				}
				return;
			}
		}else {
			//官道南打强盗
			Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
			Vo_61553_0 shimen = chara.taskMap.get("主线—拜入师门");
			Vo_61553_0 shanyu = chara.taskMap.get("主线—山雨欲来");
			if(chara.mapName.equals("官道南") && task != null && "主线—浮生若梦_s12".equals(task.currentTask)) {
				if(id == 55555555) {
					content = "干一行就要爱一行，打劫也是一样的！[【主线】打劫/主线—浮生若梦_s12][离开]";
					GameUtil.changeNpcSession(id, 6201, "强盗", content);
				}
			}else if(chara.mapName.equals("天墉城") && shimen != null) {
				if(shimen != null && "主线—拜入师门s7".equals(shimen.currentTask) && id == 55555555) {
					content = "小哥哥、小姐姐我好无聊，我想去玩。 [【主线】不要跑/主线—拜入师门s7][离开]";
					GameUtil.changeNpcSession(id, 6018, "走失的小孩", content);
				}else if(shimen != null && "主线—拜入师门s8".equals(shimen.currentTask) && id == 66666666) {
					content = "小孩子就是我抢了，咋地了你咬我啊[【主线】放开孩子/主线—拜入师门s8][离开]";
					GameUtil.changeNpcSession(id, 6213, "神秘蒙面人", content);
				}
			}else if(chara.mapName.equals("官道北") && shimen != null) {
				if("主线—拜入师门s15".equals(shimen.currentTask) && id == 66666666) {
					content = "上次你伤我，这次我要连本带利讨还回来！ [【主线】应约切磋/主线—拜入师门s15][离开]";
					String[] att_name = {"金光洞外门弟子", "云霄洞外门弟子", "玉柱洞外门弟子", "斗阙宫外门弟子", "白骨洞外门弟子" };
					int[] att_icon = new int[] {6004,6001,7002,7003,7005};
					GameUtil.changeNpcSession(id,  att_icon[chara.polar-1], att_name[chara.polar-1], content);
				}
			}else if(shimen != null && ("主线—拜入师门s22".equals(shimen.currentTask)) && id == 66666666 && chara.mapName.equals("桃柳林")){
				content = "我是一只小小小小鸟，想要飞呀飞却飞也飞不高！[【主线】查探究竟/主线—拜入师门s22][离开]";
				GameUtil.changeNpcSession(id, 6211 , "赤羽鸟怪", content);
			}else if(shimen != null && ("主线—拜入师门s23".equals(shimen.currentTask)) && id == 66666666 && chara.mapName.equals("桃柳林")){
				content = "我是一只小小小小鸟，想要飞呀飞却飞也飞不高！[【主线】查探究竟/主线—拜入师门s23][离开]";
				GameUtil.changeNpcSession(id, 6211 , "赤羽鸟怪", content);
			}else if(shimen != null && ("主线—拜入师门s24".equals(shimen.currentTask)) && id == 66666666 && chara.mapName.equals("东海渔村")){
				content = "在下乃诸葛亮在世，无所不能！[【主线】寻找蟒精/主线—拜入师门s24][离开]";
				GameUtil.changeNpcSession(id, 6035 , "樵夫", content);
			}else if(shimen != null && ("主线—拜入师门s27".equals(shimen.currentTask)) && id == 66666666 && chara.mapName.equals("东海渔村")){
				content = "还好我足够小心，不然就被那道士发现了。[【主线】看我的照妖镜/主线—拜入师门s27][离开]";
				GameUtil.changeNpcSession(id, 6035 , "樵夫", content);
			}else if(shimen != null && ("主线—拜入师门s28".equals(shimen.currentTask)) && id == 66666666 && chara.mapName.equals("东海渔村")){
				content = "这镜子是什么宝贝，竟破了我的易容术！[【主线】铲除蟒精/主线—拜入师门s28][离开]";
				GameUtil.changeNpcSession(id, 6206 , "蟒精", content);
			}else if(shanyu != null && ("主线—山雨欲来s6".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("轩辕庙")){
				content = "我看你这是来找死了，兄弟们给我上！[【主线】教训恶霸/主线—山雨欲来s6][离开]";
				GameUtil.changeNpcSession(id, 6202 , "恶霸", content);
			}else if(shanyu != null && ("主线—山雨欲来s7".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("轩辕庙")){
				content = "想不到啊，追的还挺快的啊！[【主线】追上妖风/主线—山雨欲来s7][离开]";
				GameUtil.changeNpcSession(id, 6140 , "妖风", content);
			}else if(shanyu != null && ("主线—山雨欲来s9".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("轩辕坟一层")){
				content = "又有美味送上门了，真爽啊。[【主线】查探一番/主线—山雨欲来s9][离开]";
				GameUtil.changeNpcSession(id, 6204 , "琵琶精", content);
			}else if(shanyu != null && ("主线—山雨欲来s14".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("北海沙滩")){
				content = "阿西吧！一天到晚的都打不到鱼，钱也赚不到，我都快饿死了。[【主线】可有异常/主线—山雨欲来s14][离开]";
				GameUtil.changeNpcSession(id, 6044 , "渔夫", content);
			}else if(shanyu != null && ("主线—山雨欲来s15".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("北海沙滩")){
				content = "我很饿啊，真的很饿啊，很久没有吃#R毛爷爷#n了。终于来了一个给我送#R毛爷爷#n的人！[【主线】前往查探/主线—山雨欲来s15][离开]";
				GameUtil.changeNpcSession(id, 6141 , "恶霸怨魂", content);
			}else if(shanyu != null && ("主线—山雨欲来s16".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("北海沙滩")){
				content = "我要赚很多钱钱钱！！[【主线】寻找定魂香/主线—山雨欲来s16][离开]";
				GameUtil.changeNpcSession(id, 6117 , "北海乌龙", content);
			}else if(shanyu != null && ("主线—山雨欲来s17".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("北海沙滩")){
				content = "我真的死的好惨啊，#R因为没有钱被人搞死的，#n你快救救我，#n[【主线】交给恶霸/主线—山雨欲来s17][离开]";
				GameUtil.changeNpcSession(id, 6141 , "恶霸怨魂", content);
			}else if(shanyu != null && ("主线—山雨欲来s18".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("北海沙滩")){
				content = "我要赚很多钱钱钱！！！[【主线】寻找定魂珠/主线—山雨欲来s18][离开]";
				GameUtil.changeNpcSession(id, 6117 , "北海乌龙", content);
			}else if(shanyu != null && ("主线—山雨欲来s22".equals(shanyu.currentTask)) && id == 66666666 && chara.mapName.equals("天墉城")){
				content = "我乃的东北虎，我最爱吃人了！！！[【主线】收伏虎妖/主线—山雨欲来s22][离开]";
				GameUtil.changeNpcSession(id, 6121 , "虎妖", content);
			}
			return;
		}
		List<NpcDialogueFrame> npcDialogueFrameList = (List<NpcDialogueFrame>) GameData.that.baseNpcDialogueFrameService
				.findByName(npc.getName());
		
		if (npcDialogueFrameList.size() != 0) {
			content = npcDialogueFrameList.get(0).getUncontent();
		}
		if(id == 1678) {
			List<Charge> chargeList = GameData.that.baseChargeService
					.findByAccountname(gameObjectChar.account.getName());
			int sum = chargeList.stream().mapToInt(Charge::getMoney).sum();
			String vipLevel = "VIP0";
			ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("VIP_FULI_CONFIG");
			List<VipChargeConfig> vipConfigs = com.alibaba.fastjson.JSONObject.parseArray(configInfo.getData(),VipChargeConfig.class);
			vipConfigs.sort(new Comparator<VipChargeConfig>() {
				@Override
				public int compare(VipChargeConfig o1, VipChargeConfig o2) {
					return o1.getMinMoney().compareTo(o2.getMinMoney());
				}
			});
			int level = 0;
			for(VipChargeConfig vipConfig:vipConfigs) {
				level++;
				//如果满足要求
				if(sum>=vipConfig.getMinMoney() && sum<= vipConfig.getMaxMoney()) {
					vipLevel = "VIP"+level;
					break;
				}
			}
			content =  String.format(content, sum, vipLevel+"\n");
		}
		//千面怪
		if(id == 982) {
			if(chara.taskMap.get("门派转换") != null) {
				content = new StringBuilder(content).insert(content.indexOf("["), "[门派转换/changePolar]").toString();
			}
			if(GameConfig.taoziLuoboStatus == 1) {
				String menu = "[萝卜桃子大收集/getLuoBoTaiZiTask]";
				Vo_61553_0 taoziTask = chara.taskMap.get("萝卜桃子大收集");
				if(taoziTask != null) {
					if(taoziTask.task_state.equals("success")) {
						menu = "[提交萝卜桃子任务/submitLuoBoTaiZiTask]";
					}
				}
				content = new StringBuilder(content).insert(content.indexOf("["), menu).toString();
			}
		}
		// 金
		if (id == 829) {
			content = "[挑战掌门][我要一睹掌门风采][进入证道殿]" + content;
			if(chara.polar != 1) {
				content = "找我什么事吗？[我要一睹掌门风采][离开]";
			}
			CharaStatue charStaure = CharaStatueService.getCharStaure("金系掌门");
			if(charStaure != null) {
				int icon = GameUtil.getWaiguan(1, charStaure.sex, null);
				GameUtil.changeNpcSession(id, icon, npc.getName(), content);
				return;
			}
		}
		// 木
		if (id == 1066) {
			content = "[挑战掌门][我要一睹掌门风采][进入证道殿]" + content;
			if(chara.polar != 2) {
				content = "找我什么事吗？[我要一睹掌门风采][离开]";
			}
			CharaStatue charStaure = CharaStatueService.getCharStaure("木系掌门");
			if(charStaure != null) {
				int icon = GameUtil.getWaiguan(2, charStaure.sex, null);
				GameUtil.changeNpcSession(id, icon, npc.getName(), content);
				return;
			}
		}
		// 水
		if (id == 1017) {
			content = "[挑战掌门][我要一睹掌门风采][进入证道殿]" + content;
			if(chara.polar != 3) {
				content = "找我什么事吗？[我要一睹掌门风采][离开]";
			}
			CharaStatue charStaure = CharaStatueService.getCharStaure("水系掌门");
			if(charStaure != null) {
				int icon = GameUtil.getWaiguan(3, charStaure.sex, null);
				GameUtil.changeNpcSession(id, icon, npc.getName(), content);
				return;
			}
		}
		// 火
		if (id == 1105) {
			content = "[挑战掌门][我要一睹掌门风采][进入证道殿]" + content;
			if(chara.polar != 4) {
				content = "找我什么事吗？[我要一睹掌门风采][离开]";
			}
			CharaStatue charStaure = CharaStatueService.getCharStaure("火系掌门");
			if(charStaure != null) {
				int icon = GameUtil.getWaiguan(4, charStaure.sex, null);
				GameUtil.changeNpcSession(id, icon, npc.getName(), content);
				return;
			}
		}
		// 土
		if (id == 941) {
			content = "[挑战掌门][我要一睹掌门风采][进入证道殿]" + content;
			if(chara.polar != 5) {
				content = "找我什么事吗？[我要一睹掌门风采][离开]";
			}
			CharaStatue charStaure = CharaStatueService.getCharStaure("土系掌门");
			if(charStaure != null) {
				int icon = GameUtil.getWaiguan(5, charStaure.sex, null);
				GameUtil.changeNpcSession(id, icon, npc.getName(), content);
				return;
			}
		}
		
		if (chara.taskMap.get("八仙梦境") != null) {
			Vo_61553_0 baxian = chara.taskMap.get("八仙梦境");
			if (baxian.show_name.equals("八仙梦境-吕洞宾")) {
				if (id == 948 && "0".equals(baxian.task_state)) {
					content = "[【八仙】吕洞宾的苦恼/问路]" + content;
				} else if (id == 949 && "1".equals(baxian.task_state)) {
					content = "[【八仙】请求帮助/帮助]" + content;
				} else if (id == 949 && "2".equals(baxian.task_state)) {
					content = "[【八仙】前去瑶池/找牡丹仙子]" + content;
				} else if (id == 949 && "8".equals(baxian.task_state)) {
					content = "[【八仙】前去瑶池/找牡丹仙子]" + content;
				} else if (id == 1022 && "2".equals(baxian.task_state)) {
					content = "[【八仙】凡尘美景/牡丹仙子领取]" + content;
				} else if (id == 1208 && "3".equals(baxian.task_state)) {
					content = "[【八仙】玄玉冰/玄玉冰]" + content;
				} else if (id == 1022 && "5".equals(baxian.task_state)) {
					content = "[【八仙】万事俱备/万事俱备]" + content;
				} else if (id == 950 && "7".equals(baxian.task_state)) {
					content = "[【八仙】铲除妖孽/铲除妖孽]" + content;
				} else if (id == 1023 && "8".equals(baxian.task_state)) {
					content = "[【八仙】物归原主/物归原主]" + content;
				}
			}
		}
		if (id == 1225) {
			content = "[杀我/离开]" + content;
		}
		if (chara.taskMap.get("师门任务") != null && chara.taskMap.get("师门任务").task_extra_para.equals(npc.getName())) {
			content = "[【师门】入世/sm-002_s1]" + content;
		}

		// 如果是各个阵主
		if (chara.taskMap.get("十绝阵") != null && npc.getName().contains("阵主")) {
			content = "[【十绝阵】讨教/十绝阵_s1]" + content;
		}


		// 飞升
		if (PetFlyMgr.zhanDouNpcName.equals(npc.getName())) {
			content = ("人可修道，宠物亦可修道，不管是人还是宠物，修炼到深处，皆可得道飞升[宠物飞升][帮派求助][离开]");
		}

		if (PetFlyMgr.YinLuNpcName.equals(npc.getName())) {
			if (PetFlyMgr.isTongGuoKaoYan(chara)) {
				content = ("飞升宠物所需材料：2阶骑宠灵魂3个以重塑其魂，驯兽诀1本以定其魂，萦香丸20颗以滋养其血肉，聚灵丹20颗以调养精气\n[飞升]\n"
						+ "[离开]".replace("\\", ""));
			} else {
				content = ("哎呀，最近鄙人乔迁新居，实在是忙不过来了，无奈只好托付#Y杂货店老板#n代为出售#R驯兽诀#n，想要医治宠物的道长大可移步前去，质量绝对有保证！[飞升/chose_pet_feiSheng][离开/离开]"
						.replace("\\", ""));
			}
		}
		for(Entry<String, Vo_61553_0> task:chara.taskMap.entrySet()) {
			String currentTask = task.getValue().currentTask;
			if(currentTask == null) {
				continue;
			}
			Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(currentTask);
			if (renwu != null && renwu.getNpcName() != null) {
				if (npc.getName().equals(renwu.getNpcName())) {
					content = renwu.getUncontent() + content;
				}
				if(!StringUtils.isNullOrEmpty(renwu.getNpcName())) {
					String[] split = renwu.getNpcName().split("\\_");
					if(split != null && split.length==5) {
						String name = split[chara.polar - 1];
						if (name.equals(npc.getName())) {
							content = renwu.getUncontent() + content;
						}
					}
				}
			}
		}
		// 妙手道人NPC自动填充销毁宠物的列表
		if (id == 978 || id == 972) {
			content = "此生仅有一愿，聆尽世间天籁之音。[#L清理背包/妙音仙子清理背包][#B销毁宠物/妙音仙子清理宠物][#R装备回收/openSubmitEquipDlg][离开]";
		}
		if (id == 928) {
			content = "[【领取法宝】提交#R蟠螭结、雪魂丝链#n]" + content;
		}

		// 如果是四大神兽
		if ("朱雀玄武青龙1白虎".contains(npc.getName()) && chara.xiufaNpcName != null && !chara.xiufaNpcName.equals("")) {
			if (!npc.getName().equals(chara.xiufaNpcName))
				content = "没事别来烦我![离开/离开]";
			if (chara.xiufacishu < 4 && chara.xiufaNpcName.equals(npc.getName()))
				content = "[【修法】我是来消灭你的/消灭修法神兽][离开/离开]" + "您是活腻歪了吗？";
		}
		// 天机老人飞升
		if (id == 955) {
			if (chara.level >= 110 && chara.isFeisheng == 0) {
				content = "[【飞升引路人】人物飞升/getFsrw]" + content;
			}
		}
		// 南华真人
		if (id == 1196) {
			Vo_61553_0 vo_61553_0 = chara.taskMap.get("飞升—引路人");
			if (chara.isFeisheng == 0 && vo_61553_0 == null) {
				content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！[离开/离开]";
			} else if (vo_61553_0 != null) {
				if (vo_61553_0.task_state.equals("1")) {
					content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！[【飞升】结婴之路/结婴之路][【飞升】帮派求助/帮派求助][离开/离开]";
				} else if (vo_61553_0.task_state.equals("4")) {
					// 完成任务凝结成婴
					content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！[【飞升】结婴/结婴][离开/离开]";
				}
			} else if (chara.isFeisheng == 1) {
				// 已经完成引路人
				if (chara.upgrade_type < 3) {
					content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！[【飞升】重新结婴/重新结婴]";
					//只有当角色和娃娃都达到120级才可飞升
					if (chara.upgrade_level > 119) {
						content += "[【仙道难】飞升仙魔/飞升仙魔]";
					}
					content += "[离开/离开]";
				} else {
					content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！[【仙道难】仙魔转换/仙魔转换][离开/离开]";
				}
			}
		}
		if (1344 == id) {
			Vo_61553_0 vo_61553_0 = chara.taskMap.get("飞升—引路人");
			if (vo_61553_0 != null && vo_61553_0.task_state.equals("2")) {
				content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！\n[【飞升】那只有得罪了！]\n[【飞升】帮派求助]\n[离开]\n";
			}
		}
		if (1345 == id) {
			Vo_61553_0 vo_61553_0 = chara.taskMap.get("飞升—引路人");
			if (vo_61553_0 != null && vo_61553_0.task_state.equals("3")) {
				content = "修真之人，若能结成纯正之婴，加以修炼，日后必定飞升！\n[【飞升】那只有得罪了！]\n[【飞升】帮派求助]\n[离开]\n";
			}
		}
		if (id == 1649) {
			content = "[杀我][离开]";
		}
		// 地劫
		if (chara.upgrade_state != 0) {
			if (chara.upgrade_level >= 82) {
				if (id == 1046) {
					content = "[【地劫】大闹龙宫][离开]";
				}
			}
		}
		// 帮派总管
		if (id == 1006) {
			content = "我是整个帮派的大总管，帮里的大事小事我都可以处理，你找我有什么事？";
			int partyFightNum = GameConfig.config.getBaseConfig().getPartyFightNum();
			if (partyFightNum == 0) {
				partyFightNum = 1;
			}
			int partyNum = GameConfig.config.getBaseConfig().getPartyNum();
			if (partyNum == 0) {
				partyNum = 10;
			}
			if (chara.partyNum <= partyNum) {
				content += "[【帮派任务】我要为帮派出力/bprw][【帮派任务】扫荡帮派任务/bprwsd#TIP:1]";
			}
			if (chara.partyFightNum <= partyFightNum) {
				content += "[【日常挑战】帮派日常挑战/bprctz][【日常挑战】扫荡日常挑战/bprctzsd#TIP:1]";
			}
			content += "[离开]";
		}
		// 帮派任务
		Vo_61553_0 partyTask = chara.taskMap.get("帮派任务");
		if (partyTask != null) {
			String ext = partyTask.task_extra_para;
			// 解析参数
			if (ext.indexOf("goGoods:天山雪莲") != -1) {
				String[] e = ext.split(":");
				// 如果当前id是一样的话
				if (Integer.valueOf(e[2]) == id) {
					content = "护送天山雪莲[【帮派任务】护送天山雪莲/tsxl][离开]";
				}
			}
		}
		// 帮派日常挑战点击
		Vo_61553_0 partyTzTask = chara.taskMap.get("帮派日常挑战");
		if (partyTzTask != null) {
			if (partyTzTask.task_prompt.indexOf(npc.getName()) != -1) {
				content = new StringBuilder(content).insert(content.indexOf("["), "[【日常挑战】帮派日常挑战]").toString();
			}
		}
		// 贾师爷
		if (1044 == id) {
			if (!StringUtils.isNullOrEmpty(chara.getPartyName())) {
				if (!StringUtils.isNullOrEmpty(chara.getPartyJob()) && "帮主".equals(chara.getPartyJob())) {
					content = new StringBuilder(content).insert(content.indexOf("["), "[【帮派管理】我想解散帮派]").toString();
				}
			}
		}
		// 试道申请人
		if (962 == id || 1170 == id) {
			// 查询该角色是否为GM
			if (gameObjectChar.privilege == 1000) {
				content = new StringBuilder(content).insert(content.indexOf("["),"[记者专属入口/gmEnterSd]").toString();
			}
			//参加试道有奖励
			if(chara.shidaoExp >0 || chara.shidaoMartial>0 || chara.shidaoTao>0) {
				if(962 == id) {
					content = new StringBuilder(content).insert(content.indexOf("["), "[领取奖励/shidaoReward]").toString();
				}
			}
		}
		// 五大门派师尊
		if (id == 831 || id == 1068 || id == 1019 || id == 1107 || id == 943) {
			
			if (chara.level >= 100 && chara.isFinish100Task == 0) {
				int[] polar2 = { 831, 1068, 1019, 1107, 943 };
				if (polar2[chara.polar - 1] == id) {
					content = new StringBuilder(content).insert(content.indexOf("["), "[【百级拜师】学习更多东西/百级拜师任务]")
							.toString();
				}
			}
		}
		if (1663 == id) {
			Characters characters = GameData.that.characterService.findById(chara.id);
			Accounts accounts = GameData.that.baseAccountsService.findById(characters.getAccountId());
			List<Charge> chargeList = (List<Charge>) GameData.that.baseChargeService
					.findByAccountname(accounts.getName());
			int sum = chargeList.stream().mapToInt(Charge::getMoney).sum();
			content = "你已累计充值#R" + sum + "#n元\n#n" + content;
		}
		// 仙界神捕
		if (1195 == id) {
			//59以上才能领任务
			if(chara.level>=60) {
				Vo_61553_0 task = chara.taskMap.get("悬赏任务");
				if (task != null && task.task_state.equals("finish")) {
					content += "[我想领取悬赏经验/我想领取悬赏经验][我想领取悬赏道行/我想领取悬赏道行][我想领取悬赏潜能/我想领取悬赏潜能]";
				}else {
					if(chara.xuanshangcishu<GameConfig.config.getBaseConfig().getXuanshangcishu()) {
						content += "[领取悬赏任务/领取悬赏任务]";
					}
				}
			}
			content += "[离开/离开]";
		}
		// 升级奖励大使
		if (1669 == id) {
			content = "亲爱的道友我这里可以领取升级奖励哦，每个奖励只能领取一次。";
			if (chara.levelUpReward[0] == 0) {
				content += "[领取70级奖励/2ee3028051f059e9ad57743f80ab5860]";
			}
			if (chara.levelUpReward[1] == 0) {
				content += "[领取80级奖励/f3285ad36aea51fd9f3c831b62818a83]";
			}
			if (chara.levelUpReward[2] == 0) {
				content += "[领取90级奖励/fc164c14f5f45512a79dbbf89ef47bf3]";
			}
			if (chara.levelUpReward[3] == 0) {
				content += "[领取100级奖励/fb33f062d73f5dfe83440db547a44096]";
			}
			if (chara.levelUpReward[4] == 0) {
				content += "[领取110级奖励/45a34b68cec95251aff8a7e6aa37bb89]";
			}
			if (chara.levelUpReward[5] == 0) {
				content += "[领取120级奖励/8ff29f968d7851d098c6a8dbe72018a3]";
			}
			if (chara.levelUpReward[6] == 0) {
				content += "[领取130级奖励/6518b4267cf5561dba569174d3bcb87b]";
			}
			content += "[离开/离开]";
		}
		Vo_MENU_LIST vo_8247_4 = GameUtil.a8247_1(npc, content);
		GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_4);
	}

	@Override
	public int cmd() {
		return 4150;
	}
}