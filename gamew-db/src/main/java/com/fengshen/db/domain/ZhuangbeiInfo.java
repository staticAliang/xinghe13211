package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class ZhuangbeiInfo implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private Integer attrib; // 装备的等级
    private Integer amount; // 装备类型，1是武器，2是帽子，3是衣服，10是鞋子，4是项链，5是玉佩，6是手镯
    private Integer type;
    private String str;
    private String quality; // 装备的颜色，蓝色、粉色、黄色、绿色
    private Integer master;
    private Integer metal; // 武器的门派
    private Integer mana;
    private Integer accurate;
    private Integer def;
    private Integer dex;
    private Integer wiz;
    private Integer parry;
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
    
    public Integer getAttrib() {
        return this.attrib;
    }
    
    public void setAttrib(final Integer attrib) {
        this.attrib = attrib;
    }
    
    public Integer getAmount() {
        return this.amount;
    }
    
    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setType(final Integer type) {
        this.type = type;
    }
    
    public String getStr() {
        return this.str;
    }
    
    public void setStr(final String str) {
        this.str = str;
    }
    
    public String getQuality() {
        return this.quality;
    }
    
    public void setQuality(final String quality) {
        this.quality = quality;
    }
    
    public Integer getMaster() {
        return this.master;
    }
    
    public void setMaster(final Integer master) {
        this.master = master;
    }
    
    public Integer getMetal() {
        return this.metal;
    }
    
    public void setMetal(final Integer metal) {
        this.metal = metal;
    }
    
    public Integer getMana() {
        return this.mana;
    }
    
    public void setMana(final Integer mana) {
        this.mana = mana;
    }
    
    public Integer getAccurate() {
        return this.accurate;
    }
    
    public void setAccurate(final Integer accurate) {
        this.accurate = accurate;
    }
    
    public Integer getDef() {
        return this.def;
    }
    
    public void setDef(final Integer def) {
        this.def = def;
    }
    
    public Integer getDex() {
        return this.dex;
    }
    
    public void setDex(final Integer dex) {
        this.dex = dex;
    }
    
    public Integer getWiz() {
        return this.wiz;
    }
    
    public void setWiz(final Integer wiz) {
        this.wiz = wiz;
    }
    
    public Integer getParry() {
        return this.parry;
    }
    
    public void setParry(final Integer parry) {
        this.parry = parry;
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
        sb.append(", IS_DELETED=").append(ZhuangbeiInfo.IS_DELETED);
        sb.append(", NOT_DELETED=").append(ZhuangbeiInfo.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", attrib=").append(this.attrib);
        sb.append(", amount=").append(this.amount);
        sb.append(", type=").append(this.type);
        sb.append(", str=").append(this.str);
        sb.append(", quality=").append(this.quality);
        sb.append(", master=").append(this.master);
        sb.append(", metal=").append(this.metal);
        sb.append(", mana=").append(this.mana);
        sb.append(", accurate=").append(this.accurate);
        sb.append(", def=").append(this.def);
        sb.append(", dex=").append(this.dex);
        sb.append(", wiz=").append(this.wiz);
        sb.append(", parry=").append(this.parry);
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
        final ZhuangbeiInfo other = (ZhuangbeiInfo)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getAttrib() == null) {
            if (other.getAttrib() != null) {
                return false;
            }
        }
        else if (!this.getAttrib().equals(other.getAttrib())) {
            return false;
        }
        if (this.getAmount() == null) {
            if (other.getAmount() != null) {
                return false;
            }
        }
        else if (!this.getAmount().equals(other.getAmount())) {
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
        if (this.getStr() == null) {
            if (other.getStr() != null) {
                return false;
            }
        }
        else if (!this.getStr().equals(other.getStr())) {
            return false;
        }
        if (this.getQuality() == null) {
            if (other.getQuality() != null) {
                return false;
            }
        }
        else if (!this.getQuality().equals(other.getQuality())) {
            return false;
        }
        if (this.getMaster() == null) {
            if (other.getMaster() != null) {
                return false;
            }
        }
        else if (!this.getMaster().equals(other.getMaster())) {
            return false;
        }
        if (this.getMetal() == null) {
            if (other.getMetal() != null) {
                return false;
            }
        }
        else if (!this.getMetal().equals(other.getMetal())) {
            return false;
        }
        if (this.getMana() == null) {
            if (other.getMana() != null) {
                return false;
            }
        }
        else if (!this.getMana().equals(other.getMana())) {
            return false;
        }
        if (this.getAccurate() == null) {
            if (other.getAccurate() != null) {
                return false;
            }
        }
        else if (!this.getAccurate().equals(other.getAccurate())) {
            return false;
        }
        if (this.getDef() == null) {
            if (other.getDef() != null) {
                return false;
            }
        }
        else if (!this.getDef().equals(other.getDef())) {
            return false;
        }
        if (this.getDex() == null) {
            if (other.getDex() != null) {
                return false;
            }
        }
        else if (!this.getDex().equals(other.getDex())) {
            return false;
        }
        if (this.getWiz() == null) {
            if (other.getWiz() != null) {
                return false;
            }
        }
        else if (!this.getWiz().equals(other.getWiz())) {
            return false;
        }
        if (this.getParry() == null) {
            if (other.getParry() != null) {
                return false;
            }
        }
        else if (!this.getParry().equals(other.getParry())) {
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
        if (this.getDeleted() == null) {
            if (other.getDeleted() != null) {
                return false;
            }
        }
        else if (!this.getDeleted().equals(other.getDeleted())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getAttrib() == null) ? 0 : this.getAttrib().hashCode());
        result = 31 * result + ((this.getAmount() == null) ? 0 : this.getAmount().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getStr() == null) ? 0 : this.getStr().hashCode());
        result = 31 * result + ((this.getQuality() == null) ? 0 : this.getQuality().hashCode());
        result = 31 * result + ((this.getMaster() == null) ? 0 : this.getMaster().hashCode());
        result = 31 * result + ((this.getMetal() == null) ? 0 : this.getMetal().hashCode());
        result = 31 * result + ((this.getMana() == null) ? 0 : this.getMana().hashCode());
        result = 31 * result + ((this.getAccurate() == null) ? 0 : this.getAccurate().hashCode());
        result = 31 * result + ((this.getDef() == null) ? 0 : this.getDef().hashCode());
        result = 31 * result + ((this.getDex() == null) ? 0 : this.getDex().hashCode());
        result = 31 * result + ((this.getWiz() == null) ? 0 : this.getWiz().hashCode());
        result = 31 * result + ((this.getParry() == null) ? 0 : this.getParry().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public ZhuangbeiInfo clone() throws CloneNotSupportedException {
        return (ZhuangbeiInfo)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        attrib("attrib", "attrib", "INTEGER", false), 
        amount("amount", "amount", "INTEGER", false), 
        type("type", "type", "INTEGER", true), 
        str("str", "str", "VARCHAR", false), 
        quality("quality", "quality", "VARCHAR", false), 
        master("master", "master", "INTEGER", false), 
        metal("metal", "metal", "INTEGER", false), 
        mana("mana", "mana", "INTEGER", false), 
        accurate("accurate", "accurate", "INTEGER", false), 
        def("def", "def", "INTEGER", false), 
        dex("dex", "dex", "INTEGER", false), 
        wiz("wiz", "wiz", "INTEGER", false), 
        parry("parry", "parry", "INTEGER", false), 
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
