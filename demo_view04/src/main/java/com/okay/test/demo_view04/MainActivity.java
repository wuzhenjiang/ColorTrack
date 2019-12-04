package com.okay.test.demo_view04;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import test.okay.com.demo_view04.ColorTrackTextView;

public class MainActivity extends AppCompatActivity {

    private ColorTrackTextView mColorTrackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorTrackTextView = findViewById(R.id.colorTrackTextView);
    }

    public void leftToRight(View view){
        mColorTrackTextView.setDirection(ColorTrackTextView.DIRECTION.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                mColorTrackTextView.setPercentage(animatedFraction);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view){
        mColorTrackTextView.setDirection(ColorTrackTextView.DIRECTION.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                mColorTrackTextView.setPercentage(animatedFraction);
            }
        });
        valueAnimator.start();
    }
}
