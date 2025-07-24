package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface MapsMapper
{
    long countByExample(final MapsExample paramMapsExample);
    
    int deleteByExample(final MapsExample paramMapsExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Maps paramMaps);
    
    int insertSelective(final Maps paramMaps);
    
    Maps selectOneByExample(final MapsExample paramMapsExample);
    
    Maps selectOneByExampleSelective(@Param("example") final MapsExample paramMapsExample, @Param("selective") final Maps.Column... paramVarArgs);
    
    List<Maps> selectByExampleSelective(@Param("example") final MapsExample paramMapsExample, @Param("selective") final Maps.Column... paramVarArgs);
    
    List<Maps> selectByExample(final MapsExample paramMapsExample);
    
    Maps selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Maps.Column... paramVarArgs);
    
    Maps selectByPrimaryKey(final Integer paramInteger);
    
    Maps selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Maps paramMaps, @Param("example") final MapsExample paramMapsExample);
    
    int updateByExample(@Param("record") final Maps paramMaps, @Param("example") final MapsExample paramMapsExample);
    
    int updateByPrimaryKeySelective(final Maps paramMaps);
    
    int updateByPrimaryKey(final Maps paramMaps);
    
    int logicalDeleteByExample(@Param("example") final MapsExample paramMapsExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
