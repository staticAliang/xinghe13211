package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;
// 杂货店售卖的道具	(这里不用改，没用，仅作为显示用的)
public class GroceriesShop implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private Integer goodsNo;
    private Integer payType;
    private String name;
    private Integer value;
    private Integer level;
    private Integer type;
    private Integer itemcount;
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
    
    public Integer getGoodsNo() {
        return this.goodsNo;
    }
    
    public void setGoodsNo(final Integer goodsNo) {
        this.goodsNo = goodsNo;
    }
    
    public Integer getPayType() {
        return this.payType;
    }
    
    public void setPayType(final Integer payType) {
        this.payType = payType;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(final Integer value) {
        this.value = value;
    }
    
    public Integer getLevel() {
        return this.level;
    }
    
    public void setLevel(final Integer level) {
        this.level = level;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setType(final Integer type) {
        this.type = type;
    }
    
    public Integer getItemcount() {
        return this.itemcount;
    }
    
    public void setItemcount(final Integer itemcount) {
        this.itemcount = itemcount;
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
        sb.append(", IS_DELETED=").append(GroceriesShop.IS_DELETED);
        sb.append(", NOT_DELETED=").append(GroceriesShop.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", goodsNo=").append(this.goodsNo);
        sb.append(", payType=").append(this.payType);
        sb.append(", name=").append(this.name);
        sb.append(", value=").append(this.value);
        sb.append(", level=").append(this.level);
        sb.append(", type=").append(this.type);
        sb.append(", itemcount=").append(this.itemcount);
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
        final GroceriesShop other = (GroceriesShop)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getGoodsNo() == null) {
            if (other.getGoodsNo() != null) {
                return false;
            }
        }
        else if (!this.getGoodsNo().equals(other.getGoodsNo())) {
            return false;
        }
        if (this.getPayType() == null) {
            if (other.getPayType() != null) {
                return false;
            }
        }
        else if (!this.getPayType().equals(other.getPayType())) {
            return false;
        }
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        }
        else if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (this.getValue() == null) {
            if (other.getValue() != null) {
                return false;
            }
        }
        else if (!this.getValue().equals(other.getValue())) {
            return false;
        }
        if (this.getLevel() == null) {
            if (other.getLevel() != null) {
                return false;
            }
        }
        else if (!this.getLevel().equals(other.getLevel())) {
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
        if (this.getItemcount() == null) {
            if (other.getItemcount() != null) {
                return false;
            }
        }
        else if (!this.getItemcount().equals(other.getItemcount())) {
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
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getGoodsNo() == null) ? 0 : this.getGoodsNo().hashCode());
        result = 31 * result + ((this.getPayType() == null) ? 0 : this.getPayType().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        result = 31 * result + ((this.getLevel() == null) ? 0 : this.getLevel().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getItemcount() == null) ? 0 : this.getItemcount().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public GroceriesShop clone() throws CloneNotSupportedException {
        return (GroceriesShop)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        goodsNo("goods_no", "goodsNo", "INTEGER", false), 
        payType("pay_type", "payType", "INTEGER", false), 
        name("name", "name", "VARCHAR", true), 
        value("value", "value", "INTEGER", true), 
        level("level", "level", "INTEGER", true), 
        type("type", "type", "INTEGER", true), 
        itemcount("itemCount", "itemcount", "INTEGER", false), 
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
