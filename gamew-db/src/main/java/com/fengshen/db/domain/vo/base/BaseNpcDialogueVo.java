package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseNpcDialogueVo
{
    public Integer id;
    public String name;
    public Integer portranit;
    public Integer picNo;
    public String content;
    public Integer isconmlete;
    public Integer isincombat;
    public Integer palytime;
    public String taskType;
    public String idname;
    
    public BaseNpcDialogueVo() {
    }
    
    public BaseNpcDialogueVo(final NpcDialogue vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.name = vo.getName();
        this.portranit = vo.getPortranit();
        this.picNo = vo.getPicNo();
        this.content = vo.getContent();
        this.isconmlete = vo.getIsconmlete();
        this.isincombat = vo.getIsincombat();
        this.palytime = vo.getPalytime();
        this.taskType = vo.getTaskType();
        this.idname = vo.getIdname();
    }
    
    public static final BaseNpcDialogueVo t(final NpcDialogue vo) {
        return new BaseNpcDialogueVo(vo);
    }
    
    public static final List<BaseNpcDialogueVo> t(final List<NpcDialogue> list) {
        final List<BaseNpcDialogueVo> listVo = new ArrayList<BaseNpcDialogueVo>();
        for (final NpcDialogue temp : list) {
            listVo.add(new BaseNpcDialogueVo(temp));
        }
        return listVo;
    }
}
