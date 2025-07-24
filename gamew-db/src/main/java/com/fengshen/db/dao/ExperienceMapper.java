package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ExperienceMapper
{
    long countByExample(final ExperienceExample paramExperienceExample);
    
    int deleteByExample(final ExperienceExample paramExperienceExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Experience paramExperience);
    
    int insertSelective(final Experience paramExperience);
    
    Experience selectOneByExample(final ExperienceExample paramExperienceExample);
    
    Experience selectOneByExampleSelective(@Param("example") final ExperienceExample paramExperienceExample, @Param("selective") final Experience.Column... paramVarArgs);
    
    List<Experience> selectByExampleSelective(@Param("example") final ExperienceExample paramExperienceExample, @Param("selective") final Experience.Column... paramVarArgs);
    
    List<Experience> selectByExample(final ExperienceExample paramExperienceExample);
    
    Experience selectByPrimaryKeySelective(@Param("attrib") final Integer paramInteger, @Param("selective") final Experience.Column... paramVarArgs);
    
    Experience selectByPrimaryKey(final Integer paramInteger);
    
    Experience selectByPrimaryKeyWithLogicalDelete(@Param("attrib") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Experience paramExperience, @Param("example") final ExperienceExample paramExperienceExample);
    
    int updateByExample(@Param("record") final Experience paramExperience, @Param("example") final ExperienceExample paramExperienceExample);
    
    int updateByPrimaryKeySelective(final Experience paramExperience);
    
    int updateByPrimaryKey(final Experience paramExperience);
    
    int logicalDeleteByExample(@Param("example") final ExperienceExample paramExperienceExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
