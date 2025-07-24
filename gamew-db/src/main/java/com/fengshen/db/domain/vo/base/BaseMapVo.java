package com.fengshen.db.domain.vo.base;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.db.domain.Map;

public class BaseMapVo
{
    public Integer id;
    public String name;
    public Integer mapId;
    public Integer x;
    public Integer y;
    public String icon;
    public Integer monsterLevel;
    
    public BaseMapVo() {
    }
    
    public BaseMapVo(final Map vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.mapId = vo.getMapId();
        this.x = vo.getX();
        this.y = vo.getY();
        this.icon = vo.getIcon();
        this.monsterLevel = vo.getMonsterLevel();
    }
    
    public static final BaseMapVo t(final Map vo) {
        return new BaseMapVo(vo);
    }
    
    public static final List<BaseMapVo> t(final List<Map> list) {
        final List<BaseMapVo> listVo = new ArrayList<BaseMapVo>();
        for (final Map temp : list) {
            listVo.add(new BaseMapVo(temp));
        }
        return listVo;
    }
}
