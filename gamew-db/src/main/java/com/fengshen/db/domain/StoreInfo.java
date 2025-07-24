package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class StoreInfo implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String quality;
    private Integer value;
    private Integer type;
    private String name;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Boolean deleted;
    private Integer totalScore;
    private Integer recognizeRecognized;
    private Integer rebuildLevel;
    private Integer silverCoin;
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
    
    public String getQuality() {
        return this.quality;
    }
    
    public void setQuality(final String quality) {
        this.quality = quality;
    }
    
    public Integer getValue() {
        return this.value;
    }
    
    public void setValue(final Integer value) {
        this.value = value;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setType(final Integer type) {
        this.type = type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
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
    
    public Integer getTotalScore() {
        return this.totalScore;
    }
    
    public void setTotalScore(final Integer totalScore) {
        this.totalScore = totalScore;
    }
    
    public Integer getRecognizeRecognized() {
        return this.recognizeRecognized;
    }
    
    public void setRecognizeRecognized(final Integer recognizeRecognized) {
        this.recognizeRecognized = recognizeRecognized;
    }
    
    public Integer getRebuildLevel() {
        return this.rebuildLevel;
    }
    
    public void setRebuildLevel(final Integer rebuildLevel) {
        this.rebuildLevel = rebuildLevel;
    }
    
    public Integer getSilverCoin() {
        return this.silverCoin;
    }
    
    public void setSilverCoin(final Integer silverCoin) {
        this.silverCoin = silverCoin;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", IS_DELETED=").append(StoreInfo.IS_DELETED);
        sb.append(", NOT_DELETED=").append(StoreInfo.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", quality=").append(this.quality);
        sb.append(", value=").append(this.value);
        sb.append(", type=").append(this.type);
        sb.append(", name=").append(this.name);
        sb.append(", addTime=").append(this.addTime);
        sb.append(", updateTime=").append(this.updateTime);
        sb.append(", deleted=").append(this.deleted);
        sb.append(", totalScore=").append(this.totalScore);
        sb.append(", recognizeRecognized=").append(this.recognizeRecognized);
        sb.append(", rebuildLevel=").append(this.rebuildLevel);
        sb.append(", silverCoin=").append(this.silverCoin);
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
        final StoreInfo other = (StoreInfo)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
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
        if (this.getValue() == null) {
            if (other.getValue() != null) {
                return false;
            }
        }
        else if (!this.getValue().equals(other.getValue())) {
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
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        }
        else if (!this.getName().equals(other.getName())) {
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
        if (this.getTotalScore() == null) {
            if (other.getTotalScore() != null) {
                return false;
            }
        }
        else if (!this.getTotalScore().equals(other.getTotalScore())) {
            return false;
        }
        if (this.getRecognizeRecognized() == null) {
            if (other.getRecognizeRecognized() != null) {
                return false;
            }
        }
        else if (!this.getRecognizeRecognized().equals(other.getRecognizeRecognized())) {
            return false;
        }
        if (this.getRebuildLevel() == null) {
            if (other.getRebuildLevel() != null) {
                return false;
            }
        }
        else if (!this.getRebuildLevel().equals(other.getRebuildLevel())) {
            return false;
        }
        if (this.getSilverCoin() == null) {
            if (other.getSilverCoin() != null) {
                return false;
            }
        }
        else if (!this.getSilverCoin().equals(other.getSilverCoin())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getQuality() == null) ? 0 : this.getQuality().hashCode());
        result = 31 * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        result = 31 * result + ((this.getTotalScore() == null) ? 0 : this.getTotalScore().hashCode());
        result = 31 * result + ((this.getRecognizeRecognized() == null) ? 0 : this.getRecognizeRecognized().hashCode());
        result = 31 * result + ((this.getRebuildLevel() == null) ? 0 : this.getRebuildLevel().hashCode());
        result = 31 * result + ((this.getSilverCoin() == null) ? 0 : this.getSilverCoin().hashCode());
        return result;
    }
    
    public StoreInfo clone() throws CloneNotSupportedException {
        return (StoreInfo)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        quality("quality", "quality", "VARCHAR", false), 
        value("value", "value", "INTEGER", true), 
        type("type", "type", "INTEGER", true), 
        name("name", "name", "VARCHAR", true), 
        addTime("add_time", "addTime", "TIMESTAMP", false), 
        updateTime("update_time", "updateTime", "TIMESTAMP", false), 
        deleted("deleted", "deleted", "BIT", false), 
        totalScore("total_score", "totalScore", "INTEGER", false), 
        recognizeRecognized("recognize_recognized", "recognizeRecognized", "INTEGER", false), 
        rebuildLevel("rebuild_level", "rebuildLevel", "INTEGER", false), 
        silverCoin("silver_coin", "silverCoin", "INTEGER", false);
        
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
