package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BasePetVo
{
    public Integer id;
    public Integer index;
    public Integer levelReq;
    public Integer life;
    public Integer mana;
    public Integer speed;
    public Integer phyAttack;
    public Integer magAttack;
    public String polar;
    public String skiils;
    public String zoon;
    public Integer icon;
    public String name;
    
    public BasePetVo() {
    }
    
    public BasePetVo(final Pet vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.index = vo.getIndex();
        this.levelReq = vo.getLevelReq();
        this.life = vo.getLife();
        this.mana = vo.getMana();
        this.speed = vo.getSpeed();
        this.phyAttack = vo.getPhyAttack();
        this.magAttack = vo.getMagAttack();
        this.polar = vo.getPolar();
        this.skiils = vo.getSkiils();
        this.zoon = vo.getZoon();
        this.icon = vo.getIcon();
        this.name = vo.getName();
    }
    
    public static final BasePetVo t(final Pet vo) {
        return new BasePetVo(vo);
    }
    
    public static final List<BasePetVo> t(final List<Pet> list) {
        final List<BasePetVo> listVo = new ArrayList<BasePetVo>();
        for (final Pet temp : list) {
            listVo.add(new BasePetVo(temp));
        }
        return listVo;
    }
}
