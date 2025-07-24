package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class ReportsExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public ReportsExample() {
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
    
    public ReportsExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public ReportsExample orderBy(final String... orderByClauses) {
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
        final ReportsExample example = new ReportsExample();
        return example.createCriteria();
    }
    
    public ReportsExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public ReportsExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private ReportsExample example;
        
        protected Criteria(final ReportsExample example) {
            this.example = example;
        }
        
        public ReportsExample example() {
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
            return deleted ? this.andDeletedEqualTo(Reports.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Reports.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Reports.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Reports.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Reports.Column column) {
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
        
        public Criteria andZhanghaoIsNull() {
            this.addCriterion("zhanghao is null");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoIsNotNull() {
            this.addCriterion("zhanghao is not null");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoEqualTo(final String value) {
            this.addCriterion("zhanghao =", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoEqualToColumn(final Reports.Column column) {
            this.addCriterion("zhanghao = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoNotEqualTo(final String value) {
            this.addCriterion("zhanghao <>", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("zhanghao <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoGreaterThan(final String value) {
            this.addCriterion("zhanghao >", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("zhanghao > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoGreaterThanOrEqualTo(final String value) {
            this.addCriterion("zhanghao >=", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("zhanghao >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoLessThan(final String value) {
            this.addCriterion("zhanghao <", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoLessThanColumn(final Reports.Column column) {
            this.addCriterion("zhanghao < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoLessThanOrEqualTo(final String value) {
            this.addCriterion("zhanghao <=", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoLessThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("zhanghao <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoLike(final String value) {
            this.addCriterion("zhanghao like", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoNotLike(final String value) {
            this.addCriterion("zhanghao not like", value, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoIn(final List<String> values) {
            this.addCriterion("zhanghao in", values, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoNotIn(final List<String> values) {
            this.addCriterion("zhanghao not in", values, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoBetween(final String value1, final String value2) {
            this.addCriterion("zhanghao between", value1, value2, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andZhanghaoNotBetween(final String value1, final String value2) {
            this.addCriterion("zhanghao not between", value1, value2, "zhanghao");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuIsNull() {
            this.addCriterion("yuanbaoshu is null");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuIsNotNull() {
            this.addCriterion("yuanbaoshu is not null");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuEqualTo(final Integer value) {
            this.addCriterion("yuanbaoshu =", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuEqualToColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuNotEqualTo(final Integer value) {
            this.addCriterion("yuanbaoshu <>", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuGreaterThan(final Integer value) {
            this.addCriterion("yuanbaoshu >", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("yuanbaoshu >=", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuLessThan(final Integer value) {
            this.addCriterion("yuanbaoshu <", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuLessThanColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuLessThanOrEqualTo(final Integer value) {
            this.addCriterion("yuanbaoshu <=", value, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuLessThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("yuanbaoshu <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuIn(final List<Integer> values) {
            this.addCriterion("yuanbaoshu in", values, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuNotIn(final List<Integer> values) {
            this.addCriterion("yuanbaoshu not in", values, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuanbaoshu between", value1, value2, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andYuanbaoshuNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuanbaoshu not between", value1, value2, "yuanbaoshu");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiIsNull() {
            this.addCriterion("shifouchongzhi is null");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiIsNotNull() {
            this.addCriterion("shifouchongzhi is not null");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiEqualTo(final String value) {
            this.addCriterion("shifouchongzhi =", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiEqualToColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiNotEqualTo(final String value) {
            this.addCriterion("shifouchongzhi <>", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiGreaterThan(final String value) {
            this.addCriterion("shifouchongzhi >", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiGreaterThanOrEqualTo(final String value) {
            this.addCriterion("shifouchongzhi >=", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiLessThan(final String value) {
            this.addCriterion("shifouchongzhi <", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiLessThanColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiLessThanOrEqualTo(final String value) {
            this.addCriterion("shifouchongzhi <=", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiLessThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("shifouchongzhi <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiLike(final String value) {
            this.addCriterion("shifouchongzhi like", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiNotLike(final String value) {
            this.addCriterion("shifouchongzhi not like", value, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiIn(final List<String> values) {
            this.addCriterion("shifouchongzhi in", values, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiNotIn(final List<String> values) {
            this.addCriterion("shifouchongzhi not in", values, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiBetween(final String value1, final String value2) {
            this.addCriterion("shifouchongzhi between", value1, value2, "shifouchongzhi");
            return (Criteria)this;
        }
        
        public Criteria andShifouchongzhiNotBetween(final String value1, final String value2) {
            this.addCriterion("shifouchongzhi not between", value1, value2, "shifouchongzhi");
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
        
        public Criteria andAddTimeEqualToColumn(final Reports.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Reports.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Reports.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Reports.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Reports.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Reports.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Reports.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Reports.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Reports.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Reports.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Reports.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Reports.Column column) {
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
        void example(final ReportsExample paramReportsExample);
    }
}
