package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseNoticeVo
{
    public Integer id;
    public String message;
    public Integer time;
    
    public BaseNoticeVo() {
    }
    
    public BaseNoticeVo(final Notice vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.message = vo.getMessage();
        this.time = vo.getTime();
    }
    
    public static final BaseNoticeVo t(final Notice vo) {
        return new BaseNoticeVo(vo);
    }
    
    public static final List<BaseNoticeVo> t(final List<Notice> list) {
        final List<BaseNoticeVo> listVo = new ArrayList<BaseNoticeVo>();
        for (final Notice temp : list) {
            listVo.add(new BaseNoticeVo(temp));
        }
        return listVo;
    }
}
