package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BasePetHelpTypeVo
{
    public Integer id;
    public Integer type;
    public String name;
    public Integer quality;
    public Integer money;
    public Integer polar;
    
    public BasePetHelpTypeVo() {
    }
    
    public BasePetHelpTypeVo(final PetHelpType vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.type = vo.getType();
        this.name = vo.getName();
        this.quality = vo.getQuality();
        this.money = vo.getMoney();
        this.polar = vo.getPolar();
    }
    
    public static final BasePetHelpTypeVo t(final PetHelpType vo) {
        return new BasePetHelpTypeVo(vo);
    }
    
    public static final List<BasePetHelpTypeVo> t(final List<PetHelpType> list) {
        final List<BasePetHelpTypeVo> listVo = new ArrayList<BasePetHelpTypeVo>();
        for (final PetHelpType temp : list) {
            listVo.add(new BasePetHelpTypeVo(temp));
        }
        return listVo;
    }
}
