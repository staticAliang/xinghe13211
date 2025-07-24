package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class MedicineShopExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public MedicineShopExample() {
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
    
    public MedicineShopExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public MedicineShopExample orderBy(final String... orderByClauses) {
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
        final MedicineShopExample example = new MedicineShopExample();
        return example.createCriteria();
    }
    
    public MedicineShopExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public MedicineShopExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private MedicineShopExample example;
        
        protected Criteria(final MedicineShopExample example) {
            this.example = example;
        }
        
        public MedicineShopExample example() {
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
            return deleted ? this.andDeletedEqualTo(MedicineShop.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(MedicineShop.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andGoodsNoIsNull() {
            this.addCriterion("goods_no is null");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoIsNotNull() {
            this.addCriterion("goods_no is not null");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoEqualTo(final Integer value) {
            this.addCriterion("goods_no =", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoNotEqualTo(final Integer value) {
            this.addCriterion("goods_no <>", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoGreaterThan(final Integer value) {
            this.addCriterion("goods_no >", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("goods_no >=", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoLessThan(final Integer value) {
            this.addCriterion("goods_no <", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoLessThanOrEqualTo(final Integer value) {
            this.addCriterion("goods_no <=", value, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoLessThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("goods_no <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoIn(final List<Integer> values) {
            this.addCriterion("goods_no in", values, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoNotIn(final List<Integer> values) {
            this.addCriterion("goods_no not in", values, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoBetween(final Integer value1, final Integer value2) {
            this.addCriterion("goods_no between", value1, value2, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andGoodsNoNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("goods_no not between", value1, value2, "goodsNo");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIsNull() {
            this.addCriterion("pay_type is null");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIsNotNull() {
            this.addCriterion("pay_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeEqualTo(final Integer value) {
            this.addCriterion("pay_type =", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotEqualTo(final Integer value) {
            this.addCriterion("pay_type <>", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThan(final Integer value) {
            this.addCriterion("pay_type >", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("pay_type >=", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThan(final Integer value) {
            this.addCriterion("pay_type <", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("pay_type <=", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("pay_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIn(final List<Integer> values) {
            this.addCriterion("pay_type in", values, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotIn(final List<Integer> values) {
            this.addCriterion("pay_type not in", values, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("pay_type not between", value1, value2, "payType");
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
        
        public Criteria andNameEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andValueIsNull() {
            this.addCriterion("`value` is null");
            return (Criteria)this;
        }
        
        public Criteria andValueIsNotNull() {
            this.addCriterion("`value` is not null");
            return (Criteria)this;
        }
        
        public Criteria andValueEqualTo(final Integer value) {
            this.addCriterion("`value` =", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueNotEqualTo(final Integer value) {
            this.addCriterion("`value` <>", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThan(final Integer value) {
            this.addCriterion("`value` >", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`value` >=", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueLessThan(final Integer value) {
            this.addCriterion("`value` <", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`value` <=", value, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueLessThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`value` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andValueIn(final List<Integer> values) {
            this.addCriterion("`value` in", values, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueNotIn(final List<Integer> values) {
            this.addCriterion("`value` not in", values, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`value` between", value1, value2, "value");
            return (Criteria)this;
        }
        
        public Criteria andValueNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`value` not between", value1, value2, "value");
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
        
        public Criteria andLevelEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`level` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualTo(final Integer value) {
            this.addCriterion("`level` <>", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`level` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThan(final Integer value) {
            this.addCriterion("`level` >", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`level` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` >=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`level` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThan(final Integer value) {
            this.addCriterion("`level` <", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`level` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`level` <=", value, "level");
            return (Criteria)this;
        }
        
        public Criteria andLevelLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andTypeEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andItemcountIsNull() {
            this.addCriterion("itemCount is null");
            return (Criteria)this;
        }
        
        public Criteria andItemcountIsNotNull() {
            this.addCriterion("itemCount is not null");
            return (Criteria)this;
        }
        
        public Criteria andItemcountEqualTo(final Integer value) {
            this.addCriterion("itemCount =", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountNotEqualTo(final Integer value) {
            this.addCriterion("itemCount <>", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountGreaterThan(final Integer value) {
            this.addCriterion("itemCount >", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("itemCount >=", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountLessThan(final Integer value) {
            this.addCriterion("itemCount <", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountLessThanOrEqualTo(final Integer value) {
            this.addCriterion("itemCount <=", value, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountLessThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("itemCount <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andItemcountIn(final List<Integer> values) {
            this.addCriterion("itemCount in", values, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountNotIn(final List<Integer> values) {
            this.addCriterion("itemCount not in", values, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountBetween(final Integer value1, final Integer value2) {
            this.addCriterion("itemCount between", value1, value2, "itemcount");
            return (Criteria)this;
        }
        
        public Criteria andItemcountNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("itemCount not between", value1, value2, "itemcount");
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
        
        public Criteria andAddTimeEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final MedicineShop.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final MedicineShop.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final MedicineShop.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final MedicineShop.Column column) {
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
        void example(final MedicineShopExample paramMedicineShopExample);
    }
}
