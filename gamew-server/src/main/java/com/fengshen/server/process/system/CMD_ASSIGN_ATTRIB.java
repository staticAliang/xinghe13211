package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 加点
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_ASSIGN_ATTRIB implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		Chara chara = GameObjectChar.getGameObjectChar().chara;
//		if(GameCommonUtil.rejectRequestTimeOut(chara, "CHARA_ADD_POINT", "操作频繁", 1)) {
//			return;
//		}
		int id = GameReadTool.readInt(buff);
		int type = GameReadTool.readByte(buff);
		int para1 = GameReadTool.readShort(buff);
		int para2 = GameReadTool.readShort(buff);
		int para3 = GameReadTool.readShort(buff);
		int para4 = GameReadTool.readShort(buff);
		int para5 = GameReadTool.readShort(buff);
		int para6 = GameReadTool.readShort(buff);
		log.info("客户端请求加点,para6={}",para6);
		//需要洗点总点数
		int subAllPoint = 0;
		if (para1 > 3000) {
			para1 -= 65536;
			subAllPoint += Math.abs(para1);
		}
		if (para2 > 3000) {
			para2 -= 65536;
			subAllPoint += Math.abs(para2);
		}
		if (para3 > 3000) {
			para3 -= 65536;
			subAllPoint += Math.abs(para3);
		}
		if (para4 > 3000) {
			para4 -= 65536;
			subAllPoint += Math.abs(para4);
		}
		if (para5 > 3000) {
			para5 -= 65536;
			subAllPoint += Math.abs(para5);
		}
		int fen = 0;
		if (id == 0) {
			if (type == 1) {
				fen = 59;
			} else {
				fen = 164;
			}
		} else {
			fen = 36;
		}
		if(id == 0) {
			if(type == 1) {
				Map<String,Object> obj = new HashMap<>();
				obj.put("con", chara.life+para1);
				obj.put("wiz", chara.mag_power+para1);
				obj.put("str", chara.phy_power+para3);
				obj.put("dex", chara.speed+para4);
				GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
			}else if(type == 2) {
				Map<String,Object> obj = new HashMap<>();
				obj.put("metal", chara.metal+para1);
				obj.put("wood", chara.wood+para2);
				obj.put("water", chara.water+para3);
				obj.put("fire", chara.fire+para4);
				obj.put("earth", chara.earth+para5);
				GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
			}
		}
		
		int zong = para1 + para2 + para3 + para4 + para5;
		if (zong < 0) {
			if (chara.goldCoin < zong * -fen) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "元宝不足";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			} else {
				Chara chara2 = chara;
				chara2.goldCoin -= -zong * fen;
				Map<String,Object> obj = new HashMap<>();
				obj.put("gold_coin", chara2.goldCoin);
				GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
			}
		}
		if (id == 0) {
			int count4 = para1 + para2 + para3 + para4 + para5;
			if (type == 1) {
				int point = chara.attribPoint+subAllPoint;
				// 洗点计算是否还满足
				if (count4 < 0) {
					int maxPoint = (chara.level * 10)-4;
					if (chara.upgrade_level > 0) {
						// 飞升了.
						maxPoint += (chara.upgrade_level / 10)*10;
					}
					if ((chara.attribPoint - count4) > maxPoint) {
						GameUtil.sendMeTips("可洗点的点数不足");
						return;
					} else {
						maxPoint += count4;
						if (maxPoint < 0) {
							GameUtil.sendMeTips("可洗点的点数超出范围");
							return;
						}
					}
				} else {
					if (para1 > count4 || para2 > point ||
							para3 > point || para4 > point) {
						Vo_20481_0 vo_20481_2 = new Vo_20481_0();
						vo_20481_2.msg = "剩余点数不足";
						vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_2);
						return;
					}
					if(count4>chara.attribPoint) {
						GameUtil.sendMeTips("剩余点数不足");
						return;
					}
				}
				if(chara.life+para1<0) {
					GameUtil.sendMeTips("剩余点数不足");
					return;
				}else if(chara.mag_power+para2<0) {
					GameUtil.sendMeTips("剩余点数不足");
					return;
				}else if(chara.phy_power+para3<0) {
					GameUtil.sendMeTips("剩余点数不足");
					return;
				}else if(chara.speed+para4<0) {
					GameUtil.sendMeTips("剩余点数不足");
					return;
				}
				chara.life += para1;
				chara.mag_power += para2;
				chara.phy_power += para3;
				chara.speed += para4;
				chara.attribPoint -= count4;
			} else if (type == 2) {
				if (count4 > 0) {
					//角色相性点最大上限
					int charaSingle = 30+chara.upgrade_max_polar_extra;
					// 金相性
					if ((chara.metal + para1) > charaSingle) {
						GameUtil.sendMeTips("金相性已达上限");
						return;
					}
					// 木
					if ((chara.wood + para2) > charaSingle) {
						GameUtil.sendMeTips("木相性已达上限");
						return;
					}
					// 水
					if ((chara.water + para3) > charaSingle) {
						GameUtil.sendMeTips("水相性已达上限");
						return;
					}
					// 火
					if ((chara.fire + para4) > charaSingle) {
						GameUtil.sendMeTips("火相性已达上限");
						return;
					}
					// 土
					if ((chara.earth + para5) > charaSingle) {
						GameUtil.sendMeTips("土相性已达上限");
						return;
					}
				}
				//如果为0,并且其中一个为负数、表示洗点和加点一起
				int point = chara.polarPoint;
				if(count4 == 0 && (para1<0||para2<0||para3<0||para4<0||para5<0)){
					if(para1>0) {
						count4+=para1;
					}else if(para2>0) {
						count4+=para2;
					}else if(para3>0) {
						count4+=para3;
					}else if(para4>0) {
						count4+=para4;
					}else if(para5>0) {
						count4+=para5;
					}
					point = count4;
					chara.polarPoint+=count4;
				}
				if (para1 > point || para2 > point ||
						para3 > point || para4 > point || para5 > point) {
					Vo_20481_0 vo_20481_3 = new Vo_20481_0();
					vo_20481_3.msg = "可用相性点不足";
					vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_3);
					return;
				} else {
					if(chara.metal+para1<0) {
						GameUtil.sendMeTips("剩余相性点不足");
						chara.polarPoint -= count4;
						return;
					}else if(chara.wood+para2<0) {
						GameUtil.sendMeTips("剩余相性点不足");
						chara.polarPoint -= count4;
						return;
					}else if(chara.water+para3<0) {
						GameUtil.sendMeTips("剩余相性点不足");
						chara.polarPoint -= count4;
						return;
					}else if(chara.fire+para4<0) {
						GameUtil.sendMeTips("剩余相性点不足");
						chara.polarPoint -= count4;
						return;
					}else if(chara.earth+para5<0) {
						GameUtil.sendMeTips("剩余相性点不足");
						chara.polarPoint -= count4;
						return;
					}
					chara.metal += para1;
					chara.wood += para2;
					chara.water += para3;
					chara.fire += para4;
					chara.earth += para5;
					chara.polarPoint -= count4;
				}
			}
			ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), vo_65527_0);
		} else {
			int count3 = para1 + para2 + para3 + para4 + para5;
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.id == id) {
					PetShuXing petShuXing = petbeibao.petShuXing.get(0);
					int count5 = para1 + para2 + para3 + para4 + para5;
					if ((petShuXing.polar_point != 0 || count3 <= 0) && count3 <= petShuXing.polar_point
							&& petShuXing.skill <= petShuXing.life + para1
							&& petShuXing.skill <= petShuXing.phy_power + para3
							&& petShuXing.skill <= petShuXing.speed + para4) {
						if (petShuXing.skill <= petShuXing.mag_power + para2) {
							// TODO: 2020/3/4 这里需要加上亲密度的复活效果，以及其他buff
							// 分为两步计算加点的值，先计算原始的值，再计算增加后的值，最后做增量增加
							int deltArray1[] = PetAttributesUtils.petAttributes(false, petShuXing.skill,
									petShuXing.life, petShuXing.mag_power, petShuXing.phy_power, petShuXing.speed,
									petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_phy_shape,
									petShuXing.pet_mag_shape, petShuXing.rank);
							int bfDef = petShuXing.def; // 气血
							int bfDex = petShuXing.dex; // 法力
							int bfAccuate = petShuXing.accurate; // 物伤
							int bfMana = petShuXing.mana; // 法伤
							int bfParry = petShuXing.parry; // 速度
							int bfWiz = petShuXing.wiz;

							petShuXing.life += para1; // 增加体质
							petShuXing.mag_power += para2; // 增加灵力
							petShuXing.phy_power += para3; // 增加力量
							petShuXing.speed += para4; // 增加敏捷
							petShuXing.polar_point -= count5; // 从总的相性点中扣除使用的相性点

							// 这里不能用BasicAttributesUtils.petshuxing(petShuXing)，否则会覆盖掉亲密度加上的伤害
							int deltArray2[] = PetAttributesUtils.petAttributes(false, petShuXing.skill,
									petShuXing.life, petShuXing.mag_power, petShuXing.phy_power, petShuXing.speed,
									petShuXing.pet_mana_shape, petShuXing.pet_speed_shape, petShuXing.pet_phy_shape,
									petShuXing.pet_mag_shape, petShuXing.rank);
							if (petShuXing.max_life >= petShuXing.def) {
								petShuXing.max_life = petShuXing.def;
							}
							if (petShuXing.max_mana >= petShuXing.dex) {
								petShuXing.max_mana = petShuXing.dex;
							}
							petShuXing.def = bfDef + (deltArray2[0] - deltArray1[0]); // 气血
							petShuXing.dex = bfDex + (deltArray2[1] - deltArray1[1]); // 法力
							petShuXing.accurate = bfAccuate + (deltArray2[2] - deltArray1[2]); // 物伤
							petShuXing.mana = bfMana + (deltArray2[3] - deltArray1[3]); // 法伤
							petShuXing.parry = bfParry + (deltArray2[4] - deltArray1[4]); // 速度
							petShuXing.wiz = bfWiz + (deltArray2[5] - deltArray1[5]); // 防御
							List<Petbeibao> list = new ArrayList<>();
							list.add(petbeibao);
							GameObjectChar.send(new MSG_UPDATE_PETS(), list);
						}
					}
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 8254;
	}
}