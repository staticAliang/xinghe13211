package com.fengshen.server.job;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fengshen.core.util.DateUtil;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.CharaTrail;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.service.CharacterService;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.game.RankUtils;
import com.fengshen.server.data.vo.rank.Vo_TOP_USER;
import com.fengshen.server.data.vo.user.Vo_CharaTrail_Tao;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.ZbAttribute;
import com.fengshen.server.domain.rank.Rank;
import com.fengshen.server.game.GameData;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Component
@Slf4j
public class GameRankJob {

	private final static String RANK_PREFIX = "rank_type:";
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 生成排行榜
	 * 
	 * @throws JsonProcessingException
	 */
	@Async
	@Scheduled(cron = "0 0 1 * * ?")
	public void createRank() throws JsonProcessingException {
		refreshRank();
	}
	
	/**
	 * 刷新排行榜
	 * @throws JsonProcessingException
	 */
	public void refreshRank() throws JsonProcessingException {
		log.error("刷新排行榜....");
		CharacterService characterService = GameData.that.characterService;
		// 玩家集合
		List<Chara> sortCharas = new ArrayList<>();
		// 存放装备
		List<VoEquip> voEquips = new ArrayList<>();
		List<VoRankPet> petLists = new ArrayList<>();
		Example examples = new Example(Characters.class);
		examples.selectProperties("id");
		examples.createCriteria().andEqualTo("block", 0).andEqualTo("deleted", false).andEqualTo("xiaozi", 0);
		List<Characters> idList = characterService.selectByExample(examples);
		for(Characters ch:idList) {
			Example example = new Example(Characters.class);
			example.selectProperties("data","name","id","gid","level","polar", "backpack");
			example.createCriteria().andEqualTo("id", ch.getId());
			Characters characters = characterService.selectOneByExample(example);
			try {
				Chara c = JSONObject.parseObject(characters.getData(), Chara.class);
				Map<String, List<Goods>> goodsMap = new HashMap<>();
				String hash = characters.getBackpack();
				goodsMap.put(characters.getGid(), JSONObject.parseArray(hash, Goods.class));
				// 设置装备数据
				List<Goods> goods = JSONObject.parseArray(hash, Goods.class);
				if(goods != null && !goods.isEmpty()) {
					for (Goods g : goods) {
						if(g != null) {
							if (g.pos == 1 || g.pos == 2 || g.pos == 3 || g.pos == 10) {
								VoEquip voEquip = new VoEquip();
								voEquip.setEquip_perfect_percent(g.goodsInfo.dunwu_times);
								voEquip.setName(g.goodsInfo.str);
								voEquip.setIid_str(characters.getGid() + "|" + g.goodsInfo.auto_fight);
								voEquip.setOwner_name(characters.getName());
								voEquip.setRebuild_level(g.goodsInfo.color);
								voEquip.setPos(g.pos);
								voEquip.setEquipLevel(g.goodsInfo.attrib);
								voEquips.add(voEquip);
							}
						}
					}
				}
				//设置宠物
				List<CharaPet> petsByCid = GameData.that.charaPetService.getPetsByCid(characters.getId());
				for(CharaPet cp:petsByCid) {
					//只有参战id的宠物才计入排行榜
					if(cp.getId() == c.chongwuchanzhanId) {
						Petbeibao petbeibao = JSONObject.parseObject(cp.getPet(),Petbeibao.class);
						PetShuXing petShuXing = petbeibao.petShuXing.get(0);
						//暂时只有阳间宠物的排行
						if(petShuXing.penetrate<6) {
							VoRankPet vrp = new VoRankPet();
							vrp.setFangyu(petShuXing.getWiz());
							vrp.setFashang(petShuXing.getMana());
							vrp.setWushang(petShuXing.getAccurate());
							vrp.setSudu(petShuXing.getParry());
							vrp.setMartial(petShuXing.getIntimacy());
							vrp.setOwner_name(characters.getName());
							vrp.setLevel(petShuXing.getSkill());
							vrp.setName(petShuXing.getStr());
							vrp.setIid_str(String.valueOf(cp.getId()));
							petLists.add(vrp);
						}
						break;
					}
				}
				if (c != null) {
					Chara chara = new Chara();
					ZbAttribute zb = c.getZbAttribute();
					// 法伤
					chara.setMana(c.getMana() + zb.mana+c.shenHunMagPower + c.luoshuMagpower);
					// 物伤
					chara.setAccurate(c.getAccurate() + zb.accurate+c.shenHunPhyPower +c.luoshumPhypower);
					// 防御
					chara.setWiz(c.getWiz() + zb.wiz + c.shenHunDef + c.luoshuDefense);
					// 速度
					chara.setParry(c.getParry() + zb.parry+c.shenHunSpeed + c.luoshuSpeed);
					// 道行
					chara.setTao(c.getTao());
					// 等级
					chara.setLevel(characters.getLevel());
					// 名称
					chara.setName(characters.getName());
					// uuid
					chara.setUuid(characters.getGid());
					// 门派
					chara.setPolar(characters.getPolar());
					chara.setUpgrade_level(c.getUpgrade_level());
					chara.setUpgrade_type(c.getUpgrade_type());
					chara.setIsFeisheng(c.getIsFeisheng());
					// 帮派
					chara.setPartyName(c.getPartyName());
					sortCharas.add(chara);
				}
				characters = null;
			} catch (Exception e) {
				log.error("生成排行耪错误。{}",e);
			}
		}
		// 人物-等级排行 101
		List<Chara> levelCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getLevel).reversed())
				.collect(Collectors.toList());
		List<Rank> levelCharaRankList = convertCharaRank(levelCharaList, 101, 0, 0);
		redisUtils.set(RANK_PREFIX + 101, levelCharaRankList);

		// 人物-道行排行 102 [RANK_TYPE.CHAR_TAO] = { "45-79", "80-89", "90-99", "100-109",
		// "110-119", "120-129" },
		List<Chara> taoCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getTao).reversed())
				.collect(Collectors.toList());

		List<Chara> taoCharaList45_79 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 45 && chara.getLevel() <= 79).collect(Collectors.toList());
		List<Rank> taoCharaRankList45_79 = convertCharaRank(taoCharaList45_79, 102, 45, 79);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 45 + "-" + 79, taoCharaRankList45_79);

		List<Chara> taoCharaList80_89 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 80 && chara.getLevel() <= 89).collect(Collectors.toList());
		List<Rank> taoCharaRankList80_89 = convertCharaRank(taoCharaList80_89, 102, 80, 89);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 80 + "-" + 89, taoCharaRankList80_89);

		List<Chara> taoCharaList90_99 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 90 && chara.getLevel() <= 99).collect(Collectors.toList());
		List<Rank> taoCharaRankList90_99 = convertCharaRank(taoCharaList90_99, 102, 90, 99);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 90 + "-" + 99, taoCharaRankList90_99);

		List<Chara> taoCharaList100_109 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 100 && chara.getLevel() <= 109).collect(Collectors.toList());
		List<Rank> taoCharaRankList100_109 = convertCharaRank(taoCharaList100_109, 102, 100, 109);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 100 + "-" + 109, taoCharaRankList100_109);

		List<Chara> taoCharaList110_119 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 110 && chara.getLevel() <= 119).collect(Collectors.toList());
		List<Rank> taoCharaRankList110_119 = convertCharaRank(taoCharaList110_119, 102, 110, 119);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 110 + "-" + 119, taoCharaRankList110_119);

		List<Chara> taoCharaList120_129 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 120 && chara.getLevel() <= 129).collect(Collectors.toList());
		List<Rank> taoCharaRankList120_129 = convertCharaRank(taoCharaList120_129, 102, 120, 129);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 120 + "-" + 129, taoCharaRankList120_129);

		List<Chara> taoCharaList130_139 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 130 && chara.getLevel() <= 139).collect(Collectors.toList());
		List<Rank> taoCharaRankList130_139 = convertCharaRank(taoCharaList130_139, 102, 130, 139);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 130 + "-" + 139, taoCharaRankList130_139);


		List<Chara> taoCharaList140_149 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 140 && chara.getLevel() <= 149).collect(Collectors.toList());
		List<Rank> taoCharaRankList140_149 = convertCharaRank(taoCharaList140_149, 102, 140, 149);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 140 + "-" + 149, taoCharaRankList140_149);

		List<Chara> taoCharaList150_159 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 150 && chara.getLevel() <= 159).collect(Collectors.toList());
		List<Rank> taoCharaRankList150_159 = convertCharaRank(taoCharaList150_159, 102, 150, 159);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 150 + "-" + 159, taoCharaRankList150_159);

		List<Chara> taoCharaList160_169 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 160 && chara.getLevel() <= 169).collect(Collectors.toList());
		List<Rank> taoCharaRankList160_169 = convertCharaRank(taoCharaList160_169, 102, 160, 169);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 160 + "-" + 169, taoCharaRankList160_169);

		List<Chara> taoCharaList170_179 = taoCharaList.stream()
				.filter(chara -> chara.getLevel() >= 170 && chara.getLevel() <= 179).collect(Collectors.toList());
		List<Rank> taoCharaRankList170_179 = convertCharaRank(taoCharaList170_179, 102, 170, 179);
		redisUtils.set(RANK_PREFIX + 102 + ":" + 170 + "-" + 179, taoCharaRankList170_179);

		
		// 人物-物伤排行 103
		List<Chara> phyCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getAccurate).reversed())
				.collect(Collectors.toList());
		List<Rank> phyCharaRankList = convertCharaRank(phyCharaList, 103, 0, 0);
		redisUtils.set(RANK_PREFIX + 103, phyCharaRankList);

		// 人物-法伤排行 104
		List<Chara> magCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getMana).reversed())
				.collect(Collectors.toList());
		List<Rank> magCharaRankList = convertCharaRank(magCharaList, 104, 0, 0);
		redisUtils.set(RANK_PREFIX + 104, magCharaRankList);

		// 人物-速度排行 105
		List<Chara> speedCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getParry).reversed())
				.collect(Collectors.toList());
		List<Rank> speedCharaRankList = convertCharaRank(speedCharaList, 105, 0, 0);
		redisUtils.set(RANK_PREFIX + 105, speedCharaRankList);

		// 人物-防御排行 106
		List<Chara> defCharaList = sortCharas.stream().sorted(Comparator.comparing(Chara::getWiz).reversed())
				.collect(Collectors.toList());
		List<Rank> defCharaRankList = convertCharaRank(defCharaList, 106, 0, 0);
		redisUtils.set(RANK_PREFIX + 106, defCharaRankList);

		// 元血婴排行榜
		List<Chara> levelYuanXueYingCharaList = sortCharas.stream()
				.filter(chara -> chara.upgrade_type > 0 && chara.isFeisheng == 1)
				.sorted(Comparator.comparing(Chara::getUpgrade_level).reversed()).collect(Collectors.toList());
		List<Rank> levelYuanXueYingCharaRankList = convertCharaRank(levelYuanXueYingCharaList, 107, 0, 0);
		redisUtils.set(RANK_PREFIX + 107, levelYuanXueYingCharaRankList);

		// 装备排行榜
		cacheRankEquip(voEquips);

		// 宠物排行榜
		cacheRankPet(petLists);
		
		log.error("排行榜刷新成功....");
	}

	/**
	 * 缓存装备排行榜
	 * 
	 * @param voEquips
	 * @throws JsonProcessingException
	 */
	public void cacheRankEquip(List<VoEquip> voEquips) throws JsonProcessingException {
		Map<String, List<VoEquip>> equipRankMap1 = getEquipRankMap(voEquips, 70, 79);
		GameData.that.redisUtils.set("RANK_EQUIP_201:70-79", equipRankMap1.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:70-79", equipRankMap1.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:70-79", equipRankMap1.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:70-79", equipRankMap1.get("boot"));

		Map<String, List<VoEquip>> equipRankMap2 = getEquipRankMap(voEquips, 80, 89);
		GameData.that.redisUtils.set("RANK_EQUIP_201:80-89", equipRankMap2.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:80-89", equipRankMap2.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:80-89", equipRankMap2.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:80-89", equipRankMap2.get("boot"));

		Map<String, List<VoEquip>> equipRankMap3 = getEquipRankMap(voEquips, 90, 99);
		GameData.that.redisUtils.set("RANK_EQUIP_201:90-99", equipRankMap3.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:90-99", equipRankMap3.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:90-99", equipRankMap3.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:90-99", equipRankMap3.get("boot"));

		Map<String, List<VoEquip>> equipRankMap4 = getEquipRankMap(voEquips, 100, 109);
		GameData.that.redisUtils.set("RANK_EQUIP_201:100-109", equipRankMap4.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:100-109", equipRankMap4.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:100-109", equipRankMap4.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:100-109", equipRankMap4.get("boot"));

		Map<String, List<VoEquip>> equipRankMap5 = getEquipRankMap(voEquips, 110, 119);
		GameData.that.redisUtils.set("RANK_EQUIP_201:110-119", equipRankMap5.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:110-119", equipRankMap5.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:110-119", equipRankMap5.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:110-119", equipRankMap5.get("boot"));

		Map<String, List<VoEquip>> equipRankMap6 = getEquipRankMap(voEquips, 120, 129);
		GameData.that.redisUtils.set("RANK_EQUIP_201:120-129", equipRankMap6.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:120-129", equipRankMap6.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:120-129", equipRankMap6.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:120-129", equipRankMap6.get("boot"));
	
		Map<String, List<VoEquip>> equipRankMap7 = getEquipRankMap(voEquips, 130, 139);
		GameData.that.redisUtils.set("RANK_EQUIP_201:130-139", equipRankMap7.get("weapon"));
		GameData.that.redisUtils.set("RANK_EQUIP_202:130-139", equipRankMap7.get("helmet"));
		GameData.that.redisUtils.set("RANK_EQUIP_203:130-139", equipRankMap7.get("armor"));
		GameData.that.redisUtils.set("RANK_EQUIP_204:130-139", equipRankMap7.get("boot"));
	}

	/**
	 * a本月道行排行
	 */
	private void monthTaoRank() {
		//统计本月刷道总数
		Example example = new Example(CharaTrail.class);
		example.createCriteria().andEqualTo("remarks", "道行").andCondition("DATE_FORMAT(add_time,\"%Y-%m\")=", DateUtil.format(new Date(), "yyyy-MM"));
		List<CharaTrail> monthCharaTrail = GameData.that.charaTrailService.selectByExample(example);
		
		//总道行vo对象
		List<Vo_CharaTrail_Tao> taos = new ArrayList<>();
		for(CharaTrail mon:monthCharaTrail) {
			Vo_CharaTrail_Tao taoAll = new Vo_CharaTrail_Tao();
			taoAll.setData(Integer.valueOf(taoAll.getData()));
			taoAll.setCid(mon.getCid());
		}
		taos.parallelStream().collect(Collectors.groupingBy(Vo_CharaTrail_Tao::getData,Collectors.counting()));
	}
	
	private void cacheRankPet(List<VoRankPet> pet) throws JsonProcessingException {
		
		//武学
		List<VoRankPet> petRankWuxue = pet.stream().sorted(Comparator.comparing(VoRankPet::getMartial)
			.thenComparing(VoRankPet::getLevel).reversed()).collect(Collectors.toList());
		if (petRankWuxue.size() > 100) {
			petRankWuxue = petRankWuxue.subList(0, 100);
		}
		GameData.that.redisUtils.set("RANK_PET_301", convertPetMap("wuxue", petRankWuxue));
		
		//物伤
		List<VoRankPet> petRankWushang = pet.stream().sorted(Comparator.comparing(VoRankPet::getWushang)
				.thenComparing(VoRankPet::getLevel).reversed()).collect(Collectors.toList());
		if (petRankWushang.size() > 100) {
			petRankWushang = petRankWushang.subList(0, 100);
		}
		GameData.that.redisUtils.set("RANK_PET_302", convertPetMap("wushang", petRankWushang));
		
		//法伤
		List<VoRankPet> petRankFashang = pet.stream().sorted(Comparator.comparing(VoRankPet::getFashang).
				thenComparing(VoRankPet::getLevel).reversed()).collect(Collectors.toList());
		if (petRankFashang.size() > 100) {
			petRankFashang = petRankFashang.subList(0, 100);
		}
		GameData.that.redisUtils.set("RANK_PET_303", convertPetMap("fashang", petRankFashang));
		
		//速度
		List<VoRankPet> petRankSudu = pet.stream().sorted(Comparator.comparing(VoRankPet::getSudu)
				.thenComparing(VoRankPet::getLevel).reversed()).collect(Collectors.toList());
		if (petRankSudu.size() > 100) {
			petRankSudu = petRankSudu.subList(0, 100);
		}
		GameData.that.redisUtils.set("RANK_PET_304", convertPetMap("sudu", petRankSudu));
		
		//防御
		List<VoRankPet> petRankFangyu = pet.stream().sorted(Comparator.comparing(VoRankPet::getFangyu)
				.thenComparing(VoRankPet::getLevel).reversed()).collect(Collectors.toList());
		if (petRankFangyu.size() > 100) {
			petRankFangyu = petRankFangyu.subList(0, 100);
		}
		GameData.that.redisUtils.set("RANK_PET_305", convertPetMap("fangyu", petRankFangyu));
	
	}

	/**
	 * 人物排行
	 *
	 * @param list
	 * @param type
	 */
	private List<Rank> convertCharaRank(List<Chara> list, int type, int minLevel, int maxLevel) {
		List<Rank> rankList = Lists.newLinkedList();
		int sortIdx = 0;
		for (Chara chara : list) {
			sortIdx++;
			Rank rank = new Rank();
			rank.setUuid(chara.getUuid());
			rank.setName(chara.getName());
			rank.setLevel(chara.getLevel());
			rank.setPolar(chara.getPolar());
			rank.setPartyName(chara.getPartyName());
			rank.setSortIdx(sortIdx);
			rank.setUpgrade_level(chara.upgrade_level);
			rank.setUpgrade_type(chara.upgrade_type);
			if (minLevel == 0 || maxLevel == 0) {
				rank.setType(String.valueOf(type));
			} else {
				rank.setType(type + ":" + minLevel + "-" + maxLevel);
			}
			rank.setValue(RankUtils.getRankValue(chara, type));
			rank.setCreateTime(new Date());
			rankList.add(rank);
		}
		log.info("人物道行排名信息："+ JSON.toJSON(rankList));
		return rankList;
	}

	@Getter
	@Setter
	public static class VoEquip {
		private String name;
		private String iid_str;
		private Integer rebuild_level;
		private String owner_name;
		private Integer equip_perfect_percent;
		private Integer pos;
		private Integer equipLevel;
	}

	

	public Map<String, List<VoEquip>> getEquipRankMap(List<VoEquip> voEquips, int minLevel, int maxLevel) {
		List<VoEquip> weapon = voEquips.stream()
				.sorted(Comparator.comparing(VoEquip::getRebuild_level)
						.thenComparing(VoEquip::getEquip_perfect_percent).reversed())
				.filter(vo -> vo.getPos() == 1 && vo.getEquipLevel() >= minLevel && vo.getEquipLevel() <= maxLevel)
				.collect(Collectors.toList());
		if (weapon.size() > 100) {
			weapon = weapon.subList(0, 100);
		}

		// 帽子
		List<VoEquip> helmet = voEquips.stream()
				.sorted(Comparator.comparing(VoEquip::getRebuild_level)
						.thenComparing(VoEquip::getEquip_perfect_percent).reversed())
				.filter(vo -> vo.getPos() == 2 && vo.getEquipLevel() >= minLevel && vo.getEquipLevel() <= maxLevel)
				.collect(Collectors.toList());
		if (helmet.size() > 100) {
			helmet = helmet.subList(0, 100);
		}
		// 衣服
		List<VoEquip> armor = voEquips.stream()
				.sorted(Comparator.comparing(VoEquip::getRebuild_level)
						.thenComparing(VoEquip::getEquip_perfect_percent).reversed())
				.filter(vo -> vo.getPos() == 3 && vo.getEquipLevel() >= minLevel && vo.getEquipLevel() <= maxLevel)
				.collect(Collectors.toList());
		if (armor.size() > 100) {
			armor = armor.subList(0, 100);
		}
		// 鞋子
		List<VoEquip> boot = voEquips.stream()
				.sorted(Comparator.comparing(VoEquip::getRebuild_level)
						.thenComparing(VoEquip::getEquip_perfect_percent).reversed())
				.filter(vo -> vo.getPos() == 10 && vo.getEquipLevel() >= minLevel && vo.getEquipLevel() <= maxLevel)
				.collect(Collectors.toList());
		if (boot.size() > 100) {
			boot = boot.subList(0, 100);
		}
		Map<String, List<VoEquip>> map = new HashMap<>();
		map.put("weapon", weapon);
		map.put("helmet", helmet);
		map.put("armor", armor);
		map.put("boot", boot);
		return map;
	}

	/**
	 * 转换对象
	 * 
	 * @param vo
	 * @return
	 */
	public Map<Object, Object> convertEquipMap(VoEquip vo) {
		Map<Object, Object> map = new HashMap<>();
		map.put("name", vo.getName());
		map.put("iid_str", vo.getIid_str());
		map.put("equip_perfect_percent", vo.getEquip_perfect_percent());
		map.put("rebuild_level", vo.getRebuild_level());
		map.put("owner_name", vo.getOwner_name());
		return map;
	}
	@Getter
	@Setter
	public static class VoRankPet {
		private String name;
		private int level;
		private String owner_name;
		private int martial;
		private int wushang;
		private int fashang;
		private int fangyu;
		private int sudu;
		private String iid_str;
	}
	/**
	 * 封装宠物信息
	 * @param ownerName 主人
	 * @param petbeibao 宠物信息
	 * @return
	 */
	private List<Map<Object,Object>> convertPetMap(String type, List<VoRankPet> pets) {
		List<Map<Object,Object>> listMap = new ArrayList<>();
		for(VoRankPet p:pets) {
			Map<Object,Object> petMap = new HashMap<Object, Object>();
			petMap.put("name", p.getName());
			petMap.put("level", p.getLevel());
			petMap.put("owner_name", p.getOwner_name());
			petMap.put("iid_str", p.getIid_str());
			if("fashang".equals(type)) {
				petMap.put("mag_power", p.getFashang());
			}else if("wushang".equals(type)) {
				petMap.put("phy_power", p.getWushang());
			}else if("fangyu".equals(type)) {
				petMap.put("def", p.getFangyu());
			}else if("wuxue".equals(type)) {
				petMap.put("martial", p.getMartial());
			}else if("sudu".equals(type)) {
				petMap.put("speed", p.getSudu());
			}
			listMap.add(petMap);
		}
		return listMap;
	}

	/**
	 * 转换排行耪客户端直接send得对象
	 * 
	 * @param minLevel
	 * @param maxLevel
	 * @param requestType
	 * @param type
	 * @return
	 */
	public Vo_TOP_USER getRankEquipVo(int minLevel, int maxLevel, int requestType, int type) {
		Vo_TOP_USER top = new Vo_TOP_USER();
		top.setCookie((int) (System.currentTimeMillis() / 1000L));
		top.setRequestType(requestType);
		String json = GameData.that.redisUtils.get("RANK_EQUIP_" + type + ":" + minLevel + "-" + maxLevel + "");
		if (!StringUtils.isNullOrEmpty(json)) {
			List<Map<Object, Object>> datas = new ArrayList<>();
			List<VoEquip> parseArray = JSONObject.parseArray(json, VoEquip.class);
			for (VoEquip v : parseArray) {
				datas.add(convertEquipMap(v));
			}
			top.setData(datas);
		} else {
			top.setData(new ArrayList<>());
		}
		top.setType(type);
		top.setMaxLevel(maxLevel);
		top.setMinLevel(minLevel);
		return top;
	}
	
	/**
	 * 获取排行榜直接send的对象
	 * @param key
	 * @param requestType
	 * @param type
	 * @return
	 */
	public Vo_TOP_USER getRankVo(String key, int requestType, int type) {
		Vo_TOP_USER top = new Vo_TOP_USER();
		top.setCookie((int) (System.currentTimeMillis() / 1000L));
		top.setRequestType(requestType);
		String json = GameData.that.redisUtils.get(key+type);
		if (!StringUtils.isNullOrEmpty(json)) {
			JSONArray jsonArray = JSONObject.parseArray(json);
			List<Map<Object,Object>> datas = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Map<Object,Object> data = new HashMap<>();
				for(Map.Entry<String, Object> m:jsonObject.entrySet()) {
					data.put(m.getKey(), m.getValue());
				}
				datas.add(data);
			}
			top.setData(datas);
		} else {
			top.setData(new ArrayList<>());
		}
		top.setType(type);
		return top;
	}
	
	public List<Rank> getRankList(String rankType) {
		String rankString = GameData.that.redisUtils.get("rank_type:" + rankType);
		try {
			List<Rank> rankList = JSONObject.parseArray(rankString, Rank.class);
			if (rankList == null) {
				return Lists.newArrayList();
			}
			if (rankList.size() > 100) {
				rankList = rankList.subList(0, 100);
			}
			log.info("获取排行榜");
			return Lists.newArrayList(rankList);
		} catch (Exception e) {
			log.error("排行榜错误:{}", e);
			return Lists.newArrayList();
		}
	}
	

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(i);
		}
		System.out.println(list.subList(0, 100));
	}
}
