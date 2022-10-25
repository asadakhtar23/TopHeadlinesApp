package com.android.topheadlinesapp.viewmodels

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.topheadlinesapp.enums.BiometricResult
import java.util.concurrent.Executor

class MainViewModel: ViewModel() {

    private lateinit var biometricPrompt: BiometricPrompt

    var showErrorMessage = MutableLiveData<String>()

    private val _biometricResult: MutableLiveData<BiometricResult> = MutableLiveData()

    val biometricResult: LiveData<BiometricResult> = _biometricResult

    fun checkBiometricAvailability(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                return true
            }
        }
        return false
    }

    fun initBiometricPrompt(fragmentActivity: FragmentActivity, executor: Executor) {

        biometricPrompt = BiometricPrompt(fragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    _biometricResult.value = BiometricResult.AUTH_ERROR
                    showErrorMessage.value = "Authentication error: $errString"
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _biometricResult.value = BiometricResult.SUCCESS
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    _biometricResult.value = BiometricResult.AUTH_FAILED
                }
            })

        biometricPrompt.authenticate(getBiometricPromptInfo())

    }

    private fun getBiometricPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("App Authentication")
            .setNegativeButtonText("Cancel")
            .build()
    }
}