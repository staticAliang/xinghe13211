package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSrenwuVo
{
    public Integer id;
    public String pid;
    public Integer rid;
    public String skillName;
    public String skillJieshao;
    public String skillDqti;
    public String skillXck;
    
    public BaseSrenwuVo() {
    }
    
    public BaseSrenwuVo(final Srenwu vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.pid = vo.getPid();
        this.rid = vo.getRid();
        this.skillName = vo.getSkillName();
        this.skillJieshao = vo.getSkillJieshao();
        this.skillDqti = vo.getSkillDqti();
        this.skillXck = vo.getSkillXck();
    }
    
    public static final BaseSrenwuVo t(final Srenwu vo) {
        return new BaseSrenwuVo(vo);
    }
    
    public static final List<BaseSrenwuVo> t(final List<Srenwu> list) {
        final List<BaseSrenwuVo> listVo = new ArrayList<BaseSrenwuVo>();
        for (final Srenwu temp : list) {
            listVo.add(new BaseSrenwuVo(temp));
        }
        return listVo;
    }
}
