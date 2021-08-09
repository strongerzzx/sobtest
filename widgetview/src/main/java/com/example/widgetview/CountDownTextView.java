package com.example.widgetview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;

public class CountDownTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = "CountDownTextView";
    private CountDownTimer mCountDownTimer;

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
            mCountDownTimer = new CountDownTimer(1000 * 5 * 10, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setText(String.valueOf((millisUntilFinished / 1000)));
                    Log.d(TAG,"count down time   --> "+(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    stopCountDown();
                }
            };
        }
    }


    public void stopCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
