package a45125.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static android.content.Context.ALARM_SERVICE;

public abstract class TimerBroadcastReceiver extends BroadcastReceiver {
    private static final int REQUEST_CODE = 101;
    private static int TIMER_ID = 0;
    private final String ACTION_TIMER = "dam_a45125.ACTIONTIMER -"+ TIMER_ID ++;
    private final Context context;
    private final AlarmManager alarmManager;
    private final PendingIntent alarmPendingIntent;

    public TimerBroadcastReceiver ( Context context ) {
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        context.registerReceiver (this , new IntentFilter(ACTION_TIMER));
        alarmPendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, new Intent(ACTION_TIMER), 0);
    }

    public void start ( long delaySegs ) {
        long time = System.currentTimeMillis() + delaySegs * 1000;
        alarmManager.setExact(AlarmManager.RTC , time , alarmPendingIntent);
    }

    public void cancel () {
        alarmManager.cancel(alarmPendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onTick();
    }

    public abstract void onTick();

    public void onDestroy() {
        context.unregisterReceiver(this);
    }
}
