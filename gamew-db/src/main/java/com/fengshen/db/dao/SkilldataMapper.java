package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SkilldataMapper
{
    long countByExample(final SkilldataExample paramSkilldataExample);
    
    int deleteByExample(final SkilldataExample paramSkilldataExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Skilldata paramSkilldata);
    
    int insertSelective(final Skilldata paramSkilldata);
    
    Skilldata selectOneByExample(final SkilldataExample paramSkilldataExample);
    
    Skilldata selectOneByExampleSelective(@Param("example") final SkilldataExample paramSkilldataExample, @Param("selective") final Skilldata.Column... paramVarArgs);
    
    List<Skilldata> selectByExampleSelective(@Param("example") final SkilldataExample paramSkilldataExample, @Param("selective") final Skilldata.Column... paramVarArgs);
    
    List<Skilldata> selectByExample(final SkilldataExample paramSkilldataExample);
    
    Skilldata selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Skilldata.Column... paramVarArgs);
    
    Skilldata selectByPrimaryKey(final Integer paramInteger);
    
    Skilldata selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Skilldata paramSkilldata, @Param("example") final SkilldataExample paramSkilldataExample);
    
    int updateByExample(@Param("record") final Skilldata paramSkilldata, @Param("example") final SkilldataExample paramSkilldataExample);
    
    int updateByPrimaryKeySelective(final Skilldata paramSkilldata);
    
    int updateByPrimaryKey(final Skilldata paramSkilldata);
    
    int logicalDeleteByExample(@Param("example") final SkilldataExample paramSkilldataExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
