package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;
// 传送阵
public class NpcPoint implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String mapname;
    private String doorname;
    private Integer x;
    private Integer y;
    private Integer z;
    private Integer inx;
    private Integer iny;
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

    public String getMapname() {
        return this.mapname;
    }

    public void setMapname(final String mapname) {
        this.mapname = mapname;
    }

    public String getDoorname() {
        return this.doorname;
    }

    public void setDoorname(final String doorname) {
        this.doorname = doorname;
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

    public Integer getZ() {
        return this.z;
    }

    public void setZ(final Integer z) {
        this.z = z;
    }

    public Integer getInx() {
        return this.inx;
    }

    public void setInx(final Integer inx) {
        this.inx = inx;
    }

    public Integer getIny() {
        return this.iny;
    }

    public void setIny(final Integer iny) {
        this.iny = iny;
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
        sb.append(", IS_DELETED=").append(NpcPoint.IS_DELETED);
        sb.append(", NOT_DELETED=").append(NpcPoint.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", mapname=").append(this.mapname);
        sb.append(", doorname=").append(this.doorname);
        sb.append(", x=").append(this.x);
        sb.append(", y=").append(this.y);
        sb.append(", z=").append(this.z);
        sb.append(", inx=").append(this.inx);
        sb.append(", iny=").append(this.iny);
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
        final NpcPoint other = (NpcPoint)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getMapname() == null) {
            if (other.getMapname() != null) {
                return false;
            }
        }
        else if (!this.getMapname().equals(other.getMapname())) {
            return false;
        }
        if (this.getDoorname() == null) {
            if (other.getDoorname() != null) {
                return false;
            }
        }
        else if (!this.getDoorname().equals(other.getDoorname())) {
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
        if (this.getZ() == null) {
            if (other.getZ() != null) {
                return false;
            }
        }
        else if (!this.getZ().equals(other.getZ())) {
            return false;
        }
        if (this.getInx() == null) {
            if (other.getInx() != null) {
                return false;
            }
        }
        else if (!this.getInx().equals(other.getInx())) {
            return false;
        }
        if (this.getIny() == null) {
            if (other.getIny() != null) {
                return false;
            }
        }
        else if (!this.getIny().equals(other.getIny())) {
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
        result = 31 * result + ((this.getMapname() == null) ? 0 : this.getMapname().hashCode());
        result = 31 * result + ((this.getDoorname() == null) ? 0 : this.getDoorname().hashCode());
        result = 31 * result + ((this.getX() == null) ? 0 : this.getX().hashCode());
        result = 31 * result + ((this.getY() == null) ? 0 : this.getY().hashCode());
        result = 31 * result + ((this.getZ() == null) ? 0 : this.getZ().hashCode());
        result = 31 * result + ((this.getInx() == null) ? 0 : this.getInx().hashCode());
        result = 31 * result + ((this.getIny() == null) ? 0 : this.getIny().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }

    public NpcPoint clone() throws CloneNotSupportedException {
        return (NpcPoint)super.clone();
    }

    public enum Column
    {
        id("id", "id", "INTEGER", false),
        mapname("mapname", "mapname", "VARCHAR", false),
        doorname("doorname", "doorname", "VARCHAR", false),
        x("x", "x", "INTEGER", false),
        y("y", "y", "INTEGER", false),
        z("z", "z", "INTEGER", false),
        inx("inx", "inx", "INTEGER", false),
        iny("iny", "iny", "INTEGER", false),
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