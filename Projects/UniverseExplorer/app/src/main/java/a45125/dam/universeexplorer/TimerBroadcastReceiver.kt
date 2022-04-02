package a45125.dam.universeexplorer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.IntentFilter


internal abstract class TimerBroadcastReceiver(private val context: Context) : BroadcastReceiver() {
    companion object {
        private const val REQUEST_CODE = 101
        private var TIMER_ID = 0
    }
    private val ACTION_TIMER = "dam_45125.ACTION_TIMER-" + TIMER_ID++
    private val alarmManager: AlarmManager
    private val alarmPendingIntent: PendingIntent

    init {
        alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        context.registerReceiver(this, IntentFilter(ACTION_TIMER))
        alarmPendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, Intent(ACTION_TIMER), 0)
    }


    fun start(delaySegs: Long) {
        val time = System.currentTimeMillis() + delaySegs * 1000
        alarmManager.setExact(AlarmManager.RTC, time, alarmPendingIntent)
    }

    fun cancel() {
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        onTick()
    }

    abstract fun onTick()
    fun onDestroy() {
        context.unregisterReceiver(this)
    }

}