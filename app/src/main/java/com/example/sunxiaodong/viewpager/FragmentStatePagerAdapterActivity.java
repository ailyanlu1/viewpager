package com.example.sunxiaodong.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxiaodong on 16/1/28.
 */
public class FragmentStatePagerAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME = FragmentStatePagerAdapterActivity.class.getSimpleName();
    private static final String TAG = "sxd";

    private ViewPager mViewPager;
    private MyFragmentStatePagerAdapter mMyFragmentStatePagerAdapter;
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

        List<Fragment> fragments = new ArrayList<Fragment>();
        Fragment fragment1 = MyFragment.newInstance(1);
        Fragment fragment2 = MyFragment.newInstance(2);
        Fragment fragment3 = MyFragment.newInstance(3);
        Fragment fragment4 = MyFragment.newInstance(4);
        Fragment fragment5 = MyFragment.newInstance(5);


        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

        mMyFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mMyFragmentStatePagerAdapter);
        mViewPager.addOnPageChangeListener(new OnMyPageChangeListener());//页面变化监听
        mViewPager.setOffscreenPageLimit(2);//设置缓存页面数。当前页，左右两边（单边）最大缓存页面数。
//        mViewPager.setOnScrollChangeListener(new OnMyScrollChangeListener());//滚动状态监听，minSdkVersion：23
//        mViewPager.getCurrentItem();//获取当前显示页索引
//        mViewPager.getOffscreenPageLimit();//获取缓存页面数
//        mViewPager.onSaveInstanceState();
//        mViewPager.setPageTransformer();
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
        if (pageNum > 0 && pageNum <= mMyFragmentStatePagerAdapter.getCount()) {
            mViewPager.setCurrentItem(pageNum - 1);//设置当前显示页索引
        }
    }

    private void setPageNum(int position) {
        String pageNum = (position + 1) + "/" + mMyFragmentStatePagerAdapter.getCount();
        mPageNum.setText(pageNum);
    }

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
     * 能够保存和恢复Fragment状态的适配器
     * 超出缓存的Fragment会调用onDestory()方法，彻底对销毁Fragment进行销毁
     */
    class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;

        MyFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            //该方法在滑到已经缓存的页面时，并不被调用。缓存外已经创建过并被销毁的页面，还会再调用该方法，重新创建。
            Log.i(TAG, NAME + "--getItem++position:" + position);
            return mFragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //除非碰到 FragmentManager 刚好从 SavedState 中恢复了对应的 Fragment 的情况外(从页面缓存中恢复)，该函数将会调用 getItem() 函数，生成新的 Fragment 对象。新的对象将被 FragmentTransaction.add()。
            Log.i(TAG, NAME + "--instantiateItem++container:" + container.getChildCount() + "++position:" + position);
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //超出缓存的页面，将调用该方法从视图中移除
            Log.i(TAG, NAME + "--destroyItem++container:" + container.getChildCount() + "++position:" + position);
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            Log.i(TAG, NAME + "--getCount++");
            return mFragments.size();
        }
    }

}
