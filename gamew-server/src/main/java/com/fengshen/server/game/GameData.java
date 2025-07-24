package com.fengshen.server.game;

import javax.annotation.PostConstruct;

import com.fengshen.db.service.base.*;
import com.fengshen.db.service.chara.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fengshen.db.service.AccessibilityMapService;
import com.fengshen.db.service.CharacterService;
import com.fengshen.db.service.SaleGoodService;
import com.fengshen.db.service.friend.FriendGroupService;
import com.fengshen.db.service.friend.FriendService;
import com.fengshen.db.service.game.DaySignPrizeService;
import com.fengshen.db.service.party.PartyMemberService;
import com.fengshen.db.service.party.PartyService;
import com.fengshen.db.service.party.PartySkillService;
import com.fengshen.db.service.pet.CustomPetSkillService;
import com.fengshen.db.service.shidao.ShidaoHistoryService;
import com.fengshen.db.service.shidao.ShidaoHistoryteamService;
import com.fengshen.db.service.shop.RareShopItemService;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.db.service.system.ConfigInfoService;
import com.fengshen.db.service.system.DialogService;
import com.fengshen.db.service.system.LuckDrawItemService;
import com.fengshen.db.service.system.MailboxRefreshService;
import com.fengshen.db.service.system.StallRecordService;
import com.fengshen.db.service.system.VictoryDieRewardService;
import com.fengshen.db.service.zhenbao.GoldStallNineGoodsService;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.job.GameRankJob;

@Service
public class GameData {
	public static GameData that;
	@Autowired
	public ChargePointMng chargePointMng;

	//baseDailiService
	@Qualifier("baseDailiService")
	@Autowired
	public BaseDailiService baseDailiService;

	@Qualifier("baseCharactersService")
	@Autowired
	public BaseCharactersService baseCharactersService;
	@Qualifier("baseCharaStatueService")
	@Autowired
	public BaseCharaStatueService baseCharaStatueService;
	// add:e
	@Qualifier("characterService")
	@Autowired
	public CharacterService characterService;
	@Autowired
	public BasePetService basePetService;
	@Autowired
	public BaseExperienceService baseExperienceService;
	@Autowired
	public BaseStoreInfoService baseStoreInfoService;
	@Autowired
	public BaseZhuangbeiInfoService baseZhuangbeiInfoService;
	@Autowired
	public BaseChoujiangService baseChoujiangService;
	@Autowired
	public BaseShowTasksService baseShowTasksService;
	@Autowired
	public BasePetHelpTypeService basePetHelpTypeService;
	@Autowired
	public SaleGoodService saleGoodService;
	@Autowired
	public BaseNpcService baseNpcService;
	@Autowired
	public BaseMapService baseMapService;
	@Autowired
	public BaseAccountsService baseAccountsService;
	@Autowired
	public BaseNpcPointService baseNpcPointService;
	@Autowired
	public BaseNpcDialogueService baseNpcDialogueService;
	@Autowired
	public BaseNpcDialogueFrameService baseNpcDialogueFrameService;
	@Autowired
	public BaseCreepsStoreService baseCreepsStoreService;
	@Autowired
	public BaseGroceriesShopService baseGroceriesShopService;
	@Autowired
	public BaseMedicineShopService baseMedicineShopService;
	@Autowired
	public BaseSaleClassifyGoodService baseSaleClassifyGoodService;
	@Autowired
	public BaseStoreGoodsService baseStoreGoodsService;
	@Autowired
	public BaseShuxingduiyingService baseShuxingduiyingService;
	@Autowired
	public BasePackModificationService basePackModificationService;
	@Autowired
	public BaseSkillMonsterService baseSkillMonsterService;
	@Autowired
	public BaseRenwuService baseRenwuService;
	@Autowired
	public BaseRenwuMonsterService baseRenwuMonsterService;
	@Autowired
	public BaseExperienceTreasureService baseExperienceTreasureService;
	@Autowired
	public BaseNoticeService baseNoticeService;
	@Autowired
	public BaseChargeService baseChargeService;
	@Autowired
	public BaseRenwuResetService baseRenwuResetService;

	@Autowired
	public FightObjectInfoService baseFightObjectService;
	
	@Autowired
	public AccessibilityMapService accessibilityMapService;
	
	@Autowired
	public FriendService friendService;
	@Autowired
	public FriendGroupService friendGroupService;
	@Autowired
	public MailboxRefreshService mailboxRefreshService;
	@Autowired
	public GoldStallNineGoodsService zhenbao;
	@Autowired
	public RedisUtils redisUtils;
	@Autowired
    public GameRankJob rj;
	@Autowired
	public CharaNicknameService charaNicknameService;
	//帮派
	@Autowired
	public PartyService partyService;
	@Autowired
	public PartyMemberService partyMemberService;
	@Autowired
	public PartySkillService partySkill;
	@Autowired
	public DialogService dialogService;
	//变身卡
	@Autowired
	public ChangeCardService changeCardService;
	//宠物
	@Autowired
	public CharaPetService charaPetService;
	@Autowired
	public ChargeGetRecordService chargeGetRecordService;
	@Autowired
	public ShidaoHistoryService shidaoHistoryService;
	@Autowired
	public ShidaoHistoryteamService shidaoHistoryteamService;
	@Autowired
	public VictoryDieRewardService victoryDieRewardService;
	@Autowired
	public CustomPetSkillService customPetSkillService;
	@Autowired
	public ChengweiService chengweiService;
	@Autowired
	public ConfigInfoService configInfoService;
	@Autowired
	public LuckDrawItemService luckDrawItemService;
	@Autowired
	public FasionCustomInfoService fasionCustomInfoService;
	@Autowired
	public BlackListService blackListService;
	@Autowired
	public DaySignPrizeService daySignPrizeService;
	@Autowired
	public StallRecordService stallRecordService;
	@Autowired
	public CharaTrailService charaTrailService;
	@Autowired
	public FixedTeamService fixedTeamService;
	@Autowired
	public RareShopItemService rareShopItemService;
	@Autowired
	public FuDaiChengweiService fuDaiChengweiService;
	
	@PostConstruct
	public void initAfter() {
		GameData.that = this;
	}
}
