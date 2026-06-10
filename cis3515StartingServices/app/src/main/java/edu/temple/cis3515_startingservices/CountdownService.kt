package edu.temple.cis3515_startingservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownService : Service() {

    companion object {
        const val START_VALUE = "start_value"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val startValue = intent?.getIntExtra(START_VALUE, -1) ?: -1

        if(startValue < 0) {
            Log.d("Countdown", "Invalid Value")
            stopSelf(startId)
            return START_NOT_STICKY
        }


        serviceScope.launch {
            Log.d("Countdown", "Start from $startValue")

            for(i in startValue downTo  0) {
                Log.d("Countdown", "$i")
                delay(1000)
            }

            Log.d("Countdown", "Finished")
            stopSelf(startId)
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}