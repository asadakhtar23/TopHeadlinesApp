package com.android.topheadlinesapp.viewmodels

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

    var isBiometricScanned = false

    /**
     * check biometric sensor availability and it is configured in the device
     *
     * @param biometricManager BiometricManager of the biometric sensor
     *
     * @return Boolean - return true if the biometric sensor available and configured
     * otherwise return false
     */
    fun checkBiometricAvailability(biometricManager: BiometricManager): Boolean {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                return true
            }
        }
        return false
    }

    /**
     * init biometric prompt and get biometric prompt info to show biometric
     * authentication dialog to authenticate user
     *
     * @param fragmentActivity FragmentActivity
     * @param executor Executor
     *
     */
    fun initBiometricPrompt(fragmentActivity: FragmentActivity, executor: Executor) {

        biometricPrompt = BiometricPrompt(fragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    _biometricResult.value = BiometricResult.AUTH_ERROR
                    showErrorMessage.value = "Authentication error: $errString"
                    isBiometricScanned = true
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _biometricResult.value = BiometricResult.SUCCESS
                    isBiometricScanned = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    _biometricResult.value = BiometricResult.AUTH_FAILED
                }
            })

        biometricPrompt.authenticate(getBiometricPromptInfo())

    }

    /**
     * set biometric prompt info and return that prompt info to show
     * those details on the prompt dialog
     *
     * @return BiometricPrompt.PromptInfo - information to show on the prompt dialog
     *
     */
    private fun getBiometricPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("App Authentication")
            .setNegativeButtonText("Cancel")
            .build()
    }
}