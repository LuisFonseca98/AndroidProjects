package a45125.myapplication;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
public class Planet{

    private int index;
    private String name;
    private double mass;
    private double gravity;
    private int numColonies = 1;
    private int numMilitaryBases = 0;
    private boolean forceField = false;
    private int[] imgResourceValues;
    private int imgSmallResourceValues;
    private static final String TAG = "MyActivity";


    public Planet(int index, String name, double mass, double gravity,int[] imgResourceValues, int imgSmallResourceValues ) {
        this.index = index;
        this.name = name;
        this.mass = mass;
        this.gravity = gravity;
        this.imgResourceValues = imgResourceValues;
        this.imgSmallResourceValues = imgSmallResourceValues;

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public int getNumColonies() {
        return numColonies;
    }

    public void setNumColonies(int numColonies) {
        this.numColonies = numColonies;
    }

    public int getNumMilitaryBases() {
        return numMilitaryBases;
    }

    public void setNumMilitaryBases(int numMilitaryBases) {
        this.numMilitaryBases = numMilitaryBases;
    }

    public boolean isForceField() {
        return forceField;
    }

    public void setForceField(boolean forceField) {
        this.forceField = forceField;
    }

    public int[] getImgResourceValues() {
        return imgResourceValues;
    }

    public void setImgResourceValues(int[] imgResourceValues) {
        this.imgResourceValues = imgResourceValues;
    }

    public int getImgSmallResourceValues() {
        return imgSmallResourceValues;
    }

    public void setImgSmallResourceValues(int imgSmallResourceValues) { this.imgSmallResourceValues = imgSmallResourceValues;}

    public String toString() {
        String s = "planet " + this.name + " com massa " + this.mass
                + " com gravidade " + this.gravity + " com colonias " + this.numColonies
                + " com bases militares de  " + this.numMilitaryBases;
        Log.i(TAG, s);
        return s;
    }

    public static Planet getPlanetFromResources(int index, Resources res) {
        TypedArray planets = res.obtainTypedArray(R.array.planets);
        int idPlanet = planets.getResourceId(index, -1);
        TypedArray planet = res.obtainTypedArray(idPlanet);
        String name = planet.getString(0);
        @SuppressLint("ResourceType") String mas = planet.getString(1);
        double mass = Double.parseDouble(mas);
        @SuppressLint("ResourceType") String grt = planet.getString(2);
        double gravity = Double.parseDouble(grt);
        @SuppressLint("ResourceType") int id = planet.getResourceId(3, -1);
        return new Planet(index,name,mass,gravity,new int[3],id);
    }


}
