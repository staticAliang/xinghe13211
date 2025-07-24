package com.fengshen.server.game;

import com.fengshen.server.data.xls_config.DugenoItem;
import com.fengshen.server.domain.Chara;

public class GameZone extends GameMap {
    // 场景id
    public String uid = "";
    // 场景销毁时间
    @SuppressWarnings("unused")
	private long endTime = -1;
    // 副本逻辑对象
    public GameDugeon gameDugeon = null;

    //是否永远存在
    public boolean forever = false;


    public boolean isHouseZone;

    public GameZone() {
        super();
        super.map_type = 1;
    }

    @Override
    public void joinduiyuan(GameObjectChar gameObjectChar, Chara charaduizhang) {
        super.joinduiyuan(gameObjectChar, charaduizhang);

        if (gameDugeon != null) {
            gameDugeon.onJoinMap(gameObjectChar.chara);
        }
    }

    @Override
    public void leave(GameObjectChar gameObjectChar) {
        if (gameDugeon != null) {
            DugenoItem cfg = gameDugeon.getDugenoItemCfg();
            GameUtilRenWu.createTask(cfg.task_type, "", "", gameObjectChar.chara);
        }

        if (isHouseZone) {
            gameObjectChar.chara.line = 1;
        }
        super.leave(gameObjectChar);
        if (!super.sessionList.isEmpty()) {
            return;
        }

        GameLine.deleteZoneGameMap(gameObjectChar.chara.line, this.uid);
    }

    public GameDugeon initGameDugeon(String dugeon_name) {
        gameDugeon = new GameDugeon();
        gameDugeon.name = dugeon_name;
        super.map_type = 2;
        return gameDugeon;
    }

    // 设置生命周期单位s todo
    public void setLifeTime(int lifeTime) {
        this.endTime = lifeTime + System.currentTimeMillis();
    }
}
