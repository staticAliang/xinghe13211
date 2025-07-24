package com.fengshen.db.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.CharactersMapper;
import com.fengshen.db.domain.Characters;
import com.mysql.jdbc.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BaseCharactersService implements BaseServiceSupport<Characters>{
	@Autowired
	protected CharactersMapper mapper;

	public Characters findById(final int id) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("id", id);
		return this.mapper.selectByPrimaryKey(id);
	}

	public Characters findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Characters characters) {
		characters.setAddTime(new Date());
		characters.setUpdateTime(new Date());
		this.mapper.insertSelective(characters);
	}

	public int updateById(final Characters characters) {
		characters.setUpdateTime(new Date());
		return this.mapper.updateByPrimaryKeySelective(characters);
	}
	
	public int updateSelective(final Characters characters) {
		characters.setUpdateTime(new Date());
		return this.mapper.updateByPrimaryKeySelective(characters);
	}

	public void deleteById(final int id) {
		this.mapper.deleteByPrimaryKey(id);
	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Characters> findByName(final String name) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name);
		return this.mapper.selectByExample(example);
	}

	public List<Characters> findByAccountId(final Integer accountId) {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("accountId", accountId);
		return this.mapper.selectByExample(example);
	}

	/**
	 * 没有调用
	 * @param gid
	 * @return
	 */
	public List<Characters> findByGid(final String gid) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("gid", gid);
		return this.mapper.selectByExample(example);
	}


	public Characters findOneByName(final String name) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name.replaceAll("\\s*", ""));
		return this.mapper.selectOneByExample(example);
	}
	
	public Characters findOneBlobByName(final String name) {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name.replaceAll("\\s*", ""));
		return this.mapper.selectOneByExample(example);
	}
	
	public Characters login(int accountId, String name) {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name.replaceAll("\\s*", "")).andEqualTo("accountId", accountId);
		return this.mapper.selectOneByExample(example);
	}
	
	public Characters findOneByNameSelectProperties(final String name, String... propString) {
		Example example = new Example(Characters.class);
		example.selectProperties(propString);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name.replaceAll("\\s*", ""));
		return this.mapper.selectOneByExample(example);
	}
	public Characters findOneByGidSelectProperties(final String gid, String... propString) {
		Example example = new Example(Characters.class);
		example.selectProperties(propString);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("gid", gid);
		return this.mapper.selectOneByExample(example);
	}
	
	public Characters findOneByIdSelectProperties(final Integer id, String... propString) {
		Example example = new Example(Characters.class);
		example.selectProperties(propString);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("id", id);
		return this.mapper.selectOneByExample(example);
	}
	
	public List<Characters> findByObjSelectProperties(Characters chara, String... propString) {
		Example example = new Example(Characters.class);
		example.selectProperties(propString);
		if(chara != null) {
			example.createCriteria().andEqualTo(chara);
		}
		return this.mapper.selectByExample(example);
	}
	
	public List<Characters> listjiaose(Characters chara, String... propString) {
		Example example = new Example(Characters.class);
		example.selectProperties(propString);
		Criteria createCriteria = example.createCriteria();
		if(chara != null) {
			createCriteria.andEqualTo(chara);
		}
		createCriteria.andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}
	

	/**
	 * 没人使用
	 * @param accountId
	 * @return
	 */
	public Characters findOneByAccountId(final Integer accountId) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("accountId", accountId);
		return this.mapper.selectOneByExample(example);
	}

	public Characters findOneByGid(final String gid) {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("gid", gid);
		return this.mapper.selectOneByExample(example);
	}


	public List<Characters> findAll() {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}

	public List<Characters> findAllByBolb() {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}

	public Characters findOneByGid2(String gid) {
		Example example = new Example(Characters.class);
		example.createCriteria().andEqualTo("gid", gid);
		return this.mapper.selectOneByExample(example);
	}

	public long getOnlines() {
		Example example = new Example(Characters.class);
		example.excludeProperties("data","cangku","shizhuang","genchong","texiao","backpack",
				"listshouhu","cardStore");
		example.createCriteria().andEqualTo("online", 1).andEqualTo("xiaozi", 0);
		return this.mapper.selectCountByExample(example);
	}

	@Override
	public BaseCustomMapper<Characters> getBaseMapper() {
		return mapper;
	}
	
	public List<Characters> selectAll(String name) {
		Example example = new Example(Characters.class);
		example.orderBy("online").desc();
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isNullOrEmpty(name)) {
			createCriteria.andLike("name", "%"+name+"%");
    	}
		return this.mapper.selectByExample(example);
	}
	
	//获取在线人员
	public List<Characters> getOnLineChar(int accountid, String... prop) {
		Example example = new Example(Characters.class);
		example.selectProperties(prop);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("online", 1);
		createCriteria.andEqualTo("accountId", accountid);
		createCriteria.andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}
	
	/**
	 * 获取排行榜信息
	 * @return
	 */
	public List<Characters> getRankData() {
		return this.mapper.getRankData();
	}
	
	/**
	 * 动态获取近期注册用户
	 * @param ch
	 * @return
	 */
	public List<Characters> getLastLoginTimeData(int level, int day) {
		Characters ch  = new Characters();
		ch.setLevel(level);
		ch.setOnline(day);
		return this.mapper.getLastLoginTimeData(ch);
	}
}
