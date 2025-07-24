package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface ZhuangbeiInfoMapper
{
    long countByExample(final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    int deleteByExample(final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final ZhuangbeiInfo paramZhuangbeiInfo);
    
    int insertSelective(final ZhuangbeiInfo paramZhuangbeiInfo);
    
    ZhuangbeiInfo selectOneByExample(final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    ZhuangbeiInfo selectOneByExampleSelective(@Param("example") final ZhuangbeiInfoExample paramZhuangbeiInfoExample, @Param("selective") final ZhuangbeiInfo.Column... paramVarArgs);
    
    List<ZhuangbeiInfo> selectByExampleSelective(@Param("example") final ZhuangbeiInfoExample paramZhuangbeiInfoExample, @Param("selective") final ZhuangbeiInfo.Column... paramVarArgs);
    
    List<ZhuangbeiInfo> selectByExample(final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    ZhuangbeiInfo selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final ZhuangbeiInfo.Column... paramVarArgs);
    
    ZhuangbeiInfo selectByPrimaryKey(final Integer paramInteger);
    
    ZhuangbeiInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final ZhuangbeiInfo paramZhuangbeiInfo, @Param("example") final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    int updateByExample(@Param("record") final ZhuangbeiInfo paramZhuangbeiInfo, @Param("example") final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    int updateByPrimaryKeySelective(final ZhuangbeiInfo paramZhuangbeiInfo);
    
    int updateByPrimaryKey(final ZhuangbeiInfo paramZhuangbeiInfo);
    
    int logicalDeleteByExample(@Param("example") final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
