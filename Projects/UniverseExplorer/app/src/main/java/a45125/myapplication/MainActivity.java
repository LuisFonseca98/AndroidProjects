package a45125.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_MSG = "this is a string for debuging" ;
    int idx,idxSA;
    Intent intent;
    TextView t;
    ArrayList<Planet> planets = new ArrayList<Planet>();
    ArrayList<Integer> colonIdx = new ArrayList<Integer>();
    ArrayList<LinearLayout> aLinearL = new ArrayList<LinearLayout>();
    private static final int myRequestCode=10,rqCodeSA=20;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Universe Explorer");
        intent = new Intent(MainActivity.this, PlanetActivity.class);
        mp = MediaPlayer.create(MainActivity.this, R.raw.planetcreated);
        for(int i=0; i<4;i++){
            Planet planet = Planet.getPlanetFromResources(i,getResources());
            colonIdx.add(i);
            planets.add(planet);
        }
        for(int i=0;i<4;i++){
            addPlanetToUI(planets.get(i));
            setAction(aLinearL.get(i),i);
        }


    }

    private void addPlanetToUI(final Planet planet) {
        LinearLayout verticalLayout = findViewById(R.id.linear);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setBackgroundColor(getResources().getColor(R.color.colorBackgroundYellow));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 15, 0, 0);
        l.setLayoutParams(layoutParams);
        layoutParams.height = 320;

        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(planet.getImgSmallResourceValues());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams2.setMargins(20, 20, 0, 20);
        iv.setLayoutParams(layoutParams2);
        iv.getLayoutParams().width = 280;
        iv.getLayoutParams().height = 280;

        TextView t = new TextView(this);
        t.setText(planet.getName());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams3.setMargins(50, 20, 50, 20);
        t.setTextSize(20);
        t.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        t.setBackgroundColor(getResources().getColor(R.color.gray));
        t.setLayoutParams(layoutParams3);
        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        l.addView(iv);
        l.addView(t);
        verticalLayout.addView(l);



        if(aLinearL.size()<8){aLinearL.add(l);}

    }
    private void setAction(LinearLayout l, final int index){
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("PlanetIdx",index);
                intent.putExtra("ind",planets.get(index).getIndex());
                intent.putExtra("Name",planets.get(index).getName());
                intent.putExtra("Gravity",planets.get(index).getGravity());
                intent.putExtra("mass",planets.get(index).getMass());
                intent.putExtra("Colonies",planets.get(index).getNumColonies());
                intent.putExtra("Mbases",planets.get(index).getNumMilitaryBases());
                intent.putExtra("field",planets.get(index).isForceField());
                intent.putExtra("img",planets.get(index).getImgSmallResourceValues());
                startActivityForResult(intent,myRequestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case myRequestCode:
                if(resultCode==RESULT_OK){
                    Bundle extras = data.getExtras();
                    idx = extras.getInt("PlanetIdx");
                    planets.get(idx).setNumColonies(extras.getInt("Colonies"));
                    planets.get(idx).setNumMilitaryBases(extras.getInt("Mbases"));
                    planets.get(idx).setForceField(extras.getBoolean("field"));
                }else if(resultCode==RESULT_CANCELED){
                    Toast.makeText(MainActivity.this, "Some text to see what go wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
            case rqCodeSA:
                if(resultCode==RESULT_OK){
                    Bundle extras = data.getExtras();
                    int index= extras.getInt("idx");
                    Planet p = Planet.getPlanetFromResources(index,getResources());
                    planets.add(p);
                    colonIdx.add(p.getIndex());
                    addPlanetToUI(planets.get(planets.size()-1));
                   setAction(aLinearL.get(aLinearL.size()-1),planets.size()-1);
                }
                else if(resultCode==RESULT_CANCELED){
                    Toast.makeText(MainActivity.this, "Some text to see what go wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_planets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.colonizePlanet) {
            Intent intent = new Intent(MainActivity.this,SelectPlanet.class);
            /*for(Planet p:planets){
                colonIdx.add(p.getIndex());
            }*/
            mp.start();
            intent.putIntegerArrayListExtra("ArrayInts",colonIdx);
            startActivityForResult(intent,rqCodeSA);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
