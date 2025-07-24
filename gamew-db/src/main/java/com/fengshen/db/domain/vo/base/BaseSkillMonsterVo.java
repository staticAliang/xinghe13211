package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSkillMonsterVo
{
    public Integer id;
    public String name;
    public String skills;
    public Integer type;
    
    public BaseSkillMonsterVo() {
    }
    
    public BaseSkillMonsterVo(final SkillMonster vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.skills = vo.getSkills();
        this.type = vo.getType();
    }
    
    public static final BaseSkillMonsterVo t(final SkillMonster vo) {
        return new BaseSkillMonsterVo(vo);
    }
    
    public static final List<BaseSkillMonsterVo> t(final List<SkillMonster> list) {
        final List<BaseSkillMonsterVo> listVo = new ArrayList<BaseSkillMonsterVo>();
        for (final SkillMonster temp : list) {
            listVo.add(new BaseSkillMonsterVo(temp));
        }
        return listVo;
    }
}
