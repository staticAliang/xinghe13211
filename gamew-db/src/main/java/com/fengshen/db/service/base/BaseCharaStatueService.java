package com.fengshen.db.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.dao.Chara_StatueMapper;
import com.fengshen.db.domain.Chara_Statue;
import com.fengshen.db.domain.Chara_StatueExample;
import com.fengshen.db.domain.Chara_StatueExample.Criteria;

@Service
public class BaseCharaStatueService {
    @Autowired
    protected Chara_StatueMapper mapper;

    public BaseCharaStatueService() {
    }

    public List<Chara_Statue> findAll(String serverId) {
        Chara_StatueExample example = new Chara_StatueExample();
        Chara_StatueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andServeridEqualTo(serverId);
        return this.mapper.selectByExampleWithBLOBs(example);
    }

    public Chara_Statue findByName(String serverId, String npcName) {
        Chara_StatueExample example = new Chara_StatueExample();
        Chara_StatueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andServeridEqualTo(serverId).andNpcNameEqualTo(npcName);
        List<Chara_Statue> list = this.mapper.selectByExample(example);
        assert list.size()<=1;
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public void insert(Chara_Statue chara_statue) {
        chara_statue.setAddTime(new Date());
        chara_statue.setUpdateTime(new Date());
        this.mapper.insertSelective(chara_statue);
    }

    public int updateById(Chara_Statue chara_statue) {
        chara_statue.setUpdateTime(new Date());
        return this.mapper.updateByPrimaryKeySelective(chara_statue);
    }

    public int deletedById(Chara_Statue chara_statue) {
        return this.mapper.deleteByPrimaryKey(chara_statue.getId());
    }
    
    public List<Chara_Statue> selectAll(Chara_Statue c) {
        Chara_StatueExample example = new Chara_StatueExample();
        Criteria c2 = example.createCriteria();
        if(c.getServerid() != null) {
        	c2.andServeridEqualTo(c.getServerid());
        }
        if(c.getNpcName() != null) {
        	c2.andNpcNameEqualTo(c.getNpcName());
        }
        return this.mapper.selectByExampleWithBLOBs(example);
    }
    
    public List<Chara_Statue> selectById(int id) {
        Chara_StatueExample example = new Chara_StatueExample();
        Chara_StatueExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return this.mapper.selectByExampleWithBLOBs(example);
    }
}
