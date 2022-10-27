package com.android.topheadlinesapp

import androidx.biometric.BiometricManager
import com.android.topheadlinesapp.viewmodels.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    private val mainViewModel = MainViewModel()

    private val biometricManager: BiometricManager = mockk()

    @Test
    fun `should return true if biometric sensor available` () {
        every { biometricManager.canAuthenticate(any()) } returns BiometricManager.BIOMETRIC_SUCCESS
        Assert.assertTrue(mainViewModel.checkBiometricAvailability(biometricManager))
    }

    @Test
    fun `should return false if biometric sensor is not available` () {
        every { biometricManager.canAuthenticate(any()) } returns BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
        Assert.assertTrue(!mainViewModel.checkBiometricAvailability(biometricManager))
    }
}