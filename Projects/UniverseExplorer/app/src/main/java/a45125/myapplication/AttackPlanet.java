package a45125.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AttackPlanet extends AppCompatActivity {
    private ImageButton bombImage,invadeImage,infectImage ,laserImage,exitImage;
    private TextView tv;
    private ImageView iv;
    private Bundle extras;
    private int index;
    private AnimationDrawable infectAnimation;
    private Animation bombAnimation,laserAnimation;
    private boolean bombState = false,invadeState = false,infectedState = false,laserState = false,virusState = false;
    private TimerBroadcastReceiver timer;
    private SoundPool sp;
    private int sound1,sound2,sound3,sound4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_planet);
        startService(new Intent(this, MusicService.class));

        bombImage = findViewById(R.id.bombPic);
        invadeImage = findViewById(R.id.invasionPic);
        laserImage = findViewById(R.id.laserPic);
        exitImage = findViewById(R.id.exitPic);
        tv = findViewById(R.id.planetName);
        iv = findViewById(R.id.planetPic);

        bombAnimation = AnimationUtils.loadAnimation(this,R.anim.rotation);
        bombImage.startAnimation(bombAnimation);

        infectImage  = findViewById(R.id.infectionPic);
        infectImage.setBackgroundResource(R.drawable.infect_anim);
        infectAnimation = (AnimationDrawable) infectImage.getBackground();
        infectAnimation.start();

        laserAnimation = AnimationUtils.loadAnimation(this,R.anim.translation);
        timer = new TimerBroadcastReceiver(this) {
            @Override
            public void onTick() {
                laserImage.clearAnimation();
            }
        };

        extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setPlanetPicture();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        } else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }

        sound1 = sp.load(this,R.raw.explosion,1);
        sound2 = sp.load(this,R.raw.transport,2);
        sound3 = sp.load(this,R.raw.virus,3);
        sound4 = sp.load(this,R.raw.laser,4);


        bombImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bombState){
                    bombImage.setPressed(true);
                    bombState = true;
                    bombImage.setPressed(false);
                    bombImage.setActivated(true);
                    bombImage.clearAnimation();
                    sp.play(sound1,1,1,0,0,1);
                }else if(bombState){
                    bombImage.setActivated(false);
                    bombImage.setPressed(true);
                    bombState = false;
                    bombImage.setPressed(false);
                    bombImage.startAnimation(bombAnimation);

                }
            }
        });

        invadeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!invadeState){
                    invadeImage.setPressed(true);
                    invadeState = true;
                    invadeImage.setPressed(false);
                    invadeImage.setActivated(true);
                    sp.play(sound2,1,1,0,0,1);
                }else if(invadeState){
                    invadeImage.setActivated(false);
                    invadeImage.setPressed(true);
                    invadeState = false;
                    invadeImage.setPressed(false);
                }
            }
        });

        infectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!infectedState){
                    infectAnimation.stop();
                    infectImage.setPressed(true);
                    infectedState = true;
                    infectImage.setPressed(false);
                    infectImage.setActivated(true);
                    sp.play(sound3,1,1,0,0,1);
                }else if(infectedState){
                    infectAnimation.start();
                    infectImage.setActivated(false);
                    infectImage.setPressed(true);
                    infectedState = false;
                    infectImage.setPressed(false);
                }
            }
        });

        laserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!laserState){
                    laserImage.startAnimation(laserAnimation);
                    timer.start(6);
                    laserImage.setPressed(true);
                    laserState = true;
                    laserImage.setPressed(false);
                    laserImage.setActivated(true);
                    sp.play(sound4,1,1,0,0,1);
                }else if(laserState){
                    laserImage.clearAnimation();
                    timer.cancel();
                    laserImage.setActivated(false);
                    laserImage.setPressed(true);
                    laserState = false;
                    laserImage.setPressed(false);
                }
            }
        });

        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(AttackPlanet.this, MusicService.class));
                finish();

            }
        });

    }

    @SuppressLint("ResourceType")
    private void setPlanetPicture(){
        TypedArray arrayPlanets = getResources().obtainTypedArray(R.array.planets);
        index = extras.getInt("idx");
        int idPlanet = arrayPlanets.getResourceId(index, -1);
        TypedArray planet = getResources().obtainTypedArray(idPlanet);
        tv.setText(planet.getString(0));
        iv.setImageResource(planet.getResourceId(4,0));
        setTitle("Attack Planet" + tv.getText().toString());
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                stopService(new Intent(AttackPlanet.this, MusicService.class));
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
