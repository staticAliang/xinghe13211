package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class PetHelpTypeExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public PetHelpTypeExample() {
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
    
    public PetHelpTypeExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public PetHelpTypeExample orderBy(final String... orderByClauses) {
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
        final PetHelpTypeExample example = new PetHelpTypeExample();
        return example.createCriteria();
    }
    
    public PetHelpTypeExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public PetHelpTypeExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private PetHelpTypeExample example;
        
        protected Criteria(final PetHelpTypeExample example) {
            this.example = example;
        }
        
        public PetHelpTypeExample example() {
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
            return deleted ? this.andDeletedEqualTo(PetHelpType.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(PetHelpType.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andTypeEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andNameEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andAddTimeEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final PetHelpType.Column column) {
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
        
        public Criteria andQualityIsNull() {
            this.addCriterion("quality is null");
            return (Criteria)this;
        }
        
        public Criteria andQualityIsNotNull() {
            this.addCriterion("quality is not null");
            return (Criteria)this;
        }
        
        public Criteria andQualityEqualTo(final Integer value) {
            this.addCriterion("quality =", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("quality = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityNotEqualTo(final Integer value) {
            this.addCriterion("quality <>", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("quality <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThan(final Integer value) {
            this.addCriterion("quality >", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("quality > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("quality >=", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("quality >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThan(final Integer value) {
            this.addCriterion("quality <", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("quality < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanOrEqualTo(final Integer value) {
            this.addCriterion("quality <=", value, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityLessThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("quality <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQualityIn(final List<Integer> values) {
            this.addCriterion("quality in", values, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotIn(final List<Integer> values) {
            this.addCriterion("quality not in", values, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityBetween(final Integer value1, final Integer value2) {
            this.addCriterion("quality between", value1, value2, "quality");
            return (Criteria)this;
        }
        
        public Criteria andQualityNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("quality not between", value1, value2, "quality");
            return (Criteria)this;
        }
        
        public Criteria andMoneyIsNull() {
            this.addCriterion("money is null");
            return (Criteria)this;
        }
        
        public Criteria andMoneyIsNotNull() {
            this.addCriterion("money is not null");
            return (Criteria)this;
        }
        
        public Criteria andMoneyEqualTo(final Integer value) {
            this.addCriterion("money =", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("money = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualTo(final Integer value) {
            this.addCriterion("money <>", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("money <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThan(final Integer value) {
            this.addCriterion("money >", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("money > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("money >=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("money >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThan(final Integer value) {
            this.addCriterion("money <", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("money < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualTo(final Integer value) {
            this.addCriterion("money <=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("money <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyIn(final List<Integer> values) {
            this.addCriterion("money in", values, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotIn(final List<Integer> values) {
            this.addCriterion("money not in", values, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyBetween(final Integer value1, final Integer value2) {
            this.addCriterion("money between", value1, value2, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("money not between", value1, value2, "money");
            return (Criteria)this;
        }
        
        public Criteria andPolarIsNull() {
            this.addCriterion("polar is null");
            return (Criteria)this;
        }
        
        public Criteria andPolarIsNotNull() {
            this.addCriterion("polar is not null");
            return (Criteria)this;
        }
        
        public Criteria andPolarEqualTo(final Integer value) {
            this.addCriterion("polar =", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("polar = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarNotEqualTo(final Integer value) {
            this.addCriterion("polar <>", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("polar <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThan(final Integer value) {
            this.addCriterion("polar >", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanColumn(final PetHelpType.Column column) {
            this.addCriterion("polar > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("polar >=", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("polar >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThan(final Integer value) {
            this.addCriterion("polar <", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanColumn(final PetHelpType.Column column) {
            this.addCriterion("polar < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanOrEqualTo(final Integer value) {
            this.addCriterion("polar <=", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanOrEqualToColumn(final PetHelpType.Column column) {
            this.addCriterion("polar <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarIn(final List<Integer> values) {
            this.addCriterion("polar in", values, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotIn(final List<Integer> values) {
            this.addCriterion("polar not in", values, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarBetween(final Integer value1, final Integer value2) {
            this.addCriterion("polar between", value1, value2, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("polar not between", value1, value2, "polar");
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
        void example(final PetHelpTypeExample paramPetHelpTypeExample);
    }
}
