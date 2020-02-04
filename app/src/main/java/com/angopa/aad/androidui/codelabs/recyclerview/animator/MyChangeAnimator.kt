package com.angopa.aad.androidui.codelabs.recyclerview.animator

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.ArrayMap
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.androidui.codelabs.recyclerview.adapter.RecyclerViewAnimations

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 *
 * Custom ItemAnimator that handles change animations. The default animator simple cross-
 * fades the old and new ViewHolder items. Our custom animation re-uses the same
 * ViewHolder and animates the contents of the views that it contains. The animation
 * consists of a color fade through black and a text rotation. The animation also handles
 * interruption, when a new change event happens on an item that is currently being
 * animated.
 */
class MyChangeAnimator : DefaultItemAnimator() {
    // stateless interpolators re-used for every change animation

    // Maps to hold running animators. These are used when running a new change
    // animation on an item that is already being animated. mRunningAnimators is
    // used to cancel the previous animation. mAnimatorMap is used to construct
    // the new change animation based on where the previous one was at when it
    // was interrupted.
    private val animatorMap = ArrayMap<RecyclerView.ViewHolder, AnimatorInfo>()

    private val colorEvaluator = ArgbEvaluator()

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        // This allows our custom change animation on the contents of the holder instead
        // of the default behavior of replacing the viewHolder entirely
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        val colorTextInfo: ColorTextInfo = super.recordPreLayoutInformation(
            state,
            viewHolder,
            changeFlags,
            payloads
        ) as ColorTextInfo
        return getItemHolderInfo(viewHolder as RecyclerViewAnimations.ViewHolder, colorTextInfo)
    }

    override fun recordPostLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder
    ): ItemHolderInfo {
        val colorTextInfo = super.recordPostLayoutInformation(state, viewHolder) as ColorTextInfo
        return getItemHolderInfo(viewHolder as RecyclerViewAnimations.ViewHolder, colorTextInfo)
    }

    override fun obtainHolderInfo(): ItemHolderInfo {
        return ColorTextInfo()
    }

    override fun isRunning(): Boolean = super.isRunning() || !animatorMap.isEmpty()

    override fun endAnimations() {
        super.endAnimations()
        animatorMap.forEach { (_, animatorInfo) ->
            animatorInfo.animator.cancel()
        }
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        super.endAnimation(item)
        animatorMap.forEach { (viewHolder, animatorInfo) ->
            if (viewHolder == item) {
                animatorInfo.animator.cancel()
            }
        }
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        if (oldHolder != newHolder) {
            // use default behavior if not re-using view holders
            return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
        }

        val viewHolder = newHolder as RecyclerViewAnimations.ViewHolder

        // Get the pre/post change values; these are what we are animating between
        val oldInfo = preInfo as ColorTextInfo
        val newInfo = postInfo as ColorTextInfo

        val oldColor = oldInfo.color
        val newColor = newInfo.color

        val oldText = oldInfo.text
        val newText = newInfo.text

        // These are the objects whose values will be animated
        val newContainer = viewHolder.view as LinearLayout
        val newTextView = viewHolder.textView

        // Check to see if there's a change animation already running on this ite
        val runningInfo = animatorMap[newHolder]
        var prevAnimPlayTime: Long = 0
        var firstHalf = false

        if (runningInfo != null) {
            // The information we need to construct the new animators is whether we
            // are in the 'first half' (fading to black and rotating the old text out)
            // and how far we are in whichever half is running
            firstHalf = runningInfo.oldTextRotator != null &&
                    runningInfo.oldTextRotator.isRunning

            prevAnimPlayTime = if (firstHalf)
                runningInfo.oldTextRotator.currentPlayTime else runningInfo.newTextRotator.currentPlayTime

            // done with previous animation - cancel it
            runningInfo.animator.cancel()
        }

        // Construct the fade to/from black animation
        var fadeToBlack: ObjectAnimator? = null
        var fadeFromBlack = ObjectAnimator()

        if (runningInfo == null || firstHalf) {
            // The first part of the animation fades to black. Skip this phase
            // if we're interrupting an animation that was already in the second phase.
            var startColor = oldColor
            if (runningInfo != null) {
                startColor = runningInfo.fadeToBlackAnim.animatedValue as Int
            }

            fadeToBlack =
                ObjectAnimator.ofInt(newContainer, "backgroundColor", startColor, Color.BLACK)
            fadeToBlack.setEvaluator(colorEvaluator)
            if (runningInfo != null) {
                // Seek to appropriate time in new animator if we were already
                // running a previous animation
                fadeToBlack.currentPlayTime = prevAnimPlayTime
            }
        }

        // Second phase of animation fades from black to the new bg color
        fadeFromBlack = ObjectAnimator.ofInt(
            newContainer, "backgroundColor",
            Color.BLACK, newColor
        )
        fadeFromBlack.setEvaluator(colorEvaluator)
        if (runningInfo != null && !firstHalf) {
            // Seek to appropriate time in new animator if we were already
            // running a previous animation
            fadeFromBlack.currentPlayTime = prevAnimPlayTime
        }

        // Set up an animation to play both the first (if non-null) and second phases
        val beginAnim = AnimatorSet()
        if (fadeToBlack != null) {
            beginAnim.playSequentially(fadeToBlack, fadeFromBlack)
        } else {
            beginAnim.play(fadeFromBlack)
        }

        // The other part of the animation rotates the text, switching it to the
        // new value half-way through (when it is perpendicular to the user)
        var oldTextRotate: ObjectAnimator? = null
        var newTextRotate = ObjectAnimator()

        if (runningInfo == null || firstHalf) {
            // The first part of the animation rotates text to be perpendicular to user.
            // Skip this phase if we're interrupting an animation that was already
            // in the second phase.
            oldTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, 0F, 90F)
            if (runningInfo != null) {
                oldTextRotate.currentPlayTime = prevAnimPlayTime
            }
            oldTextRotate.addListener(object : AnimatorListenerAdapter() {
                var canceled = false
                override fun onAnimationStart(animation: Animator?) {
                    // text was changed as part of the item change notification. Change
                    // it back for the first phase of the animation
                    newTextView.text = oldText
                }

                override fun onAnimationCancel(animation: Animator?) {
                    canceled = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (!canceled) {
                        // Set it to the new text when the old rotator ends - this is when
                        // it is perpendicular to the user (thus making the switch
                        // invisible)
                        newTextView.text = newText
                    }
                }
            })
        }

        // Second half of text rotation rotates from perpendicular to 0
        newTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, -90F, 0F)
        if (runningInfo != null && !firstHalf) {
            // If we're interrupting a previous second-phase animation, seek to that time
            newTextRotate.currentPlayTime = prevAnimPlayTime
        }

        // Choreograph first and second half. First half may be null if we interrupted
        // a second-phase animation
        val textAnim = AnimatorSet()
        if (oldTextRotate != null) {
            textAnim.playSequentially(oldTextRotate, newTextRotate)
        } else {
            textAnim.play(newTextRotate)
        }

        // Choreograph both animations: color fading and text rotating
        val changeAnim = AnimatorSet()
        changeAnim.playTogether(beginAnim, textAnim)
        changeAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                dispatchAnimationFinished(newHolder)
                animatorMap.remove(newHolder)
            }
        })
        changeAnim.start()

        // Store info about this animation to be re-used if a succeeding change event
        // occurs while it's still running
        val animatorInfo =
            AnimatorInfo(changeAnim, fadeToBlack!!, fadeFromBlack, oldTextRotate!!, newTextRotate)

        animatorMap[newHolder] = animatorInfo

        return true
    }

    private fun getItemHolderInfo(
        viewHolder: RecyclerViewAnimations.ViewHolder,
        info: ColorTextInfo
    ): ItemHolderInfo {
        val color = (viewHolder.view.background as ColorDrawable).color
        info.color = color
        info.text = viewHolder.textView.text.toString()
        return info
    }
}