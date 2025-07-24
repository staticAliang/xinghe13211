package com.fengshen.server.game.scene;

import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameObjectCharMng;

/**
 */
public class ScenePlayer extends WalkableObj {
    private final int charaId;
    private VisionGrid curVisionGrid;

    public ScenePlayer(int charaId, VisionGrid visionGrid) {
        this.charaId = charaId;
        this.curVisionGrid = visionGrid;
    }

    public Chara getChara(){
        return GameObjectCharMng.getGameObjectChar(charaId).chara;
    }
    @Override
    public int getId() {
        return charaId;
    }

    @Override
    public SceneObjType getType() {
        return SceneObjType.PLAYER;
    }

    @Override
    public int getX() {
        return getChara().x;
    }

    @Override
    public int getY() {
        return getChara().y;
    }

    @Override
    public VisionGrid getCurVisionGrid() {
        return curVisionGrid;
    }

    @Override
    public void setCurVisionGrid(VisionGrid visionGrid) {
        curVisionGrid = visionGrid;
    }
}
