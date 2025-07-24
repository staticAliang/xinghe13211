package com.fengshen.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Characters;
import com.fengshen.db.service.base.BaseCharactersService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CharacterService extends BaseCharactersService {

	/**
	 * 重置任务过滤
	 * 
	 * @param gids
	 * @return
	 */
	public List<Characters> getResetRenwu(List<String> gids) {
		Example example = new Example(Characters.class);
		example.selectProperties("data","gid","id");
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("deleted", false);
		if (gids != null && !gids.isEmpty()) {
			createCriteria.andNotIn("gid",gids);
		}
		return this.mapper.selectByExample(example);
	}
	
	public List<Characters> findByAccountIdManage(Integer id) {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("accountId", id);
		return this.mapper.selectByExample(example);
	}
	
	
}
