package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface PetHelpTypeMapper
{
    long countByExample(final PetHelpTypeExample paramPetHelpTypeExample);
    
    int deleteByExample(final PetHelpTypeExample paramPetHelpTypeExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final PetHelpType paramPetHelpType);
    
    int insertSelective(final PetHelpType paramPetHelpType);
    
    PetHelpType selectOneByExample(final PetHelpTypeExample paramPetHelpTypeExample);
    
    PetHelpType selectOneByExampleSelective(@Param("example") final PetHelpTypeExample paramPetHelpTypeExample, @Param("selective") final PetHelpType.Column... paramVarArgs);
    
    List<PetHelpType> selectByExampleSelective(@Param("example") final PetHelpTypeExample paramPetHelpTypeExample, @Param("selective") final PetHelpType.Column... paramVarArgs);
    
    List<PetHelpType> selectByExample(final PetHelpTypeExample paramPetHelpTypeExample);
    
    PetHelpType selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final PetHelpType.Column... paramVarArgs);
    
    PetHelpType selectByPrimaryKey(final Integer paramInteger);
    
    PetHelpType selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final PetHelpType paramPetHelpType, @Param("example") final PetHelpTypeExample paramPetHelpTypeExample);
    
    int updateByExample(@Param("record") final PetHelpType paramPetHelpType, @Param("example") final PetHelpTypeExample paramPetHelpTypeExample);
    
    int updateByPrimaryKeySelective(final PetHelpType paramPetHelpType);
    
    int updateByPrimaryKey(final PetHelpType paramPetHelpType);
    
    int logicalDeleteByExample(@Param("example") final PetHelpTypeExample paramPetHelpTypeExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
