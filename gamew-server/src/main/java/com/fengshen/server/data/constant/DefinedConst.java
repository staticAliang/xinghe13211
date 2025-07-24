package com.fengshen.server.data.constant;

public class DefinedConst {
    public enum  SUBMIT_PET_TYPE  {
        SUBMIT_PET_TYPE_NORMAL (1),
        SUBMIT_PET_TYPE_FEISHENG (2),
        SUBMIT_PET_TYPE_FEED (3),  // 饲养宠物的提交
        SUBMIT_PET_TYPE_INNER_ALLCHEMY ( 4), // 内丹修炼宠物提交
        SUBMIT_PET_TYPE_BUYBACK  (100),
        ;  // 销毁宠物的提交(目前只有客户端配置了该常量)

        int value;
        SUBMIT_PET_TYPE(int tag){
             value  = tag;
         }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    //精怪类型
    public enum  MOUNT_TYPE
    {
        MOUNT_TYPE_NORMAL,
        MOUNT_TYPE_JINGGUAI,           // 坐骑-精怪
        MOUNT_TYPE_YULING,           // 坐骑-御灵
    }

    public  enum  PET_RANK{
        PET_RANK_NORMAL,
        PET_RANK_WILD               , // 野生
        PET_RANK_BABY               , // 宝宝
        PET_RANK_ELITE              , // 变异
        PET_RANK_EPIC               , // 神兽
        PET_RANK_GUARD              , // 守护
    }

    public enum FLY_TYPE{
        FLY_TYPE_NORMAL,
        FLY_TYPE_STRARTFLY,//天机老人
        FLY_TYPE_TIANJILAOR,//天机老人
        FLY_TYPE_NANHUAZHENR_PET_FIGHT,//挑战南华真人的灵兽
        FLY_TYPE_LINPIAN_FIGHT,//获取鳞片
        FLY_TYPE_XUE_FIGHT,//获取血
        //FLY_TYPE_YAOSHI_FIGHT,//妖石
        FLY_TYPE_FLY,//fly
        FLY_TYPE_FINISH,//完成
    }

    public  enum  MSG_CONFIRM_TYPE{
        MSG_CONFIRM_TYPE_NORMAL,
        MSG_CONFIRM_TYPE_REQUEST_TEAM_LEADER,
        MSG_CONFIRM_TYPE_YUANYING_FLY,
    }

    public  enum  CHILD_TYPE {
        NO_CHILD ,               // 无
        YUANYING ,               // 元婴
        XUEYING  ,               // 血婴
        UPGRADE_IMMORTAL    ,    // 仙
        UPGRADE_MAGIC       ,    // 魔
    }

    /**
     * 0：没有主任务    1：子任务进行中  2主任务完成
     * ***/
    public  enum  BAXIAN_STATUS {
        BAXIAN_STATUS_NORMAL ,
        BAXIAN_STATUS_PLAYING ,
        BAXIAN_STATUS_FINISH  ,
    }

    /**
     * 0：没有主任务    1：子任务进行中  2主任务完成
     * ***/
    public  enum  WUXUE_STATUS {
        WUXUE_STATUS_NORMAL ,
        WUXUE_STATUS_PLAYING ,
        WUXUE_STATUS_FINISH  ,
    }

    /**
     * 真人达到飞升所需要的等级
     * **/
    public  static  final  int MAX_LEVEL = 115;
    /**
     * 元婴等级
     * **/
    public  static  final  int YUANYING_MAX_LEVEL = 115;
    /***
     * 武学历练最高任务ID
     * **/
    public  static  final  int MAX_WUXUELILIAN_TASK_COUNT = 70;

    /***
     * 宠物最高的等级
     * **/
    public  static  final  int PET_MAX_LEVEL = 125;

    /***
     * 任务最高等级
     * **/
    public  static  final  int PLAYER_MAX_LEVEL = 130;
    
    public static final String GOLD_STALL_PREFIX = "GOLDSTALL";
    
    public static final String CHANGE_CARD = "CHANGE_CARD";
}
