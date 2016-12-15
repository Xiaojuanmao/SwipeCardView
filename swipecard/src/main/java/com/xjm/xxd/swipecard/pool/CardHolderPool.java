package com.xjm.xxd.swipecard.pool;

import android.content.Context;
import android.support.annotation.Nullable;

import com.xjm.xxd.swipecard.holder.CardHolder;

import java.lang.ref.WeakReference;

/**
 * Created by queda on 2016/12/14.
 */

public class CardHolderPool<HOLDER extends CardHolder> extends BasePool<HOLDER> {

    private WeakReference<Context> mContextReference;

    public CardHolderPool(Context context, int poolCount) {
        super(poolCount);
        mContextReference = new WeakReference<>(context);
    }

    public
    @Nullable
    Context getContext() {
        if (mContextReference == null || mContextReference.get() == null) {
            return null;
        }
        return mContextReference.get();
    }

}
