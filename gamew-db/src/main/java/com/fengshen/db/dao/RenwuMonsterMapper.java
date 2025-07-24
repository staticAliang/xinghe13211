package com.fengshen.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.db.domain.example.RenwuMonsterExample;

public interface RenwuMonsterMapper
{
    long countByExample(final RenwuMonsterExample paramRenwuMonsterExample);
    
    int deleteByExample(final RenwuMonsterExample paramRenwuMonsterExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final RenwuMonster paramRenwuMonster);
    
    int insertSelective(final RenwuMonster paramRenwuMonster);
    
    RenwuMonster selectOneByExample(final RenwuMonsterExample paramRenwuMonsterExample);
    
    RenwuMonster selectOneByExampleSelective(@Param("example") final RenwuMonsterExample paramRenwuMonsterExample, @Param("selective") final RenwuMonster.Column... paramVarArgs);
    
    List<RenwuMonster> selectByExampleSelective(@Param("example") final RenwuMonsterExample paramRenwuMonsterExample, @Param("selective") final RenwuMonster.Column... paramVarArgs);
    
    List<RenwuMonster> selectByExample(final RenwuMonsterExample paramRenwuMonsterExample);
    
    RenwuMonster selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final RenwuMonster.Column... paramVarArgs);
    
    RenwuMonster selectByPrimaryKey(final Integer paramInteger);
    
    RenwuMonster selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final RenwuMonster paramRenwuMonster, @Param("example") final RenwuMonsterExample paramRenwuMonsterExample);
    
    int updateByExample(@Param("record") final RenwuMonster paramRenwuMonster, @Param("example") final RenwuMonsterExample paramRenwuMonsterExample);
    
    int updateByPrimaryKeySelective(final RenwuMonster paramRenwuMonster);
    
    int updateByPrimaryKey(final RenwuMonster paramRenwuMonster);
    
    int logicalDeleteByExample(@Param("example") final RenwuMonsterExample paramRenwuMonsterExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
    
    RenwuMonster getOneRandomRenwuMonster(int type);
    
    RenwuMonster getOneRandomRenwuMonsterByTypeNotReplace(RenwuMonster paramRenwuMonster);
}
