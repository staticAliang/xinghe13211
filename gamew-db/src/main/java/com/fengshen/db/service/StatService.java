package com.fengshen.db.service;

import org.springframework.stereotype.*;

import com.fengshen.db.dao.*;

import javax.annotation.*;
import java.util.*;

@Service
public class StatService
{
    @Resource
    private StatMapper statMapper;
    
    public List<Map> statUser() {
        return this.statMapper.statUser();
    }
    
    public List<Map> statOrder() {
        return this.statMapper.statOrder();
    }
    
    public List<Map> statGoods() {
        return this.statMapper.statGoods();
    }
}
