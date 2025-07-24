package com.fengshen.web.controller.sys.xiaozi;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.server.process.xiaozi.CMD_XIAOZI_AUTO_WABAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.HttpUtils;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.AccessibilityMap;
import com.fengshen.db.domain.CharaNickname;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.M16431_0;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.process.CommonCmd;
import com.fengshen.server.process.xiaozi.CMD_XIAOZI_AUTO_WALK;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 问道小子控制
 *
 *
 *
 */
@RestController
@RequestMapping("/wd/xiaozi")
@Slf4j
public class WdXiaoZiController extends BaseController {

	//问道小子自动任务
	public static HashMap<Integer,String> autoMap =  new HashMap<>() ;


	@PostMapping("/wdXiaoZiOp")
	public ResponseView wdXiaoZiOp(@RequestParam Map<String, Object> data) {
		String type = MapUtils.getString(data, "type");
		String uuid = MapUtils.getString(data, "uuid");
		log.info("type："+type+"uuid:"+uuid);
		if (StringUtils.isNullOrEmpty(type)) {
			ResponseView.fail("操作类型为空");
		}
		if (StringUtils.isNullOrEmpty(type)) {
			ResponseView.fail("uui为空");
		}

		List<GameObjectChar> gameObjects = new ArrayList<>();
		Object isAll = data.get("isAll");
		if (isAll == null) {
			if ("useName".equals(uuid)) {
				// 使用名字
				gameObjects.add(GameObjectCharMng.getGameObjectChar((String) data.get("value")));
			} else {
				gameObjects.add(GameObjectCharMng.getGameObjectCharByUUid(uuid));
			}
		} else {
			// 对集体问道小子发起指令
			List<GameObjectChar> all = GameObjectCharMng.getAll();
			for (GameObjectChar a : all) {
				if (a.characters.getXiaozi() != null && a.characters.getXiaozi() == 1) {
					gameObjects.add(a);
				}
			}
		}
		int index = 1;
		for (GameObjectChar gameObject : gameObjects) {
			if ("builderWdXiaozi".equals(type)) {
				Chara chara = null;
				if (gameObject == null) {
					// 去数据库查询
					Characters characters = GameData.that.baseCharactersService.findOneByGid2(uuid);
					String jsonData = characters.getData();
					chara = JSONObject.parseObject(jsonData, Chara.class);
					chara.setGenchong(JSONObject.parseArray(characters.getGenchong(), Goods.class));
					chara.setBackpack(JSONObject.parseArray(characters.getBackpack(), Goods.class));
					// 查询出当前用户的宠物
					CharaPet petsByCid = GameData.that.charaPetService.getPetById(chara.chongwuchanzhanId);
					List<Petbeibao> petbeibaos = new ArrayList<>();
					if (petsByCid != null) {
						petbeibaos.add(JSONObject.parseObject(petsByCid.getPet(), Petbeibao.class));
					}
					chara.setPets(petbeibaos);
				}else {
					chara = com.fengshen.server.util.BeanUtils.clone(gameObject.chara);
					chara.setCangku(null);
					chara.setCardStore(null);
					chara.setCustomShizhuang(null);
					chara.setTyzqStore(null);
					chara.setListshouhu(null);
					chara.setBackpack(chara.otherGoods);
					chara.setOtherGoods(null);
				}
				// 根据角色查询出他所有的背包信息.
				List<Goods> godss = new ArrayList<>();
				for (Goods g : chara.backpack) {
					if (g.pos > 0 && g.pos < 11) {
						godss.add(g);
					}
					if(g.pos == 40) {
						godss.add(g);
					}
				}
				chara.setBackpack(godss);
				// 构建问道小子
				return ResponseView.ok(chara);
			}
			if (gameObject == null && data.get("isAll") == null) {
				ResponseView.fail("角色不在线");
			}
			Chara chara = gameObject.chara;
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			if ("createTeam".equals(type)) {
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					ResponseView.fail("你已有队伍");
				}
				// 创建队伍
				map.put("peer_name:str", chara.getName());
				map.put("ud:int", chara.id);
				map.put("asktype:str", "request_join");
				gameObject.sendOne(new CommonCmd(0x103C), map);
			} else if ("jiesanTeam".equals(type)) {
				gameObject.sendOne(new CommonCmd(26), null);
			} else if ("posi".equals(type)) {
				// 如果有队伍
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					// 如果自己不是队长
					if (gameObject.gameTeam.duiwu.get(0).id != gameObject.chara.id) {
						ResponseView.fail("既然是集体行动，还是老老实实听从队长指挥");
					}
				}
				// 移动问道小子
				String value = MapUtils.getString(data, "value");
				String typeX = "";
				String typeY = "";
				if ("up".equals(value)) {
					chara.x += 1;
					chara.y -= 1;
					typeX = "X-";
					typeY = "Y+";
					map.put("dir:short", 4);
				} else if ("down".equals(value)) {
					chara.x -= 1;
					chara.y += 1;
					typeX = "X+";
					typeY = "Y-";
					map.put("dir:short", 3);
				} else if ("left".equals(value)) {
					chara.y -= 1;
					chara.x -= 1;
					typeX = "X+";
					typeY = "Y+";
					map.put("dir:short", 2);
				} else if ("right".equals(value)) {
					chara.y += 1;
					chara.x += 1;
					typeX = "X-";
					typeY = "Y-";
					map.put("dir:short", 1);
				}

				// 这里是让队友跟随队长的位置
				if (gameObject.gameTeam != null && gameObject.gameTeam.duiwu != null
						&& gameObject.gameTeam.duiwu.size() > 0) {
					for (int j = 0; j < gameObject.gameTeam.duiwu.size(); ++j) {
						Chara chara2 = gameObject.gameTeam.duiwu.get(j);
						chara2.x = chara.x;
						chara2.y = chara.y;
						chara2.dir = chara.dir;
						if (chara2.id != gameObject.chara.id) {
							Vo_16431_0 vo_16431_0 = new Vo_16431_0();
							vo_16431_0.id = chara2.id;
							if ("X+".equals(typeX)) {
								vo_16431_0.x = chara.x + (j + 1);
							} else {
								vo_16431_0.x = chara.x - (j + 1);
							}
							if ("Y+".equals(typeY)) {
								vo_16431_0.y = chara.y + (j + 1);
							} else {
								vo_16431_0.y = chara.y - (j + 1);
							}
							GameObjectCharMng.getGameObjectChar(chara2.id).gameMap.send(new M16431_0(), vo_16431_0);
						}
					}
				}
				Vo_16431_0 vo_16431_0 = new Vo_16431_0();
				vo_16431_0.id = gameObject.chara.id;
				vo_16431_0.x = chara.x;
				vo_16431_0.y = chara.y;
				// 通知该地图所有玩家
				GameObjectCharMng.getGameObjectChar(gameObject.chara.id).gameMap.send(new M16431_0(), vo_16431_0);
				return ResponseView.ok(map);
			} else if ("move".equals(type)) {
				// 如果有队伍
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					// 如果自己不是队长
					if (gameObject.gameTeam.duiwu.get(0).id != gameObject.chara.id) {
						ResponseView.fail("既然是集体行动，还是老老实实听从队长指挥");
					}
				}
				Integer x = MapUtils.getInteger(data, "x");
				Integer y = MapUtils.getInteger(data, "y");
				if (gameObject.gameTeam != null && gameObject.gameTeam.duiwu != null
						&& gameObject.gameTeam.duiwu.size() > 0) {
					for (int j = 0; j < gameObject.gameTeam.duiwu.size(); ++j) {
						Chara chara2 = gameObject.gameTeam.duiwu.get(j);
						chara2.x = x;
						chara2.y = y;
						chara2.dir = chara.dir;
						if (chara2.id != gameObject.chara.id) {
							Vo_16431_0 vo_16431_0 = new Vo_16431_0();
							vo_16431_0.id = chara2.id;
							vo_16431_0.x = x - 2;
							vo_16431_0.y = y - 2;
							GameObjectCharMng.getGameObjectChar(chara2.id).gameMap.send(new M16431_0(), vo_16431_0);
						}
					}
				}

				Vo_16431_0 vo_16431_0 = new Vo_16431_0();
				vo_16431_0.id = gameObject.chara.id;
				vo_16431_0.x = x;
				vo_16431_0.y = y;
				gameObject.chara.x = x;
				gameObject.chara.y = y;
				// 通知该地图所有玩家
				GameObjectCharMng.getGameObjectChar(gameObject.chara.id).gameMap.send(new M16431_0(), vo_16431_0);
			} else if ("script".equals(type)) {
				// 如果有队伍
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					// 如果自己不是队长
					if (gameObject.gameTeam.duiwu.get(0).id != gameObject.chara.id) {
						ResponseView.fail("既然是集体行动，还是老老实实听从队长指挥");
					}
				}
				// 如果是很多人的话,每个人都延迟一下
//				if (index > 1) {
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				// 执行脚本
				String value = MapUtils.getString(data, "value");
				map = new LinkedHashMap<String, Object>();
				map.put("dest:str", value);
				map.put("type:str", "");
				gameObject.sendOne(new CommonCmd(45063), map);

				String executeType = MapUtils.getString(data, "executeType");
				map = new LinkedHashMap<String, Object>();
				map.put("action:str", executeType);
				gameObject.sendOne(new CommonCmd(9222), map);
				index++;
			} else if ("reqeust_join".equals(type)) {// 加入队伍
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					ResponseView.fail("你已有队伍");
				}
				// 判断对方是否有队伍
				String value = MapUtils.getString(data, "value");
				GameObjectChar forRequestJoinGameObject = GameObjectCharMng.getGameObjectChar(value);
				if (forRequestJoinGameObject == null) {
					ResponseView.fail("对方不在线");
				} else if (!GameCommonUtil.isNotGameTeam(forRequestJoinGameObject.gameTeam)) {
					ResponseView.fail("对方还没队伍呢？");
				} else if (forRequestJoinGameObject.gameTeam.duiwu.size() >= 5) {
					ResponseView.fail("对方队伍人数已满！");
				}
				// 获取队伍队长
				Chara leaderTeam = forRequestJoinGameObject.gameTeam.duiwu.get(0);
				// 加入队伍的队长名称
				String peer_name = leaderTeam.name;
				// 队长id
				int id = leaderTeam.id;
				// 请求类型
				String ask_type = "request_join";
				map.put("peer_name:str", peer_name);
				map.put("id:int", id);
				map.put("asktype:str", ask_type);
				gameObject.sendOne(new CommonCmd(0x103C), map);
			} else if ("comment_send".equals(type)) {// 发送消息
				String value = MapUtils.getString(data, "value");
				String[] msgs = value.split("#I");
				int index1 = (int) (Math.random() * msgs.length);
				String msg = msgs[index1];
				int msgType = MapUtils.getIntValue(data, "msgType");
				if (msgType == 5) {
					// 判断是否有加入帮派
					if (StringUtils.isNullOrEmpty(gameObject.chara.getPartyName())) {
						ResponseView.fail("开什么玩笑，你还未加入帮派呢！！！");
					}
				} else if (msgType == 4) {
					if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
						ResponseView.fail("开什么玩笑，你还未加入队伍呢！！！");
					}
				}
				map.put("channel:short", msgType);
				map.put("msg:str", msg);
				// 聊天消息
				gameObject.sendOne(new CommonCmd(16482), map);
			} else if ("showAppliTeams".equals(type)) {
				// 查看申请列表
				if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					ResponseView.fail("开什么玩笑，你还未创建队伍呢！！！");
				} else if (gameObject.gameTeam.duiwu.get(0).id != gameObject.chara.id) {
					ResponseView.fail("既然是集体行动，还是老老实实听从队长指挥");
				} else if (gameObject.gameTeam.duiwu.size() >= 5) {
					ResponseView.fail("队伍人数已满");
				}
				// 返回申请列表
				List<List<Chara>> liebiao = gameObject.gameTeam.liebiao;
				List<Map<String, Object>> applyChara = new ArrayList<>();
				for (List<Chara> c : liebiao) {
					for (Chara ch : c) {
						Map<String, Object> cj = new HashMap<>();
						cj.put("id", ch.id);
						cj.put("name", ch.name);
						cj.put("partyName", ch.getPartyName());
						cj.put("uuid", ch.uuid);
						applyChara.add(cj);
					}
				}
				return ResponseView.ok(applyChara);
			} else if ("acceptJoin".equals(type)) {
				// 同意某人加入
				String value = MapUtils.getString(data, "value");
				GameObjectChar forRequestJoinGameObject = GameObjectCharMng.getGameObjectChar(value);
				if (forRequestJoinGameObject == null) {
					ResponseView.fail("对方不在线");
				} else if (GameCommonUtil.isNotGameTeam(forRequestJoinGameObject.gameTeam)) {
					// 发送提示
					GameTeam gameTeam = gameObject.gameTeam;
					List<List<Chara>> list = gameObject.gameTeam.liebiao;
					for (List<Chara> charas : list) {
						Iterator<Chara> iterator = charas.iterator();
						while (iterator.hasNext()) {
							Chara chara2 = iterator.next();
							if (chara2.getName().equals(value)) {
								iterator.remove();
								break;
							}
						}
					}
					// 如果全部清空的话就设置为null
					if (gameTeam.liebiao.size() == 0) {
						gameTeam.liebiao.clear();
					}
					ResponseView.fail("对方有队伍");
				}
				// 加入队伍请求
				map = new LinkedHashMap<String, Object>();
				map.put("peer_name:str", value);
				map.put("askType:str", "request_join");
				gameObject.sendOne(new CommonCmd(4132), map);
			} else if ("rejectJoin".equals(type)) {
				// 拒绝某人加入,这里无需通知客户端.服务端直接操作就好
				Integer toCharaId = null;
				String value = MapUtils.getString(data, "value");
				GameObjectChar forRequestJoinGameObject = GameObjectCharMng.getGameObjectChar(value);
				if (forRequestJoinGameObject == null) {
					ResponseView.fail("对方不在线");
				}
				// 发送提示
				GameTeam gameTeam = gameObject.gameTeam;
				List<List<Chara>> list = gameObject.gameTeam.liebiao;
				for (List<Chara> charas : list) {
					Iterator<Chara> iterator = charas.iterator();
					while (iterator.hasNext()) {
						Chara chara2 = iterator.next();
						if (chara2.getName().equals(value)) {
							toCharaId = chara2.id;
							iterator.remove();
							break;
						}
					}
				}
				// 如果全部清空的话就设置为null
				if (gameTeam.liebiao.size() == 0) {
					gameTeam.liebiao.clear();
				}
				GameCommonUtil.dialogOk("#Y" + gameObject.chara.name + "#n拒绝了你的申请！", toCharaId);
			} else if ("autoTask".equals(type)) {

				String value = MapUtils.getString(data, "value");
				gameObject.action = value;
				// 通知客户端当前任务


				if ("".equals(value)) {
					// 取消自动任务
					if (CMD_XIAOZI_AUTO_WALK.xiaoziAutoTimer != null) {
						CMD_XIAOZI_AUTO_WALK.xiaoziAutoTimer.cancel();
						autoMap.remove(chara.id);
					}
				}
				log.info("这里判断是否是寻宝："+value);
				if ("wabao".equals(value)) {
					autoMap.put(chara.id,chara.name);
					ExecutorsUtils.getExecutorPools().execute(() -> {
						while(autoMap.containsKey(chara.id)){
							Map map2 = new LinkedHashMap<String, Object>();
							gameObject.sendOne(new CommonCmd(9333), map2);
							try {
								TimeUnit.SECONDS.sleep(30);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					});
				} else if ("chubao".equals(value)) {
					// 判断是否有队伍
					if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
						ResponseView.fail("请组队");
					}
					gameObject.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK("#P李总兵|M=dispatch_chubao#P", "除暴"));
				} else if ("chuyao".equals(value)) {
					// 判断是否有队伍
					if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
						ResponseView.fail("请组队");
					}
					if (GameUtil.duiwudengji80(chara, gameObject)) {
						ResponseView.fail("队伍中有80级及以上成员，无法继续进行！");
					}

					if (gameObject.gameTeam.duiwu.size() < GameConfig.XIANGYAO_NUM) {
						ResponseView.fail("人数不足" + GameConfig.XIANGYAO_NUM + "人！");
					}
					if (!GameUtil.judgeDuiyuanLevel(chara, gameObject, 45)) {
						ResponseView.fail("队伍中有不足45级的成员，无法继续进行！");
					}
					if (GameUtil.duiwudengji80(chara, gameObject)) {
						ResponseView.fail("队伍中有80级及以上成员，无法继续进行！");
					}

					gameObject.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK("#P通灵道人|M=dispatch_xiangy#P", "降妖"));
				} else if ("fumo".equals(value)) {
					// 判断是否有队伍
					if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
						ResponseView.fail("请组队");
					}

					if (gameObject.gameTeam.duiwu.size() < GameConfig.LY_SHUADAO_NUM) {
						ResponseView.fail("人数不足" + GameConfig.LY_SHUADAO_NUM + "人！");
					}
					if (GameUtil.duiwudengji120(chara, gameObject)) {
						ResponseView.fail("队伍中有120级及以上成员，无法继续进行！");
					}
					if (!GameUtil.duiwudengji80(chara, gameObject)) {
						ResponseView.fail("不满80级的成员，无法继续进行！");
					}
					if (!GameUtil.duiwudengji(chara, gameObject)) {
						ResponseView.fail("人物等级相差10级，不能接任务！");
					}

					gameObject.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK("#P陆压真人|M=领取任务#P", "伏魔"));
				} else if ("feixian".equals(value)) {
					// 判断是否有队伍
					if (!GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
						ResponseView.fail("请组队");
					}
					if (gameObject.gameTeam.duiwu.size() < GameConfig.LY_SHUADAO_NUM) {
						ResponseView.fail("人数不足" + GameConfig.LY_SHUADAO_NUM + "人！");
					}
					if (!GameUtil.duiwudengji120(chara, gameObject)) {
						ResponseView.fail("不满120级的成员，无法继续进行");
					}
					if (!GameUtil.duiwudengji(chara, gameObject)) {
						ResponseView.fail("人物等级相差10级，不能接任务！");
					}
					gameObject.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK("#P清微真人|M=飞仙渡邪_dispatch#P", "飞仙"));
				}
			} else if ("moveNpc".equals(type)) {
				// 如果有队伍
				if (GameCommonUtil.isNotGameTeam(gameObject.gameTeam)) {
					// 如果自己不是队长
					if (gameObject.gameTeam.duiwu.get(0).id != gameObject.chara.id) {
						ResponseView.fail("既然是集体行动，还是老老实实听从队长指挥");
					}
				}
				String value = MapUtils.getString(data, "value");
				Npc npc = GameData.that.baseNpcService.findOneByName(value);
				if (npc == null) {
					ResponseView.fail("没有找到该npc");
				}
				String typeX = "";
				String typeY = "";
				if (StringUtils.isNullOrEmpty(npc.getExt())) {
					typeX = "X+";
					typeY = "Y+";
				} else {
					if (npc.getExt().equals("右下")) {
						typeX = "X-";
						typeY = "Y+";
					} else if (npc.getExt().equals("左下")) {
						typeX = "X+";
						typeY = "Y-";
					} else if (npc.getExt().equals("右上")) {
						typeX = "X+";
						typeY = "Y+";
					} else if (npc.getExt().equals("左上")) {
						typeX = "X-";
						typeY = "Y-";
					}
				}
				int x = 0;
				int y = 0;
				if (gameObject.gameTeam != null && gameObject.gameTeam.duiwu != null
						&& gameObject.gameTeam.duiwu.size() > 0) {
					for (int j = 0; j < gameObject.gameTeam.duiwu.size(); ++j) {
						Chara chara2 = gameObject.gameTeam.duiwu.get(j);
						if (chara2.id != gameObject.chara.id) {
							if ("X+".equals(typeX)) {
								x = npc.getX() + (j + 1);
							} else {
								x = npc.getX() - (j + 1);
							}
							if ("Y+".equals(typeY)) {
								y = npc.getY() + (j + 1);
							} else {
								y = npc.getY() - (j + 1);
							}
						}
						chara2.x = x;
						chara2.y = y;
						chara2.dir = chara.dir;
					}
				} else {
					if ("X+".equals(typeX)) {
						x = npc.getX() + 1;
					} else {
						x = npc.getX() - 1;
					}
					if ("Y+".equals(typeY)) {
						y = npc.getY() + 1;
					} else {
						y = npc.getY() - 1;
					}
					chara.x = x;
					chara.y = y;
				}
				com.fengshen.db.domain.Map gameMap = GameData.that.baseMapService
						.findOneByMapId(npc.getMapId());
				map = new LinkedHashMap<String, Object>();
				map.put("dest:str", "#Z" + npc.getName() + "|" + gameMap.getName() + "(" + x + "," + y + ")" + "#Z");
				map.put("type:str", "");
				gameObject.sendOne(new CommonCmd(45063), map);
			} else if ("listerChara".equals(type)) {
				// 监听玩家坐标
				Map<String, Object> xy = new HashMap<>();
				xy.put("x", gameObject.chara.x);
				xy.put("y", gameObject.chara.y);
				xy.put("mapName", gameObject.chara.mapName);
				return ResponseView.ok(xy);
			} else if ("splitCharas".equals(type)) {
				// 分散玩家
//				Vo_16431_0 vo_16431_0 = new Vo_16431_0();
//				vo_16431_0.id = chara.id;
//				vo_16431_0.x = chara.x - index;
//				vo_16431_0.y = chara.y - index;
				index += 2;
				// 通知该地图所有玩家
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("int:id", gameObject.chara.id);
				gameMap.put("int:mapId", gameObject.chara.mapid);
				gameMap.put("int:index", 0);
				gameMap.put("short:count", 1);
				gameMap.put("short:x", chara.x - index);
				gameMap.put("short:y",chara.y - index);
				gameMap.put("int:dir",chara.dir);
				gameMap.put("int:sendTime",System.currentTimeMillis()/1000L);
				gameObject.sendOne(new CommonCmd(61634), gameMap);
			}else if ("randomMove".equals(type)) {
				//随机走动
				AccessibilityMap where = new AccessibilityMap();
				where.setMapName(chara.mapName);
				where.setX(10);
				List<AccessibilityMap> randomData = GameData.that.accessibilityMapService.getRandomData(where);
				AccessibilityMap accessibilityMap = randomData.get(ThreadLocalRandom.current().nextInt(randomData.size()));
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("int:id", gameObject.chara.id);
				gameMap.put("int:mapId", gameObject.chara.mapid);
				gameMap.put("int:index", 0);
				gameMap.put("short:count", 1);
				gameMap.put("short:x", accessibilityMap.getX());
				gameMap.put("short:y",accessibilityMap.getY());
				gameMap.put("int:dir",chara.dir);
				gameMap.put("int:sendTime",System.currentTimeMillis()/1000L);
				gameObject.sendOne(new CommonCmd(61634), gameMap);
			}
		}
		return ResponseView.ok();
	}

	@PostMapping("/getOnLineUsers")
	public ResponseView getOnLineUsers(Page<Characters> page, String name, String id) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("online desc");
		PageInfo<Characters> pageInfo = null;
		if(!StringUtils.isNullOrEmpty(id)) {
			pageInfo = new PageInfo<Characters>(GameData.that.characterService.findByAccountIdManage(Integer.valueOf(id)));
		}else {
			Example example = new Example(Characters.class);
			example.selectProperties("id","gid","online","block","level","goldCoin","polar","sex","portrait","name","chargeScore","x","y","mapName","accountId","shut","addTime");
			example.orderBy("online").desc();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andEqualTo("xiaozi", 1);
			if(!StringUtils.isNullOrEmpty(name)) {
				createCriteria.andLike("name", "%"+name+"%");
			}
			pageInfo = new PageInfo<Characters>(GameData.that.baseCharactersService.selectByExample(example));
		}
		//在线玩家
		long online = GameData.that.baseCharactersService.getOnlines();
		List<Map<String,Object>> chara = new ArrayList<>();
		List<Integer> onLineIds = new ArrayList<>();
		for(Characters c2:pageInfo.getList()) {
			Map<String,Object> map = new HashMap<>();
			map.put("id", c2.getId());
			map.put("uuid", c2.getGid());
			map.put("name", c2.getName());
			map.put("sex", c2.getSex()== 1?"男":"女");
			map.put("mapName", c2.getMapName());
			map.put("x", c2.getX());
			map.put("y", c2.getY());
			map.put("goldCoin", c2.getGoldCoin());
			map.put("polar", c2.getPolar());
			map.put("level", c2.getLevel());
			map.put("chargeScore", c2.getChargeScore());
			map.put("isEnable",  c2.getBlock());
			map.put("onLine",  c2.getOnline()==0?"离线":"在线");
			map.put("accountid",  c2.getAccountId());
			map.put("shut",  c2.getShut());
			map.put("addTime",  c2.getAddTime());
			chara.add(map);
			onLineIds.add(c2.getId());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", pageInfo.getTotal());
		resultMap.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getList() == null) {
			resultMap.put("resultData", new ArrayList<Object>());
		} else {
			resultMap.put("resultData", chara);
		}
		//查询在线玩家记录
		resultMap.put("onlines", online);
		resultMap.put("currentPageNum", pageInfo.getPageNum());
		return ResponseView.ok(resultMap);
	}

	public List<String> getOnLineUsers() {

		PageHelper.startPage(1, 50).setOrderBy("online desc");

		Example example = new Example(Characters.class);
		example.selectProperties("id","gid","online","block","level","goldCoin","polar","sex","portrait","name","chargeScore","x","y","mapName","accountId","shut","addTime");
		example.orderBy("online").desc();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("xiaozi", 1);
		PageInfo<Characters> pageInfo = new PageInfo<Characters>(GameData.that.baseCharactersService.selectByExample(example));


		List<String> onLineIds = new ArrayList<>();
		for(Characters c2:pageInfo.getList()) {
			onLineIds.add(c2.getGid());
		}
		return onLineIds;
	}




	/**
	 * 删除问道小子
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/delWenDaoXiaoZi")
	public ResponseView delWenDaoXiaoZi(Integer id) {

		GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectChar(id);
		if (gameObjectCharByUUid != null) {
			gameObjectCharByUUid.offline();
		}
		// 删除宠物数据
		Example petExample = new Example(CharaPet.class);
		petExample.createCriteria().andEqualTo("cid", id);
		GameData.that.charaPetService.deleteByExample(petExample);
		// 删除角色数据
		GameData.that.baseCharactersService.deleteById(id);

		return ResponseView.ok();
	}

	/**
	 * 对问道小子发送数据
	 *
	 * @param uuid
	 * @param type
	 * @param value
	 * @param sendAllChara
	 * @return
	 */
	@PostMapping("/sendCharInfo")
	public ResponseView sendCharInfo(String uuid, String type, Integer value, Integer sendAllChara) {
		List<Chara> charas = new ArrayList<>();
		if (sendAllChara != null) {
			// 发送全部问道小子
			for (GameObjectChar gameObject : GameObjectCharMng.getAll()) {
				if (gameObject.characters.getXiaozi() == 1) {
					charas.add(gameObject.chara);
				}
			}
		} else {
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(uuid);
			if (gameObjectCharByUUid != null) {
				charas = Lists.newArrayList(gameObjectCharByUUid.chara);
			} else {
				charas = Lists.newArrayList();
			}
		}
		for (Chara chara : charas) {
			switch (type) {
				case "exp":
					GameUtil.addjingyanToManage(chara, value);
					break;
			}
		}
		return ResponseView.ok();
	}

	/**
	 * 创建问道小子
	 *
	 * @param name 名称
	 * @param templeatId 模板id
	 * @return
	 */
	@PostMapping("/addXiaoZi")
	public ResponseView addXiaoZi(String name, String templeatId) {
		// 角色名重复
		Example example1 = new Example(Characters.class);
		example1.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name.replaceAll("\\s*", ""));
		if (GameData.that.characterService.selectCountByExample(example1) > 0) {
			ResponseView.fail("角色名已被使用！");
		}
		// 指定了tid
		String data = GameData.that.redisUtils.getHash("WD_XIAOZI_TEMPLATE", templeatId);
		if(data == null) {
			ResponseView.fail("模板不存在！");
		}
		JSONObject tempLateData = JSONObject.parseObject(data);
		// 取出模板的信息
		Chara chara = JSONObject.parseObject(tempLateData.getString("data"), Chara.class);
		chara.name = name;
		chara.uuid = UUID.randomUUID().toString().replace("-", "");
		// 1男 2女
//		if (chara.sex == 0) {
//			// 随机性别
//			chara.sex = ThreadLocalRandom.current().nextInt(2) + 1;
//		}
		// 如果为0表示随机门派
//		if (chara.polar == 0) {
//			chara.polar = ThreadLocalRandom.current().nextInt(5) + 1;
//		}
		// 开始登录并设置信息
		Characters characters = new Characters();
		characters.setName(chara.getName());
		characters.setPolar(chara.polar);
		characters.setGid(chara.uuid);
		characters.setXiaozi(1);
		characters.setAddTime(new Date());
		characters.setSex(chara.sex);
		characters.setShut(0);
		characters.setPetStore("[]");
		characters.setFixedTeamName("");
		characters.setTyzqStore("[]");
		characters.setChargeScore(0);
		characters.setMapId(chara.getMapid());
		characters.setX(chara.x);
		characters.setY(chara.y);
		characters.setLevel(chara.level);
		characters.setGoldCoin(0);
		if(chara.mapName == null) {
			characters.setX(95);
			characters.setY(64);
			characters.setMapName("天墉城");
		}else {
			characters.setMapName(chara.mapName);
		}
		characters.setPortrait(GameUtil.getWaiguan(chara.polar, chara.sex, chara));
		characters.setCustomShizhuang("[]");
		// 先插入
		GameData.that.characterService.add(characters);

		// 以下设置其他信息
		characters.setCangku("[]");
		characters.setShizhuang("[]");
		characters.setTexiao(JSONObject.toJSONString(chara.texiao));
		characters.setGenchong(JSONObject.toJSONString(chara.genchong));

		// 设置装备信息
		for (Goods goods : chara.backpack) {
			goods.goodsInfo.auto_fight = GameCommonUtil.UUID();
		}
		characters.setBackpack(JSONObject.toJSONString(chara.backpack));
		characters.setListshouhu("[]");
		characters.setCardStore("[]");
		characters.setAccountId(0); // 角色账号
		characters.setTyzqStore("[]");
		// 宠物信息插入到数据库
		for (Petbeibao pet : chara.pets) {
			CharaPet charaPet = new CharaPet();
			charaPet.setPet(JSONObject.toJSONString(pet));
			charaPet.setCid(characters.getId());
			charaPet.setOwnerName(name);
			charaPet.setPetName(pet.petShuXing.get(0).str);
			charaPet.setUuid(chara.uuid);
			charaPet.setAddTime(new Date());
			GameData.that.charaPetService.insertSelective(charaPet);
			if (chara.chongwuchanzhanId == pet.id) {
				chara.chongwuchanzhanId = charaPet.getId();
				if (chara.upgrade_state != 0) {
					chara.charaYuanyingInfo.chongwuchanzhanId = charaPet.getId();
				} else {
					chara.charaRealInfo.chongwuchanzhanId = charaPet.getId();
				}
			}
			pet.id = charaPet.getId();
		}
		if (chara.pets != null && !chara.pets.isEmpty()) {
			characters.setPetStore(JSONObject.toJSONString(chara.pets));
		}

		// 把角色需要的信息复制到这个对象中
		SaveChara saveChara = new SaveChara();
		if (chara.upgrade_state != 0) {
			if (chara.upgrade_state != 0) {
				chara.charaYuanyingInfo.autofight_select = 1;
				chara.charaYuanyingInfo.autofight_skillaction = 2;
				chara.charaYuanyingInfo.autofight_skillno = 0;
			} else {
				chara.charaRealInfo.autofight_select = 1;
				chara.charaRealInfo.autofight_skillaction = 2;
				chara.charaRealInfo.autofight_skillno = 0;
			}
		}
		chara.id = characters.getId();
		chara.allId = chara.id * 100000;
		BeanUtils.copyProperties(chara, saveChara);
		String jsonString = JSONObject.toJSONString(saveChara);
		characters.setData(jsonString);
		// 更新
		characters.setPetStore(null);
		int updateByPrimaryKeySelective = GameData.that.baseCharactersService.updateByPrimaryKeySelective(characters);

		//删除昵称库这个名称
		Example random = new Example(CharaNickname.class);
		random.createCriteria().andEqualTo("name", chara.name);
		GameData.that.charaNicknameService.deleteByExample(random);

		return ResponseView.ok(updateByPrimaryKeySelective);
	}

	/**
	 * 获取随机昵称
	 * @return
	 */
	@PostMapping("/getRandomName")
	public ResponseView getRandomName() {
		CharaNickname cn = new CharaNickname();
		int nextInt = ThreadLocalRandom.current().nextInt(1);
		cn.setSex(nextInt == 0 ? "男" : "女");
		CharaNickname randomData = GameData.that.charaNicknameService.randomData(cn);
		return ResponseView.ok(randomData);
	}


	/**
	 * 获取客户端
	 * @param name
	 * @return
	 */
	@PostMapping("/getClients")
	public ResponseView getClients(String name) {
		List<Map<String,Object>> clients = new ArrayList<>();
		for(Entry<String, Map<String, Object>> m:GameCore.xiaoziClientInfo.entrySet()) {
			Map<String, Object> value = m.getValue();
			Map<String,Object> showValue = new HashMap<>();
			showValue.put("name", value.get("name"));
			showValue.put("online", value.get("online"));
			showValue.put("uuid", m.getKey());
			clients.add(showValue);
		}
		return ResponseView.ok(clients);
	}

	@PostMapping({ "/switchCharaLogin" })
	public ResponseView switchCharaLogin(final String uuid, final String name) {
		if (name == null) {
			ResponseView.fail("角色名不能为空");
		}
		final Example example = new Example(Characters.class);
		Example.Criteria criteria =example.createCriteria().andEqualTo("xiaozi", 1);
		if("all:online".equals(name)){
			criteria .andEqualTo("online", 0);
		}else{
			criteria .andEqualTo("name", name);
		}
		List<Characters> charactersList = GameData.that.baseCharactersService.selectByExample(example);
		if(CollectionUtils.isEmpty(charactersList)){
			ResponseView.fail("未找到可上线的小子");
		}
		final Map<String, Map<String, Object>> xiaoziClientInfo = GameCore.xiaoziClientInfo;
		int i = 0;
		for (Characters characters : charactersList) {
			for (final Map.Entry<String, Map<String, Object>> data : xiaoziClientInfo.entrySet()) {
				final Map<String, Object> clientInfo = data.getValue();

				if (clientInfo.get("name") != null && !clientInfo.get("name").equals("")) {
					continue;
				}

				clientInfo.put("online", 1);
				clientInfo.put("name", characters.getName());
				final ChannelHandlerContext ctx = (ChannelHandlerContext)clientInfo.get("ctx");
				final Map<String, Object> object = new LinkedHashMap<String, Object>();
				object.put("uuid:str", data.getKey());
				object.put("name:str", characters.getName());
				ctx.writeAndFlush(new CommonCmd(9999).write(object, new boolean[0]));
				ctx.writeAndFlush(new CommonCmd(41920).write(object, new boolean[0]));

				++i;
				break;
			}

		}
		if (i != charactersList.size()) {
			ResponseView.fail("可用的客户端不足,预计需要:" + charactersList.size()+ "个,实际只有"+ i+"个,缺少"+(charactersList.size() - i)+"个");
		}
		return ResponseView.ok("登录成功" + charactersList.size() + "个");
	}

	@PostMapping({ "/openClients" })
	public ResponseView openClients(Integer count) {
		if (count == null) {
			count = 1;
		}
		int sum = 0;
		for (int i = 0; i < count; i++) {
			boolean startFlag = new WdXiaoZiClient().startClient();
			if(startFlag){
				sum++;
			}
		}
		//final String doPost = HttpUtils.doPost(GameConfig.config.getBaseConfig().getXiaoziUrl() + "/xiaozi/openClients", (Map)params);

		if (sum != count) {
			ResponseView.fail("启动失败" + (count - sum) + "个");
		}
		return ResponseView.ok("启动成功"+(sum) + "个");
	}
	/**
	 * 获取可用内存
	 * @param count
	 * @return
	 */
	@PostMapping("/getAvailableRam")
	public ResponseView getAvailableRam(Integer count) {
		return ResponseView.ok(Utils.getAvailableRam());
	}


	/**
	 * 强制关闭客户端
	 * @param uuid
	 * @return
	 */
	@PostMapping("/shutdownClient")
	public ResponseView getAvailableRam(String uuid) {
		Map<String, Map<String, Object>> xiaoziClientInfo = GameCore.xiaoziClientInfo;
		Map<String, Object> clientInfo = xiaoziClientInfo.get(uuid);
		if(clientInfo == null) {
			ResponseView.fail("客户端不在线！");
		}
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar((String)clientInfo.get("name"));
		//给客户端发通知
		ChannelHandlerContext ctx = (ChannelHandlerContext) clientInfo.get("ctx");
		ctx.writeAndFlush(new CommonCmd(9000).write(null));
		if(gameObjectChar != null) {
			gameObjectChar.offline();
		}
		xiaoziClientInfo.remove(uuid);
		return ResponseView.ok();
	}

	/**
	 * 关闭所有的客户端
	 * @return
	 */
	@PostMapping("/closeAllClient")
	public ResponseView closeAllClient() {

		Map<String, Map<String, Object>> xiaoziClientInfo = GameCore.xiaoziClientInfo;
		for(Map.Entry<String, Map<String,Object>> m:xiaoziClientInfo.entrySet()) {
			Map<String, Object> clientInfo = m.getValue();
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar((String)clientInfo.get("name"));
			//给客户端发通知
			ChannelHandlerContext ctx = (ChannelHandlerContext) clientInfo.get("ctx");
			ctx.writeAndFlush(new CommonCmd(9000).write(null));
			if(gameObjectChar != null) {
				gameObjectChar.offline();
			}
		}
		//删除所有
		xiaoziClientInfo.clear();
		return ResponseView.ok(Utils.getAvailableRam());
	}

	/**
	 * 问道小子托管执行
	 * @param type
	 * @param content
	 * @param time
	 * @return
	 */
	@PostMapping("/xiaoziBackTimer")
	public ResponseView xiaoziBackTimer(String uuid, String type, String content, Integer time) {
		if("timerContent".equals(type)) {

		}else if("timerScripts".equals(type)) {

		}
		return ResponseView.ok();
	}

	/**
	 * 获取可用的问道小子
	 * @return
	 */
	@PostMapping("/getAvailable")
	public ResponseView xiaoziBackTimer(int count) {
		PageHelper.startPage(1, count);
		Example example = new Example(Characters.class);
		example.selectProperties("name");
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("xiaozi", 1).andEqualTo("online", 0);
		PageInfo<Characters> pageInfo = new PageInfo<Characters>(GameData.that.baseCharactersService.selectByExample(example));
		List<Characters> selectByExample = pageInfo.getList();
		StringBuilder names = new StringBuilder();
		if(!selectByExample.isEmpty()) {
			for (int i = 0; i < selectByExample.size(); i++) {
				if(i<selectByExample.size()-1) {
					names.append(selectByExample.get(i).getName()).append("#");
				}else {
					names.append(selectByExample.get(i).getName());
				}
			}
		}
		return ResponseView.ok(names);
	}

	/**
	 * 添加小子模板
	 * @param name 名字
	 * @param data 数据
	 * @return
	 */
	@PostMapping("/addXiaoZiTemplate")
	public ResponseView addXiaoZiTemplate(@RequestParam()Map<String,Object> para) {
		String uuid = GameCommonUtil.UUID();
		para.put("tid", uuid);
		GameData.that.redisUtils.setHash("WD_XIAOZI_TEMPLATE", uuid, JSONObject.toJSONString(para));
		return ResponseView.ok();
	}

	/**
	 * 获取问道小子模板
	 * @return
	 */
	@PostMapping("/getXiaoZiTemplates")
	public ResponseView getXiaoZiTemplates() {
		List<Object> listKey = GameData.that.redisUtils.getHashs("WD_XIAOZI_TEMPLATE");
		List<Object> data = new ArrayList<>();
		for(Object o:listKey) {
			data.add(JSONObject.parse(String.valueOf(o)));
		}
		return ResponseView.ok(data);
	}
	/**
	 * 获取问道小子模板
	 * @return
	 */
	@PostMapping("/delXiaoZiTemplate")
	public ResponseView delXiaoZiTemplate(String tid) {
		Long delete = GameData.that.redisUtils.getHashObj().delete("WD_XIAOZI_TEMPLATE", tid);
		return ResponseView.ok(delete);
	}
}