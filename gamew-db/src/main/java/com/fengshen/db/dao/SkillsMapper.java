package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SkillsMapper
{
    long countByExample(final SkillsExample paramSkillsExample);
    
    int deleteByExample(final SkillsExample paramSkillsExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Skills paramSkills);
    
    int insertSelective(final Skills paramSkills);
    
    Skills selectOneByExample(final SkillsExample paramSkillsExample);
    
    Skills selectOneByExampleSelective(@Param("example") final SkillsExample paramSkillsExample, @Param("selective") final Skills.Column... paramVarArgs);
    
    List<Skills> selectByExampleSelective(@Param("example") final SkillsExample paramSkillsExample, @Param("selective") final Skills.Column... paramVarArgs);
    
    List<Skills> selectByExample(final SkillsExample paramSkillsExample);
    
    Skills selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Skills.Column... paramVarArgs);
    
    Skills selectByPrimaryKey(final Integer paramInteger);
    
    Skills selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Skills paramSkills, @Param("example") final SkillsExample paramSkillsExample);
    
    int updateByExample(@Param("record") final Skills paramSkills, @Param("example") final SkillsExample paramSkillsExample);
    
    int updateByPrimaryKeySelective(final Skills paramSkills);
    
    int updateByPrimaryKey(final Skills paramSkills);
    
    int logicalDeleteByExample(@Param("example") final SkillsExample paramSkillsExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
