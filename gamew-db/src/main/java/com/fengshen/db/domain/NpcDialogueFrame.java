package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class NpcDialogueFrame implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private Integer portrait;
    private Integer picNo;
    private String content;
    private String secretKey;
    private String name;
    private Integer attrib;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTimes;
    private Boolean deleted;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer idname;
    private String next;
    private String currentTask;
    private String uncontent;
    private String zhuangbei;
    private Integer jingyan;
    private Integer money;
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
    
    public Integer getPortrait() {
        return this.portrait;
    }
    
    public void setPortrait(final Integer portrait) {
        this.portrait = portrait;
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
    
    public String getSecretKey() {
        return this.secretKey;
    }
    
    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getAttrib() {
        return this.attrib;
    }
    
    public void setAttrib(final Integer attrib) {
        this.attrib = attrib;
    }
    
    public LocalDateTime getAddTime() {
        return this.addTime;
    }
    
    public void setAddTime(final LocalDateTime addTime) {
        this.addTime = addTime;
    }
    
    public LocalDateTime getUpdateTimes() {
        return this.updateTimes;
    }
    
    public void setUpdateTimes(final LocalDateTime updateTimes) {
        this.updateTimes = updateTimes;
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
    
    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(final LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    public Integer getIdname() {
        return this.idname;
    }
    
    public void setIdname(final Integer idname) {
        this.idname = idname;
    }
    
    public String getNext() {
        return this.next;
    }
    
    public void setNext(final String next) {
        this.next = next;
    }
    
    public String getCurrentTask() {
        return this.currentTask;
    }
    
    public void setCurrentTask(final String currentTask) {
        this.currentTask = currentTask;
    }
    
    public String getUncontent() {
        return this.uncontent;
    }
    
    public void setUncontent(final String uncontent) {
        this.uncontent = uncontent;
    }
    
    public String getZhuangbei() {
        return this.zhuangbei;
    }
    
    public void setZhuangbei(final String zhuangbei) {
        this.zhuangbei = zhuangbei;
    }
    
    public Integer getJingyan() {
        return this.jingyan;
    }
    
    public void setJingyan(final Integer jingyan) {
        this.jingyan = jingyan;
    }
    
    public Integer getMoney() {
        return this.money;
    }
    
    public void setMoney(final Integer money) {
        this.money = money;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", IS_DELETED=").append(NpcDialogueFrame.IS_DELETED);
        sb.append(", NOT_DELETED=").append(NpcDialogueFrame.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", portrait=").append(this.portrait);
        sb.append(", picNo=").append(this.picNo);
        sb.append(", content=").append(this.content);
        sb.append(", secretKey=").append(this.secretKey);
        sb.append(", name=").append(this.name);
        sb.append(", attrib=").append(this.attrib);
        sb.append(", addTime=").append(this.addTime);
        sb.append(", updateTimes=").append(this.updateTimes);
        sb.append(", deleted=").append(this.deleted);
        sb.append(", updateTime=").append(this.updateTime);
        sb.append(", idname=").append(this.idname);
        sb.append(", next=").append(this.next);
        sb.append(", currentTask=").append(this.currentTask);
        sb.append(", uncontent=").append(this.uncontent);
        sb.append(", zhuangbei=").append(this.zhuangbei);
        sb.append(", jingyan=").append(this.jingyan);
        sb.append(", money=").append(this.money);
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
        final NpcDialogueFrame other = (NpcDialogueFrame)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getPortrait() == null) {
            if (other.getPortrait() != null) {
                return false;
            }
        }
        else if (!this.getPortrait().equals(other.getPortrait())) {
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
        if (this.getSecretKey() == null) {
            if (other.getSecretKey() != null) {
                return false;
            }
        }
        else if (!this.getSecretKey().equals(other.getSecretKey())) {
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
        if (this.getAttrib() == null) {
            if (other.getAttrib() != null) {
                return false;
            }
        }
        else if (!this.getAttrib().equals(other.getAttrib())) {
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
        if (this.getUpdateTimes() == null) {
            if (other.getUpdateTimes() != null) {
                return false;
            }
        }
        else if (!this.getUpdateTimes().equals(other.getUpdateTimes())) {
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
        if (this.getUpdateTime() == null) {
            if (other.getUpdateTime() != null) {
                return false;
            }
        }
        else if (!this.getUpdateTime().equals(other.getUpdateTime())) {
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
        if (this.getNext() == null) {
            if (other.getNext() != null) {
                return false;
            }
        }
        else if (!this.getNext().equals(other.getNext())) {
            return false;
        }
        if (this.getCurrentTask() == null) {
            if (other.getCurrentTask() != null) {
                return false;
            }
        }
        else if (!this.getCurrentTask().equals(other.getCurrentTask())) {
            return false;
        }
        if (this.getUncontent() == null) {
            if (other.getUncontent() != null) {
                return false;
            }
        }
        else if (!this.getUncontent().equals(other.getUncontent())) {
            return false;
        }
        if (this.getZhuangbei() == null) {
            if (other.getZhuangbei() != null) {
                return false;
            }
        }
        else if (!this.getZhuangbei().equals(other.getZhuangbei())) {
            return false;
        }
        if (this.getJingyan() == null) {
            if (other.getJingyan() != null) {
                return false;
            }
        }
        else if (!this.getJingyan().equals(other.getJingyan())) {
            return false;
        }
        if (this.getMoney() == null) {
            if (other.getMoney() != null) {
                return false;
            }
        }
        else if (!this.getMoney().equals(other.getMoney())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getPortrait() == null) ? 0 : this.getPortrait().hashCode());
        result = 31 * result + ((this.getPicNo() == null) ? 0 : this.getPicNo().hashCode());
        result = 31 * result + ((this.getContent() == null) ? 0 : this.getContent().hashCode());
        result = 31 * result + ((this.getSecretKey() == null) ? 0 : this.getSecretKey().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getAttrib() == null) ? 0 : this.getAttrib().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTimes() == null) ? 0 : this.getUpdateTimes().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getIdname() == null) ? 0 : this.getIdname().hashCode());
        result = 31 * result + ((this.getNext() == null) ? 0 : this.getNext().hashCode());
        result = 31 * result + ((this.getCurrentTask() == null) ? 0 : this.getCurrentTask().hashCode());
        result = 31 * result + ((this.getUncontent() == null) ? 0 : this.getUncontent().hashCode());
        result = 31 * result + ((this.getZhuangbei() == null) ? 0 : this.getZhuangbei().hashCode());
        result = 31 * result + ((this.getJingyan() == null) ? 0 : this.getJingyan().hashCode());
        result = 31 * result + ((this.getMoney() == null) ? 0 : this.getMoney().hashCode());
        return result;
    }
    
    public NpcDialogueFrame clone() throws CloneNotSupportedException {
        return (NpcDialogueFrame)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        portrait("portrait", "portrait", "INTEGER", false), 
        picNo("pic_no", "picNo", "INTEGER", false), 
        content("content", "content", "VARCHAR", false), 
        secretKey("secret_key", "secretKey", "VARCHAR", false), 
        name("name", "name", "VARCHAR", true), 
        attrib("attrib", "attrib", "INTEGER", false), 
        addTime("add_time", "addTime", "TIMESTAMP", false), 
        updateTimes("update_times", "updateTimes", "TIMESTAMP", false), 
        deleted("deleted", "deleted", "BIT", false), 
        updateTime("update_time", "updateTime", "TIMESTAMP", false), 
        idname("idname", "idname", "INTEGER", false), 
        next("next", "next", "VARCHAR", true), 
        currentTask("current_task", "currentTask", "VARCHAR", false), 
        uncontent("uncontent", "uncontent", "VARCHAR", false), 
        zhuangbei("zhuangbei", "zhuangbei", "VARCHAR", false), 
        jingyan("jingyan", "jingyan", "INTEGER", false), 
        money("money", "money", "INTEGER", false);
        
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
