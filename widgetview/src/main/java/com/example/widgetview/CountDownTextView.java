package com.example.widgetview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;

public class CountDownTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = "CountDownTextView";
    private CountDownTimer mCountDownTimer;
    private OnCountDownOverListener mOnCountDownOverListener;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void startCountDown() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(60 * 5 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setText("验证码倒计时(" + (millisUntilFinished / 1000) + ")");
                    Log.d(TAG, "count down time   --> " + (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    stopCountDown();
                    if (mOnCountDownOverListener != null) {
                        mOnCountDownOverListener.onCountDownOverListener();
                    }
                }
            };
            mCountDownTimer.start();
        }
    }

    public void stopCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
            Log.d(TAG,"倒计时结束 -->  ");
        }
    }

    public void setmOnCountDownOverListener(OnCountDownOverListener mOnCountDownOverListener) {
        this.mOnCountDownOverListener = mOnCountDownOverListener;
    }

    public interface OnCountDownOverListener {
        void onCountDownOverListener();
    }
}
