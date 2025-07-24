package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class ZhuangbeiInfoExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public ZhuangbeiInfoExample() {
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
    
    public ZhuangbeiInfoExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public ZhuangbeiInfoExample orderBy(final String... orderByClauses) {
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
        final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
        return example.createCriteria();
    }
    
    public ZhuangbeiInfoExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public ZhuangbeiInfoExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private ZhuangbeiInfoExample example;
        
        protected Criteria(final ZhuangbeiInfoExample example) {
            this.example = example;
        }
        
        public ZhuangbeiInfoExample example() {
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
            return deleted ? this.andDeletedEqualTo(ZhuangbeiInfo.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(ZhuangbeiInfo.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
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
        
        public Criteria andAttribIsNull() {
            this.addCriterion("attrib is null");
            return (Criteria)this;
        }
        
        public Criteria andAttribIsNotNull() {
            this.addCriterion("attrib is not null");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualTo(final Integer value) {
            this.addCriterion("attrib =", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualTo(final Integer value) {
            this.addCriterion("attrib <>", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThan(final Integer value) {
            this.addCriterion("attrib >", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib >=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThan(final Integer value) {
            this.addCriterion("attrib <", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib <=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("attrib <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribIn(final List<Integer> values) {
            this.addCriterion("attrib in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotIn(final List<Integer> values) {
            this.addCriterion("attrib not in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib between", value1, value2, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib not between", value1, value2, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAmountIsNull() {
            this.addCriterion("amount is null");
            return (Criteria)this;
        }
        
        public Criteria andAmountIsNotNull() {
            this.addCriterion("amount is not null");
            return (Criteria)this;
        }
        
        public Criteria andAmountEqualTo(final Integer value) {
            this.addCriterion("amount =", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountNotEqualTo(final Integer value) {
            this.addCriterion("amount <>", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountGreaterThan(final Integer value) {
            this.addCriterion("amount >", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("amount >=", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountLessThan(final Integer value) {
            this.addCriterion("amount <", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountLessThanOrEqualTo(final Integer value) {
            this.addCriterion("amount <=", value, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("amount <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAmountIn(final List<Integer> values) {
            this.addCriterion("amount in", values, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountNotIn(final List<Integer> values) {
            this.addCriterion("amount not in", values, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountBetween(final Integer value1, final Integer value2) {
            this.addCriterion("amount between", value1, value2, "amount");
            return (Criteria)this;
        }
        
        public Criteria andAmountNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("amount not between", value1, value2, "amount");
            return (Criteria)this;
        }
        
        public Criteria andTypeIsNull() {
            this.addCriterion("`type` is null");
            return (Criteria)this;
        }
        
        public Criteria andTypeIsNotNull() {
            this.addCriterion("`type` is not null");
            return (Criteria)this;
        }
        
        public Criteria andTypeEqualTo(final Integer value) {
            this.addCriterion("`type` =", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("`type` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeIn(final List<Integer> values) {
            this.addCriterion("`type` in", values, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotIn(final List<Integer> values) {
            this.addCriterion("`type` not in", values, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`type` between", value1, value2, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`type` not between", value1, value2, "type");
            return (Criteria)this;
        }
        
        public Criteria andStrIsNull() {
            this.addCriterion("str is null");
            return (Criteria)this;
        }
        
        public Criteria andStrIsNotNull() {
            this.addCriterion("str is not null");
            return (Criteria)this;
        }
        
        public Criteria andStrEqualTo(final String value) {
            this.addCriterion("str =", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrNotEqualTo(final String value) {
            this.addCriterion("str <>", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThan(final String value) {
            this.addCriterion("str >", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanOrEqualTo(final String value) {
            this.addCriterion("str >=", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrLessThan(final String value) {
            this.addCriterion("str <", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanOrEqualTo(final String value) {
            this.addCriterion("str <=", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("str <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrLike(final String value) {
            this.addCriterion("str like", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrNotLike(final String value) {
            this.addCriterion("str not like", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrIn(final List<String> values) {
            this.addCriterion("str in", values, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrNotIn(final List<String> values) {
            this.addCriterion("str not in", values, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrBetween(final String value1, final String value2) {
            this.addCriterion("str between", value1, value2, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrNotBetween(final String value1, final String value2) {
            this.addCriterion("str not between", value1, value2, "str");
            return (Criteria)this;
        }
        
        public Criteria andQualityIsNull() {
            this.addCriterion("quality is null");
            return (Criteria)this;
        }
        
        public Criteria andQualityIsNotNull() {
            this.addCriterion("quality is not null");
            return (Criteria)this;
        }
        
        public Criteria andQualityEqualTo(final String value) {
            this.addCriterion("quality =", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityNotEqualTo(final String value) {
            this.addCriterion("quality <>", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThan(final String value) {
            this.addCriterion("quality >", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanOrEqualTo(final String value) {
            this.addCriterion("quality >=", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThan(final String value) {
            this.addCriterion("quality <", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanOrEqualTo(final String value) {
            this.addCriterion("quality <=", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("quality <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityLike(final String value) {
            this.addCriterion("quality like", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotLike(final String value) {
            this.addCriterion("quality not like", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityIn(final List<String> values) {
            this.addCriterion("quality in", values, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotIn(final List<String> values) {
            this.addCriterion("quality not in", values, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityBetween(final String value1, final String value2) {
            this.addCriterion("quality between", value1, value2, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotBetween(final String value1, final String value2) {
            this.addCriterion("quality not between", value1, value2, "quality");
            return (Criteria)this;
        }
        
        public Criteria andMasterIsNull() {
            this.addCriterion("master is null");
            return (Criteria)this;
        }
        
        public Criteria andMasterIsNotNull() {
            this.addCriterion("master is not null");
            return (Criteria)this;
        }
        
        public Criteria andMasterEqualTo(final Integer value) {
            this.addCriterion("master =", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterNotEqualTo(final Integer value) {
            this.addCriterion("master <>", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterGreaterThan(final Integer value) {
            this.addCriterion("master >", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("master >=", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterLessThan(final Integer value) {
            this.addCriterion("master <", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterLessThanOrEqualTo(final Integer value) {
            this.addCriterion("master <=", value, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("master <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMasterIn(final List<Integer> values) {
            this.addCriterion("master in", values, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterNotIn(final List<Integer> values) {
            this.addCriterion("master not in", values, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterBetween(final Integer value1, final Integer value2) {
            this.addCriterion("master between", value1, value2, "master");
            return (Criteria)this;
        }
        
        public Criteria andMasterNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("master not between", value1, value2, "master");
            return (Criteria)this;
        }
        
        public Criteria andMetalIsNull() {
            this.addCriterion("metal is null");
            return (Criteria)this;
        }
        
        public Criteria andMetalIsNotNull() {
            this.addCriterion("metal is not null");
            return (Criteria)this;
        }
        
        public Criteria andMetalEqualTo(final Integer value) {
            this.addCriterion("metal =", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalNotEqualTo(final Integer value) {
            this.addCriterion("metal <>", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalGreaterThan(final Integer value) {
            this.addCriterion("metal >", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("metal >=", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalLessThan(final Integer value) {
            this.addCriterion("metal <", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalLessThanOrEqualTo(final Integer value) {
            this.addCriterion("metal <=", value, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("metal <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMetalIn(final List<Integer> values) {
            this.addCriterion("metal in", values, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalNotIn(final List<Integer> values) {
            this.addCriterion("metal not in", values, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalBetween(final Integer value1, final Integer value2) {
            this.addCriterion("metal between", value1, value2, "metal");
            return (Criteria)this;
        }
        
        public Criteria andMetalNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("metal not between", value1, value2, "metal");
            return (Criteria)this;
        }
        
        public Criteria andManaIsNull() {
            this.addCriterion("mana is null");
            return (Criteria)this;
        }
        
        public Criteria andManaIsNotNull() {
            this.addCriterion("mana is not null");
            return (Criteria)this;
        }
        
        public Criteria andManaEqualTo(final Integer value) {
            this.addCriterion("mana =", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaNotEqualTo(final Integer value) {
            this.addCriterion("mana <>", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThan(final Integer value) {
            this.addCriterion("mana >", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("mana >=", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaLessThan(final Integer value) {
            this.addCriterion("mana <", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanOrEqualTo(final Integer value) {
            this.addCriterion("mana <=", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("mana <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaIn(final List<Integer> values) {
            this.addCriterion("mana in", values, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotIn(final List<Integer> values) {
            this.addCriterion("mana not in", values, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mana between", value1, value2, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mana not between", value1, value2, "mana");
            return (Criteria)this;
        }
        
        public Criteria andAccurateIsNull() {
            this.addCriterion("accurate is null");
            return (Criteria)this;
        }
        
        public Criteria andAccurateIsNotNull() {
            this.addCriterion("accurate is not null");
            return (Criteria)this;
        }
        
        public Criteria andAccurateEqualTo(final Integer value) {
            this.addCriterion("accurate =", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateNotEqualTo(final Integer value) {
            this.addCriterion("accurate <>", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateGreaterThan(final Integer value) {
            this.addCriterion("accurate >", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("accurate >=", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateLessThan(final Integer value) {
            this.addCriterion("accurate <", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateLessThanOrEqualTo(final Integer value) {
            this.addCriterion("accurate <=", value, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("accurate <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccurateIn(final List<Integer> values) {
            this.addCriterion("accurate in", values, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateNotIn(final List<Integer> values) {
            this.addCriterion("accurate not in", values, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateBetween(final Integer value1, final Integer value2) {
            this.addCriterion("accurate between", value1, value2, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andAccurateNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("accurate not between", value1, value2, "accurate");
            return (Criteria)this;
        }
        
        public Criteria andDefIsNull() {
            this.addCriterion("def is null");
            return (Criteria)this;
        }
        
        public Criteria andDefIsNotNull() {
            this.addCriterion("def is not null");
            return (Criteria)this;
        }
        
        public Criteria andDefEqualTo(final Integer value) {
            this.addCriterion("def =", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefNotEqualTo(final Integer value) {
            this.addCriterion("def <>", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefGreaterThan(final Integer value) {
            this.addCriterion("def >", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("def >=", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefLessThan(final Integer value) {
            this.addCriterion("def <", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefLessThanOrEqualTo(final Integer value) {
            this.addCriterion("def <=", value, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("def <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDefIn(final List<Integer> values) {
            this.addCriterion("def in", values, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefNotIn(final List<Integer> values) {
            this.addCriterion("def not in", values, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefBetween(final Integer value1, final Integer value2) {
            this.addCriterion("def between", value1, value2, "def");
            return (Criteria)this;
        }
        
        public Criteria andDefNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("def not between", value1, value2, "def");
            return (Criteria)this;
        }
        
        public Criteria andDexIsNull() {
            this.addCriterion("dex is null");
            return (Criteria)this;
        }
        
        public Criteria andDexIsNotNull() {
            this.addCriterion("dex is not null");
            return (Criteria)this;
        }
        
        public Criteria andDexEqualTo(final Integer value) {
            this.addCriterion("dex =", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexNotEqualTo(final Integer value) {
            this.addCriterion("dex <>", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexGreaterThan(final Integer value) {
            this.addCriterion("dex >", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("dex >=", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexLessThan(final Integer value) {
            this.addCriterion("dex <", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexLessThanOrEqualTo(final Integer value) {
            this.addCriterion("dex <=", value, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("dex <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDexIn(final List<Integer> values) {
            this.addCriterion("dex in", values, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexNotIn(final List<Integer> values) {
            this.addCriterion("dex not in", values, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dex between", value1, value2, "dex");
            return (Criteria)this;
        }
        
        public Criteria andDexNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dex not between", value1, value2, "dex");
            return (Criteria)this;
        }
        
        public Criteria andWizIsNull() {
            this.addCriterion("wiz is null");
            return (Criteria)this;
        }
        
        public Criteria andWizIsNotNull() {
            this.addCriterion("wiz is not null");
            return (Criteria)this;
        }
        
        public Criteria andWizEqualTo(final Integer value) {
            this.addCriterion("wiz =", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizNotEqualTo(final Integer value) {
            this.addCriterion("wiz <>", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizGreaterThan(final Integer value) {
            this.addCriterion("wiz >", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("wiz >=", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizLessThan(final Integer value) {
            this.addCriterion("wiz <", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizLessThanOrEqualTo(final Integer value) {
            this.addCriterion("wiz <=", value, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("wiz <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andWizIn(final List<Integer> values) {
            this.addCriterion("wiz in", values, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizNotIn(final List<Integer> values) {
            this.addCriterion("wiz not in", values, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizBetween(final Integer value1, final Integer value2) {
            this.addCriterion("wiz between", value1, value2, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andWizNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("wiz not between", value1, value2, "wiz");
            return (Criteria)this;
        }
        
        public Criteria andParryIsNull() {
            this.addCriterion("parry is null");
            return (Criteria)this;
        }
        
        public Criteria andParryIsNotNull() {
            this.addCriterion("parry is not null");
            return (Criteria)this;
        }
        
        public Criteria andParryEqualTo(final Integer value) {
            this.addCriterion("parry =", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryNotEqualTo(final Integer value) {
            this.addCriterion("parry <>", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryGreaterThan(final Integer value) {
            this.addCriterion("parry >", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("parry >=", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryLessThan(final Integer value) {
            this.addCriterion("parry <", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryLessThanOrEqualTo(final Integer value) {
            this.addCriterion("parry <=", value, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("parry <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andParryIn(final List<Integer> values) {
            this.addCriterion("parry in", values, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryNotIn(final List<Integer> values) {
            this.addCriterion("parry not in", values, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryBetween(final Integer value1, final Integer value2) {
            this.addCriterion("parry between", value1, value2, "parry");
            return (Criteria)this;
        }
        
        public Criteria andParryNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("parry not between", value1, value2, "parry");
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
        
        public Criteria andAddTimeEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final ZhuangbeiInfo.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final ZhuangbeiInfo.Column column) {
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
        void example(final ZhuangbeiInfoExample paramZhuangbeiInfoExample);
    }
}
