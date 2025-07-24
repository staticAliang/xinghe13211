package com.fengshen.server.util;

import java.util.Arrays;

import com.fengshen.db.domain.Npc;
import com.fengshen.server.game.GameData;
import com.fengshen.server.service.MapGuardianService;

/**
 */
public class NpcIds {
    /**
     * 证道殿
     */
   public static int ZHEGN_DAO_NPC_ID_BEGIN = 10000;
   public static int ZHEGN_DAO_NPC_ID_END = 10099;

    /**
     * 英雄会
     */
    public static int HERO_PUB_NPC_ID_BEGIN = 1655;
    public static int HERO_PUB_NPC_ID_END = 1660;
    //英雄会id
    public static int[] HERO_PUB_NPC_ID = {1655,1656,1657,1658,1659,1660,1677};

    /**
     * 地图守护神
     */
    public static int MAP_GUARDIAN_NPC_ID_BEGIN = 20000;
    public static int  MAP_GUARDIAN_NPC_ID_END = 21000;

    /**
     * 郝文佳
     * @param npcId
     * @return
     */
    public static int  HAO_WEN_JIA_NPC_ID = 990;

    public static int  GUAN_JIA_NPC_ID = 1102;




    public static boolean isZhengDaoDianNpc(int npcId){
        return npcId>=ZHEGN_DAO_NPC_ID_BEGIN && npcId<=ZHEGN_DAO_NPC_ID_END;
    }

    /**
     * 英雄会
     * @param npcId
     * @return
     */
    public static boolean isHeroPubNpc(int npcId){
    	int a = Arrays.binarySearch(HERO_PUB_NPC_ID, npcId);
        return a>=0;
    }
    /**
     * 地图守护神
     * @param npcId
     * @return
     */
    public static boolean isMapGuardianNpc(int npcId){
        Npc npc = GameData.that.baseNpcService.findById(npcId);
        // 如果原生守护神为空，继续查
        if (null == npc) {
        	if(npcId == 1670) {
        		return true;
        	}
            // 判断是否是新晋守护神
            return npcId>=MAP_GUARDIAN_NPC_ID_BEGIN && npcId<=MAP_GUARDIAN_NPC_ID_END;
        } else {
            if (MapGuardianService.configMap.keySet().contains(npc.getName()))
                return true;
        }
        return false;
    }
}
