package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseShowTasksVo
{
    public Integer id;
    public String taskType;
    public String taskDesc;
    public String taskPrompt;
    public Integer refresh;
    public Integer taskEndTime;
    public Integer attrib;
    public String reward;
    public String showName;
    public String tasktaskExtraPara;
    public Integer tasktaskState;
    
    public BaseShowTasksVo() {
    }
    
    public BaseShowTasksVo(final ShowTasks vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.taskType = vo.getTaskType();
        this.taskDesc = vo.getTaskDesc();
        this.taskPrompt = vo.getTaskPrompt();
        this.refresh = vo.getRefresh();
        this.taskEndTime = vo.getTaskEndTime();
        this.attrib = vo.getAttrib();
        this.reward = vo.getReward();
        this.showName = vo.getShowName();
        this.tasktaskExtraPara = vo.getTasktaskExtraPara();
        this.tasktaskState = vo.getTasktaskState();
    }
    
    public static final BaseShowTasksVo t(final ShowTasks vo) {
        return new BaseShowTasksVo(vo);
    }
    
    public static final List<BaseShowTasksVo> t(final List<ShowTasks> list) {
        final List<BaseShowTasksVo> listVo = new ArrayList<BaseShowTasksVo>();
        for (final ShowTasks temp : list) {
            listVo.add(new BaseShowTasksVo(temp));
        }
        return listVo;
    }
}
