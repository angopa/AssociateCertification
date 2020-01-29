package com.angopa.aad.dependencyinjection.daggercodelab.registration

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.CoreApplication
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityRegistrationDaggerCodelabBinding
import com.angopa.aad.dependencyinjection.daggercodelab.BaseActivityDagger
import com.angopa.aad.dependencyinjection.daggercodelab.main.MainActivity
import com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails.EnterDetailsFragment
import com.angopa.aad.dependencyinjection.daggercodelab.registration.termsandconditions.TermsAndConditionsFragment
import javax.inject.Inject

class RegistrationActivity : BaseActivityDagger() {
    private lateinit var binding: ActivityRegistrationDaggerCodelabBinding

    // Stores an instance of RegistrationComponent so that its Fragments can access it
    lateinit var registrationComponent: RegistrationComponent

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    override fun getComponent() {
        // Creates an instance of Registration component by grabbing the factory from the app graph
        registrationComponent = (application as CoreApplication)
            .appComponentCodelab.registrationComponent().create()

        // Injects this activity to the just created Registration component
        registrationComponent.inject(this)
    }

    override fun getScreenTitle(): Int {
        return R.string.registration_screen_title
    }

    override fun getBindingComponent() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_registration_dagger_codelab)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, EnterDetailsFragment())
            .commit()
    }

    /**
     * Callback from EnterDetailsFragment when username and password has been entered
     */
    fun onDetailsEntered() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, TermsAndConditionsFragment())
            .addToBackStack(TermsAndConditionsFragment::class.java.simpleName)
            .commit()
    }

    /**
     * Callback from T&CsFragment when TCs have been accepted
     */
    fun onTermsAndConditionsAccepted() {
        registrationViewModel.registerUser()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            startActivity(Intent(this, com.angopa.aad.MainActivity::class.java))
            finish()
        }
    }
}