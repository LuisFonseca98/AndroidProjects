package a45125.dam.universeexplorer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class ImagePlanet : AppCompatActivity() {
    lateinit var planetName: TextView
    lateinit var img: ImageView
    private var index = 0
    private var extras: Bundle? = null
    private val tb: Toolbar? = null
    private var changeImg = false
    private var arrayPlanets: TypedArray? = null
    private var planet: TypedArray? = null
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_planet)
        planetName = findViewById(R.id.planetNameImage)
        img = findViewById(R.id.planetpicBig)
        extras = intent.extras
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        arrayPlanets = resources.obtainTypedArray(R.array.planets)
        index = extras!!.getInt("idx")
        val idPlanet = arrayPlanets!!.getResourceId(index, -1)
        planet = resources.obtainTypedArray(idPlanet)
        planetName.setText(planet!!.getString(0))
        img.setImageResource(planet!!.getResourceId(4, 0))
        title = "Planet " + planetName.getText().toString() + " images "
        img.setOnClickListener(View.OnClickListener {
            if (!changeImg) {
                changeImg = true
                img.setImageResource(planet!!.getResourceId(5, -1))
            } else {
                changeImg = false
                img.setImageResource(planet!!.getResourceId(4, -1))
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}