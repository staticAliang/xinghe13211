package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BasePackModificationVo
{
    public Integer id;
    public String alias;
    public String fasionType;
    public String str;
    public String type;
    public Integer foodNum;
    public Integer goodsPrice;
    public Integer sex;
    public Integer position;
    public Integer category;
    
    public BasePackModificationVo() {
    }
    
    public BasePackModificationVo(final PackModification vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.alias = vo.getAlias();
        this.fasionType = vo.getFasionType();
        this.str = vo.getStr();
        this.type = vo.getType();
        this.foodNum = vo.getFoodNum();
        this.goodsPrice = vo.getGoodsPrice();
        this.sex = vo.getSex();
        this.position = vo.getPosition();
        this.category = vo.getCategory();
    }
    
    public static final BasePackModificationVo t(final PackModification vo) {
        return new BasePackModificationVo(vo);
    }
    
    public static final List<BasePackModificationVo> t(final List<PackModification> list) {
        final List<BasePackModificationVo> listVo = new ArrayList<BasePackModificationVo>();
        for (final PackModification temp : list) {
            listVo.add(new BasePackModificationVo(temp));
        }
        return listVo;
    }
}
