package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class PetsExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public PetsExample() {
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
    
    public PetsExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public PetsExample orderBy(final String... orderByClauses) {
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
        final PetsExample example = new PetsExample();
        return example.createCriteria();
    }
    
    public PetsExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public PetsExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private PetsExample example;
        
        protected Criteria(final PetsExample example) {
            this.example = example;
        }
        
        public PetsExample example() {
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
            return deleted ? this.andDeletedEqualTo(Pets.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Pets.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Pets.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Pets.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andOwneridEqualToColumn(final Pets.Column column) {
            this.addCriterion("ownerid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotEqualTo(final String value) {
            this.addCriterion("ownerid <>", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("ownerid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThan(final String value) {
            this.addCriterion("ownerid >", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("ownerid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanOrEqualTo(final String value) {
            this.addCriterion("ownerid >=", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("ownerid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThan(final String value) {
            this.addCriterion("ownerid <", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanColumn(final Pets.Column column) {
            this.addCriterion("ownerid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanOrEqualTo(final String value) {
            this.addCriterion("ownerid <=", value, "ownerid");
            return (Criteria)this;
        }
        
        public Criteria andOwneridLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andPetidIsNull() {
            this.addCriterion("petid is null");
            return (Criteria)this;
        }
        
        public Criteria andPetidIsNotNull() {
            this.addCriterion("petid is not null");
            return (Criteria)this;
        }
        
        public Criteria andPetidEqualTo(final String value) {
            this.addCriterion("petid =", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidEqualToColumn(final Pets.Column column) {
            this.addCriterion("petid = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidNotEqualTo(final String value) {
            this.addCriterion("petid <>", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("petid <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidGreaterThan(final String value) {
            this.addCriterion("petid >", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("petid > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidGreaterThanOrEqualTo(final String value) {
            this.addCriterion("petid >=", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("petid >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidLessThan(final String value) {
            this.addCriterion("petid <", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidLessThanColumn(final Pets.Column column) {
            this.addCriterion("petid < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidLessThanOrEqualTo(final String value) {
            this.addCriterion("petid <=", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("petid <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPetidLike(final String value) {
            this.addCriterion("petid like", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidNotLike(final String value) {
            this.addCriterion("petid not like", value, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidIn(final List<String> values) {
            this.addCriterion("petid in", values, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidNotIn(final List<String> values) {
            this.addCriterion("petid not in", values, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidBetween(final String value1, final String value2) {
            this.addCriterion("petid between", value1, value2, "petid");
            return (Criteria)this;
        }
        
        public Criteria andPetidNotBetween(final String value1, final String value2) {
            this.addCriterion("petid not between", value1, value2, "petid");
            return (Criteria)this;
        }
        
        public Criteria andNicknameIsNull() {
            this.addCriterion("nickname is null");
            return (Criteria)this;
        }
        
        public Criteria andNicknameIsNotNull() {
            this.addCriterion("nickname is not null");
            return (Criteria)this;
        }
        
        public Criteria andNicknameEqualTo(final String value) {
            this.addCriterion("nickname =", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameEqualToColumn(final Pets.Column column) {
            this.addCriterion("nickname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameNotEqualTo(final String value) {
            this.addCriterion("nickname <>", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("nickname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameGreaterThan(final String value) {
            this.addCriterion("nickname >", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("nickname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("nickname >=", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("nickname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameLessThan(final String value) {
            this.addCriterion("nickname <", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameLessThanColumn(final Pets.Column column) {
            this.addCriterion("nickname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameLessThanOrEqualTo(final String value) {
            this.addCriterion("nickname <=", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("nickname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNicknameLike(final String value) {
            this.addCriterion("nickname like", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameNotLike(final String value) {
            this.addCriterion("nickname not like", value, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameIn(final List<String> values) {
            this.addCriterion("nickname in", values, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameNotIn(final List<String> values) {
            this.addCriterion("nickname not in", values, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameBetween(final String value1, final String value2) {
            this.addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria)this;
        }
        
        public Criteria andNicknameNotBetween(final String value1, final String value2) {
            this.addCriterion("nickname not between", value1, value2, "nickname");
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
        
        public Criteria andNameEqualToColumn(final Pets.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final Pets.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andHorsetypeIsNull() {
            this.addCriterion("horsetype is null");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeIsNotNull() {
            this.addCriterion("horsetype is not null");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeEqualTo(final Integer value) {
            this.addCriterion("horsetype =", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeEqualToColumn(final Pets.Column column) {
            this.addCriterion("horsetype = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeNotEqualTo(final Integer value) {
            this.addCriterion("horsetype <>", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("horsetype <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeGreaterThan(final Integer value) {
            this.addCriterion("horsetype >", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("horsetype > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("horsetype >=", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("horsetype >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeLessThan(final Integer value) {
            this.addCriterion("horsetype <", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeLessThanColumn(final Pets.Column column) {
            this.addCriterion("horsetype < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("horsetype <=", value, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("horsetype <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeIn(final List<Integer> values) {
            this.addCriterion("horsetype in", values, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeNotIn(final List<Integer> values) {
            this.addCriterion("horsetype not in", values, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("horsetype between", value1, value2, "horsetype");
            return (Criteria)this;
        }
        
        public Criteria andHorsetypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("horsetype not between", value1, value2, "horsetype");
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
        
        public Criteria andTypeEqualToColumn(final Pets.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final Pets.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andLevelIsNull() {
            this.addCriterion("`level` is null");
            return (Criteria)this;
        }
        
        public Criteria andLevelIsNotNull() {
            this.addCriterion("`level` is not null");
            return (Criteria)this;
        }
        
        public Criteria andLevelEqualTo(final Integer value) {
            this.addCriterion("`level` =", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelEqualToColumn(final Pets.Column column) {
            this.addCriterion("`level` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualTo(final Integer value) {
            this.addCriterion("`level` <>", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("`level` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThan(final Integer value) {
            this.addCriterion("`level` >", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("`level` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` >=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("`level` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThan(final Integer value) {
            this.addCriterion("`level` <", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanColumn(final Pets.Column column) {
            this.addCriterion("`level` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` <=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("`level` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelIn(final List<Integer> values) {
            this.addCriterion("`level` in", values, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotIn(final List<Integer> values) {
            this.addCriterion("`level` not in", values, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`level` between", value1, value2, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`level` not between", value1, value2, "level");
            return (Criteria)this;
        }
        
        public Criteria andLiliangIsNull() {
            this.addCriterion("liliang is null");
            return (Criteria)this;
        }
        
        public Criteria andLiliangIsNotNull() {
            this.addCriterion("liliang is not null");
            return (Criteria)this;
        }
        
        public Criteria andLiliangEqualTo(final Integer value) {
            this.addCriterion("liliang =", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangEqualToColumn(final Pets.Column column) {
            this.addCriterion("liliang = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangNotEqualTo(final Integer value) {
            this.addCriterion("liliang <>", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("liliang <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangGreaterThan(final Integer value) {
            this.addCriterion("liliang >", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("liliang > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("liliang >=", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("liliang >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangLessThan(final Integer value) {
            this.addCriterion("liliang <", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangLessThanColumn(final Pets.Column column) {
            this.addCriterion("liliang < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangLessThanOrEqualTo(final Integer value) {
            this.addCriterion("liliang <=", value, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("liliang <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLiliangIn(final List<Integer> values) {
            this.addCriterion("liliang in", values, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangNotIn(final List<Integer> values) {
            this.addCriterion("liliang not in", values, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangBetween(final Integer value1, final Integer value2) {
            this.addCriterion("liliang between", value1, value2, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andLiliangNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("liliang not between", value1, value2, "liliang");
            return (Criteria)this;
        }
        
        public Criteria andMinjieIsNull() {
            this.addCriterion("minjie is null");
            return (Criteria)this;
        }
        
        public Criteria andMinjieIsNotNull() {
            this.addCriterion("minjie is not null");
            return (Criteria)this;
        }
        
        public Criteria andMinjieEqualTo(final Integer value) {
            this.addCriterion("minjie =", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieEqualToColumn(final Pets.Column column) {
            this.addCriterion("minjie = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieNotEqualTo(final Integer value) {
            this.addCriterion("minjie <>", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("minjie <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieGreaterThan(final Integer value) {
            this.addCriterion("minjie >", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("minjie > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("minjie >=", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("minjie >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieLessThan(final Integer value) {
            this.addCriterion("minjie <", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieLessThanColumn(final Pets.Column column) {
            this.addCriterion("minjie < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieLessThanOrEqualTo(final Integer value) {
            this.addCriterion("minjie <=", value, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("minjie <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMinjieIn(final List<Integer> values) {
            this.addCriterion("minjie in", values, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieNotIn(final List<Integer> values) {
            this.addCriterion("minjie not in", values, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieBetween(final Integer value1, final Integer value2) {
            this.addCriterion("minjie between", value1, value2, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andMinjieNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("minjie not between", value1, value2, "minjie");
            return (Criteria)this;
        }
        
        public Criteria andLingliIsNull() {
            this.addCriterion("lingli is null");
            return (Criteria)this;
        }
        
        public Criteria andLingliIsNotNull() {
            this.addCriterion("lingli is not null");
            return (Criteria)this;
        }
        
        public Criteria andLingliEqualTo(final Integer value) {
            this.addCriterion("lingli =", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliEqualToColumn(final Pets.Column column) {
            this.addCriterion("lingli = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliNotEqualTo(final Integer value) {
            this.addCriterion("lingli <>", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("lingli <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliGreaterThan(final Integer value) {
            this.addCriterion("lingli >", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("lingli > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("lingli >=", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("lingli >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliLessThan(final Integer value) {
            this.addCriterion("lingli <", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliLessThanColumn(final Pets.Column column) {
            this.addCriterion("lingli < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliLessThanOrEqualTo(final Integer value) {
            this.addCriterion("lingli <=", value, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("lingli <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLingliIn(final List<Integer> values) {
            this.addCriterion("lingli in", values, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliNotIn(final List<Integer> values) {
            this.addCriterion("lingli not in", values, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliBetween(final Integer value1, final Integer value2) {
            this.addCriterion("lingli between", value1, value2, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andLingliNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("lingli not between", value1, value2, "lingli");
            return (Criteria)this;
        }
        
        public Criteria andTiliIsNull() {
            this.addCriterion("tili is null");
            return (Criteria)this;
        }
        
        public Criteria andTiliIsNotNull() {
            this.addCriterion("tili is not null");
            return (Criteria)this;
        }
        
        public Criteria andTiliEqualTo(final Integer value) {
            this.addCriterion("tili =", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliEqualToColumn(final Pets.Column column) {
            this.addCriterion("tili = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliNotEqualTo(final Integer value) {
            this.addCriterion("tili <>", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("tili <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliGreaterThan(final Integer value) {
            this.addCriterion("tili >", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("tili > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("tili >=", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("tili >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliLessThan(final Integer value) {
            this.addCriterion("tili <", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliLessThanColumn(final Pets.Column column) {
            this.addCriterion("tili < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliLessThanOrEqualTo(final Integer value) {
            this.addCriterion("tili <=", value, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("tili <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTiliIn(final List<Integer> values) {
            this.addCriterion("tili in", values, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliNotIn(final List<Integer> values) {
            this.addCriterion("tili not in", values, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliBetween(final Integer value1, final Integer value2) {
            this.addCriterion("tili between", value1, value2, "tili");
            return (Criteria)this;
        }
        
        public Criteria andTiliNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("tili not between", value1, value2, "tili");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxIsNull() {
            this.addCriterion("dianhualx is null");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxIsNotNull() {
            this.addCriterion("dianhualx is not null");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxEqualTo(final Integer value) {
            this.addCriterion("dianhualx =", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhualx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxNotEqualTo(final Integer value) {
            this.addCriterion("dianhualx <>", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhualx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxGreaterThan(final Integer value) {
            this.addCriterion("dianhualx >", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("dianhualx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhualx >=", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhualx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxLessThan(final Integer value) {
            this.addCriterion("dianhualx <", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxLessThanColumn(final Pets.Column column) {
            this.addCriterion("dianhualx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhualx <=", value, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhualx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhualxIn(final List<Integer> values) {
            this.addCriterion("dianhualx in", values, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxNotIn(final List<Integer> values) {
            this.addCriterion("dianhualx not in", values, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhualx between", value1, value2, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhualxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhualx not between", value1, value2, "dianhualx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdIsNull() {
            this.addCriterion("dianhuazd is null");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdIsNotNull() {
            this.addCriterion("dianhuazd is not null");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdEqualTo(final Integer value) {
            this.addCriterion("dianhuazd =", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdNotEqualTo(final Integer value) {
            this.addCriterion("dianhuazd <>", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdGreaterThan(final Integer value) {
            this.addCriterion("dianhuazd >", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhuazd >=", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdLessThan(final Integer value) {
            this.addCriterion("dianhuazd <", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdLessThanColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhuazd <=", value, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazd <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdIn(final List<Integer> values) {
            this.addCriterion("dianhuazd in", values, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdNotIn(final List<Integer> values) {
            this.addCriterion("dianhuazd not in", values, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhuazd between", value1, value2, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhuazd not between", value1, value2, "dianhuazd");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxIsNull() {
            this.addCriterion("dianhuazx is null");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxIsNotNull() {
            this.addCriterion("dianhuazx is not null");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxEqualTo(final Integer value) {
            this.addCriterion("dianhuazx =", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxNotEqualTo(final Integer value) {
            this.addCriterion("dianhuazx <>", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxGreaterThan(final Integer value) {
            this.addCriterion("dianhuazx >", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhuazx >=", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxLessThan(final Integer value) {
            this.addCriterion("dianhuazx <", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxLessThanColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("dianhuazx <=", value, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("dianhuazx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxIn(final List<Integer> values) {
            this.addCriterion("dianhuazx in", values, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxNotIn(final List<Integer> values) {
            this.addCriterion("dianhuazx not in", values, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhuazx between", value1, value2, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andDianhuazxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("dianhuazx not between", value1, value2, "dianhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxIsNull() {
            this.addCriterion("yuhualx is null");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxIsNotNull() {
            this.addCriterion("yuhualx is not null");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxEqualTo(final Integer value) {
            this.addCriterion("yuhualx =", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhualx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxNotEqualTo(final Integer value) {
            this.addCriterion("yuhualx <>", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhualx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxGreaterThan(final Integer value) {
            this.addCriterion("yuhualx >", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("yuhualx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhualx >=", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhualx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxLessThan(final Integer value) {
            this.addCriterion("yuhualx <", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxLessThanColumn(final Pets.Column column) {
            this.addCriterion("yuhualx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhualx <=", value, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhualx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhualxIn(final List<Integer> values) {
            this.addCriterion("yuhualx in", values, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxNotIn(final List<Integer> values) {
            this.addCriterion("yuhualx not in", values, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhualx between", value1, value2, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhualxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhualx not between", value1, value2, "yuhualx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdIsNull() {
            this.addCriterion("yuhuazd is null");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdIsNotNull() {
            this.addCriterion("yuhuazd is not null");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdEqualTo(final Integer value) {
            this.addCriterion("yuhuazd =", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdNotEqualTo(final Integer value) {
            this.addCriterion("yuhuazd <>", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdGreaterThan(final Integer value) {
            this.addCriterion("yuhuazd >", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhuazd >=", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdLessThan(final Integer value) {
            this.addCriterion("yuhuazd <", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdLessThanColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhuazd <=", value, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazd <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdIn(final List<Integer> values) {
            this.addCriterion("yuhuazd in", values, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdNotIn(final List<Integer> values) {
            this.addCriterion("yuhuazd not in", values, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhuazd between", value1, value2, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhuazd not between", value1, value2, "yuhuazd");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxIsNull() {
            this.addCriterion("yuhuazx is null");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxIsNotNull() {
            this.addCriterion("yuhuazx is not null");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxEqualTo(final Integer value) {
            this.addCriterion("yuhuazx =", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxNotEqualTo(final Integer value) {
            this.addCriterion("yuhuazx <>", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxGreaterThan(final Integer value) {
            this.addCriterion("yuhuazx >", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhuazx >=", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxLessThan(final Integer value) {
            this.addCriterion("yuhuazx <", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxLessThanColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("yuhuazx <=", value, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("yuhuazx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxIn(final List<Integer> values) {
            this.addCriterion("yuhuazx in", values, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxNotIn(final List<Integer> values) {
            this.addCriterion("yuhuazx not in", values, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhuazx between", value1, value2, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andYuhuazxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("yuhuazx not between", value1, value2, "yuhuazx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxIsNull() {
            this.addCriterion("cwjyzx is null");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxIsNotNull() {
            this.addCriterion("cwjyzx is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxEqualTo(final Integer value) {
            this.addCriterion("cwjyzx =", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxNotEqualTo(final Integer value) {
            this.addCriterion("cwjyzx <>", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxGreaterThan(final Integer value) {
            this.addCriterion("cwjyzx >", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cwjyzx >=", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxLessThan(final Integer value) {
            this.addCriterion("cwjyzx <", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxLessThanColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cwjyzx <=", value, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxIn(final List<Integer> values) {
            this.addCriterion("cwjyzx in", values, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxNotIn(final List<Integer> values) {
            this.addCriterion("cwjyzx not in", values, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cwjyzx between", value1, value2, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cwjyzx not between", value1, value2, "cwjyzx");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdIsNull() {
            this.addCriterion("cwjyzd is null");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdIsNotNull() {
            this.addCriterion("cwjyzd is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdEqualTo(final Integer value) {
            this.addCriterion("cwjyzd =", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdNotEqualTo(final Integer value) {
            this.addCriterion("cwjyzd <>", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdGreaterThan(final Integer value) {
            this.addCriterion("cwjyzd >", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cwjyzd >=", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdLessThan(final Integer value) {
            this.addCriterion("cwjyzd <", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdLessThanColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cwjyzd <=", value, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cwjyzd <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdIn(final List<Integer> values) {
            this.addCriterion("cwjyzd in", values, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdNotIn(final List<Integer> values) {
            this.addCriterion("cwjyzd not in", values, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cwjyzd between", value1, value2, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andCwjyzdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cwjyzd not between", value1, value2, "cwjyzd");
            return (Criteria)this;
        }
        
        public Criteria andFeishengIsNull() {
            this.addCriterion("feisheng is null");
            return (Criteria)this;
        }
        
        public Criteria andFeishengIsNotNull() {
            this.addCriterion("feisheng is not null");
            return (Criteria)this;
        }
        
        public Criteria andFeishengEqualTo(final Integer value) {
            this.addCriterion("feisheng =", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengEqualToColumn(final Pets.Column column) {
            this.addCriterion("feisheng = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengNotEqualTo(final Integer value) {
            this.addCriterion("feisheng <>", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("feisheng <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengGreaterThan(final Integer value) {
            this.addCriterion("feisheng >", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("feisheng > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("feisheng >=", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("feisheng >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengLessThan(final Integer value) {
            this.addCriterion("feisheng <", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengLessThanColumn(final Pets.Column column) {
            this.addCriterion("feisheng < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengLessThanOrEqualTo(final Integer value) {
            this.addCriterion("feisheng <=", value, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("feisheng <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFeishengIn(final List<Integer> values) {
            this.addCriterion("feisheng in", values, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengNotIn(final List<Integer> values) {
            this.addCriterion("feisheng not in", values, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengBetween(final Integer value1, final Integer value2) {
            this.addCriterion("feisheng between", value1, value2, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFeishengNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("feisheng not between", value1, value2, "feisheng");
            return (Criteria)this;
        }
        
        public Criteria andFsuduIsNull() {
            this.addCriterion("fsudu is null");
            return (Criteria)this;
        }
        
        public Criteria andFsuduIsNotNull() {
            this.addCriterion("fsudu is not null");
            return (Criteria)this;
        }
        
        public Criteria andFsuduEqualTo(final Integer value) {
            this.addCriterion("fsudu =", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduEqualToColumn(final Pets.Column column) {
            this.addCriterion("fsudu = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduNotEqualTo(final Integer value) {
            this.addCriterion("fsudu <>", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("fsudu <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduGreaterThan(final Integer value) {
            this.addCriterion("fsudu >", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("fsudu > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("fsudu >=", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("fsudu >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduLessThan(final Integer value) {
            this.addCriterion("fsudu <", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduLessThanColumn(final Pets.Column column) {
            this.addCriterion("fsudu < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduLessThanOrEqualTo(final Integer value) {
            this.addCriterion("fsudu <=", value, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("fsudu <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFsuduIn(final List<Integer> values) {
            this.addCriterion("fsudu in", values, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduNotIn(final List<Integer> values) {
            this.addCriterion("fsudu not in", values, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fsudu between", value1, value2, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andFsuduNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("fsudu not between", value1, value2, "fsudu");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgIsNull() {
            this.addCriterion("qhcw_wg is null");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgIsNotNull() {
            this.addCriterion("qhcw_wg is not null");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgEqualTo(final Integer value) {
            this.addCriterion("qhcw_wg =", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgNotEqualTo(final Integer value) {
            this.addCriterion("qhcw_wg <>", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgGreaterThan(final Integer value) {
            this.addCriterion("qhcw_wg >", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("qhcw_wg >=", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgLessThan(final Integer value) {
            this.addCriterion("qhcw_wg <", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgLessThanColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgLessThanOrEqualTo(final Integer value) {
            this.addCriterion("qhcw_wg <=", value, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_wg <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgIn(final List<Integer> values) {
            this.addCriterion("qhcw_wg in", values, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgNotIn(final List<Integer> values) {
            this.addCriterion("qhcw_wg not in", values, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgBetween(final Integer value1, final Integer value2) {
            this.addCriterion("qhcw_wg between", value1, value2, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwWgNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("qhcw_wg not between", value1, value2, "qhcwWg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgIsNull() {
            this.addCriterion("qhcw_fg is null");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgIsNotNull() {
            this.addCriterion("qhcw_fg is not null");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgEqualTo(final Integer value) {
            this.addCriterion("qhcw_fg =", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgNotEqualTo(final Integer value) {
            this.addCriterion("qhcw_fg <>", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgGreaterThan(final Integer value) {
            this.addCriterion("qhcw_fg >", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("qhcw_fg >=", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgLessThan(final Integer value) {
            this.addCriterion("qhcw_fg <", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgLessThanColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgLessThanOrEqualTo(final Integer value) {
            this.addCriterion("qhcw_fg <=", value, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("qhcw_fg <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgIn(final List<Integer> values) {
            this.addCriterion("qhcw_fg in", values, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgNotIn(final List<Integer> values) {
            this.addCriterion("qhcw_fg not in", values, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgBetween(final Integer value1, final Integer value2) {
            this.addCriterion("qhcw_fg between", value1, value2, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andQhcwFgNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("qhcw_fg not between", value1, value2, "qhcwFg");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingIsNull() {
            this.addCriterion("cw_xiangxing is null");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingIsNotNull() {
            this.addCriterion("cw_xiangxing is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingEqualTo(final Integer value) {
            this.addCriterion("cw_xiangxing =", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingNotEqualTo(final Integer value) {
            this.addCriterion("cw_xiangxing <>", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingGreaterThan(final Integer value) {
            this.addCriterion("cw_xiangxing >", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_xiangxing >=", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingLessThan(final Integer value) {
            this.addCriterion("cw_xiangxing <", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingLessThanColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_xiangxing <=", value, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xiangxing <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingIn(final List<Integer> values) {
            this.addCriterion("cw_xiangxing in", values, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingNotIn(final List<Integer> values) {
            this.addCriterion("cw_xiangxing not in", values, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_xiangxing between", value1, value2, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwXiangxingNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_xiangxing not between", value1, value2, "cwXiangxing");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueIsNull() {
            this.addCriterion("cw_wuxue is null");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueIsNotNull() {
            this.addCriterion("cw_wuxue is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueEqualTo(final Integer value) {
            this.addCriterion("cw_wuxue =", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueNotEqualTo(final Integer value) {
            this.addCriterion("cw_wuxue <>", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueGreaterThan(final Integer value) {
            this.addCriterion("cw_wuxue >", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_wuxue >=", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueLessThan(final Integer value) {
            this.addCriterion("cw_wuxue <", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueLessThanColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_wuxue <=", value, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_wuxue <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueIn(final List<Integer> values) {
            this.addCriterion("cw_wuxue in", values, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueNotIn(final List<Integer> values) {
            this.addCriterion("cw_wuxue not in", values, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_wuxue between", value1, value2, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwWuxueNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_wuxue not between", value1, value2, "cwWuxue");
            return (Criteria)this;
        }
        
        public Criteria andCwIconIsNull() {
            this.addCriterion("cw_icon is null");
            return (Criteria)this;
        }
        
        public Criteria andCwIconIsNotNull() {
            this.addCriterion("cw_icon is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwIconEqualTo(final String value) {
            this.addCriterion("cw_icon =", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_icon = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconNotEqualTo(final String value) {
            this.addCriterion("cw_icon <>", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_icon <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconGreaterThan(final String value) {
            this.addCriterion("cw_icon >", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cw_icon > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconGreaterThanOrEqualTo(final String value) {
            this.addCriterion("cw_icon >=", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_icon >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconLessThan(final String value) {
            this.addCriterion("cw_icon <", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconLessThanColumn(final Pets.Column column) {
            this.addCriterion("cw_icon < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconLessThanOrEqualTo(final String value) {
            this.addCriterion("cw_icon <=", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_icon <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwIconLike(final String value) {
            this.addCriterion("cw_icon like", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconNotLike(final String value) {
            this.addCriterion("cw_icon not like", value, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconIn(final List<String> values) {
            this.addCriterion("cw_icon in", values, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconNotIn(final List<String> values) {
            this.addCriterion("cw_icon not in", values, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconBetween(final String value1, final String value2) {
            this.addCriterion("cw_icon between", value1, value2, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwIconNotBetween(final String value1, final String value2) {
            this.addCriterion("cw_icon not between", value1, value2, "cwIcon");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaIsNull() {
            this.addCriterion("cw_xinfa is null");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaIsNotNull() {
            this.addCriterion("cw_xinfa is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaEqualTo(final Integer value) {
            this.addCriterion("cw_xinfa =", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaNotEqualTo(final Integer value) {
            this.addCriterion("cw_xinfa <>", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaGreaterThan(final Integer value) {
            this.addCriterion("cw_xinfa >", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_xinfa >=", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaLessThan(final Integer value) {
            this.addCriterion("cw_xinfa <", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaLessThanColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_xinfa <=", value, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_xinfa <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaIn(final List<Integer> values) {
            this.addCriterion("cw_xinfa in", values, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaNotIn(final List<Integer> values) {
            this.addCriterion("cw_xinfa not in", values, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_xinfa between", value1, value2, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwXinfaNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_xinfa not between", value1, value2, "cwXinfa");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiIsNull() {
            this.addCriterion("cw_qinmi is null");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiIsNotNull() {
            this.addCriterion("cw_qinmi is not null");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiEqualTo(final Integer value) {
            this.addCriterion("cw_qinmi =", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiNotEqualTo(final Integer value) {
            this.addCriterion("cw_qinmi <>", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiGreaterThan(final Integer value) {
            this.addCriterion("cw_qinmi >", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_qinmi >=", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiLessThan(final Integer value) {
            this.addCriterion("cw_qinmi <", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiLessThanColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiLessThanOrEqualTo(final Integer value) {
            this.addCriterion("cw_qinmi <=", value, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiLessThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("cw_qinmi <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiIn(final List<Integer> values) {
            this.addCriterion("cw_qinmi in", values, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiNotIn(final List<Integer> values) {
            this.addCriterion("cw_qinmi not in", values, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_qinmi between", value1, value2, "cwQinmi");
            return (Criteria)this;
        }
        
        public Criteria andCwQinmiNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("cw_qinmi not between", value1, value2, "cwQinmi");
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
        
        public Criteria andAddTimeEqualToColumn(final Pets.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Pets.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Pets.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Pets.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Pets.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Pets.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Pets.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Pets.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Pets.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Pets.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Pets.Column column) {
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
        void example(final PetsExample paramPetsExample);
    }
}
