package com.fengshen.server.data.write;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_65527_0;
import com.fengshen.server.data.vo.weddingBook.Vo_WB_HOME_INFO;
import com.fengshen.server.data.write.weddingBook.MSG_WB_HOME_INFO;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SkillCost;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 查看名片
 */
@Service
@Slf4j
public class MSG_CARD_INFO extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		String type = (String) object;
		if (type != null && !type.startsWith("集市=")) {
			//参数
			String jsonParams = type.split("\\|")[1];
			//聊天发送名片就少了}所以这里加上方便解析
			String json = jsonParams+"}";
			JSONObject js = JSONObject.parseObject(json);
			int fromCharaId = js.getIntValue("id");
			GameObjectChar fromfromGameObjectChar = GameObjectCharMng.getGameObjectChar(fromCharaId);
			if (fromfromGameObjectChar == null) {
				GameUtil.sendMeTips("该链接已失效");
				return;
			}
			Chara fromChara = fromfromGameObjectChar.chara;
			GameWriteTool.writeString(buff, type);
			String als = type.substring(0, type.indexOf("="));
			if ("宠物".equals(als)) {
				// 新版
				String petId = type.split("=")[1].split("\\|")[0];
				GameWriteTool.writeString(buff, "宠物");
				// 去用户仓库查找对应的数据
				List<Petbeibao> pets = fromChara.pets;
				for (Petbeibao list : pets) {
					if (list.petShuXing.get(0).auto_fight.equals(petId)) {
						GameWriteTool.writeShort(buff, list.petShuXing.size());
						final PetShuXing petShuXing = list.petShuXing.get(0);
						GameWriteTool.writeByte(buff, list.petShuXing.get(0).no);
						GameWriteTool.writeByte(buff, list.petShuXing.get(0).type1);
						Map<Object, Object> map = new HashMap<Object, Object>();
						map = UtilObjMapshuxing.PetShuXing(petShuXing, fromChara.name);
						if(!petShuXing.getStr().equals(petShuXing.getSuit_polar())) {
							map.put("str", petShuXing.getSuit_polar());
						}
						map.remove("no");
						map.remove("type1");
						GameWriteTool.writeShort(buff, map.size());
						for (final Map.Entry<Object, Object> entry : map.entrySet()) {
							if (BuildFields.data.get(entry.getKey()) != null) {
								BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
							} else {
								log.info((String)entry.getKey());
							}
						}
						log.info("找到宠物名片, id:{},name:{},master:{}", list.id, list.petShuXing.get(0).str,
								fromChara.name);
						break;
					}
				}
			} else if ("道具".equals(als)) {
				String autoId = type.split("=")[1].split("\\|")[0];
				Goods goods = null;
				// 去用户仓库查找对应的数据
				List<Goods> goodss = new ArrayList<>();
				goodss.addAll(fromChara.backpack);
				goodss.addAll(fromChara.otherGoods);
				goodss.addAll(fromChara.customShizhuang);
				goodss.addAll(fromChara.shizhuang);
				goodss.addAll(fromChara.cardStore);
				goodss.addAll(fromChara.texiao);
				goodss.addAll(fromChara.teamIconStore);
				goodss.addAll(fromChara.genchong);
				goodss.addAll(fromChara.tyzqStore);
				for (Goods g : goodss) {
					if (g.goodsInfo.auto_fight.equals(autoId)) {
						goods = g;
						break;
					}
				}
				if(goods == null) {
					GameUtil.sendMeTips("该链接已失效");
					return;
				}
				GameWriteTool.writeString(buff, "道具");
				Map<Object, Object> map = new HashMap<Object, Object>();
				GameWriteTool.writeShort(buff, 10);
				if(goods.goodsInfo.amount != 0) {
					if (goods.goodsInfo != null) {
						map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
						map.remove("groupNo");
						map.remove("groupType");
						GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
						GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
						final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
						while (it.hasNext()) {
							final Map.Entry<Object, Object> entry = it.next();
							if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
								it.remove();
							}
							if (entry.getValue().equals(0) && entry.getKey().equals("pot")) {
								it.remove();
							}
						}
						GameWriteTool.writeShort(buff, map.size());
						for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
							if (BuildFields.data.get(entry2.getKey()) != null) {
								BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
							} else {
								System.out.println(entry2.getKey());
							}
						}
					}
					GameCommonUtil.goodsCreate(buff, goods);
				}else {
					log.info("商城道具");
					if (goods.goodsInfo != null) {
						map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
						map.remove("groupNo");
						map.remove("groupType");
						GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
						GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
						final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
						while (it.hasNext()) {
							final Map.Entry<Object, Object> entry = it.next();
							if (entry.getValue().equals(0)) {
								it.remove();
							}
						}
						GameWriteTool.writeShort(buff, map.size());
						for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
							if (BuildFields.data.get(entry2.getKey()) != null) {
								BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
							} else {
								System.out.println(entry2.getKey());
							}
						}
					}
					GameCommonUtil.goodsCreate(buff, goods);
				}
			} else if ("称谓".equals(als)) {
				String para = type.split("=")[1].split("\\|")[0];
				String name = fromChara.chenghao.get(para);
				if(name == null) {
					GameUtil.sendMeTips("该链接已失效");
					return;
				}
				GameWriteTool.writeString(buff, "称谓");//
				GameWriteTool.writeString(buff, para);//称谓类型
				GameWriteTool.writeString(buff, name); //title
			} else if (type.indexOf("角色") != -1) {
				GameWriteTool.writeString(buff, "角色");
				Vo_65527_0 vo_65527_0 = GameUtil.a65527(fromChara).vo_65527_0;
				if(fromChara.upgrade_state != 0) {
					//真身等级修改
					vo_65527_0.skill = fromChara.realLevel;
				}
				Map<Object, Object> map = UtilObjMap.userCardNew(vo_65527_0,
						fromChara);
				GameWriteTool.writeShort(buff, map.size());
				for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFieldsNew.data.get(entry2.getKey()) != null) {
						BuildFieldsNew.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						log.info((String) entry2.getKey());
					}
				}
			}else if("今日统计".equals(als)) {
				GameWriteTool.writeString(buff, "今日统计"); // 来源
				//写入数据
				Map<String, Integer> dayDataCount = GameCommonUtil.toDayDataCount(fromChara);
				GameWriteTool.writeInt(buff, dayDataCount.get("exp")); //exp
				GameWriteTool.writeInt(buff, dayDataCount.get("tao")); //tao
				GameWriteTool.writeInt(buff, 0); //tao_point
				GameWriteTool.writeInt(buff, dayDataCount.get("monTao")); //mon_tao
				GameWriteTool.writeInt(buff, 0); //mon_tao_ex
				GameWriteTool.writeInt(buff, dayDataCount.get("pot")); //pot
				GameWriteTool.writeInt(buff, dayDataCount.get("death")); //death
				GameWriteTool.writeInt(buff, (int)(fromChara.online_time / 1000L + (System.currentTimeMillis() - fromChara.uptime) / 1000L)); //onLine_time
				GameWriteTool.writeInt(buff, dayDataCount.get("shuadaoTimes")); //shuadaoTimes
				GameWriteTool.writeInt(buff, fromChara.getWaiguan()); //org_icon
				GameWriteTool.writeShort(buff, fromChara.getLevel()); //level
				GameWriteTool.writeString(buff, fromChara.getName());
				GameWriteTool.writeString(buff, fromChara.getPartyName());
				
				GameWriteTool.writeInt(buff, 0); //double_point
				GameWriteTool.writeInt(buff, fromChara.enable_double_points); //double_point_max
				GameWriteTool.writeInt(buff, 0); //chongfx_point
				GameWriteTool.writeInt(buff, fromChara.shuadaochongfeng_san); //chongfx_point_max
				GameWriteTool.writeInt(buff, 0); //jiji_point
				GameWriteTool.writeInt(buff, fromChara.jijirulvling); //jiji_point_max
				GameWriteTool.writeInt(buff, 0); //zqhm_point
				GameWriteTool.writeInt(buff, fromChara.ziqihongmeng); //zqhm_point_max
				
			}else if("任务".equals(als)) {
				//任务名片
				String para = type.split("=")[1].split("\\|")[0].trim();
				Map<String, Vo_61553_0> commonTaskMap = fromChara.commonTaskMap;
				Vo_61553_0 task = commonTaskMap.get(para);
				if(task == null) {
					//去其他任务中查找
					task = fromChara.taskMap.get(para);
				}
				if(task != null) {
					GameWriteTool.writeString(buff, "任务");
					GameWriteTool.writeShort(buff, task.count);
					GameWriteTool.writeString(buff, task.task_type);
					GameWriteTool.writeString2(buff, task.task_desc);
					GameWriteTool.writeString2(buff, task.task_prompt);
					GameWriteTool.writeShort(buff, task.refresh);
					GameWriteTool.writeInt(buff, task.task_end_time);
					GameWriteTool.writeShort(buff, task.attrib);
					GameWriteTool.writeString2(buff, task.reward);
					GameWriteTool.writeString(buff, task.show_name);
					GameWriteTool.writeString(buff, task.task_extra_para);
					GameWriteTool.writeString(buff, task.task_state);
				}else {
					GameUtil.sendMeTips("该链接已失效");
				}
			}else if("技能".equals(als)) {
				String para = type.split("=")[1].split("\\|")[0].trim();
				GameWriteTool.writeString(buff, "技能");
				GameWriteTool.writeShort(buff, 1);
				for(JiNeng jineng:fromChara.jiNengList) {
					if(jineng.skill_no == Integer.valueOf(para)) {
			            GameWriteTool.writeShort(buff, jineng.skill_no);
			            GameWriteTool.writeShort(buff, jineng.skill_attrib1);
			            GameWriteTool.writeShort(buff, jineng.skill_level+jineng.level_improved);
			            GameWriteTool.writeShort(buff, jineng.level_improved);
			            GameWriteTool.writeShort(buff, jineng.skill_mana_cost);
			            GameWriteTool.writeInt(buff, jineng.skill_nimbus);
			            GameWriteTool.writeByte(buff, jineng.skill_disabled);
			            GameWriteTool.writeShort(buff, jineng.range);
			            GameWriteTool.writeShort(buff, jineng.max_range);
			            if(jineng.count1>0) {
			            	GameWriteTool.writeShort(buff, jineng.count1);
			            	GameWriteTool.writeString(buff, jineng.s1);
			        		GameWriteTool.writeInt(buff, jineng.s2);
			            }else {
			            	 GameWriteTool.writeShort(buff, jineng.skillCost.size());
			                 for(SkillCost cost:jineng.skillCost) {
			             		GameWriteTool.writeString(buff, cost.s1);
			             		GameWriteTool.writeInt(buff, cost.s2);
			                 }
			            }
			            GameWriteTool.writeByte(buff, 0);
						break;
					}
				}
			}else if("结婚纪念册".equals(als)) {
				//结婚纪念册
				Vo_WB_HOME_INFO homeInfo = new Vo_WB_HOME_INFO();
				homeInfo.setBookId("AA15545545125");
				homeInfo.setWedding_start_ti((int) (fromfromGameObjectChar.chara.marriageTime/1000L));
				homeInfo.setWedding_end_ti(0);
				homeInfo.setHus_name(fromfromGameObjectChar.chara.name);
				homeInfo.setWife_name(fromfromGameObjectChar.chara.marriageName);
				homeInfo.setHome_img("");
				GameObjectChar.send(new MSG_WB_HOME_INFO(), homeInfo);
			}else if("附灵阵".equals(als)) {
				GameWriteTool.writeString(buff, als);
				GameWriteTool.writeByte(buff, fromChara.zhenlingStage==0?1:fromChara.zhenlingStage);
				GameWriteTool.writeByte(buff, fromChara.zhenlingLevel==0?1:fromChara.zhenlingStage);
				GameWriteTool.writeByte(buff, fromChara.qinglongZhenlingLevel==0?1:fromChara.qinglongZhenlingLevel);
				GameWriteTool.writeByte(buff, fromChara.baihuhenlingLevel==0?1:fromChara.baihuhenlingLevel);
				GameWriteTool.writeByte(buff, fromChara.zhuqueZhenlingLevel==0?1:fromChara.zhuqueZhenlingLevel);
				GameWriteTool.writeByte(buff, fromChara.xuanwuZhenlingLevel==0?1:fromChara.xuanwuZhenlingLevel);
			}
		}else {
			//集市
			log.info("打开集市货物");
		}
	}

	@Override
	public int cmd() {
		return 0xA002;
	}

}
