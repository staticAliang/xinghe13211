package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface NpcDialogueFrameMapper
{
    long countByExample(final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    int deleteByExample(final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final NpcDialogueFrame paramNpcDialogueFrame);
    
    int insertSelective(final NpcDialogueFrame paramNpcDialogueFrame);
    
    NpcDialogueFrame selectOneByExample(final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    NpcDialogueFrame selectOneByExampleSelective(@Param("example") final NpcDialogueFrameExample paramNpcDialogueFrameExample, @Param("selective") final NpcDialogueFrame.Column... paramVarArgs);
    
    List<NpcDialogueFrame> selectByExampleSelective(@Param("example") final NpcDialogueFrameExample paramNpcDialogueFrameExample, @Param("selective") final NpcDialogueFrame.Column... paramVarArgs);
    
    List<NpcDialogueFrame> selectByExample(final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    NpcDialogueFrame selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final NpcDialogueFrame.Column... paramVarArgs);
    
    NpcDialogueFrame selectByPrimaryKey(final Integer paramInteger);
    
    NpcDialogueFrame selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final NpcDialogueFrame paramNpcDialogueFrame, @Param("example") final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    int updateByExample(@Param("record") final NpcDialogueFrame paramNpcDialogueFrame, @Param("example") final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    int updateByPrimaryKeySelective(final NpcDialogueFrame paramNpcDialogueFrame);
    
    int updateByPrimaryKey(final NpcDialogueFrame paramNpcDialogueFrame);
    
    int logicalDeleteByExample(@Param("example") final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
