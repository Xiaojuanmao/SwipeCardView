package com.xjm.xxd.swipecard.adapter;

import com.xjm.xxd.swipecard.holder.CardHolder;

/**
 * Created by queda on 2016/12/15.
 */

public interface ICardAdapter<CH extends CardHolder> {

    CH onCreateCardHolder(int position);

    CH onPreloadCardHolder(CH ch, int position);

    CH onBindCardHolder(CH ch, int position);

    void onRecycleCardHolder(CH ch, int position);

}
