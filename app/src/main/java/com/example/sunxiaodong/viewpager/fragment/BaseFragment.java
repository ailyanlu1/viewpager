package com.example.sunxiaodong.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sunxiaodong.viewpager.R;
import com.example.sunxiaodong.viewpager.bean.LayersHolder;
import com.example.sunxiaodong.viewpager.bean.TransformItem;

/**
 * Created by sunxiaodong on 16/1/27.
 */
public abstract class BaseFragment extends Fragment {

    private static final String NAME = BaseFragment.class.getSimpleName();
    private static final String TAG = "sxd";

    private LayersHolder holder;

    @Nullable
         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        holder = new LayersHolder(view, provideTransformItems());
        view.setTag(R.id.st_fragment, this);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void transformPage(View view, float position) {
        holder.transformPage(view.getWidth(), position);
        Log.i(TAG, NAME + "--transformPage++position:" + position);
    }

    protected abstract int getLayoutResId();

    protected abstract TransformItem[] provideTransformItems();

    public abstract int getPageBgColor();

}