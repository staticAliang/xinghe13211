package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface StoreInfoMapper
{
    long countByExample(final StoreInfoExample paramStoreInfoExample);
    
    int deleteByExample(final StoreInfoExample paramStoreInfoExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final StoreInfo paramStoreInfo);
    
    int insertSelective(final StoreInfo paramStoreInfo);
    
    StoreInfo selectOneByExample(final StoreInfoExample paramStoreInfoExample);
    
    StoreInfo selectOneByExampleSelective(@Param("example") final StoreInfoExample paramStoreInfoExample, @Param("selective") final StoreInfo.Column... paramVarArgs);
    
    List<StoreInfo> selectByExampleSelective(@Param("example") final StoreInfoExample paramStoreInfoExample, @Param("selective") final StoreInfo.Column... paramVarArgs);
    
    List<StoreInfo> selectByExample(final StoreInfoExample paramStoreInfoExample);
    
    StoreInfo selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final StoreInfo.Column... paramVarArgs);
    
    StoreInfo selectByPrimaryKey(final Integer paramInteger);
    
    StoreInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final StoreInfo paramStoreInfo, @Param("example") final StoreInfoExample paramStoreInfoExample);
    
    int updateByExample(@Param("record") final StoreInfo paramStoreInfo, @Param("example") final StoreInfoExample paramStoreInfoExample);
    
    int updateByPrimaryKeySelective(final StoreInfo paramStoreInfo);
    
    int updateByPrimaryKey(final StoreInfo paramStoreInfo);
    
    int logicalDeleteByExample(@Param("example") final StoreInfoExample paramStoreInfoExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
