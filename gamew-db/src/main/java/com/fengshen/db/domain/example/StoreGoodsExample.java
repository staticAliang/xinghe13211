package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class StoreGoodsExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public StoreGoodsExample() {
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
    
    public StoreGoodsExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public StoreGoodsExample orderBy(final String... orderByClauses) {
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
        final StoreGoodsExample example = new StoreGoodsExample();
        return example.createCriteria();
    }
    
    public StoreGoodsExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public StoreGoodsExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private StoreGoodsExample example;
        
        protected Criteria(final StoreGoodsExample example) {
            this.example = example;
        }
        
        public StoreGoodsExample example() {
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
            return deleted ? this.andDeletedEqualTo(StoreGoods.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(StoreGoods.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        
        public Criteria andNameEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        
        public Criteria andBarcodeIsNull() {
            this.addCriterion("barcode is null");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeIsNotNull() {
            this.addCriterion("barcode is not null");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeEqualTo(final String value) {
            this.addCriterion("barcode =", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeNotEqualTo(final String value) {
            this.addCriterion("barcode <>", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeGreaterThan(final String value) {
            this.addCriterion("barcode >", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("barcode >=", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeLessThan(final String value) {
            this.addCriterion("barcode <", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeLessThanOrEqualTo(final String value) {
            this.addCriterion("barcode <=", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("barcode <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andBarcodeLike(final String value) {
            this.addCriterion("barcode like", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeNotLike(final String value) {
            this.addCriterion("barcode not like", value, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeIn(final List<String> values) {
            this.addCriterion("barcode in", values, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeNotIn(final List<String> values) {
            this.addCriterion("barcode not in", values, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeBetween(final String value1, final String value2) {
            this.addCriterion("barcode between", value1, value2, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andBarcodeNotBetween(final String value1, final String value2) {
            this.addCriterion("barcode not between", value1, value2, "barcode");
            return (Criteria)this;
        }
        
        public Criteria andForSaleIsNull() {
            this.addCriterion("for_sale is null");
            return (Criteria)this;
        }
        
        public Criteria andForSaleIsNotNull() {
            this.addCriterion("for_sale is not null");
            return (Criteria)this;
        }
        
        public Criteria andForSaleEqualTo(final Integer value) {
            this.addCriterion("for_sale =", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleNotEqualTo(final Integer value) {
            this.addCriterion("for_sale <>", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleGreaterThan(final Integer value) {
            this.addCriterion("for_sale >", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("for_sale >=", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleLessThan(final Integer value) {
            this.addCriterion("for_sale <", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleLessThanOrEqualTo(final Integer value) {
            this.addCriterion("for_sale <=", value, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("for_sale <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andForSaleIn(final List<Integer> values) {
            this.addCriterion("for_sale in", values, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleNotIn(final List<Integer> values) {
            this.addCriterion("for_sale not in", values, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleBetween(final Integer value1, final Integer value2) {
            this.addCriterion("for_sale between", value1, value2, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andForSaleNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("for_sale not between", value1, value2, "forSale");
            return (Criteria)this;
        }
        
        public Criteria andShowPosIsNull() {
            this.addCriterion("show_pos is null");
            return (Criteria)this;
        }
        
        public Criteria andShowPosIsNotNull() {
            this.addCriterion("show_pos is not null");
            return (Criteria)this;
        }
        
        public Criteria andShowPosEqualTo(final Integer value) {
            this.addCriterion("show_pos =", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosNotEqualTo(final Integer value) {
            this.addCriterion("show_pos <>", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosGreaterThan(final Integer value) {
            this.addCriterion("show_pos >", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("show_pos >=", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosLessThan(final Integer value) {
            this.addCriterion("show_pos <", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosLessThanOrEqualTo(final Integer value) {
            this.addCriterion("show_pos <=", value, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("show_pos <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowPosIn(final List<Integer> values) {
            this.addCriterion("show_pos in", values, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosNotIn(final List<Integer> values) {
            this.addCriterion("show_pos not in", values, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosBetween(final Integer value1, final Integer value2) {
            this.addCriterion("show_pos between", value1, value2, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andShowPosNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("show_pos not between", value1, value2, "showPos");
            return (Criteria)this;
        }
        
        public Criteria andRposIsNull() {
            this.addCriterion("rpos is null");
            return (Criteria)this;
        }
        
        public Criteria andRposIsNotNull() {
            this.addCriterion("rpos is not null");
            return (Criteria)this;
        }
        
        public Criteria andRposEqualTo(final Integer value) {
            this.addCriterion("rpos =", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposNotEqualTo(final Integer value) {
            this.addCriterion("rpos <>", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposGreaterThan(final Integer value) {
            this.addCriterion("rpos >", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("rpos >=", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposLessThan(final Integer value) {
            this.addCriterion("rpos <", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposLessThanOrEqualTo(final Integer value) {
            this.addCriterion("rpos <=", value, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("rpos <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRposIn(final List<Integer> values) {
            this.addCriterion("rpos in", values, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposNotIn(final List<Integer> values) {
            this.addCriterion("rpos not in", values, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rpos between", value1, value2, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andRposNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("rpos not between", value1, value2, "rpos");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaIsNull() {
            this.addCriterion("sale_quota is null");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaIsNotNull() {
            this.addCriterion("sale_quota is not null");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaEqualTo(final Integer value) {
            this.addCriterion("sale_quota =", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaNotEqualTo(final Integer value) {
            this.addCriterion("sale_quota <>", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaGreaterThan(final Integer value) {
            this.addCriterion("sale_quota >", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("sale_quota >=", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaLessThan(final Integer value) {
            this.addCriterion("sale_quota <", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaLessThanOrEqualTo(final Integer value) {
            this.addCriterion("sale_quota <=", value, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("sale_quota <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaIn(final List<Integer> values) {
            this.addCriterion("sale_quota in", values, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaNotIn(final List<Integer> values) {
            this.addCriterion("sale_quota not in", values, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sale_quota between", value1, value2, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andSaleQuotaNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sale_quota not between", value1, value2, "saleQuota");
            return (Criteria)this;
        }
        
        public Criteria andRecommendIsNull() {
            this.addCriterion("recommend is null");
            return (Criteria)this;
        }
        
        public Criteria andRecommendIsNotNull() {
            this.addCriterion("recommend is not null");
            return (Criteria)this;
        }
        
        public Criteria andRecommendEqualTo(final Integer value) {
            this.addCriterion("recommend =", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendNotEqualTo(final Integer value) {
            this.addCriterion("recommend <>", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendGreaterThan(final Integer value) {
            this.addCriterion("recommend >", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("recommend >=", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendLessThan(final Integer value) {
            this.addCriterion("recommend <", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendLessThanOrEqualTo(final Integer value) {
            this.addCriterion("recommend <=", value, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("recommend <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRecommendIn(final List<Integer> values) {
            this.addCriterion("recommend in", values, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendNotIn(final List<Integer> values) {
            this.addCriterion("recommend not in", values, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendBetween(final Integer value1, final Integer value2) {
            this.addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andRecommendNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria)this;
        }
        
        public Criteria andCoinIsNull() {
            this.addCriterion("coin is null");
            return (Criteria)this;
        }
        
        public Criteria andCoinIsNotNull() {
            this.addCriterion("coin is not null");
            return (Criteria)this;
        }
        
        public Criteria andCoinEqualTo(final Integer value) {
            this.addCriterion("coin =", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("coin = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinNotEqualTo(final Integer value) {
            this.addCriterion("coin <>", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("coin <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThan(final Integer value) {
            this.addCriterion("coin >", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("coin > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("coin >=", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("coin >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThan(final Integer value) {
            this.addCriterion("coin <", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("coin < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanOrEqualTo(final Integer value) {
            this.addCriterion("coin <=", value, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("coin <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andCoinIn(final List<Integer> values) {
            this.addCriterion("coin in", values, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotIn(final List<Integer> values) {
            this.addCriterion("coin not in", values, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinBetween(final Integer value1, final Integer value2) {
            this.addCriterion("coin between", value1, value2, "coin");
            return (Criteria)this;
        }
        
        public Criteria andCoinNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("coin not between", value1, value2, "coin");
            return (Criteria)this;
        }
        
        public Criteria andDiscountIsNull() {
            this.addCriterion("discount is null");
            return (Criteria)this;
        }
        
        public Criteria andDiscountIsNotNull() {
            this.addCriterion("discount is not null");
            return (Criteria)this;
        }
        
        public Criteria andDiscountEqualTo(final Integer value) {
            this.addCriterion("discount =", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("discount = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountNotEqualTo(final Integer value) {
            this.addCriterion("discount <>", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("discount <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountGreaterThan(final Integer value) {
            this.addCriterion("discount >", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("discount > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("discount >=", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("discount >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountLessThan(final Integer value) {
            this.addCriterion("discount <", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("discount < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountLessThanOrEqualTo(final Integer value) {
            this.addCriterion("discount <=", value, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("discount <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDiscountIn(final List<Integer> values) {
            this.addCriterion("discount in", values, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountNotIn(final List<Integer> values) {
            this.addCriterion("discount not in", values, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountBetween(final Integer value1, final Integer value2) {
            this.addCriterion("discount between", value1, value2, "discount");
            return (Criteria)this;
        }
        
        public Criteria andDiscountNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("discount not between", value1, value2, "discount");
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
        
        public Criteria andTypeEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`type` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualTo(final Integer value) {
            this.addCriterion("`type` <>", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`type` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThan(final Integer value) {
            this.addCriterion("`type` >", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("`type` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` >=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("`type` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThan(final Integer value) {
            this.addCriterion("`type` <", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("`type` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`type` <=", value, "type");
            return (Criteria)this;
        }
        
        public Criteria andTypeLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        
        public Criteria andQuotaLimitIsNull() {
            this.addCriterion("quota_limit is null");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitIsNotNull() {
            this.addCriterion("quota_limit is not null");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitEqualTo(final Integer value) {
            this.addCriterion("quota_limit =", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitNotEqualTo(final Integer value) {
            this.addCriterion("quota_limit <>", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitGreaterThan(final Integer value) {
            this.addCriterion("quota_limit >", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("quota_limit >=", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitLessThan(final Integer value) {
            this.addCriterion("quota_limit <", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitLessThanOrEqualTo(final Integer value) {
            this.addCriterion("quota_limit <=", value, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("quota_limit <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitIn(final List<Integer> values) {
            this.addCriterion("quota_limit in", values, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitNotIn(final List<Integer> values) {
            this.addCriterion("quota_limit not in", values, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitBetween(final Integer value1, final Integer value2) {
            this.addCriterion("quota_limit between", value1, value2, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andQuotaLimitNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("quota_limit not between", value1, value2, "quotaLimit");
            return (Criteria)this;
        }
        
        public Criteria andMustVipIsNull() {
            this.addCriterion("must_vip is null");
            return (Criteria)this;
        }
        
        public Criteria andMustVipIsNotNull() {
            this.addCriterion("must_vip is not null");
            return (Criteria)this;
        }
        
        public Criteria andMustVipEqualTo(final Integer value) {
            this.addCriterion("must_vip =", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipNotEqualTo(final Integer value) {
            this.addCriterion("must_vip <>", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipGreaterThan(final Integer value) {
            this.addCriterion("must_vip >", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("must_vip >=", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipLessThan(final Integer value) {
            this.addCriterion("must_vip <", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipLessThanOrEqualTo(final Integer value) {
            this.addCriterion("must_vip <=", value, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("must_vip <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMustVipIn(final List<Integer> values) {
            this.addCriterion("must_vip in", values, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipNotIn(final List<Integer> values) {
            this.addCriterion("must_vip not in", values, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipBetween(final Integer value1, final Integer value2) {
            this.addCriterion("must_vip between", value1, value2, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andMustVipNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("must_vip not between", value1, value2, "mustVip");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftIsNull() {
            this.addCriterion("is_gift is null");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftIsNotNull() {
            this.addCriterion("is_gift is not null");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftEqualTo(final Integer value) {
            this.addCriterion("is_gift =", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftNotEqualTo(final Integer value) {
            this.addCriterion("is_gift <>", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftGreaterThan(final Integer value) {
            this.addCriterion("is_gift >", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("is_gift >=", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftLessThan(final Integer value) {
            this.addCriterion("is_gift <", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftLessThanOrEqualTo(final Integer value) {
            this.addCriterion("is_gift <=", value, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("is_gift <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIsGiftIn(final List<Integer> values) {
            this.addCriterion("is_gift in", values, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftNotIn(final List<Integer> values) {
            this.addCriterion("is_gift not in", values, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_gift between", value1, value2, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andIsGiftNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_gift not between", value1, value2, "isGift");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeIsNull() {
            this.addCriterion("follow_pet_type is null");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeIsNotNull() {
            this.addCriterion("follow_pet_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeEqualTo(final Integer value) {
            this.addCriterion("follow_pet_type =", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeNotEqualTo(final Integer value) {
            this.addCriterion("follow_pet_type <>", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeGreaterThan(final Integer value) {
            this.addCriterion("follow_pet_type >", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("follow_pet_type >=", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeLessThan(final Integer value) {
            this.addCriterion("follow_pet_type <", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("follow_pet_type <=", value, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeLessThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("follow_pet_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeIn(final List<Integer> values) {
            this.addCriterion("follow_pet_type in", values, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeNotIn(final List<Integer> values) {
            this.addCriterion("follow_pet_type not in", values, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("follow_pet_type between", value1, value2, "followPetType");
            return (Criteria)this;
        }
        
        public Criteria andFollowPetTypeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("follow_pet_type not between", value1, value2, "followPetType");
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
        
        public Criteria andAddTimeEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final StoreGoods.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final StoreGoods.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final StoreGoods.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final StoreGoods.Column column) {
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
        void example(final StoreGoodsExample paramStoreGoodsExample);
    }
}
