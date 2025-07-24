package com.fengshen.db.auth;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface DailiMapper
{
    long countByExample(final DailiExample paramDailiExample);
    
    int deleteByExample(final DailiExample paramDailiExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Daili paramDaili);
    
    int insertSelective(final Daili paramDaili);
    
    Daili selectOneByExample(final DailiExample paramDailiExample);
    
    Daili selectOneByExampleSelective(@Param("example") final DailiExample paramDailiExample, @Param("selective") final Daili.Column... paramVarArgs);
    
    List<Daili> selectByExampleSelective(@Param("example") final DailiExample paramDailiExample, @Param("selective") final Daili.Column... paramVarArgs);
    
    List<Daili> selectByExample(final DailiExample paramDailiExample);
    
    Daili selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Daili.Column... paramVarArgs);
    
    Daili selectByPrimaryKey(final Integer paramInteger);
    
    Daili selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Daili paramDaili, @Param("example") final DailiExample paramDailiExample);
    
    int updateByExample(@Param("record") final Daili paramDaili, @Param("example") final DailiExample paramDailiExample);
    
    int updateByPrimaryKeySelective(final Daili paramDaili);
    
    int updateByPrimaryKey(final Daili paramDaili);
    
    int logicalDeleteByExample(@Param("example") final DailiExample paramDailiExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
