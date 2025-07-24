package com.fengshen.server.game.scene;

/**
 */
public abstract class WalkableObj extends SceneObj {
    public abstract int getX();
    public abstract int getY();
    public abstract VisionGrid getCurVisionGrid();
    public abstract void setCurVisionGrid(VisionGrid visionGrid);
}
