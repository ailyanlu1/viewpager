package com.example.sunxiaodong.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME = PagerAdapterActivity.class.getSimpleName();
    private static final String TAG = "sxd";

    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    private TextView mPageNum;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_adapter);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        mPageNum = (TextView) this.findViewById(R.id.page_num);
        mEditText = (EditText) this.findViewById(R.id.edit_text);
        mButton = (Button) this.findViewById(R.id.button);
        mButton.setOnClickListener(this);

        List<View> viewList = new ArrayList<View>();

        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.page1, null);
        View view2 = layoutInflater.inflate(R.layout.page2, null);
        View view3 = layoutInflater.inflate(R.layout.page3, null);
        View view4 = layoutInflater.inflate(R.layout.page4, null);
        View view5 = layoutInflater.inflate(R.layout.page5, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        mMyPagerAdapter = new MyPagerAdapter(viewList);
        mViewPager.setAdapter(mMyPagerAdapter);
        mViewPager.addOnPageChangeListener(new OnMyPageChangeListener());//页面变化监听
        mViewPager.setOffscreenPageLimit(2);//设置缓存页面数。当前页，左右两边（单边）最大缓存页面数。
//        mViewPager.setOnScrollChangeListener(new OnMyScrollChangeListener());//滚动状态监听，minSdkVersion：23
//        mViewPager.getCurrentItem();//获取当前显示页索引
//        mViewPager.getOffscreenPageLimit();//获取缓存页面数
//        mViewPager.onSaveInstanceState();
//        mViewPager.setPageTransformer();
        mMyPagerAdapter.notifyDataSetChanged();//当适配器数据集更改时，调用通知UI更改。PagerAdapter.notifyDataSetChanged() 被触发时，
//         ViewPager.dataSetChanged() 也会被触发。该函数将使用 getItemPosition() 的返回值来进行判断，如果为 POSITION_UNCHANGED，
        // 则什么都不做；如果为 POSITION_NONE，则调用 PagerAdapter.destroyItem() 来去掉该对象，
        // 并设置为需要刷新 (needPopulate = true) 以便触发 PagerAdapter.instantiateItem() 来生成新的对象。
        setPageNum(0);//设置显示首页
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                goPage();
                break;
        }
    }

    private void goPage() {
        String pageNumStr = mEditText.getText().toString();
        if (pageNumStr == null || pageNumStr.isEmpty()) {
            return;
        }
        int pageNum = Integer.parseInt(pageNumStr);
        if (pageNum > 0 && pageNum <= mMyPagerAdapter.getCount()) {
            mViewPager.setCurrentItem(pageNum - 1);//设置当前显示页索引
        }
    }

    private void setPageNum(int position) {
        String pageNum = (position + 1) + "/" + mMyPagerAdapter.getCount();
        mPageNum.setText(pageNum);
    }

    /*private class OnMyScrollChangeListener implements View.OnScrollChangeListener {

        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        }
    }*/

    /**
     * 页面变化监听器。
     * 三个方法的执行顺序为：用手指拖动翻页时，最先执行一遍onPageScrollStateChanged（1），然后不断执行onPageScrolled，放手指的时候，直接立即执行一次onPageScrollStateChanged（2），然后立即执行一次onPageSelected，然后再不断执行onPageScrollStateChanged，最后执行一次onPageScrollStateChanged（0）。
     */
    private class OnMyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //position:当用手指滑动时，如果手指按在页面上不动，position和当前页面index是一致的；如果手指向左拖动（相应页面向右翻动），这时候position大部分时间和当前页面是一致的，只有翻页成功的情况下最后一次调用才会变为目标页面；如果手指向右拖动（相应页面向左翻动），这时候position大部分时间和目标页面是一致的，只有翻页不成功的情况下最后一次调用才会变为原页面。
            //当直接设置setCurrentItem翻页时，如果是相邻的情况（比如现在是第二个页面，跳到第一或者第三个页面），如果页面向右翻动，大部分时间是和当前页面是一致的，只有最后才变成目标页面；如果向左翻动，position和目标页面是一致的。这和用手指拖动页面翻动是基本一致的。
            //如果不是相邻的情况，比如我从第一个页面跳到第三个页面，position先是0，然后逐步变成1，然后逐步变成2；我从第三个页面跳到第一个页面，position先是1，然后逐步变成0，并没有出现为2的情况。
            //positionOffset:当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0。
            //positionOffsetPixels:当前页面滑动像素，变化情况和positionOffset一致。
            Log.i(TAG, NAME + "--onPageScrolled++position:" + position + ",++positionOffset:" + positionOffset + ",++positionOffsetPixels:" + positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            //position是被选中页面的索引，该方法在页面被选中或页面滑动足够距离切换到该页手指抬起时调用。
            Log.i(TAG, NAME + "--onPageSelected++position:" + position);
            setPageNum(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
            //当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，手指抬起时，
            //如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。总共执行这个方法三次。
            //一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方法两次，state值分别是1,0 。
            //当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
            Log.i(TAG, NAME + "--onPageScrollStateChanged++state:" + state);
        }
    }

    /**
     * 页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        MyPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            Log.i(TAG, NAME + "--getCount");
            return mViewList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            //该函数用以返回给定对象的位置，给定对象是由 instantiateItem()的返回值。
            //在 ViewPager.dataSetChanged() 中将对该函数的返回值进行判断，以决定是否最终触发 PagerAdapter.instantiateItem() 函数。
            //在 PagerAdapter 中的实现是直接传回 POSITION_UNCHANGED。如果该函数不被重载，则会一直返回 POSITION_UNCHANGED，
            //从而导致 ViewPager.dataSetChanged() 被调用时，认为不必触发 PagerAdapter.instantiateItem()。很多人因为没有重载该函数，而导致调用
            //PagerAdapter.notifyDataSetChanged() 后，什么都没有发生。
            return super.getItemPosition(object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.i(TAG, NAME + "--isViewFromObject");
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            Log.i(TAG, NAME + "--instantiateItem++container:" + container.getChildCount() + "++position:" + position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
            Log.i(TAG, NAME + "--destroyItem++container:" + container.getChildCount() + "++position:" + position);
        }

    }

}
