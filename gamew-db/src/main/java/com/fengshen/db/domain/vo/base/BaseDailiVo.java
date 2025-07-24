package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseDailiVo
{
    public Integer id;
    public String account;
    public String passwd;
    public String code;
    public String token;
    
    public BaseDailiVo() {
    }
    
    public BaseDailiVo(final Daili vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.account = vo.getAccount();
        this.passwd = vo.getPasswd();
        this.code = vo.getCode();
        this.token = vo.getToken();
    }
    
    public static final BaseDailiVo t(final Daili vo) {
        return new BaseDailiVo(vo);
    }
    
    public static final List<BaseDailiVo> t(final List<Daili> list) {
        final List<BaseDailiVo> listVo = new ArrayList<BaseDailiVo>();
        for (final Daili temp : list) {
            listVo.add(new BaseDailiVo(temp));
        }
        return listVo;
    }
}
