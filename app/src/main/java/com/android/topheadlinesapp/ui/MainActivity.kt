package com.android.topheadlinesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.topheadlinesapp.enums.BiometricResult
import com.android.topheadlinesapp.BuildConfig
import com.android.topheadlinesapp.R
import com.android.topheadlinesapp.databinding.ActivityMainBinding
import com.android.topheadlinesapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = BuildConfig.PROVIDER
        observeViewModel()
        if(!viewModel.isBiometricScanned)
            initView()
    }

    private fun observeViewModel() {
        viewModel.biometricResult.observe(this) { result ->
            handleUI(result)
        }

        viewModel.showErrorMessage.observe(this) { message ->
            showSnackBar(binding.root, "$message (Application is Closing in 2 Seconds)")
        }
    }

    private fun handleUI(result: BiometricResult) {
        when (result) {
            BiometricResult.SUCCESS -> {
                binding.topLayer.visibility = View.GONE
            }
            BiometricResult.AUTH_ERROR -> {
                binding.topLayer.visibility = View.VISIBLE

                lifecycleScope.launch {
                    delay(3500)
                    finish()
                }
            }
            BiometricResult.AUTH_FAILED -> {
                binding.topLayer.visibility = View.VISIBLE
            }
        }
    }

    private fun initView() {
        if (viewModel.checkBiometricAvailability(BiometricManager.from(this))) {
            binding.topLayer.visibility = View.VISIBLE
            viewModel.initBiometricPrompt(this, ContextCompat.getMainExecutor(this))
        } else {
            binding.topLayer.visibility = View.GONE
        }
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}