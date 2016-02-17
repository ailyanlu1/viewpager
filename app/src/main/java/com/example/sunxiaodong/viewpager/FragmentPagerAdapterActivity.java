package com.example.sunxiaodong.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

/**
 * Created by sunxiaodong on 16/1/27.
 * 该适配器最好用于有限个静态fragment页面的管理。尽管不可见的视图有时会被销毁，但用户所有访问过的fragment都会被保存在内存中。因此fragment实例会保存大量的各种状态，这就造成了很大的内存开销。
 * 所以如果要处理大量的页面切换，建议使用FragmentStatePagerAdapter.
 */
public class FragmentPagerAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME = FragmentPagerAdapterActivity.class.getSimpleName();
    private static final String TAG = "sxd";

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
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

        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
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
        if (pageNum > 0 && pageNum <= mMyFragmentPagerAdapter.getCount()) {
            mViewPager.setCurrentItem(pageNum - 1);//设置当前显示页索引
        }
    }

    private void setPageNum(int position) {
        String pageNum = (position + 1) + "/" + mMyFragmentPagerAdapter.getCount();
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
     * Fragment页面适配器
     * ViewPager在进行Fragment界面切换时，会将超过缓存数的界面销毁，但不销毁数据，即调用Fragment的onDestoryView方法
     * 所有创建过的Fragment都会被保留
     */
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            //FragmentPagerAdapter 会将所有生成的 Fragment 对象通过 FragmentManager 保存起来备用，以后需要该 Fragment 时，
            // 都会从 FragmentManager 读取，而不会再次调用 getItem() 方法。该方法只有在创建新的Fragment时才调用。
            //1. 在需要时，该函数将被 instantiateItem() 所调用。
            //2. 如果需要向 Fragment 对象传递相对静态的数据时，我们一般通过 Fragment.setArguments() 来进行，这部分代码应当放到 getItem()。它们只会在新生成 Fragment 对象时执行一遍。
            Log.i(TAG, NAME + "--getItem++position:" + position);
            return mFragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //该方法每次生成缓存页面之外的Fragment时都会调用，这里的Fragment可能是新的生成，也可能是恢复
            //1.函数中判断一下要生成的 Fragment 是否已经生成过了，如果生成过了，就使用旧的，旧的将被 Fragment.attach()；如果没有，就调用 getItem() 生成一个新的，新的对象将被 FragmentTransation.add()。
            //2. FragmentPagerAdapter 会将所有生成的 Fragment 对象通过 FragmentManager 保存起来备用，以后需要该 Fragment 时，都会从 FragmentManager 读取，而不会再次调用 getItem() 方法。
            //3. 如果需要在生成 Fragment 对象后，将数据集中的一些数据传递给该 Fragment，这部分代码应该放到这个函数的重载里。在我们继承的子类中，重载该函数，并调用 FragmentPagerAdapter.instantiateItem() 取得该函数返回 Fragment 对象，然后，我们该 Fragment 对象中对应的方法，将数据传递过去，然后返回该对象。
            //否则，如果将这部分传递数据的代码放到 getItem()中，在 PagerAdapter.notifyDataSetChanged() 后，这部分数据设置代码将不会被调用。
            Log.i(TAG, NAME + "--instantiateItem++container:" + container.getChildCount() + "++position:" + position);
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //超出缓存的页面，将调用该方法从视图中移除
            //该函数被调用后，会对 Fragment 进行 FragmentTransaction.detach()。这里不是 remove()，只是 detach()，因此 Fragment 还在 FragmentManager 管理中，Fragment 所占用的资源不会被释放。
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
