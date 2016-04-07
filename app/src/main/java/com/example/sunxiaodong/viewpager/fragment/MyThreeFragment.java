package com.example.sunxiaodong.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunxiaodong.viewpager.R;
import com.example.sunxiaodong.viewpager.bean.TransformItem;

/**
 * Created by sunxiaodong on 16/4/7.
 */
public class MyThreeFragment extends BaseFragment {

    private View mRootView;
    private TextView mPage;
    private int mIndex = 3;

    public static BaseFragment newInstance() {
        MyThreeFragment baseFragment = new MyThreeFragment();
        /*Bundle argBundle = new Bundle();
        baseFragment.setArguments(argBundle);*/
        return baseFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_layout;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[0];
    }

    @Override
    public int getPageBgColor() {
        return ContextCompat.getColor(getContext(), R.color.color_00ff00);
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
