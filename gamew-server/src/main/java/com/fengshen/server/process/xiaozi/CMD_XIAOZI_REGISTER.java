package com.fengshen.server.process.xiaozi;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.CharaNickname;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.ServerHandler;
import com.fengshen.server.process.CommonCmd;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/*
 * 问道小子登录
 */
@Service
@Slf4j
public class CMD_XIAOZI_REGISTER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String tid = GameReadTool.readString(buff);
		// 随机从模板中取出一个信息,如果没有则采用默认模板
		ConfigInfo configInfo = null;
		if (StringUtils.isNullOrEmpty(tid)) {
			Example example = new Example(ConfigInfo.class);
			example.createCriteria().andEqualTo("keyName", "问道小子");
			List<ConfigInfo> selectByExample = GameData.that.configInfoService.selectByExample(example);
			if (selectByExample != null && !selectByExample.isEmpty()) {
				int nextInt = ThreadLocalRandom.current().nextInt(selectByExample.size());
				configInfo = selectByExample.get(nextInt);
			}
		} else {
			// 指定了tid
			Example example = new Example(ConfigInfo.class);
			example.createCriteria().andEqualTo("uuid", tid);
			configInfo = GameData.that.configInfoService.selectOneByExample(example);
		}

		if (configInfo == null) {
			// 没有设置任何模板信息
			// 随机取出一个名字
			String randomName = randomName(1, ThreadLocalRandom.current().nextInt(2) + 1);
			if (randomName.equals("")) {
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("msg", "注册失败,名字重复");
				ctx.writeAndFlush(new CommonWrite(9995).write(gameMap));
				return;
			}
			createChara(randomName,ThreadLocalRandom.current().nextInt(2) + 1,ThreadLocalRandom.current().nextInt(5) + 1, UUID.randomUUID().toString().replace("-", ""), ctx);
		} else {
			try {
				// 取出模板的信息
				Chara chara = JSONObject.parseObject(configInfo.getData(), Chara.class);
				chara.uuid = UUID.randomUUID().toString().replace("-", "");
				// 1男 2女
//				if (chara.sex == 0) {
//					// 随机性别
//					chara.sex = ThreadLocalRandom.current().nextInt(2) + 1;
//				}
				// 如果为0表示随机门派
//				if (chara.polar == 0) {
//					chara.polar = ThreadLocalRandom.current().nextInt(5) + 1;
//				}
				// 随机取出一个名字
				String randomName = randomName(1, chara.sex);
				chara.name = randomName;
				if (randomName.equals("")) {
					LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
					gameMap.put("msg", "注册失败,名字重复");
					ctx.writeAndFlush(new CommonWrite(9995).write(gameMap));
					return;
				}
				// 开始登录并设置信息
				Characters characters = new Characters();
				characters.setName(chara.getName());
				characters.setPolar(chara.polar);
				characters.setGid(chara.uuid);
				characters.setXiaozi(1);
				characters.setAddTime(new Date());
				characters.setBlock(0);
				//先插入
				GameData.that.characterService.add(characters);
			
				// 以下设置其他信息
				characters.setShizhuang(JSONObject.toJSONString(chara.shizhuang));
				characters.setTexiao(JSONObject.toJSONString(chara.texiao));
				characters.setGenchong(JSONObject.toJSONString(chara.genchong));
				characters.setPetStore("[]");
				//设置装备信息
				for(Goods goods:chara.backpack) {
					goods.goodsInfo.auto_fight = GameCommonUtil.UUID();
				}
				characters.setCangku("[]");
				characters.setBackpack("[]");
				characters.setCardStore("[]");
				characters.setTyzqStore("[]");
				
				characters.setListshouhu(JSONObject.toJSONString(chara.listshouhu));
				characters.setAccountId(0); // 角色账号
				characters.setLastLoginTime((int) (System.currentTimeMillis()/1000L));
				//宠物信息插入到数据库
				for(Petbeibao pet:chara.pets) {
					CharaPet charaPet = new CharaPet();
					charaPet.setPet(JSONObject.toJSONString(pet));
					charaPet.setCid(characters.getId());
					charaPet.setOwnerName(randomName);
					charaPet.setPetName(pet.petShuXing.get(0).str);
					charaPet.setUuid(chara.uuid);
					charaPet.setAddTime(new Date());
					GameData.that.charaPetService.insertSelective(charaPet);
					if(chara.chongwuchanzhanId == pet.id) {
						chara.chongwuchanzhanId = charaPet.getId();
						if(chara.upgrade_state != 0) {
							chara.charaYuanyingInfo.chongwuchanzhanId = charaPet.getId();
						}else {
							chara.charaRealInfo.chongwuchanzhanId = charaPet.getId();
						}
					}
					pet.id = charaPet.getId();
				}
				if(chara.pets != null && !chara.pets.isEmpty()) {
					characters.setPetStore(JSONObject.toJSONString(chara.pets));
				}
				
				// 把角色需要的信息复制到这个对象中
				SaveChara saveChara = new SaveChara();
				if(chara.upgrade_state != 0) {
					if(chara.upgrade_state != 0) {
						chara.charaYuanyingInfo.autofight_select = 1;
						chara.charaYuanyingInfo.autofight_skillaction = 2;
						chara.charaYuanyingInfo.autofight_skillno = 0;
					}else {
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
				//更新
				characters.setPetStore(null);
				GameData.that.baseCharactersService.updateByPrimaryKeySelective(characters);
				
				// 开始登录并设置信息
				Accounts account = new Accounts();
				account.setId(1);
				account.setToken(GameCommonUtil.UUID());
				GameObjectChar gameSession = new GameObjectChar(account, ctx);
				gameSession.characters = characters;
				gameSession.characters.setXiaozi(1);
				Attribute<GameObjectChar> attr = ctx.channel().attr(ServerHandler.akey);
				attr.set(gameSession);
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("name", chara.getName());
				ctx.writeAndFlush(new CommonWrite(9998).write(gameMap));
				
				
			} catch (Exception e) {
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("msg:str", "注册失败错误原因:"+e.getMessage());
				ctx.writeAndFlush(new CommonCmd(9995).write(gameMap));
			}
		}

		log.info("问道小子请求注册{}", tid);
	}

	@Override
	public int cmd() {
		return 9996;
	}

	private void createChara(String char_name, int gender, int polar, String uuid, ChannelHandlerContext ctx) {
		Chara chara = new Chara(char_name, gender, polar, uuid);
		chara.max_mana = chara.zbAttribute.dex + chara.dex; // dex法力
		chara.max_life = chara.zbAttribute.def + chara.def; // def气血
		chara.mapid = 1000; // 角色的位置

		Characters characters = new Characters();
		characters.setName(char_name);
		characters.setPolar(chara.polar);
		characters.setGid(uuid);
		characters.setXiaozi(1);
		characters.setBlock(0);
		// 把角色需要的信息复制到这个对象中
		SaveChara saveChara = new SaveChara();
		if(chara.autofight_select == 0) {
			if(chara.upgrade_state != 0) {
				chara.charaYuanyingInfo.autofight_select = 1;
				chara.charaYuanyingInfo.autofight_skillaction = 2;
				chara.charaYuanyingInfo.autofight_skillno = 0;
			}else {
				chara.charaRealInfo.autofight_select = 1;
				chara.charaRealInfo.autofight_skillaction = 2;
				chara.charaRealInfo.autofight_skillno = 0;
			}
		}
		BeanUtils.copyProperties(chara, saveChara);
		String jsonString = JSONObject.toJSONString(saveChara);
		characters.setData(jsonString);
		// 以下设置其他信息
		characters.setCangku(JSONObject.toJSONString(chara.cangku));
		characters.setBackpack(JSONObject.toJSONString(chara.backpack));
		characters.setCardStore(JSONObject.toJSONString(chara.cardStore));
		
		characters.setShizhuang(JSONObject.toJSONString(chara.shizhuang));
		characters.setTexiao(JSONObject.toJSONString(chara.texiao));
		characters.setGenchong(JSONObject.toJSONString(chara.genchong));
		characters.setPetStore(JSONObject.toJSONString(chara.pets));
		characters.setListshouhu(JSONObject.toJSONString(chara.listshouhu));
		characters.setTyzqStore(JSONObject.toJSONString(chara.tyzqStore));
		characters.setAccountId(0); // 角色账号
		// 将当前角色添加到全局角色对象中
		GameData.that.characterService.add(characters);
		chara.id = characters.getId();
		chara.allId = chara.id * 100000;
		chara.autofight_select = 1;
		
		// 开始登录并设置信息
		Accounts account = new Accounts();
		account.setId(1);
		account.setToken(GameCommonUtil.UUID());
		GameObjectChar gameSession = new GameObjectChar(account, ctx);
		gameSession.characters = characters;
		Attribute<GameObjectChar> attr = ctx.channel().attr(ServerHandler.akey);
		attr.set(gameSession);
		LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
		gameMap.put("name", char_name);
		ctx.writeAndFlush(new CommonWrite(9998).write(gameMap));
	}

	public String randomName(int count, int sex) {
		if (count > 5000) {
			return "";
		}
		CharaNickname cn = new CharaNickname();
		cn.setSex(sex == 1 ? "男" : "女");
		CharaNickname randomData = GameData.that.charaNicknameService.randomData(cn);

		Example example2 = new Example(Characters.class);
		example2.createCriteria().andEqualTo("deleted", false).andEqualTo("name",
				randomData.getName().replaceAll("\\s*", ""));
		if (GameData.that.characterService.selectCountByExample(example2) > 0) {
			randomName(count++, sex);
		}
		return randomData.getName();
	}

	// 初始化角色的装备栏
	public void addbackpack(Chara chara) {
		ZhuangbeiInfo zhuangb = new ZhuangbeiInfo();
		List<ZhuangbeiInfo> byAttrib = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService.findByAttrib(1);
		for (int i = 0; i < byAttrib.size(); ++i) {
			if (byAttrib.get(i).getMaster() == chara.sex && byAttrib.get(i).getAmount() == 3) {
				zhuangb = byAttrib.get(i);
				Goods goods = new Goods();
				goods.pos = 3;
				goods.goodsInfo = new GoodsInfo();
				goods.goodsBasics = new GoodsBasics();
				goods.goodsLanSe = new GoodsLanSe();
				goods.goodsCreate(zhuangb);
				chara.backpack.add(goods);
			}
			if (byAttrib.get(i).getMaster() == chara.sex && byAttrib.get(i).getAmount() == 2) {
				zhuangb = byAttrib.get(i);
				Goods goods = new Goods();
				goods.pos = 2;
				goods.goodsInfo = new GoodsInfo();
				goods.goodsBasics = new GoodsBasics();
				goods.goodsLanSe = new GoodsLanSe();
				goods.goodsCreate(zhuangb);
				chara.backpack.add(goods);
			}
		}
		zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("麻鞋");
		Goods goods2 = new Goods();
		goods2.pos = 10;
		goods2.goodsInfo = new GoodsInfo();
		goods2.goodsBasics = new GoodsBasics();
		goods2.goodsLanSe = new GoodsLanSe();
		goods2.goodsCreate(zhuangb);
		chara.backpack.add(goods2);
	}
}
