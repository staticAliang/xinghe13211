package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface NpcDialogueMapper
{
    long countByExample(final NpcDialogueExample paramNpcDialogueExample);
    
    int deleteByExample(final NpcDialogueExample paramNpcDialogueExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final NpcDialogue paramNpcDialogue);
    
    int insertSelective(final NpcDialogue paramNpcDialogue);
    
    NpcDialogue selectOneByExample(final NpcDialogueExample paramNpcDialogueExample);
    
    NpcDialogue selectOneByExampleSelective(@Param("example") final NpcDialogueExample paramNpcDialogueExample, @Param("selective") final NpcDialogue.Column... paramVarArgs);
    
    List<NpcDialogue> selectByExampleSelective(@Param("example") final NpcDialogueExample paramNpcDialogueExample, @Param("selective") final NpcDialogue.Column... paramVarArgs);
    
    List<NpcDialogue> selectByExample(final NpcDialogueExample paramNpcDialogueExample);
    
    NpcDialogue selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final NpcDialogue.Column... paramVarArgs);
    
    NpcDialogue selectByPrimaryKey(final Integer paramInteger);
    
    NpcDialogue selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final NpcDialogue paramNpcDialogue, @Param("example") final NpcDialogueExample paramNpcDialogueExample);
    
    int updateByExample(@Param("record") final NpcDialogue paramNpcDialogue, @Param("example") final NpcDialogueExample paramNpcDialogueExample);
    
    int updateByPrimaryKeySelective(final NpcDialogue paramNpcDialogue);
    
    int updateByPrimaryKey(final NpcDialogue paramNpcDialogue);
    
    int logicalDeleteByExample(@Param("example") final NpcDialogueExample paramNpcDialogueExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
