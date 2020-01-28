package com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentEnterDetailsFragmentDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationActivity
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationViewModel
import javax.inject.Inject

class EnterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEnterDetailsFragmentDaggerCodelabBinding

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    private lateinit var errorTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

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
        binding = DataBindingUtil.inflate<FragmentEnterDetailsFragmentDaggerCodelabBinding>(
            inflater,
            R.layout.fragment_enter_details_fragment_dagger_codelab,
            container,
            false
        ).apply {
            errorTextView = error
            usernameEditText = username
            passwordEditText = password

            usernameEditText.doOnTextChanged { _, _, _, _ ->
                errorTextView.visibility = View.INVISIBLE
            }
            passwordEditText.doOnTextChanged { _, _, _, _ ->
                errorTextView.visibility = View.INVISIBLE
            }

            next.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()
                registrationViewModel.validateInput(username, password)
            }

        }

        registrationViewModel.enterDetailState.observe(this,
            Observer<EnterDetailsViewState> { state ->
                when (state) {
                    is EnterDetailSuccess -> {
                        val username = usernameEditText.text.toString()
                        val password = passwordEditText.text.toString()
                        registrationViewModel.updateUserData(username, password)

                        (activity as RegistrationActivity).onDetailsEntered()
                    }
                    is EnterDetailError -> {
                        errorTextView.text = state.error
                        errorTextView.visibility = View.VISIBLE
                    }
                }
            })

        return binding.root
    }
}

sealed class EnterDetailsViewState
object EnterDetailSuccess : EnterDetailsViewState()
data class EnterDetailError(val error: String) : EnterDetailsViewState()