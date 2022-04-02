package a45125.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class ImagePlanet extends AppCompatActivity {
    private TextView planetName;
    private ImageView img;
    private int index;
    private Bundle extras;
    private Toolbar tb;
    private boolean changeImg = false;
    private TypedArray arrayPlanets,planet;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_planet);

        planetName = findViewById(R.id.planetNameImage);
        img = findViewById(R.id.planetpicBig);

        extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayPlanets = getResources().obtainTypedArray(R.array.planets);
        index = extras.getInt("idx");
        int idPlanet = arrayPlanets.getResourceId(index, -1);
        planet = getResources().obtainTypedArray(idPlanet);
        planetName.setText(planet.getString(0));
        img.setImageResource(planet.getResourceId(4,0));
        setTitle("Planet " + planetName.getText().toString() + " images ");


        img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if(!changeImg){
                    changeImg=true;
                    img.setImageResource(planet.getResourceId(5,-1));
                }else{
                    changeImg=false;
                    img.setImageResource(planet.getResourceId(4,-1));
                }
            }
        });
    }


    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}