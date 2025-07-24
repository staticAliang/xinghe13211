package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface PetMapper
{
    long countByExample(final PetExample paramPetExample);
    
    int deleteByExample(final PetExample paramPetExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Pet paramPet);
    
    int insertSelective(final Pet paramPet);
    
    Pet selectOneByExample(final PetExample paramPetExample);
    
    Pet selectOneByExampleSelective(@Param("example") final PetExample paramPetExample, @Param("selective") final Pet.Column... paramVarArgs);
    
    List<Pet> selectByExampleSelective(@Param("example") final PetExample paramPetExample, @Param("selective") final Pet.Column... paramVarArgs);
    
    List<Pet> selectByExample(final PetExample paramPetExample);
    
    Pet selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Pet.Column... paramVarArgs);
    
    Pet selectByPrimaryKey(final Integer paramInteger);
    
    Pet selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Pet paramPet, @Param("example") final PetExample paramPetExample);
    
    int updateByExample(@Param("record") final Pet paramPet, @Param("example") final PetExample paramPetExample);
    
    int updateByPrimaryKeySelective(final Pet paramPet);
    
    int updateByPrimaryKey(final Pet paramPet);
    
    int logicalDeleteByExample(@Param("example") final PetExample paramPetExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
