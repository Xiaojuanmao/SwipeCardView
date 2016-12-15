package com.xjm.xxd.swipecard.pool;

import android.support.annotation.Nullable;

/**
 * Created by queda on 2016/12/14.
 */

public interface IPool<T> {

    // 从pool中获取一个T对象实例
    @Nullable
    T get();

    // 将一个t对象实例返还给pool
    void recycle(T t);

    // 获取pool中可用T的数量
    int size();

    // 对pool进行裁剪压缩
    int trim();

    void destroy();
}
