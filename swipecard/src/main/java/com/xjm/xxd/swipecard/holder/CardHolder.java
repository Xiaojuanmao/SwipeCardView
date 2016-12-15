package com.xjm.xxd.swipecard.holder;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by queda on 2016/12/15.
 */

/**
 * 存放在SwipeCardContainer组件中的小卡片容器
 * Container通过管理这些holder的生命周期以及各种特殊事件
 * 例如创建、预加载数据、滑动停止、回弹恢复等等事件
 */

public abstract class CardHolder extends RelativeLayout {

    private float mDownX;
    private float mDownY;
    private float mFingerX;
    private float mFingerY;
    private float mDeltaX;
    private float mDeltaY;

    private float rightBoundary;
    private float leftBoundary;

    private int screenWidth;
    private int padding;

    private STATE mState = STATE.PRE_PRELOAD;

    private static final float CARD_ROTATION_DEGREES = 40.0f;

    private static final String TAG = CardHolder.class.getSimpleName();

    /**
     * cardholder目前的状态
     */
    public enum STATE {
        POOLED(0), // 处于pool中待用
        PRE_PRELOAD(1), // 在shown队列，处于预加载数据之前状态
        PRELOADING(2), // 在shown队列，处于预加载中
        POST_PRELOAD(3), // 在shown队列，处于预加载完成
        SHOWING(4); // 正处于展示中

        // TODO : 还有更多状态，处于拖拽中、拖拽回弹动画、拖拽消失动画等等

        STATE(int state) {
            this.state = state;
        }

        private int state;

        public int getState() {
            return state;
        }
    }

    public CardHolder(Context context) {
        this(context, null);
    }

    public CardHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                clearAnimation();
                Log.d(TAG, "finger down (" + mDownX + ", " + mDownY + ")");
                break;

            case MotionEvent.ACTION_MOVE:
                mFingerX = event.getX();
                mFingerY = event.getY();
                mDeltaX = mFingerX - mDownX;
                mDeltaY = mFingerY - mDownY;

                // Set new position
                setX(getX() + mDeltaX);
                setY(getY() + mDeltaY);
                setCardRotation(getX());

                Log.d(TAG, "finger move (" + mDeltaX + ", " + mDeltaY + ")");
                break;

            case MotionEvent.ACTION_UP:
                if(isCardBeyondLeftBoundary()){
                    dismissCard(-screenWidth * 2);
                } else if(isCardBeyondRightBoundary()){
                    dismissCard(screenWidth * 2);
                } else {
                    resetCard();
                }
                Log.d(TAG, "finger up (" + event.getX() + ", " + event.getY() + ")");
                break;
        }
        return true;
    }

    private void setCardRotation(float posX) {
        float rotation = (CARD_ROTATION_DEGREES * (posX)) / screenWidth;
        int halfCardHeight = (getHeight() / 2);
        if (mDownY < halfCardHeight - (2 * padding)) {
            setRotation(rotation);
        } else {
            setRotation(-rotation);
        }
    }

    private void dismissCard(int xPos){
        this.animate()
                .x(xPos)
                .y(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ViewGroup viewGroup = (ViewGroup) CardHolder.this.getParent();
                        if(viewGroup != null) {
                            viewGroup.removeView(CardHolder.this);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    private void resetCard(){
        this.animate()
                .x(0)
                .y(0)
                .rotation(0)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(500);
    }

    // Check if card's middle is beyond the left boundary
    private boolean isCardBeyondLeftBoundary(){
        return (getX() + (getWidth() / 2) < leftBoundary);
    }

    // Check if card's middle is beyond the right boundary
    private boolean isCardBeyondRightBoundary(){
        return (getX() + (getWidth() / 2) > rightBoundary);
    }

    private void initViews(Context context) {
        createView(LayoutInflater.from(context));
        screenWidth = DisplayUtil.getScreenWidth(context);
        padding = DisplayUtil.dip2px(context, 16);
        leftBoundary =  screenWidth * (1.0f/6.0f); // Left 1/6 of screen
        rightBoundary = screenWidth * (5.0f/6.0f); // Right 1/6 of screen
    }

    public void setState(STATE state) {
        mState = state;
    }

    public STATE getState() {
        return mState;
    }

    protected abstract void createView(@NonNull LayoutInflater inflater);
}
