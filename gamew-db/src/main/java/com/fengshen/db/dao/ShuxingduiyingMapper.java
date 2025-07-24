package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ShuxingduiyingMapper
{
    long countByExample(final ShuxingduiyingExample paramShuxingduiyingExample);
    
    int deleteByExample(final ShuxingduiyingExample paramShuxingduiyingExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Shuxingduiying paramShuxingduiying);
    
    int insertSelective(final Shuxingduiying paramShuxingduiying);
    
    Shuxingduiying selectOneByExample(final ShuxingduiyingExample paramShuxingduiyingExample);
    
    Shuxingduiying selectOneByExampleSelective(@Param("example") final ShuxingduiyingExample paramShuxingduiyingExample, @Param("selective") final Shuxingduiying.Column... paramVarArgs);
    
    List<Shuxingduiying> selectByExampleSelective(@Param("example") final ShuxingduiyingExample paramShuxingduiyingExample, @Param("selective") final Shuxingduiying.Column... paramVarArgs);
    
    List<Shuxingduiying> selectByExample(final ShuxingduiyingExample paramShuxingduiyingExample);
    
    Shuxingduiying selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Shuxingduiying.Column... paramVarArgs);
    
    Shuxingduiying selectByPrimaryKey(final Integer paramInteger);
    
    Shuxingduiying selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Shuxingduiying paramShuxingduiying, @Param("example") final ShuxingduiyingExample paramShuxingduiyingExample);
    
    int updateByExample(@Param("record") final Shuxingduiying paramShuxingduiying, @Param("example") final ShuxingduiyingExample paramShuxingduiyingExample);
    
    int updateByPrimaryKeySelective(final Shuxingduiying paramShuxingduiying);
    
    int updateByPrimaryKey(final Shuxingduiying paramShuxingduiying);
    
    int logicalDeleteByExample(@Param("example") final ShuxingduiyingExample paramShuxingduiyingExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
