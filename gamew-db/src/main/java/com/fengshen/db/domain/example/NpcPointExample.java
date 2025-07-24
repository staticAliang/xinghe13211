package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class NpcPointExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public NpcPointExample() {
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
    
    public NpcPointExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public NpcPointExample orderBy(final String... orderByClauses) {
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
        final NpcPointExample example = new NpcPointExample();
        return example.createCriteria();
    }
    
    public NpcPointExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public NpcPointExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private NpcPointExample example;
        
        protected Criteria(final NpcPointExample example) {
            this.example = example;
        }
        
        public NpcPointExample example() {
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
            return deleted ? this.andDeletedEqualTo(NpcPoint.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(NpcPoint.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        
        public Criteria andMapnameIsNull() {
            this.addCriterion("mapname is null");
            return (Criteria)this;
        }
        
        public Criteria andMapnameIsNotNull() {
            this.addCriterion("mapname is not null");
            return (Criteria)this;
        }
        
        public Criteria andMapnameEqualTo(final String value) {
            this.addCriterion("mapname =", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameNotEqualTo(final String value) {
            this.addCriterion("mapname <>", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameGreaterThan(final String value) {
            this.addCriterion("mapname >", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("mapname >=", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameLessThan(final String value) {
            this.addCriterion("mapname <", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameLessThanOrEqualTo(final String value) {
            this.addCriterion("mapname <=", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameLessThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("mapname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMapnameLike(final String value) {
            this.addCriterion("mapname like", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameNotLike(final String value) {
            this.addCriterion("mapname not like", value, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameIn(final List<String> values) {
            this.addCriterion("mapname in", values, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameNotIn(final List<String> values) {
            this.addCriterion("mapname not in", values, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameBetween(final String value1, final String value2) {
            this.addCriterion("mapname between", value1, value2, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andMapnameNotBetween(final String value1, final String value2) {
            this.addCriterion("mapname not between", value1, value2, "mapname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameIsNull() {
            this.addCriterion("doorname is null");
            return (Criteria)this;
        }
        
        public Criteria andDoornameIsNotNull() {
            this.addCriterion("doorname is not null");
            return (Criteria)this;
        }
        
        public Criteria andDoornameEqualTo(final String value) {
            this.addCriterion("doorname =", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameNotEqualTo(final String value) {
            this.addCriterion("doorname <>", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameGreaterThan(final String value) {
            this.addCriterion("doorname >", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("doorname >=", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameLessThan(final String value) {
            this.addCriterion("doorname <", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameLessThanOrEqualTo(final String value) {
            this.addCriterion("doorname <=", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameLessThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("doorname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDoornameLike(final String value) {
            this.addCriterion("doorname like", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameNotLike(final String value) {
            this.addCriterion("doorname not like", value, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameIn(final List<String> values) {
            this.addCriterion("doorname in", values, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameNotIn(final List<String> values) {
            this.addCriterion("doorname not in", values, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameBetween(final String value1, final String value2) {
            this.addCriterion("doorname between", value1, value2, "doorname");
            return (Criteria)this;
        }
        
        public Criteria andDoornameNotBetween(final String value1, final String value2) {
            this.addCriterion("doorname not between", value1, value2, "doorname");
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
        
        public Criteria andXEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("x = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXNotEqualTo(final Integer value) {
            this.addCriterion("x <>", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("x <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThan(final Integer value) {
            this.addCriterion("x >", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("x > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("x >=", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("x >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXLessThan(final Integer value) {
            this.addCriterion("x <", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("x < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andXLessThanOrEqualTo(final Integer value) {
            this.addCriterion("x <=", value, "x");
            return (Criteria)this;
        }
        
        public Criteria andXLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        
        public Criteria andYEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("y = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYNotEqualTo(final Integer value) {
            this.addCriterion("y <>", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("y <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThan(final Integer value) {
            this.addCriterion("y >", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("y > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("y >=", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("y >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYLessThan(final Integer value) {
            this.addCriterion("y <", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("y < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andYLessThanOrEqualTo(final Integer value) {
            this.addCriterion("y <=", value, "y");
            return (Criteria)this;
        }
        
        public Criteria andYLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        
        public Criteria andZIsNull() {
            this.addCriterion("z is null");
            return (Criteria)this;
        }
        
        public Criteria andZIsNotNull() {
            this.addCriterion("z is not null");
            return (Criteria)this;
        }
        
        public Criteria andZEqualTo(final Integer value) {
            this.addCriterion("z =", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("z = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZNotEqualTo(final Integer value) {
            this.addCriterion("z <>", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("z <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZGreaterThan(final Integer value) {
            this.addCriterion("z >", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("z > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("z >=", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("z >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZLessThan(final Integer value) {
            this.addCriterion("z <", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("z < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZLessThanOrEqualTo(final Integer value) {
            this.addCriterion("z <=", value, "z");
            return (Criteria)this;
        }
        
        public Criteria andZLessThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("z <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZIn(final List<Integer> values) {
            this.addCriterion("z in", values, "z");
            return (Criteria)this;
        }
        
        public Criteria andZNotIn(final List<Integer> values) {
            this.addCriterion("z not in", values, "z");
            return (Criteria)this;
        }
        
        public Criteria andZBetween(final Integer value1, final Integer value2) {
            this.addCriterion("z between", value1, value2, "z");
            return (Criteria)this;
        }
        
        public Criteria andZNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("z not between", value1, value2, "z");
            return (Criteria)this;
        }
        
        public Criteria andInxIsNull() {
            this.addCriterion("inx is null");
            return (Criteria)this;
        }
        
        public Criteria andInxIsNotNull() {
            this.addCriterion("inx is not null");
            return (Criteria)this;
        }
        
        public Criteria andInxEqualTo(final Integer value) {
            this.addCriterion("inx =", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("inx = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxNotEqualTo(final Integer value) {
            this.addCriterion("inx <>", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("inx <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxGreaterThan(final Integer value) {
            this.addCriterion("inx >", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("inx > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("inx >=", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("inx >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxLessThan(final Integer value) {
            this.addCriterion("inx <", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("inx < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxLessThanOrEqualTo(final Integer value) {
            this.addCriterion("inx <=", value, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxLessThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("inx <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInxIn(final List<Integer> values) {
            this.addCriterion("inx in", values, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxNotIn(final List<Integer> values) {
            this.addCriterion("inx not in", values, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxBetween(final Integer value1, final Integer value2) {
            this.addCriterion("inx between", value1, value2, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInxNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("inx not between", value1, value2, "inx");
            return (Criteria)this;
        }
        
        public Criteria andInyIsNull() {
            this.addCriterion("iny is null");
            return (Criteria)this;
        }
        
        public Criteria andInyIsNotNull() {
            this.addCriterion("iny is not null");
            return (Criteria)this;
        }
        
        public Criteria andInyEqualTo(final Integer value) {
            this.addCriterion("iny =", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("iny = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyNotEqualTo(final Integer value) {
            this.addCriterion("iny <>", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("iny <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyGreaterThan(final Integer value) {
            this.addCriterion("iny >", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("iny > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("iny >=", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("iny >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyLessThan(final Integer value) {
            this.addCriterion("iny <", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("iny < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyLessThanOrEqualTo(final Integer value) {
            this.addCriterion("iny <=", value, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyLessThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("iny <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andInyIn(final List<Integer> values) {
            this.addCriterion("iny in", values, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyNotIn(final List<Integer> values) {
            this.addCriterion("iny not in", values, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyBetween(final Integer value1, final Integer value2) {
            this.addCriterion("iny between", value1, value2, "iny");
            return (Criteria)this;
        }
        
        public Criteria andInyNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("iny not between", value1, value2, "iny");
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
        
        public Criteria andAddTimeEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final NpcPoint.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final NpcPoint.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final NpcPoint.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final NpcPoint.Column column) {
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
        void example(final NpcPointExample paramNpcPointExample);
    }
}
