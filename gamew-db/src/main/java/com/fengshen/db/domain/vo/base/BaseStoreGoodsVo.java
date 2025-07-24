package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseStoreGoodsVo
{
    public Integer id;
    public String name;
    public String barcode;
    public Integer forSale;
    public Integer showPos;
    public Integer rpos;
    public Integer saleQuota;
    public Integer recommend;
    public Integer coin;
    public Integer discount;
    public Integer type;
    public Integer quotaLimit;
    public Integer mustVip;
    public Integer isGift;
    public Integer followPetType;
    
    public BaseStoreGoodsVo() {
    }
    
    public BaseStoreGoodsVo(final StoreGoods vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.barcode = vo.getBarcode();
        this.forSale = vo.getForSale();
        this.showPos = vo.getShowPos();
        this.rpos = vo.getRpos();
        this.saleQuota = vo.getSaleQuota();
        this.recommend = vo.getRecommend();
        this.coin = vo.getCoin();
        this.discount = vo.getDiscount();
        this.type = vo.getType();
        this.quotaLimit = vo.getQuotaLimit();
        this.mustVip = vo.getMustVip();
        this.isGift = vo.getIsGift();
        this.followPetType = vo.getFollowPetType();
    }
    
    public static final BaseStoreGoodsVo t(final StoreGoods vo) {
        return new BaseStoreGoodsVo(vo);
    }
    
    public static final List<BaseStoreGoodsVo> t(final List<StoreGoods> list) {
        final List<BaseStoreGoodsVo> listVo = new ArrayList<BaseStoreGoodsVo>();
        for (final StoreGoods temp : list) {
            listVo.add(new BaseStoreGoodsVo(temp));
        }
        return listVo;
    }
}
