package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class Map implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String name;
    private Integer mapId;
    private Integer x;
    private Integer y;
    private String icon;
    private Integer monsterLevel;
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

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getMapId() {
        return this.mapId;
    }

    public void setMapId(final Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getX() {
        return this.x;
    }

    public void setX(final Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return this.y;
    }

    public void setY(final Integer y) {
        this.y = y;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Integer getMonsterLevel() {
        return this.monsterLevel;
    }

    public void setMonsterLevel(final Integer monsterLevel) {
        this.monsterLevel = monsterLevel;
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
        sb.append(", IS_DELETED=").append(Map.IS_DELETED);
        sb.append(", NOT_DELETED=").append(Map.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", name=").append(this.name);
        sb.append(", mapId=").append(this.mapId);
        sb.append(", x=").append(this.x);
        sb.append(", y=").append(this.y);
        sb.append(", icon=").append(this.icon);
        sb.append(", monsterLevel=").append(this.monsterLevel);
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
        final Map other = (Map)that;
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
        if (this.getMapId() == null) {
            if (other.getMapId() != null) {
                return false;
            }
        }
        else if (!this.getMapId().equals(other.getMapId())) {
            return false;
        }
        if (this.getX() == null) {
            if (other.getX() != null) {
                return false;
            }
        }
        else if (!this.getX().equals(other.getX())) {
            return false;
        }
        if (this.getY() == null) {
            if (other.getY() != null) {
                return false;
            }
        }
        else if (!this.getY().equals(other.getY())) {
            return false;
        }
        if (this.getIcon() == null) {
            if (other.getIcon() != null) {
                return false;
            }
        }
        else if (!this.getIcon().equals(other.getIcon())) {
            return false;
        }
        if (this.getMonsterLevel() == null) {
            if (other.getMonsterLevel() != null) {
                return false;
            }
        }
        else if (!this.getMonsterLevel().equals(other.getMonsterLevel())) {
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
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getMapId() == null) ? 0 : this.getMapId().hashCode());
        result = 31 * result + ((this.getX() == null) ? 0 : this.getX().hashCode());
        result = 31 * result + ((this.getY() == null) ? 0 : this.getY().hashCode());
        result = 31 * result + ((this.getIcon() == null) ? 0 : this.getIcon().hashCode());
        result = 31 * result + ((this.getMonsterLevel() == null) ? 0 : this.getMonsterLevel().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }

    public Map clone() throws CloneNotSupportedException {
        return (Map)super.clone();
    }

    public enum Column
    {
        id("id", "id", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        mapId("map_id", "mapId", "INTEGER", false),
        x("x", "x", "INTEGER", false),
        y("y", "y", "INTEGER", false),
        icon("icon", "icon", "VARCHAR", false),
        monsterLevel("monster_level", "monsterLevel", "INTEGER", false),
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