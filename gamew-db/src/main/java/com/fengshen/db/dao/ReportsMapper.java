package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ReportsMapper
{
    long countByExample(final ReportsExample paramReportsExample);
    
    int deleteByExample(final ReportsExample paramReportsExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Reports paramReports);
    
    int insertSelective(final Reports paramReports);
    
    Reports selectOneByExample(final ReportsExample paramReportsExample);
    
    Reports selectOneByExampleSelective(@Param("example") final ReportsExample paramReportsExample, @Param("selective") final Reports.Column... paramVarArgs);
    
    List<Reports> selectByExampleSelective(@Param("example") final ReportsExample paramReportsExample, @Param("selective") final Reports.Column... paramVarArgs);
    
    List<Reports> selectByExample(final ReportsExample paramReportsExample);
    
    Reports selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Reports.Column... paramVarArgs);
    
    Reports selectByPrimaryKey(final Integer paramInteger);
    
    Reports selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Reports paramReports, @Param("example") final ReportsExample paramReportsExample);
    
    int updateByExample(@Param("record") final Reports paramReports, @Param("example") final ReportsExample paramReportsExample);
    
    int updateByPrimaryKeySelective(final Reports paramReports);
    
    int updateByPrimaryKey(final Reports paramReports);
    
    int logicalDeleteByExample(@Param("example") final ReportsExample paramReportsExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
