package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseGroceriesShopVo
{
    public Integer id;
    public Integer goodsNo;
    public Integer payType;
    public String name;
    public Integer value;
    public Integer level;
    public Integer type;
    public Integer itemcount;
    
    public BaseGroceriesShopVo() {
    }
    
    public BaseGroceriesShopVo(final GroceriesShop vo) {
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
    
    public static final BaseGroceriesShopVo t(final GroceriesShop vo) {
        return new BaseGroceriesShopVo(vo);
    }
    
    public static final List<BaseGroceriesShopVo> t(final List<GroceriesShop> list) {
        final List<BaseGroceriesShopVo> listVo = new ArrayList<BaseGroceriesShopVo>();
        for (final GroceriesShop temp : list) {
            listVo.add(new BaseGroceriesShopVo(temp));
        }
        return listVo;
    }
}
