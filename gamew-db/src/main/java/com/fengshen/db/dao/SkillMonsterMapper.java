package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SkillMonsterMapper
{
    long countByExample(final SkillMonsterExample paramSkillMonsterExample);
    
    int deleteByExample(final SkillMonsterExample paramSkillMonsterExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final SkillMonster paramSkillMonster);
    
    int insertSelective(final SkillMonster paramSkillMonster);
    
    SkillMonster selectOneByExample(final SkillMonsterExample paramSkillMonsterExample);
    
    SkillMonster selectOneByExampleSelective(@Param("example") final SkillMonsterExample paramSkillMonsterExample, @Param("selective") final SkillMonster.Column... paramVarArgs);
    
    List<SkillMonster> selectByExampleSelective(@Param("example") final SkillMonsterExample paramSkillMonsterExample, @Param("selective") final SkillMonster.Column... paramVarArgs);
    
    List<SkillMonster> selectByExample(final SkillMonsterExample paramSkillMonsterExample);
    
    SkillMonster selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final SkillMonster.Column... paramVarArgs);
    
    SkillMonster selectByPrimaryKey(final Integer paramInteger);
    
    SkillMonster selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final SkillMonster paramSkillMonster, @Param("example") final SkillMonsterExample paramSkillMonsterExample);
    
    int updateByExample(@Param("record") final SkillMonster paramSkillMonster, @Param("example") final SkillMonsterExample paramSkillMonsterExample);
    
    int updateByPrimaryKeySelective(final SkillMonster paramSkillMonster);
    
    int updateByPrimaryKey(final SkillMonster paramSkillMonster);
    
    int logicalDeleteByExample(@Param("example") final SkillMonsterExample paramSkillMonsterExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
