package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseNpcVo
{
    public Integer id;
    public Integer icon;
    public Integer x;
    public Integer y;
    public String name;
    public Integer mapId;
    
    public BaseNpcVo() {
    }
    
    public BaseNpcVo(final Npc vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.icon = vo.getIcon();
        this.x = vo.getX();
        this.y = vo.getY();
        this.name = vo.getName();
        this.mapId = vo.getMapId();
    }
    
    public static final BaseNpcVo t(final Npc vo) {
        return new BaseNpcVo(vo);
    }
    
    public static final List<BaseNpcVo> t(final List<Npc> list) {
        final List<BaseNpcVo> listVo = new ArrayList<BaseNpcVo>();
        for (final Npc temp : list) {
            listVo.add(new BaseNpcVo(temp));
        }
        return listVo;
    }
}
