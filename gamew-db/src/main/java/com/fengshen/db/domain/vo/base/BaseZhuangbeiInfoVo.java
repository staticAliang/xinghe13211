package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseZhuangbeiInfoVo
{
    public Integer id;
    public Integer attrib;
    public Integer amount;
    public Integer type;
    public String str;
    public String quality;
    public Integer master;
    public Integer metal;
    public Integer mana;
    public Integer accurate;
    public Integer def;
    public Integer dex;
    public Integer wiz;
    public Integer parry;
    
    public BaseZhuangbeiInfoVo() {
    }
    
    public BaseZhuangbeiInfoVo(final ZhuangbeiInfo vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.attrib = vo.getAttrib();
        this.amount = vo.getAmount();
        this.type = vo.getType();
        this.str = vo.getStr();
        this.quality = vo.getQuality();
        this.master = vo.getMaster();
        this.metal = vo.getMetal();
        this.mana = vo.getMana();
        this.accurate = vo.getAccurate();
        this.def = vo.getDef();
        this.dex = vo.getDex();
        this.wiz = vo.getWiz();
        this.parry = vo.getParry();
    }
    
    public static final BaseZhuangbeiInfoVo t(final ZhuangbeiInfo vo) {
        return new BaseZhuangbeiInfoVo(vo);
    }
    
    public static final List<BaseZhuangbeiInfoVo> t(final List<ZhuangbeiInfo> list) {
        final List<BaseZhuangbeiInfoVo> listVo = new ArrayList<BaseZhuangbeiInfoVo>();
        for (final ZhuangbeiInfo temp : list) {
            listVo.add(new BaseZhuangbeiInfoVo(temp));
        }
        return listVo;
    }
}
