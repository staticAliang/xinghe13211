package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class ShowTasks implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String taskType;
    private String taskDesc;
    private String taskPrompt;
    private Integer refresh;
    private Integer taskEndTime;
    private Integer attrib;
    private String reward;
    private String showName;
    private String tasktaskExtraPara;
    private Integer tasktaskState;
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
    
    public String getTaskType() {
        return this.taskType;
    }
    
    public void setTaskType(final String taskType) {
        this.taskType = taskType;
    }
    
    public String getTaskDesc() {
        return this.taskDesc;
    }
    
    public void setTaskDesc(final String taskDesc) {
        this.taskDesc = taskDesc;
    }
    
    public String getTaskPrompt() {
        return this.taskPrompt;
    }
    
    public void setTaskPrompt(final String taskPrompt) {
        this.taskPrompt = taskPrompt;
    }
    
    public Integer getRefresh() {
        return this.refresh;
    }
    
    public void setRefresh(final Integer refresh) {
        this.refresh = refresh;
    }
    
    public Integer getTaskEndTime() {
        return this.taskEndTime;
    }
    
    public void setTaskEndTime(final Integer taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
    
    public Integer getAttrib() {
        return this.attrib;
    }
    
    public void setAttrib(final Integer attrib) {
        this.attrib = attrib;
    }
    
    public String getReward() {
        return this.reward;
    }
    
    public void setReward(final String reward) {
        this.reward = reward;
    }
    
    public String getShowName() {
        return this.showName;
    }
    
    public void setShowName(final String showName) {
        this.showName = showName;
    }
    
    public String getTasktaskExtraPara() {
        return this.tasktaskExtraPara;
    }
    
    public void setTasktaskExtraPara(final String tasktaskExtraPara) {
        this.tasktaskExtraPara = tasktaskExtraPara;
    }
    
    public Integer getTasktaskState() {
        return this.tasktaskState;
    }
    
    public void setTasktaskState(final Integer tasktaskState) {
        this.tasktaskState = tasktaskState;
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
        sb.append(", IS_DELETED=").append(ShowTasks.IS_DELETED);
        sb.append(", NOT_DELETED=").append(ShowTasks.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", taskType=").append(this.taskType);
        sb.append(", taskDesc=").append(this.taskDesc);
        sb.append(", taskPrompt=").append(this.taskPrompt);
        sb.append(", refresh=").append(this.refresh);
        sb.append(", taskEndTime=").append(this.taskEndTime);
        sb.append(", attrib=").append(this.attrib);
        sb.append(", reward=").append(this.reward);
        sb.append(", showName=").append(this.showName);
        sb.append(", tasktaskExtraPara=").append(this.tasktaskExtraPara);
        sb.append(", tasktaskState=").append(this.tasktaskState);
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
        final ShowTasks other = (ShowTasks)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
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
        if (this.getTaskDesc() == null) {
            if (other.getTaskDesc() != null) {
                return false;
            }
        }
        else if (!this.getTaskDesc().equals(other.getTaskDesc())) {
            return false;
        }
        if (this.getTaskPrompt() == null) {
            if (other.getTaskPrompt() != null) {
                return false;
            }
        }
        else if (!this.getTaskPrompt().equals(other.getTaskPrompt())) {
            return false;
        }
        if (this.getRefresh() == null) {
            if (other.getRefresh() != null) {
                return false;
            }
        }
        else if (!this.getRefresh().equals(other.getRefresh())) {
            return false;
        }
        if (this.getTaskEndTime() == null) {
            if (other.getTaskEndTime() != null) {
                return false;
            }
        }
        else if (!this.getTaskEndTime().equals(other.getTaskEndTime())) {
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
        if (this.getReward() == null) {
            if (other.getReward() != null) {
                return false;
            }
        }
        else if (!this.getReward().equals(other.getReward())) {
            return false;
        }
        if (this.getShowName() == null) {
            if (other.getShowName() != null) {
                return false;
            }
        }
        else if (!this.getShowName().equals(other.getShowName())) {
            return false;
        }
        if (this.getTasktaskExtraPara() == null) {
            if (other.getTasktaskExtraPara() != null) {
                return false;
            }
        }
        else if (!this.getTasktaskExtraPara().equals(other.getTasktaskExtraPara())) {
            return false;
        }
        if (this.getTasktaskState() == null) {
            if (other.getTasktaskState() != null) {
                return false;
            }
        }
        else if (!this.getTasktaskState().equals(other.getTasktaskState())) {
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
        result = 31 * result + ((this.getTaskType() == null) ? 0 : this.getTaskType().hashCode());
        result = 31 * result + ((this.getTaskDesc() == null) ? 0 : this.getTaskDesc().hashCode());
        result = 31 * result + ((this.getTaskPrompt() == null) ? 0 : this.getTaskPrompt().hashCode());
        result = 31 * result + ((this.getRefresh() == null) ? 0 : this.getRefresh().hashCode());
        result = 31 * result + ((this.getTaskEndTime() == null) ? 0 : this.getTaskEndTime().hashCode());
        result = 31 * result + ((this.getAttrib() == null) ? 0 : this.getAttrib().hashCode());
        result = 31 * result + ((this.getReward() == null) ? 0 : this.getReward().hashCode());
        result = 31 * result + ((this.getShowName() == null) ? 0 : this.getShowName().hashCode());
        result = 31 * result + ((this.getTasktaskExtraPara() == null) ? 0 : this.getTasktaskExtraPara().hashCode());
        result = 31 * result + ((this.getTasktaskState() == null) ? 0 : this.getTasktaskState().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public ShowTasks clone() throws CloneNotSupportedException {
        return (ShowTasks)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        taskType("task_type", "taskType", "VARCHAR", false), 
        taskDesc("task_desc", "taskDesc", "VARCHAR", false), 
        taskPrompt("task_prompt", "taskPrompt", "VARCHAR", false), 
        refresh("refresh", "refresh", "INTEGER", false), 
        taskEndTime("task_end_time", "taskEndTime", "INTEGER", false), 
        attrib("attrib", "attrib", "INTEGER", false), 
        reward("reward", "reward", "VARCHAR", false), 
        showName("show_name", "showName", "VARCHAR", false), 
        tasktaskExtraPara("tasktask_extra_para", "tasktaskExtraPara", "VARCHAR", false), 
        tasktaskState("tasktask_state", "tasktaskState", "INTEGER", false), 
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
