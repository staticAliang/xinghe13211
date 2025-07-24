package com.fengshen.server.game.scene;

import java.util.List;

public interface INotifyHandler<T extends SceneObj> {
    /**
     * 广播通知多个视野格子，unit这个单位进人到这几个视野格子中来了。
     * 或者是这个单位状态更新了，广播通知多个能看见该单位的视野格子。
     *
     * @param boardCastRange 广播通知的多个视野格子
     * @param newInUnit           新加入视野的单位
     */
    void notifyOthersObjIn(List<VisionGrid> boardCastRange, T newInUnit);

    /**
     * 广播通知多个视野格子，unitGuid的这个单位从视野中删除
     *  @param boardCastRange 广播通知的多个视野格子
     * @param removeUnitGuid       离开视野需要删除的单位的GUID
     */
    void notifyOthersObjOut(List<VisionGrid> boardCastRange, int removeUnitGuid);

    /**
     * syncPlayerGuid这个单位的视野里新加入unitAddRanges这么多个视野格子，通知syncPlayerGuid这个相机，unitAddRanges视野格子里的所有单位信息
     *  @param syncPlayerGuid
     * @param unitAddRanges 新加入进来的视野格子集合
     */
    void notifyObjOthersIn(int syncPlayerGuid, List<VisionGrid> unitAddRanges);

    /**
     * syncPlayerGuid这个单位的视野里删除了unitRemoveRanges这么多个视野格子，通知syncPlayerGuid这个相机将unitRemoveRanges视野格子里的单位信息全部删除
     *  @param syncPlayerGuid
     * @param unitRemoveRange 从视野中删除的视野格子集合
     */
    void notifyObjOthersOut(int syncPlayerGuid, List<VisionGrid> unitRemoveRange);

}