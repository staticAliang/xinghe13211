package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class Srenwu implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String pid;
    private Integer rid;
    private String skillName;
    private String skillJieshao;
    private String skillDqti;
    private String skillXck;
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
    
    public String getPid() {
        return this.pid;
    }
    
    public void setPid(final String pid) {
        this.pid = pid;
    }
    
    public Integer getRid() {
        return this.rid;
    }
    
    public void setRid(final Integer rid) {
        this.rid = rid;
    }
    
    public String getSkillName() {
        return this.skillName;
    }
    
    public void setSkillName(final String skillName) {
        this.skillName = skillName;
    }
    
    public String getSkillJieshao() {
        return this.skillJieshao;
    }
    
    public void setSkillJieshao(final String skillJieshao) {
        this.skillJieshao = skillJieshao;
    }
    
    public String getSkillDqti() {
        return this.skillDqti;
    }
    
    public void setSkillDqti(final String skillDqti) {
        this.skillDqti = skillDqti;
    }
    
    public String getSkillXck() {
        return this.skillXck;
    }
    
    public void setSkillXck(final String skillXck) {
        this.skillXck = skillXck;
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
        sb.append(", IS_DELETED=").append(Srenwu.IS_DELETED);
        sb.append(", NOT_DELETED=").append(Srenwu.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", pid=").append(this.pid);
        sb.append(", rid=").append(this.rid);
        sb.append(", skillName=").append(this.skillName);
        sb.append(", skillJieshao=").append(this.skillJieshao);
        sb.append(", skillDqti=").append(this.skillDqti);
        sb.append(", skillXck=").append(this.skillXck);
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
        final Srenwu other = (Srenwu)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getPid() == null) {
            if (other.getPid() != null) {
                return false;
            }
        }
        else if (!this.getPid().equals(other.getPid())) {
            return false;
        }
        if (this.getRid() == null) {
            if (other.getRid() != null) {
                return false;
            }
        }
        else if (!this.getRid().equals(other.getRid())) {
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
        if (this.getSkillJieshao() == null) {
            if (other.getSkillJieshao() != null) {
                return false;
            }
        }
        else if (!this.getSkillJieshao().equals(other.getSkillJieshao())) {
            return false;
        }
        if (this.getSkillDqti() == null) {
            if (other.getSkillDqti() != null) {
                return false;
            }
        }
        else if (!this.getSkillDqti().equals(other.getSkillDqti())) {
            return false;
        }
        if (this.getSkillXck() == null) {
            if (other.getSkillXck() != null) {
                return false;
            }
        }
        else if (!this.getSkillXck().equals(other.getSkillXck())) {
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
        result = 31 * result + ((this.getPid() == null) ? 0 : this.getPid().hashCode());
        result = 31 * result + ((this.getRid() == null) ? 0 : this.getRid().hashCode());
        result = 31 * result + ((this.getSkillName() == null) ? 0 : this.getSkillName().hashCode());
        result = 31 * result + ((this.getSkillJieshao() == null) ? 0 : this.getSkillJieshao().hashCode());
        result = 31 * result + ((this.getSkillDqti() == null) ? 0 : this.getSkillDqti().hashCode());
        result = 31 * result + ((this.getSkillXck() == null) ? 0 : this.getSkillXck().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public Srenwu clone() throws CloneNotSupportedException {
        return (Srenwu)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        pid("pid", "pid", "VARCHAR", false), 
        rid("rid", "rid", "INTEGER", false), 
        skillName("skill_name", "skillName", "VARCHAR", false), 
        skillJieshao("skill_jieshao", "skillJieshao", "VARCHAR", false), 
        skillDqti("skill_dqti", "skillDqti", "VARCHAR", false), 
        skillXck("skill_xck", "skillXck", "VARCHAR", false), 
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
