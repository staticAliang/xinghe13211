package com.fengshen.web.controller.sys.chara;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.service.system.ConfigInfoService;
import com.fengshen.server.domain.config.CtConfig;
import com.fengshen.server.domain.config.NeiDanConfig;
import com.fengshen.server.domain.config.VipChargeConfig;
import com.fengshen.server.domain.config.VipChargeConfig.Reward;
import com.fengshen.server.domain.config.VipChargeConfig.Reward.RewardInfo;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.job.GameUtilJob;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RestController
@RequestMapping("/ws/configInfo")
public class WdConfigInfoController extends BaseController {

	@Autowired
	private ConfigInfoService cs;
	
	@PostMapping("/getConfigInfos")
	public ResponseView getConfigInfos(Page<Object> page, ConfigInfo configInfo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(ConfigInfo.class);
		example.excludeProperties("data");
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isNullOrEmpty(configInfo.getKeyName())) {
			createCriteria.andLike("keyName", "%"+configInfo.getKeyName()+"%");
		}
		if(!StringUtils.isNullOrEmpty(configInfo.getAliasName())) {
			createCriteria.andEqualTo("aliasName", configInfo.getAliasName());
		}
		List<ConfigInfo> selectAll = cs.selectByExample(example);
		PageInfo<ConfigInfo> pageInfo = new PageInfo<ConfigInfo>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	@PostMapping("/addConfigInfo")
	public ResponseView addConfigInfo(ConfigInfo configInfo) {
		configInfo.setUuid(UUID.randomUUID().toString().replace("-", ""));
		configInfo.setAddTime(new Date());
		cs.insertSelective(configInfo);
		return ResponseView.ok();
	}
	
	@PostMapping("/getConfigInfoDataById")
	public ResponseView getConfigInfoDataById(ConfigInfo configInfo) {
		Example example = new Example(ConfigInfo.class);
		example.selectProperties("data");
		example.createCriteria().andEqualTo("id", configInfo.getId());
		return ResponseView.ok(cs.selectOneByExample(example));
	}
	
	/**
	 * a根据多个UUID获取配置信息
	 * @param uuids 一组特定的UUID
	 * @return
	 */
	@PostMapping("/getConfigInfoDataByUUIDS")
	public ResponseView getConfigInfoDataByUUIDS(String uuids) {
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andIn("uuid", Lists.newArrayList(uuids.split(",")));
		return ResponseView.ok(cs.selectByExample(example));
	}
	
	/**
	 * a根据多个UUID获取配置信息
	 * @param uuids 一组特定的UUID
	 * @return
	 */
	@PostMapping("/getConfigInfoDataByUUIDSToMap")
	public ResponseView getConfigInfoDataByUUIDSToMap(String uuids) {
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andIn("uuid", Lists.newArrayList(uuids.split(",")));
		Map<String, ConfigInfo> collect = cs.selectByExample(example).stream().collect(Collectors.toMap(ConfigInfo::getUuid,a->a,(k1,k2)->k2));
		return ResponseView.ok(collect);
	}
	
	
	@PostMapping("/getConfigInfoData")
	public ResponseView getConfigInfoData(ConfigInfo configInfo) {
		return ResponseView.ok(cs.select(configInfo));
	}
	
	@PostMapping("/getOneConfigInfoData")
	public ResponseView getOneConfigInfoData(ConfigInfo configInfo) {
		return ResponseView.ok(cs.selectOne(configInfo));
	}
	
	@PostMapping("/delConfigInfoById")
	public ResponseView delConfigInfoById(int id) {
		cs.deleteByPrimaryKey(id);
		return ResponseView.ok();
	}
	
	@PostMapping("/updateConfigInfoById")
	public ResponseView updateConfigInfoById(ConfigInfo configInfo) {
		String data = configInfo.getData();
		configInfo.setData(data.replaceAll("\\s*", "").trim());
		cs.updateByPrimaryKeySelective(configInfo);
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	@PostMapping("/updateConfigInfoByUUID")
	public ResponseView updateConfigInfoByUUID(ConfigInfo configInfo) {
		cs.updateByPrimaryKeySelective(configInfo);
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	
	@PostMapping("/updateConfigInfoByUUID2")
	public ResponseView updateConfigInfoByUUID2(ConfigInfo configInfo) {
		Example example = new Example(ConfigInfo.class);
		example.selectProperties("data","id");
		example.createCriteria().andEqualTo("uuid", configInfo.getUuid());
		cs.updateByExampleSelective(configInfo, example);
		cs.refreshCache();
		//如果别名是带有timer表示为REDIS定时器
		if("redis_timer".equals(configInfo.getAliasName())) {
			JSONObject parseObject = JSONObject.parseObject(configInfo.getData());
			GameData.that.redisUtils.set(configInfo.getUuid(), "", parseObject.getIntValue("time"), TimeUnit.MINUTES);
		}
		return ResponseView.ok();
	}
	
	@PostMapping("/getConfigInfoDataByUUID")
	public ResponseView getConfigInfoDataByUUID(String uuid) {
		Example example = new Example(ConfigInfo.class);
		example.selectProperties("data","id");
		example.createCriteria().andEqualTo("uuid", uuid);
		return ResponseView.ok(cs.selectOneByExample(example));
	}
	
	@PostMapping("/updateConfigInfos")
	public ResponseView updateConfigInfos(@RequestBody List<ConfigInfo> configInfos) {
		for(ConfigInfo configInfo:configInfos) {
			Example example = new Example(ConfigInfo.class);
			example.createCriteria().andEqualTo("uuid", configInfo.getUuid());
			cs.updateByExampleSelective(configInfo, example);
			if("new_year_beast_time".equals(configInfo.getUuid())) {
				GameUtilJob.new_year_beast_time = configInfo;
			}
		}
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	/**
	 * 更新VIP福利配置
	 * @param config
	 * @return
	 */
	@PostMapping("/updateVipConfig")
	public ResponseView updateVipConfig(@RequestParam Map<String,Object> config) {
		VipChargeConfig vip = new VipChargeConfig();
		vip.setMaxMoney(MapUtils.getIntValue(config, "maxMoney"));
		vip.setMinMoney(MapUtils.getIntValue(config, "minMoney"));
		//唯一标识
		String uid = (String) config.get("uid");
		//vip配置
		ConfigInfo vipc = GameData.that.configInfoService.getOneByKeyName("VIP_FULI_CONFIG");
		//解析
		List<VipChargeConfig> vipConfigs = com.alibaba.fastjson.JSONObject.parseArray(vipc.getData(),VipChargeConfig.class);
		vipConfigs.sort(new Comparator<VipChargeConfig>() {
			@Override
			public int compare(VipChargeConfig o1, VipChargeConfig o2) {
				return o1.getMinMoney().compareTo(o2.getMinMoney());
			}
		});
		Iterator<VipChargeConfig> iterator = vipConfigs.iterator();
		while(iterator.hasNext()) {
			VipChargeConfig vipConfig = iterator.next();
			//如果满足要求就删除后在添加新的
			if(vipConfig.getUuid().equals(uid)) {
				iterator.remove();
				Reward reward = new Reward();
				reward.setTask(JSONObject.parseArray((String) config.get("task"),RewardInfo.class));
				reward.setValue(JSONObject.parseArray((String) config.get("value"),RewardInfo.class));
				vip.setReward(reward);
				vip.setUuid(uid);
				//保存信息
				vipConfigs.add(vip);
				//重新设置信息
				vipc.setData(JSONObject.toJSONString(vipConfigs));
				//更新
				Example example = new Example(ConfigInfo.class);
				example.createCriteria().andEqualTo("uuid", "VIP_FULI_CONFIG");
				GameData.that.configInfoService.updateByExampleSelective(vipc, example);
				cs.refreshCache();
				break;
			}
		}
		return ResponseView.ok();
	}
	
	@PostMapping("/addVipConfig")
	public ResponseView addVipConfig(@RequestParam Map<String,Object> config) {
		//新数据
		VipChargeConfig vip = new VipChargeConfig();
		vip.setMaxMoney(MapUtils.getIntValue(config, "maxMoney"));
		vip.setMinMoney(MapUtils.getIntValue(config, "minMoney"));
		//vip配置
		ConfigInfo vipc = GameData.that.configInfoService.getOneByKeyName("VIP_FULI_CONFIG");
		//解析
		List<VipChargeConfig> vipConfigs = com.alibaba.fastjson.JSONObject.parseArray(vipc.getData(),VipChargeConfig.class);
		Reward reward = new Reward();
		reward.setTask(JSONObject.parseArray((String) config.get("task"),RewardInfo.class));
		reward.setValue(JSONObject.parseArray((String) config.get("value"),RewardInfo.class));
		vip.setReward(reward);
		vip.setUuid(GameCommonUtil.UUID());
		//保存信息
		vipConfigs.add(vip);
		//重新设置信息
		vipc.setData(JSONObject.toJSONString(vipConfigs));
		//更新
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", "VIP_FULI_CONFIG");
		GameData.that.configInfoService.updateByExampleSelective(vipc, example);
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	@PostMapping("/delVipConfig")
	public ResponseView delVipConfig(String uid) {
		//vip配置
		ConfigInfo vipc = GameData.that.configInfoService.getOneByKeyName("VIP_FULI_CONFIG");
		//解析
		List<VipChargeConfig> vipConfigs = com.alibaba.fastjson.JSONObject.parseArray(vipc.getData(),VipChargeConfig.class);
		Iterator<VipChargeConfig> iterator = vipConfigs.iterator();
		while(iterator.hasNext()) {
			VipChargeConfig vipConfig = iterator.next();
			//如果满足要求就删除后在添加新的
			if(vipConfig.getUuid().equals(uid)) {
				iterator.remove();
				break;
			}
		}
		//重新设置信息
		vipc.setData(JSONObject.toJSONString(vipConfigs));
		//更新
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", "VIP_FULI_CONFIG");
		GameData.that.configInfoService.updateByExampleSelective(vipc, example);
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	
	/**
	 * 修改升级奖励大使
	 * @param config
	 * @return
	 */
	@PostMapping("/updateNpcForReward")
	public ResponseView updateNpcForReward(@RequestParam Map<String,Object> config) {
		ConfigInfo info = GameData.that.configInfoService.getOneByUuid("8afc361ba57649b093c2b480a00897b1");
		JSONObject parseObject = JSONObject.parseObject(info.getData());
		for(Map.Entry<String, Object> m:config.entrySet()) {
			JSONObject jsonObject = parseObject.getJSONObject(m.getKey());
			if(jsonObject != null) {
				String value = (String) m.getValue();
				jsonObject.put("reward", value);
			}
		}
		//更新
		info.setData(parseObject.toJSONString());
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", "8afc361ba57649b093c2b480a00897b1");
		GameData.that.configInfoService.updateByExampleSelective(info, example);
		cs.refreshCache();
		return ResponseView.ok();
	}
	
	/**
	 * 获取内丹配置
	 * @return
	 */
	@PostMapping("/getNeiDanConfig")
	public ResponseView getNeiDanConfig() {
		return ResponseView.ok(GameConfig.neiDanConfig);
	}
	
	/**
	 * 修改内丹配置
	 * @return
	 */
	@PostMapping("/updateNeiDanConfig")
	public ResponseView updateNeiDanConfig(String info) {
		NeiDanConfig parseObject = JSONObject.parseObject(info,NeiDanConfig.class);
		//重新替换配置
		GameConfig.neiDanConfig = parseObject;
		//重新设置到数据库
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", "c7608ca8c7af4a30b7f4f46e7ad47ad3");
		ConfigInfo infos = new ConfigInfo();
		infos.setData(JSONObject.toJSONString(parseObject));
		GameData.that.configInfoService.updateByExampleSelective(infos, example);
		cs.refreshCache();
		return ResponseView.ok();
	}

	
	/**
	 * 获取擂台配置
	 * @return
	 */
	@PostMapping("/getCtConfig")
	public ResponseView getCtConfig() {
		return ResponseView.ok(GameCore.ctConfig);
	}
	
	/**
	 * 修改擂台配置
	 * @param ctConfig 擂台配置
	 * @return
	 */
	@PostMapping("/updateCtConfig")
	public ResponseView updateCtConfig(CtConfig ctConfig) {
		GameCore.ctConfig = ctConfig;
		//重新设置到数据库
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", "ct_config");
		ConfigInfo info = new ConfigInfo();
		info.setData(JSONObject.toJSONString(ctConfig));
		GameData.that.configInfoService.updateByExampleSelective(info, example);
		cs.refreshCache();
		return ResponseView.ok();
	}
}