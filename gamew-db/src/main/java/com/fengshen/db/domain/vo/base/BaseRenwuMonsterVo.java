package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseRenwuMonsterVo
{
    public Integer id;
    public String mapName;
    public Integer x;
    public Integer y;
    public String name;
    public Integer icon;
    public String skills;
    public Integer type;
    
    public BaseRenwuMonsterVo() {
    }
    
    public BaseRenwuMonsterVo(final RenwuMonster vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.mapName = vo.getMapName();
        this.x = vo.getX();
        this.y = vo.getY();
        this.name = vo.getName();
        this.icon = vo.getIcon();
        this.skills = vo.getSkills();
        this.type = vo.getType();
    }
    
    public static final BaseRenwuMonsterVo t(final RenwuMonster vo) {
        return new BaseRenwuMonsterVo(vo);
    }
    
    public static final List<BaseRenwuMonsterVo> t(final List<RenwuMonster> list) {
        final List<BaseRenwuMonsterVo> listVo = new ArrayList<BaseRenwuMonsterVo>();
        for (final RenwuMonster temp : list) {
            listVo.add(new BaseRenwuMonsterVo(temp));
        }
        return listVo;
    }
}
