package com.fengshen.server.process.pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Pet;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_41043_0;
import com.fengshen.server.data.vo.Vo_41045_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M12285_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M41043_0;
import com.fengshen.server.data.write.M41045_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求召唤精怪
 * 
 *
 */
@Service
@Slf4j
public class CMD_SUMMON_MOUNT_REQUEST implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int flag = GameReadTool.readByte(buff);
		log.info("请求召唤精怪");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		String[] getStr = null;
		String petId = "";
		// 普通召唤2-8阶数
		if (flag == 1) {
			Vo_41043_0 vo_41043_0 = new Vo_41043_0();
			vo_41043_0.flag = 1;
			vo_41043_0.name = randonSummonMount()[0];
			GameObjectChar.send(new M41043_0(), vo_41043_0);
			GameObjectChar.send(new M12285_0(), chara.id);
		}
		if (flag == 3) {
			if (GameCommonUtil.isNotGameManage(GameObjectChar.getGameObjectChar())) {
				if(GameCommonUtil.getGoodsNum(chara, "精怪诱饵") < 1) {
					GameUtil.sendMeTips("精怪诱饵不足");
					return;
				}
			}
			String[] strings = randonSummonMount();
			getStr = strings;
			List<Petbeibao> list = new ArrayList<Petbeibao>();
			Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
			Petbeibao petbeibao = new Petbeibao();
			createZuoQi(petbeibao, pet, chara, 10);
			petbeibao.petShuXing.get(0).penetrate = 2;
			petbeibao.petShuXing.get(0).polar_point = 4;
			petbeibao.petShuXing.get(0).max_life = petbeibao.petShuXing.get(0).def;
			petbeibao.petShuXing.get(0).max_mana = petbeibao.petShuXing.get(0).dex;
			petbeibao.petShuXing.get(0).enchant_nimbus = 0;
			petbeibao.petShuXing.get(0).max_enchant_nimbus = 0;
			petbeibao.petShuXing.get(0).suit_light_effect = 1;
			petbeibao.petShuXing.get(0).hide_mount = Integer.valueOf(strings[1]);
			petbeibao.petShuXing.get(0).phy_power = 1;
			petbeibao.petShuXing.get(0).mag_power = 1;
			petbeibao.petShuXing.get(0).life = 1;
			petbeibao.petShuXing.get(0).speed = 1;
			PetShuXing shuXing = new PetShuXing();
			shuXing.no = 23;
			shuXing.type1 = 2;
			shuXing.accurate = 4 * (Integer.valueOf(strings[1]) - 1);
			shuXing.mana = 4 * (Integer.valueOf(strings[1]) - 1);
			shuXing.wiz = 3 * (Integer.valueOf(strings[1]) - 1);
			shuXing.all_polar = 0;
			shuXing.upgrade_magic = 0;
			shuXing.upgrade_total = 0;
			petbeibao.petShuXing.add(shuXing);
			BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
			petbeibao.petShuXing.get(0).max_life = petbeibao.petShuXing.get(0).def;
			petbeibao.petShuXing.get(0).max_mana = petbeibao.petShuXing.get(0).dex;
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			GameUtil.dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
					petbeibao.id, chara, petbeibao);
			chara.pets.add(petbeibao);
			list.add(petbeibao);
			GameObjectChar.send(new MSG_UPDATE_PETS(), list);
			Vo_12269_0 vo_12269_0 = new Vo_12269_0();
			vo_12269_0.id = petbeibao.id;
			vo_12269_0.owner_id = chara.id;
			GameObjectChar.send(new M12269_0(), vo_12269_0);
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 2;
			vo_40964_0.name = strings[0];
			vo_40964_0.param = String.valueOf(petbeibao.petShuXing.get(0).type);
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_2 = new Vo_20480_0();
			vo_20480_2.msg = "恭喜你召唤了一只" + strings[0];
			vo_20480_2.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_2);
			Vo_41045_0 vo_41045_0 = new Vo_41045_0();
			vo_41045_0.flag = 3;
			vo_41045_0.id = petbeibao.id;
			GameObjectChar.send(new M41045_0(), vo_41045_0);
			petId = petbeibao.petShuXing.get(0).auto_fight;
			// 扣除道具
			if (GameCommonUtil.isNotGameManage(GameObjectChar.getGameObjectChar())) {
				int coin = 5000000;
				chara.cash -= coin;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				GameUtil.removemunber(chara, "精怪诱饵", 1);
			}
		}

		// 高级召唤
		if (flag == 2) {
			Vo_41043_0 vo_41043_0 = new Vo_41043_0();
			vo_41043_0.flag = 1;
			vo_41043_0.name = hightSummonMount()[0];
			GameObjectChar.send(new M41043_0(), vo_41043_0);
			GameObjectChar.send(new M12285_0(), chara.id);
		}
		if (flag == 4) {
			if (GameCommonUtil.isNotGameManage(GameObjectChar.getGameObjectChar())) {
				if(GameCommonUtil.getGoodsNum(chara, "精怪诱饵") < 10) {
					GameUtil.sendMeTips("精怪诱饵不足");
					return;
				}
			}
			String[] strings = hightSummonMount();
			getStr = strings;
			List<Petbeibao> list = new ArrayList<Petbeibao>();
			Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
			Petbeibao petbeibao = new Petbeibao();
			createZuoQi(petbeibao, pet, chara, 10);
			petbeibao.petShuXing.get(0).penetrate = 2;
			petbeibao.petShuXing.get(0).polar_point = 4;
			petbeibao.petShuXing.get(0).max_life = petbeibao.petShuXing.get(0).def;
			petbeibao.petShuXing.get(0).max_mana = petbeibao.petShuXing.get(0).dex;
			petbeibao.petShuXing.get(0).enchant_nimbus = 0;
			petbeibao.petShuXing.get(0).max_enchant_nimbus = 0;
			petbeibao.petShuXing.get(0).suit_light_effect = 1;
			petbeibao.petShuXing.get(0).hide_mount = Integer.valueOf(strings[1]);
			petbeibao.petShuXing.get(0).phy_power = 1;
			petbeibao.petShuXing.get(0).mag_power = 1;
			petbeibao.petShuXing.get(0).life = 1;
			petbeibao.petShuXing.get(0).speed = 1;
			PetShuXing shuXing = new PetShuXing();
			shuXing.no = 23;
			shuXing.type1 = 2;
			shuXing.accurate = 4 * (Integer.valueOf(strings[1]) - 1);
			shuXing.mana = 4 * (Integer.valueOf(strings[1]) - 1);
			shuXing.wiz = 3 * (Integer.valueOf(strings[1]) - 1);
			shuXing.all_polar = 0;
			shuXing.upgrade_magic = 0;
			shuXing.upgrade_total = 0;
			petbeibao.petShuXing.add(shuXing);
			BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0), petbeibao);
			petbeibao.petShuXing.get(0).max_life = petbeibao.petShuXing.get(0).def;
			petbeibao.petShuXing.get(0).max_mana = petbeibao.petShuXing.get(0).dex;
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			GameUtil.dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
					petbeibao.id, chara, petbeibao);
			chara.pets.add(petbeibao);
			list.add(petbeibao);
			GameObjectChar.send(new MSG_UPDATE_PETS(), list);
			Vo_12269_0 vo_12269_0 = new Vo_12269_0();
			vo_12269_0.id = petbeibao.id;
			vo_12269_0.owner_id = chara.id;
			GameObjectChar.send(new M12269_0(), vo_12269_0);
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 2;
			vo_40964_0.name = strings[0];
			vo_40964_0.param = String.valueOf(petbeibao.petShuXing.get(0).type);
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
			Vo_20480_0 vo_20480_2 = new Vo_20480_0();
			vo_20480_2.msg = "恭喜你召唤了一只" + strings[0];
			vo_20480_2.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_2);
			Vo_41045_0 vo_41045_0 = new Vo_41045_0();
			vo_41045_0.flag = 3;
			vo_41045_0.id = petbeibao.id;
			GameObjectChar.send(new M41045_0(), vo_41045_0);
			petId = petbeibao.petShuXing.get(0).auto_fight;
			// 扣除道具
			if (GameCommonUtil.isNotGameManage(GameObjectChar.getGameObjectChar())) {
				int coin = 50000000;
				chara.cash -= coin;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				GameUtil.removemunber(chara, "精怪诱饵", 10);
			}
		}
		if (getStr != null && getStr.length > 1) {
			if ("6".equals(getStr[1])) {
				// 爆谣言
				String msg = "呀这是什么#63m，恭喜#Y" + chara.name + "#n玩家成功召唤出#R" + getStr[1] + "介#n坐骑" + "{\t" + getStr[0]
						+ "=宠物=" + petId + "|{\"id\":" + chara.id + ",\"time\":" + System.currentTimeMillis() + "}"
						+ "这运气不是一般的好，各位道友如果想要召唤坐骑，可到#R东海渔村#n玉真子处。";
				GameUtil.sendYaoYan(msg);
			} else if ("8".equals(getStr[1])) {
				String msg = "难道这就是传说中的#R八阶#n超级坐骑？？？#63m，恭喜#Y" + chara.name + "#n玩家成功召唤出#R" + getStr[1] + "介#n坐骑"
						+ "{\t" + getStr[0] + "=宠物=" + petId + "|{\"id\":" + chara.id + ",\"time\":"
						+ System.currentTimeMillis() + "}" + "这运气不是一般的好，简直是爆棚了，各位道友如果想要召唤坐骑，可到#R东海渔村#n玉真子处。";
				GameUtil.sendYaoYan(msg);
			}
			
		}
	}

	@Override
	public int cmd() {
		return 41044;
	}

	public int subtraction() {
		Random r = new Random();
		return r.nextInt(10);
	}

	// 普通坐骑抽奖
	private static String[] randonSummonMount() {
		int random = new Random().nextInt(10000)+1;
//		if (random > 9999) {
//			// 随机抽取8介坐骑
//			String[] zuoqi = new String[] { "墨麒麟", "太极熊" };
//			String name = zuoqi[new Random().nextInt(zuoqi.length)];
//			return new String[] { name, "8" };
//		}
		if (random > 9990) {
			String[] zuoqi = new String[] { "岳麓剑", "古鹿", "北极熊", "筋斗云" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "6" };
		} else if (random > 9900) {
			String[] zuoqi = new String[] { "玉豹", "仙葫芦", "无极熊","翠灵剑" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "5" };
		} else if (random > 5000) {
			String[] zuoqi = new String[] { "幻鹿", "赤焰葫芦" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "4" };
		} else if (random > 40) {
			return new String[] { "凌岩豹", "3" };
		} else {
			return new String[] { "仙阳剑", "2" };
		}
	}

	private static String[] hightSummonMount() {
		int random = new Random().nextInt(10000)+1;
//		if (random > 9990) {
//			// 随机抽取8介坐骑
//			String[] zuoqi = new String[] { "墨麒麟", "太极熊" };
//			String name = zuoqi[new Random().nextInt(zuoqi.length)];
//			return new String[] { name, "8" };
//		} else 
		if (random > 9900) {
			String[] zuoqi = new String[] { "岳麓剑", "古鹿", "北极熊", "筋斗云" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "6" };
		} else if (random > 7800) {
			String[] zuoqi = new String[] { "玉豹", "仙葫芦", "无极熊" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "5" };
		} else {
			String[] zuoqi = new String[] { "幻鹿", "赤焰葫芦" };
			return new String[] { zuoqi[new Random().nextInt(zuoqi.length)], "4" };
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10000; i++) {
			String[] callMounts = hightSummonMount();
//			if (callMounts[1].equals("6")) {
//				System.out.println("出现6介");
//				System.out.println(i);
//				break;
//			}
			if (callMounts[1].equals("8")) {
				System.out.println("出现8介");
				System.out.println(i);
				break;
			}
		}

	}

	private void createZuoQi(Petbeibao petbeibao, Pet pet, Chara chara, final int suiji) {
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		petbeibao.no = GameUtil.getNo(chara);
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 15000;
		shuXing.double_hit = 100;
		shuXing.suit_polar = pet.getName();
		shuXing.auto_fight = GameCommonUtil.UUID();
		if (pet.getPolar().equals("金")) {
			shuXing.metal = 1;
		}
		if (pet.getPolar().equals("木")) {
			shuXing.metal = 2;
		}
		if (pet.getPolar().equals("水")) {
			shuXing.metal = 3;
		}
		if (pet.getPolar().equals("火")) {
			shuXing.metal = 4;
		}
		if (pet.getPolar().equals("土")) {
			shuXing.metal = 5;
		}
		shuXing.mana_effect = pet.getLife() - 40 - Petbeibao.subtraction(suiji);
		shuXing.attack_effect = pet.getMana() - 40 - Petbeibao.subtraction(suiji);
		shuXing.mag_effect = pet.getPhyAttack() - 40 - Petbeibao.subtraction(suiji);
		shuXing.phy_absorb = pet.getMagAttack() - 40 - Petbeibao.subtraction(suiji);
		shuXing.phy_effect = pet.getSpeed() - 40 - Petbeibao.subtraction(suiji);
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;

		petbeibao.petShuXing.add(shuXing);

		CharaPet charaPet = new CharaPet();
		charaPet.setCid(chara.id);
		charaPet.setUuid(chara.uuid);
		charaPet.setOwnerName(chara.name);
		charaPet.setPetName(shuXing.str);
		charaPet.setPet(JSONObject.toJSONString(petbeibao));
		GameData.that.charaPetService.createPet(charaPet);
		petbeibao.id = charaPet.getId();
	}
}