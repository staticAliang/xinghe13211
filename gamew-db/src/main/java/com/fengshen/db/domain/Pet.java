package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class Pet implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private Integer index;
    private Integer levelReq;
    private Integer life;
    private Integer mana;
    private Integer speed;
    private Integer phyAttack;
    private Integer magAttack;
    private String polar;
    private String skiils;
    private String zoon;
    private Integer icon;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Boolean deleted;
    private String name;
    private Integer skillRange;
    private String petType;



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
    
    public Integer getIndex() {
        return this.index;
    }
    
    public void setIndex(final Integer index) {
        this.index = index;
    }
    
    public Integer getLevelReq() {
        return this.levelReq;
    }
    
    public void setLevelReq(final Integer levelReq) {
        this.levelReq = levelReq;
    }
    
    public Integer getLife() {
        return this.life;
    }
    
    public void setLife(final Integer life) {
        this.life = life;
    }
    
    public Integer getMana() {
        return this.mana;
    }
    
    public void setMana(final Integer mana) {
        this.mana = mana;
    }
    
    public Integer getSpeed() {
        return this.speed;
    }
    
    public void setSpeed(final Integer speed) {
        this.speed = speed;
    }
    
    public Integer getPhyAttack() {
        return this.phyAttack;
    }
    
    public void setPhyAttack(final Integer phyAttack) {
        this.phyAttack = phyAttack;
    }
    
    public Integer getMagAttack() {
        return this.magAttack;
    }
    
    public void setMagAttack(final Integer magAttack) {
        this.magAttack = magAttack;
    }
    
    public String getPolar() {
        return this.polar;
    }
    
    public void setPolar(final String polar) {
        this.polar = polar;
    }
    
    public String getSkiils() {
        return this.skiils;
    }
    
    public void setSkiils(final String skiils) {
        this.skiils = skiils;
    }
    
    public String getZoon() {
        return this.zoon;
    }
    
    public void setZoon(final String zoon) {
        this.zoon = zoon;
    }
    
    public Integer getIcon() {
        return this.icon;
    }
    
    public void setIcon(final Integer icon) {
        this.icon = icon;
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
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getSkillRange() {
		return skillRange;
	}

	public void setSkillRange(Integer skillRange) {
		this.skillRange = skillRange;
	}

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(this.hashCode());
        sb.append(", IS_DELETED=").append(Pet.IS_DELETED);
        sb.append(", NOT_DELETED=").append(Pet.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", index=").append(this.index);
        sb.append(", levelReq=").append(this.levelReq);
        sb.append(", life=").append(this.life);
        sb.append(", mana=").append(this.mana);
        sb.append(", speed=").append(this.speed);
        sb.append(", phyAttack=").append(this.phyAttack);
        sb.append(", magAttack=").append(this.magAttack);
        sb.append(", polar=").append(this.polar);
        sb.append(", skiils=").append(this.skiils);
        sb.append(", zoon=").append(this.zoon);
        sb.append(", icon=").append(this.icon);
        sb.append(", addTime=").append(this.addTime);
        sb.append(", updateTime=").append(this.updateTime);
        sb.append(", deleted=").append(this.deleted);
        sb.append(", name=").append(this.name);
        sb.append(", pet_type_level=").append(this.petType);
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
        final Pet other = (Pet)that;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (this.getIndex() == null) {
            if (other.getIndex() != null) {
                return false;
            }
        }
        else if (!this.getIndex().equals(other.getIndex())) {
            return false;
        }
        if (this.getLevelReq() == null) {
            if (other.getLevelReq() != null) {
                return false;
            }
        }
        else if (!this.getLevelReq().equals(other.getLevelReq())) {
            return false;
        }
        if (this.getLife() == null) {
            if (other.getLife() != null) {
                return false;
            }
        }
        else if (!this.getLife().equals(other.getLife())) {
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
        if (this.getSpeed() == null) {
            if (other.getSpeed() != null) {
                return false;
            }
        }
        else if (!this.getSpeed().equals(other.getSpeed())) {
            return false;
        }
        if (this.getPhyAttack() == null) {
            if (other.getPhyAttack() != null) {
                return false;
            }
        }
        else if (!this.getPhyAttack().equals(other.getPhyAttack())) {
            return false;
        }
        if (this.getMagAttack() == null) {
            if (other.getMagAttack() != null) {
                return false;
            }
        }
        else if (!this.getMagAttack().equals(other.getMagAttack())) {
            return false;
        }
        if (this.getPolar() == null) {
            if (other.getPolar() != null) {
                return false;
            }
        }
        else if (!this.getPolar().equals(other.getPolar())) {
            return false;
        }
        if (this.getSkiils() == null) {
            if (other.getSkiils() != null) {
                return false;
            }
        }
        else if (!this.getSkiils().equals(other.getSkiils())) {
            return false;
        }
        if (this.getZoon() == null) {
            if (other.getZoon() != null) {
                return false;
            }
        }
        else if (!this.getZoon().equals(other.getZoon())) {
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
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        }
        else if (!this.getName().equals(other.getName())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = 31 * result + ((this.getIndex() == null) ? 0 : this.getIndex().hashCode());
        result = 31 * result + ((this.getLevelReq() == null) ? 0 : this.getLevelReq().hashCode());
        result = 31 * result + ((this.getLife() == null) ? 0 : this.getLife().hashCode());
        result = 31 * result + ((this.getMana() == null) ? 0 : this.getMana().hashCode());
        result = 31 * result + ((this.getSpeed() == null) ? 0 : this.getSpeed().hashCode());
        result = 31 * result + ((this.getPhyAttack() == null) ? 0 : this.getPhyAttack().hashCode());
        result = 31 * result + ((this.getMagAttack() == null) ? 0 : this.getMagAttack().hashCode());
        result = 31 * result + ((this.getPolar() == null) ? 0 : this.getPolar().hashCode());
        result = 31 * result + ((this.getSkiils() == null) ? 0 : this.getSkiils().hashCode());
        result = 31 * result + ((this.getZoon() == null) ? 0 : this.getZoon().hashCode());
        result = 31 * result + ((this.getIcon() == null) ? 0 : this.getIcon().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        return result;
    }
    
    public Pet clone() throws CloneNotSupportedException {
        return (Pet)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        index("index", "index", "INTEGER", true), 
        levelReq("level_req", "levelReq", "INTEGER", false), 
        life("life", "life", "INTEGER", false), 
        mana("mana", "mana", "INTEGER", false), 
        speed("speed", "speed", "INTEGER", false), 
        phyAttack("phy_attack", "phyAttack", "INTEGER", false), 
        magAttack("mag_attack", "magAttack", "INTEGER", false), 
        polar("polar", "polar", "VARCHAR", false), 
        skiils("skiils", "skiils", "VARCHAR", false), 
        zoon("zoon", "zoon", "VARCHAR", false), 
        icon("icon", "icon", "INTEGER", false), 
        addTime("add_time", "addTime", "TIMESTAMP", false), 
        updateTime("update_time", "updateTime", "TIMESTAMP", false), 
        deleted("deleted", "deleted", "BIT", false), 
        name("name", "name", "VARCHAR", true),
        petTypeLevel("pet_type", "petType", "VARCHAR", false),
        skillRange("skill_range", "skillRange", "INTEGER", true);
        
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
