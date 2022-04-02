package a45125.dam.universeexplorer

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SelectPlanet : AppCompatActivity() {
    private var extras: Bundle? = null
    private var alIndex: ArrayList<Int>? = ArrayList()
    private var freePlanets = ArrayList<Planet>()
    lateinit var grid: GridView
    private var gridADP: GridViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_planet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        grid = findViewById(R.id.gridView)
        extras = intent.extras
        alIndex = extras!!.getIntegerArrayList("ArrayInts")
        freePlanets = getFreePlanets(alIndex)
        gridADP = GridViewAdapter(this@SelectPlanet, freePlanets)
        grid.adapter = gridADP
        title = "Select Planet"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getFreePlanets(activePlanets: ArrayList<Int>?): ArrayList<Planet> {
        val array = resources.obtainTypedArray(R.array.planets)
        val freePlanets = ArrayList<Planet>()
        for (i in 0..7) {
            if (activePlanets!!.contains(i)) {
            } else {
                freePlanets.add(Planet.Companion.getPlanetFromResources(i, resources))
            }
        }
        return freePlanets
    }

    fun onClickGridViewPlanetsItem(view: View) {
        val p = view.tag as Planet
        val idx = p.index
        val intent = Intent()
        intent.putExtra("idx", idx)
        setResult(RESULT_OK, intent)
        finish()
    }
}