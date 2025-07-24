package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class SkillsChongw implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String ownerid;
    private String skllCwid;
    private String skillIdHex;
    private String skillName;
    private Integer skillReqpolar;
    private Integer skillLevel;
    private Integer skillMubiao;
    private String tianshuId;
    private String tianshuName;
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
    
    public String getOwnerid() {
        return this.ownerid;
    }
    
    public void setOwnerid(final String ownerid) {
        this.ownerid = ownerid;
    }
    
    public String getSkllCwid() {
        return this.skllCwid;
    }
    
    public void setSkllCwid(final String skllCwid) {
        this.skllCwid = skllCwid;
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
    
    public Integer getSkillLevel() {
        return this.skillLevel;
    }
    
    public void setSkillLevel(final Integer skillLevel) {
        this.skillLevel = skillLevel;
    }
    
    public Integer getSkillMubiao() {
        return this.skillMubiao;
    }
    
    public void setSkillMubiao(final Integer skillMubiao) {
        this.skillMubiao = skillMubiao;
    }
    
    public String getTianshuId() {
        return this.tianshuId;
    }
    
    public void setTianshuId(final String tianshuId) {
        this.tianshuId = tianshuId;
    }
    
    public String getTianshuName() {
        return this.tianshuName;
    }
    
    public void setTianshuName(final String tianshuName) {
        this.tianshuName = tianshuName;
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
        sb.append(", IS_DELETED=").append(SkillsChongw.IS_DELETED);
        sb.append(", NOT_DELETED=").append(SkillsChongw.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", ownerid=").append(this.ownerid);
        sb.append(", skllCwid=").append(this.skllCwid);
        sb.append(", skillIdHex=").append(this.skillIdHex);
        sb.append(", skillName=").append(this.skillName);
        sb.append(", skillReqpolar=").append(this.skillReqpolar);
        sb.append(", skillLevel=").append(this.skillLevel);
        sb.append(", skillMubiao=").append(this.skillMubiao);
        sb.append(", tianshuId=").append(this.tianshuId);
        sb.append(", tianshuName=").append(this.tianshuName);
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
        final SkillsChongw other = (SkillsChongw)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getOwnerid() == null) {
            if (other.getOwnerid() != null) {
                return false;
            }
        }
        else if (!this.getOwnerid().equals(other.getOwnerid())) {
            return false;
        }
        if (this.getSkllCwid() == null) {
            if (other.getSkllCwid() != null) {
                return false;
            }
        }
        else if (!this.getSkllCwid().equals(other.getSkllCwid())) {
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
        if (this.getSkillLevel() == null) {
            if (other.getSkillLevel() != null) {
                return false;
            }
        }
        else if (!this.getSkillLevel().equals(other.getSkillLevel())) {
            return false;
        }
        if (this.getSkillMubiao() == null) {
            if (other.getSkillMubiao() != null) {
                return false;
            }
        }
        else if (!this.getSkillMubiao().equals(other.getSkillMubiao())) {
            return false;
        }
        if (this.getTianshuId() == null) {
            if (other.getTianshuId() != null) {
                return false;
            }
        }
        else if (!this.getTianshuId().equals(other.getTianshuId())) {
            return false;
        }
        if (this.getTianshuName() == null) {
            if (other.getTianshuName() != null) {
                return false;
            }
        }
        else if (!this.getTianshuName().equals(other.getTianshuName())) {
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
        result = 31 * result + ((this.getOwnerid() == null) ? 0 : this.getOwnerid().hashCode());
        result = 31 * result + ((this.getSkllCwid() == null) ? 0 : this.getSkllCwid().hashCode());
        result = 31 * result + ((this.getSkillIdHex() == null) ? 0 : this.getSkillIdHex().hashCode());
        result = 31 * result + ((this.getSkillName() == null) ? 0 : this.getSkillName().hashCode());
        result = 31 * result + ((this.getSkillReqpolar() == null) ? 0 : this.getSkillReqpolar().hashCode());
        result = 31 * result + ((this.getSkillLevel() == null) ? 0 : this.getSkillLevel().hashCode());
        result = 31 * result + ((this.getSkillMubiao() == null) ? 0 : this.getSkillMubiao().hashCode());
        result = 31 * result + ((this.getTianshuId() == null) ? 0 : this.getTianshuId().hashCode());
        result = 31 * result + ((this.getTianshuName() == null) ? 0 : this.getTianshuName().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public SkillsChongw clone() throws CloneNotSupportedException {
        return (SkillsChongw)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        ownerid("ownerid", "ownerid", "VARCHAR", false), 
        skllCwid("skll_cwid", "skllCwid", "VARCHAR", false), 
        skillIdHex("skill_id_hex", "skillIdHex", "VARCHAR", false), 
        skillName("skill_name", "skillName", "VARCHAR", false), 
        skillReqpolar("skill_req_polar", "skillReqpolar", "INTEGER", false), 
        skillLevel("skill_level", "skillLevel", "INTEGER", false), 
        skillMubiao("skill_mubiao", "skillMubiao", "INTEGER", false), 
        tianshuId("tianshu_id", "tianshuId", "VARCHAR", false), 
        tianshuName("tianshu_name", "tianshuName", "VARCHAR", false), 
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
