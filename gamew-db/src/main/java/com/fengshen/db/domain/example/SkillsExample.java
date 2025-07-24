package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class SkillsExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public SkillsExample() {
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
    
    public SkillsExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public SkillsExample orderBy(final String... orderByClauses) {
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
        final SkillsExample example = new SkillsExample();
        return example.createCriteria();
    }
    
    public SkillsExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public SkillsExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private SkillsExample example;
        
        protected Criteria(final SkillsExample example) {
            this.example = example;
        }
        
        public SkillsExample example() {
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
            return deleted ? this.andDeletedEqualTo(Skills.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Skills.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Skills.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Skills.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andSkillIdHexEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_id_hex = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotEqualTo(final String value) {
            this.addCriterion("skill_id_hex <>", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_id_hex <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThan(final String value) {
            this.addCriterion("skill_id_hex >", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_id_hex > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_id_hex >=", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_id_hex >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThan(final String value) {
            this.addCriterion("skill_id_hex <", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_id_hex < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_id_hex <=", value, "skillIdHex");
            return (Criteria)this;
        }
        
        public Criteria andSkillIdHexLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andSkillNameEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualTo(final String value) {
            this.addCriterion("skill_name <>", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThan(final String value) {
            this.addCriterion("skill_name >", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_name >=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThan(final String value) {
            this.addCriterion("skill_name <", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_name <=", value, "skillName");
            return (Criteria)this;
        }
        
        public Criteria andSkillNameLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andSkillReqpolarEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_polar = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar <>", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_polar <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThan(final Integer value) {
            this.addCriterion("skill_req_polar >", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_req_polar > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar >=", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_polar >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThan(final Integer value) {
            this.addCriterion("skill_req_polar <", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_req_polar < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_polar <=", value, "skillReqpolar");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqpolarLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andSkillTypeIsNull() {
            this.addCriterion("skill_type is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeIsNotNull() {
            this.addCriterion("skill_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeEqualTo(final Integer value) {
            this.addCriterion("skill_type =", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeNotEqualTo(final Integer value) {
            this.addCriterion("skill_type <>", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeGreaterThan(final Integer value) {
            this.addCriterion("skill_type >", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_type >=", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLessThan(final Integer value) {
            this.addCriterion("skill_type <", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_type <=", value, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLessThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeIn(final List<Integer> values) {
            this.addCriterion("skill_type in", values, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeNotIn(final List<Integer> values) {
            this.addCriterion("skill_type not in", values, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_type between", value1, value2, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_type not between", value1, value2, "skillType");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelIsNull() {
            this.addCriterion("skill_type_level is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelIsNotNull() {
            this.addCriterion("skill_type_level is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelEqualTo(final Integer value) {
            this.addCriterion("skill_type_level =", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelNotEqualTo(final Integer value) {
            this.addCriterion("skill_type_level <>", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelGreaterThan(final Integer value) {
            this.addCriterion("skill_type_level >", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_type_level >=", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelLessThan(final Integer value) {
            this.addCriterion("skill_type_level <", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_type_level <=", value, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelLessThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_type_level <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelIn(final List<Integer> values) {
            this.addCriterion("skill_type_level in", values, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelNotIn(final List<Integer> values) {
            this.addCriterion("skill_type_level not in", values, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_type_level between", value1, value2, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillTypeLevelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_type_level not between", value1, value2, "skillTypeLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicIsNull() {
            this.addCriterion("skill_magic is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicIsNotNull() {
            this.addCriterion("skill_magic is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicEqualTo(final Integer value) {
            this.addCriterion("skill_magic =", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_magic = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicNotEqualTo(final Integer value) {
            this.addCriterion("skill_magic <>", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_magic <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicGreaterThan(final Integer value) {
            this.addCriterion("skill_magic >", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_magic > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_magic >=", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_magic >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicLessThan(final Integer value) {
            this.addCriterion("skill_magic <", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_magic < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_magic <=", value, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicLessThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_magic <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicIn(final List<Integer> values) {
            this.addCriterion("skill_magic in", values, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicNotIn(final List<Integer> values) {
            this.addCriterion("skill_magic not in", values, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_magic between", value1, value2, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillMagicNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_magic not between", value1, value2, "skillMagic");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelIsNull() {
            this.addCriterion("skill_req_level is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelIsNotNull() {
            this.addCriterion("skill_req_level is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelEqualTo(final Integer value) {
            this.addCriterion("skill_req_level =", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelNotEqualTo(final Integer value) {
            this.addCriterion("skill_req_level <>", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelGreaterThan(final Integer value) {
            this.addCriterion("skill_req_level >", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_level >=", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelLessThan(final Integer value) {
            this.addCriterion("skill_req_level <", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("skill_req_level <=", value, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelLessThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_req_level <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelIn(final List<Integer> values) {
            this.addCriterion("skill_req_level in", values, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelNotIn(final List<Integer> values) {
            this.addCriterion("skill_req_level not in", values, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_req_level between", value1, value2, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillReqLevelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("skill_req_level not between", value1, value2, "skillReqLevel");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextIsNull() {
            this.addCriterion("skill_context is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextIsNotNull() {
            this.addCriterion("skill_context is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextEqualTo(final String value) {
            this.addCriterion("skill_context =", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_context = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextNotEqualTo(final String value) {
            this.addCriterion("skill_context <>", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_context <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextGreaterThan(final String value) {
            this.addCriterion("skill_context >", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("skill_context > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skill_context >=", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_context >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextLessThan(final String value) {
            this.addCriterion("skill_context <", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextLessThanColumn(final Skills.Column column) {
            this.addCriterion("skill_context < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextLessThanOrEqualTo(final String value) {
            this.addCriterion("skill_context <=", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextLessThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("skill_context <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillContextLike(final String value) {
            this.addCriterion("skill_context like", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextNotLike(final String value) {
            this.addCriterion("skill_context not like", value, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextIn(final List<String> values) {
            this.addCriterion("skill_context in", values, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextNotIn(final List<String> values) {
            this.addCriterion("skill_context not in", values, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextBetween(final String value1, final String value2) {
            this.addCriterion("skill_context between", value1, value2, "skillContext");
            return (Criteria)this;
        }
        
        public Criteria andSkillContextNotBetween(final String value1, final String value2) {
            this.addCriterion("skill_context not between", value1, value2, "skillContext");
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
        
        public Criteria andAddTimeEqualToColumn(final Skills.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Skills.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Skills.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Skills.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Skills.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Skills.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Skills.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Skills.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Skills.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Skills.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Skills.Column column) {
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
        void example(final SkillsExample paramSkillsExample);
    }
}
