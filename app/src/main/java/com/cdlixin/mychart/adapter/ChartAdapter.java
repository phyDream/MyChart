package com.cdlixin.mychart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdlixin.mychart.R;
import com.cdlixin.mychart.bean.ChartItem;
import com.cdlixin.mychart.view.ChartView;

import java.util.List;

/**
 * Created by 蒲弘宇 on 2017/10/26.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartViewHolder>{

    private Context context;
    private List<ChartItem> chartItems;

    public ChartAdapter(Context context, List<ChartItem> chartItems) {
        this.context = context;
        this.chartItems = chartItems;
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chart_item,parent,false);
        ChartViewHolder holder = new ChartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        ChartItem chartItem = chartItems.get(position);
        holder.cv.setmNumber(chartItem.getNumber());
        holder.tv_name.setText(chartItem.getName());
        holder.tv_number.setText(""+chartItem.getNumber());
    }

    @Override
    public int getItemCount() {
        return chartItems.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tv_name;
        private TextView tv_number;
        private ChartView cv;
        public ChartViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            cv = (ChartView) view.findViewById(R.id.cv);
        }
    }
}

