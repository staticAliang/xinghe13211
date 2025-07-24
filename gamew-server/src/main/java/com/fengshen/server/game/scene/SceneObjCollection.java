package com.fengshen.server.game.scene;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.function.Consumer;

public class SceneObjCollection {
    /**
     * key:id
     */
    private final Int2ObjectMap<ScenePlayer> players = new Int2ObjectOpenHashMap<>();
    /**
     * key:id
     */
    private final Int2ObjectMap<SceneNpc> npcs = new Int2ObjectOpenHashMap<>();

    public void addSceneNpc(SceneNpc sceneNpc){
        assert !npcs.containsKey(sceneNpc.getId());
        npcs.put(sceneNpc.getId(), sceneNpc);
    }

    public void cleanSceneNpc(){
        npcs.clear();
    }

    public void addScenePlayer(ScenePlayer scenePlayer){
        assert !players.containsKey(scenePlayer.getId());
        players.put(scenePlayer.getId(), scenePlayer);
    }

    public void removeWalkableObj(WalkableObj walkableObj){
        if(walkableObj instanceof ScenePlayer){
            ScenePlayer scenePlayer = (ScenePlayer) walkableObj;
            assert players.containsKey(scenePlayer.getId());
            players.remove(scenePlayer.getId());
        }
    }
    public void removeScenePlayer(int charaId){
        players.remove(charaId);
    }
    public void removeSceneNpc(int id){
        npcs.remove(id);
    }
    public void addWalkableObj(WalkableObj walkableObj){
        if(walkableObj instanceof ScenePlayer){
            ScenePlayer scenePlayer = (ScenePlayer) walkableObj;
            assert !players.containsKey(scenePlayer.getId());
            players.put(scenePlayer.getId(), scenePlayer);
        }
    }

    public void walkableAction(Consumer<WalkableObj> consumer){
        for(ScenePlayer scenePlayer:players.values()){
            consumer.accept(scenePlayer);
        }
    }
    public void playerAction(Consumer<ScenePlayer> consumer){
        for(ScenePlayer scenePlayer:players.values()){
            consumer.accept(scenePlayer);
        }
    }
    public void npcAction(Consumer<SceneNpc> consumer){
        for(SceneNpc sceneNpc:npcs.values()){
            consumer.accept(sceneNpc);
        }
    }
    public boolean isHavePlayer(){
        return players.size()>0;
    }

    public ScenePlayer getScenePlayer(int charaId){
        return players.get(charaId);
    }
    public SceneNpc getSceneNpc(int id){
        return npcs.get(id);
    }
}
