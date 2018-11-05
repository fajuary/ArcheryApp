package com.fajuary.archeryapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fajuary.archeryapp.R;
import com.fajuary.archeryapp.utils.DensityUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangpengfei
 * @date 2018/11/3
 * 射箭动画的自定义控件
 */

public class ElectionLayout extends ViewGroup {


    private int itemHeight;
    private int itemWidth;
    private int bowWidth;
    private int arrowWidth ;
    private int arrowBorderMargin ;
    private int notShootTvWidth ;
    private int notShootTvHeight ;
    private int notShootTvVMargin;
    private int notShootTvHMargin;
    /**
     * 默认左右有多少个位置
     */
    private static final int rowNum = 4;

    private static final int[] bowResList = new int[]{
            R.drawable.prom_election_bow_left,
            R.drawable.prom_election_bow_right
    };

    private static final int[] arrowResList = new int[]{
            R.drawable.prom_election_arrow_left_1,
            R.drawable.prom_election_arrow_left_2,
            R.drawable.prom_election_arrow_left_3,
            R.drawable.prom_election_arrow_left_4,
            R.drawable.prom_election_arrow_right_1,
            R.drawable.prom_election_arrow_right_2,
            R.drawable.prom_election_arrow_right_3,
            R.drawable.prom_election_arrow_right_4
    };

    private ImageView[] mBows = new ImageView[8];
    private ImageView[] mArrowNum = new ImageView[8];
    private TextView[] mNotShootTvs = new TextView[8];

    public ElectionLayout(Context context) {
        this(context, null);
    }

    public ElectionLayout(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        initValue(mContext);
        initView(mContext);
    }
    
    private void initValue(Context mContext){
        itemHeight = DensityUtil.dip2px(mContext,72);
        itemWidth = DensityUtil.dip2px(mContext,72);
        bowWidth = DensityUtil.dip2px(mContext,41);
        arrowWidth = DensityUtil.dip2px(mContext,55);
        arrowBorderMargin = DensityUtil.dip2px(mContext,50);
        notShootTvWidth = DensityUtil.dip2px(mContext, 46);
        notShootTvHeight = DensityUtil.dip2px(mContext, 16);
        notShootTvVMargin = DensityUtil.dip2px(mContext, 45);
        notShootTvHMargin = DensityUtil.dip2px(mContext, 18);
    }

    private void initView(Context mContext) {
        final int count = 2 *rowNum;
        final int[] bowPivotX = new int[]{0,bowWidth};
        final int[] arrowPivotX = new int[]{0, arrowWidth};
        for (int i = 0; i < count; i++) {
            mBows[i] = new ImageView(mContext);
            mBows[i].setScaleType(ImageView.ScaleType.CENTER);
            mBows[i].setImageResource(bowResList[i / rowNum]);
            mBows[i].setPivotY(itemHeight / 2);
            mBows[i].setPivotX(bowPivotX[i / rowNum]);
            addView(mBows[i]);
        }
        for (int i = 0; i < count; i++) {
            mArrowNum[i] = new ImageView(mContext);
            mArrowNum[i].setScaleType(ImageView.ScaleType.CENTER);
            mArrowNum[i].setImageResource(arrowResList[i]);
            mArrowNum[i].setPivotY(itemHeight / 2);
            mArrowNum[i].setPivotX(arrowPivotX[i / rowNum]);
            addView(mArrowNum[i]);
        }
//        for (int i = 0; i < count; i++) {
//            mNotShootTvs[i] = new TextView(mContext);
//            mNotShootTvs[i].getPaint().setFakeBoldText(true);
//            mNotShootTvs[i].setText("未选择");
//            mNotShootTvs[i].setTextColor(0xffffffff);
//            mNotShootTvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//            mNotShootTvs[i].setIncludeFontPadding(false);
//            mNotShootTvs[i].setGravity(Gravity.CENTER);
//            mNotShootTvs[i].setBackgroundResource(R.drawable.prom_blind_notshoot_ivbg);
//            addView(mNotShootTvs[i]);
//        }
        reset();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int thisNewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(4 * itemHeight, MeasureSpec.EXACTLY);
        final int bowWidthMeasureSpec = MeasureSpec.makeMeasureSpec(bowWidth, MeasureSpec.EXACTLY);
        final int bowHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY);
        final int arrowWidthMeasureSpec = MeasureSpec.makeMeasureSpec(arrowWidth, MeasureSpec.EXACTLY);
        final int arrowHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY);
        final int notShootTvWidthMeasureSpec = MeasureSpec.makeMeasureSpec(notShootTvWidth, MeasureSpec.EXACTLY);
        final int notShootTvHeightMeasureSpec = MeasureSpec.makeMeasureSpec(notShootTvHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < 2 * rowNum; i++) {
            mBows[i].measure(bowWidthMeasureSpec, bowHeightMeasureSpec);
            mArrowNum[i].measure(arrowWidthMeasureSpec, arrowHeightMeasureSpec);
//            mNotShootTvs[i].measure(notShootTvWidthMeasureSpec, notShootTvHeightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec, thisNewHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int margin = arrowBorderMargin;
        final int parentWidth = getMeasuredWidth();
        for (int i = 0, count = 2 * rowNum; i < count; i++) {
            if(i < rowNum){
                mBows[i].layout(margin, i * itemHeight,
                        margin + bowWidth, (i + 1) * itemHeight);
                mArrowNum[i].layout(margin,
                        i * itemHeight,
                        margin + arrowWidth,
                        (i + 1) * itemHeight);

//                mNotShootTvs[i].layout(
//                        notShootTvHMargin,
//                        i * itemHeight + notShootTvVMargin,
//                        notShootTvHMargin + notShootTvWidth,
//                        i * itemHeight + notShootTvVMargin + notShootTvHeight
//                );
            }else{
                mBows[i].layout(parentWidth - bowWidth - margin, (i % rowNum) * itemHeight, parentWidth - margin, (i % rowNum + 1) * itemHeight);
                mArrowNum[i].layout(parentWidth - arrowWidth - margin, (i % rowNum) * itemHeight, parentWidth - margin, (i % rowNum + 1) * itemHeight);
//                mNotShootTvs[i].layout(
//                        parentWidth - notShootTvWidth - notShootTvHMargin,
//                        (i % rowNum) * itemHeight + notShootTvVMargin,
//                        parentWidth - notShootTvHMargin,
//                        (i % rowNum) * itemHeight + notShootTvVMargin + notShootTvHeight
//                );
            }
        }
    }

    private boolean checkPosition(int position){
        return position > 0 && position < 5;
    }

    /**
     * 左边的人射箭
     * @param from 左边的人的位置{1/2/3/4}
     * @param to 右边的人的位置{1/2/3/4}
     */
    private void leftElection(int from, int to){

        if(!checkPosition(from)){
            return;
        }

        final ImageView arrow = mArrowNum[from - 1];
        arrow.setVisibility(View.VISIBLE);
        final ImageView bow = mBows[from - 1];
        bow.setVisibility(View.VISIBLE);

        float startX = arrowBorderMargin;
        float startY = (from - 0.5f) * itemHeight;

        float targetX = this.getWidth() - itemWidth / 2;
        float targetY = (to - 0.5f) * itemHeight;


        double angle = Math.atan(1d * (targetY - startY) / (targetX - startX));
        float degree = (float) Math.toDegrees(angle);

        float xOffset = (float) (Math.cos(angle) * arrowWidth);
        float yOffset = (float) (Math.sin(angle) * arrowWidth);

        ValueAnimator mAnim = ValueAnimator.ofObject(new ArcheryPointFEvaluator(),
                new PointF(0, 0), new PointF(targetX - arrowBorderMargin - xOffset,
                        targetY - 0.5f * itemHeight - yOffset - (from - 1) * itemHeight));

        mAnim.setDuration(500);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                arrow.setTranslationX(p.x);
                arrow.setTranslationY(p.y);
            }
        });
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bow.setVisibility(INVISIBLE);
            }
        });
        mAnim.start();

        arrow.setRotation(degree);
        bow.setRotation(degree);
    }

    /**
     * 右边的人射箭
     * @param from 右边的人的位置{1/2/3/4}
     * @param to 左边的人的位置{1/2/3/4}
     */
    private void rightElection(int from, int to){

        if(!checkPosition(from)){
            return;
        }

        final ImageView arrow = mArrowNum[from - 1 + rowNum];
        arrow.setVisibility(VISIBLE);
        final ImageView bow = mBows[from - 1 + rowNum];
        bow.setVisibility(VISIBLE);

        float startX = this.getWidth() - arrowBorderMargin;
        float startY = (from - 0.5f) * itemHeight;

        float targetX = 0.5f * itemWidth;
        float targetY = (to - 0.5f) * itemHeight;

        double angle = Math.atan(1d * (targetY - startY) / (targetX - startX));
        float degree = (float) Math.toDegrees(angle);

        float xOffset = (float) (arrowWidth - Math.cos(angle) * arrowWidth);
        float yOffset = (float) (Math.sin(angle) * arrowWidth);

        ValueAnimator mAnim = ValueAnimator.ofObject(
                new ArcheryPointFEvaluator(),
                new PointF(0, 0),
                new PointF(targetX + arrowBorderMargin + arrowWidth - this.getWidth() - xOffset,
                        targetY + 0.5f * itemHeight - this.getHeight() + yOffset + (rowNum - from) * itemHeight)
        );

        mAnim.setDuration(500);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                arrow.setTranslationX(p.x);
                arrow.setTranslationY(p.y);
            }
        });
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bow.setVisibility(INVISIBLE);
            }
        });
        mAnim.start();

        arrow.setRotation(degree);
        bow.setRotation(degree);
    }

    /**
     * 设置左边未选择状态
     * @param pos 位置{1/2/3/4}
     */
    private void leftNotShoot(int pos){
        if(!checkPosition(pos)){
            return;
        }

        mNotShootTvs[pos - 1].setVisibility(View.VISIBLE);
    }
    /**
     * 设置右边未选择状态
     * @param pos 位置{1/2/3/4}
     */
    private void rightNotShoot(int pos){
        if(!checkPosition(pos)){
            return;
        }


//        mNotShootTvs[pos - 1 + rowNum].setVisibility(View.VISIBLE);
    }

    /**
     * 射箭
     * @param left 左边
     * @param right 右边
     */
    public void election(final SparseIntArray left, final SparseIntArray right) {
        if(left.size() > 0){
            post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < left.size(); i++) {
                        leftElection(left.keyAt(i), left.valueAt(i));
                    }
                    leftNotShoot(left);
                }
            });
        }else{
            leftNotShoot(left);
        }
        if(right.size() > 0){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < right.size(); i++) {
                        rightElection(right.keyAt(i), right.valueAt(i));
                    }
                    rightNotShoot(right);
                }
            }, left.size() > 0 ? 600 : 0);
        }else{
            rightNotShoot(right);
        }
    }

    /**
     * 设置左边未选择用户状态
     * @param array 互选map
     */
    public void leftNotShoot(SparseIntArray array){
        Set<Integer> positions = createTotalPos();
        for (int i = 0; i < array.size(); i++) {
            positions.remove(array.keyAt(i));
        }
        for (int pos : positions){
            leftNotShoot(pos);
        }
    }
    /**
     * 设置右边未选择用户状态
     * @param array 互选map
     */
    public void rightNotShoot(SparseIntArray array){
        Set<Integer> positions = createTotalPos();
        for (int i = 0; i < array.size(); i++) {
            positions.remove(array.keyAt(i));
            rightElection(array.keyAt(i), array.valueAt(i));
        }
        for (int pos : positions){
            rightNotShoot(pos);
        }
    }

    private Set<Integer> createTotalPos(){
        Set<Integer> positions = new HashSet<>(4);
        positions.add(1);
        positions.add(2);
        positions.add(3);
        positions.add(4);
        return positions;
    }

    /**
     * 重置箭和弓的状态
     */
    public void reset(){
        for (int i = 0; i < 2 * rowNum; i++) {
            mArrowNum[i].setTranslationX(0);
            mArrowNum[i].setTranslationY(0);
            mArrowNum[i].setRotation(0);
            mArrowNum[i].setVisibility(View.VISIBLE);

            mBows[i].setRotation(0);
            mBows[i].setVisibility(View.VISIBLE);

//            mNotShootTvs[i].setVisibility(View.VISIBLE);
        }
    }

    /**
     * 重置未选择状态
     * @param isLeft true=左边麦席，false=右边麦席
     * @param sort 位置{1、2、3、4}
     */
    public void resetNotShoot(boolean isLeft, int sort) {
        if(!checkPosition(sort)){
            return;
        }
        if(isLeft){
            mNotShootTvs[sort - 1].setVisibility(INVISIBLE);
        }else{
            mNotShootTvs[sort - 1 + rowNum].setVisibility(INVISIBLE);
        }
    }
}
