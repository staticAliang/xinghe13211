package com.fengshen.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.example.MapExample;

public interface MapMapper {
	long countByExample(final MapExample paramMapExample);

	int deleteByExample(final MapExample paramMapExample);

	int deleteByPrimaryKey(final Integer paramInteger);

	int insert(final Map paramMap);

	int insertSelective(final Map paramMap);

	Map selectOneByExample(final MapExample paramMapExample);

	Map selectOneByExampleSelective(@Param("example") final MapExample paramMapExample,
			@Param("selective") final Map.Column... paramVarArgs);

	List<Map> selectByExampleSelective(@Param("example") final MapExample paramMapExample,
			@Param("selective") final Map.Column... paramVarArgs);

	List<Map> selectByExample(final MapExample paramMapExample);

	Map selectByPrimaryKeySelective(@Param("id") final Integer paramInteger,
			@Param("selective") final Map.Column... paramVarArgs);

	Map selectByPrimaryKey(final Integer paramInteger);

	Map selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger,
			@Param("andLogicalDeleted") final boolean paramBoolean);

	int updateByExampleSelective(@Param("record") final Map paramMap,
			@Param("example") final MapExample paramMapExample);

	int updateByExample(@Param("record") final Map paramMap, @Param("example") final MapExample paramMapExample);

	int updateByPrimaryKeySelective(final Map paramMap);

	int updateByPrimaryKey(final Map paramMap);

	int logicalDeleteByExample(@Param("example") final MapExample paramMapExample);

	int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
