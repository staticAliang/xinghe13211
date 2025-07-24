package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface CreepsStoreMapper
{
    long countByExample(final CreepsStoreExample paramCreepsStoreExample);
    
    int deleteByExample(final CreepsStoreExample paramCreepsStoreExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final CreepsStore paramCreepsStore);
    
    int insertSelective(final CreepsStore paramCreepsStore);
    
    CreepsStore selectOneByExample(final CreepsStoreExample paramCreepsStoreExample);
    
    CreepsStore selectOneByExampleSelective(@Param("example") final CreepsStoreExample paramCreepsStoreExample, @Param("selective") final CreepsStore.Column... paramVarArgs);
    
    List<CreepsStore> selectByExampleSelective(@Param("example") final CreepsStoreExample paramCreepsStoreExample, @Param("selective") final CreepsStore.Column... paramVarArgs);
    
    List<CreepsStore> selectByExample(final CreepsStoreExample paramCreepsStoreExample);
    
    CreepsStore selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final CreepsStore.Column... paramVarArgs);
    
    CreepsStore selectByPrimaryKey(final Integer paramInteger);
    
    CreepsStore selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final CreepsStore paramCreepsStore, @Param("example") final CreepsStoreExample paramCreepsStoreExample);
    
    int updateByExample(@Param("record") final CreepsStore paramCreepsStore, @Param("example") final CreepsStoreExample paramCreepsStoreExample);
    
    int updateByPrimaryKeySelective(final CreepsStore paramCreepsStore);
    
    int updateByPrimaryKey(final CreepsStore paramCreepsStore);
    
    int logicalDeleteByExample(@Param("example") final CreepsStoreExample paramCreepsStoreExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
