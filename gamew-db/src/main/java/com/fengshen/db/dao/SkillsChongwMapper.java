package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SkillsChongwMapper
{
    long countByExample(final SkillsChongwExample paramSkillsChongwExample);
    
    int deleteByExample(final SkillsChongwExample paramSkillsChongwExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final SkillsChongw paramSkillsChongw);
    
    int insertSelective(final SkillsChongw paramSkillsChongw);
    
    SkillsChongw selectOneByExample(final SkillsChongwExample paramSkillsChongwExample);
    
    SkillsChongw selectOneByExampleSelective(@Param("example") final SkillsChongwExample paramSkillsChongwExample, @Param("selective") final SkillsChongw.Column... paramVarArgs);
    
    List<SkillsChongw> selectByExampleSelective(@Param("example") final SkillsChongwExample paramSkillsChongwExample, @Param("selective") final SkillsChongw.Column... paramVarArgs);
    
    List<SkillsChongw> selectByExample(final SkillsChongwExample paramSkillsChongwExample);
    
    SkillsChongw selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final SkillsChongw.Column... paramVarArgs);
    
    SkillsChongw selectByPrimaryKey(final Integer paramInteger);
    
    SkillsChongw selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final SkillsChongw paramSkillsChongw, @Param("example") final SkillsChongwExample paramSkillsChongwExample);
    
    int updateByExample(@Param("record") final SkillsChongw paramSkillsChongw, @Param("example") final SkillsChongwExample paramSkillsChongwExample);
    
    int updateByPrimaryKeySelective(final SkillsChongw paramSkillsChongw);
    
    int updateByPrimaryKey(final SkillsChongw paramSkillsChongw);
    
    int logicalDeleteByExample(@Param("example") final SkillsChongwExample paramSkillsChongwExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
