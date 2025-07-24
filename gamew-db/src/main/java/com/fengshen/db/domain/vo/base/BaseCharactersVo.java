package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseCharactersVo
{
    public Integer id;
    public Integer polar;
    public String name;
    public Integer accountId;
    public String gid;
    public String data;
    
    public BaseCharactersVo() {
    }
    
    public BaseCharactersVo(final Characters vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.polar = vo.getPolar();
        this.name = vo.getName();
        this.accountId = vo.getAccountId();
        this.gid = vo.getGid();
        this.data = vo.getData();
    }
    
    public static final BaseCharactersVo t(final Characters vo) {
        return new BaseCharactersVo(vo);
    }
    
    public static final List<BaseCharactersVo> t(final List<Characters> list) {
        final List<BaseCharactersVo> listVo = new ArrayList<BaseCharactersVo>();
        for (final Characters temp : list) {
            listVo.add(new BaseCharactersVo(temp));
        }
        return listVo;
    }
}
