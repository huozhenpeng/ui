package com.example.huozhenpeng.interviewui.pulldownrefresh;
 
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.huozhenpeng.interviewui.R;

/**
 * Created by ${huozhenpeng} on 17/6/29.
 * Company : www.miduo.com
 */
 
public abstract class CustomerListView extends ListView {
    private Context context;
 
    public CustomerListView(Context context) {
        this(context, null);
    }
 
    public CustomerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
 
    public abstract View addHeadView();
 
 
    public CustomerListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }
 
 
    private View headView;
    private int mHeaderHeight;
    private View firstView;
 
    private void init() {
        if (headView == null) {
            headView = LayoutInflater.from(context).inflate(R.layout.head_view, null);
        }
        firstView = addHeadView();
        addHeaderView(headView);
        mScrollChecker = new ScrollChecker();
        headView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderHeight = headView.getMeasuredHeight();
                headView.setPadding(0, -headView.getMeasuredHeight(), 0, 0);
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
 
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
 
    }
 
 
    private void setPadding(int paddingTop) {
        if (paddingTop >= 0)
            paddingTop = 0;
        if (paddingTop <= -mHeaderHeight)
            paddingTop = -mHeaderHeight;
        headView.setPadding(0, paddingTop, 0, 0);
    }
 
 
    //作用主要是一次下拉还没有结束之后又进行下一次下拉（这种的话，效果会好点，连续。。。。。）
    private int initPadding=-500;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Y = (int) ev.getY();
                if(runnable!=null)
                {
                    //移除回调，
                    initPadding=headView.getPaddingTop();
                    CustomerListView.this.removeCallbacks(runnable);
                    mScrollChecker.removeCallBacks();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                curY = (int) ev.getY();
                //firstview是区分下拉刷新上面还有没有headview
                if (firstView == null) {
                    if (getFirstVisiblePosition() == 0 && Math.abs(headView.getPaddingTop()) == mHeaderHeight) {
                        if (curY > Y) {
                            setPadding(initPadding + (curY - Y));
                            return true;
                        } else {
 
                        }
                    } else if (getFirstVisiblePosition() > 0) {
 
                    } else if (Math.abs(headView.getPaddingTop()) <= mHeaderHeight) {
                        setPadding(initPadding + (curY - Y));
                        return true;
                    }
                } else {
                    if (getFirstVisiblePosition() == 0 && firstView.getBottom() == firstView.getHeight()) {
                        if (curY > Y) {
                            setPadding(initPadding + (curY - Y));
                            return true;
                        } else {
 
                        }
 
                    } else {
 
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(firstView!=null)
                {
                    if (Math.abs(headView.getPaddingTop()) < mHeaderHeight&&getFirstVisiblePosition()<2) {
                        onRelease();
                        return true;
                    }
                    else
                    {
                        setPadding(-mHeaderHeight);
                    }
                }
                else
                {
                    if (Math.abs(headView.getPaddingTop()) < mHeaderHeight&&getFirstVisiblePosition()<1) {
                        onRelease();
                        return true;
                    }
                    else
                    {
                        setPadding(-mHeaderHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
 
    private int Y;
    private int curY;
 
 
    //释放时，contentView的top值
    private int releaseTop;
 
    private Runnable runnable;
 
    /**
     * 假设每次让回复到250px的时候执行正在刷新操作
     */
    private void onRelease() {
        releaseTop = headView.getPaddingTop();
        if (Math.abs(headView.getPaddingTop()) <= mHeaderHeight / 2) {
            mScrollChecker.tryToScrollTo(releaseTop + mHeaderHeight / 2, 1000);
            runnable=new Runnable() {
                @Override
                public void run() {
                    //刷新2s后恢复到初始位置
                    //不要用这个，getTop值恢复过程中会变化的
                    mScrollChecker.tryToScrollTo(mHeaderHeight / 2, 1500);
                }
            };
            postDelayed(runnable, 2000);
 
        } else {
            //相当于不执行刷新
            mScrollChecker.tryToScrollTo(releaseTop + mHeaderHeight, 1500);
        }
 
    }
 
    private ScrollChecker mScrollChecker;
 
    private int oldPadding;
 
    private boolean finish = true;
 
    private int currentPadding;
 
    class ScrollChecker implements Runnable {
 
        private Scroller mScroller;
 
        public ScrollChecker() {
            mScroller = new Scroller(getContext());
        }
 
 
        @Override
        public void run() {
            finish = !mScroller.computeScrollOffset() || mScroller.isFinished();
            if (!finish) {
                if (headView.getPaddingTop() < -mHeaderHeight) {
                    headView.setPadding(0, -mHeaderHeight, 0, 0);
                } else if (headView.getPaddingTop() > 0) {
                    headView.setPadding(0, -mHeaderHeight, 0, 0);
                } else {
                    currentPadding = -mScroller.getCurrY() + oldPadding;
                    headView.setPadding(0, -mScroller.getCurrY() + oldPadding, 0, 0);
                    post(this);
                }
            }
        }
 
        public void tryToScrollTo(int to, int duration) {
            oldPadding = headView.getPaddingTop();
            mScroller.startScroll(0, 0, 0, to, duration);
            post(this);
        }
 
        public void removeCallBacks() {
            removeCallbacks(this);
        }
    }
 
}
