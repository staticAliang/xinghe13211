package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseMedicineShopVo
{
    public Integer id;
    public Integer goodsNo;
    public Integer payType;
    public String name;
    public Integer value;
    public Integer level;
    public Integer type;
    public Integer itemcount;
    
    public BaseMedicineShopVo() {
    }
    
    public BaseMedicineShopVo(final MedicineShop vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.goodsNo = vo.getGoodsNo();
        this.payType = vo.getPayType();
        this.name = vo.getName();
        this.value = vo.getValue();
        this.level = vo.getLevel();
        this.type = vo.getType();
        this.itemcount = vo.getItemcount();
    }
    
    public static final BaseMedicineShopVo t(final MedicineShop vo) {
        return new BaseMedicineShopVo(vo);
    }
    
    public static final List<BaseMedicineShopVo> t(final List<MedicineShop> list) {
        final List<BaseMedicineShopVo> listVo = new ArrayList<BaseMedicineShopVo>();
        for (final MedicineShop temp : list) {
            listVo.add(new BaseMedicineShopVo(temp));
        }
        return listVo;
    }
}
