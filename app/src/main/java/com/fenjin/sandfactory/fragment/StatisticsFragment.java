package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.bean.ChartStatisticsItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.StatisticQueryActivity;
import com.fenjin.sandfactory.databinding.FragmentStaticBinding;
import com.fenjin.sandfactory.databinding.LayoutPopBarChartValueBinding;
import com.fenjin.sandfactory.viewmodel.StatisticsViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseFragment {


    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static final int STATISTIC_TYPE_SITE = 0;
    public static final int STATISTIC_TYPE_COMPANY = 1;
    public static final int STATISTIC_TYPE_BALANCE = 2;

    private StatisticsViewModel viewModel;

    private QMUITipDialog loadingDialog;

    private QMUIPullRefreshLayout pullRefreshLayout;

    private TabLayout tabLayout;

    private BarChart barChart;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
        registerObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStaticBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_static, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pullRefreshLayout = view.findViewById(R.id.layout_pull_refresh);
        pullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {
            }

            @Override
            public void onMoveRefreshView(int offset) {
            }

            @Override
            public void onRefresh() {

            }
        });

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("今日"));
        tabLayout.addTab(tabLayout.newTab().setText("本月"));
        tabLayout.addTab(tabLayout.newTab().setText("本年"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == null) return;
                if ("今日".equals(tab.getText().toString())) {
                    viewModel.loadTodayChengZhongStatistics();
                } else if ("本月".equals(tab.getText().toString())) {
                    viewModel.loadMonthChengZhongStatistics();
                } else if ("本年".equals(tab.getText().toString())) {
                    viewModel.loadYearChengZhongStatistics();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();

        barChart = view.findViewById(R.id.bar_chart);
        initBarChart();


        viewModel.loadTodayChengZhongStatistics();
    }


    private void initBarChart() {
        LogUtils.d("StatisticsFragment, initBarChart()");
        barChart.setNoDataText("暂无数据");

        //是否能缩放(总开关)
        barChart.setScaleEnabled(true);
        // 双指缩放
        barChart.setPinchZoom(true);
        //双击缩放
        barChart.setDoubleTapToZoomEnabled(true);
        //拖拽
        barChart.setDragEnabled(true);
        //X轴或Y轴缩放
        barChart.setScaleXEnabled(true);
        barChart.setScaleYEnabled(false);

        barChart.animateY(500);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                List<ChartStatisticsItem> items = viewModel.dataRepository.getChartItemList();
                int index = (int) Math.floor(e.getX());
                if (index < 0 || index > items.size()) {
                    return;
                }

                LayoutPopBarChartValueBinding binding = DataBindingUtil.inflate(getLayoutInflater(),
                        R.layout.layout_pop_bar_chart_value, null, false);
                View view = binding.getRoot();
                binding.setViewModel(items.get(index));

                PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);

                popupWindow.setOutsideTouchable(true);

                int offsetX = (int) (getResources().getDisplayMetrics().density * 125);
                popupWindow.showAsDropDown(tabLayout, offsetX, 0);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //不显示图表描述
        Description description = barChart.getDescription();
        description.setEnabled(false);

        //设置图例在右上角
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    }

    private void showBarChart() {
        final List<ChartStatisticsItem> chartStatisticsItems = viewModel.dataRepository.getChartItemList();
        LogUtils.d("StatisticsFragment, chartStatisticsItems = " + chartStatisticsItems.toString());

        /*    设置X轴坐标  */
        XAxis xAxis = barChart.getXAxis();
        //是否显示x坐标的数据
        xAxis.setDrawLabels(true);
        //设置x坐标数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否显示网格线中与x轴垂直的网格线
        xAxis.setDrawGridLines(false);
        //设置X轴坐标个数
        xAxis.setLabelCount(chartStatisticsItems.size());
        //设置X轴坐标最小值
        xAxis.setAxisMinimum(0f);
        //X轴显示Value值的精度
        xAxis.setGranularity(1f);
        //设置X轴坐标最大值
        xAxis.setAxisMaximum(chartStatisticsItems.size());
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);
        //设置x轴标签格式化器，将自动生成的float型坐标值转换成自定义String类型
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                //value是根据设置的坐标最小值、最大值以及精度自动生成的
                //此处将其转换为chartItem的month字段
                if (value < 0 || value >= chartStatisticsItems.size()) return "";

                String month = chartStatisticsItems.get((int) value).getMonth().substring(5) + "月";
                if (month.startsWith("0")) {
                    month = month.replace("0", "");
                }
                return month;
            }
        });

        /*        设置右侧Y轴          */
        YAxis rightYAxis = barChart.getAxisRight();
        //只有左右y轴标签都设置不显示水平网格线，图形才不会显示网格线
        rightYAxis.setDrawGridLines(false);
        //设置右侧的y轴是否显示。包括y轴的那一条线和上面的标签都不显示
        rightYAxis.setEnabled(true);
        //设置y轴右侧的标签是否显示。只是控制y轴处的标签。控制不了那根线。
        rightYAxis.setDrawLabels(true);
        //这个方法就是专门控制坐标轴线的
        rightYAxis.setDrawAxisLine(true);
        //设置Y轴标签格式化器
        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setLabelCount(5);
        rightYAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value <= 0) return "";
                return subZeroAndDot(String.valueOf(value));
            }
        });

        /*        设置左侧Y轴          */
        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setEnabled(true);
        leftYAxis.setDrawLabels(true);
        leftYAxis.setDrawAxisLine(true);
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //只有左右y轴标签都设置不显示水平网格线，图形才不会显示网格线
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setLabelCount(5);
        //自定义左侧标签的字符串，可以是任何的字符串、中文、英文等
        leftYAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value <= 0) return "";
                return subZeroAndDot(String.valueOf(value));
            }
        });

        ArrayList<BarEntry> weightEntries = new ArrayList<>();
        ArrayList<BarEntry> moneyEntries = new ArrayList<>();


        for (int i = 0; i < chartStatisticsItems.size(); i++) {
            ChartStatisticsItem item = chartStatisticsItems.get(i);

            BarEntry weightEntry = new BarEntry(i, item.getWeight());
            weightEntries.add(weightEntry);

            BarEntry moneyEntry = new BarEntry(i, item.getMoney());
            moneyEntries.add(moneyEntry);
        }

        BarDataSet weightDataSet = new BarDataSet(weightEntries, "重量");
        weightDataSet.setColor(ContextCompat.getColor(getContext(), R.color.blue));
        weightDataSet.setAxisDependency(barChart.getAxisLeft().getAxisDependency());
        weightDataSet.setDrawValues(false);

        BarDataSet moneyDataSet = new BarDataSet(moneyEntries, "金额");
        moneyDataSet.setColor(ContextCompat.getColor(getContext(), R.color.orange));
        moneyDataSet.setAxisDependency(barChart.getAxisRight().getAxisDependency());
        moneyDataSet.setDrawValues(false);

        BarData barData = new BarData(weightDataSet, moneyDataSet);
        int barAmount = barData.getDataSetCount(); //需要显示柱状图的类别 数量
        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        float groupSpace = 0.2f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0f;
        //设置柱状图宽度
        barData.setBarWidth(barWidth);
        //(起始点、柱状图组间距、柱状图之间间距)
        barData.groupBars(0f, groupSpace, barSpace);
        barChart.setData(barData);
        barChart.invalidate();

    }

    private void registerObserver(){

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    if (loadingDialog == null){
                        loadingDialog = new QMUITipDialog.Builder(getActivity())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                        loadingDialog.setCancelable(true);
                    }
                    loadingDialog.show();
                }else {
                    pullRefreshLayout.finishRefresh();
                    if (loadingDialog != null && loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        viewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                dealErrorCode(integer);
            }
        });

        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });

        viewModel.showBarChart.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showBarChart();
                }
            }
        });

        viewModel.statisticContent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer == null) return;
                if (integer == STATISTIC_TYPE_SITE) {
                    Intent intent = new Intent(getActivity(), StatisticQueryActivity.class);
                    intent.putExtra(StatisticQueryActivity.QUERY_TYPE, 1);
                    startActivity(intent);
                } else if (integer == STATISTIC_TYPE_COMPANY) {
                    Intent intent = new Intent(getActivity(), StatisticQueryActivity.class);
                    intent.putExtra(StatisticQueryActivity.QUERY_TYPE, 2);
                    startActivity(intent);
                } else {

                }
            }
        });
    }

    private String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
