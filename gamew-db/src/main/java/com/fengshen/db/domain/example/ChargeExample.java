package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class ChargeExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public ChargeExample() {
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
    
    public ChargeExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public ChargeExample orderBy(final String... orderByClauses) {
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
        final ChargeExample example = new ChargeExample();
        return example.createCriteria();
    }
    
    public ChargeExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public ChargeExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private ChargeExample example;
        
        protected Criteria(final ChargeExample example) {
            this.example = example;
        }
        
        public ChargeExample example() {
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
            return deleted ? this.andDeletedEqualTo(Charge.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Charge.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Charge.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Charge.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Charge.Column column) {
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
        
        public Criteria andAccountnameIsNull() {
            this.addCriterion("accountname is null");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameIsNotNull() {
            this.addCriterion("accountname is not null");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameEqualTo(final String value) {
            this.addCriterion("accountname =", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameEqualToColumn(final Charge.Column column) {
            this.addCriterion("accountname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameNotEqualTo(final String value) {
            this.addCriterion("accountname <>", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("accountname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameGreaterThan(final String value) {
            this.addCriterion("accountname >", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("accountname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("accountname >=", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("accountname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameLessThan(final String value) {
            this.addCriterion("accountname <", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameLessThanColumn(final Charge.Column column) {
            this.addCriterion("accountname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameLessThanOrEqualTo(final String value) {
            this.addCriterion("accountname <=", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameLessThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("accountname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountnameLike(final String value) {
            this.addCriterion("accountname like", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameNotLike(final String value) {
            this.addCriterion("accountname not like", value, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameIn(final List<String> values) {
            this.addCriterion("accountname in", values, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameNotIn(final List<String> values) {
            this.addCriterion("accountname not in", values, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameBetween(final String value1, final String value2) {
            this.addCriterion("accountname between", value1, value2, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andAccountnameNotBetween(final String value1, final String value2) {
            this.addCriterion("accountname not between", value1, value2, "accountname");
            return (Criteria)this;
        }
        
        public Criteria andCoinIsNull() {
            this.addCriterion("coin is null");
            return (Criteria)this;
        }
        
        public Criteria andCoinIsNotNull() {
            this.addCriterion("coin is not null");
            return (Criteria)this;
        }
        
        public Criteria andCoinEqualTo(final Integer value) {
            this.addCriterion("coin =", value, "coin");
            return (Criteria)this;
        }

        public Criteria andStatusEqualTo(final Integer value) {
            this.addCriterion("status =", value, "status");
            return (Criteria)this;
        }
        
        public Criteria andCoinEqualToColumn(final Charge.Column column) {
            this.addCriterion("coin = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinNotEqualTo(final Integer value) {
            this.addCriterion("coin <>", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("coin <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThan(final Integer value) {
            this.addCriterion("coin >", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("coin > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("coin >=", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("coin >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThan(final Integer value) {
            this.addCriterion("coin <", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanColumn(final Charge.Column column) {
            this.addCriterion("coin < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanOrEqualTo(final Integer value) {
            this.addCriterion("coin <=", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("coin <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinIn(final List<Integer> values) {
            this.addCriterion("coin in", values, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotIn(final List<Integer> values) {
            this.addCriterion("coin not in", values, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinBetween(final Integer value1, final Integer value2) {
            this.addCriterion("coin between", value1, value2, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("coin not between", value1, value2, "coin");
            return (Criteria)this;
        }
        
        public Criteria andStateIsNull() {
            this.addCriterion("`state` is null");
            return (Criteria)this;
        }
        
        public Criteria andStateIsNotNull() {
            this.addCriterion("`state` is not null");
            return (Criteria)this;
        }
        
        public Criteria andStateEqualTo(final Integer value) {
            this.addCriterion("`state` =", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateEqualToColumn(final Charge.Column column) {
            this.addCriterion("`state` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateNotEqualTo(final Integer value) {
            this.addCriterion("`state` <>", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("`state` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateGreaterThan(final Integer value) {
            this.addCriterion("`state` >", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("`state` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`state` >=", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("`state` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateLessThan(final Integer value) {
            this.addCriterion("`state` <", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateLessThanColumn(final Charge.Column column) {
            this.addCriterion("`state` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`state` <=", value, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateLessThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("`state` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andStateIn(final List<Integer> values) {
            this.addCriterion("`state` in", values, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateNotIn(final List<Integer> values) {
            this.addCriterion("`state` not in", values, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`state` between", value1, value2, "state");
            return (Criteria)this;
        }
        
        public Criteria andStateNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`state` not between", value1, value2, "state");
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
        
        public Criteria andAddTimeEqualToColumn(final Charge.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Charge.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Charge.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Charge.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Charge.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Charge.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Charge.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Charge.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Charge.Column column) {
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
        
        public Criteria andMoneyEqualToColumn(final Charge.Column column) {
            this.addCriterion("money = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualTo(final Integer value) {
            this.addCriterion("money <>", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("money <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThan(final Integer value) {
            this.addCriterion("money >", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("money > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("money >=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("money >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThan(final Integer value) {
            this.addCriterion("money <", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanColumn(final Charge.Column column) {
            this.addCriterion("money < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualTo(final Integer value) {
            this.addCriterion("money <=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualToColumn(final Charge.Column column) {
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
        
        public Criteria andCodeIsNull() {
            this.addCriterion("code is null");
            return (Criteria)this;
        }
        
        public Criteria andCodeIsNotNull() {
            this.addCriterion("code is not null");
            return (Criteria)this;
        }
        
        public Criteria andCodeEqualTo(final String value) {
            this.addCriterion("code =", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeEqualToColumn(final Charge.Column column) {
            this.addCriterion("code = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeNotEqualTo(final String value) {
            this.addCriterion("code <>", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeNotEqualToColumn(final Charge.Column column) {
            this.addCriterion("code <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThan(final String value) {
            this.addCriterion("code >", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanColumn(final Charge.Column column) {
            this.addCriterion("code > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("code >=", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("code >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThan(final String value) {
            this.addCriterion("code <", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanColumn(final Charge.Column column) {
            this.addCriterion("code < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanOrEqualTo(final String value) {
            this.addCriterion("code <=", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanOrEqualToColumn(final Charge.Column column) {
            this.addCriterion("code <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeLike(final String value) {
            this.addCriterion("code like", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeNotLike(final String value) {
            this.addCriterion("code not like", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeIn(final List<String> values) {
            this.addCriterion("code in", values, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeNotIn(final List<String> values) {
            this.addCriterion("code not in", values, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeBetween(final String value1, final String value2) {
            this.addCriterion("code between", value1, value2, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("code not between", value1, value2, "code");
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
        void example(final ChargeExample paramChargeExample);
    }
}
