package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ChargeMapper
{
    long countByExample(final ChargeExample paramChargeExample);
    
    int deleteByExample(final ChargeExample paramChargeExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Charge paramCharge);
    
    int insertSelective(final Charge paramCharge);
    
    Charge selectOneByExample(final ChargeExample paramChargeExample);
    
    Charge selectOneByExampleSelective(@Param("example") final ChargeExample paramChargeExample, @Param("selective") final Charge.Column... paramVarArgs);
    
    List<Charge> selectByExampleSelective(@Param("example") final ChargeExample paramChargeExample, @Param("selective") final Charge.Column... paramVarArgs);
    
    List<Charge> selectByExample(final ChargeExample paramChargeExample);
    
    Charge selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Charge.Column... paramVarArgs);
    
    Charge selectByPrimaryKey(final Integer paramInteger);
    
    Charge selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Charge paramCharge, @Param("example") final ChargeExample paramChargeExample);
    
    int updateByExample(@Param("record") final Charge paramCharge, @Param("example") final ChargeExample paramChargeExample);
    
    int updateByPrimaryKeySelective(final Charge paramCharge);
    
    int updateByPrimaryKey(final Charge paramCharge);
    
    int logicalDeleteByExample(@Param("example") final ChargeExample paramChargeExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
