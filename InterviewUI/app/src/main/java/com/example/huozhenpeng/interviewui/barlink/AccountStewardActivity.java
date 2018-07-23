package com.example.huozhenpeng.interviewui.barlink;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.huozhenpeng.interviewui.FloatUtil;
import com.example.huozhenpeng.interviewui.JsonUtils;
import com.example.huozhenpeng.interviewui.MToast;
import com.example.huozhenpeng.interviewui.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
 
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * @author huozhenpeng
 *         资产配置分析
 */
public class AccountStewardActivity extends Activity implements OnClickListener {
    private List<AccountAsset> lists;
    private RotationView rotationView;
    private ArrowView arrowView;
    private ListView listview;
    private ScrollAdapter scrollAdapter;
    private int position;
    private int top;
    private int itemHeight;
    // 静止之后实际的position
    private int actualPosition;
    private MyRunnable myRunnable = new MyRunnable();
    private List<AccountAsset> tempLists = new ArrayList<>();
    private boolean isListMove = true;
    private boolean isInit = true;
    private TextView tv_money;
    private MyHandler handler;
    private ImageView left_img;
    private TextView tv_amounttips;
    ViewTreeObserver observer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvents();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        },200);
    }

    @Override
    public void onClick(View view) {
 
        switch (view.getId())
        {
            case R.id.left_img:
                finish();
                break;
        }
 
    }
 
    protected void initViews() {
        setContentView(R.layout.activity_accountsteward);
        tv_amounttips= (TextView) this.findViewById(R.id.tv_amounttips);
        left_img= (ImageView) this.findViewById(R.id.left_img);
        tv_money = (TextView) this.findViewById(R.id.tv_money);
        rotationView = (RotationView) this.findViewById(R.id.rotateView);
        arrowView = (ArrowView) this.findViewById(R.id.arrowView);
        map = new HashMap<>();
        handler = new MyHandler(this);
        lists = new ArrayList<>();
        listview = (ListView) this.findViewById(R.id.listview);
        scrollAdapter = new ScrollAdapter(this, lists);
        listview.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (isInit) {
                            isInit = false;
                            int[] position = new int[2];
                            listview.getLocationOnScreen(position);
 
                            Display display = getWindowManager()
                                    .getDefaultDisplay();// 得到当前屏幕的显示器对象
                            Point size = new Point();// 创建一个Point点对象用来接收屏幕尺寸信息
                            display.getSize(size);// Point点对象接收当前设备屏幕尺寸信息
                            int width = size.x;// 从Point点对象中获取屏幕的宽度(单位像素)
                            int vHeight = size.y;// 从Point点对象中获取屏幕的高度(单位像素)
 
                            // 获取每个item的高度
                            View itemView = LayoutInflater.from(
                                    AccountStewardActivity.this).inflate(
                                    R.layout.item_scroll, null);
                            View view = new View(AccountStewardActivity.this);
                            itemHeight = measure_ll_transfer1Height(itemView);
                            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, vHeight
                                    - position[1] - itemHeight);
                            view.setLayoutParams(layoutParams);
                            listview.addFooterView(view);
                            listview.setAdapter(scrollAdapter);
 
                            FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (itemHeight * 4 / 3.0f));
                            arrowView.setLayoutParams(layoutParams1);
                            arrowView.invalidate();
 
                        }
                    }
                });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
 
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:// 手指离开屏幕后，惯性滑动
 
                        break;
                    case SCROLL_STATE_IDLE:// 滑动后静止
                        if (isListMove) {
                            position = listview.getFirstVisiblePosition();// 第几个item
                            top = getViewByPosition(position, listview).getTop();
                            if (-top <= itemHeight / 2) {
                                myRunnable.setPosition(position);
                                actualPosition = position;
                            } else {
                                myRunnable.setPosition(position + 1);
                                actualPosition = position + 1;
                            }
                            myRunnable.setOffset(0);
                            listview.post(myRunnable);
                        }
 
 
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:// 手指在屏幕上滑动
                        break;
 
                    default:
                        break;
                }
            }
 
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //启动时getScrollY为0
                if (isListMove) {
                    callbackPie1(getScrollY());
                } else {
                    oldDistance = getScrollY();
                }
                changeColor(getScrollY());
 
            }
        });
 
        listview.setOnTouchListener(new View.OnTouchListener() {
 
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isListMove = true;
                return false;
            }
        });
        rotationView.setListViewMoveListener(new RotationView.OnListViewMoveListener() {
 
            @Override
            public void onMove(double degrees) {
                isListMove = false;
                lists.get(0);
                int index = -1;
                double offest = 0;
                for (int i = 0; i < lists.size() - 1; i++) {
                    MyPoint point1 = lists.get(i).getPoint();
                    MyPoint point2 = lists.get(i + 1).getPoint();
                    if (point1.getX3() <= degrees && degrees <= point1.getX2()) {
                        offest = itemHeight / 2.0f * (degrees - point1.getX3()) / (point1.getX2() - point1.getX3());
                        index = i;
                        break;
                    } else if (point1.getX2() <= degrees && degrees <= point2.getX3()) {
                        offest = itemHeight / 2.0f + itemHeight / 2.0f * (degrees - point1.getX2()) / (point2.getX3() - point1.getX2());
                        index = i;
                        break;
                    }
                }
                if(degrees==0)
                {
                    index = lists.size()-1;
                    offest = 0;
                }
                //index变化的时候会闪屏
//				listview.smoothScrollToPositionFromTop(index, -(int)offest, 0);
                //不会回调onScroll方法
//				listview.scrollTo(0, index*itemHeight+(int)offest);
                listview.setSelectionFromTop(index, -(int) offest);
 
            }
        });
 
    }
 
    protected void initEvents() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i<lists.size()) {
                    MToast.showToast(AccountStewardActivity.this,lists.get(i).getAssetType()+"'");
                }
            }
        });
        left_img.setOnClickListener(this);
    }
 
    private Map<String, String> map;
 
    protected void loadData() {
        String result="{\n" +
                "  \"data\" : [\n" +
                "    {\n" +
                "      \"assetType\" : 1,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.25\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 2,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.1\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 3,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.1\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 4,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.3\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 5,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.15\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 6,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.05\n" +
                "    },\n" +
                "    {\n" +
                "      \"assetType\" : 7,\n" +
                "      \"groupYesterdayAsset\" : 1234,\n" +
                "      \"groupYesterdayProfit\" : 0,\n" +
                "      \"percent\" : 0.05\n" +
                "    }\n" +
                "  ],\n" +
                "  \"msg\" : \"\",\n" +
                "  \"state\" : 1\n" +
                "}";
        Message message = Message.obtain();
        message.obj = result;
        message.what = 0x01;
        handler.sendMessage(message);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
 
    private static class MyHandler extends Handler {
        private WeakReference<AccountStewardActivity> _activity;
 
        public MyHandler(AccountStewardActivity activity) {
            _activity = new WeakReference<>(activity);
        }
 
        @Override
        public void handleMessage(Message msg) {
            AccountStewardActivity activity = _activity.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case 0x01:
                    String result = (String) msg.obj;
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jo = null;
                        try {
                            jo = new JSONObject(result);
                            int state = jo.getInt("state");
                            String resultMsg = jo.optString("msg");
                            String data = jo.optString("data");
                            if (state == 1) {
                                List<AccountAsset> lists = JsonUtils.toBean(data, new TypeToken<List<AccountAsset>>() {
                                }.getType());
                                if (lists != null && lists.size() > 0) {
                                    for (int i = 0; i < lists.size(); i++) {
                                        lists.get(i).setDegrees((int) (lists.get(i).getPercent()*360));
                                    }
                                    //处理原始数据，percent不为0，但是度数为0的，分配1度，
                                    //所有度数和大于360的从最大中减去，小于360的加给最大值
                                    AccountAsset accountAsset=null;
                                    int Degrees=0;
                                    for(int i=0;i<lists.size();i++)
                                    {
                                        accountAsset=lists.get(i);
                                        if(accountAsset.getPercent()>0&&accountAsset.getDegrees()<1)
                                        {
                                            accountAsset.setDegrees(1);
                                        }
                                    }
                                    for(int i=0;i<lists.size();i++)
                                    {
                                        Degrees+=lists.get(i).getDegrees();
                                    }
                                    lists.get(0).setDegrees(lists.get(0).getDegrees()+(360-Degrees));
                                    for (int i = 0; i < lists.size(); i++) {
                                        if (i == 0) {
                                            activity.tempLists.add(lists.get(0));
                                        } else {
                                            activity.tempLists.add(lists.get(lists.size() - i));
                                        }
                                    }
                                    activity.rotationView.setData(activity.tempLists);
                                    activity.lists.clear();
                                    activity.lists.addAll(lists);
                                    activity.scrollAdapter.notifyDataSetChanged();
 
                                }
                            }
 
                        } catch (Exception e) {
                            e.printStackTrace();
 
                        }
                    }
 
                    break;
                case 0x02:
                    break;
            }
        }
    }
 
    private float oldDistance;
 
    /**
     * 让饼图联动，根据listview滑动的距离
     * itemHeight : 147
     * distance   :  147*3=447
     * <p>
     * 问题：每个delta可能跨两个或者多个扇形,快速滑动，回调是不连续的
     */
    private void callbackPie1(float distance) {
        if (itemHeight == 0)
            return;
        if(lists==null||lists.size()==0)
            return;
        float delta = distance - oldDistance;
        float reminder = Math.max(distance, oldDistance) % itemHeight;
        int divider = (int) (Math.max(distance, oldDistance) / itemHeight);
 
        //判断delta是否跨扇形
        int location = (int) (distance / (itemHeight / 2.0f));
        int oldLocation = (int) (oldDistance / (itemHeight / 2.0f));
        int deltaLocation = location - oldLocation;
        if (deltaLocation > 0 && !(deltaLocation == 0 || Math.abs(deltaLocation) == 1 && distance % (itemHeight / 2.0f) == 0)) {
            for (int i = 1; i <= deltaLocation; i++) {
                callbackPie1((oldLocation + 1) * itemHeight / 2.0f);
            }
            callbackPie1(distance);
 
        } else if (deltaLocation < 0 && !(deltaLocation == 0 || Math.abs(deltaLocation) == 1 && (Math.max(distance, oldDistance) % (itemHeight / 2.0f) == 0))) {
            oldLocation = oldLocation + 1;
            if (oldDistance % (itemHeight / 2.0f) == 0) {
                deltaLocation += 1;
            }
            for (int i = 1; i <= Math.abs(deltaLocation); i++) {
                if (oldDistance % (itemHeight / 2.0f) == 0) {
                    oldLocation = oldLocation - 1;
                }
                callbackPie1((oldLocation - 1) * itemHeight / 2.0f);
            }
            callbackPie1(distance);
        } else {
            //刚刚开始distance和oldDistance都是0
            if (reminder <= (itemHeight / 2.f)) {
                rotationView.rotateWheel3(lists.get(divider).getDegrees() * 1.0f
                        / 2 / (itemHeight / 2.0f) * delta);
            } else {
                if (divider <= tempLists.size() - 1) {
                    rotationView.rotateWheel3(lists.get(divider + 1).getDegrees() * 1.0f
                            / 2 / (itemHeight / 2.0f) * delta);
                } else {
                    rotationView.rotateWheel3(lists.get(0).getDegrees() * 1.0f
                            / 2 / (itemHeight / 2.0f) * delta);
                }
            }
            oldDistance = distance;
        }
 
    }
 
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;
 
        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
 
    private int measure_ll_transfer1Height(View view) {
        view.getLayoutParams();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = 0;
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.AT_MOST);
        } else {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY);
        }
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }
 
    class MyRunnable implements Runnable {
 
        private int position;
        private int offset;
 
        public void setPosition(int position) {
            this.position = position;
        }
 
        public void setOffset(int offset) {
            this.offset = offset;
        }
 
        @Override
        public void run() {
            listview.smoothScrollToPositionFromTop(position, offset, 500);
        }
    }
 
    public int getScrollY() {
        View c = listview.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }
 
    private int oldIndex = -1;
 
    private void changeColor(int distance) {
        if(lists==null||lists.size()==0)
            return;
 
        if (listview != null && listview.getAdapter() != null) {
            int index = 0;
            if (distance < itemHeight) {
                index = 0;
            } else {
                index = (int) ((distance) / (itemHeight));
 
            }
            if (index != oldIndex) {
                if(oldIndex>=0) {
                    //percent太小，转动过快，造成index和oldindex不连续
                    int max=Math.max(index,oldIndex);
                    int min=Math.min(index,oldIndex);
                    for(int i=max;i>min;i--)
                    {
                        View view1 = getViewByPosition(i, listview);
                        RelativeLayout relativeLayout = (RelativeLayout) view1.findViewById(R.id.rl_background);
                        relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        LinearLayout linearLayout = (LinearLayout) view1.findViewById(R.id.ll_parent);
                        View deviderView = linearLayout.findViewById(R.id.v_devider);
                        deviderView.setBackgroundColor(Color.parseColor("#dcdcdc"));
                        arrowView.setVirtualColor("#ffffff");
                    }
 
                }
                View view = getViewByPosition(index, listview);
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_background);
                relativeLayout.setBackgroundColor(Color.parseColor(lists.get(index).getVirtualColor()));
                LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.ll_parent);
                View deviderView = linearLayout.findViewById(R.id.v_devider);
                deviderView.setBackgroundColor(Color.parseColor(lists.get(index).getColor()));
                arrowView.setVirtualColor(lists.get(index).getVirtualColor());
                arrowView.setColor(lists.get(index).getColor());
 
            }
 
            try
            {
                tv_money.setText(FloatUtil.toTwoDianStringSeparator(lists.get(index).getGroupYesterdayAsset()));
            }
            catch (Exception e)
            {
                tv_money.setText(lists.get(index).getGroupYesterdayAsset() + "");
            }
            tv_amounttips.setText(getProductType(lists.get(index)));
            oldIndex = index;
 
        }
 
    }
 
    private String  getProductType(AccountAsset accountAsset) {
        String name="";
        switch (accountAsset.getAssetType()) {
            case 1://银行存款
                name="银行存款";
                break;
            case 2://p2p
                name="P2P";
                break;
            case 3://宝宝类
                name="宝宝类";
                break;
            case 4://基金
                name="基金";
                break;
            case 5://股票
                name="股票";
                break;
            case 6://银行理财
                name="银行理财";
                break;
            case 7://信托
                name="信托/资管";
                break;
            case 8://自定义资产
                name="其他资产";
                break;
            default:
                name=AccountStewardActivity.this.getResources().getString(R.string.default_value);
                break;
        }
        return name+"(元)";
    }
}
