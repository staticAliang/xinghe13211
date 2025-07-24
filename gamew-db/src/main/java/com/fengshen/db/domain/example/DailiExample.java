package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class DailiExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public DailiExample() {
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
    
    public DailiExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public DailiExample orderBy(final String... orderByClauses) {
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
        final DailiExample example = new DailiExample();
        return example.createCriteria();
    }
    
    public DailiExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public DailiExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private DailiExample example;
        
        protected Criteria(final DailiExample example) {
            this.example = example;
        }
        
        public DailiExample example() {
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
            return deleted ? this.andDeletedEqualTo(Daili.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Daili.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Daili.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Daili.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Daili.Column column) {
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
        
        public Criteria andAccountIsNull() {
            this.addCriterion("account is null");
            return (Criteria)this;
        }
        
        public Criteria andAccountIsNotNull() {
            this.addCriterion("account is not null");
            return (Criteria)this;
        }
        
        public Criteria andAccountEqualTo(final String value) {
            this.addCriterion("account =", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountEqualToColumn(final Daili.Column column) {
            this.addCriterion("account = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountNotEqualTo(final String value) {
            this.addCriterion("account <>", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("account <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountGreaterThan(final String value) {
            this.addCriterion("account >", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("account > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountGreaterThanOrEqualTo(final String value) {
            this.addCriterion("account >=", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("account >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountLessThan(final String value) {
            this.addCriterion("account <", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountLessThanColumn(final Daili.Column column) {
            this.addCriterion("account < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountLessThanOrEqualTo(final String value) {
            this.addCriterion("account <=", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountLessThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("account <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAccountLike(final String value) {
            this.addCriterion("account like", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountNotLike(final String value) {
            this.addCriterion("account not like", value, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountIn(final List<String> values) {
            this.addCriterion("account in", values, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountNotIn(final List<String> values) {
            this.addCriterion("account not in", values, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountBetween(final String value1, final String value2) {
            this.addCriterion("account between", value1, value2, "account");
            return (Criteria)this;
        }
        
        public Criteria andAccountNotBetween(final String value1, final String value2) {
            this.addCriterion("account not between", value1, value2, "account");
            return (Criteria)this;
        }
        
        public Criteria andPasswdIsNull() {
            this.addCriterion("passwd is null");
            return (Criteria)this;
        }
        
        public Criteria andPasswdIsNotNull() {
            this.addCriterion("passwd is not null");
            return (Criteria)this;
        }
        
        public Criteria andPasswdEqualTo(final String value) {
            this.addCriterion("passwd =", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdEqualToColumn(final Daili.Column column) {
            this.addCriterion("passwd = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdNotEqualTo(final String value) {
            this.addCriterion("passwd <>", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("passwd <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdGreaterThan(final String value) {
            this.addCriterion("passwd >", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("passwd > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdGreaterThanOrEqualTo(final String value) {
            this.addCriterion("passwd >=", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("passwd >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdLessThan(final String value) {
            this.addCriterion("passwd <", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdLessThanColumn(final Daili.Column column) {
            this.addCriterion("passwd < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdLessThanOrEqualTo(final String value) {
            this.addCriterion("passwd <=", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdLessThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("passwd <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPasswdLike(final String value) {
            this.addCriterion("passwd like", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdNotLike(final String value) {
            this.addCriterion("passwd not like", value, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdIn(final List<String> values) {
            this.addCriterion("passwd in", values, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdNotIn(final List<String> values) {
            this.addCriterion("passwd not in", values, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdBetween(final String value1, final String value2) {
            this.addCriterion("passwd between", value1, value2, "passwd");
            return (Criteria)this;
        }
        
        public Criteria andPasswdNotBetween(final String value1, final String value2) {
            this.addCriterion("passwd not between", value1, value2, "passwd");
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
        
        public Criteria andCodeEqualToColumn(final Daili.Column column) {
            this.addCriterion("code = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeNotEqualTo(final String value) {
            this.addCriterion("code <>", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("code <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThan(final String value) {
            this.addCriterion("code >", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("code > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("code >=", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("code >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThan(final String value) {
            this.addCriterion("code <", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanColumn(final Daili.Column column) {
            this.addCriterion("code < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanOrEqualTo(final String value) {
            this.addCriterion("code <=", value, "code");
            return (Criteria)this;
        }
        
        public Criteria andCodeLessThanOrEqualToColumn(final Daili.Column column) {
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
        
        public Criteria andTokenIsNull() {
            this.addCriterion("token is null");
            return (Criteria)this;
        }
        
        public Criteria andTokenIsNotNull() {
            this.addCriterion("token is not null");
            return (Criteria)this;
        }
        
        public Criteria andTokenEqualTo(final String value) {
            this.addCriterion("token =", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenEqualToColumn(final Daili.Column column) {
            this.addCriterion("token = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenNotEqualTo(final String value) {
            this.addCriterion("token <>", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("token <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenGreaterThan(final String value) {
            this.addCriterion("token >", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("token > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenGreaterThanOrEqualTo(final String value) {
            this.addCriterion("token >=", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("token >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenLessThan(final String value) {
            this.addCriterion("token <", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenLessThanColumn(final Daili.Column column) {
            this.addCriterion("token < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenLessThanOrEqualTo(final String value) {
            this.addCriterion("token <=", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenLessThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("token <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTokenLike(final String value) {
            this.addCriterion("token like", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenNotLike(final String value) {
            this.addCriterion("token not like", value, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenIn(final List<String> values) {
            this.addCriterion("token in", values, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenNotIn(final List<String> values) {
            this.addCriterion("token not in", values, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenBetween(final String value1, final String value2) {
            this.addCriterion("token between", value1, value2, "token");
            return (Criteria)this;
        }
        
        public Criteria andTokenNotBetween(final String value1, final String value2) {
            this.addCriterion("token not between", value1, value2, "token");
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
        
        public Criteria andAddTimeEqualToColumn(final Daili.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Daili.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Daili.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Daili.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Daili.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Daili.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Daili.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Daili.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Daili.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Daili.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Daili.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Daili.Column column) {
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
        void example(final DailiExample paramDailiExample);
    }
}
