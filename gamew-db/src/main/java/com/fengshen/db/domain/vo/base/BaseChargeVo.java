package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseChargeVo
{
    public Integer id;
    public String accountname;
    public Integer coin;
    public Integer state;
    public Integer money;
    public String code;
    
    public BaseChargeVo() {
    }
    
    public BaseChargeVo(final Charge vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.accountname = vo.getAccountname();
        this.coin = vo.getCoin();
        this.state = vo.getState();
        this.money = vo.getMoney();
        this.code = vo.getCode();
    }
    
    public static final BaseChargeVo t(final Charge vo) {
        return new BaseChargeVo(vo);
    }
    
    public static final List<BaseChargeVo> t(final List<Charge> list) {
        final List<BaseChargeVo> listVo = new ArrayList<BaseChargeVo>();
        for (final Charge temp : list) {
            listVo.add(new BaseChargeVo(temp));
        }
        return listVo;
    }
}
