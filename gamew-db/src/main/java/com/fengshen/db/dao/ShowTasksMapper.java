package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ShowTasksMapper
{
    long countByExample(final ShowTasksExample paramShowTasksExample);
    
    int deleteByExample(final ShowTasksExample paramShowTasksExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final ShowTasks paramShowTasks);
    
    int insertSelective(final ShowTasks paramShowTasks);
    
    ShowTasks selectOneByExample(final ShowTasksExample paramShowTasksExample);
    
    ShowTasks selectOneByExampleSelective(@Param("example") final ShowTasksExample paramShowTasksExample, @Param("selective") final ShowTasks.Column... paramVarArgs);
    
    List<ShowTasks> selectByExampleSelective(@Param("example") final ShowTasksExample paramShowTasksExample, @Param("selective") final ShowTasks.Column... paramVarArgs);
    
    List<ShowTasks> selectByExample(final ShowTasksExample paramShowTasksExample);
    
    ShowTasks selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final ShowTasks.Column... paramVarArgs);
    
    ShowTasks selectByPrimaryKey(final Integer paramInteger);
    
    ShowTasks selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final ShowTasks paramShowTasks, @Param("example") final ShowTasksExample paramShowTasksExample);
    
    int updateByExample(@Param("record") final ShowTasks paramShowTasks, @Param("example") final ShowTasksExample paramShowTasksExample);
    
    int updateByPrimaryKeySelective(final ShowTasks paramShowTasks);
    
    int updateByPrimaryKey(final ShowTasks paramShowTasks);
    
    int logicalDeleteByExample(@Param("example") final ShowTasksExample paramShowTasksExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
