package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface MedicineShopMapper
{
    long countByExample(final MedicineShopExample paramMedicineShopExample);
    
    int deleteByExample(final MedicineShopExample paramMedicineShopExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final MedicineShop paramMedicineShop);
    
    int insertSelective(final MedicineShop paramMedicineShop);
    
    MedicineShop selectOneByExample(final MedicineShopExample paramMedicineShopExample);
    
    MedicineShop selectOneByExampleSelective(@Param("example") final MedicineShopExample paramMedicineShopExample, @Param("selective") final MedicineShop.Column... paramVarArgs);
    
    List<MedicineShop> selectByExampleSelective(@Param("example") final MedicineShopExample paramMedicineShopExample, @Param("selective") final MedicineShop.Column... paramVarArgs);
    
    List<MedicineShop> selectByExample(final MedicineShopExample paramMedicineShopExample);
    
    MedicineShop selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final MedicineShop.Column... paramVarArgs);
    
    MedicineShop selectByPrimaryKey(final Integer paramInteger);
    
    MedicineShop selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final MedicineShop paramMedicineShop, @Param("example") final MedicineShopExample paramMedicineShopExample);
    
    int updateByExample(@Param("record") final MedicineShop paramMedicineShop, @Param("example") final MedicineShopExample paramMedicineShopExample);
    
    int updateByPrimaryKeySelective(final MedicineShop paramMedicineShop);
    
    int updateByPrimaryKey(final MedicineShop paramMedicineShop);
    
    int logicalDeleteByExample(@Param("example") final MedicineShopExample paramMedicineShopExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
