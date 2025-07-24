package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface PackModificationMapper
{
    long countByExample(final PackModificationExample paramPackModificationExample);
    
    int deleteByExample(final PackModificationExample paramPackModificationExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final PackModification paramPackModification);
    
    int insertSelective(final PackModification paramPackModification);
    
    PackModification selectOneByExample(final PackModificationExample paramPackModificationExample);
    
    PackModification selectOneByExampleSelective(@Param("example") final PackModificationExample paramPackModificationExample, @Param("selective") final PackModification.Column... paramVarArgs);
    
    List<PackModification> selectByExampleSelective(@Param("example") final PackModificationExample paramPackModificationExample, @Param("selective") final PackModification.Column... paramVarArgs);
    
    List<PackModification> selectByExample(final PackModificationExample paramPackModificationExample);
    
    PackModification selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final PackModification.Column... paramVarArgs);
    
    PackModification selectByPrimaryKey(final Integer paramInteger);
    
    PackModification selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final PackModification paramPackModification, @Param("example") final PackModificationExample paramPackModificationExample);
    
    int updateByExample(@Param("record") final PackModification paramPackModification, @Param("example") final PackModificationExample paramPackModificationExample);
    
    int updateByPrimaryKeySelective(final PackModification paramPackModification);
    
    int updateByPrimaryKey(final PackModification paramPackModification);
    
    int logicalDeleteByExample(@Param("example") final PackModificationExample paramPackModificationExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
