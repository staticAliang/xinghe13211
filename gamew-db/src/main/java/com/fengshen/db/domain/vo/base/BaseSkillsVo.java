package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseSkillsVo
{
    public Integer id;
    public String skillIdHex;
    public String skillName;
    public Integer skillReqpolar;
    public Integer skillType;
    public Integer skillTypeLevel;
    public Integer skillMagic;
    public Integer skillReqLevel;
    public String skillContext;
    
    public BaseSkillsVo() {
    }
    
    public BaseSkillsVo(final Skills vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.skillIdHex = vo.getSkillIdHex();
        this.skillName = vo.getSkillName();
        this.skillReqpolar = vo.getSkillReqpolar();
        this.skillType = vo.getSkillType();
        this.skillTypeLevel = vo.getSkillTypeLevel();
        this.skillMagic = vo.getSkillMagic();
        this.skillReqLevel = vo.getSkillReqLevel();
        this.skillContext = vo.getSkillContext();
    }
    
    public static final BaseSkillsVo t(final Skills vo) {
        return new BaseSkillsVo(vo);
    }
    
    public static final List<BaseSkillsVo> t(final List<Skills> list) {
        final List<BaseSkillsVo> listVo = new ArrayList<BaseSkillsVo>();
        for (final Skills temp : list) {
            listVo.add(new BaseSkillsVo(temp));
        }
        return listVo;
    }
}
