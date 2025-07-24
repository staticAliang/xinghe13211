package com.fengshen.db.service.chara;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.LivenessRewardsMapper;
import com.fengshen.db.domain.LivenessRewards;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class LivenessRewardsService implements BaseServiceSupport<LivenessRewards> {

	@Autowired
	private LivenessRewardsMapper lrm;
	
	@Override
	public BaseCustomMapper<LivenessRewards> getBaseMapper() {
		return lrm;
	}

	/**
	 * 根据用户uuid获取今天
	 * 的所有活跃度奖励是否领取
	 * @param gid
	 * @return
	 */
	public Map<Integer,List<LivenessRewards>> getLivenessRewardsByActiveToDay(String gid) {
		Example example = new Example(LivenessRewards.class);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = dateTimeFormatter.format(LocalDateTime.now());
		example.createCriteria().andCondition("DATE_FORMAT(add_time,\"%Y-%m-%d\")=", format)
		.andEqualTo("gid", gid);
		List<LivenessRewards> list = lrm.selectByExample(example);
		Map<Integer, List<LivenessRewards>> collect = list.stream().collect(Collectors.groupingBy(LivenessRewards::getActivity));
		return collect;
	}
	
	/**
	 * 根据用户uuid获取今天某个活跃度是否领取
	 * @param gid
	 * @return
	 */
	public int getLivenessRewardsIsGet(String gid, int activity) {
		Example example = new Example(LivenessRewards.class);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = dateTimeFormatter.format(LocalDateTime.now());
		example.createCriteria().andCondition("DATE_FORMAT(add_time,\"%Y-%m-%d\")=", format)
		.andEqualTo("gid", gid).andEqualTo("activity", activity);
		return lrm.selectCountByExample(example);
	}
}