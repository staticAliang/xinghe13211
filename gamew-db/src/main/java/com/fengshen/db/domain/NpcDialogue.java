package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class NpcDialogue implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String name;
    private Integer portranit;
    private Integer picNo;
    private String content;
    private Integer isconmlete;
    private Integer isincombat;
    private Integer palytime;
    private String taskType;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Boolean deleted;
    private String idname;
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
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getPortranit() {
        return this.portranit;
    }
    
    public void setPortranit(final Integer portranit) {
        this.portranit = portranit;
    }
    
    public Integer getPicNo() {
        return this.picNo;
    }
    
    public void setPicNo(final Integer picNo) {
        this.picNo = picNo;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    public Integer getIsconmlete() {
        return this.isconmlete;
    }
    
    public void setIsconmlete(final Integer isconmlete) {
        this.isconmlete = isconmlete;
    }
    
    public Integer getIsincombat() {
        return this.isincombat;
    }
    
    public void setIsincombat(final Integer isincombat) {
        this.isincombat = isincombat;
    }
    
    public Integer getPalytime() {
        return this.palytime;
    }
    
    public void setPalytime(final Integer palytime) {
        this.palytime = palytime;
    }
    
    public String getTaskType() {
        return this.taskType;
    }
    
    public void setTaskType(final String taskType) {
        this.taskType = taskType;
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
    
    public String getIdname() {
        return this.idname;
    }
    
    public void setIdname(final String idname) {
        this.idname = idname;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", IS_DELETED=").append(NpcDialogue.IS_DELETED);
        sb.append(", NOT_DELETED=").append(NpcDialogue.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", name=").append(this.name);
        sb.append(", portranit=").append(this.portranit);
        sb.append(", picNo=").append(this.picNo);
        sb.append(", content=").append(this.content);
        sb.append(", isconmlete=").append(this.isconmlete);
        sb.append(", isincombat=").append(this.isincombat);
        sb.append(", palytime=").append(this.palytime);
        sb.append(", taskType=").append(this.taskType);
        sb.append(", addTime=").append(this.addTime);
        sb.append(", updateTime=").append(this.updateTime);
        sb.append(", deleted=").append(this.deleted);
        sb.append(", idname=").append(this.idname);
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
        final NpcDialogue other = (NpcDialogue)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
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
        if (this.getPortranit() == null) {
            if (other.getPortranit() != null) {
                return false;
            }
        }
        else if (!this.getPortranit().equals(other.getPortranit())) {
            return false;
        }
        if (this.getPicNo() == null) {
            if (other.getPicNo() != null) {
                return false;
            }
        }
        else if (!this.getPicNo().equals(other.getPicNo())) {
            return false;
        }
        if (this.getContent() == null) {
            if (other.getContent() != null) {
                return false;
            }
        }
        else if (!this.getContent().equals(other.getContent())) {
            return false;
        }
        if (this.getIsconmlete() == null) {
            if (other.getIsconmlete() != null) {
                return false;
            }
        }
        else if (!this.getIsconmlete().equals(other.getIsconmlete())) {
            return false;
        }
        if (this.getIsincombat() == null) {
            if (other.getIsincombat() != null) {
                return false;
            }
        }
        else if (!this.getIsincombat().equals(other.getIsincombat())) {
            return false;
        }
        if (this.getPalytime() == null) {
            if (other.getPalytime() != null) {
                return false;
            }
        }
        else if (!this.getPalytime().equals(other.getPalytime())) {
            return false;
        }
        if (this.getTaskType() == null) {
            if (other.getTaskType() != null) {
                return false;
            }
        }
        else if (!this.getTaskType().equals(other.getTaskType())) {
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
        if (this.getIdname() == null) {
            if (other.getIdname() != null) {
                return false;
            }
        }
        else if (!this.getIdname().equals(other.getIdname())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getPortranit() == null) ? 0 : this.getPortranit().hashCode());
        result = 31 * result + ((this.getPicNo() == null) ? 0 : this.getPicNo().hashCode());
        result = 31 * result + ((this.getContent() == null) ? 0 : this.getContent().hashCode());
        result = 31 * result + ((this.getIsconmlete() == null) ? 0 : this.getIsconmlete().hashCode());
        result = 31 * result + ((this.getIsincombat() == null) ? 0 : this.getIsincombat().hashCode());
        result = 31 * result + ((this.getPalytime() == null) ? 0 : this.getPalytime().hashCode());
        result = 31 * result + ((this.getTaskType() == null) ? 0 : this.getTaskType().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        result = 31 * result + ((this.getIdname() == null) ? 0 : this.getIdname().hashCode());
        return result;
    }
    
    public NpcDialogue clone() throws CloneNotSupportedException {
        return (NpcDialogue)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        name("name", "name", "VARCHAR", true), 
        portranit("portranit", "portranit", "INTEGER", false), 
        picNo("pic_no", "picNo", "INTEGER", false), 
        content("content", "content", "VARCHAR", false), 
        isconmlete("isconmlete", "isconmlete", "INTEGER", false), 
        isincombat("isincombat", "isincombat", "INTEGER", false), 
        palytime("palytime", "palytime", "INTEGER", false), 
        taskType("task_type", "taskType", "VARCHAR", false), 
        addTime("add_time", "addTime", "TIMESTAMP", false), 
        updateTime("update_time", "updateTime", "TIMESTAMP", false), 
        deleted("deleted", "deleted", "BIT", false), 
        idname("idname", "idname", "VARCHAR", false);
        
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
