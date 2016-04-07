package com.example.sunxiaodong.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunxiaodong.viewpager.MyFragment;
import com.example.sunxiaodong.viewpager.R;
import com.example.sunxiaodong.viewpager.bean.TransformItem;

/**
 * Created by sunxiaodong on 16/4/7.
 */
public class MyOneFragment extends BaseFragment {

    private View mRootView;
    private TextView mPage;
    private int mIndex = 1;

    public static BaseFragment newInstance() {
        MyOneFragment baseFragment = new MyOneFragment();
        /*Bundle argBundle = new Bundle();
        baseFragment.setArguments(argBundle);*/
        return baseFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_layout_one;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{
                new TransformItem(R.id.ivFirstImage, true, 20),
                new TransformItem(R.id.ivSecondImage, false, 6),
                new TransformItem(R.id.ivThirdImage, true, 8),
                new TransformItem(R.id.ivFourthImage, false, 10),
                new TransformItem(R.id.ivFifthImage, false, 3),
                new TransformItem(R.id.ivSixthImage, false, 9),
                new TransformItem(R.id.ivSeventhImage, false, 14),
                new TransformItem(R.id.ivEighthImage, false, 7)
        };
    }

    @Override
    public int getPageBgColor() {
        return ContextCompat.getColor(getContext(), R.color.color_ff0000);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        return mRootView;
    }

    private void initView() {
        mPage = (TextView) mRootView.findViewById(R.id.page);
        mPage.setText("page" + mIndex);
    }

}
