package com.example.criticaltechworkschallenge.presentation.ui.activiity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.criticaltechworkschallenge.R
import com.example.criticaltechworkschallenge.databinding.ActivityMainBinding
import com.example.criticaltechworkschallenge.util.Constants.IS_AUTHENTICATION_SUCCESSFUL
import com.example.criticaltechworkschallenge.util.biometric.BiometricPromptUtils
import com.example.criticaltechworkschallenge.util.biometric.CryptographyManager
import com.example.criticaltechworkschallenge.util.biometric.CryptographyManagerImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var cryptographyManager: CryptographyManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        navController.setGraph(R.navigation.main_nav_graph)
        navController.graph.setStartDestination(R.id.headlineFragment)
        showBiometricPromptForAuthentication()
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun showBiometricPromptForAuthentication() {
        val canAuthenticate =
            BiometricManager.from(applicationContext).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_WEAK
            )

        // check if user can perform Biometric authentication
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val secretKeyName = CryptographyManagerImpl.SECRETE_KEY_ALIAS

            val cipher = cryptographyManager.getInitializedCipherForAuthentication(secretKeyName)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this as AppCompatActivity) {
                    navigateToTopHeadlineFragment()
                }
            val promptInfo =
                BiometricPromptUtils.createPromptInfo(this as AppCompatActivity)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        } else {
            navigateToTopHeadlineFragment()
        }
    }

    private fun navigateToTopHeadlineFragment() {
        val bundle = Bundle().apply { putBoolean(IS_AUTHENTICATION_SUCCESSFUL, true) }
        navController.popBackStack(R.id.headlineFragment, true)
        navController.navigate(R.id.headlineFragment, bundle)
    }
}