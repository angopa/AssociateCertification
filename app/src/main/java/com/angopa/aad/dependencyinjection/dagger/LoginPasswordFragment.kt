package com.angopa.aad.dependencyinjection.dagger

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController

import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentLoginPasswordDaggerBinding
import javax.inject.Inject

class LoginPasswordFragment : Fragment() {
    private lateinit var binding: FragmentLoginPasswordDaggerBinding

    private var userData: LoginUserData = LoginUserData()

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
        binding = DataBindingUtil.inflate<FragmentLoginPasswordDaggerBinding>(
            inflater,
            R.layout.fragment_login_password_dagger,
            container,
            false
        ).apply {
            buttonReturn.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            buttonLogin.setOnClickListener { getUserData() }

            userDataVariable =  this@LoginPasswordFragment.userData

            getUserData()
        }

        return binding.root
    }

    private fun getUserData() {
        loginViewModel.loadDataFromPasswordFragment().observe(this@LoginPasswordFragment) { userName ->
            if (!TextUtils.isEmpty(userName)) {
                userData.setUserName(userName)
            }
        }
    }
}