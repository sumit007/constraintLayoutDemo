package com.techsummit.android.constraintlayoutdemo

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.view.animation.LinearInterpolator
import com.raywenderlich.android.ConstraintLayoutDemo.R
import kotlinx.android.synthetic.main.keyframe1.*

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private val constraintSet1 = ConstraintSet()
  private val constraintSet2 = ConstraintSet()

  private var isOffscreen = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    switch1.setOnCheckedChangeListener { _, isChecked ->
      switch1.setText(if (isChecked) R.string.round_trip else R.string.one_way)
    }

    constraintSet1.clone(constraintLayout) //1
    constraintSet2.clone(this, R.layout.activity_main) //2

    departButton.setOnClickListener { //3
      /*//apply the transition
      TransitionManager.beginDelayedTransition(constraintLayout) //4
      val constraint = if (!isOffscreen) constraintSet1 else constraintSet2
      isOffscreen = !isOffscreen
      constraint.applyTo(constraintLayout) //5*/

      //1
      val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
      val startAngle = layoutParams.circleAngle
      val endAngle = startAngle + (if (switch1.isChecked) 360 else 180)

      //2
      val anim = ValueAnimator.ofFloat(startAngle, endAngle)
      anim.addUpdateListener { valueAnimator ->

        //3
        val animatedValue = valueAnimator.animatedValue as Float
        val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.circleAngle = animatedValue
        rocketIcon.layoutParams = layoutParams

        //4
        rocketIcon.rotation = (animatedValue % 360 - 270)
      }
      //5
      anim.duration = if (switch1.isChecked) 2000 else 1000

      //6
      anim.interpolator = LinearInterpolator()
      anim.start()

    }
  }
}
