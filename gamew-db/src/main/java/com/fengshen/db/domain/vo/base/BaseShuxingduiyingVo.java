package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseShuxingduiyingVo
{
    public Integer id;
    public String name;
    public String yingwen;
    
    public BaseShuxingduiyingVo() {
    }
    
    public BaseShuxingduiyingVo(final Shuxingduiying vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.yingwen = vo.getYingwen();
    }
    
    public static final BaseShuxingduiyingVo t(final Shuxingduiying vo) {
        return new BaseShuxingduiyingVo(vo);
    }
    
    public static final List<BaseShuxingduiyingVo> t(final List<Shuxingduiying> list) {
        final List<BaseShuxingduiyingVo> listVo = new ArrayList<BaseShuxingduiyingVo>();
        for (final Shuxingduiying temp : list) {
            listVo.add(new BaseShuxingduiyingVo(temp));
        }
        return listVo;
    }
}
