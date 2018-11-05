package com.fajuary.archeryapp.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author zhangpengfei
 * @date 2018/11/3
 * 射箭两点位移估值器
 */
public class ArcheryPointFEvaluator implements TypeEvaluator<PointF> {

    private final PointF mPoint;

    public ArcheryPointFEvaluator() {
        mPoint = new PointF();
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        /**
         * 实现思路
         * x轴直线
         * y轴直线
         * 最后设置两个点
         */
        float x = startValue.x + (fraction * (endValue.x - startValue.x));
        float y = startValue.y + (fraction * (endValue.y - startValue.y));
        mPoint.set(x, y);
        return mPoint;
    }

}
