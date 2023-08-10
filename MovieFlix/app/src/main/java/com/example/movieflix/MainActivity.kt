package com.example.movieflix

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieflix.databinding.ActivityMainBinding
import com.example.movieflix.models.Animations
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val splashDuration: Long = 5000
    private val splashDuration2: Long = 10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        // Animation on icon
        val animation= Animations()
        binding!!.imageView.startAnimation(animation.performAnimation())


        showPrompt()


    }

    private fun showPrompt()
    {
        // Check for internet connectivity
        if (checkForInternet(this))
        {
            // Delay the transition to MainActivity2
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish() // Close the current activity
            }, splashDuration)
        }
        else
        {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.act1_dialogue_title))
                .setMessage(resources.getString(R.string.act1_dialogue_text))
                .setNeutralButton(resources.getString(R.string.act1_dialogue_cancel)) { _, _ ->
                    exitApp()
                }
                .setNegativeButton(resources.getString(R.string.act1_dialogue_decline)) { _, _ ->
                    exitApp()
                }
                .setPositiveButton(resources.getString(R.string.act1_dialogue_accept)) { _, _ ->
                    checkForInternetAndContinue()
                }
                .show()
        }
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

    private fun exitApp()
    {
        Toast.makeText(this, "Exiting Application !", Toast.LENGTH_SHORT).show()
        finish()
        exitProcess(0)
    }

    private fun checkForInternetAndContinue()
    {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (!checkForInternet(this))
            {
                exitApp()
            }
            else
            {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish() // Close the current activity
            }
        }, splashDuration2)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}
