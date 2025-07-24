package com.fengshen.db.service.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.fengshen.db.base.BaseCustomMapper;

/**
 * 通用Service帮助类.
 *
 *
 * 默认提供基础增删改查,该接口除getBaseMapper需用户实现
 * ,其他方法都有默认实现方案
 * 
 * @sine: 1.2.0
 *
 * @param <T>
 */
public interface BaseServiceSupport<T> extends BaseCustomMapper<T> {

	@Override
	default T selectOne(T record) {
		return getBaseMapper().selectOne(record);
	}
	
	@Override
	default List<T> select(T record) {
		return getBaseMapper().select(record);
	}

	@Override
	default List<T> selectAll() {
		return getBaseMapper().selectAll();
	}

	@Override
	default int selectCount(T record) {

		return getBaseMapper().selectCount(record);
	}

	@Override
	default T selectByPrimaryKey(Object key) {

		return getBaseMapper().selectByPrimaryKey(key);
	}

	@Override
	default int insert(T record) {

		return getBaseMapper().insert(record);
	}

	@Override
	default int insertSelective(T record) {

		return getBaseMapper().insertSelective(record);
	}

	@Override
	default int updateByPrimaryKey(T record) {

		return getBaseMapper().updateByPrimaryKey(record);
	}

	@Override
	default int updateByPrimaryKeySelective(T record) {

		return getBaseMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	default int delete(T record) {

		return getBaseMapper().delete(record);
	}

	@Override
	default int deleteByPrimaryKey(Object key) {

		return getBaseMapper().deleteByPrimaryKey(key);
	}

	@Override
	default List<T> selectByExample(Object example) {

		return getBaseMapper().selectByExample(example);
	}

	@Override
	default int selectCountByExample(Object example) {

		return getBaseMapper().selectCountByExample(example);
	}

	@Override
	default int deleteByExample(Object example) {

		return getBaseMapper().deleteByExample(example);
	}

	@Override
	default int updateByExample(T record, Object example) {

		return getBaseMapper().updateByExample(record, example);
	}

	@Override
	default int updateByExampleSelective(T record, Object example) {

		return getBaseMapper().updateByExampleSelective(record, example);
	}

	@Override
	default List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {

		return getBaseMapper().selectByExampleAndRowBounds(example, rowBounds);
	}

	@Override
	default List<T> selectByRowBounds(T record, RowBounds rowBounds) {

		return getBaseMapper().selectByRowBounds(record, rowBounds);
	}
	
	@Override
	default boolean existsWithPrimaryKey(Object key) {
		return getBaseMapper().existsWithPrimaryKey(key);
	}

	@Override
	default T selectOneByExample(Object example) {
		return getBaseMapper().selectOneByExample(example);
	}
	
	/**
	 * 获取对应实体类的mapper
	 * @return
	 * BaseMapper<T> 
	 */
	BaseCustomMapper<T> getBaseMapper();
}
