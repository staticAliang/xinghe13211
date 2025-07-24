package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class CreepsStoreExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public CreepsStoreExample() {
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
    
    public CreepsStoreExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public CreepsStoreExample orderBy(final String... orderByClauses) {
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
        final CreepsStoreExample example = new CreepsStoreExample();
        return example.createCriteria();
    }
    
    public CreepsStoreExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public CreepsStoreExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private CreepsStoreExample example;
        
        protected Criteria(final CreepsStoreExample example) {
            this.example = example;
        }
        
        public CreepsStoreExample example() {
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
            return deleted ? this.andDeletedEqualTo(CreepsStore.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(CreepsStore.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final CreepsStore.Column column) {
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
        
        public Criteria andNameEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final CreepsStore.Column column) {
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
        
        public Criteria andPriceIsNull() {
            this.addCriterion("price is null");
            return (Criteria)this;
        }
        
        public Criteria andPriceIsNotNull() {
            this.addCriterion("price is not null");
            return (Criteria)this;
        }
        
        public Criteria andPriceEqualTo(final Integer value) {
            this.addCriterion("price =", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("price = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceNotEqualTo(final Integer value) {
            this.addCriterion("price <>", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("price <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceGreaterThan(final Integer value) {
            this.addCriterion("price >", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("price > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("price >=", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("price >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceLessThan(final Integer value) {
            this.addCriterion("price <", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("price < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceLessThanOrEqualTo(final Integer value) {
            this.addCriterion("price <=", value, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceLessThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("price <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPriceIn(final List<Integer> values) {
            this.addCriterion("price in", values, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceNotIn(final List<Integer> values) {
            this.addCriterion("price not in", values, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceBetween(final Integer value1, final Integer value2) {
            this.addCriterion("price between", value1, value2, "price");
            return (Criteria)this;
        }
        
        public Criteria andPriceNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("price not between", value1, value2, "price");
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
        
        public Criteria andAddTimeEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final CreepsStore.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final CreepsStore.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final CreepsStore.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final CreepsStore.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final CreepsStore.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final CreepsStore.Column column) {
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
        void example(final CreepsStoreExample paramCreepsStoreExample);
    }
}
