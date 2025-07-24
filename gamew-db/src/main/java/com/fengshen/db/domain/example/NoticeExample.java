package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class NoticeExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public NoticeExample() {
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
    
    public NoticeExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public NoticeExample orderBy(final String... orderByClauses) {
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
        final NoticeExample example = new NoticeExample();
        return example.createCriteria();
    }
    
    public NoticeExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public NoticeExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private NoticeExample example;
        
        protected Criteria(final NoticeExample example) {
            this.example = example;
        }
        
        public NoticeExample example() {
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
            return deleted ? this.andDeletedEqualTo(Notice.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Notice.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Notice.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Notice.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Notice.Column column) {
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
        
        public Criteria andMessageIsNull() {
            this.addCriterion("message is null");
            return (Criteria)this;
        }
        
        public Criteria andMessageIsNotNull() {
            this.addCriterion("message is not null");
            return (Criteria)this;
        }
        
        public Criteria andMessageEqualTo(final String value) {
            this.addCriterion("message =", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageEqualToColumn(final Notice.Column column) {
            this.addCriterion("message = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageNotEqualTo(final String value) {
            this.addCriterion("message <>", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("message <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageGreaterThan(final String value) {
            this.addCriterion("message >", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("message > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageGreaterThanOrEqualTo(final String value) {
            this.addCriterion("message >=", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("message >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageLessThan(final String value) {
            this.addCriterion("message <", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageLessThanColumn(final Notice.Column column) {
            this.addCriterion("message < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageLessThanOrEqualTo(final String value) {
            this.addCriterion("message <=", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageLessThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("message <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMessageLike(final String value) {
            this.addCriterion("message like", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageNotLike(final String value) {
            this.addCriterion("message not like", value, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageIn(final List<String> values) {
            this.addCriterion("message in", values, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageNotIn(final List<String> values) {
            this.addCriterion("message not in", values, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageBetween(final String value1, final String value2) {
            this.addCriterion("message between", value1, value2, "message");
            return (Criteria)this;
        }
        
        public Criteria andMessageNotBetween(final String value1, final String value2) {
            this.addCriterion("message not between", value1, value2, "message");
            return (Criteria)this;
        }
        
        public Criteria andTimeIsNull() {
            this.addCriterion("`time` is null");
            return (Criteria)this;
        }
        
        public Criteria andTimeIsNotNull() {
            this.addCriterion("`time` is not null");
            return (Criteria)this;
        }
        
        public Criteria andTimeEqualTo(final Integer value) {
            this.addCriterion("`time` =", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeEqualToColumn(final Notice.Column column) {
            this.addCriterion("`time` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeNotEqualTo(final Integer value) {
            this.addCriterion("`time` <>", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("`time` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeGreaterThan(final Integer value) {
            this.addCriterion("`time` >", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("`time` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`time` >=", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("`time` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeLessThan(final Integer value) {
            this.addCriterion("`time` <", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeLessThanColumn(final Notice.Column column) {
            this.addCriterion("`time` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`time` <=", value, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeLessThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("`time` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTimeIn(final List<Integer> values) {
            this.addCriterion("`time` in", values, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeNotIn(final List<Integer> values) {
            this.addCriterion("`time` not in", values, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`time` between", value1, value2, "time");
            return (Criteria)this;
        }
        
        public Criteria andTimeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`time` not between", value1, value2, "time");
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
        
        public Criteria andAddTimeEqualToColumn(final Notice.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Notice.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Notice.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Notice.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Notice.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Notice.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Notice.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Notice.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Notice.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Notice.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Notice.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Notice.Column column) {
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
        void example(final NoticeExample paramNoticeExample);
    }
}
