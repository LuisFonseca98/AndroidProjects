package a45125.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlanetActivity extends AppCompatActivity {

    private TextView planetName;
    private ImageView img;
    private TextView textMass, textGravity;
    private EditText textColonies, textBases,textField;
    private ImageButton save;
    private String name;
    private int index, idx;
    private double grav,mass;
    private  int col,mil,img1,ind;
    private  boolean force;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        planetName = findViewById(R.id.titleApp);
        img = findViewById(R.id.planetPic);
        textMass = findViewById(R.id.massValue);
        textGravity = findViewById(R.id.grtValues);
        textColonies = findViewById(R.id.numColoniesValue);
        textBases = findViewById(R.id.numMilitaryBasesValue);
        textField = findViewById(R.id.ffValue);
        save = findViewById(R.id.saveButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setScreenPlanetInfo();

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                textColonies.setFocusableInTouchMode(false);
                textColonies.setClickable(false);
                textColonies.setBackgroundResource(R.color.colorPrimary);

                textBases.setFocusableInTouchMode(false);
                textBases.setClickable(false);
                textBases.setBackgroundResource(R.color.colorPrimary);

                textField.setClickable(true);
                textField.setFocusableInTouchMode(false);
                textField.setBackgroundResource(R.color.colorPrimary);

                save.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(PlanetActivity.this, MainActivity.class);
                intent.putExtra("PlanetIdx", idx);
                intent.putExtra("Colonies", Integer.parseInt(textColonies.getText().toString()));
                intent.putExtra("Mbases", Integer.parseInt(textBases.getText().toString()));
                intent.putExtra("field", Boolean.parseBoolean(textField.getText().toString()));
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanetActivity.this, ImagePlanet.class);
                intent.putExtra("idx", ind);
                startActivity(intent);
            }
        });

        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textField.getText().toString() == "false"){
                    textField.setText("true");
                }else{
                    textField.setText("false");
                }
            }
        });

    }


    @SuppressLint({"ResourceType", "SetTextI18n"})
    public void setScreenPlanetInfo() {
        Bundle extras = getIntent().getExtras();
        idx = extras.getInt("PlanetIdx");
        name=extras.getString("Name");
        grav=extras.getDouble("Gravity");
        mass=extras.getDouble("mass");
        col=extras.getInt("Colonies");
        mil=extras.getInt("Mbases");
        force=extras.getBoolean("field");
        img1=extras.getInt("img");
        ind=extras.getInt("ind");
        planetName.setText(name);
        img.setImageResource(img1);
        textMass.setText("" + mass);
        textGravity.setText("" + grav);
        textColonies.setText("" + col);
        textBases.setText("" + mil);
        textField.setText("" + force);
        setTitle("Planet " + name);
    }

    //para mostrar os 3 pontos com opções
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_update_info, menu);
        return true;
    }

    //para quando se clica opções do menu
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateInfo:
                textField.setClickable(true);
                textField.setFocusableInTouchMode(true);
                textField.setBackgroundResource(R.color.changeEditTextBG);
                textColonies.setFocusableInTouchMode(true);
                textColonies.setClickable(true);
                textColonies.setBackgroundResource(R.color.changeEditTextBG);
                textBases.setFocusableInTouchMode(true);
                textBases.setClickable(true);
                textBases.setBackgroundResource(R.color.changeEditTextBG);
                save.setVisibility(View.VISIBLE);
                return true;

            case R.id.surfacePlanet:
                Intent intent = new Intent(PlanetActivity.this, ShowPlanetSurface.class);
                intent.putExtra("PlanetIdx", index);
                startActivity(intent);
                return true;

            case R.id.attackPlanet:
                Intent intentAttack = new Intent(PlanetActivity.this, AttackPlanet.class);
                intentAttack.putExtra("idx", ind);
                startActivity(intentAttack);
                return true;

            case android.R.id.home:
                if (save.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, "Nope. Save state first!", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    this.finish();
                    return true;
                }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}