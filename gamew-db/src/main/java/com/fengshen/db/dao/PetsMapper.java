package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface PetsMapper
{
    long countByExample(final PetsExample paramPetsExample);
    
    int deleteByExample(final PetsExample paramPetsExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Pets paramPets);
    
    int insertSelective(final Pets paramPets);
    
    Pets selectOneByExample(final PetsExample paramPetsExample);
    
    Pets selectOneByExampleSelective(@Param("example") final PetsExample paramPetsExample, @Param("selective") final Pets.Column... paramVarArgs);
    
    List<Pets> selectByExampleSelective(@Param("example") final PetsExample paramPetsExample, @Param("selective") final Pets.Column... paramVarArgs);
    
    List<Pets> selectByExample(final PetsExample paramPetsExample);
    
    Pets selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Pets.Column... paramVarArgs);
    
    Pets selectByPrimaryKey(final Integer paramInteger);
    
    Pets selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Pets paramPets, @Param("example") final PetsExample paramPetsExample);
    
    int updateByExample(@Param("record") final Pets paramPets, @Param("example") final PetsExample paramPetsExample);
    
    int updateByPrimaryKeySelective(final Pets paramPets);
    
    int updateByPrimaryKey(final Pets paramPets);
    
    int logicalDeleteByExample(@Param("example") final PetsExample paramPetsExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
