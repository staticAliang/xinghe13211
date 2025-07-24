package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSkilljinengVo
{
    public Integer id;
    public Integer rid;
    public String pid;
    public String skillName;
    public Integer skillLevel;
    public Integer skillMubiao;
    public Integer skillMp;
    
    public BaseSkilljinengVo() {
    }
    
    public BaseSkilljinengVo(final Skilljineng vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.rid = vo.getRid();
        this.pid = vo.getPid();
        this.skillName = vo.getSkillName();
        this.skillLevel = vo.getSkillLevel();
        this.skillMubiao = vo.getSkillMubiao();
        this.skillMp = vo.getSkillMp();
    }
    
    public static final BaseSkilljinengVo t(final Skilljineng vo) {
        return new BaseSkilljinengVo(vo);
    }
    
    public static final List<BaseSkilljinengVo> t(final List<Skilljineng> list) {
        final List<BaseSkilljinengVo> listVo = new ArrayList<BaseSkilljinengVo>();
        for (final Skilljineng temp : list) {
            listVo.add(new BaseSkilljinengVo(temp));
        }
        return listVo;
    }
}
