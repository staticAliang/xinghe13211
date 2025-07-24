package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseReportsVo
{
    public Integer id;
    public String zhanghao;
    public Integer yuanbaoshu;
    public String shifouchongzhi;
    
    public BaseReportsVo() {
    }
    
    public BaseReportsVo(final Reports vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.zhanghao = vo.getZhanghao();
        this.yuanbaoshu = vo.getYuanbaoshu();
        this.shifouchongzhi = vo.getShifouchongzhi();
    }
    
    public static final BaseReportsVo t(final Reports vo) {
        return new BaseReportsVo(vo);
    }
    
    public static final List<BaseReportsVo> t(final List<Reports> list) {
        final List<BaseReportsVo> listVo = new ArrayList<BaseReportsVo>();
        for (final Reports temp : list) {
            listVo.add(new BaseReportsVo(temp));
        }
        return listVo;
    }
}
