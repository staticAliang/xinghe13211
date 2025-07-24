package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseNpcPointVo
{
    public Integer id;
    public String mapname;
    public String doorname;
    public Integer x;
    public Integer y;
    public Integer z;
    public Integer inx;
    public Integer iny;
    
    public BaseNpcPointVo() {
    }
    
    public BaseNpcPointVo(final NpcPoint vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.mapname = vo.getMapname();
        this.doorname = vo.getDoorname();
        this.x = vo.getX();
        this.y = vo.getY();
        this.z = vo.getZ();
        this.inx = vo.getInx();
        this.iny = vo.getIny();
    }
    
    public static final BaseNpcPointVo t(final NpcPoint vo) {
        return new BaseNpcPointVo(vo);
    }
    
    public static final List<BaseNpcPointVo> t(final List<NpcPoint> list) {
        final List<BaseNpcPointVo> listVo = new ArrayList<BaseNpcPointVo>();
        for (final NpcPoint temp : list) {
            listVo.add(new BaseNpcPointVo(temp));
        }
        return listVo;
    }
}
