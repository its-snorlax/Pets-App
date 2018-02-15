/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetsDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetsDbHelper petsDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        petsDbHelper = new PetsDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPetData();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase readableDatabase = petsDbHelper.getReadableDatabase();
        String [] projection = {
          PetEntry._ID,
                PetEntry.PET_NAME,
                PetEntry.BREED_NAME,
                PetEntry.GENDER,
                PetEntry.WEIGHT
        };

        Cursor cursor = readableDatabase.query(PetEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        TextView petList = (TextView) findViewById(R.id.text_view_pet);
        try {
            petList.setText ("pet table contain - " + cursor.getCount() + "pets. \n\n");
            petList.append(PetEntry._ID + " - " );
            petList.append(PetEntry.PET_NAME + " - ");
            petList.append(PetEntry.BREED_NAME + " - ");
            petList.append(PetEntry.GENDER + " - ");
            petList.append(PetEntry.WEIGHT + "\n");

            int _idColumn = cursor.getColumnIndex(PetEntry._ID);
            int nameColumn = cursor.getColumnIndex(PetEntry.PET_NAME);
            int breedColumn = cursor.getColumnIndex(PetEntry.BREED_NAME);
            int genderColumn = cursor.getColumnIndex(PetEntry.GENDER);
            int weightColumn =  cursor.getColumnIndex(PetEntry.WEIGHT);

            while (cursor.moveToNext()){
                int currentId = cursor.getInt(_idColumn);
                String currentName = cursor.getString(nameColumn);
                String breedName = cursor.getString(breedColumn);
                String gender = cursor.getString(genderColumn);
                String weight = cursor.getString(weightColumn);
                petList.append(("\n" + currentId + " - " + currentName + " - " + breedName + " - " + gender + " - " +weight));
            }
        }finally {
            cursor.close();
        }
    }

    private void insertPetData(){
        petsDbHelper = new PetsDbHelper(this);
        SQLiteDatabase db = this.petsDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PetEntry.PET_NAME,"Toto");
        contentValues.put(PetEntry.BREED_NAME,"terrier");
        contentValues.put(PetEntry.GENDER,PetEntry.GENDER_MALE);
        contentValues.put(PetEntry.WEIGHT,12);

        db.insert(PetEntry.TABLE_NAME,null,contentValues);
    }

}


