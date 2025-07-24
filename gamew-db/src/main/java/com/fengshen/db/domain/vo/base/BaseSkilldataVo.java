package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSkilldataVo
{
    public Integer id;
    public String pid;
    public String skillName;
    public Integer skillLevel;
    public Integer skillMubiao;
    
    public BaseSkilldataVo() {
    }
    
    public BaseSkilldataVo(final Skilldata vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.pid = vo.getPid();
        this.skillName = vo.getSkillName();
        this.skillLevel = vo.getSkillLevel();
        this.skillMubiao = vo.getSkillMubiao();
    }
    
    public static final BaseSkilldataVo t(final Skilldata vo) {
        return new BaseSkilldataVo(vo);
    }
    
    public static final List<BaseSkilldataVo> t(final List<Skilldata> list) {
        final List<BaseSkilldataVo> listVo = new ArrayList<BaseSkilldataVo>();
        for (final Skilldata temp : list) {
            listVo.add(new BaseSkilldataVo(temp));
        }
        return listVo;
    }
}
