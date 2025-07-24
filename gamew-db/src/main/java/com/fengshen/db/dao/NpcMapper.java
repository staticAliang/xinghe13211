package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface NpcMapper
{
    long countByExample(final NpcExample paramNpcExample);
    
    int deleteByExample(final NpcExample paramNpcExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Npc paramNpc);
    
    int insertSelective(final Npc paramNpc);
    
    Npc selectOneByExample(final NpcExample paramNpcExample);
    
    Npc selectOneByExampleSelective(@Param("example") final NpcExample paramNpcExample, @Param("selective") final Npc.Column... paramVarArgs);
    
    List<Npc> selectByExampleSelective(@Param("example") final NpcExample paramNpcExample, @Param("selective") final Npc.Column... paramVarArgs);
    
    List<Npc> selectByExample(final NpcExample paramNpcExample);
    
    Npc selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Npc.Column... paramVarArgs);
    
    Npc selectByPrimaryKey(final Integer paramInteger);
    
    Npc selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Npc paramNpc, @Param("example") final NpcExample paramNpcExample);
    
    int updateByExample(@Param("record") final Npc paramNpc, @Param("example") final NpcExample paramNpcExample);
    
    int updateByPrimaryKeySelective(final Npc paramNpc);
    
    int updateByPrimaryKey(final Npc paramNpc);
    
    int logicalDeleteByExample(@Param("example") final NpcExample paramNpcExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
    
    Npc randomData();
}
