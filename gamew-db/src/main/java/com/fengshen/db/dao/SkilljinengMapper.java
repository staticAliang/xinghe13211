package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SkilljinengMapper
{
    long countByExample(final SkilljinengExample paramSkilljinengExample);
    
    int deleteByExample(final SkilljinengExample paramSkilljinengExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Skilljineng paramSkilljineng);
    
    int insertSelective(final Skilljineng paramSkilljineng);
    
    Skilljineng selectOneByExample(final SkilljinengExample paramSkilljinengExample);
    
    Skilljineng selectOneByExampleSelective(@Param("example") final SkilljinengExample paramSkilljinengExample, @Param("selective") final Skilljineng.Column... paramVarArgs);
    
    List<Skilljineng> selectByExampleSelective(@Param("example") final SkilljinengExample paramSkilljinengExample, @Param("selective") final Skilljineng.Column... paramVarArgs);
    
    List<Skilljineng> selectByExample(final SkilljinengExample paramSkilljinengExample);
    
    Skilljineng selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Skilljineng.Column... paramVarArgs);
    
    Skilljineng selectByPrimaryKey(final Integer paramInteger);
    
    Skilljineng selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Skilljineng paramSkilljineng, @Param("example") final SkilljinengExample paramSkilljinengExample);
    
    int updateByExample(@Param("record") final Skilljineng paramSkilljineng, @Param("example") final SkilljinengExample paramSkilljinengExample);
    
    int updateByPrimaryKeySelective(final Skilljineng paramSkilljineng);
    
    int updateByPrimaryKey(final Skilljineng paramSkilljineng);
    
    int logicalDeleteByExample(@Param("example") final SkilljinengExample paramSkilljinengExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
