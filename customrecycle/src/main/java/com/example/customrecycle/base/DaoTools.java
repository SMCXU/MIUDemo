package com.example.customrecycle.base;

import com.example.customrecycle.entity.VideoEntity;

import java.util.List;

/**
 * Created by U
 * <p/>
 * on 2017/11/9
 * <p/>
 * QQ:1347414707
 * <p/>
 * For:
 */
public class DaoTools {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param entity
     */
    public static void insertLove(VideoEntity entity) {
        BaseApp.getDaoInstant().getVideoEntityDao().insertOrReplace(entity);
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    public static void delete(VideoEntity entity) {
        BaseApp.getDaoInstant().getVideoEntityDao().delete(entity);
    }
    /**
     * 删除全部数据
     *
     *
     */
    public static void deleteAll() {
        BaseApp.getDaoInstant().getVideoEntityDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(VideoEntity shop) {
        BaseApp.getDaoInstant().getVideoEntityDao().update(shop);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */
//    public static List<VideoEntity> queryLove() {
//        return BaseApp.getDaoInstant().getVideoEntityDao().queryBuilder().where(VideoEntityDao.Properties.Type.eq(Shop.TYPE_LOVE)).list();
//    }

    /**
     * 查询全部数据
     */
    public static List<VideoEntity> queryAll() {
        return BaseApp.getDaoInstant().getVideoEntityDao().loadAll();
    }


}