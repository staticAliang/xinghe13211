package com.fengshen.db.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.StallRecordMapper;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

/**
 * a珍宝交易记录
 * 
 *
 */
@Service
public class StallRecordService implements BaseServiceSupport<StallRecord> {

	@Autowired
	private StallRecordMapper gsrm;
	
	@Override
	public BaseCustomMapper<StallRecord> getBaseMapper() {
		return gsrm;
	}

	/**
	 * a根据用户id和类型获取订单记录
	 * @param cid 用户id
	 * @param status 状态
	 * @return
	 */
	public List<StallRecord> getStallRecord(int cid, int status) {
		Example example = new Example(StallRecord.class);
		example.excludeProperties("data");
		example.createCriteria().andEqualTo("cid", cid).andEqualTo("status", status);
		return gsrm.selectByExample(example);
	}
	
	/**
	 * a根据用户id和类型获取订单记录
	 * @param cid 用户id
	 * @return
	 */
	public List<StallRecord> getStallRecordByStallRecordType(int cid, int stallRecordType) {
		Example example = new Example(StallRecord.class);
		example.excludeProperties("data");
		example.createCriteria().andEqualTo("cid", cid).andEqualTo("stallRecordType", stallRecordType);
		return gsrm.selectByExample(example);
	}
	
	public StallRecord getOneStallRecordByGoodsId(String goodsId) {
		Example example = new Example(StallRecord.class);
		example.excludeProperties("data");
		example.createCriteria().andEqualTo("goodsUuid", goodsId);
		return gsrm.selectOneByExample(example);
	}
	
}