package com.fajuary.archeryapp.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fajuary.archeryapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zhangpengfei
 * @date 2018/11/3
 */

public class MicSeatView extends RelativeLayout{

    public MicSeatView(Context context) {
        this(context,null);
    }

    public MicSeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public final static int HOST = 2;
    public final static int MIC = 1;
    public final static int DEFAULT = 0;

    private boolean isHostControl;

    @IntDef({DEFAULT, HOST, MIC})
    @Retention(RetentionPolicy.SOURCE)
    @interface Role {
    }


    private XImageView mSpeakView;
    private ImageView mMicSeatAvatarView;
    private ImageView mControlStateView;
    private ImageView mHostControlMicView;
    private ImageView mBackgroundView;
    private int mSelfRole;

    /**
     * 是否在左边
     */
    private boolean isLeft;

    public MicSeatView(Context context, boolean isLeft) {
        super(context);
        this.isLeft = isLeft;
        initView(context);
    }




    public MicSeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        if (isLeft) {
            LayoutInflater.from(context).inflate(R.layout.view_left_single_mic_seat, this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.view_right_single_mic_seat, this, true);
        }

        mSpeakView = findViewById(R.id.imv_room_mic_seat_speak);
        mBackgroundView = findViewById(R.id.imv_mic_seat_bg);
        mMicSeatAvatarView = findViewById(R.id.imv_room_mic_seat_avatar);
        mControlStateView = findViewById(R.id.imv_room_mic_seat_control_state);
        mHostControlMicView = findViewById(R.id.imv_room_mic_seat_host_control_mic);

        mMicSeatAvatarView.setImageResource(R.drawable.hot_chat_me_icon);

        mHostControlMicView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        showSpeakState(false);
    }


    /**
     * 角色
     *
     * @param selfRole DEFAULT 普通 ，HOST 主持人，MIC 麦上用户
     */
    public void setSelfRole(@Role int selfRole) {
        this.mSelfRole = selfRole;
        if (getTag() == null) {
            return;
        }
        if (mSelfRole == MIC) {
            changeRole();
        }
    }


    public boolean isHost() {
        return mSelfRole == HOST;
    }

    public boolean isMicUser() {
        return mSelfRole == MIC;
    }

    public boolean isDefault() {
        return mSelfRole == DEFAULT;
    }

    private void changeRole() {
        switch (mSelfRole) {
            case HOST:
                mControlStateView.setVisibility(GONE);
                mHostControlMicView.setVisibility(VISIBLE);
                break;
            case MIC:
                mControlStateView.setVisibility(VISIBLE);
                mHostControlMicView.setVisibility(GONE);
                break;
            default:
                mControlStateView.setVisibility(GONE);
                mHostControlMicView.setVisibility(GONE);
                break;
        }
        setAvatar();
    }

    private void setBackgroundState(boolean isStart) {
        if (isStart) {
            mBackgroundView.setImageResource(isLeft ? R.drawable.ic_left_mic_seat_bg : R.drawable.ic_right_mic_seat_bg);
        } else {
            mBackgroundView.setImageResource(isLeft ? R.drawable.ic_none_left_mic_seat_bg : R.drawable.ic_none_right_mic_seat_bg);
        }
    }


    public void setBeginAvatar() {
        setEnabled(true);
        mControlStateView.setVisibility(GONE);
        mHostControlMicView.setVisibility(GONE);
        mMicSeatAvatarView.setImageResource(R.drawable.ic_mic_seat_add);
        showSpeakState(false);
        setBackgroundState(true);
    }

    public void setBlackAvatar() {
        setEnabled(false);
        mControlStateView.setVisibility(GONE);
        mHostControlMicView.setVisibility(GONE);
        mMicSeatAvatarView.setImageResource(R.drawable.ic_mic_seat_none);
        showSpeakState(false);
        setBackgroundState(false);
    }





    public void showMicUserState(boolean on) {
        if (isLeft) {
            mControlStateView.setImageResource(on ? R.drawable.ic_mic_control_left_on : R.drawable.ic_mic_control_off);
        } else {
            mControlStateView.setImageResource(on ? R.drawable.ic_mic_control_right_on : R.drawable.ic_mic_control_off);
        }
    }


    public void showHostControlState(boolean on) {
        isHostControl = on;
        mHostControlMicView.setImageResource(on ? R.drawable.ic_mic_host_control_on : R.drawable.ic_mic_host_control_off);
        showMicUserState(on);
        if (!on) {
            showHostSpeakState(false);
        }
    }


    private void showHostSpeakState(boolean speaking) {
        if (isLeft) {
            mSpeakView.setImageResource(speaking ? R.drawable.ic_mic_seat_left_speak : R.color.transparent);
        } else {
            mSpeakView.setImageResource(speaking ? R.drawable.ic_mic_seat_right_speak : R.color.transparent);
        }
    }


    public void showSpeakState(boolean speaking) {
        if (isHostControl) {
            showHostSpeakState(speaking);
        } else {
            showMicUserState(false);
            showHostSpeakState(false);
        }
    }

    public void setAvatar() {
//        changeRole();
        mMicSeatAvatarView.setImageResource(R.drawable.hot_chat_me_icon);
    }
    public boolean isLeft() {
        return isLeft;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
