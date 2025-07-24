package com.fengshen.db.dao;

import com.fengshen.db.domain.RareShopItem;

public interface RareShopItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RareShopItem record);

    int insertSelective(RareShopItem record);

    RareShopItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RareShopItem record);

    int updateByPrimaryKey(RareShopItem record);
}