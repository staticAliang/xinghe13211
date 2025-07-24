package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseCreepsStoreVo
{
    public Integer id;
    public String name;
    public Integer price;
    
    public BaseCreepsStoreVo() {
    }
    
    public BaseCreepsStoreVo(final CreepsStore vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.price = vo.getPrice();
    }
    
    public static final BaseCreepsStoreVo t(final CreepsStore vo) {
        return new BaseCreepsStoreVo(vo);
    }
    
    public static final List<BaseCreepsStoreVo> t(final List<CreepsStore> list) {
        final List<BaseCreepsStoreVo> listVo = new ArrayList<BaseCreepsStoreVo>();
        for (final CreepsStore temp : list) {
            listVo.add(new BaseCreepsStoreVo(temp));
        }
        return listVo;
    }
}
