package com.cdlixin.mychart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cdlixin.mychart.utils.LogUtil;
import com.cdlixin.mychart.R;

/**
 * Created by 蒲弘宇 on 2017/10/25.
 */

public class ChartView extends View{

    private Paint mChartPaint;

    private int mChartColor;//柱状图颜色

    private final int defultColor = Color.BLACK;
    private int mWidth;//柱状图长度
    private int mHeight;//柱状图高度
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int mNumber;//当前需要显示的数量
    private int mItemPx;//每格占像素（百分之一）


    public ChartView(Context context) {
        this(context,null);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChartView,defStyleAttr,0);
        int attrsCount = array.getIndexCount();
        for (int i = 0; i < attrsCount ; i++) {
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.ChartView_chart_color:
                    mChartColor = array.getColor(attr,defultColor);
                    break;
                default:
                    break;
            }
        }

        array.recycle();
        init();
    }

    private void init(){
        //初始化柱状图画笔
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mChartPaint.setColor(mChartColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//得到测量宽
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//得到测量模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//得到测量宽
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//得到测量模式
        //测量模式为EXACTLY,宽高等于具体值
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {//测量模式不为EXACTLY,宽高给个默认计算公式
            width = widthSize * 4 / 5;//父控件可用空间一半
        }

        //测量模式为EXACTLY,宽高等于具体值
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {//测量模式不为EXACTLY,宽高给个默认计算公式
            height = heightSize * 1 / 25;//父控件可用空间一半
        }
        //设置宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paddingTop = getPaddingTop();
        paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingRight = getPaddingRight();
        mWidth = getWidth() - paddingLeft - paddingRight;
        mItemPx = mWidth/100;
        mHeight = getHeight() - paddingTop - paddingBottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mChartPaint.setColor(mChartColor);
        mChartPaint.setStyle(Paint.Style.FILL);
        RectF r = new RectF();

        r.top = 0;
        r.bottom = mHeight;
        if(mNumber > 100){
            mNumber = 100;
        }
        r.left = 0;
        r.right = mNumber*mItemPx;

//        LogUtil.i("~right~"+r.right+"~bottom~"+r.bottom+"~mItemPx~"+mItemPx);
        canvas.drawRoundRect(r,30,30,mChartPaint);
    }

    //对外可以设置其显示数量
    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }
}
