package com.fengshen.db.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.dao.ChargePointMapper;
import com.fengshen.db.domain.ChargePoint;
import com.fengshen.db.domain.ChargePointExample;

/**
 * 充值积分
 */
@Service
public class BaseChargePointService {
    @Autowired
    protected ChargePointMapper mapper;

    public BaseChargePointService() {
    }

    public List<ChargePoint> findAll() {
        ChargePointExample example = new ChargePointExample();
        return this.mapper.selectByExample(example);
    }

    public ChargePoint findByNo(int no) {
        ChargePointExample example = new ChargePointExample();
        ChargePointExample.Criteria criteria = example.createCriteria();
        criteria.andNoEqualTo(no);
        List<ChargePoint> list =  this.mapper.selectByExample(example);
        return list==null||list.isEmpty()?null:list.get(0);
    }
    
    public int update(ChargePoint chargePoint) {
        return this.mapper.updateByPrimaryKey(chargePoint);
    }
    
    public int updateById(ChargePoint chargePoint) {
        return this.mapper.updateByPrimaryKeySelective(chargePoint);
    }
    
    public int add(ChargePoint chargePoint) {
        return this.mapper.insertSelective(chargePoint);
    }
    
    public int deleteById(ChargePoint chargePoint) {
        return this.mapper.deleteByPrimaryKey(chargePoint.getId());
    }
}
