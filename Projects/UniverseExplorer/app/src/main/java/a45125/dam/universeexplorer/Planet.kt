package a45125.dam.universeexplorer

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log

class Planet(
    var index: Int,
    var name: String?,
    var mass: Double,
    var gravity: Double,
    var imgResourceValues: IntArray,
    var imgSmallResourceValues: Int
) {
    var numColonies = 1
    var numMilitaryBases = 0
    var isForceField = false
    fun setMass(mass: Int) {
        this.mass = mass.toDouble()
    }

    override fun toString(): String {
        val s = ("planet " + name + " com massa " + mass
                + " com gravidade " + gravity + " com colonias " + numColonies
                + " com bases militares de  " + numMilitaryBases)
        Log.i(TAG, s)
        return s
    }

    companion object {
        private const val TAG = "MyActivity"
        fun getPlanetFromResources(index: Int, res: Resources): Planet {
            val planets = res.obtainTypedArray(R.array.planets)
            val idPlanet = planets.getResourceId(index, -1)
            val planet = res.obtainTypedArray(idPlanet)
            val name = planet.getString(0)
            @SuppressLint("ResourceType") val mas = planet.getString(1)
            val mass = mas!!.toDouble()
            @SuppressLint("ResourceType") val grt = planet.getString(2)
            val gravity = grt!!.toDouble()
            @SuppressLint("ResourceType") val id = planet.getResourceId(3, -1)
            return Planet(index, name, mass, gravity, IntArray(3), id)
        }
    }
}