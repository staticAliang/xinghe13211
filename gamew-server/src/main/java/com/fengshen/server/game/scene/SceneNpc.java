package com.fengshen.server.game.scene;

import com.fengshen.db.domain.Npc;

/**
 */
public class SceneNpc extends SceneObj {
    private final Npc npc;

    public SceneNpc(Npc npc) {
        this.npc = npc;
    }

    public Npc getNpc() {
        return npc;
    }

    @Override
    public int getId() {
        return npc.getId();
    }

    @Override
    public SceneObjType getType() {
        return SceneObjType.NPC;
    }
}
