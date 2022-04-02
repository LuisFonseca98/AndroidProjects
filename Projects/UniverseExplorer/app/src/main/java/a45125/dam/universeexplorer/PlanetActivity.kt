package a45125.dam.universeexplorer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PlanetActivity : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var planetName: TextView
    lateinit var textMass: TextView
    lateinit var textGravity: TextView
    lateinit var textColonies: EditText
    lateinit var textBases: EditText
    lateinit var textField: EditText
    lateinit var save: ImageButton
    private var name: String? = null
    private val index = 0
    private var idx = 0
    private var grav = 0.0
    private var mass = 0.0
    private var col = 0
    private var mil = 0
    private var img1 = 0
    private var ind = 0
    private var force = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
        planetName = findViewById(R.id.titleApp)
        img = findViewById(R.id.planetPic)
        textMass = findViewById(R.id.massValue)
        textGravity = findViewById(R.id.grtValues)
        textColonies = findViewById(R.id.numColoniesValue)
        textBases = findViewById(R.id.numMilitaryBasesValue)
        textField = findViewById(R.id.ffValue)
        save = findViewById(R.id.saveButton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        setScreenPlanetInfo()
        save.setOnClickListener(View.OnClickListener {
            textColonies.setFocusableInTouchMode(false)
            textColonies.setClickable(false)
            textColonies.setBackgroundResource(R.color.colorPrimary)
            textBases.setFocusableInTouchMode(false)
            textBases.isClickable = false
            textBases.setBackgroundResource(R.color.colorPrimary)
            textField.setClickable(true)
            textField.setFocusableInTouchMode(false)
            textField.setBackgroundResource(R.color.colorPrimary)
            save.setVisibility(View.INVISIBLE)
            val intent = Intent(this@PlanetActivity, MainActivity::class.java)
            intent.putExtra("PlanetIdx", idx)
            intent.putExtra("Colonies", textColonies.getText().toString().toInt())
            intent.putExtra("Mbases", textBases.getText().toString().toInt())
            intent.putExtra("field", java.lang.Boolean.parseBoolean(textField.getText().toString()))
            setResult(RESULT_OK, intent)
            finish()
        })
        img.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@PlanetActivity, ImagePlanet::class.java)
            intent.putExtra("idx", ind)
            startActivity(intent)
        })
        textField.setOnClickListener(View.OnClickListener {
            if (textField.getText().toString() === "false") {
                textField.setText("true")
            } else {
                textField.setText("false")
            }
        })

        applicationInfo.targetSdkVersion = 10;

    }

    @SuppressLint("ResourceType", "SetTextI18n")
    fun setScreenPlanetInfo() {
        val extras = intent.extras
        idx = extras!!.getInt("PlanetIdx")
        name = extras.getString("Name")
        grav = extras.getDouble("Gravity")
        mass = extras.getDouble("mass")
        col = extras.getInt("Colonies")
        mil = extras.getInt("Mbases")
        force = extras.getBoolean("field")
        img1 = extras.getInt("img")
        ind = extras.getInt("ind")
        planetName!!.text = name
        img!!.setImageResource(img1)
        textMass!!.text = "" + mass
        textGravity!!.text = "" + grav
        textColonies!!.setText("" + col)
        textBases!!.setText("" + mil)
        textField!!.setText("" + force)
        title = "Planet $name"
    }

    //para mostrar os 3 pontos com opções
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_update_info, menu)
        return true
    }

    //para quando se clica opções do menu
    @SuppressLint("ResourceAsColor")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateInfo -> {
                textField!!.isClickable = true
                textField!!.isFocusableInTouchMode = true
                textField!!.setBackgroundResource(R.color.changeEditTextBG)
                textColonies!!.isFocusableInTouchMode = true
                textColonies!!.isClickable = true
                textColonies!!.setBackgroundResource(R.color.changeEditTextBG)
                textBases!!.isFocusableInTouchMode = true
                textBases!!.isClickable = true
                textBases!!.setBackgroundResource(R.color.changeEditTextBG)
                save!!.visibility = View.VISIBLE
                return true
            }
            R.id.surfacePlanet -> {
                val intent = Intent(this@PlanetActivity, ShowPlanetSurface::class.java)
                intent.putExtra("PlanetIdx", index)
                startActivity(intent)
                return true
            }
            R.id.attackPlanet -> {
                val intentAttack = Intent(this@PlanetActivity, AttackPlanet::class.java)
                intentAttack.putExtra("idx", ind)
                startActivity(intentAttack)
                return true
            }
            android.R.id.home -> return if (save!!.visibility == View.VISIBLE) {
                Toast.makeText(this, "Nope. Save state first!", Toast.LENGTH_SHORT).show()
                true
            } else {
                finish()
                true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }
}