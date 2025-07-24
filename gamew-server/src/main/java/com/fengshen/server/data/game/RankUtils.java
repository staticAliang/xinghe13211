package com.fengshen.server.data.game;

import com.fengshen.server.domain.Chara;

public class RankUtils {

    public static String[] rankTypeArray = {
            "rank_type:101",
            "rank_type:102:45-79",
            "rank_type:102:80-89",
            "rank_type:102:90-99",
            "rank_type:102:100-109",
            "rank_type:102:110-119",
            "rank_type:102:120-129",
            "rank_type:103",
            "rank_type:104",
            "rank_type:105",
            "rank_type:106",
    };

    /**
     * CHAR_LEVEL            = 101,
     * CHAR_TAO              = 102,    -- 道行排行
     * CHAR_PHY_POWER        = 103,    -- 物攻排行
     * CHAR_MAG_POWER        = 104,    -- 法攻排行
     * CHAR_SPEED            = 105,    -- 速度排行
     * CHAR_DEF              = 106,    -- 防御排行
     *
     * @param chara
     * @param type
     * @return
     */
    public static int getRankValue(Chara chara, int type) {
        int value = 0;
        switch (type) {
            case 101:
                value = chara.level;
                break;
            case 102:
            	//道行
                value = chara.tao;
                break;
            case 103:
            	//物伤
                value = chara.accurate;
                break;
            case 104:
            	//法伤
                value = chara.mana;
                break;
            case 105:
            	//速度
                value = chara.parry;
                break;
            case 106:
            	//防御
                value = chara.wiz;
                break;
        }
        return value;
    }

}
