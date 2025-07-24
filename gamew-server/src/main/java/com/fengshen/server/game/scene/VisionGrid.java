package com.fengshen.server.game.scene;

import java.util.function.Consumer;

/**
 * 视野格子
 */
public class VisionGrid {
    private final SceneObjCollection sceneObjCollection = new SceneObjCollection();

    private final int visionX;
    private final int visionY;

    public VisionGrid(int visionX, int visionY) {
        this.visionX = visionX;
        this.visionY = visionY;
    }

    public int getVisionX() {
        return visionX;
    }

    public int getVisionY() {
        return visionY;
    }

    public void removeWalkableObj(WalkableObj walkableObj){
        sceneObjCollection.removeWalkableObj(walkableObj);
    }
    public void addWalkableObj(WalkableObj walkableObj){
        sceneObjCollection.addWalkableObj(walkableObj);
    }
    public void playerAction(Consumer<ScenePlayer> consumer){
        sceneObjCollection.playerAction(consumer);
    }
    public void npcAction(Consumer<SceneNpc> consumer){
        sceneObjCollection.npcAction(consumer);
    }
    public boolean isHavePlayer(){
        return sceneObjCollection.isHavePlayer();
    }
    public void addScenePlayer(ScenePlayer scenePlayer){
        sceneObjCollection.addScenePlayer(scenePlayer);
    }
    public void addSceneNpc(SceneNpc sceneNpc){
        sceneObjCollection.addSceneNpc(sceneNpc);
    }
    public void removeScenePlayer(int charaId){
        sceneObjCollection.removeScenePlayer(charaId);
    }
    public void removeSceneNpc(int id){
        sceneObjCollection.removeSceneNpc(id);
    }
}
