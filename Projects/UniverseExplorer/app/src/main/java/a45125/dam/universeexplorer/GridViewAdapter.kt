package a45125.dam.universeexplorer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

internal class GridViewAdapter(context: Context, data: ArrayList<Planet>) : ArrayAdapter<Any>(
    context, R.layout.layout_planet_item, R.id.textViewPlanetSelect, data as List<Any>) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view2 = view
        //Log.d("LOG_MSG", "getView $position $view")
        if (view2 == null) {
            // create base view
            view2 = super.getView(position, view, parent)
            // set data from data object to view
            val planet = getItem(position) as Planet?
            val tv = view2.findViewById<TextView>(R.id.textViewPlanetSelect)
            tv.text = planet!!.name
            val iv = view2.findViewById<ImageView>(R.id.imageViewPlanetSelect)
            iv.setImageResource(planet.imgSmallResourceValues)
            // set object on view tag
            view2.tag = planet
        }
        return view2
    }
}