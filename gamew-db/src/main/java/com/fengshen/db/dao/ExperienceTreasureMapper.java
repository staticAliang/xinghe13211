package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ExperienceTreasureMapper
{
    long countByExample(final ExperienceTreasureExample paramExperienceTreasureExample);
    
    int deleteByExample(final ExperienceTreasureExample paramExperienceTreasureExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final ExperienceTreasure paramExperienceTreasure);
    
    int insertSelective(final ExperienceTreasure paramExperienceTreasure);
    
    ExperienceTreasure selectOneByExample(final ExperienceTreasureExample paramExperienceTreasureExample);
    
    ExperienceTreasure selectOneByExampleSelective(@Param("example") final ExperienceTreasureExample paramExperienceTreasureExample, @Param("selective") final ExperienceTreasure.Column... paramVarArgs);
    
    List<ExperienceTreasure> selectByExampleSelective(@Param("example") final ExperienceTreasureExample paramExperienceTreasureExample, @Param("selective") final ExperienceTreasure.Column... paramVarArgs);
    
    List<ExperienceTreasure> selectByExample(final ExperienceTreasureExample paramExperienceTreasureExample);
    
    ExperienceTreasure selectByPrimaryKeySelective(@Param("attrib") final Integer paramInteger, @Param("selective") final ExperienceTreasure.Column... paramVarArgs);
    
    ExperienceTreasure selectByPrimaryKey(final Integer paramInteger);
    
    ExperienceTreasure selectByPrimaryKeyWithLogicalDelete(@Param("attrib") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final ExperienceTreasure paramExperienceTreasure, @Param("example") final ExperienceTreasureExample paramExperienceTreasureExample);
    
    int updateByExample(@Param("record") final ExperienceTreasure paramExperienceTreasure, @Param("example") final ExperienceTreasureExample paramExperienceTreasureExample);
    
    int updateByPrimaryKeySelective(final ExperienceTreasure paramExperienceTreasure);
    
    int updateByPrimaryKey(final ExperienceTreasure paramExperienceTreasure);
    
    int logicalDeleteByExample(@Param("example") final ExperienceTreasureExample paramExperienceTreasureExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
