package a45125.e.eventsapp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

class ScreenActionReceiver extends BroadcastReceiver {

    private LinearLayout evtLayout;
    private Context ctx;

    public ScreenActionReceiver(LinearLayout evtLayout, Context ctx){
        this.evtLayout = evtLayout;
        this.ctx = ctx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_SCREEN_ON.equals(action))
        {
            displaySep("SCREEN TURNED ON", getTime());

        }

        else if(Intent.ACTION_SCREEN_OFF.equals(action))
        {
            displaySep("SCREEN TURNED OFF", getTime());
        }

    }

    public IntentFilter getFilter(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        return filter;
    }

    private void displaySep(String s, String time){
        TextView tv = new TextView(ctx);
        System.out.println(tv);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(50, 10, 50, 0);
        tv.setLayoutParams(params);
        tv.setBackgroundColor(ContextCompat.getColor(ctx, R.color.orangeTV));
        tv.setText(s + " - " + time);
        evtLayout.addView(tv);
    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

}
