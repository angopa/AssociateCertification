package com.angopa.aad.dependencyinjection.daggercodelab.registration.termsandconditions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentTermsAndConditionDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationActivity
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationViewModel
import javax.inject.Inject

class TermsAndConditionsFragment : Fragment() {
    private lateinit var binding: FragmentTermsAndConditionDaggerCodelabBinding

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Grabs the registrationComponent from the Activity and injects this Fragment
        (activity as RegistrationActivity).registrationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTermsAndConditionDaggerCodelabBinding>(
            inflater,
            R.layout.fragment_terms_and_condition_dagger_codelab,
            container,
            false
        ).apply {
            next.setOnClickListener {
                registrationViewModel.acceptTCs()
                (activity as RegistrationActivity).onTermsAndConditionsAccepted()
            }
        }

        return binding.root
    }
}