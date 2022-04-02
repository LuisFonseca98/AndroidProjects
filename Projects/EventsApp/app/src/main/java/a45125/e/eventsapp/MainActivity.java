package a45125.e.eventsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LinearLayout evtLayout;
    private Button sepButton, clearButton;
    private ArrayList<String> events, times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.events = new ArrayList<>();
        this.times = new ArrayList<>();
        setContentView(R.layout.activity_main);
        evtLayout = findViewById(R.id.evt_layout);
        final ScreenActionReceiver screenactionreceiver = new ScreenActionReceiver(evtLayout, this);
        registerReceiver(screenactionreceiver, screenactionreceiver.getFilter());

        sepButton = findViewById(R.id.sep_btn);
        clearButton = findViewById(R.id.clear_btn);

        displayEvt(getTime(), "onCreate");

        events();

    }

    public void events(){
        sepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySep(getTime());
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                evtLayout.removeAllViews();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayEvt(getTime(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayEvt(getTime(), "onResume");
        displaySep(getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        displayEvt(getTime(), "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        displayEvt(getTime(), "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        displayEvt(getTime(), "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("events", events);
        outState.putStringArrayList("times", times);
        displayEvt(getTime(), "onSaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        outState.getStringArrayList("events");
        outState.getStringArrayList("times");
        displayEvt(getTime(), "onRestoreInstanceState");
    }


    private void displayEvt(String time, String event){
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(50, 10, 50, 0);
        tv.setLayoutParams(params);

        if(event.equals("onCreate")){
            tv.setBackgroundColor(ContextCompat.getColor(this, R.color.greenTv));
            String orientation = "";
            if(this.getResources().getConfiguration().orientation == 1)  orientation = "Portrait";
            else orientation = "Landscape";
            tv.setText(event + " - " + orientation + " - " + time);
        } else{
            tv.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowTv));
            tv.setText(event + " - " + time);
        }
        events.add(event);
        times.add(time);
        evtLayout.addView(tv);
    }

    private void displaySep(String time){
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(50, 10, 50, 0);
        tv.setLayoutParams(params);

        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.sepTv));
        tv.setText("---------------------- " + time + "---------------------- ");

        events.add("onSeparator");
        times.add(time);
        evtLayout.addView(tv);
    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayEvt(getTime(), "onCreate");
            displayEvt(getTime(), "onStart");
            displayEvt(getTime(), "onConfigurationChange");
            displayEvt(getTime(), "onResume");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            displayEvt(getTime(), "onCreate");
            displayEvt(getTime(), "onStart");
            displayEvt(getTime(), "onConfigurationChange");
            displayEvt(getTime(), "onResume");
        }
    }

}
