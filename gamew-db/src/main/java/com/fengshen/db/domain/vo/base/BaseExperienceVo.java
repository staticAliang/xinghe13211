package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseExperienceVo
{
    public Integer attrib;
    public Integer maxLevel;
    
    public BaseExperienceVo() {
    }
    
    public BaseExperienceVo(final Experience vo) {
        if (vo == null) {
            return;
        }
        this.attrib = vo.getAttrib();
        this.maxLevel = vo.getMaxLevel();
    }
    
    public static final BaseExperienceVo t(final Experience vo) {
        return new BaseExperienceVo(vo);
    }
    
    public static final List<BaseExperienceVo> t(final List<Experience> list) {
        final List<BaseExperienceVo> listVo = new ArrayList<BaseExperienceVo>();
        for (final Experience temp : list) {
            listVo.add(new BaseExperienceVo(temp));
        }
        return listVo;
    }
}
