package com.cdlixin.mychart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cdlixin.mychart.R;
import com.cdlixin.mychart.adapter.ChartAdapter;
import com.cdlixin.mychart.bean.ChartItem;
import com.cdlixin.mychart.view.ListChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.lv_chart)
    RecyclerView lvChart;
    @Bind(R.id.lcv)
    ListChartView lcv;

    private List<ChartItem> chartItems = new ArrayList<>();
    private ChartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        adapter = new ChartAdapter(this, chartItems);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvChart.setLayoutManager(manager);
        lvChart.setAdapter(adapter);
        initData();
        adapter.notifyDataSetChanged();

        lcv.setChars(chartItems);
    }

    private void initData() {
        ChartItem c1 = new ChartItem();
        c1.setName("语文");
        c1.setNumber(new Random().nextInt(100));
        chartItems.add(c1);

        ChartItem c2 = new ChartItem();
        c2.setName("数学");
        c2.setNumber(new Random().nextInt(100));
        chartItems.add(c2);

        ChartItem c3 = new ChartItem();
        c3.setName("英语");
        c3.setNumber(new Random().nextInt(100));
        chartItems.add(c3);

        ChartItem c4 = new ChartItem();
        c4.setName("物理");
        c4.setNumber(new Random().nextInt(100));
        chartItems.add(c4);

        ChartItem c5 = new ChartItem();
        c5.setName("化学");
        c5.setNumber(new Random().nextInt(100));
        chartItems.add(c5);

        ChartItem c6 = new ChartItem();
        c6.setName("生物");
        c6.setNumber(new Random().nextInt(100));
        chartItems.add(c6);

        ChartItem c7 = new ChartItem();
        c7.setName("历史");
        c7.setNumber(new Random().nextInt(100));
        chartItems.add(c7);

        ChartItem c8 = new ChartItem();
        c8.setName("地理");
        c8.setNumber(new Random().nextInt(100));
        chartItems.add(c8);

        ChartItem c9 = new ChartItem();
        c9.setName("政治");
        c9.setNumber(new Random().nextInt(100));
        chartItems.add(c9);
    }

}
