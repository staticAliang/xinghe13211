package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface NpcPointMapper
{
    long countByExample(final NpcPointExample paramNpcPointExample);
    
    int deleteByExample(final NpcPointExample paramNpcPointExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final NpcPoint paramNpcPoint);
    
    int insertSelective(final NpcPoint paramNpcPoint);
    
    NpcPoint selectOneByExample(final NpcPointExample paramNpcPointExample);
    
    NpcPoint selectOneByExampleSelective(@Param("example") final NpcPointExample paramNpcPointExample, @Param("selective") final NpcPoint.Column... paramVarArgs);
    
    List<NpcPoint> selectByExampleSelective(@Param("example") final NpcPointExample paramNpcPointExample, @Param("selective") final NpcPoint.Column... paramVarArgs);
    
    List<NpcPoint> selectByExample(final NpcPointExample paramNpcPointExample);
    
    NpcPoint selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final NpcPoint.Column... paramVarArgs);
    
    NpcPoint selectByPrimaryKey(final Integer paramInteger);
    
    NpcPoint selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final NpcPoint paramNpcPoint, @Param("example") final NpcPointExample paramNpcPointExample);
    
    int updateByExample(@Param("record") final NpcPoint paramNpcPoint, @Param("example") final NpcPointExample paramNpcPointExample);
    
    int updateByPrimaryKeySelective(final NpcPoint paramNpcPoint);
    
    int updateByPrimaryKey(final NpcPoint paramNpcPoint);
    
    int logicalDeleteByExample(@Param("example") final NpcPointExample paramNpcPointExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
