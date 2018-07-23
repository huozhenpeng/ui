package com.example.huozhenpeng.interviewui.pulldownrefresh;
 
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huozhenpeng.interviewui.MToast;
import com.example.huozhenpeng.interviewui.R;

import java.util.ArrayList;
import java.util.List;
 
public class PullDownActivity extends AppCompatActivity {
 
 
    private ListView customerlistview;
 
    private MyAdapter myAdapter;
 
    private List<String> lists;
 
    private LinearLayout activity_main;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity_main= (LinearLayout) findViewById(R.id.activity_main);
//        customerlistview= (ListView) findViewById(R.id.customerlistview);
        customerlistview=new CustomerListView(this) {
            @Override
            public View addHeadView() {
                TextView textView=new TextView(PullDownActivity.this);
                textView.setBackgroundColor(Color.parseColor("#ff00ff"));
                textView.setGravity(Gravity.CENTER);
                textView.setText("listview头部");
                AbsListView.LayoutParams params=new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,200);
                textView.setLayoutParams(params);
                addHeaderView(textView);
                return textView;
            }
        };
        customerlistview.setSelector(R.drawable.bg_item);
        activity_main.addView(customerlistview);
        lists=new ArrayList<>();
 
        for(int i=0;i<20;i++)
        {
            lists.add(""+i);
        }
        myAdapter=new MyAdapter(this,lists);
        customerlistview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
 
        customerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String desc = (String) parent.getAdapter().getItem(position);
                MToast.showToast(PullDownActivity.this,desc);
            }
        });
    }
}
