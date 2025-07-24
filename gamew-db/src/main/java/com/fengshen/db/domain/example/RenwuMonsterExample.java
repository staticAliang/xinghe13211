package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class RenwuMonsterExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public RenwuMonsterExample() {
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
    
    public RenwuMonsterExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public RenwuMonsterExample orderBy(final String... orderByClauses) {
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
        final RenwuMonsterExample example = new RenwuMonsterExample();
        return example.createCriteria();
    }
    
    public RenwuMonsterExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public RenwuMonsterExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private RenwuMonsterExample example;
        
        protected Criteria(final RenwuMonsterExample example) {
            this.example = example;
        }
        
        public RenwuMonsterExample example() {
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
            return deleted ? this.andDeletedEqualTo(RenwuMonster.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(RenwuMonster.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        
        public Criteria andMapNameIsNull() {
            this.addCriterion("map_name is null");
            return (Criteria)this;
        }
        
        public Criteria andMapNameIsNotNull() {
            this.addCriterion("map_name is not null");
            return (Criteria)this;
        }
        
        public Criteria andMapNameEqualTo(final String value) {
            this.addCriterion("map_name =", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameNotEqualTo(final String value) {
            this.addCriterion("map_name <>", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameGreaterThan(final String value) {
            this.addCriterion("map_name >", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("map_name >=", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameLessThan(final String value) {
            this.addCriterion("map_name <", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameLessThanOrEqualTo(final String value) {
            this.addCriterion("map_name <=", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameLessThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("map_name <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapNameLike(final String value) {
            this.addCriterion("map_name like", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameNotLike(final String value) {
            this.addCriterion("map_name not like", value, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameIn(final List<String> values) {
            this.addCriterion("map_name in", values, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameNotIn(final List<String> values) {
            this.addCriterion("map_name not in", values, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameBetween(final String value1, final String value2) {
            this.addCriterion("map_name between", value1, value2, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andMapNameNotBetween(final String value1, final String value2) {
            this.addCriterion("map_name not between", value1, value2, "mapName");
            return (Criteria)this;
        }
        
        public Criteria andXIsNull() {
            this.addCriterion("x is null");
            return (Criteria)this;
        }
        
        public Criteria andXIsNotNull() {
            this.addCriterion("x is not null");
            return (Criteria)this;
        }
        
        public Criteria andXEqualTo(final Integer value) {
            this.addCriterion("x =", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("x = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXNotEqualTo(final Integer value) {
            this.addCriterion("x <>", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("x <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThan(final Integer value) {
            this.addCriterion("x >", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("x > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("x >=", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("x >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXLessThan(final Integer value) {
            this.addCriterion("x <", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("x < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXLessThanOrEqualTo(final Integer value) {
            this.addCriterion("x <=", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXLessThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("x <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXIn(final List<Integer> values) {
            this.addCriterion("x in", values, "x");
            return (Criteria)this;
        }
        
        public Criteria andXNotIn(final List<Integer> values) {
            this.addCriterion("x not in", values, "x");
            return (Criteria)this;
        }
        
        public Criteria andXBetween(final Integer value1, final Integer value2) {
            this.addCriterion("x between", value1, value2, "x");
            return (Criteria)this;
        }
        
        public Criteria andXNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("x not between", value1, value2, "x");
            return (Criteria)this;
        }
        
        public Criteria andYIsNull() {
            this.addCriterion("y is null");
            return (Criteria)this;
        }
        
        public Criteria andYIsNotNull() {
            this.addCriterion("y is not null");
            return (Criteria)this;
        }
        
        public Criteria andYEqualTo(final Integer value) {
            this.addCriterion("y =", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("y = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYNotEqualTo(final Integer value) {
            this.addCriterion("y <>", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("y <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThan(final Integer value) {
            this.addCriterion("y >", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("y > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("y >=", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("y >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYLessThan(final Integer value) {
            this.addCriterion("y <", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("y < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYLessThanOrEqualTo(final Integer value) {
            this.addCriterion("y <=", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYLessThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("y <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYIn(final List<Integer> values) {
            this.addCriterion("y in", values, "y");
            return (Criteria)this;
        }
        
        public Criteria andYNotIn(final List<Integer> values) {
            this.addCriterion("y not in", values, "y");
            return (Criteria)this;
        }
        
        public Criteria andYBetween(final Integer value1, final Integer value2) {
            this.addCriterion("y between", value1, value2, "y");
            return (Criteria)this;
        }
        
        public Criteria andYNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("y not between", value1, value2, "y");
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
        
        public Criteria andNameEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        
        public Criteria andIconIsNull() {
            this.addCriterion("icon is null");
            return (Criteria)this;
        }
        
        public Criteria andIconIsNotNull() {
            this.addCriterion("icon is not null");
            return (Criteria)this;
        }
        
        public Criteria andIconEqualTo(final Integer value) {
            this.addCriterion("icon =", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconNotEqualTo(final Integer value) {
            this.addCriterion("icon <>", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThan(final Integer value) {
            this.addCriterion("icon >", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("icon >=", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconLessThan(final Integer value) {
            this.addCriterion("icon <", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanOrEqualTo(final Integer value) {
            this.addCriterion("icon <=", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("icon <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconIn(final List<Integer> values) {
            this.addCriterion("icon in", values, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconNotIn(final List<Integer> values) {
            this.addCriterion("icon not in", values, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconBetween(final Integer value1, final Integer value2) {
            this.addCriterion("icon between", value1, value2, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("icon not between", value1, value2, "icon");
            return (Criteria)this;
        }
        
        public Criteria andSkillsIsNull() {
            this.addCriterion("skills is null");
            return (Criteria)this;
        }
        
        public Criteria andSkillsIsNotNull() {
            this.addCriterion("skills is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkillsEqualTo(final String value) {
            this.addCriterion("skills =", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsNotEqualTo(final String value) {
            this.addCriterion("skills <>", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsGreaterThan(final String value) {
            this.addCriterion("skills >", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skills >=", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsLessThan(final String value) {
            this.addCriterion("skills <", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsLessThanOrEqualTo(final String value) {
            this.addCriterion("skills <=", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsLessThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("skills <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkillsLike(final String value) {
            this.addCriterion("skills like", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsNotLike(final String value) {
            this.addCriterion("skills not like", value, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsIn(final List<String> values) {
            this.addCriterion("skills in", values, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsNotIn(final List<String> values) {
            this.addCriterion("skills not in", values, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsBetween(final String value1, final String value2) {
            this.addCriterion("skills between", value1, value2, "skills");
            return (Criteria)this;
        }
        
        public Criteria andSkillsNotBetween(final String value1, final String value2) {
            this.addCriterion("skills not between", value1, value2, "skills");
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
        
        public Criteria andTypeEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        
        public Criteria andAddTimeEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final RenwuMonster.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final RenwuMonster.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final RenwuMonster.Column column) {
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
        void example(final RenwuMonsterExample paramRenwuMonsterExample);
    }
}
