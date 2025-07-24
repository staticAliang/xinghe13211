package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class SrenwuExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public SrenwuExample() {
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
    
    public SrenwuExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public SrenwuExample orderBy(final String... orderByClauses) {
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
        final SrenwuExample example = new SrenwuExample();
        return example.createCriteria();
    }
    
    public SrenwuExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public SrenwuExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private SrenwuExample example;
        
        protected Criteria(final SrenwuExample example) {
            this.example = example;
        }
        
        public SrenwuExample example() {
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
            return deleted ? this.andDeletedEqualTo(Srenwu.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Srenwu.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        
        public Criteria andPidEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("pid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidNotEqualTo(final String value) {
            this.addCriterion("pid <>", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("pid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThan(final String value) {
            this.addCriterion("pid >", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("pid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("pid >=", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("pid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidLessThan(final String value) {
            this.addCriterion("pid <", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("pid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanOrEqualTo(final String value) {
            this.addCriterion("pid <=", value, "pid");
            return (Criteria)this;
        }
        
        public Criteria andPidLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        
        public Criteria andRidIsNull() {
            this.addCriterion("rid is null");
            return (Criteria)this;
        }
        
        public Criteria andRidIsNotNull() {
            this.addCriterion("rid is not null");
            return (Criteria)this;
        }
        
        public Criteria andRidEqualTo(final Integer value) {
            this.addCriterion("rid =", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("rid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidNotEqualTo(final Integer value) {
            this.addCriterion("rid <>", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("rid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidGreaterThan(final Integer value) {
            this.addCriterion("rid >", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("rid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("rid >=", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("rid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidLessThan(final Integer value) {
            this.addCriterion("rid <", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("rid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidLessThanOrEqualTo(final Integer value) {
            this.addCriterion("rid <=", value, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidLessThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("rid <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRidIn(final List<Integer> values) {
            this.addCriterion("rid in", values, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidNotIn(final List<Integer> values) {
            this.addCriterion("rid not in", values, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rid between", value1, value2, "rid");
            return (Criteria)this;
        }
        
        public Criteria andRidNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rid not between", value1, value2, "rid");
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
        
        public Criteria andSkillNameEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualTo(final String value) {
            this.addCriterion("skill_name <>", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThan(final String value) {
            this.addCriterion("skill_name >", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_name >=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThan(final String value) {
            this.addCriterion("skill_name <", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_name <=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        
        public Criteria andSkillJieshaoIsNull() {
            this.addCriterion("skill_jieshao is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoIsNotNull() {
            this.addCriterion("skill_jieshao is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoEqualTo(final String value) {
            this.addCriterion("skill_jieshao =", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoNotEqualTo(final String value) {
            this.addCriterion("skill_jieshao <>", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoGreaterThan(final String value) {
            this.addCriterion("skill_jieshao >", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_jieshao >=", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoLessThan(final String value) {
            this.addCriterion("skill_jieshao <", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_jieshao <=", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoLessThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_jieshao <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoLike(final String value) {
            this.addCriterion("skill_jieshao like", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoNotLike(final String value) {
            this.addCriterion("skill_jieshao not like", value, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoIn(final List<String> values) {
            this.addCriterion("skill_jieshao in", values, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoNotIn(final List<String> values) {
            this.addCriterion("skill_jieshao not in", values, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoBetween(final String value1, final String value2) {
            this.addCriterion("skill_jieshao between", value1, value2, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillJieshaoNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_jieshao not between", value1, value2, "skillJieshao");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiIsNull() {
            this.addCriterion("skill_dqti is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiIsNotNull() {
            this.addCriterion("skill_dqti is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiEqualTo(final String value) {
            this.addCriterion("skill_dqti =", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiNotEqualTo(final String value) {
            this.addCriterion("skill_dqti <>", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiGreaterThan(final String value) {
            this.addCriterion("skill_dqti >", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_dqti >=", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiLessThan(final String value) {
            this.addCriterion("skill_dqti <", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_dqti <=", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiLessThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_dqti <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiLike(final String value) {
            this.addCriterion("skill_dqti like", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiNotLike(final String value) {
            this.addCriterion("skill_dqti not like", value, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiIn(final List<String> values) {
            this.addCriterion("skill_dqti in", values, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiNotIn(final List<String> values) {
            this.addCriterion("skill_dqti not in", values, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiBetween(final String value1, final String value2) {
            this.addCriterion("skill_dqti between", value1, value2, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillDqtiNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_dqti not between", value1, value2, "skillDqti");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckIsNull() {
            this.addCriterion("skill_xck is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckIsNotNull() {
            this.addCriterion("skill_xck is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckEqualTo(final String value) {
            this.addCriterion("skill_xck =", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckNotEqualTo(final String value) {
            this.addCriterion("skill_xck <>", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckGreaterThan(final String value) {
            this.addCriterion("skill_xck >", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_xck >=", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckLessThan(final String value) {
            this.addCriterion("skill_xck <", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_xck <=", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckLessThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("skill_xck <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillXckLike(final String value) {
            this.addCriterion("skill_xck like", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckNotLike(final String value) {
            this.addCriterion("skill_xck not like", value, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckIn(final List<String> values) {
            this.addCriterion("skill_xck in", values, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckNotIn(final List<String> values) {
            this.addCriterion("skill_xck not in", values, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckBetween(final String value1, final String value2) {
            this.addCriterion("skill_xck between", value1, value2, "skillXck");
            return (Criteria)this;
        }
        
        public Criteria andSkillXckNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_xck not between", value1, value2, "skillXck");
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
        
        public Criteria andAddTimeEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Srenwu.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Srenwu.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Srenwu.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Srenwu.Column column) {
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
        void example(final SrenwuExample paramSrenwuExample);
    }
}
