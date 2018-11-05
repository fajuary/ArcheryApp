package com.fajuary.archeryapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.fajuary.archeryapp.utils.DensityUtil;

/**
 * @author zhangpengfei
 * @date 2018/11/3
 */

public class MicSeatLayout extends ViewGroup {

    private static final int rowsNum = 4;
    private final int itemWidth;
    private final int itemHeight;

    public MicSeatLayout(Context context) {
        this(context, null);
    }

    public MicSeatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemWidth = DensityUtil.dip2px(context, 72);
        itemHeight = DensityUtil.dip2px(context, 72);
        initView(context);
    }


    private void initView(Context mContext) {
        for (int count = 2 * rowsNum, i = 0; i < count; i++) {
            boolean isLeft = i < rowsNum;
            MicSeatView mic = new MicSeatView(mContext, isLeft);
            mic.setSelfRole(MicSeatView.MIC);

            addView(mic);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        /**
         * 最后设置的为真实值 前面的都是测量模式
         */
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(itemHeight * rowsNum, MeasureSpec.EXACTLY));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int parentWidth = getMeasuredWidth();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (i < rowsNum) {
                /**
                 * 小于四个的时候 布局在左边 top为第几个高度
                 * 宽度为itemWidth
                 * top加itemHeight
                 */
                view.layout(0, i * itemHeight, itemWidth, (i + 1) * itemHeight);
            } else {
                /**
                 * 多余4个为右边的
                 * 左边为父布局宽度-每一个宽度
                 */
                view.layout(parentWidth - itemWidth,
                        (i % rowsNum) * itemHeight,
                        parentWidth, (i % rowsNum + 1) * itemHeight);
            }
        }
    }
}
