package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class Pets implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String ownerid;
    private String petid;
    private String nickname;
    private String name;
    private Integer horsetype;
    private Integer type;
    private Integer level;
    private Integer liliang;
    private Integer minjie;
    private Integer lingli;
    private Integer tili;
    private Integer dianhualx;
    private Integer dianhuazd;
    private Integer dianhuazx;
    private Integer yuhualx;
    private Integer yuhuazd;
    private Integer yuhuazx;
    private Integer cwjyzx;
    private Integer cwjyzd;
    private Integer feisheng;
    private Integer fsudu;
    private Integer qhcwWg;
    private Integer qhcwFg;
    private Integer cwXiangxing;
    private Integer cwWuxue;
    private String cwIcon;
    private Integer cwXinfa;
    private Integer cwQinmi;
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
    
    public String getPetid() {
        return this.petid;
    }
    
    public void setPetid(final String petid) {
        this.petid = petid;
    }
    
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getHorsetype() {
        return this.horsetype;
    }
    
    public void setHorsetype(final Integer horsetype) {
        this.horsetype = horsetype;
    }
    
    public Integer getType() {
        return this.type;
    }
    
    public void setType(final Integer type) {
        this.type = type;
    }
    
    public Integer getLevel() {
        return this.level;
    }
    
    public void setLevel(final Integer level) {
        this.level = level;
    }
    
    public Integer getLiliang() {
        return this.liliang;
    }
    
    public void setLiliang(final Integer liliang) {
        this.liliang = liliang;
    }
    
    public Integer getMinjie() {
        return this.minjie;
    }
    
    public void setMinjie(final Integer minjie) {
        this.minjie = minjie;
    }
    
    public Integer getLingli() {
        return this.lingli;
    }
    
    public void setLingli(final Integer lingli) {
        this.lingli = lingli;
    }
    
    public Integer getTili() {
        return this.tili;
    }
    
    public void setTili(final Integer tili) {
        this.tili = tili;
    }
    
    public Integer getDianhualx() {
        return this.dianhualx;
    }
    
    public void setDianhualx(final Integer dianhualx) {
        this.dianhualx = dianhualx;
    }
    
    public Integer getDianhuazd() {
        return this.dianhuazd;
    }
    
    public void setDianhuazd(final Integer dianhuazd) {
        this.dianhuazd = dianhuazd;
    }
    
    public Integer getDianhuazx() {
        return this.dianhuazx;
    }
    
    public void setDianhuazx(final Integer dianhuazx) {
        this.dianhuazx = dianhuazx;
    }
    
    public Integer getYuhualx() {
        return this.yuhualx;
    }
    
    public void setYuhualx(final Integer yuhualx) {
        this.yuhualx = yuhualx;
    }
    
    public Integer getYuhuazd() {
        return this.yuhuazd;
    }
    
    public void setYuhuazd(final Integer yuhuazd) {
        this.yuhuazd = yuhuazd;
    }
    
    public Integer getYuhuazx() {
        return this.yuhuazx;
    }
    
    public void setYuhuazx(final Integer yuhuazx) {
        this.yuhuazx = yuhuazx;
    }
    
    public Integer getCwjyzx() {
        return this.cwjyzx;
    }
    
    public void setCwjyzx(final Integer cwjyzx) {
        this.cwjyzx = cwjyzx;
    }
    
    public Integer getCwjyzd() {
        return this.cwjyzd;
    }
    
    public void setCwjyzd(final Integer cwjyzd) {
        this.cwjyzd = cwjyzd;
    }
    
    public Integer getFeisheng() {
        return this.feisheng;
    }
    
    public void setFeisheng(final Integer feisheng) {
        this.feisheng = feisheng;
    }
    
    public Integer getFsudu() {
        return this.fsudu;
    }
    
    public void setFsudu(final Integer fsudu) {
        this.fsudu = fsudu;
    }
    
    public Integer getQhcwWg() {
        return this.qhcwWg;
    }
    
    public void setQhcwWg(final Integer qhcwWg) {
        this.qhcwWg = qhcwWg;
    }
    
    public Integer getQhcwFg() {
        return this.qhcwFg;
    }
    
    public void setQhcwFg(final Integer qhcwFg) {
        this.qhcwFg = qhcwFg;
    }
    
    public Integer getCwXiangxing() {
        return this.cwXiangxing;
    }
    
    public void setCwXiangxing(final Integer cwXiangxing) {
        this.cwXiangxing = cwXiangxing;
    }
    
    public Integer getCwWuxue() {
        return this.cwWuxue;
    }
    
    public void setCwWuxue(final Integer cwWuxue) {
        this.cwWuxue = cwWuxue;
    }
    
    public String getCwIcon() {
        return this.cwIcon;
    }
    
    public void setCwIcon(final String cwIcon) {
        this.cwIcon = cwIcon;
    }
    
    public Integer getCwXinfa() {
        return this.cwXinfa;
    }
    
    public void setCwXinfa(final Integer cwXinfa) {
        this.cwXinfa = cwXinfa;
    }
    
    public Integer getCwQinmi() {
        return this.cwQinmi;
    }
    
    public void setCwQinmi(final Integer cwQinmi) {
        this.cwQinmi = cwQinmi;
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
        sb.append(", IS_DELETED=").append(Pets.IS_DELETED);
        sb.append(", NOT_DELETED=").append(Pets.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", ownerid=").append(this.ownerid);
        sb.append(", petid=").append(this.petid);
        sb.append(", nickname=").append(this.nickname);
        sb.append(", name=").append(this.name);
        sb.append(", horsetype=").append(this.horsetype);
        sb.append(", type=").append(this.type);
        sb.append(", level=").append(this.level);
        sb.append(", liliang=").append(this.liliang);
        sb.append(", minjie=").append(this.minjie);
        sb.append(", lingli=").append(this.lingli);
        sb.append(", tili=").append(this.tili);
        sb.append(", dianhualx=").append(this.dianhualx);
        sb.append(", dianhuazd=").append(this.dianhuazd);
        sb.append(", dianhuazx=").append(this.dianhuazx);
        sb.append(", yuhualx=").append(this.yuhualx);
        sb.append(", yuhuazd=").append(this.yuhuazd);
        sb.append(", yuhuazx=").append(this.yuhuazx);
        sb.append(", cwjyzx=").append(this.cwjyzx);
        sb.append(", cwjyzd=").append(this.cwjyzd);
        sb.append(", feisheng=").append(this.feisheng);
        sb.append(", fsudu=").append(this.fsudu);
        sb.append(", qhcwWg=").append(this.qhcwWg);
        sb.append(", qhcwFg=").append(this.qhcwFg);
        sb.append(", cwXiangxing=").append(this.cwXiangxing);
        sb.append(", cwWuxue=").append(this.cwWuxue);
        sb.append(", cwIcon=").append(this.cwIcon);
        sb.append(", cwXinfa=").append(this.cwXinfa);
        sb.append(", cwQinmi=").append(this.cwQinmi);
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
        final Pets other = (Pets)that;
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
        if (this.getPetid() == null) {
            if (other.getPetid() != null) {
                return false;
            }
        }
        else if (!this.getPetid().equals(other.getPetid())) {
            return false;
        }
        if (this.getNickname() == null) {
            if (other.getNickname() != null) {
                return false;
            }
        }
        else if (!this.getNickname().equals(other.getNickname())) {
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
        if (this.getHorsetype() == null) {
            if (other.getHorsetype() != null) {
                return false;
            }
        }
        else if (!this.getHorsetype().equals(other.getHorsetype())) {
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
        if (this.getLevel() == null) {
            if (other.getLevel() != null) {
                return false;
            }
        }
        else if (!this.getLevel().equals(other.getLevel())) {
            return false;
        }
        if (this.getLiliang() == null) {
            if (other.getLiliang() != null) {
                return false;
            }
        }
        else if (!this.getLiliang().equals(other.getLiliang())) {
            return false;
        }
        if (this.getMinjie() == null) {
            if (other.getMinjie() != null) {
                return false;
            }
        }
        else if (!this.getMinjie().equals(other.getMinjie())) {
            return false;
        }
        if (this.getLingli() == null) {
            if (other.getLingli() != null) {
                return false;
            }
        }
        else if (!this.getLingli().equals(other.getLingli())) {
            return false;
        }
        if (this.getTili() == null) {
            if (other.getTili() != null) {
                return false;
            }
        }
        else if (!this.getTili().equals(other.getTili())) {
            return false;
        }
        if (this.getDianhualx() == null) {
            if (other.getDianhualx() != null) {
                return false;
            }
        }
        else if (!this.getDianhualx().equals(other.getDianhualx())) {
            return false;
        }
        if (this.getDianhuazd() == null) {
            if (other.getDianhuazd() != null) {
                return false;
            }
        }
        else if (!this.getDianhuazd().equals(other.getDianhuazd())) {
            return false;
        }
        if (this.getDianhuazx() == null) {
            if (other.getDianhuazx() != null) {
                return false;
            }
        }
        else if (!this.getDianhuazx().equals(other.getDianhuazx())) {
            return false;
        }
        if (this.getYuhualx() == null) {
            if (other.getYuhualx() != null) {
                return false;
            }
        }
        else if (!this.getYuhualx().equals(other.getYuhualx())) {
            return false;
        }
        if (this.getYuhuazd() == null) {
            if (other.getYuhuazd() != null) {
                return false;
            }
        }
        else if (!this.getYuhuazd().equals(other.getYuhuazd())) {
            return false;
        }
        if (this.getYuhuazx() == null) {
            if (other.getYuhuazx() != null) {
                return false;
            }
        }
        else if (!this.getYuhuazx().equals(other.getYuhuazx())) {
            return false;
        }
        if (this.getCwjyzx() == null) {
            if (other.getCwjyzx() != null) {
                return false;
            }
        }
        else if (!this.getCwjyzx().equals(other.getCwjyzx())) {
            return false;
        }
        if (this.getCwjyzd() == null) {
            if (other.getCwjyzd() != null) {
                return false;
            }
        }
        else if (!this.getCwjyzd().equals(other.getCwjyzd())) {
            return false;
        }
        if (this.getFeisheng() == null) {
            if (other.getFeisheng() != null) {
                return false;
            }
        }
        else if (!this.getFeisheng().equals(other.getFeisheng())) {
            return false;
        }
        if (this.getFsudu() == null) {
            if (other.getFsudu() != null) {
                return false;
            }
        }
        else if (!this.getFsudu().equals(other.getFsudu())) {
            return false;
        }
        if (this.getQhcwWg() == null) {
            if (other.getQhcwWg() != null) {
                return false;
            }
        }
        else if (!this.getQhcwWg().equals(other.getQhcwWg())) {
            return false;
        }
        if (this.getQhcwFg() == null) {
            if (other.getQhcwFg() != null) {
                return false;
            }
        }
        else if (!this.getQhcwFg().equals(other.getQhcwFg())) {
            return false;
        }
        if (this.getCwXiangxing() == null) {
            if (other.getCwXiangxing() != null) {
                return false;
            }
        }
        else if (!this.getCwXiangxing().equals(other.getCwXiangxing())) {
            return false;
        }
        if (this.getCwWuxue() == null) {
            if (other.getCwWuxue() != null) {
                return false;
            }
        }
        else if (!this.getCwWuxue().equals(other.getCwWuxue())) {
            return false;
        }
        if (this.getCwIcon() == null) {
            if (other.getCwIcon() != null) {
                return false;
            }
        }
        else if (!this.getCwIcon().equals(other.getCwIcon())) {
            return false;
        }
        if (this.getCwXinfa() == null) {
            if (other.getCwXinfa() != null) {
                return false;
            }
        }
        else if (!this.getCwXinfa().equals(other.getCwXinfa())) {
            return false;
        }
        if (this.getCwQinmi() == null) {
            if (other.getCwQinmi() != null) {
                return false;
            }
        }
        else if (!this.getCwQinmi().equals(other.getCwQinmi())) {
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
        result = 31 * result + ((this.getPetid() == null) ? 0 : this.getPetid().hashCode());
        result = 31 * result + ((this.getNickname() == null) ? 0 : this.getNickname().hashCode());
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getHorsetype() == null) ? 0 : this.getHorsetype().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getLevel() == null) ? 0 : this.getLevel().hashCode());
        result = 31 * result + ((this.getLiliang() == null) ? 0 : this.getLiliang().hashCode());
        result = 31 * result + ((this.getMinjie() == null) ? 0 : this.getMinjie().hashCode());
        result = 31 * result + ((this.getLingli() == null) ? 0 : this.getLingli().hashCode());
        result = 31 * result + ((this.getTili() == null) ? 0 : this.getTili().hashCode());
        result = 31 * result + ((this.getDianhualx() == null) ? 0 : this.getDianhualx().hashCode());
        result = 31 * result + ((this.getDianhuazd() == null) ? 0 : this.getDianhuazd().hashCode());
        result = 31 * result + ((this.getDianhuazx() == null) ? 0 : this.getDianhuazx().hashCode());
        result = 31 * result + ((this.getYuhualx() == null) ? 0 : this.getYuhualx().hashCode());
        result = 31 * result + ((this.getYuhuazd() == null) ? 0 : this.getYuhuazd().hashCode());
        result = 31 * result + ((this.getYuhuazx() == null) ? 0 : this.getYuhuazx().hashCode());
        result = 31 * result + ((this.getCwjyzx() == null) ? 0 : this.getCwjyzx().hashCode());
        result = 31 * result + ((this.getCwjyzd() == null) ? 0 : this.getCwjyzd().hashCode());
        result = 31 * result + ((this.getFeisheng() == null) ? 0 : this.getFeisheng().hashCode());
        result = 31 * result + ((this.getFsudu() == null) ? 0 : this.getFsudu().hashCode());
        result = 31 * result + ((this.getQhcwWg() == null) ? 0 : this.getQhcwWg().hashCode());
        result = 31 * result + ((this.getQhcwFg() == null) ? 0 : this.getQhcwFg().hashCode());
        result = 31 * result + ((this.getCwXiangxing() == null) ? 0 : this.getCwXiangxing().hashCode());
        result = 31 * result + ((this.getCwWuxue() == null) ? 0 : this.getCwWuxue().hashCode());
        result = 31 * result + ((this.getCwIcon() == null) ? 0 : this.getCwIcon().hashCode());
        result = 31 * result + ((this.getCwXinfa() == null) ? 0 : this.getCwXinfa().hashCode());
        result = 31 * result + ((this.getCwQinmi() == null) ? 0 : this.getCwQinmi().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }
    
    public Pets clone() throws CloneNotSupportedException {
        return (Pets)super.clone();
    }
    
    public enum Column
    {
        id("id", "id", "INTEGER", false), 
        ownerid("ownerid", "ownerid", "VARCHAR", false), 
        petid("petid", "petid", "VARCHAR", false), 
        nickname("nickname", "nickname", "VARCHAR", false), 
        name("name", "name", "VARCHAR", true), 
        horsetype("horsetype", "horsetype", "INTEGER", false), 
        type("type", "type", "INTEGER", true), 
        level("level", "level", "INTEGER", true), 
        liliang("liliang", "liliang", "INTEGER", false), 
        minjie("minjie", "minjie", "INTEGER", false), 
        lingli("lingli", "lingli", "INTEGER", false), 
        tili("tili", "tili", "INTEGER", false), 
        dianhualx("dianhualx", "dianhualx", "INTEGER", false), 
        dianhuazd("dianhuazd", "dianhuazd", "INTEGER", false), 
        dianhuazx("dianhuazx", "dianhuazx", "INTEGER", false), 
        yuhualx("yuhualx", "yuhualx", "INTEGER", false), 
        yuhuazd("yuhuazd", "yuhuazd", "INTEGER", false), 
        yuhuazx("yuhuazx", "yuhuazx", "INTEGER", false), 
        cwjyzx("cwjyzx", "cwjyzx", "INTEGER", false), 
        cwjyzd("cwjyzd", "cwjyzd", "INTEGER", false), 
        feisheng("feisheng", "feisheng", "INTEGER", false), 
        fsudu("fsudu", "fsudu", "INTEGER", false), 
        qhcwWg("qhcw_wg", "qhcwWg", "INTEGER", false), 
        qhcwFg("qhcw_fg", "qhcwFg", "INTEGER", false), 
        cwXiangxing("cw_xiangxing", "cwXiangxing", "INTEGER", false), 
        cwWuxue("cw_wuxue", "cwWuxue", "INTEGER", false), 
        cwIcon("cw_icon", "cwIcon", "VARCHAR", false), 
        cwXinfa("cw_xinfa", "cwXinfa", "INTEGER", false), 
        cwQinmi("cw_qinmi", "cwQinmi", "INTEGER", false), 
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
