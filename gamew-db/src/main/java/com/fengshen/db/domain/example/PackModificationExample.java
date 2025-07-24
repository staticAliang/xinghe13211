package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class PackModificationExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public PackModificationExample() {
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
    
    public PackModificationExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public PackModificationExample orderBy(final String... orderByClauses) {
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
        final PackModificationExample example = new PackModificationExample();
        return example.createCriteria();
    }
    
    public PackModificationExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public PackModificationExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private PackModificationExample example;
        
        protected Criteria(final PackModificationExample example) {
            this.example = example;
        }
        
        public PackModificationExample example() {
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
            return deleted ? this.andDeletedEqualTo(PackModification.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(PackModification.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final PackModification.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final PackModification.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final PackModification.Column column) {
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
        
        public Criteria andAliasIsNull() {
            this.addCriterion("`alias` is null");
            return (Criteria)this;
        }
        
        public Criteria andAliasIsNotNull() {
            this.addCriterion("`alias` is not null");
            return (Criteria)this;
        }
        
        public Criteria andAliasEqualTo(final String value) {
            this.addCriterion("`alias` =", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`alias` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasNotEqualTo(final String value) {
            this.addCriterion("`alias` <>", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`alias` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasGreaterThan(final String value) {
            this.addCriterion("`alias` >", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("`alias` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`alias` >=", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`alias` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasLessThan(final String value) {
            this.addCriterion("`alias` <", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasLessThanColumn(final PackModification.Column column) {
            this.addCriterion("`alias` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasLessThanOrEqualTo(final String value) {
            this.addCriterion("`alias` <=", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`alias` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAliasLike(final String value) {
            this.addCriterion("`alias` like", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasNotLike(final String value) {
            this.addCriterion("`alias` not like", value, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasIn(final List<String> values) {
            this.addCriterion("`alias` in", values, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasNotIn(final List<String> values) {
            this.addCriterion("`alias` not in", values, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasBetween(final String value1, final String value2) {
            this.addCriterion("`alias` between", value1, value2, "alias");
            return (Criteria)this;
        }
        
        public Criteria andAliasNotBetween(final String value1, final String value2) {
            this.addCriterion("`alias` not between", value1, value2, "alias");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeIsNull() {
            this.addCriterion("fasion_type is null");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeIsNotNull() {
            this.addCriterion("fasion_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeEqualTo(final String value) {
            this.addCriterion("fasion_type =", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeEqualToColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeNotEqualTo(final String value) {
            this.addCriterion("fasion_type <>", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeGreaterThan(final String value) {
            this.addCriterion("fasion_type >", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("fasion_type >=", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeLessThan(final String value) {
            this.addCriterion("fasion_type <", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeLessThanColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeLessThanOrEqualTo(final String value) {
            this.addCriterion("fasion_type <=", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("fasion_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeLike(final String value) {
            this.addCriterion("fasion_type like", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeNotLike(final String value) {
            this.addCriterion("fasion_type not like", value, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeIn(final List<String> values) {
            this.addCriterion("fasion_type in", values, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeNotIn(final List<String> values) {
            this.addCriterion("fasion_type not in", values, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeBetween(final String value1, final String value2) {
            this.addCriterion("fasion_type between", value1, value2, "fasionType");
            return (Criteria)this;
        }
        
        public Criteria andFasionTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("fasion_type not between", value1, value2, "fasionType");
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
        
        public Criteria andStrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("str = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrNotEqualTo(final String value) {
            this.addCriterion("str <>", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("str <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThan(final String value) {
            this.addCriterion("str >", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("str > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanOrEqualTo(final String value) {
            this.addCriterion("str >=", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("str >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrLessThan(final String value) {
            this.addCriterion("str <", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanColumn(final PackModification.Column column) {
            this.addCriterion("str < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanOrEqualTo(final String value) {
            this.addCriterion("str <=", value, "str");
            return (Criteria)this;
        }
        
        public Criteria andStrLessThanOrEqualToColumn(final PackModification.Column column) {
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
        
        public Criteria andTypeIsNull() {
            this.addCriterion("`type` is null");
            return (Criteria)this;
        }
        
        public Criteria andTypeIsNotNull() {
            this.addCriterion("`type` is not null");
            return (Criteria)this;
        }
        
        public Criteria andTypeEqualTo(final String value) {
            this.addCriterion("`type` =", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final String value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final String value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final String value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final PackModification.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final String value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`type` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLike(final String value) {
            this.addCriterion("`type` like", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotLike(final String value) {
            this.addCriterion("`type` not like", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeIn(final List<String> values) {
            this.addCriterion("`type` in", values, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotIn(final List<String> values) {
            this.addCriterion("`type` not in", values, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeBetween(final String value1, final String value2) {
            this.addCriterion("`type` between", value1, value2, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("`type` not between", value1, value2, "type");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumIsNull() {
            this.addCriterion("food_num is null");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumIsNotNull() {
            this.addCriterion("food_num is not null");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumEqualTo(final Integer value) {
            this.addCriterion("food_num =", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumEqualToColumn(final PackModification.Column column) {
            this.addCriterion("food_num = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumNotEqualTo(final Integer value) {
            this.addCriterion("food_num <>", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("food_num <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumGreaterThan(final Integer value) {
            this.addCriterion("food_num >", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("food_num > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("food_num >=", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("food_num >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumLessThan(final Integer value) {
            this.addCriterion("food_num <", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumLessThanColumn(final PackModification.Column column) {
            this.addCriterion("food_num < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumLessThanOrEqualTo(final Integer value) {
            this.addCriterion("food_num <=", value, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("food_num <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFoodNumIn(final List<Integer> values) {
            this.addCriterion("food_num in", values, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumNotIn(final List<Integer> values) {
            this.addCriterion("food_num not in", values, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumBetween(final Integer value1, final Integer value2) {
            this.addCriterion("food_num between", value1, value2, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andFoodNumNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("food_num not between", value1, value2, "foodNum");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceIsNull() {
            this.addCriterion("goods_price is null");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceIsNotNull() {
            this.addCriterion("goods_price is not null");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceEqualTo(final Integer value) {
            this.addCriterion("goods_price =", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceEqualToColumn(final PackModification.Column column) {
            this.addCriterion("goods_price = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceNotEqualTo(final Integer value) {
            this.addCriterion("goods_price <>", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("goods_price <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceGreaterThan(final Integer value) {
            this.addCriterion("goods_price >", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("goods_price > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("goods_price >=", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("goods_price >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceLessThan(final Integer value) {
            this.addCriterion("goods_price <", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceLessThanColumn(final PackModification.Column column) {
            this.addCriterion("goods_price < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceLessThanOrEqualTo(final Integer value) {
            this.addCriterion("goods_price <=", value, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("goods_price <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceIn(final List<Integer> values) {
            this.addCriterion("goods_price in", values, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceNotIn(final List<Integer> values) {
            this.addCriterion("goods_price not in", values, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceBetween(final Integer value1, final Integer value2) {
            this.addCriterion("goods_price between", value1, value2, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andGoodsPriceNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("goods_price not between", value1, value2, "goodsPrice");
            return (Criteria)this;
        }
        
        public Criteria andSexIsNull() {
            this.addCriterion("sex is null");
            return (Criteria)this;
        }
        
        public Criteria andSexIsNotNull() {
            this.addCriterion("sex is not null");
            return (Criteria)this;
        }
        
        public Criteria andSexEqualTo(final Integer value) {
            this.addCriterion("sex =", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexEqualToColumn(final PackModification.Column column) {
            this.addCriterion("sex = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexNotEqualTo(final Integer value) {
            this.addCriterion("sex <>", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("sex <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexGreaterThan(final Integer value) {
            this.addCriterion("sex >", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("sex > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("sex >=", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("sex >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexLessThan(final Integer value) {
            this.addCriterion("sex <", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexLessThanColumn(final PackModification.Column column) {
            this.addCriterion("sex < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexLessThanOrEqualTo(final Integer value) {
            this.addCriterion("sex <=", value, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("sex <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSexIn(final List<Integer> values) {
            this.addCriterion("sex in", values, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexNotIn(final List<Integer> values) {
            this.addCriterion("sex not in", values, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sex between", value1, value2, "sex");
            return (Criteria)this;
        }
        
        public Criteria andSexNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sex not between", value1, value2, "sex");
            return (Criteria)this;
        }
        
        public Criteria andPositionIsNull() {
            this.addCriterion("`position` is null");
            return (Criteria)this;
        }
        
        public Criteria andPositionIsNotNull() {
            this.addCriterion("`position` is not null");
            return (Criteria)this;
        }
        
        public Criteria andPositionEqualTo(final Integer value) {
            this.addCriterion("`position` =", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`position` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionNotEqualTo(final Integer value) {
            this.addCriterion("`position` <>", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`position` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionGreaterThan(final Integer value) {
            this.addCriterion("`position` >", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("`position` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`position` >=", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`position` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionLessThan(final Integer value) {
            this.addCriterion("`position` <", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionLessThanColumn(final PackModification.Column column) {
            this.addCriterion("`position` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`position` <=", value, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("`position` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPositionIn(final List<Integer> values) {
            this.addCriterion("`position` in", values, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionNotIn(final List<Integer> values) {
            this.addCriterion("`position` not in", values, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`position` between", value1, value2, "position");
            return (Criteria)this;
        }
        
        public Criteria andPositionNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`position` not between", value1, value2, "position");
            return (Criteria)this;
        }
        
        public Criteria andCategoryIsNull() {
            this.addCriterion("category is null");
            return (Criteria)this;
        }
        
        public Criteria andCategoryIsNotNull() {
            this.addCriterion("category is not null");
            return (Criteria)this;
        }
        
        public Criteria andCategoryEqualTo(final Integer value) {
            this.addCriterion("category =", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryEqualToColumn(final PackModification.Column column) {
            this.addCriterion("category = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryNotEqualTo(final Integer value) {
            this.addCriterion("category <>", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("category <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryGreaterThan(final Integer value) {
            this.addCriterion("category >", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("category > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("category >=", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("category >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryLessThan(final Integer value) {
            this.addCriterion("category <", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryLessThanColumn(final PackModification.Column column) {
            this.addCriterion("category < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryLessThanOrEqualTo(final Integer value) {
            this.addCriterion("category <=", value, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryLessThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("category <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCategoryIn(final List<Integer> values) {
            this.addCriterion("category in", values, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryNotIn(final List<Integer> values) {
            this.addCriterion("category not in", values, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryBetween(final Integer value1, final Integer value2) {
            this.addCriterion("category between", value1, value2, "category");
            return (Criteria)this;
        }
        
        public Criteria andCategoryNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("category not between", value1, value2, "category");
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
        
        public Criteria andAddTimeEqualToColumn(final PackModification.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final PackModification.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final PackModification.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final PackModification.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final PackModification.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final PackModification.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final PackModification.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final PackModification.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final PackModification.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final PackModification.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final PackModification.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final PackModification.Column column) {
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
        void example(final PackModificationExample paramPackModificationExample);
    }
}
