package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseExperienceTreasureVo
{
    public Integer attrib;
    public Integer maxLevel;
    
    public BaseExperienceTreasureVo() {
    }
    
    public BaseExperienceTreasureVo(final ExperienceTreasure vo) {
        if (vo == null) {
            return;
        }
        this.attrib = vo.getAttrib();
        this.maxLevel = vo.getMaxLevel();
    }
    
    public static final BaseExperienceTreasureVo t(final ExperienceTreasure vo) {
        return new BaseExperienceTreasureVo(vo);
    }
    
    public static final List<BaseExperienceTreasureVo> t(final List<ExperienceTreasure> list) {
        final List<BaseExperienceTreasureVo> listVo = new ArrayList<BaseExperienceTreasureVo>();
        for (final ExperienceTreasure temp : list) {
            listVo.add(new BaseExperienceTreasureVo(temp));
        }
        return listVo;
    }
}
