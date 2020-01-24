package com.angopa.aad.dependencyinjection.dagger

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentLoginUsernameDaggerBinding
import javax.inject.Inject

class LoginUsernameFragment : Fragment() {
    private lateinit var binding: FragmentLoginUsernameDaggerBinding

    // Fields that need to be injected by the login graph
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Obtaining the login graph from LoginActivity and instantiate the @Inject fields
        // with objects from the graph
        (activity as LoginActivity).loginComponent.inject(this)

        // Now you can access loginViewModel here and onCreateView too
        // (shared instance with the Activity and the other Fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginUsernameDaggerBinding>(
            layoutInflater,
            R.layout.fragment_login_username_dagger,
            container,
            false
        ).apply {
            buttonPassword.setOnClickListener { view ->
                val directions = LoginUsernameFragmentDirections.actionUsernameToPassword()
                view.findNavController().navigate(directions)
            }
        }

        return binding.root
    }
}