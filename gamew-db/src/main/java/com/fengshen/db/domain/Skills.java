package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class Skills implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String skillIdHex;
    private String skillName;
    private Integer skillReqpolar;
    private Integer skillType;
    private Integer skillTypeLevel;
    private Integer skillMagic;
    private Integer skillReqLevel;
    private String skillContext;
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
    
    public String getSkillIdHex() {
        return this.skillIdHex;
    }
    
    public void setSkillIdHex(final String skillIdHex) {
        this.skillIdHex = skillIdHex;
    }
    
    public String getSkillName() {
        return this.skillName;
    }
    
    public void setSkillName(final String skillName) {
        this.skillName = skillName;
    }
    
    public Integer getSkillReqpolar() {
        return this.skillReqpolar;
    }
    
    public void setSkillReqpolar(final Integer skillReqpolar) {
        this.skillReqpolar = skillReqpolar;
    }
    
    public Integer getSkillType() {
        return this.skillType;
    }
    
    public void setSkillType(final Integer skillType) {
        this.skillType = skillType;
    }
    
    public Integer getSkillTypeLevel() {
        return this.skillTypeLevel;
    }
    
    public void setSkillTypeLevel(final Integer skillTypeLevel) {
        this.skillTypeLevel = skillTypeLevel;
    }
    
    public Integer getSkillMagic() {
        return this.skillMagic;
    }
    
    public void setSkillMagic(final Integer skillMagic) {
        this.skillMagic = skillMagic;
    }
    
    public Integer getSkillReqLevel() {
        return this.skillReqLevel;
    }
    
    public void setSkillReqLevel(final Integer skillReqLevel) {
        this.skillReqLevel = skillReqLevel;
    }
    
    public String getSkillContext() {
        return this.skillContext;
    }
    
    public void setSkillContext(final String skillContext) {
        this.skillContext = skillContext;
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
        sb.append(", IS_DELETED=").append(Skills.IS_DELETED);
        sb.append(", NOT_DELETED=").append(Skills.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", skillIdHex=").append(this.skillIdHex);
        sb.append(", skillName=").append(this.skillName);
        sb.append(", skillReqpolar=").append(this.skillReqpolar);
        sb.append(", skillType=").append(this.skillType);
        sb.append(", skillTypeLevel=").append(this.skillTypeLevel);
        sb.append(", skillMagic=").append(this.skillMagic);
        sb.append(", skillReqLevel=").append(this.skillReqLevel);
        sb.append(", skillContext=").append(this.skillContext);
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
        final Skills other = (Skills)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getSkillIdHex() == null) {
            if (other.getSkillIdHex() != null) {
                return false;
            }
        }
        else if (!this.getSkillIdHex().equals(other.getSkillIdHex())) {
            return false;
        }
        if (this.getSkillName() == null) {
            if (other.getSkillName() != null) {
                return false;
            }
        }
        else if (!this.getSkillName().equals(other.getSkillName())) {
            return false;
        }
        if (this.getSkillReqpolar() == null) {
            if (other.getSkillReqpolar() != null) {
                return false;
            }
        }
        else if (!this.getSkillReqpolar().equals(other.getSkillReqpolar())) {
            return false;
        }
        if (this.getSkillType() == null) {
            if (other.getSkillType() != null) {
                return false;
            }
        }
        else if (!this.getSkillType().equals(other.getSkillType())) {
            return false;
        }
        if (this.getSkillTypeLevel() == null) {
            if (other.getSkillTypeLevel() != null) {
                return false;
            }
        }
        else if (!this.getSkillTypeLevel().equals(other.getSkillTypeLevel())) {
            return false;
        }
        if (this.getSkillMagic() == null) {
            if (other.getSkillMagic() != null) {
                return false;
            }
        }
        else if (!this.getSkillMagic().equals(other.getSkillMagic())) {
            return false;
        }
        if (this.getSkillReqLevel() == null) {
            if (other.getSkillReqLevel() != null) {
                return false;
            }
        }
        else if (!this.getSkillReqLevel().equals(other.getSkillReqLevel())) {
            return false;
        }
        if (this.getSkillContext() == null) {
            if (other.getSkillContext() != null) {
                return false;
            }
        }
        else if (!this.getSkillContext().equals(other.getSkillContext())) {
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
        result = 31 * result + ((this.getSkillIdHex() == null) ? 0 : this.getSkillIdHex().hashCode());
        result = 31 * result + ((this.getSkillName() == null) ? 0 : this.getSkillName().hashCode());
        result = 31 * result + ((this.getSkillReqpolar() == null) ? 0 : this.getSkillReqpolar().hashCode());
        result = 31 * result + ((this.getSkillType() == null) ? 0 : this.getSkillType().hashCode());
        result = 31 * result + ((this.getSkillTypeLevel() == null) ? 0 : this.getSkillTypeLevel().hashCode());
        result = 31 * result + ((this.getSkillMagic() == null) ? 0 : this.getSkillMagic().hashCode());
        result = 31 * result + ((this.getSkillReqLevel() == null) ? 0 : this.getSkillReqLevel().hashCode());
        result = 31 * result + ((this.getSkillContext() == null) ? 0 : this.getSkillContext().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public Skills clone() throws CloneNotSupportedException {
        return (Skills)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        skillIdHex("skill_id_hex", "skillIdHex", "VARCHAR", false), 
        skillName("skill_name", "skillName", "VARCHAR", false), 
        skillReqpolar("skill_req_polar", "skillReqpolar", "INTEGER", false), 
        skillType("skill_type", "skillType", "INTEGER", false), 
        skillTypeLevel("skill_type_level", "skillTypeLevel", "INTEGER", false), 
        skillMagic("skill_magic", "skillMagic", "INTEGER", false), 
        skillReqLevel("skill_req_level", "skillReqLevel", "INTEGER", false), 
        skillContext("skill_context", "skillContext", "VARCHAR", false), 
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
