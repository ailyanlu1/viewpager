package com.example.sunxiaodong.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME = MainActivity.class.getSimpleName();
    private static final String TAG = "sxd";

    private Button mGoPagerAdapter;
    private Button mGoFragmentPagerAdapter;
    private Button mGoFragmentStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mGoPagerAdapter = (Button) this.findViewById(R.id.go_pager_adapter);
        mGoPagerAdapter.setOnClickListener(this);
        mGoFragmentPagerAdapter = (Button) this.findViewById(R.id.go_fragment_pager_adapter);
        mGoFragmentPagerAdapter.setOnClickListener(this);
        mGoFragmentStatePagerAdapter = (Button) this.findViewById(R.id.go_fragment_state_pager_adapter);
        mGoFragmentStatePagerAdapter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_pager_adapter:
                goPagerAdapter();
                break;
            case R.id.go_fragment_pager_adapter:
                goFragmentPagerAdapter();
                break;
            case R.id.go_fragment_state_pager_adapter:
                goFragmentStatePagerAdapter();
                break;
        }
    }

    private void goFragmentStatePagerAdapter() {
        Intent intent = new Intent(this, FragmentStatePagerAdapterActivity.class);
        startActivity(intent);
    }

    private void goFragmentPagerAdapter() {
        Intent intent = new Intent(this, FragmentPagerAdapterActivity.class);
        startActivity(intent);
    }

    private void goPagerAdapter() {
        Intent intent = new Intent(this, PagerAdapterActivity.class);
        startActivity(intent);
    }

}
