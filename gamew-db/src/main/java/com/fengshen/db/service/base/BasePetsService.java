package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.PetsMapper;
import com.fengshen.db.domain.Pets;
import com.fengshen.db.domain.example.PetsExample;
import com.github.pagehelper.PageHelper;

@Service
public class BasePetsService {
	@Autowired
	protected PetsMapper mapper;

	public Pets findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Pets findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Pets pets) {
		pets.setAddTime(LocalDateTime.now());
		pets.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(pets);
	}

	public int updateById(final Pets pets) {
		pets.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(pets);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Pets> findByOwnerid(final String ownerid) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andOwneridEqualTo(ownerid);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByPetid(final String petid) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPetidEqualTo(petid);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByNickname(final String nickname) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNicknameEqualTo(nickname);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByName(final String name) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByHorsetype(final Integer horsetype) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andHorsetypeEqualTo(horsetype);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByType(final Integer type) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByLevel(final Integer level) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByLiliang(final Integer liliang) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLiliangEqualTo(liliang);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByMinjie(final Integer minjie) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMinjieEqualTo(minjie);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByLingli(final Integer lingli) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLingliEqualTo(lingli);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByTili(final Integer tili) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTiliEqualTo(tili);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByDianhualx(final Integer dianhualx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhualxEqualTo(dianhualx);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByDianhuazd(final Integer dianhuazd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhuazdEqualTo(dianhuazd);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByDianhuazx(final Integer dianhuazx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhuazxEqualTo(dianhuazx);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByYuhualx(final Integer yuhualx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhualxEqualTo(yuhualx);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByYuhuazd(final Integer yuhuazd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhuazdEqualTo(yuhuazd);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByYuhuazx(final Integer yuhuazx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhuazxEqualTo(yuhuazx);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwjyzx(final Integer cwjyzx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwjyzxEqualTo(cwjyzx);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwjyzd(final Integer cwjyzd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwjyzdEqualTo(cwjyzd);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByFeisheng(final Integer feisheng) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFeishengEqualTo(feisheng);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByFsudu(final Integer fsudu) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFsuduEqualTo(fsudu);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByQhcwWg(final Integer qhcwWg) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQhcwWgEqualTo(qhcwWg);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByQhcwFg(final Integer qhcwFg) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQhcwFgEqualTo(qhcwFg);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwXiangxing(final Integer cwXiangxing) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwXiangxingEqualTo(cwXiangxing);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwWuxue(final Integer cwWuxue) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwWuxueEqualTo(cwWuxue);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwIcon(final String cwIcon) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwIconEqualTo(cwIcon);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwXinfa(final Integer cwXinfa) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwXinfaEqualTo(cwXinfa);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findByCwQinmi(final Integer cwQinmi) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwQinmiEqualTo(cwQinmi);
		return this.mapper.selectByExample(example);
	}

	public Pets findOneByOwnerid(final String ownerid) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andOwneridEqualTo(ownerid);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByPetid(final String petid) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPetidEqualTo(petid);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByNickname(final String nickname) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNicknameEqualTo(nickname);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByName(final String name) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByHorsetype(final Integer horsetype) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andHorsetypeEqualTo(horsetype);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByType(final Integer type) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByLevel(final Integer level) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByLiliang(final Integer liliang) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLiliangEqualTo(liliang);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByMinjie(final Integer minjie) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMinjieEqualTo(minjie);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByLingli(final Integer lingli) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLingliEqualTo(lingli);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByTili(final Integer tili) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTiliEqualTo(tili);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByDianhualx(final Integer dianhualx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhualxEqualTo(dianhualx);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByDianhuazd(final Integer dianhuazd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhuazdEqualTo(dianhuazd);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByDianhuazx(final Integer dianhuazx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDianhuazxEqualTo(dianhuazx);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByYuhualx(final Integer yuhualx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhualxEqualTo(yuhualx);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByYuhuazd(final Integer yuhuazd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhuazdEqualTo(yuhuazd);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByYuhuazx(final Integer yuhuazx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYuhuazxEqualTo(yuhuazx);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwjyzx(final Integer cwjyzx) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwjyzxEqualTo(cwjyzx);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwjyzd(final Integer cwjyzd) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwjyzdEqualTo(cwjyzd);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByFeisheng(final Integer feisheng) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFeishengEqualTo(feisheng);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByFsudu(final Integer fsudu) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFsuduEqualTo(fsudu);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByQhcwWg(final Integer qhcwWg) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQhcwWgEqualTo(qhcwWg);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByQhcwFg(final Integer qhcwFg) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQhcwFgEqualTo(qhcwFg);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwXiangxing(final Integer cwXiangxing) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwXiangxingEqualTo(cwXiangxing);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwWuxue(final Integer cwWuxue) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwWuxueEqualTo(cwWuxue);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwIcon(final String cwIcon) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwIconEqualTo(cwIcon);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwXinfa(final Integer cwXinfa) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwXinfaEqualTo(cwXinfa);
		return this.mapper.selectOneByExample(example);
	}

	public Pets findOneByCwQinmi(final Integer cwQinmi) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCwQinmiEqualTo(cwQinmi);
		return this.mapper.selectOneByExample(example);
	}

	public List<Pets> findAll(final int page, final int size, final String sort, final String order) {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Pets> findAll() {
		final PetsExample example = new PetsExample();
		final PetsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
