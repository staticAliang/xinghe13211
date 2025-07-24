package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseRenwuVo
{
    public Integer id;
    public String uncontent;
    public String npcName;
    public String currentTask;
    public String showName;
    public String taskPrompt;
    public String reward;
    
    public BaseRenwuVo() {
    }
    
    public BaseRenwuVo(final Renwu vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.uncontent = vo.getUncontent();
        this.npcName = vo.getNpcName();
        this.currentTask = vo.getCurrentTask();
        this.showName = vo.getShowName();
        this.taskPrompt = vo.getTaskPrompt();
        this.reward = vo.getReward();
    }
    
    public static final BaseRenwuVo t(final Renwu vo) {
        return new BaseRenwuVo(vo);
    }
    
    public static final List<BaseRenwuVo> t(final List<Renwu> list) {
        final List<BaseRenwuVo> listVo = new ArrayList<BaseRenwuVo>();
        for (final Renwu temp : list) {
            listVo.add(new BaseRenwuVo(temp));
        }
        return listVo;
    }
}
