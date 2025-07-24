package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class SkillsChongwExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public SkillsChongwExample() {
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
    
    public SkillsChongwExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public SkillsChongwExample orderBy(final String... orderByClauses) {
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
        final SkillsChongwExample example = new SkillsChongwExample();
        return example.createCriteria();
    }
    
    public SkillsChongwExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public SkillsChongwExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private SkillsChongwExample example;
        
        protected Criteria(final SkillsChongwExample example) {
            this.example = example;
        }
        
        public SkillsChongwExample example() {
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
            return deleted ? this.andDeletedEqualTo(SkillsChongw.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(SkillsChongw.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andOwneridIsNull() {
            this.addCriterion("ownerid is null");
            return (Criteria)this;
        }
        
        public Criteria andOwneridIsNotNull() {
            this.addCriterion("ownerid is not null");
            return (Criteria)this;
        }
        
        public Criteria andOwneridEqualTo(final String value) {
            this.addCriterion("ownerid =", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotEqualTo(final String value) {
            this.addCriterion("ownerid <>", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThan(final String value) {
            this.addCriterion("ownerid >", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ownerid >=", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThan(final String value) {
            this.addCriterion("ownerid <", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanOrEqualTo(final String value) {
            this.addCriterion("ownerid <=", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("ownerid <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridLike(final String value) {
            this.addCriterion("ownerid like", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotLike(final String value) {
            this.addCriterion("ownerid not like", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridIn(final List<String> values) {
            this.addCriterion("ownerid in", values, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotIn(final List<String> values) {
            this.addCriterion("ownerid not in", values, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridBetween(final String value1, final String value2) {
            this.addCriterion("ownerid between", value1, value2, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotBetween(final String value1, final String value2) {
            this.addCriterion("ownerid not between", value1, value2, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidIsNull() {
            this.addCriterion("skll_cwid is null");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidIsNotNull() {
            this.addCriterion("skll_cwid is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidEqualTo(final String value) {
            this.addCriterion("skll_cwid =", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidNotEqualTo(final String value) {
            this.addCriterion("skll_cwid <>", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidGreaterThan(final String value) {
            this.addCriterion("skll_cwid >", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skll_cwid >=", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidLessThan(final String value) {
            this.addCriterion("skll_cwid <", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidLessThanOrEqualTo(final String value) {
            this.addCriterion("skll_cwid <=", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skll_cwid <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidLike(final String value) {
            this.addCriterion("skll_cwid like", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidNotLike(final String value) {
            this.addCriterion("skll_cwid not like", value, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidIn(final List<String> values) {
            this.addCriterion("skll_cwid in", values, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidNotIn(final List<String> values) {
            this.addCriterion("skll_cwid not in", values, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidBetween(final String value1, final String value2) {
            this.addCriterion("skll_cwid between", value1, value2, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkllCwidNotBetween(final String value1, final String value2) {
            this.addCriterion("skll_cwid not between", value1, value2, "skllCwid");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexIsNull() {
            this.addCriterion("skill_id_hex is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexIsNotNull() {
            this.addCriterion("skill_id_hex is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexEqualTo(final String value) {
            this.addCriterion("skill_id_hex =", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotEqualTo(final String value) {
            this.addCriterion("skill_id_hex <>", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThan(final String value) {
            this.addCriterion("skill_id_hex >", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_id_hex >=", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThan(final String value) {
            this.addCriterion("skill_id_hex <", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_id_hex <=", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_id_hex <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLike(final String value) {
            this.addCriterion("skill_id_hex like", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotLike(final String value) {
            this.addCriterion("skill_id_hex not like", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexIn(final List<String> values) {
            this.addCriterion("skill_id_hex in", values, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotIn(final List<String> values) {
            this.addCriterion("skill_id_hex not in", values, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexBetween(final String value1, final String value2) {
            this.addCriterion("skill_id_hex between", value1, value2, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_id_hex not between", value1, value2, "skillIdHex");
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
        
        public Criteria andSkillNameEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualTo(final String value) {
            this.addCriterion("skill_name <>", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThan(final String value) {
            this.addCriterion("skill_name >", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_name >=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThan(final String value) {
            this.addCriterion("skill_name <", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_name <=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andSkillReqpolarIsNull() {
            this.addCriterion("skill_req_polar is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarIsNotNull() {
            this.addCriterion("skill_req_polar is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar =", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar <>", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThan(final Integer value) {
            this.addCriterion("skill_req_polar >", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar >=", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThan(final Integer value) {
            this.addCriterion("skill_req_polar <", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar <=", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_req_polar <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarIn(final List<Integer> values) {
            this.addCriterion("skill_req_polar in", values, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotIn(final List<Integer> values) {
            this.addCriterion("skill_req_polar not in", values, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_req_polar between", value1, value2, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_req_polar not between", value1, value2, "skillReqpolar");
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
        
        public Criteria andSkillLevelEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_level = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotEqualTo(final Integer value) {
            this.addCriterion("skill_level <>", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_level <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThan(final Integer value) {
            this.addCriterion("skill_level >", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_level > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_level >=", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_level >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThan(final Integer value) {
            this.addCriterion("skill_level <", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_level < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_level <=", value, "skillLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillLevelLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andSkillMubiaoEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_mubiao = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao <>", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_mubiao <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThan(final Integer value) {
            this.addCriterion("skill_mubiao >", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_mubiao > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao >=", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_mubiao >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThan(final Integer value) {
            this.addCriterion("skill_mubiao <", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("skill_mubiao < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_mubiao <=", value, "skillMubiao");
            return (Criteria)this;
        }
        
        public Criteria andSkillMubiaoLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andTianshuIdIsNull() {
            this.addCriterion("tianshu_id is null");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdIsNotNull() {
            this.addCriterion("tianshu_id is not null");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdEqualTo(final String value) {
            this.addCriterion("tianshu_id =", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdNotEqualTo(final String value) {
            this.addCriterion("tianshu_id <>", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdGreaterThan(final String value) {
            this.addCriterion("tianshu_id >", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdGreaterThanOrEqualTo(final String value) {
            this.addCriterion("tianshu_id >=", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdLessThan(final String value) {
            this.addCriterion("tianshu_id <", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdLessThanOrEqualTo(final String value) {
            this.addCriterion("tianshu_id <=", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_id <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdLike(final String value) {
            this.addCriterion("tianshu_id like", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdNotLike(final String value) {
            this.addCriterion("tianshu_id not like", value, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdIn(final List<String> values) {
            this.addCriterion("tianshu_id in", values, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdNotIn(final List<String> values) {
            this.addCriterion("tianshu_id not in", values, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdBetween(final String value1, final String value2) {
            this.addCriterion("tianshu_id between", value1, value2, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuIdNotBetween(final String value1, final String value2) {
            this.addCriterion("tianshu_id not between", value1, value2, "tianshuId");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameIsNull() {
            this.addCriterion("tianshu_name is null");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameIsNotNull() {
            this.addCriterion("tianshu_name is not null");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameEqualTo(final String value) {
            this.addCriterion("tianshu_name =", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameNotEqualTo(final String value) {
            this.addCriterion("tianshu_name <>", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameGreaterThan(final String value) {
            this.addCriterion("tianshu_name >", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("tianshu_name >=", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameLessThan(final String value) {
            this.addCriterion("tianshu_name <", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameLessThanOrEqualTo(final String value) {
            this.addCriterion("tianshu_name <=", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameLessThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("tianshu_name <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameLike(final String value) {
            this.addCriterion("tianshu_name like", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameNotLike(final String value) {
            this.addCriterion("tianshu_name not like", value, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameIn(final List<String> values) {
            this.addCriterion("tianshu_name in", values, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameNotIn(final List<String> values) {
            this.addCriterion("tianshu_name not in", values, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameBetween(final String value1, final String value2) {
            this.addCriterion("tianshu_name between", value1, value2, "tianshuName");
            return (Criteria)this;
        }
        
        public Criteria andTianshuNameNotBetween(final String value1, final String value2) {
            this.addCriterion("tianshu_name not between", value1, value2, "tianshuName");
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
        
        public Criteria andAddTimeEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final SkillsChongw.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final SkillsChongw.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final SkillsChongw.Column column) {
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
        void example(final SkillsChongwExample paramSkillsChongwExample);
    }
}
