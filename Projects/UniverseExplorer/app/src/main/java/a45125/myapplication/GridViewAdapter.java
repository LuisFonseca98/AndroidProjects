package a45125.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {

    public GridViewAdapter(Context context , ArrayList<Planet> data) {
        super(context , R.layout.layout_planet_item , R.id.textViewPlanetSelect , data);
    }

    @SuppressLint("LongLogTag")
    @Override public View getView(int position , View view, ViewGroup parent) {
        Log.d(MainActivity.LOG_MSG , "getView " + position + " " + view);
        if (view == null) {
            // create base view
            view = super.getView(position , view, parent);
            // set data from data object to view
            Planet planet = (Planet) getItem(position);
            TextView tv = view.findViewById(R.id.textViewPlanetSelect);
            tv.setText(planet.getName());
            ImageView iv = view.findViewById(R.id.imageViewPlanetSelect);
            iv.setImageResource(planet.getImgSmallResourceValues());
            // set object on view tag
            view.setTag(planet);
        }
        return view;
    }

}
