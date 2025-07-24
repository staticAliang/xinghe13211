package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ChoujiangMapper
{
    long countByExample(final ChoujiangExample paramChoujiangExample);
    
    int deleteByExample(final ChoujiangExample paramChoujiangExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Choujiang paramChoujiang);
    
    int insertSelective(final Choujiang paramChoujiang);
    
    Choujiang selectOneByExample(final ChoujiangExample paramChoujiangExample);
    
    Choujiang selectOneByExampleSelective(@Param("example") final ChoujiangExample paramChoujiangExample, @Param("selective") final Choujiang.Column... paramVarArgs);
    
    List<Choujiang> selectByExampleSelective(@Param("example") final ChoujiangExample paramChoujiangExample, @Param("selective") final Choujiang.Column... paramVarArgs);
    
    List<Choujiang> selectByExample(final ChoujiangExample paramChoujiangExample);
    
    Choujiang selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Choujiang.Column... paramVarArgs);
    
    Choujiang selectByPrimaryKey(final Integer paramInteger);
    
    Choujiang selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Choujiang paramChoujiang, @Param("example") final ChoujiangExample paramChoujiangExample);
    
    int updateByExample(@Param("record") final Choujiang paramChoujiang, @Param("example") final ChoujiangExample paramChoujiangExample);
    
    int updateByPrimaryKeySelective(final Choujiang paramChoujiang);
    
    int updateByPrimaryKey(final Choujiang paramChoujiang);
    
    int logicalDeleteByExample(@Param("example") final ChoujiangExample paramChoujiangExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
