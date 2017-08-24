package com.example.android.fashionhome;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.fashionhome.data.ClientContract.ClientEntry;
import com.example.android.fashionhome.data.ClientCursorAdapter;
import com.example.android.fashionhome.data.ClientDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Environment.getExternalStorageDirectory;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.ADVANCE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.AMOUNT;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_ANKLE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BELLY;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BLOUSE_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CAFTAN_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CALF;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CHEST;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_ADDRESS;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_BOSS;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_GENDER;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NAME;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NUMBER;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NUMBER2;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_STYLE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_WAIST;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_DATE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_EMAIL;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALELONGSLEEVE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALESHORTSLEEVE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALE_SHOULDER;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_HIP;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_LS;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_SHOULDER;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_SS;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_NECK;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_SKIRT_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_THIGH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TOP;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TOP_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TROUSER_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_WAIST;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.GENDER_FEMALE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry._ID;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.CONTENT_URI;
import static com.example.android.fashionhome.data.ClientDbHelper.DATABASE_NAME;

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/13/2017.
 */

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Identifier for the pet data loader
    private static final int PET_LOADER = 0;

    // Adapter for the ListView
    ClientCursorAdapter mCursorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_catalog);

        // Importing and exporting of data

        // creating a new folder for the database to be backed up // TODO: 7/8/2017
       File direct = new File(getExternalStorageDirectory() + "/Fashion Home");
        if(!direct.exists()){
            if(direct.mkdir()){
                // directory is created
            }
        }



//////////////////////////////

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // FInd the listView which will be populated with the client data
        ListView clientListView = (ListView) findViewById(R.id.list);

        //Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        clientListView.setEmptyView(emptyView);

        //Set up an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes ) so pass in null for the Cursor.
        mCursorAdapter = new ClientCursorAdapter(this, null);

        clientListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        clientListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" ( passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentClientUri =  ContentUris.withAppendedId(ClientEntry.CONTENT_URI, id);

                //Set the URI on the data field of the intent
                intent.setData(currentClientUri);

                //Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        // Kick off the Loader
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    // Helper method to insert hardcoded pet data into the database. For debugging purposes only
    private void insertClient() {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, "Ajoke Adeyemi");
        values.put(COLUMN_CLIENT_ADDRESS, "34, Lade lane, G.R.A Ikeja, Lagos");
        values.put(COLUMN_CLIENT_STYLE, "Caftan");
        values.put(COLUMN_CLIENT_GENDER, GENDER_FEMALE);
        values.put(COLUMN_CLIENT_NUMBER, "08063496062");
        values.put(COLUMN_CLIENT_NUMBER2, "01-2800100");
        values.put(COLUMN_EMAIL, "seki.live@gmail.com");
        values.put(COLUMN_CLIENT_BOSS, "44");
        values.put(COLUMN_CLIENT_WAIST, "23");
        values.put(COLUMN_HIP, "24");
        values.put(COLUMN_FEMALE_SHOULDER, "16");
        values.put(COLUMN_FEMALESHORTSLEEVE, "8.5");
        values.put(COLUMN_FEMALELONGSLEEVE, "14.2");
        values.put(COLUMN_TOP, "8.6");
        values.put(COLUMN_SKIRT_LENGTH, "9");
        values.put(COLUMN_BLOUSE_LENGTH, "12");
        values.put(AMOUNT, 150000);
        values.put(ADVANCE, 60000);


        // insert a new row for Toto into the provider using the ContentResolver
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(ClientEntry.CONTENT_URI, values);
    }

    // Helper method to delete all pets in the database
    private void deleteAllPets(){
        int rowsDeleted = getContentResolver().delete(ClientEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }
 /*   // retrieve data and filter
    public Cursor retrieve (String searchTerm){

        ClientDbHelper mDbHelper = null;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String[] columns = {ClientEntry._ID, ClientEntry.COLUMN_CLIENT_NAME};
        Cursor c = null;

        if( searchTerm != null && searchTerm.length()>0){
            String sql = "SELECT * FROM "+ ClientEntry.TABLE_NAME+
                    " WHERE "+ClientEntry.COLUMN_CLIENT_NAME+
                    " LIKE '%"+searchTerm+"%'";
            c= database.rawQuery(sql,null);
            return c;
        }

        c = database.query(ClientEntry.TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }
*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertClient();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:

                return true;

            case R.id.action_export:
                exportDB();

                return true;

            case R.id.action_import:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


 /*   private void backUpDatabase(){
        //Open your local db as the input stream

        //Context context ;
        String inFileName = getFilesDir().getPath()+DATABASE_NAME;

        File dbFile = new File(inFileName);
        try{
            FileInputStream fis = new FileInputStream(dbFile);
            String outFileName = Environment.getExternalStorageDirectory()+"/"+ ClientDbHelper.DATABASE_NAME;
            //Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            //transfer bytes from teh input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }
            //Close the streams
            output.flush();
            output.close();
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }


    }*/


    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                _ID,
                COLUMN_CLIENT_NAME,
                COLUMN_CLIENT_ADDRESS,
                COLUMN_CLIENT_STYLE,
                COLUMN_CLIENT_GENDER,
                COLUMN_CLIENT_NUMBER,
                COLUMN_CLIENT_NUMBER2,
                COLUMN_EMAIL,
                COLUMN_CLIENT_BOSS,
                COLUMN_CLIENT_WAIST,
                COLUMN_HIP,
                COLUMN_FEMALE_SHOULDER,
                COLUMN_FEMALESHORTSLEEVE,
                COLUMN_FEMALELONGSLEEVE,
                COLUMN_TOP,
                COLUMN_BLOUSE_LENGTH,
                COLUMN_SKIRT_LENGTH,
                COLUMN_CAFTAN_LENGTH,
                COLUMN_TOP_LENGTH,
                COLUMN_NECK,
                COLUMN_MALE_SHOULDER,
                COLUMN_MALE_SS,
                COLUMN_MALE_LS,
                COLUMN_CHEST,
                COLUMN_BELLY,
                COLUMN_TROUSER_LENGTH,
                COLUMN_THIGH,
                COLUMN_WAIST,
                COLUMN_CALF,
                COLUMN_ANKLE,
                COLUMN_DATE,
                AMOUNT,
                ADVANCE
        };


        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                CONTENT_URI,    // Provider content URI to query
                projection,     // Columns to include in the resulting Cursor
                null,           // No selection clause
                null,           // No selection arguments
                null);           // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader,Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }




   private void exportDB(){


            File sd = getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            FileChannel source = null;
            FileChannel destination = null;

                String currentDBPath = getFilesDir().getPath()+DATABASE_NAME;
                String backupDBPath = DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                /*if(currentDB.exists()){
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileInputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getBaseContext(), "DB Exported!", Toast.LENGTH_LONG).show();
            }*/

       try{
           source = new FileInputStream(currentDB).getChannel();
           destination = new FileOutputStream(backupDB).getChannel();
           destination.transferFrom(source, 0, source.size());
           source.close();
           destination.close();
           Toast.makeText(this, "DB Exported", Toast.LENGTH_LONG).show();
       } catch (IOException e){
           e.printStackTrace();
       }



    }


}

