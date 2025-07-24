package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseChoujiangVo
{
    public Integer id;
    public Integer no;
    public String name;
    public String desc;
    public Integer level;
    
    public BaseChoujiangVo() {
    }
    
    public BaseChoujiangVo(final Choujiang vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.no = vo.getNo();
        this.name = vo.getName();
        this.desc = vo.getDesc();
        this.level = vo.getLevel();
    }
    
    public static final BaseChoujiangVo t(final Choujiang vo) {
        return new BaseChoujiangVo(vo);
    }
    
    public static final List<BaseChoujiangVo> t(final List<Choujiang> list) {
        final List<BaseChoujiangVo> listVo = new ArrayList<BaseChoujiangVo>();
        for (final Choujiang temp : list) {
            listVo.add(new BaseChoujiangVo(temp));
        }
        return listVo;
    }
}
