package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.DetailActivity;
import com.fenjin.sandfactory.databinding.FragmentFirstBinding;
import com.fenjin.sandfactory.viewmodel.FirstViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment {


    public FirstFragment() {
        // Required empty public constructor
    }

    private FirstViewModel viewModel;

    private QMUITipDialog loadingDialog;

    private QMUIPullRefreshLayout pullRefreshLayout;

    private BarChart barChart;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        registerObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFirstBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_view);

        View footerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot, listView, false);
        listView.addFooterView(footerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChengZhongRecord record = (ChengZhongRecord) viewModel.adapter.getItem(i);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("record",record);
                startActivity(intent);
            }
        });

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
                viewModel.loadChengZhongRecordList();
            }
        });

//        viewModel.loadChengZhongRecordList();
        barChart = view.findViewById(R.id.bar_chart);

        initBarChart();
    }

    private void initBarChart() {

        final List<GetChartStaticResult.ChartItem> chartItems = new ArrayList<>();
        ArrayList<BarEntry> weightEntries = new ArrayList<>();
        ArrayList<BarEntry> moneyEntries = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            GetChartStaticResult.ChartItem chartItem = new GetChartStaticResult().new ChartItem();
            chartItem.setKey(i + 1 + "月");
            chartItem.setWeight(random.nextFloat() * 20);
            chartItem.setMoney(random.nextFloat() * 20);
            chartItems.add(chartItem);

            BarEntry weightEntry = new BarEntry(i, chartItem.getWeight());
            weightEntries.add(weightEntry);

            BarEntry moneyEntry = new BarEntry(i, chartItem.getMoney());
            moneyEntries.add(moneyEntry);
        }

        BarDataSet weightDataSet = new BarDataSet(weightEntries, "重量");
        weightDataSet.setColor(Color.YELLOW);
        BarDataSet moneyDataSet = new BarDataSet(moneyEntries, "金额");
        moneyDataSet.setColor(Color.GREEN);

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


        //不显示图标描述
        Description description = barChart.getDescription();
        description.setEnabled(false);

        //设置图例在右上角
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


        barChart.setNoDataText("暂无数据");
        // 双指缩放
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        //禁止拖拽
        barChart.setDragEnabled(false);
        //X轴或Y轴禁止缩放
        barChart.setScaleXEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.setScaleEnabled(false);

        barChart.animateY(500);

        /*    设置X轴坐标  */
        XAxis xAxis = barChart.getXAxis();
        //是否显示x坐标的数据
        xAxis.setDrawLabels(true);
        //设置x坐标数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否显示网格线中与x轴垂直的网格线
        xAxis.setDrawGridLines(false);
        //设置X轴坐标个数
        xAxis.setLabelCount(chartItems.size());
        //设置X轴坐标最小值
        xAxis.setAxisMinimum(0f);
        //X轴显示Value值的精度
        xAxis.setGranularity(1f);
        //设置X轴坐标最大值
        xAxis.setAxisMaximum(chartItems.size());
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);
        //设置x轴标签格式化器，将自动生成的float型坐标值转换成自定义String类型
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                //value是根据设置的坐标最小值、最大值以及精度自动生成的
                //此处将其转换为chartItem的key字段
                if (value < 0 || value >= chartItems.size()) return "";
                return chartItems.get((int) value).getKey();
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
        rightYAxis.setLabelCount(4);
        rightYAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                LogUtils.d("FirstFragment, value = " + value);
                if (value <= 0) return "";
                return value + "元";
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
        leftYAxis.setLabelCount(4);
        //自定义左侧标签的字符串，可以是任何的字符串、中文、英文等
        leftYAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value <= 0) return "";
                return value + "T";
            }
        });

        barChart.setData(barData);

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
    }

}
