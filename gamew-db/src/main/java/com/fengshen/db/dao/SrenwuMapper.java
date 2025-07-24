package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface SrenwuMapper
{
    long countByExample(final SrenwuExample paramSrenwuExample);
    
    int deleteByExample(final SrenwuExample paramSrenwuExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Srenwu paramSrenwu);
    
    int insertSelective(final Srenwu paramSrenwu);
    
    Srenwu selectOneByExample(final SrenwuExample paramSrenwuExample);
    
    Srenwu selectOneByExampleSelective(@Param("example") final SrenwuExample paramSrenwuExample, @Param("selective") final Srenwu.Column... paramVarArgs);
    
    List<Srenwu> selectByExampleSelective(@Param("example") final SrenwuExample paramSrenwuExample, @Param("selective") final Srenwu.Column... paramVarArgs);
    
    List<Srenwu> selectByExample(final SrenwuExample paramSrenwuExample);
    
    Srenwu selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Srenwu.Column... paramVarArgs);
    
    Srenwu selectByPrimaryKey(final Integer paramInteger);
    
    Srenwu selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Srenwu paramSrenwu, @Param("example") final SrenwuExample paramSrenwuExample);
    
    int updateByExample(@Param("record") final Srenwu paramSrenwu, @Param("example") final SrenwuExample paramSrenwuExample);
    
    int updateByPrimaryKeySelective(final Srenwu paramSrenwu);
    
    int updateByPrimaryKey(final Srenwu paramSrenwu);
    
    int logicalDeleteByExample(@Param("example") final SrenwuExample paramSrenwuExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
