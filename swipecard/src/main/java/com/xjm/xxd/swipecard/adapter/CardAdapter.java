package com.xjm.xxd.swipecard.adapter;

import com.xjm.xxd.swipecard.holder.CardHolder;

/**
 * Created by queda on 2016/12/15.
 */

public abstract class CardAdapter<CH extends CardHolder> implements ICardAdapter<CH> {

    @Override
    public CH onCreateCardHolder(int position) {
        return null;
    }

    @Override
    public CH onPreloadCardHolder(CH ch, int position) {
        return null;
    }

    @Override
    public CH onBindCardHolder(CH ch, int position) {
        return null;
    }

    @Override
    public void onRecycleCardHolder(CH ch, int position) {

    }

    // 总共有多少数据源
    abstract protected int getSize();

    // 预加载的数据源数量
    abstract protected int getPreloadCount();

}
