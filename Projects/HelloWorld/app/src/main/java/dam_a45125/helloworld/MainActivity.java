package dam_a45125.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable animDraw;
    private RatingBar ratingBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        button = (Button) findViewById(R.id.checkButton);
        ImageView imageView = (ImageView) findViewById(R.id.imageAnim);
        imageView.setBackgroundResource(R.drawable.animation);
        animDraw = (AnimationDrawable) imageView.getBackground();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(MainActivity.this,"Stars: " + (int)rating,Toast.LENGTH_LONG).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Stars: " + (int)ratingBar.getRating(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animDraw.start();
    }
}
