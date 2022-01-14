package com.mygdx.game;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.Firebase.DataHolderClass;
import com.mygdx.game.Firebase.FirebaseInterface;

import static android.content.ContentValues.TAG;

public class AndroidInterFaceClass implements FirebaseInterface {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public AndroidInterFaceClass()
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Level");
    }

    @Override
    public void SomeFunction() {
        System.out.println("Just some function");

    }

    @Override
    public void FirstFireBaseTest() {
        if(myRef != null){
            myRef.setValue("Hello, World!");
        }
        else{
            System.out.println("Databasereference was not set -> therefore could not write to DB");
        }
    }


    @Override
    public void SetOnValueChangedListener(final DataHolderClass dataholder) {
        myRef.addValueEventListener(new ValueEventListener() {
            // Read from the database

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                //dataholder.someValue = value;
                //dataholder.PrintSomeValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void SetValueInDb(String target, int score) {
        myRef = database.getReference(target);
        myRef.setValue(String.valueOf(score));
    }



}
