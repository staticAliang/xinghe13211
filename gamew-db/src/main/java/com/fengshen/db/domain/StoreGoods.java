package com.fengshen.db.domain;

import java.io.*;
import java.time.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.springframework.format.annotation.*;
import java.util.*;

public class StoreGoods implements Cloneable, Serializable
{
    public static final Boolean IS_DELETED;
    public static final Boolean NOT_DELETED;
    private Integer id;
    private String name;
    private String barcode;
    private Integer forSale;
    private Integer showPos;
    private Integer rpos;
    private Integer saleQuota;
    private Integer recommend;
    private Integer coin;
    private Integer discount;
    private Integer type;
    private Integer quotaLimit;
    private Integer mustVip;
    private Integer isGift;
    private Integer followPetType;
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

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    public Integer getForSale() {
        return this.forSale;
    }

    public void setForSale(final Integer forSale) {
        this.forSale = forSale;
    }

    public Integer getShowPos() {
        return this.showPos;
    }

    public void setShowPos(final Integer showPos) {
        this.showPos = showPos;
    }

    public Integer getRpos() {
        return this.rpos;
    }

    public void setRpos(final Integer rpos) {
        this.rpos = rpos;
    }

    public Integer getSaleQuota() {
        return this.saleQuota;
    }

    public void setSaleQuota(final Integer saleQuota) {
        this.saleQuota = saleQuota;
    }

    public Integer getRecommend() {
        return this.recommend;
    }

    public void setRecommend(final Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getCoin() {
        return this.coin;
    }

    public void setCoin(final Integer coin) {
        this.coin = coin;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public void setDiscount(final Integer discount) {
        this.discount = discount;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public Integer getQuotaLimit() {
        return this.quotaLimit;
    }

    public void setQuotaLimit(final Integer quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    public Integer getMustVip() {
        return this.mustVip;
    }

    public void setMustVip(final Integer mustVip) {
        this.mustVip = mustVip;
    }

    public Integer getIsGift() {
        return this.isGift;
    }

    public void setIsGift(final Integer isGift) {
        this.isGift = isGift;
    }

    public Integer getFollowPetType() {
        return this.followPetType;
    }

    public void setFollowPetType(final Integer followPetType) {
        this.followPetType = followPetType;
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
        sb.append(", IS_DELETED=").append(StoreGoods.IS_DELETED);
        sb.append(", NOT_DELETED=").append(StoreGoods.NOT_DELETED);
        sb.append(", id=").append(this.id);
        sb.append(", name=").append(this.name);
        sb.append(", barcode=").append(this.barcode);
        sb.append(", forSale=").append(this.forSale);
        sb.append(", showPos=").append(this.showPos);
        sb.append(", rpos=").append(this.rpos);
        sb.append(", saleQuota=").append(this.saleQuota);
        sb.append(", recommend=").append(this.recommend);
        sb.append(", coin=").append(this.coin);
        sb.append(", discount=").append(this.discount);
        sb.append(", type=").append(this.type);
        sb.append(", quotaLimit=").append(this.quotaLimit);
        sb.append(", mustVip=").append(this.mustVip);
        sb.append(", isGift=").append(this.isGift);
        sb.append(", followPetType=").append(this.followPetType);
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
        final StoreGoods other = (StoreGoods)that;
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
        if (this.getBarcode() == null) {
            if (other.getBarcode() != null) {
                return false;
            }
        }
        else if (!this.getBarcode().equals(other.getBarcode())) {
            return false;
        }
        if (this.getForSale() == null) {
            if (other.getForSale() != null) {
                return false;
            }
        }
        else if (!this.getForSale().equals(other.getForSale())) {
            return false;
        }
        if (this.getShowPos() == null) {
            if (other.getShowPos() != null) {
                return false;
            }
        }
        else if (!this.getShowPos().equals(other.getShowPos())) {
            return false;
        }
        if (this.getRpos() == null) {
            if (other.getRpos() != null) {
                return false;
            }
        }
        else if (!this.getRpos().equals(other.getRpos())) {
            return false;
        }
        if (this.getSaleQuota() == null) {
            if (other.getSaleQuota() != null) {
                return false;
            }
        }
        else if (!this.getSaleQuota().equals(other.getSaleQuota())) {
            return false;
        }
        if (this.getRecommend() == null) {
            if (other.getRecommend() != null) {
                return false;
            }
        }
        else if (!this.getRecommend().equals(other.getRecommend())) {
            return false;
        }
        if (this.getCoin() == null) {
            if (other.getCoin() != null) {
                return false;
            }
        }
        else if (!this.getCoin().equals(other.getCoin())) {
            return false;
        }
        if (this.getDiscount() == null) {
            if (other.getDiscount() != null) {
                return false;
            }
        }
        else if (!this.getDiscount().equals(other.getDiscount())) {
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
        if (this.getQuotaLimit() == null) {
            if (other.getQuotaLimit() != null) {
                return false;
            }
        }
        else if (!this.getQuotaLimit().equals(other.getQuotaLimit())) {
            return false;
        }
        if (this.getMustVip() == null) {
            if (other.getMustVip() != null) {
                return false;
            }
        }
        else if (!this.getMustVip().equals(other.getMustVip())) {
            return false;
        }
        if (this.getIsGift() == null) {
            if (other.getIsGift() != null) {
                return false;
            }
        }
        else if (!this.getIsGift().equals(other.getIsGift())) {
            return false;
        }
        if (this.getFollowPetType() == null) {
            if (other.getFollowPetType() != null) {
                return false;
            }
        }
        else if (!this.getFollowPetType().equals(other.getFollowPetType())) {
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
        result = 31 * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = 31 * result + ((this.getBarcode() == null) ? 0 : this.getBarcode().hashCode());
        result = 31 * result + ((this.getForSale() == null) ? 0 : this.getForSale().hashCode());
        result = 31 * result + ((this.getShowPos() == null) ? 0 : this.getShowPos().hashCode());
        result = 31 * result + ((this.getRpos() == null) ? 0 : this.getRpos().hashCode());
        result = 31 * result + ((this.getSaleQuota() == null) ? 0 : this.getSaleQuota().hashCode());
        result = 31 * result + ((this.getRecommend() == null) ? 0 : this.getRecommend().hashCode());
        result = 31 * result + ((this.getCoin() == null) ? 0 : this.getCoin().hashCode());
        result = 31 * result + ((this.getDiscount() == null) ? 0 : this.getDiscount().hashCode());
        result = 31 * result + ((this.getType() == null) ? 0 : this.getType().hashCode());
        result = 31 * result + ((this.getQuotaLimit() == null) ? 0 : this.getQuotaLimit().hashCode());
        result = 31 * result + ((this.getMustVip() == null) ? 0 : this.getMustVip().hashCode());
        result = 31 * result + ((this.getIsGift() == null) ? 0 : this.getIsGift().hashCode());
        result = 31 * result + ((this.getFollowPetType() == null) ? 0 : this.getFollowPetType().hashCode());
        result = 31 * result + ((this.getAddTime() == null) ? 0 : this.getAddTime().hashCode());
        result = 31 * result + ((this.getUpdateTime() == null) ? 0 : this.getUpdateTime().hashCode());
        result = 31 * result + ((this.getDeleted() == null) ? 0 : this.getDeleted().hashCode());
        return result;
    }

    public StoreGoods clone() throws CloneNotSupportedException {
        return (StoreGoods)super.clone();
    }

    public enum Column
    {
        id("id", "id", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        barcode("barcode", "barcode", "VARCHAR", false),
        forSale("for_sale", "forSale", "INTEGER", false),
        showPos("show_pos", "showPos", "INTEGER", false),
        rpos("rpos", "rpos", "INTEGER", false),
        saleQuota("sale_quota", "saleQuota", "INTEGER", false),
        recommend("recommend", "recommend", "INTEGER", false),
        coin("coin", "coin", "INTEGER", false),
        discount("discount", "discount", "INTEGER", false),
        type("type", "type", "INTEGER", true),
        quotaLimit("quota_limit", "quotaLimit", "INTEGER", false),
        mustVip("must_vip", "mustVip", "INTEGER", false),
        isGift("is_gift", "isGift", "INTEGER", false),
        followPetType("follow_pet_type", "followPetType", "INTEGER", false),
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