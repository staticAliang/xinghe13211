package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BasePetsVo
{
    public Integer id;
    public String ownerid;
    public String petid;
    public String nickname;
    public String name;
    public Integer horsetype;
    public Integer type;
    public Integer level;
    public Integer liliang;
    public Integer minjie;
    public Integer lingli;
    public Integer tili;
    public Integer dianhualx;
    public Integer dianhuazd;
    public Integer dianhuazx;
    public Integer yuhualx;
    public Integer yuhuazd;
    public Integer yuhuazx;
    public Integer cwjyzx;
    public Integer cwjyzd;
    public Integer feisheng;
    public Integer fsudu;
    public Integer qhcwWg;
    public Integer qhcwFg;
    public Integer cwXiangxing;
    public Integer cwWuxue;
    public String cwIcon;
    public Integer cwXinfa;
    public Integer cwQinmi;
    
    public BasePetsVo() {
    }
    
    public BasePetsVo(final Pets vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.ownerid = vo.getOwnerid();
        this.petid = vo.getPetid();
        this.nickname = vo.getNickname();
        this.name = vo.getName();
        this.horsetype = vo.getHorsetype();
        this.type = vo.getType();
        this.level = vo.getLevel();
        this.liliang = vo.getLiliang();
        this.minjie = vo.getMinjie();
        this.lingli = vo.getLingli();
        this.tili = vo.getTili();
        this.dianhualx = vo.getDianhualx();
        this.dianhuazd = vo.getDianhuazd();
        this.dianhuazx = vo.getDianhuazx();
        this.yuhualx = vo.getYuhualx();
        this.yuhuazd = vo.getYuhuazd();
        this.yuhuazx = vo.getYuhuazx();
        this.cwjyzx = vo.getCwjyzx();
        this.cwjyzd = vo.getCwjyzd();
        this.feisheng = vo.getFeisheng();
        this.fsudu = vo.getFsudu();
        this.qhcwWg = vo.getQhcwWg();
        this.qhcwFg = vo.getQhcwFg();
        this.cwXiangxing = vo.getCwXiangxing();
        this.cwWuxue = vo.getCwWuxue();
        this.cwIcon = vo.getCwIcon();
        this.cwXinfa = vo.getCwXinfa();
        this.cwQinmi = vo.getCwQinmi();
    }
    
    public static final BasePetsVo t(final Pets vo) {
        return new BasePetsVo(vo);
    }
    
    public static final List<BasePetsVo> t(final List<Pets> list) {
        final List<BasePetsVo> listVo = new ArrayList<BasePetsVo>();
        for (final Pets temp : list) {
            listVo.add(new BasePetsVo(temp));
        }
        return listVo;
    }
}
