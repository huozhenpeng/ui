package com.example.huozhenpeng.interviewui.interactive;
 
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.List;
 
public class InteractiveActivity extends AppCompatActivity {
 
    private ListView listview;
 
    private MyAdapter myAdapter;
 
    private List<String> lists;
 
    private View headView;
 
    private ImageView iv_2;
 
    private ImageView iv_3;
 
    private View key_view;
 
    private HorizontalScrollView horizontalScrollView;
 
 
    private LinearLayout ll_parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive);
        iv_2= (ImageView) findViewById(R.id.iv_2);
 
        iv_3= (ImageView) findViewById(R.id.iv_3);
        headView= LayoutInflater.from(this).inflate(R.layout.head,null);
        listview= (ListView) findViewById(R.id.listview);
 
        key_view=findViewById(R.id.key_view);
 
        ll_parent= (LinearLayout) findViewById(R.id.ll_parent);
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        lists=new ArrayList<>();
 
        for(int i=0;i<20;i++)
        {
            lists.add(""+i);
        }
        myAdapter=new MyAdapter(this,lists);
        listview.addHeaderView(headView);
        listview.setAdapter(myAdapter);
 
 
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
 
            }
 
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
 
                changeHeadHeight(getScrollY());
            }
        });
    }
 
    private void changeHeadHeight(int height)
    {
        Log.e("abc",height+"aaaaaaa");
        if (height > 500) height = 500;
        ViewGroup.LayoutParams layoutParams =  ll_parent.getLayoutParams();
        layoutParams.height = 750-height;
        ll_parent.setLayoutParams(layoutParams);
 
        ViewGroup.LayoutParams kayParams =  key_view.getLayoutParams();
        kayParams.width = 500+2*height;
        key_view.setLayoutParams(kayParams);
 
 
        makeAnimation(height*1.0f/500);
    }
 
    private void makeAnimation(float fraction)
    {
 
        iv_2.setTranslationY(evaluate(fraction,0,-250));
        iv_2.setTranslationX(evaluate(fraction,0,500));
 
        iv_3.setTranslationY(evaluate(fraction,0,-500));
        iv_3.setTranslationX(evaluate(fraction,0,1000));
    }
 
 
    /**
     * @param fraction   变换因子
     * @param startValue 起始值
     * @param endValue   结束指
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
    /**
     * 使用与listview的item高度都相同的情况，head无所谓
     * @return
     */
    public int getScrollY() {
        View c = listview.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        int top = c.getTop();
        if(firstVisiblePosition==0)
        {
            return -top;
        }
        else
        {
            //只适合本项目
            return  iv_2.getHeight()+iv_3.getHeight();
        }
 
    }
 
}
