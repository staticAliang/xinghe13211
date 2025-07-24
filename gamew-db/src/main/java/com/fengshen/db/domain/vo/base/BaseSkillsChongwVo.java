package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSkillsChongwVo
{
    public Integer id;
    public String ownerid;
    public String skllCwid;
    public String skillIdHex;
    public String skillName;
    public Integer skillReqpolar;
    public Integer skillLevel;
    public Integer skillMubiao;
    public String tianshuId;
    public String tianshuName;
    
    public BaseSkillsChongwVo() {
    }
    
    public BaseSkillsChongwVo(final SkillsChongw vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.ownerid = vo.getOwnerid();
        this.skllCwid = vo.getSkllCwid();
        this.skillIdHex = vo.getSkillIdHex();
        this.skillName = vo.getSkillName();
        this.skillReqpolar = vo.getSkillReqpolar();
        this.skillLevel = vo.getSkillLevel();
        this.skillMubiao = vo.getSkillMubiao();
        this.tianshuId = vo.getTianshuId();
        this.tianshuName = vo.getTianshuName();
    }
    
    public static final BaseSkillsChongwVo t(final SkillsChongw vo) {
        return new BaseSkillsChongwVo(vo);
    }
    
    public static final List<BaseSkillsChongwVo> t(final List<SkillsChongw> list) {
        final List<BaseSkillsChongwVo> listVo = new ArrayList<BaseSkillsChongwVo>();
        for (final SkillsChongw temp : list) {
            listVo.add(new BaseSkillsChongwVo(temp));
        }
        return listVo;
    }
}
