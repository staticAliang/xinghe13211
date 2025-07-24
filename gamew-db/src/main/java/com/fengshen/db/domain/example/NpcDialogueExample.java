package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class NpcDialogueExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public NpcDialogueExample() {
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
    
    public NpcDialogueExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public NpcDialogueExample orderBy(final String... orderByClauses) {
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
        final NpcDialogueExample example = new NpcDialogueExample();
        return example.createCriteria();
    }
    
    public NpcDialogueExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public NpcDialogueExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private NpcDialogueExample example;
        
        protected Criteria(final NpcDialogueExample example) {
            this.example = example;
        }
        
        public NpcDialogueExample example() {
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
            return deleted ? this.andDeletedEqualTo(NpcDialogue.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(NpcDialogue.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final NpcDialogue.Column column) {
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
        
        public Criteria andNameEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final NpcDialogue.Column column) {
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
        
        public Criteria andPortranitIsNull() {
            this.addCriterion("portranit is null");
            return (Criteria)this;
        }
        
        public Criteria andPortranitIsNotNull() {
            this.addCriterion("portranit is not null");
            return (Criteria)this;
        }
        
        public Criteria andPortranitEqualTo(final Integer value) {
            this.addCriterion("portranit =", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitNotEqualTo(final Integer value) {
            this.addCriterion("portranit <>", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitGreaterThan(final Integer value) {
            this.addCriterion("portranit >", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("portranit >=", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitLessThan(final Integer value) {
            this.addCriterion("portranit <", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitLessThanOrEqualTo(final Integer value) {
            this.addCriterion("portranit <=", value, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("portranit <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortranitIn(final List<Integer> values) {
            this.addCriterion("portranit in", values, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitNotIn(final List<Integer> values) {
            this.addCriterion("portranit not in", values, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitBetween(final Integer value1, final Integer value2) {
            this.addCriterion("portranit between", value1, value2, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPortranitNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("portranit not between", value1, value2, "portranit");
            return (Criteria)this;
        }
        
        public Criteria andPicNoIsNull() {
            this.addCriterion("pic_no is null");
            return (Criteria)this;
        }
        
        public Criteria andPicNoIsNotNull() {
            this.addCriterion("pic_no is not null");
            return (Criteria)this;
        }
        
        public Criteria andPicNoEqualTo(final Integer value) {
            this.addCriterion("pic_no =", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotEqualTo(final Integer value) {
            this.addCriterion("pic_no <>", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThan(final Integer value) {
            this.addCriterion("pic_no >", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("pic_no >=", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThan(final Integer value) {
            this.addCriterion("pic_no <", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("pic_no <=", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("pic_no <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoIn(final List<Integer> values) {
            this.addCriterion("pic_no in", values, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotIn(final List<Integer> values) {
            this.addCriterion("pic_no not in", values, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pic_no between", value1, value2, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pic_no not between", value1, value2, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andContentIsNull() {
            this.addCriterion("content is null");
            return (Criteria)this;
        }
        
        public Criteria andContentIsNotNull() {
            this.addCriterion("content is not null");
            return (Criteria)this;
        }
        
        public Criteria andContentEqualTo(final String value) {
            this.addCriterion("content =", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("content = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentNotEqualTo(final String value) {
            this.addCriterion("content <>", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("content <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThan(final String value) {
            this.addCriterion("content >", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("content > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanOrEqualTo(final String value) {
            this.addCriterion("content >=", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("content >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentLessThan(final String value) {
            this.addCriterion("content <", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("content < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanOrEqualTo(final String value) {
            this.addCriterion("content <=", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("content <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentLike(final String value) {
            this.addCriterion("content like", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentNotLike(final String value) {
            this.addCriterion("content not like", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentIn(final List<String> values) {
            this.addCriterion("content in", values, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentNotIn(final List<String> values) {
            this.addCriterion("content not in", values, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentBetween(final String value1, final String value2) {
            this.addCriterion("content between", value1, value2, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentNotBetween(final String value1, final String value2) {
            this.addCriterion("content not between", value1, value2, "content");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteIsNull() {
            this.addCriterion("isconmlete is null");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteIsNotNull() {
            this.addCriterion("isconmlete is not null");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteEqualTo(final Integer value) {
            this.addCriterion("isconmlete =", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteNotEqualTo(final Integer value) {
            this.addCriterion("isconmlete <>", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteGreaterThan(final Integer value) {
            this.addCriterion("isconmlete >", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("isconmlete >=", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteLessThan(final Integer value) {
            this.addCriterion("isconmlete <", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteLessThanOrEqualTo(final Integer value) {
            this.addCriterion("isconmlete <=", value, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isconmlete <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteIn(final List<Integer> values) {
            this.addCriterion("isconmlete in", values, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteNotIn(final List<Integer> values) {
            this.addCriterion("isconmlete not in", values, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteBetween(final Integer value1, final Integer value2) {
            this.addCriterion("isconmlete between", value1, value2, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsconmleteNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("isconmlete not between", value1, value2, "isconmlete");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatIsNull() {
            this.addCriterion("isincombat is null");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatIsNotNull() {
            this.addCriterion("isincombat is not null");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatEqualTo(final Integer value) {
            this.addCriterion("isincombat =", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatNotEqualTo(final Integer value) {
            this.addCriterion("isincombat <>", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatGreaterThan(final Integer value) {
            this.addCriterion("isincombat >", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("isincombat >=", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatLessThan(final Integer value) {
            this.addCriterion("isincombat <", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatLessThanOrEqualTo(final Integer value) {
            this.addCriterion("isincombat <=", value, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("isincombat <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsincombatIn(final List<Integer> values) {
            this.addCriterion("isincombat in", values, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatNotIn(final List<Integer> values) {
            this.addCriterion("isincombat not in", values, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatBetween(final Integer value1, final Integer value2) {
            this.addCriterion("isincombat between", value1, value2, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andIsincombatNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("isincombat not between", value1, value2, "isincombat");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeIsNull() {
            this.addCriterion("palytime is null");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeIsNotNull() {
            this.addCriterion("palytime is not null");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeEqualTo(final Integer value) {
            this.addCriterion("palytime =", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeNotEqualTo(final Integer value) {
            this.addCriterion("palytime <>", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeGreaterThan(final Integer value) {
            this.addCriterion("palytime >", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("palytime >=", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeLessThan(final Integer value) {
            this.addCriterion("palytime <", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("palytime <=", value, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("palytime <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPalytimeIn(final List<Integer> values) {
            this.addCriterion("palytime in", values, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeNotIn(final List<Integer> values) {
            this.addCriterion("palytime not in", values, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("palytime between", value1, value2, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andPalytimeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("palytime not between", value1, value2, "palytime");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIsNull() {
            this.addCriterion("task_type is null");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIsNotNull() {
            this.addCriterion("task_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeEqualTo(final String value) {
            this.addCriterion("task_type =", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotEqualTo(final String value) {
            this.addCriterion("task_type <>", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThan(final String value) {
            this.addCriterion("task_type >", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("task_type >=", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThan(final String value) {
            this.addCriterion("task_type <", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanOrEqualTo(final String value) {
            this.addCriterion("task_type <=", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("task_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLike(final String value) {
            this.addCriterion("task_type like", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotLike(final String value) {
            this.addCriterion("task_type not like", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIn(final List<String> values) {
            this.addCriterion("task_type in", values, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotIn(final List<String> values) {
            this.addCriterion("task_type not in", values, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeBetween(final String value1, final String value2) {
            this.addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("task_type not between", value1, value2, "taskType");
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
        
        public Criteria andAddTimeEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final NpcDialogue.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final NpcDialogue.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final NpcDialogue.Column column) {
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
        
        public Criteria andIdnameIsNull() {
            this.addCriterion("idname is null");
            return (Criteria)this;
        }
        
        public Criteria andIdnameIsNotNull() {
            this.addCriterion("idname is not null");
            return (Criteria)this;
        }
        
        public Criteria andIdnameEqualTo(final String value) {
            this.addCriterion("idname =", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotEqualTo(final String value) {
            this.addCriterion("idname <>", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThan(final String value) {
            this.addCriterion("idname >", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("idname >=", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThan(final String value) {
            this.addCriterion("idname <", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanOrEqualTo(final String value) {
            this.addCriterion("idname <=", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanOrEqualToColumn(final NpcDialogue.Column column) {
            this.addCriterion("idname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameLike(final String value) {
            this.addCriterion("idname like", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotLike(final String value) {
            this.addCriterion("idname not like", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameIn(final List<String> values) {
            this.addCriterion("idname in", values, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotIn(final List<String> values) {
            this.addCriterion("idname not in", values, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameBetween(final String value1, final String value2) {
            this.addCriterion("idname between", value1, value2, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotBetween(final String value1, final String value2) {
            this.addCriterion("idname not between", value1, value2, "idname");
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
        void example(final NpcDialogueExample paramNpcDialogueExample);
    }
}
