package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface StoreGoodsMapper
{
    long countByExample(final StoreGoodsExample paramStoreGoodsExample);
    
    int deleteByExample(final StoreGoodsExample paramStoreGoodsExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final StoreGoods paramStoreGoods);
    
    int insertSelective(final StoreGoods paramStoreGoods);
    
    StoreGoods selectOneByExample(final StoreGoodsExample paramStoreGoodsExample);
    
    StoreGoods selectOneByExampleSelective(@Param("example") final StoreGoodsExample paramStoreGoodsExample, @Param("selective") final StoreGoods.Column... paramVarArgs);
    
    List<StoreGoods> selectByExampleSelective(@Param("example") final StoreGoodsExample paramStoreGoodsExample, @Param("selective") final StoreGoods.Column... paramVarArgs);
    
    List<StoreGoods> selectByExample(final StoreGoodsExample paramStoreGoodsExample);
    
    StoreGoods selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final StoreGoods.Column... paramVarArgs);
    
    StoreGoods selectByPrimaryKey(final Integer paramInteger);
    
    StoreGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final StoreGoods paramStoreGoods, @Param("example") final StoreGoodsExample paramStoreGoodsExample);
    
    int updateByExample(@Param("record") final StoreGoods paramStoreGoods, @Param("example") final StoreGoodsExample paramStoreGoodsExample);
    
    int updateByPrimaryKeySelective(final StoreGoods paramStoreGoods);
    
    int updateByPrimaryKey(final StoreGoods paramStoreGoods);
    
    int logicalDeleteByExample(@Param("example") final StoreGoodsExample paramStoreGoodsExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
