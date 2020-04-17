package com.angopa.aad.androidui.codelabs.transition.dashboard

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.transition.activitytoactivity.TransitionStartActivity
import com.angopa.aad.databinding.FragmentTransitionDashboardBinding

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class TransitionDashboardFragment : Fragment() {
    private lateinit var binding: FragmentTransitionDashboardBinding

    private lateinit var imageAnimation: AnimationDrawable

    private lateinit var animatedVectorDrawable: AnimatedVectorDrawable

    private lateinit var radioVectorDrawable: AnimatedVectorDrawable

    private lateinit var morphVectorDrawable: Animatable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTransitionDashboardBinding>(
            inflater,
            R.layout.fragment_transition_dashboard,
            container,
            false
        ).apply {
            transitionBetweenFragments.setOnClickListener {
                val directions =
                    TransitionDashboardFragmentDirections.actionDashboardToTransitionBetweenFragments()
                findNavController().navigate(directions)
            }

            transitionBetweenActivities.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        TransitionStartActivity::class.java
                    )
                )
            }

            imageTransition.apply {
                setBackgroundResource(R.drawable.animate_image_button)
                imageAnimation = background as AnimationDrawable
            }

            imageRoundRocket.apply {
                setBackgroundResource(R.drawable.animate_triangle_to_square)
                animatedVectorDrawable = background as AnimatedVectorDrawable
            }

            radioPlayImage.apply {
                setBackgroundResource(R.drawable.animator_playing)
                radioVectorDrawable = background as AnimatedVectorDrawable
            }

            fabStateListAnimator.setOnClickListener {
                (AnimatorInflater.loadAnimator(
                    requireContext(),
                    R.animator.animate_position
                ) as ValueAnimator).apply {
                    addUpdateListener { updateAnimator ->
                        it.translationX = updateAnimator.animatedValue as Float
                    }
                    start()
                }
            }

            fabMorphIcon.apply {
                setImageResource(R.drawable.animate_morph)
                morphVectorDrawable = drawable as Animatable
            }

            fabMorphIcon.setOnClickListener {

            }
            imageTransition.setOnClickListener {
                imageAnimation.start()
            }

            imageRoundRocket.setOnClickListener {
                animatedVectorDrawable.start()
            }

            radioPlayImage.setOnClickListener {
                radioVectorDrawable.start()
            }

            fabMorphIcon.setOnClickListener {
                morphVectorDrawable.start()
            }

        }
        return binding.root
    }
}