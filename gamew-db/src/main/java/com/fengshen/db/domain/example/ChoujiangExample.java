package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class ChoujiangExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public ChoujiangExample() {
        this.oredCriteria = new ArrayList<Criteria>();
    }
    
    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause() {
        return this.orderByClause;
    }
    
    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }
    
    public boolean isDistinct() {
        return this.distinct;
    }
    
    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public Criteria or() {
        final Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public ChoujiangExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public ChoujiangExample orderBy(final String... orderByClauses) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; ++i) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }
    
    public Criteria createCriteria() {
        final Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected Criteria createCriteriaInternal() {
        final Criteria criteria = new Criteria(this);
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
    
    public static Criteria newAndCreateCriteria() {
        final ChoujiangExample example = new ChoujiangExample();
        return example.createCriteria();
    }
    
    public ChoujiangExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public ChoujiangExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        }
        else {
            otherwise.example(this);
        }
        return this;
    }
    
    public static class Criteria extends GeneratedCriteria
    {
        private ChoujiangExample example;
        
        protected Criteria(final ChoujiangExample example) {
            this.example = example;
        }
        
        public ChoujiangExample example() {
            return this.example;
        }
        
        @Deprecated
        public Criteria andIf(final boolean ifAdd, final ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }
        
        public Criteria when(final boolean condition, final ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }
        
        public Criteria when(final boolean condition, final ICriteriaWhen then, final ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            }
            else {
                otherwise.criteria(this);
            }
            return this;
        }
        
        public Criteria andLogicalDeleted(final boolean deleted) {
            return deleted ? this.andDeletedEqualTo(Choujiang.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Choujiang.Deleted.IS_DELETED.value());
        }
        
        @Deprecated
        public interface ICriteriaAdd
        {
            Criteria add(final Criteria paramCriteria);
        }
    }
    
    protected abstract static class GeneratedCriteria
    {
        protected List<Criterion> criteria;
        
        protected GeneratedCriteria() {
            this.criteria = new ArrayList<Criterion>();
        }
        
        public boolean isValid() {
            return this.criteria.size() > 0;
        }
        
        public List<Criterion> getAllCriteria() {
            return this.criteria;
        }
        
        public List<Criterion> getCriteria() {
            return this.criteria;
        }
        
        protected void addCriterion(final String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }
        
        protected void addCriterion(final String condition, final Object value, final String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }
        
        protected void addCriterion(final String condition, final Object value1, final Object value2, final String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }
        
        public Criteria andIdIsNull() {
            this.addCriterion("id is null");
            return (Criteria)this;
        }
        
        public Criteria andIdIsNotNull() {
            this.addCriterion("id is not null");
            return (Criteria)this;
        }
        
        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("id <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andNoIsNull() {
            this.addCriterion("`no` is null");
            return (Criteria)this;
        }
        
        public Criteria andNoIsNotNull() {
            this.addCriterion("`no` is not null");
            return (Criteria)this;
        }
        
        public Criteria andNoEqualTo(final Integer value) {
            this.addCriterion("`no` =", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`no` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoNotEqualTo(final Integer value) {
            this.addCriterion("`no` <>", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`no` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoGreaterThan(final Integer value) {
            this.addCriterion("`no` >", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("`no` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`no` >=", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`no` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoLessThan(final Integer value) {
            this.addCriterion("`no` <", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("`no` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`no` <=", value, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`no` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNoIn(final List<Integer> values) {
            this.addCriterion("`no` in", values, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoNotIn(final List<Integer> values) {
            this.addCriterion("`no` not in", values, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`no` between", value1, value2, "no");
            return (Criteria)this;
        }
        
        public Criteria andNoNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`no` not between", value1, value2, "no");
            return (Criteria)this;
        }
        
        public Criteria andNameIsNull() {
            this.addCriterion("`name` is null");
            return (Criteria)this;
        }
        
        public Criteria andNameIsNotNull() {
            this.addCriterion("`name` is not null");
            return (Criteria)this;
        }
        
        public Criteria andNameEqualTo(final String value) {
            this.addCriterion("`name` =", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`name` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLike(final String value) {
            this.addCriterion("`name` like", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotLike(final String value) {
            this.addCriterion("`name` not like", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameIn(final List<String> values) {
            this.addCriterion("`name` in", values, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotIn(final List<String> values) {
            this.addCriterion("`name` not in", values, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameBetween(final String value1, final String value2) {
            this.addCriterion("`name` between", value1, value2, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotBetween(final String value1, final String value2) {
            this.addCriterion("`name` not between", value1, value2, "name");
            return (Criteria)this;
        }
        
        public Criteria andDescIsNull() {
            this.addCriterion("`desc` is null");
            return (Criteria)this;
        }
        
        public Criteria andDescIsNotNull() {
            this.addCriterion("`desc` is not null");
            return (Criteria)this;
        }
        
        public Criteria andDescEqualTo(final String value) {
            this.addCriterion("`desc` =", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescNotEqualTo(final String value) {
            this.addCriterion("`desc` <>", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescGreaterThan(final String value) {
            this.addCriterion("`desc` >", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`desc` >=", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescLessThan(final String value) {
            this.addCriterion("`desc` <", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescLessThanOrEqualTo(final String value) {
            this.addCriterion("`desc` <=", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`desc` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDescLike(final String value) {
            this.addCriterion("`desc` like", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescNotLike(final String value) {
            this.addCriterion("`desc` not like", value, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescIn(final List<String> values) {
            this.addCriterion("`desc` in", values, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescNotIn(final List<String> values) {
            this.addCriterion("`desc` not in", values, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescBetween(final String value1, final String value2) {
            this.addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria)this;
        }
        
        public Criteria andDescNotBetween(final String value1, final String value2) {
            this.addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria)this;
        }
        
        public Criteria andLevelIsNull() {
            this.addCriterion("`level` is null");
            return (Criteria)this;
        }
        
        public Criteria andLevelIsNotNull() {
            this.addCriterion("`level` is not null");
            return (Criteria)this;
        }
        
        public Criteria andLevelEqualTo(final Integer value) {
            this.addCriterion("`level` =", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`level` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualTo(final Integer value) {
            this.addCriterion("`level` <>", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`level` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThan(final Integer value) {
            this.addCriterion("`level` >", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("`level` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` >=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`level` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThan(final Integer value) {
            this.addCriterion("`level` <", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("`level` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` <=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("`level` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelIn(final List<Integer> values) {
            this.addCriterion("`level` in", values, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotIn(final List<Integer> values) {
            this.addCriterion("`level` not in", values, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`level` between", value1, value2, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`level` not between", value1, value2, "level");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIsNull() {
            this.addCriterion("add_time is null");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIsNotNull() {
            this.addCriterion("add_time is not null");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time =", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("add_time <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIn(final List<LocalDateTime> values) {
            this.addCriterion("add_time in", values, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotIn(final List<LocalDateTime> values) {
            this.addCriterion("add_time not in", values, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIsNull() {
            this.addCriterion("update_time is null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIsNotNull() {
            this.addCriterion("update_time is not null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time =", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("update_time <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIn(final List<LocalDateTime> values) {
            this.addCriterion("update_time in", values, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotIn(final List<LocalDateTime> values) {
            this.addCriterion("update_time not in", values, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andDeletedIsNull() {
            this.addCriterion("deleted is null");
            return (Criteria)this;
        }
        
        public Criteria andDeletedIsNotNull() {
            this.addCriterion("deleted is not null");
            return (Criteria)this;
        }
        
        public Criteria andDeletedEqualTo(final Boolean value) {
            this.addCriterion("deleted =", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Choujiang.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Choujiang.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Choujiang.Column column) {
            this.addCriterion("deleted <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedIn(final List<Boolean> values) {
            this.addCriterion("deleted in", values, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotIn(final List<Boolean> values) {
            this.addCriterion("deleted not in", values, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedBetween(final Boolean value1, final Boolean value2) {
            this.addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotBetween(final Boolean value1, final Boolean value2) {
            this.addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria)this;
        }
    }
    
    public static class Criterion
    {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;
        
        public String getCondition() {
            return this.condition;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public Object getSecondValue() {
            return this.secondValue;
        }
        
        public boolean isNoValue() {
            return this.noValue;
        }
        
        public boolean isSingleValue() {
            return this.singleValue;
        }
        
        public boolean isBetweenValue() {
            return this.betweenValue;
        }
        
        public boolean isListValue() {
            return this.listValue;
        }
        
        public String getTypeHandler() {
            return this.typeHandler;
        }
        
        protected Criterion(final String condition) {
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        
        protected Criterion(final String condition, final Object value, final String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List) {
                this.listValue = true;
            }
            else {
                this.singleValue = true;
            }
        }
        
        protected Criterion(final String condition, final Object value) {
            this(condition, value, null);
        }
        
        protected Criterion(final String condition, final Object value, final Object secondValue, final String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }
        
        protected Criterion(final String condition, final Object value, final Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
    
    public interface ICriteriaWhen
    {
        void criteria(final Criteria paramCriteria);
    }
    
    public interface IExampleWhen
    {
        void example(final ChoujiangExample paramChoujiangExample);
    }
}
