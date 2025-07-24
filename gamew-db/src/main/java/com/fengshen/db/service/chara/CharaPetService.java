package com.fengshen.db.service.chara;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.CharaPetMapper;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class CharaPetService implements BaseServiceSupport<CharaPet>{

	@Autowired
	private CharaPetMapper cpm;
	
	
	@Override
	public BaseCustomMapper<CharaPet> getBaseMapper() {
		return cpm;
	}
	
	/**
	 * 批量更新
	 * @param charaPets
	 * @return
	 */
	public int updateBatch(List<CharaPet> charaPets) {
		return cpm.updateBatch(charaPets);
	}
	
	public int createPet(CharaPet pet) {
		pet.setAddTime(new Date());
		return cpm.insertSelective(pet);
	}
	
	public List<CharaPet> getPetsByCid(Integer cid) {
		Example example = new Example(CharaPet.class);
		example.createCriteria().andEqualTo("cid", cid);
		return this.selectByExample(example);
	}
	
	public List<CharaPet> getPetsByCuid(String cuid) {
		Example example = new Example(CharaPet.class);
		example.createCriteria().andEqualTo("uuid", cuid);
		return this.selectByExample(example);
	}
	
	public CharaPet getPetById(Integer id) {
		Example example = new Example(CharaPet.class);
		example.createCriteria().andEqualTo("id", id);
		return this.selectOneByExample(example);
	}
}