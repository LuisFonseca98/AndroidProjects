package a45125.myapplication;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectPlanet extends AppCompatActivity {
    private Intent intent;
    private Bundle extras;
    private ArrayList<Integer> alIndex = new ArrayList<Integer>();
    private ArrayList<Planet> freePlanets = new ArrayList<Planet>();
    private GridView grid;
    private GridViewAdapter gridADP;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_planet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        grid = findViewById(R.id.gridView);
        extras = getIntent().getExtras();
        alIndex = extras.getIntegerArrayList("ArrayInts");
        freePlanets=getFreePlanets(alIndex);
        gridADP = new GridViewAdapter(SelectPlanet.this,freePlanets);
        grid.setAdapter(gridADP);
        setTitle("Select Planet");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<Planet> getFreePlanets(ArrayList<Integer> activePlanets){
        TypedArray array = getResources().obtainTypedArray(R.array.planets);
        ArrayList<Planet> freePlanets = new ArrayList<Planet>();
        for(int i=0; i<8;i++){
            if(activePlanets.contains(i)){
            }else{
                freePlanets.add(Planet.getPlanetFromResources(i,getResources()));
            }
        }
        return freePlanets;
    }

    public void onClickGridViewPlanetsItem(View view){
        Planet p = (Planet) view.getTag();
        int idx = p.getIndex();
        Intent intent = new Intent();
        intent.putExtra("idx",idx);
        setResult(RESULT_OK,intent);
        finish();

    }
}
