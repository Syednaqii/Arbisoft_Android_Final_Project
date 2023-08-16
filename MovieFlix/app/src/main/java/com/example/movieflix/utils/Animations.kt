package com.example.movieflix.utils

import android.view.animation.Animation
import android.view.animation.ScaleAnimation

class Animations {

    fun performAnimation():Animation
    {
        val scaleAnimation = ScaleAnimation(1F, 1.1F, 1F, 1.1F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        scaleAnimation.duration = 500
        scaleAnimation.repeatMode = Animation.REVERSE
        scaleAnimation.repeatCount = Animation.INFINITE
        return scaleAnimation

    }
}

