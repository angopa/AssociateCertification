package com.angopa.aad.androidui.codelabs.recyclerview.animator

import android.animation.Animator
import android.animation.ObjectAnimator

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 *
 * Holds child animator objects for any change animation. Used when a new change
 * animation interrupts one already in progress; the new one is constructed to start
 * from where the previous one was at when the interruption occurred.
 */
data class AnimatorInfo(
    val animator: Animator,
    val fadeToBlackAnim: ObjectAnimator,
    val fadeFromBlackAnim: ObjectAnimator,
    val oldTextRotator: ObjectAnimator,
    val newTextRotator: ObjectAnimator
)