package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class SkilldataExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public SkilldataExample() {
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
    
    public SkilldataExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public SkilldataExample orderBy(final String... orderByClauses) {
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
        final SkilldataExample example = new SkilldataExample();
        return example.createCriteria();
    }
    
    public SkilldataExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public SkilldataExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private SkilldataExample example;
        
        protected Criteria(final SkilldataExample example) {
            this.example = example;
        }
        
        public SkilldataExample example() {
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
            return deleted ? this.andDeletedEqualTo(Skilldata.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Skilldata.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Skilldata.Column column) {
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
        
        public Criteria andPidIsNull() {
            this.addCriterion("pid is null");
            return (Criteria)this;
        }
        
        public Criteria andPidIsNotNull() {
            this.addCriterion("pid is not null");
            return (Criteria)this;
        }
        
        public Criteria andPidEqualTo(final String value) {
            this.addCriterion("pid =", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("pid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidNotEqualTo(final String value) {
            this.addCriterion("pid <>", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("pid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThan(final String value) {
            this.addCriterion("pid >", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("pid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("pid >=", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("pid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidLessThan(final String value) {
            this.addCriterion("pid <", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("pid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanOrEqualTo(final String value) {
            this.addCriterion("pid <=", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("pid <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidLike(final String value) {
            this.addCriterion("pid like", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidNotLike(final String value) {
            this.addCriterion("pid not like", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidIn(final List<String> values) {
            this.addCriterion("pid in", values, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidNotIn(final List<String> values) {
            this.addCriterion("pid not in", values, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidBetween(final String value1, final String value2) {
            this.addCriterion("pid between", value1, value2, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidNotBetween(final String value1, final String value2) {
            this.addCriterion("pid not between", value1, value2, "pid");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameIsNull() {
            this.addCriterion("skill_name is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameIsNotNull() {
            this.addCriterion("skill_name is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameEqualTo(final String value) {
            this.addCriterion("skill_name =", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualTo(final String value) {
            this.addCriterion("skill_name <>", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThan(final String value) {
            this.addCriterion("skill_name >", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_name >=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThan(final String value) {
            this.addCriterion("skill_name <", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_name <=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_name <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLike(final String value) {
            this.addCriterion("skill_name like", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotLike(final String value) {
            this.addCriterion("skill_name not like", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameIn(final List<String> values) {
            this.addCriterion("skill_name in", values, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotIn(final List<String> values) {
            this.addCriterion("skill_name not in", values, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameBetween(final String value1, final String value2) {
            this.addCriterion("skill_name between", value1, value2, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_name not between", value1, value2, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelIsNull() {
            this.addCriterion("skill_level is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelIsNotNull() {
            this.addCriterion("skill_level is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelEqualTo(final Integer value) {
            this.addCriterion("skill_level =", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotEqualTo(final Integer value) {
            this.addCriterion("skill_level <>", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThan(final Integer value) {
            this.addCriterion("skill_level >", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_level >=", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThan(final Integer value) {
            this.addCriterion("skill_level <", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_level <=", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_level <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelIn(final List<Integer> values) {
            this.addCriterion("skill_level in", values, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotIn(final List<Integer> values) {
            this.addCriterion("skill_level not in", values, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_level between", value1, value2, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_level not between", value1, value2, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoIsNull() {
            this.addCriterion("skill_mubiao is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoIsNotNull() {
            this.addCriterion("skill_mubiao is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao =", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao <>", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThan(final Integer value) {
            this.addCriterion("skill_mubiao >", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao >=", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThan(final Integer value) {
            this.addCriterion("skill_mubiao <", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao <=", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("skill_mubiao <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoIn(final List<Integer> values) {
            this.addCriterion("skill_mubiao in", values, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotIn(final List<Integer> values) {
            this.addCriterion("skill_mubiao not in", values, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_mubiao between", value1, value2, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_mubiao not between", value1, value2, "skillMubiao");
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
        
        public Criteria andAddTimeEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Skilldata.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Skilldata.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Skilldata.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Skilldata.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Skilldata.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Skilldata.Column column) {
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
        void example(final SkilldataExample paramSkilldataExample);
    }
}
