package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ChargeGetRecordMapper;
import com.fengshen.db.domain.ChargeGetRecord;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class ChargeGetRecordService implements BaseServiceSupport<ChargeGetRecord> {

	@Autowired
	private ChargeGetRecordMapper cm;

	@Override
	public BaseCustomMapper<ChargeGetRecord> getBaseMapper() {
		return cm;
	}
	
	/**
	 * 获取用户领取状态
	 * @param gid uuid
	 * @param money 金额
	 * @return
	 */
	public int getUserChargeGetRecords(String account, int money) {
		Example example = new Example(ChargeGetRecord.class);
		example.createCriteria().andEqualTo("account", account).andEqualTo("money", money);
		return cm.selectCountByExample(example);
	}

	public int getUserChargeGetRecordsDanBi(String account, int money) {
		Example example = new Example(ChargeGetRecord.class);
		example.createCriteria().andEqualTo("account", account).andEqualTo("status", 0).andEqualTo("money", money);
		return cm.selectCountByExample(example);
	}
}
