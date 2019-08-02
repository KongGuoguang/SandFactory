package com.fenjin.sandfactory.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.fragment.FirstFragment;
import com.fenjin.sandfactory.fragment.MeFragment;
import com.fenjin.sandfactory.fragment.QueryFragment;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private QMUITabSegment tabSegment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabs();
        initPagers();
    }

    private void initTabs(){
        tabSegment = findViewById(R.id.tabs);
        //tabSegment.setDefaultSelectedColor(Color.parseColor("#3076f6"));
        QMUITabSegment.Tab workRecord = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.ic_vehicle_normal),
                ContextCompat.getDrawable(this, R.mipmap.ic_vehicle_pressed),
                "首页",false);
        QMUITabSegment.Tab modifyAudit = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.ic_tab_search_normal),
                ContextCompat.getDrawable(this, R.mipmap.ic_tab_search_pressed),
                "查询",false);
//        QMUITabSegment.Tab workStatistics = new QMUITabSegment.Tab(
//                ContextCompat.getDrawable(this, R.mipmap.ic_monitor_normal),
//                ContextCompat.getDrawable(this, R.mipmap.ic_monitor_selected),
//                "监控",false);
        QMUITabSegment.Tab me = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.ic_mine_normal),
                ContextCompat.getDrawable(this, R.mipmap.ic_mine_pressed),
                "我的",false);
        tabSegment.addTab(workRecord)
                .addTab(modifyAudit)
//                .addTab(workStatistics)
                .addTab(me);
    }

    private void initPagers(){
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new QueryFragment());
//        fragments.add(new MonitorFragment());
        fragments.add(new MeFragment());

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabSegment.setupWithViewPager(viewPager, false);
    }
}
