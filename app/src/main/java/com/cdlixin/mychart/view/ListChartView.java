package com.cdlixin.mychart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cdlixin.mychart.R;
import com.cdlixin.mychart.bean.ChartItem;
import com.cdlixin.mychart.utils.DensityUtil;
import com.cdlixin.mychart.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒲弘宇 on 2017/10/26.
 */

public class ListChartView extends View{

    private Paint mChartPaint; //柱状图画笔
    private Paint mTextPaint;//文字画笔
    private int mChartColor;//默认柱状图颜色
    private int mChartColorSelect;//选中柱状图颜色
    private int mTextColor;//文字颜色
    private int itemPx;//柱状图 每百分之一占的像素
    private int mChartWidth;//View宽
    private int mChartHeight;//View高
    private List<ChartItem> charts = new ArrayList<>();//数据源
    private int chartCount;//柱状图列数
    private int chartItewWidth;//每根柱状图宽度
    private int selectChartIndex = -1;//当前选中列数
    private int textSize;//文字大小

    public void setChars(List<ChartItem> chartItems) {
        charts.clear();
        charts.addAll(chartItems);
    }
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public ListChartView(Context context) {
        this(context,null);
    }
    // 如果View是在.xml里声明的，则调用第二个构造函数
    // 自定义属性是从AttributeSet参数传进来的
    public ListChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public ListChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ListChartView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ListChartView_list_chart_color:
                    mChartColor = array.getColor(attr,Color.GREEN);
                    break;
                case R.styleable.ListChartView_list_chart_color_select:
                    mChartColorSelect = array.getColor(attr,Color.GREEN);
                    break;
                case R.styleable.ListChartView_list_text_color:
                    mTextColor = array.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.ListChartView_chart_item_width:
                    chartItewWidth = DensityUtil.dip2px(context,attr);
                    break;
                default:
                    break;
            }
        }
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //柱状图画笔
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode= MeasureSpec.getMode(heightMeasureSpec);

        //默认宽就是父控件剩余，默认高是父控件一半
        width = widthSize;
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = heightSize / 2;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mChartWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mChartHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        itemPx = mChartHeight / 110;//得到每百分之一高度,算上底部文字
        chartCount = charts.size();//得到组数
        chartItewWidth = mChartWidth / (chartCount * 2 +1);//每根柱子的宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < chartCount; i++) {
            drawChart(canvas,i);
        }
    }

    /**
     * 画第n根柱子
     * @param canvas
     * @param n
     */
    private void drawChart(Canvas canvas,int n){
        ChartItem item = charts.get(n);
        int number = item.getNumber();
        String name = item.getName();
        //画柱子
        RectF recF = new RectF();
        recF.top = mChartHeight - (number + 10) * itemPx ;
        recF.left = chartItewWidth * (2 * n + 1) ;
        recF.bottom = mChartHeight - 10 * itemPx;
        recF.right = chartItewWidth * (2 * n + 2);
        if(selectChartIndex == n){
            mChartPaint.setColor(mChartColorSelect);
        }else {
            mChartPaint.setColor(mChartColor);
        }
        canvas.drawRoundRect(recF,30,30,mChartPaint);
        //画文字
        int x = chartItewWidth * (2 * n + 1) + chartItewWidth / 2;
        int y = mChartHeight - 3 * itemPx ;
        textSize = 6*itemPx;
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(textSize);
        canvas.drawText(name,x,y,mTextPaint);
        //画选中文字
        if(selectChartIndex == n){
            int x1 = chartItewWidth * (2 * n + 1) + chartItewWidth / 2 - 8 * itemPx;
            int y1 = mChartHeight - (number + 10) * itemPx + 3 * itemPx;
            mTextPaint.setTextSize(textSize);
            canvas.drawText(number+"",x1,y1,mTextPaint);
        }
    }

    //点击事件处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();//触摸点的X坐标
        int y = (int) event.getY();//触摸点的Y坐标
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < chartCount; i++) {
                    ChartItem item = charts.get(i);
                    int number = item.getNumber();
                    //计算柱状图坐标
                    int top =  mChartHeight - (number + 10) * itemPx + getPaddingTop() - chartItewWidth;
                    int left = getPaddingLeft() + chartItewWidth * (2 * i + 1) - chartItewWidth/3;
                    int right = getPaddingLeft() + chartItewWidth * (2 * i + 2) + chartItewWidth/3;
                    int bottom = mChartHeight - getPaddingTop() - getPaddingBottom() + chartItewWidth;
                    Rect rect = new Rect(left, top, right, bottom);
                    //判断当前触摸点是否在该坐标内
                    if (rect.contains(x, y)) {
                        //设置选中标记
                        selectChartIndex = i;
                        invalidate();//重新绘制该View
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            default:
                break;
        }
        return true;//自定义View消耗该点击事件
    }
}
