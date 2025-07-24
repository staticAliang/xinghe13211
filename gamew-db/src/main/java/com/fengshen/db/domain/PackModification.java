package com.fengshen.db.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
// 时装
public class PackModification implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String alias;
    private String fasionType;
    private String str;
    private String type;
    private Integer foodNum;
    private Integer goodsPrice;
    private Integer sex;
    private Integer position;
    private Integer category;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Boolean deleted;
    private static final long serialVersionUID = 1L;
    
    static {
        IS_DELETED = Deleted.IS_DELETED.value();
        NOT_DELETED = Deleted.NOT_DELETED.value();
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String alias) {
        this.alias = alias;
    }
    
    public String getFasionType() {
        return this.fasionType;
    }
    
    public void setFasionType(final String fasionType) {
        this.fasionType = fasionType;
    }
    
    public String getStr() {
        return this.str;
    }
    
    public void setStr(final String str) {
        this.str = str;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public Integer getFoodNum() {
        return this.foodNum;
    }
    
    public void setFoodNum(final Integer foodNum) {
        this.foodNum = foodNum;
    }
    
    public Integer getGoodsPrice() {
        return this.goodsPrice;
    }
    
    public void setGoodsPrice(final Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    
    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(final Integer sex) {
        this.sex = sex;
    }
    
    public Integer getPosition() {
        return this.position;
    }
    
    public void setPosition(final Integer position) {
        this.position = position;
    }
    
    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(final Integer category) {
        this.category = category;
    }
    
    public LocalDateTime getAddTime() {
        return this.addTime;
    }
    
    public void setAddTime(final LocalDateTime addTime) {
        this.addTime = addTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(final LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public void andLogicalDeleted(final boolean deleted) {
        this.setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }
    
    public Boolean getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(final Boolean deleted) {
        this.deleted = deleted;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", IS_DELETED=").append(PackModification.IS_DELETED);
        sb.append(", NOT_DELETED=").append(PackModification.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", alias=").append(this.alias);
        sb.append(", fasionType=").append(this.fasionType);
        sb.append(", str=").append(this.str);
        sb.append(", type=").append(this.type);
        sb.append(", foodNum=").append(this.foodNum);
        sb.append(", goodsPrice=").append(this.goodsPrice);
        sb.append(", sex=").append(this.sex);
        sb.append(", position=").append(this.position);
        sb.append(", category=").append(this.category);
        sb.append(", addTime=").append(this.addTime);
        sb.append(", updateTime=").append(this.updateTime);
        sb.append(", deleted=").append(this.deleted);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        final PackModification other = (PackModification)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getAlias() == null) {
            if (other.getAlias() != null) {
                return false;
            }
        }
        else if (!this.getAlias().equals(other.getAlias())) {
            return false;
        }
        if (this.getFasionType() == null) {
            if (other.getFasionType() != null) {
                return false;
            }
        }
        else if (!this.getFasionType().equals(other.getFasionType())) {
            return false;
        }
        if (this.getStr() == null) {
            if (other.getStr() != null) {
                return false;
            }
        }
        else if (!this.getStr().equals(other.getStr())) {
            return false;
        }
        if (this.getType() == null) {
            if (other.getType() != null) {
                return false;
            }
        }
        else if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (this.getFoodNum() == null) {
            if (other.getFoodNum() != null) {
                return false;
            }
        }
        else if (!this.getFoodNum().equals(other.getFoodNum())) {
            return false;
        }
        if (this.getGoodsPrice() == null) {
            if (other.getGoodsPrice() != null) {
                return false;
            }
        }
        else if (!this.getGoodsPrice().equals(other.getGoodsPrice())) {
            return false;
        }
        if (this.getSex() == null) {
            if (other.getSex() != null) {
                return false;
            }
        }
        else if (!this.getSex().equals(other.getSex())) {
            return false;
        }
        if (this.getPosition() == null) {
            if (other.getPosition() != null) {
                return false;
            }
        }
        else if (!this.getPosition().equals(other.getPosition())) {
            return false;
        }
        if (this.getCategory() == null) {
            if (other.getCategory() != null) {
                return false;
            }
        }
        else if (!this.getCategory().equals(other.getCategory())) {
            return false;
        }
        if (this.getAddTime() == null) {
            if (other.getAddTime() != null) {
                return false;
            }
        }
        else if (!this.getAddTime().equals(other.getAddTime())) {
            return false;
        }
        if (this.getUpdateTime() == null) {
            if (other.getUpdateTime() != null) {
                return false;
            }
        }
        else if (!this.getUpdateTime().equals(other.getUpdateTime())) {
            return false;
        }
        if (this.getDeleted() != null) {
            if (!this.getDeleted().equals(other.getDeleted())) {
                return false;
            }
        }
        else if (other.getDeleted() != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getAlias() == null) ? 0 : this.getAlias().hashCode());
        result = 31 * result + ((this.getFasionType() == null) ? 0 : this.getFasionType().hashCode());
        result = 31 * result + ((this.getStr() == null) ? 0 : this.getStr().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getFoodNum() == null) ? 0 : this.getFoodNum().hashCode());
        result = 31 * result + ((this.getGoodsPrice() == null) ? 0 : this.getGoodsPrice().hashCode());
        result = 31 * result + ((this.getSex() == null) ? 0 : this.getSex().hashCode());
        result = 31 * result + ((this.getPosition() == null) ? 0 : this.getPosition().hashCode());
        result = 31 * result + ((this.getCategory() == null) ? 0 : this.getCategory().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public PackModification clone() throws CloneNotSupportedException {
        return (PackModification)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        alias("alias", "alias", "VARCHAR", true), 
        fasionType("fasion_type", "fasionType", "VARCHAR", false), 
        str("str", "str", "VARCHAR", false), 
        type("type", "type", "VARCHAR", true), 
        foodNum("food_num", "foodNum", "INTEGER", false), 
        goodsPrice("goods_price", "goodsPrice", "INTEGER", false), 
        sex("sex", "sex", "INTEGER", false), 
        position("position", "position", "INTEGER", true), 
        category("category", "category", "INTEGER", false), 
        addTime("add_time", "addTime", "TIMESTAMP", false), 
        updateTime("update_time", "updateTime", "TIMESTAMP", false), 
        deleted("deleted", "deleted", "BIT", false);
        
        private static final String BEGINNING_DELIMITER = "`";
        private static final String ENDING_DELIMITER = "`";
        private final String column;
        private final boolean isColumnNameDelimited;
        private final String javaProperty;
        private final String jdbcType;
        
        public String value() {
            return this.column;
        }
        
        public String getValue() {
            return this.column;
        }
        
        public String getJavaProperty() {
            return this.javaProperty;
        }
        
        public String getJdbcType() {
            return this.jdbcType;
        }
        
        private Column(final String column, final String javaProperty, final String jdbcType, final boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }
        
        public String desc() {
            return String.valueOf(this.getEscapedColumnName()) + " DESC";
        }
        
        public String asc() {
            return String.valueOf(this.getEscapedColumnName()) + " ASC";
        }
        
        public static Column[] excludes(final Column... excludes) {
            final ArrayList<Column> columns = new ArrayList<Column>(Arrays.asList(values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<Object>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[0]);
        }
        
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return "`" + this.column + "`";
            }
            return this.column;
        }
    }
    
    public enum Deleted
    {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");
        
        private final Boolean value;
        private final String name;
        
        private Deleted(final Boolean value, final String name) {
            this.value = value;
            this.name = name;
        }
        
        public Boolean getValue() {
            return this.value;
        }
        
        public Boolean value() {
            return this.value;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
