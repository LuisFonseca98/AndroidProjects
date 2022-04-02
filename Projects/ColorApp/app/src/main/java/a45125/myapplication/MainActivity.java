package a45125.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutLeft,layoutRight,layoutBlue;
    private Button plusButton,minusButton;
    private TextView blueTXT;
    private int bcr = 100, bcg = 100, bcb = 100;
    private float dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = this.findViewById(R.id.plusR);
        layoutLeft = findViewById(R.id.UpperLayout);
        layoutRight = findViewById(R.id.LowerLayout);
        layoutBlue = findViewById(R.id.BlueLayout);

        dm = getScreenDensity(this);
        dm = getDmin(dm);
        landscapeAdjust(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress(findViewById(R.id.plusR));
            }
        });
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(findViewById(R.id.plusR));
                return true;
            }
        });

        b = this.findViewById(R.id.minusR);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress(findViewById(R.id.minusR));
            }
        });

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(findViewById(R.id.minusR));
                return true;
            }
        });

        b = this.findViewById(R.id.plusG);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress(findViewById(R.id.plusG));
                new CountDownTimer(100, 10){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        buttonPress(findViewById(R.id.plusG));
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        });


        b = this.findViewById(R.id.minusG);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(100, 10){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        buttonPress(findViewById(R.id.minusG));
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        });

        b = this.findViewById(R.id.plusB);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress(findViewById(R.id.plusB));
            }
        });

        b = this.findViewById(R.id.minusB);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress(findViewById(R.id.minusB));
            }
        });

    }

    public void buttonPress(View v) {

        TextView redtv = findViewById(R.id.resR);
        TextView greentv = findViewById(R.id.resG);
        TextView bluetv = findViewById(R.id.resB);
        TextView colortv = findViewById(R.id.resultFinal);

        switch (v.getId()) {
            case R.id.minusR:
                bcr = decrease(redtv);
                redtv.setBackgroundColor(Color.rgb(bcr,0,0));
                break;
            case R.id.plusR:
                bcr = increase(redtv);
                redtv.setBackgroundColor(Color.rgb(bcr,0,0));
                break;
            case R.id.minusG:
                bcg = decrease(greentv);
                greentv.setBackgroundColor(Color.rgb(0,bcg,0));
                break;
            case R.id.plusG:
                bcg = increase(greentv);
                greentv.setBackgroundColor(Color.rgb(0,bcg,0));
                break;
            case R.id.minusB:
                bcb = decrease(bluetv);
                bluetv.setBackgroundColor(Color.rgb(0,0,bcb));
                break;
            case R.id.plusB:
                bcb = increase(bluetv);
                bluetv.setBackgroundColor(Color.rgb(0,0,bcb));
                break;
        }

        colortv.setBackgroundColor(Color.rgb(bcr,bcg,bcb));
        colortv.setText(bcr + ", " + bcg + ", " + bcb);
    }


    public int increase(TextView tv){
        int old_text = Integer.parseInt(tv.getText().toString());
        int new_text = (old_text < 255)? old_text + 1 : old_text;
        tv.setText(new_text + "");
        return new_text;

    }

    public int decrease(TextView tv){
        int old_text = Integer.parseInt(tv.getText().toString());
        int new_text = (old_text > 1)? old_text - 1 : old_text;
        tv.setText(new_text + "");
        return new_text;
    }

    private float getDmin(float dm){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return dm*height;
    }

    private void landscapeAdjust (Activity a) {
        int heightNeeded = (int) dm;
        if (getScreenOrientation(a) == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textResult = findViewById(R.id.resultFinal);
            if (layoutLeft.getHeight() < heightNeeded) {
                layoutLeft.removeView(layoutBlue);
                layoutRight.removeView(textResult);
                layoutRight.addView(layoutBlue);
                layoutRight.addView(textResult);
            }
        }
    }

    public static int getScreenOrientation (Activity ac) {
        return ac.getResources().getConfiguration().orientation;
    }

    public static float getScreenDensity (Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Objects.requireNonNull(windowManager).getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }


}
