package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class NpcDialogueFrameExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public NpcDialogueFrameExample() {
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
    
    public NpcDialogueFrameExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public NpcDialogueFrameExample orderBy(final String... orderByClauses) {
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
        final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
        return example.createCriteria();
    }
    
    public NpcDialogueFrameExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public NpcDialogueFrameExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private NpcDialogueFrameExample example;
        
        protected Criteria(final NpcDialogueFrameExample example) {
            this.example = example;
        }
        
        public NpcDialogueFrameExample example() {
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
            return deleted ? this.andDeletedEqualTo(NpcDialogueFrame.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(NpcDialogueFrame.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andPortraitIsNull() {
            this.addCriterion("portrait is null");
            return (Criteria)this;
        }
        
        public Criteria andPortraitIsNotNull() {
            this.addCriterion("portrait is not null");
            return (Criteria)this;
        }
        
        public Criteria andPortraitEqualTo(final Integer value) {
            this.addCriterion("portrait =", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitNotEqualTo(final Integer value) {
            this.addCriterion("portrait <>", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitGreaterThan(final Integer value) {
            this.addCriterion("portrait >", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("portrait >=", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitLessThan(final Integer value) {
            this.addCriterion("portrait <", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitLessThanOrEqualTo(final Integer value) {
            this.addCriterion("portrait <=", value, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("portrait <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPortraitIn(final List<Integer> values) {
            this.addCriterion("portrait in", values, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitNotIn(final List<Integer> values) {
            this.addCriterion("portrait not in", values, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitBetween(final Integer value1, final Integer value2) {
            this.addCriterion("portrait between", value1, value2, "portrait");
            return (Criteria)this;
        }
        
        public Criteria andPortraitNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("portrait not between", value1, value2, "portrait");
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
        
        public Criteria andPicNoEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("pic_no = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotEqualTo(final Integer value) {
            this.addCriterion("pic_no <>", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("pic_no <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThan(final Integer value) {
            this.addCriterion("pic_no >", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("pic_no > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("pic_no >=", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("pic_no >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThan(final Integer value) {
            this.addCriterion("pic_no <", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("pic_no < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("pic_no <=", value, "picNo");
            return (Criteria)this;
        }
        
        public Criteria andPicNoLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andContentEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("content = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentNotEqualTo(final String value) {
            this.addCriterion("content <>", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("content <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThan(final String value) {
            this.addCriterion("content >", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("content > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanOrEqualTo(final String value) {
            this.addCriterion("content >=", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("content >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentLessThan(final String value) {
            this.addCriterion("content <", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("content < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanOrEqualTo(final String value) {
            this.addCriterion("content <=", value, "content");
            return (Criteria)this;
        }
        
        public Criteria andContentLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andSecretKeyIsNull() {
            this.addCriterion("secret_key is null");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyIsNotNull() {
            this.addCriterion("secret_key is not null");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyEqualTo(final String value) {
            this.addCriterion("secret_key =", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyNotEqualTo(final String value) {
            this.addCriterion("secret_key <>", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyGreaterThan(final String value) {
            this.addCriterion("secret_key >", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyGreaterThanOrEqualTo(final String value) {
            this.addCriterion("secret_key >=", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyLessThan(final String value) {
            this.addCriterion("secret_key <", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyLessThanOrEqualTo(final String value) {
            this.addCriterion("secret_key <=", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("secret_key <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyLike(final String value) {
            this.addCriterion("secret_key like", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyNotLike(final String value) {
            this.addCriterion("secret_key not like", value, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyIn(final List<String> values) {
            this.addCriterion("secret_key in", values, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyNotIn(final List<String> values) {
            this.addCriterion("secret_key not in", values, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyBetween(final String value1, final String value2) {
            this.addCriterion("secret_key between", value1, value2, "secretKey");
            return (Criteria)this;
        }
        
        public Criteria andSecretKeyNotBetween(final String value1, final String value2) {
            this.addCriterion("secret_key not between", value1, value2, "secretKey");
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
        
        public Criteria andNameEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andAttribIsNull() {
            this.addCriterion("attrib is null");
            return (Criteria)this;
        }
        
        public Criteria andAttribIsNotNull() {
            this.addCriterion("attrib is not null");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualTo(final Integer value) {
            this.addCriterion("attrib =", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualTo(final Integer value) {
            this.addCriterion("attrib <>", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThan(final Integer value) {
            this.addCriterion("attrib >", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib >=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThan(final Integer value) {
            this.addCriterion("attrib <", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib <=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("attrib <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribIn(final List<Integer> values) {
            this.addCriterion("attrib in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotIn(final List<Integer> values) {
            this.addCriterion("attrib not in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib between", value1, value2, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib not between", value1, value2, "attrib");
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
        
        public Criteria andAddTimeEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andUpdateTimesIsNull() {
            this.addCriterion("update_times is null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesIsNotNull() {
            this.addCriterion("update_times is not null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesEqualTo(final LocalDateTime value) {
            this.addCriterion("update_times =", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_times <>", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_times >", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_times >=", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesLessThan(final LocalDateTime value) {
            this.addCriterion("update_times <", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_times <=", value, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_times <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesIn(final List<LocalDateTime> values) {
            this.addCriterion("update_times in", values, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesNotIn(final List<LocalDateTime> values) {
            this.addCriterion("update_times not in", values, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_times between", value1, value2, "updateTimes");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimesNotBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_times not between", value1, value2, "updateTimes");
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
        
        public Criteria andDeletedEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
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
        
        public Criteria andIdnameIsNull() {
            this.addCriterion("idname is null");
            return (Criteria)this;
        }
        
        public Criteria andIdnameIsNotNull() {
            this.addCriterion("idname is not null");
            return (Criteria)this;
        }
        
        public Criteria andIdnameEqualTo(final Integer value) {
            this.addCriterion("idname =", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotEqualTo(final Integer value) {
            this.addCriterion("idname <>", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThan(final Integer value) {
            this.addCriterion("idname >", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("idname >=", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThan(final Integer value) {
            this.addCriterion("idname <", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanOrEqualTo(final Integer value) {
            this.addCriterion("idname <=", value, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("idname <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdnameIn(final List<Integer> values) {
            this.addCriterion("idname in", values, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotIn(final List<Integer> values) {
            this.addCriterion("idname not in", values, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameBetween(final Integer value1, final Integer value2) {
            this.addCriterion("idname between", value1, value2, "idname");
            return (Criteria)this;
        }
        
        public Criteria andIdnameNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("idname not between", value1, value2, "idname");
            return (Criteria)this;
        }
        
        public Criteria andNextIsNull() {
            this.addCriterion("`next` is null");
            return (Criteria)this;
        }
        
        public Criteria andNextIsNotNull() {
            this.addCriterion("`next` is not null");
            return (Criteria)this;
        }
        
        public Criteria andNextEqualTo(final String value) {
            this.addCriterion("`next` =", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextNotEqualTo(final String value) {
            this.addCriterion("`next` <>", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextGreaterThan(final String value) {
            this.addCriterion("`next` >", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`next` >=", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextLessThan(final String value) {
            this.addCriterion("`next` <", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextLessThanOrEqualTo(final String value) {
            this.addCriterion("`next` <=", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("`next` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNextLike(final String value) {
            this.addCriterion("`next` like", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextNotLike(final String value) {
            this.addCriterion("`next` not like", value, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextIn(final List<String> values) {
            this.addCriterion("`next` in", values, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextNotIn(final List<String> values) {
            this.addCriterion("`next` not in", values, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextBetween(final String value1, final String value2) {
            this.addCriterion("`next` between", value1, value2, "next");
            return (Criteria)this;
        }
        
        public Criteria andNextNotBetween(final String value1, final String value2) {
            this.addCriterion("`next` not between", value1, value2, "next");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskIsNull() {
            this.addCriterion("current_task is null");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskIsNotNull() {
            this.addCriterion("current_task is not null");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskEqualTo(final String value) {
            this.addCriterion("current_task =", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskNotEqualTo(final String value) {
            this.addCriterion("current_task <>", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskGreaterThan(final String value) {
            this.addCriterion("current_task >", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskGreaterThanOrEqualTo(final String value) {
            this.addCriterion("current_task >=", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskLessThan(final String value) {
            this.addCriterion("current_task <", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskLessThanOrEqualTo(final String value) {
            this.addCriterion("current_task <=", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("current_task <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskLike(final String value) {
            this.addCriterion("current_task like", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskNotLike(final String value) {
            this.addCriterion("current_task not like", value, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskIn(final List<String> values) {
            this.addCriterion("current_task in", values, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskNotIn(final List<String> values) {
            this.addCriterion("current_task not in", values, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskBetween(final String value1, final String value2) {
            this.addCriterion("current_task between", value1, value2, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andCurrentTaskNotBetween(final String value1, final String value2) {
            this.addCriterion("current_task not between", value1, value2, "currentTask");
            return (Criteria)this;
        }
        
        public Criteria andUncontentIsNull() {
            this.addCriterion("uncontent is null");
            return (Criteria)this;
        }
        
        public Criteria andUncontentIsNotNull() {
            this.addCriterion("uncontent is not null");
            return (Criteria)this;
        }
        
        public Criteria andUncontentEqualTo(final String value) {
            this.addCriterion("uncontent =", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentNotEqualTo(final String value) {
            this.addCriterion("uncontent <>", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentGreaterThan(final String value) {
            this.addCriterion("uncontent >", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentGreaterThanOrEqualTo(final String value) {
            this.addCriterion("uncontent >=", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentLessThan(final String value) {
            this.addCriterion("uncontent <", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentLessThanOrEqualTo(final String value) {
            this.addCriterion("uncontent <=", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("uncontent <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUncontentLike(final String value) {
            this.addCriterion("uncontent like", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentNotLike(final String value) {
            this.addCriterion("uncontent not like", value, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentIn(final List<String> values) {
            this.addCriterion("uncontent in", values, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentNotIn(final List<String> values) {
            this.addCriterion("uncontent not in", values, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentBetween(final String value1, final String value2) {
            this.addCriterion("uncontent between", value1, value2, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andUncontentNotBetween(final String value1, final String value2) {
            this.addCriterion("uncontent not between", value1, value2, "uncontent");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiIsNull() {
            this.addCriterion("zhuangbei is null");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiIsNotNull() {
            this.addCriterion("zhuangbei is not null");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiEqualTo(final String value) {
            this.addCriterion("zhuangbei =", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiNotEqualTo(final String value) {
            this.addCriterion("zhuangbei <>", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiGreaterThan(final String value) {
            this.addCriterion("zhuangbei >", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiGreaterThanOrEqualTo(final String value) {
            this.addCriterion("zhuangbei >=", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiLessThan(final String value) {
            this.addCriterion("zhuangbei <", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiLessThanOrEqualTo(final String value) {
            this.addCriterion("zhuangbei <=", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("zhuangbei <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiLike(final String value) {
            this.addCriterion("zhuangbei like", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiNotLike(final String value) {
            this.addCriterion("zhuangbei not like", value, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiIn(final List<String> values) {
            this.addCriterion("zhuangbei in", values, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiNotIn(final List<String> values) {
            this.addCriterion("zhuangbei not in", values, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiBetween(final String value1, final String value2) {
            this.addCriterion("zhuangbei between", value1, value2, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andZhuangbeiNotBetween(final String value1, final String value2) {
            this.addCriterion("zhuangbei not between", value1, value2, "zhuangbei");
            return (Criteria)this;
        }
        
        public Criteria andJingyanIsNull() {
            this.addCriterion("jingyan is null");
            return (Criteria)this;
        }
        
        public Criteria andJingyanIsNotNull() {
            this.addCriterion("jingyan is not null");
            return (Criteria)this;
        }
        
        public Criteria andJingyanEqualTo(final Integer value) {
            this.addCriterion("jingyan =", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanNotEqualTo(final Integer value) {
            this.addCriterion("jingyan <>", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanGreaterThan(final Integer value) {
            this.addCriterion("jingyan >", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("jingyan >=", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanLessThan(final Integer value) {
            this.addCriterion("jingyan <", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanLessThanOrEqualTo(final Integer value) {
            this.addCriterion("jingyan <=", value, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("jingyan <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andJingyanIn(final List<Integer> values) {
            this.addCriterion("jingyan in", values, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanNotIn(final List<Integer> values) {
            this.addCriterion("jingyan not in", values, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanBetween(final Integer value1, final Integer value2) {
            this.addCriterion("jingyan between", value1, value2, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andJingyanNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("jingyan not between", value1, value2, "jingyan");
            return (Criteria)this;
        }
        
        public Criteria andMoneyIsNull() {
            this.addCriterion("money is null");
            return (Criteria)this;
        }
        
        public Criteria andMoneyIsNotNull() {
            this.addCriterion("money is not null");
            return (Criteria)this;
        }
        
        public Criteria andMoneyEqualTo(final Integer value) {
            this.addCriterion("money =", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualTo(final Integer value) {
            this.addCriterion("money <>", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThan(final Integer value) {
            this.addCriterion("money >", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("money >=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyGreaterThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThan(final Integer value) {
            this.addCriterion("money <", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualTo(final Integer value) {
            this.addCriterion("money <=", value, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyLessThanOrEqualToColumn(final NpcDialogueFrame.Column column) {
            this.addCriterion("money <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMoneyIn(final List<Integer> values) {
            this.addCriterion("money in", values, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotIn(final List<Integer> values) {
            this.addCriterion("money not in", values, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyBetween(final Integer value1, final Integer value2) {
            this.addCriterion("money between", value1, value2, "money");
            return (Criteria)this;
        }
        
        public Criteria andMoneyNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("money not between", value1, value2, "money");
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
        void example(final NpcDialogueFrameExample paramNpcDialogueFrameExample);
    }
}
