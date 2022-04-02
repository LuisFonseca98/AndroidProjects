package a45125.dam.universeexplorer


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var idx = 0
    var idxSA = 0
    var intentAct: Intent? = null
    var t: TextView? = null
    var planets = ArrayList<Planet>()
    var colonIdx = ArrayList<Int>()
    var aLinearL = ArrayList<LinearLayout>()
    private var mp: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Universe Explorer"
        intentAct = Intent(this@MainActivity, PlanetActivity::class.java)
        mp = MediaPlayer.create(this@MainActivity, R.raw.planetcreated)
        for (i in 0..3) {
            val planet: Planet = Planet.Companion.getPlanetFromResources(i, resources)
            colonIdx.add(i)
            planets.add(planet)
        }
        for (i in 0..3) {
            addPlanetToUI(planets[i])
            setAction(aLinearL[i], i)
        }

    }

    private fun addPlanetToUI(planet: Planet) {
        val verticalLayout = findViewById<LinearLayout>(R.id.linear)
        val l = LinearLayout(this)
        l.orientation = LinearLayout.HORIZONTAL
        l.setBackgroundColor(resources.getColor(R.color.colorBackgroundYellow))
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(0, 15, 0, 0)
        l.layoutParams = layoutParams
        layoutParams.height = 320
        val iv = ImageView(this)
        iv.setBackgroundResource(planet.imgSmallResourceValues)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        iv.layoutParams = params
        val layoutParams2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams2.setMargins(20, 20, 0, 20)
        iv.layoutParams = layoutParams2
        iv.layoutParams.width = 280
        iv.layoutParams.height = 280
        val t = TextView(this)
        t.text = planet.name
        val layoutParams3 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams3.setMargins(50, 20, 50, 20)
        t.textSize = 20f
        t.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        t.setBackgroundColor(resources.getColor(R.color.gray))
        t.layoutParams = layoutParams3
        t.textAlignment = View.TEXT_ALIGNMENT_CENTER
        l.addView(iv)
        l.addView(t)
        verticalLayout.addView(l)
        if (aLinearL.size < 8) {
            aLinearL.add(l)
        }

    }

    private fun setAction(l: LinearLayout, index: Int) {
        l.setOnClickListener {
            intentAct?.putExtra("PlanetIdx", index)
            intentAct?.putExtra("ind", planets[index].index)
            intentAct?.putExtra("Name", planets[index].name)
            intentAct?.putExtra("Gravity", planets[index].gravity)
            intentAct?.putExtra("mass", planets[index].mass)
            intentAct?.putExtra("Colonies", planets[index].numColonies)
            intentAct?.putExtra("Mbases", planets[index].numMilitaryBases)
            intentAct?.putExtra("field", planets[index].isForceField)
            intentAct?.putExtra("img", planets[index].imgSmallResourceValues)
            startActivityForResult(intentAct, myRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            myRequestCode -> if (resultCode == RESULT_OK) {
                val extras = data!!.extras
                idx = extras!!.getInt("PlanetIdx")
                planets[idx].numColonies = extras.getInt("Colonies")
                planets[idx].numMilitaryBases = extras.getInt("Mbases")
                planets[idx].isForceField = extras.getBoolean("field")
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                    this@MainActivity,
                    "Some text to see what go wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            rqCodeSA -> if (resultCode == RESULT_OK) {
                val extras = data!!.extras
                val index = extras!!.getInt("idx")
                val p: Planet = Planet.Companion.getPlanetFromResources(index, resources)
                planets.add(p)
                colonIdx.add(p.index)
                addPlanetToUI(planets[planets.size - 1])
                setAction(aLinearL[aLinearL.size - 1], planets.size - 1)
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                    this@MainActivity,
                    "Some text to see what go wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_planets, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.colonizePlanet) {
            val intent = Intent(this@MainActivity, SelectPlanet::class.java)
            /*for(Planet p:planets){
                colonIdx.add(p.getIndex());
            }*/
            mp!!.start()
            intent.putIntegerArrayListExtra("ArrayInts", colonIdx)
            startActivityForResult(intent, rqCodeSA)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val LOG_MSG = "this is a string for debuging"
        private const val myRequestCode = 10
        private const val rqCodeSA = 20
    }
}