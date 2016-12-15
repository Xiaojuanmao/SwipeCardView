package com.xjm.xxd.swipecard.pool;

import android.support.annotation.Nullable;

import java.util.LinkedList;

/**
 * Created by queda on 2016/12/14.
 */

public abstract class BasePool<T> implements IPool<T> {

    private LinkedList<T> mPool;

    private final int mPoolSize; // pool的大小，超过size不接收recycle

    private static final int DEFAULT_POOL_SIZE = 5;

    public BasePool() {
        this(DEFAULT_POOL_SIZE);
    }

    public BasePool(int initSize) {
        if (initSize <= 0) {
            initSize = DEFAULT_POOL_SIZE;
        }
        mPool = new LinkedList<>();
        mPoolSize = initSize;
    }

    @Override
    public int size() {
        if (mPool == null || mPool.isEmpty()) {
            return 0;
        }
        return mPool.size();
    }

    @Override
    public void recycle(T t) {
        // 回收入pool中，在trim的时候会裁剪至mPoolSize的大小
        if (t == null) {
            return;
        }
        mPool.addLast(t);
    }

    @Override
    public
    @Nullable
    T get() {
        if (mPool == null) {
            mPool = new LinkedList<>();
        }
        T result;
        if (mPool.isEmpty() || mPool.peek() == null) {
            // pool中没有可复用的组件
            result = null;
        } else {
            result = mPool.poll();
        }
        return result;
    }

    @Override
    public int trim() {
        if (mPool == null || mPool.isEmpty()) {
            return 0;
        }
        int currentPoolSize = mPool.size();
        if (currentPoolSize <= mPoolSize) {
            return 0;
        }
        int needTrimCount = currentPoolSize - mPoolSize;
        for(int i = 0; i < needTrimCount; i++) {
            mPool.removeLast();
        }
        return needTrimCount;
    }

    @Override
    public void destroy() {
        if (mPool == null || mPool.isEmpty()) {
            return;
        }
        mPool.clear();
    }

}
