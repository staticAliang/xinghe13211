package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface GroceriesShopMapper
{
    long countByExample(final GroceriesShopExample paramGroceriesShopExample);
    
    int deleteByExample(final GroceriesShopExample paramGroceriesShopExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final GroceriesShop paramGroceriesShop);
    
    int insertSelective(final GroceriesShop paramGroceriesShop);
    
    GroceriesShop selectOneByExample(final GroceriesShopExample paramGroceriesShopExample);
    
    GroceriesShop selectOneByExampleSelective(@Param("example") final GroceriesShopExample paramGroceriesShopExample, @Param("selective") final GroceriesShop.Column... paramVarArgs);
    
    List<GroceriesShop> selectByExampleSelective(@Param("example") final GroceriesShopExample paramGroceriesShopExample, @Param("selective") final GroceriesShop.Column... paramVarArgs);
    
    List<GroceriesShop> selectByExample(final GroceriesShopExample paramGroceriesShopExample);
    
    GroceriesShop selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final GroceriesShop.Column... paramVarArgs);
    
    GroceriesShop selectByPrimaryKey(final Integer paramInteger);
    
    GroceriesShop selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final GroceriesShop paramGroceriesShop, @Param("example") final GroceriesShopExample paramGroceriesShopExample);
    
    int updateByExample(@Param("record") final GroceriesShop paramGroceriesShop, @Param("example") final GroceriesShopExample paramGroceriesShopExample);
    
    int updateByPrimaryKeySelective(final GroceriesShop paramGroceriesShop);
    
    int updateByPrimaryKey(final GroceriesShop paramGroceriesShop);
    
    int logicalDeleteByExample(@Param("example") final GroceriesShopExample paramGroceriesShopExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
