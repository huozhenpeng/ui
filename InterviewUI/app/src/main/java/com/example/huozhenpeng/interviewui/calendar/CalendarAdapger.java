package com.example.huozhenpeng.interviewui.calendar;
 
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
 
import java.util.List;
 
/**
 * Created by ${huozhenpeng} on 17/3/27.
 * Company : www.miduo.com
 */
 
public class CalendarAdapger extends PagerAdapter {
 
 
    private List<viewPagerItem> lists;
    private Context context;
    public CalendarAdapger(List<viewPagerItem> lists,Context context)
    {
 
        this.lists=lists;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
 
 
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("abc","destroyItem----->"+(position%3));
//        container.removeView(lists.get(position%3));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("abc","instantiateItem------>"+(position%3));
 
        viewPagerItem viewPagerItem=lists.get(position%3);
        String log="";
 
        for(int i=0;i<viewPagerItem.getLists().size();i++)
        {
            log+=viewPagerItem.getLists().get(i).getDate()+"----";
 
        }
        Log.e("abc",log);
 
        ViewGroup parentView= (ViewGroup) viewPagerItem.getParent();
        if(parentView!=null)
        {
            parentView.removeView(viewPagerItem);
        }
        container.addView(lists.get(position%3));
        return lists.get(position%3);
    }
 
}
