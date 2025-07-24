package com.fengshen.db.domain.vo.base;

import java.util.*;

import com.fengshen.db.domain.*;

public class BaseStoreInfoVo
{
    public Integer id;
    public String quality;
    public Integer value;
    public Integer type;
    public String name;
    public Integer totalScore;
    public Integer recognizeRecognized;
    public Integer rebuildLevel;
    public Integer silverCoin;
    
    public BaseStoreInfoVo() {
    }
    
    public BaseStoreInfoVo(final StoreInfo vo) {
        if (vo == null) {
            return;
        }
        this.id = vo.getId();
        this.quality = vo.getQuality();
        this.value = vo.getValue();
        this.type = vo.getType();
        this.name = vo.getName();
        this.totalScore = vo.getTotalScore();
        this.recognizeRecognized = vo.getRecognizeRecognized();
        this.rebuildLevel = vo.getRebuildLevel();
        this.silverCoin = vo.getSilverCoin();
    }
    
    public static final BaseStoreInfoVo t(final StoreInfo vo) {
        return new BaseStoreInfoVo(vo);
    }
    
    public static final List<BaseStoreInfoVo> t(final List<StoreInfo> list) {
        final List<BaseStoreInfoVo> listVo = new ArrayList<BaseStoreInfoVo>();
        for (final StoreInfo temp : list) {
            listVo.add(new BaseStoreInfoVo(temp));
        }
        return listVo;
    }
}
