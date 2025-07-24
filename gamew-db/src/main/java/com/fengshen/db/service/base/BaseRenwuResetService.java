package com.fengshen.db.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.dao.RenwuResetMapper;
import com.fengshen.db.domain.RenwuReset;

@Service
public class BaseRenwuResetService {
	@Autowired
	protected RenwuResetMapper mapper;

	public List<RenwuReset> select(final RenwuReset renwuReset) {
		return this.mapper.select(renwuReset);
	}

	public int insert(final RenwuReset renwuReset) {
		return this.mapper.insert(renwuReset);
	}

	public int update(final RenwuReset renwuReset) {
		return this.mapper.update(renwuReset);
	}

	public int delete() {
		return this.mapper.delete();
	}
}
