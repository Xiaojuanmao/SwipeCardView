package com.xjm.xxd.swipecard.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.xjm.xxd.swipecard.R;
import com.xjm.xxd.swipecard.adapter.ICardAdapter;

/**
 * Created by queda on 2016/12/15.
 */

public class SwipeCardContainer extends RelativeLayout implements ISwipeCardContainer {

    RelativeLayout mContainer;

    private ICardAdapter mAdapter;

    public SwipeCardContainer(Context context) {
        this(context, null);
    }

    public SwipeCardContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCardContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        View view = inflate(context, R.layout.layout_card_video_container, this);
        mContainer = ((RelativeLayout) view.findViewById(R.id.card_container));
    }

    public void setAdapter(ICardAdapter adapter) {
        mAdapter = adapter;
    }

}
