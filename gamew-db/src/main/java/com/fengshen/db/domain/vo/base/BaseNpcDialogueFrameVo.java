package com.fengshen.db.domain.vo.base;

import java.time.*;
import java.util.*;

import com.fengshen.db.domain.*;

public class BaseNpcDialogueFrameVo
{
    public Integer id;
    public Integer portrait;
    public Integer picNo;
    public String content;
    public String secretKey;
    public String name;
    public Integer attrib;
    public LocalDateTime updateTimes;
    public Integer idname;
    public String next;
    public String currentTask;
    public String uncontent;
    public String zhuangbei;
    public Integer jingyan;
    public Integer money;
    
    public BaseNpcDialogueFrameVo() {
    }
    
    public BaseNpcDialogueFrameVo(final NpcDialogueFrame vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.portrait = vo.getPortrait();
        this.picNo = vo.getPicNo();
        this.content = vo.getContent();
        this.secretKey = vo.getSecretKey();
        this.name = vo.getName();
        this.attrib = vo.getAttrib();
        this.updateTimes = vo.getUpdateTimes();
        this.idname = vo.getIdname();
        this.next = vo.getNext();
        this.currentTask = vo.getCurrentTask();
        this.uncontent = vo.getUncontent();
        this.zhuangbei = vo.getZhuangbei();
        this.jingyan = vo.getJingyan();
        this.money = vo.getMoney();
    }
    
    public static final BaseNpcDialogueFrameVo t(final NpcDialogueFrame vo) {
        return new BaseNpcDialogueFrameVo(vo);
    }
    
    public static final List<BaseNpcDialogueFrameVo> t(final List<NpcDialogueFrame> list) {
        final List<BaseNpcDialogueFrameVo> listVo = new ArrayList<BaseNpcDialogueFrameVo>();
        for (final NpcDialogueFrame temp : list) {
            listVo.add(new BaseNpcDialogueFrameVo(temp));
        }
        return listVo;
    }
}
