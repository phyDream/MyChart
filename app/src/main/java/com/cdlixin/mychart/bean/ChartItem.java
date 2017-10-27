package com.cdlixin.mychart.bean;

import com.cdlixin.mychart.view.ChartView;

/**
 * Created by 蒲弘宇 on 2017/10/26.
 */

public class ChartItem {
    private String name;//科目名
    private int number;//分数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ChartItem{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
