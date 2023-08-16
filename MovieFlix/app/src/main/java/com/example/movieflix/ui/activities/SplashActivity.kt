package com.example.movieflix.ui.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieflix.R
import com.example.movieflix.databinding.ActivitySplashBinding
import com.example.movieflix.utils.Animations
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity()
{
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding
    private val splashDuration: Long = 5000
    private val splashDuration2: Long = 10000

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        // Animation on icon
        val animation = Animations()
        binding!!.imageView.startAnimation(animation.performAnimation())
        // Launch the coroutine
        CoroutineScope(Dispatchers.Main).launch {
            internetAvailability()
        }
    }

    private suspend fun internetAvailability()
    {
        // Check for internet connectivity
        if (checkForInternet(this))
        {
            // Delay the transition to MainActivity2
            delay(splashDuration)
            startActivity(Intent(this@SplashActivity, MovieActivity::class.java))
            finish() // Close the current activity
        }
        else
        {
            showInternetDialog()
        }
    }

    private fun showInternetDialog()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.act1_dialogue_title))
            .setMessage(resources.getString(R.string.act1_dialogue_text))
            .setNeutralButton(resources.getString(R.string.act1_dialogue_cancel)) { _, _ ->
                Toast.makeText(this, "Exiting Application !", Toast.LENGTH_SHORT).show()
                finishAndRemoveTask()
            }
            .setNegativeButton(resources.getString(R.string.act1_dialogue_decline)) { _, _ ->
                Toast.makeText(this, "Exiting Application !", Toast.LENGTH_SHORT).show()
                finishAndRemoveTask()
            }
            .setPositiveButton(resources.getString(R.string.act1_dialogue_accept)) { _, _ ->
                checkForInternetAndContinue()
            }
            .show()
    }

    private fun checkForInternet(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when
            {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        else
        {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun checkForInternetAndContinue()
    {
        // Launch a new coroutine
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashDuration2)
            val isConnected = checkForInternet(this@SplashActivity)
            if (!isConnected)
            {
                Toast.makeText(this@SplashActivity, "Exiting Application !", Toast.LENGTH_SHORT).show()
                finishAndRemoveTask()
            }
            else
            {
                startActivity(Intent(this@SplashActivity, MovieActivity::class.java))
                finish() // Close the current activity
            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}



