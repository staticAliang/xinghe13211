package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseMapsVo
{
    public Integer id;
    public String name;
    public Integer type;
    public Integer map;
    public Float dir;
    public Float x;
    public Float y;
    
    public BaseMapsVo() {
    }
    
    public BaseMapsVo(final Maps vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.type = vo.getType();
        this.map = vo.getMap();
        this.dir = vo.getDir();
        this.x = vo.getX();
        this.y = vo.getY();
    }
    
    public static final BaseMapsVo t(final Maps vo) {
        return new BaseMapsVo(vo);
    }
    
    public static final List<BaseMapsVo> t(final List<Maps> list) {
        final List<BaseMapsVo> listVo = new ArrayList<BaseMapsVo>();
        for (final Maps temp : list) {
            listVo.add(new BaseMapsVo(temp));
        }
        return listVo;
    }
}
