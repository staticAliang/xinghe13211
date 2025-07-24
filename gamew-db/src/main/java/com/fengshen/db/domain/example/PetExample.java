package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class PetExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public PetExample() {
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
    
    public PetExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public PetExample orderBy(final String... orderByClauses) {
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
        final PetExample example = new PetExample();
        return example.createCriteria();
    }
    
    public PetExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public PetExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
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
        private PetExample example;
        
        protected Criteria(final PetExample example) {
            this.example = example;
        }
        
        public PetExample example() {
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
            return deleted ? this.andDeletedEqualTo(Pet.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(Pet.Deleted.IS_DELETED.value());
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
        
        public Criteria andIdEqualToColumn(final Pet.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final Pet.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final Pet.Column column) {
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
        
        public Criteria andIndexIsNull() {
            this.addCriterion("`index` is null");
            return (Criteria)this;
        }
        
        public Criteria andIndexIsNotNull() {
            this.addCriterion("`index` is not null");
            return (Criteria)this;
        }
        
        public Criteria andIndexEqualTo(final Integer value) {
            this.addCriterion("`index` =", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexEqualToColumn(final Pet.Column column) {
            this.addCriterion("`index` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexNotEqualTo(final Integer value) {
            this.addCriterion("`index` <>", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("`index` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexGreaterThan(final Integer value) {
            this.addCriterion("`index` >", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("`index` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("`index` >=", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("`index` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexLessThan(final Integer value) {
            this.addCriterion("`index` <", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexLessThanColumn(final Pet.Column column) {
            this.addCriterion("`index` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexLessThanOrEqualTo(final Integer value) {
            this.addCriterion("`index` <=", value, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("`index` <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIndexIn(final List<Integer> values) {
            this.addCriterion("`index` in", values, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexNotIn(final List<Integer> values) {
            this.addCriterion("`index` not in", values, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`index` between", value1, value2, "index");
            return (Criteria)this;
        }
        
        public Criteria andIndexNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("`index` not between", value1, value2, "index");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqIsNull() {
            this.addCriterion("level_req is null");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqIsNotNull() {
            this.addCriterion("level_req is not null");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqEqualTo(final Integer value) {
            this.addCriterion("level_req =", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqEqualToColumn(final Pet.Column column) {
            this.addCriterion("level_req = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqNotEqualTo(final Integer value) {
            this.addCriterion("level_req <>", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("level_req <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqGreaterThan(final Integer value) {
            this.addCriterion("level_req >", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("level_req > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("level_req >=", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("level_req >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqLessThan(final Integer value) {
            this.addCriterion("level_req <", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqLessThanColumn(final Pet.Column column) {
            this.addCriterion("level_req < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqLessThanOrEqualTo(final Integer value) {
            this.addCriterion("level_req <=", value, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("level_req <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLevelReqIn(final List<Integer> values) {
            this.addCriterion("level_req in", values, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqNotIn(final List<Integer> values) {
            this.addCriterion("level_req not in", values, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqBetween(final Integer value1, final Integer value2) {
            this.addCriterion("level_req between", value1, value2, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLevelReqNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("level_req not between", value1, value2, "levelReq");
            return (Criteria)this;
        }
        
        public Criteria andLifeIsNull() {
            this.addCriterion("life is null");
            return (Criteria)this;
        }
        
        public Criteria andLifeIsNotNull() {
            this.addCriterion("life is not null");
            return (Criteria)this;
        }
        
        public Criteria andLifeEqualTo(final Integer value) {
            this.addCriterion("life =", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeEqualToColumn(final Pet.Column column) {
            this.addCriterion("life = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeNotEqualTo(final Integer value) {
            this.addCriterion("life <>", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("life <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeGreaterThan(final Integer value) {
            this.addCriterion("life >", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("life > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("life >=", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("life >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeLessThan(final Integer value) {
            this.addCriterion("life <", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeLessThanColumn(final Pet.Column column) {
            this.addCriterion("life < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("life <=", value, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("life <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andLifeIn(final List<Integer> values) {
            this.addCriterion("life in", values, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeNotIn(final List<Integer> values) {
            this.addCriterion("life not in", values, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("life between", value1, value2, "life");
            return (Criteria)this;
        }
        
        public Criteria andLifeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("life not between", value1, value2, "life");
            return (Criteria)this;
        }
        
        public Criteria andManaIsNull() {
            this.addCriterion("mana is null");
            return (Criteria)this;
        }
        
        public Criteria andManaIsNotNull() {
            this.addCriterion("mana is not null");
            return (Criteria)this;
        }
        
        public Criteria andManaEqualTo(final Integer value) {
            this.addCriterion("mana =", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaEqualToColumn(final Pet.Column column) {
            this.addCriterion("mana = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaNotEqualTo(final Integer value) {
            this.addCriterion("mana <>", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("mana <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThan(final Integer value) {
            this.addCriterion("mana >", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("mana > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("mana >=", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("mana >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaLessThan(final Integer value) {
            this.addCriterion("mana <", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanColumn(final Pet.Column column) {
            this.addCriterion("mana < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanOrEqualTo(final Integer value) {
            this.addCriterion("mana <=", value, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("mana <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andManaIn(final List<Integer> values) {
            this.addCriterion("mana in", values, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotIn(final List<Integer> values) {
            this.addCriterion("mana not in", values, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mana between", value1, value2, "mana");
            return (Criteria)this;
        }
        
        public Criteria andManaNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mana not between", value1, value2, "mana");
            return (Criteria)this;
        }
        
        public Criteria andSpeedIsNull() {
            this.addCriterion("speed is null");
            return (Criteria)this;
        }
        
        public Criteria andSpeedIsNotNull() {
            this.addCriterion("speed is not null");
            return (Criteria)this;
        }
        
        public Criteria andSpeedEqualTo(final Integer value) {
            this.addCriterion("speed =", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedEqualToColumn(final Pet.Column column) {
            this.addCriterion("speed = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedNotEqualTo(final Integer value) {
            this.addCriterion("speed <>", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("speed <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedGreaterThan(final Integer value) {
            this.addCriterion("speed >", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("speed > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("speed >=", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("speed >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedLessThan(final Integer value) {
            this.addCriterion("speed <", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedLessThanColumn(final Pet.Column column) {
            this.addCriterion("speed < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedLessThanOrEqualTo(final Integer value) {
            this.addCriterion("speed <=", value, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("speed <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSpeedIn(final List<Integer> values) {
            this.addCriterion("speed in", values, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedNotIn(final List<Integer> values) {
            this.addCriterion("speed not in", values, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedBetween(final Integer value1, final Integer value2) {
            this.addCriterion("speed between", value1, value2, "speed");
            return (Criteria)this;
        }
        
        public Criteria andSpeedNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("speed not between", value1, value2, "speed");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackIsNull() {
            this.addCriterion("phy_attack is null");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackIsNotNull() {
            this.addCriterion("phy_attack is not null");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackEqualTo(final Integer value) {
            this.addCriterion("phy_attack =", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackEqualToColumn(final Pet.Column column) {
            this.addCriterion("phy_attack = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackNotEqualTo(final Integer value) {
            this.addCriterion("phy_attack <>", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("phy_attack <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackGreaterThan(final Integer value) {
            this.addCriterion("phy_attack >", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("phy_attack > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("phy_attack >=", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("phy_attack >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackLessThan(final Integer value) {
            this.addCriterion("phy_attack <", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackLessThanColumn(final Pet.Column column) {
            this.addCriterion("phy_attack < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackLessThanOrEqualTo(final Integer value) {
            this.addCriterion("phy_attack <=", value, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("phy_attack <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackIn(final List<Integer> values) {
            this.addCriterion("phy_attack in", values, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackNotIn(final List<Integer> values) {
            this.addCriterion("phy_attack not in", values, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackBetween(final Integer value1, final Integer value2) {
            this.addCriterion("phy_attack between", value1, value2, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andPhyAttackNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("phy_attack not between", value1, value2, "phyAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackIsNull() {
            this.addCriterion("mag_attack is null");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackIsNotNull() {
            this.addCriterion("mag_attack is not null");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackEqualTo(final Integer value) {
            this.addCriterion("mag_attack =", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackEqualToColumn(final Pet.Column column) {
            this.addCriterion("mag_attack = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackNotEqualTo(final Integer value) {
            this.addCriterion("mag_attack <>", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("mag_attack <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackGreaterThan(final Integer value) {
            this.addCriterion("mag_attack >", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("mag_attack > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("mag_attack >=", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("mag_attack >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackLessThan(final Integer value) {
            this.addCriterion("mag_attack <", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackLessThanColumn(final Pet.Column column) {
            this.addCriterion("mag_attack < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackLessThanOrEqualTo(final Integer value) {
            this.addCriterion("mag_attack <=", value, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("mag_attack <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andMagAttackIn(final List<Integer> values) {
            this.addCriterion("mag_attack in", values, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackNotIn(final List<Integer> values) {
            this.addCriterion("mag_attack not in", values, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mag_attack between", value1, value2, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andMagAttackNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("mag_attack not between", value1, value2, "magAttack");
            return (Criteria)this;
        }
        
        public Criteria andPolarIsNull() {
            this.addCriterion("polar is null");
            return (Criteria)this;
        }
        
        public Criteria andPolarIsNotNull() {
            this.addCriterion("polar is not null");
            return (Criteria)this;
        }
        
        public Criteria andPolarEqualTo(final String value) {
            this.addCriterion("polar =", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarEqualToColumn(final Pet.Column column) {
            this.addCriterion("polar = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarNotEqualTo(final String value) {
            this.addCriterion("polar <>", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("polar <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThan(final String value) {
            this.addCriterion("polar >", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("polar > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanOrEqualTo(final String value) {
            this.addCriterion("polar >=", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("polar >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThan(final String value) {
            this.addCriterion("polar <", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanColumn(final Pet.Column column) {
            this.addCriterion("polar < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanOrEqualTo(final String value) {
            this.addCriterion("polar <=", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("polar <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andPolarLike(final String value) {
            this.addCriterion("polar like", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotLike(final String value) {
            this.addCriterion("polar not like", value, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarIn(final List<String> values) {
            this.addCriterion("polar in", values, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotIn(final List<String> values) {
            this.addCriterion("polar not in", values, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarBetween(final String value1, final String value2) {
            this.addCriterion("polar between", value1, value2, "polar");
            return (Criteria)this;
        }
        
        public Criteria andPolarNotBetween(final String value1, final String value2) {
            this.addCriterion("polar not between", value1, value2, "polar");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsIsNull() {
            this.addCriterion("skiils is null");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsIsNotNull() {
            this.addCriterion("skiils is not null");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsEqualTo(final String value) {
            this.addCriterion("skiils =", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsEqualToColumn(final Pet.Column column) {
            this.addCriterion("skiils = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsNotEqualTo(final String value) {
            this.addCriterion("skiils <>", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("skiils <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsGreaterThan(final String value) {
            this.addCriterion("skiils >", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("skiils > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsGreaterThanOrEqualTo(final String value) {
            this.addCriterion("skiils >=", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("skiils >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsLessThan(final String value) {
            this.addCriterion("skiils <", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsLessThanColumn(final Pet.Column column) {
            this.addCriterion("skiils < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsLessThanOrEqualTo(final String value) {
            this.addCriterion("skiils <=", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("skiils <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andSkiilsLike(final String value) {
            this.addCriterion("skiils like", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsNotLike(final String value) {
            this.addCriterion("skiils not like", value, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsIn(final List<String> values) {
            this.addCriterion("skiils in", values, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsNotIn(final List<String> values) {
            this.addCriterion("skiils not in", values, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsBetween(final String value1, final String value2) {
            this.addCriterion("skiils between", value1, value2, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andSkiilsNotBetween(final String value1, final String value2) {
            this.addCriterion("skiils not between", value1, value2, "skiils");
            return (Criteria)this;
        }
        
        public Criteria andZoonIsNull() {
            this.addCriterion("zoon is null");
            return (Criteria)this;
        }
        
        public Criteria andZoonIsNotNull() {
            this.addCriterion("zoon is not null");
            return (Criteria)this;
        }
        
        public Criteria andZoonEqualTo(final String value) {
            this.addCriterion("zoon =", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonEqualToColumn(final Pet.Column column) {
            this.addCriterion("zoon = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonNotEqualTo(final String value) {
            this.addCriterion("zoon <>", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("zoon <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonGreaterThan(final String value) {
            this.addCriterion("zoon >", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("zoon > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonGreaterThanOrEqualTo(final String value) {
            this.addCriterion("zoon >=", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("zoon >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonLessThan(final String value) {
            this.addCriterion("zoon <", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonLessThanColumn(final Pet.Column column) {
            this.addCriterion("zoon < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonLessThanOrEqualTo(final String value) {
            this.addCriterion("zoon <=", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonLessThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("zoon <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andZoonLike(final String value) {
            this.addCriterion("zoon like", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonNotLike(final String value) {
            this.addCriterion("zoon not like", value, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonIn(final List<String> values) {
            this.addCriterion("zoon in", values, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonNotIn(final List<String> values) {
            this.addCriterion("zoon not in", values, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonBetween(final String value1, final String value2) {
            this.addCriterion("zoon between", value1, value2, "zoon");
            return (Criteria)this;
        }
        
        public Criteria andZoonNotBetween(final String value1, final String value2) {
            this.addCriterion("zoon not between", value1, value2, "zoon");
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
        
        public Criteria andIconEqualToColumn(final Pet.Column column) {
            this.addCriterion("icon = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconNotEqualTo(final Integer value) {
            this.addCriterion("icon <>", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("icon <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThan(final Integer value) {
            this.addCriterion("icon >", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("icon > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("icon >=", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("icon >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconLessThan(final Integer value) {
            this.addCriterion("icon <", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanColumn(final Pet.Column column) {
            this.addCriterion("icon < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanOrEqualTo(final Integer value) {
            this.addCriterion("icon <=", value, "icon");
            return (Criteria)this;
        }
        
        public Criteria andIconLessThanOrEqualToColumn(final Pet.Column column) {
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
        
        public Criteria andAddTimeEqualToColumn(final Pet.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final Pet.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final Pet.Column column) {
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
        
        public Criteria andUpdateTimeEqualToColumn(final Pet.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final Pet.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final Pet.Column column) {
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
        
        public Criteria andDeletedEqualToColumn(final Pet.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final Pet.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final Pet.Column column) {
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
        
        public Criteria andNameEqualToColumn(final Pet.Column column) {
            this.addCriterion("`name` = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("`name` <>", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameNotEqualToColumn(final Pet.Column column) {
            this.addCriterion("`name` <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("`name` >", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanColumn(final Pet.Column column) {
            this.addCriterion("`name` > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("`name` >=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameGreaterThanOrEqualToColumn(final Pet.Column column) {
            this.addCriterion("`name` >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThan(final String value) {
            this.addCriterion("`name` <", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanColumn(final Pet.Column column) {
            this.addCriterion("`name` < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("`name` <=", value, "name");
            return (Criteria)this;
        }
        
        public Criteria andNameLessThanOrEqualToColumn(final Pet.Column column) {
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
        void example(final PetExample paramPetExample);
    }
}
