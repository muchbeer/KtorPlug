package com.muchbeer.ktorplug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.muchbeer.ktorplug.databinding.ActivityMainBinding
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.worker.ServerWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view =  binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val topLevelDestinations = setOf(R.id.saveFragment, R.id.viewFragment)
         appBarConfiguration = AppBarConfiguration(topLevelDestinations)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

       // sendToServer()
    }

    override fun onNavigateUp() : Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun sendToServer() {
        val work = PeriodicWorkRequestBuilder<ServerWorker>(5, TimeUnit.MINUTES)
            .setConstraints(setConstraint())
            .build()

        WorkManager.getInstance(this).enqueue(work)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(work.id).observe(this)
             {
                if (it != null) {
                    when (it.state) {

                        WorkInfo.State.SUCCEEDED -> {
                           // val myResult = it.outputData.getString("Success")
                            logs(TAG, "SUCCEEDED")
                        }

                        WorkInfo.State.RUNNING -> {  logs(TAG, "RUNNING")   }
                        WorkInfo.State.ENQUEUED -> {  logs(TAG, "ENQUIED") }
                        WorkInfo.State.FAILED -> { logs(TAG, "FAILED") }
                        WorkInfo.State.BLOCKED -> { logs(TAG, "BLOCKED") }
                        WorkInfo.State.CANCELLED -> { logs(TAG, "CANCELLED") }
                    }.exhaustive
                }

            }
    }

    private fun setConstraint(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()

    }

    companion object {
        private val TAG = MainActivity::class.simpleName.toString()
    }
}