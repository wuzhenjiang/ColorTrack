package com.okay.test.demo03

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var qqExerciseView = findViewById<QQExerciseView>(R.id.qqExerciseView)
        var objectAnimator = ObjectAnimator.ofInt(0, 1000)
        objectAnimator.interpolator = AccelerateInterpolator()
        qqExerciseView.setMaxSize(1500)
        objectAnimator.duration = 2000
        objectAnimator.addUpdateListener {
            var animatedFraction = Integer.parseInt(it.animatedValue.toString())
            qqExerciseView.setCurrentSize(animatedFraction)
        }
        objectAnimator.start()
    }
}
