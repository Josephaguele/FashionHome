package com.example.android.fashionhome;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.android.fashionhome.data.ClientContract;

import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_STYLE;

/**
 * Created by AGUELE OSEKUEMEN JOE on 10/7/2017.
 */

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // implement Up Navigation with caret in front of App icon in the Action Bar
        // getActionBar().setDisplayedHomeAsEnabled(true);
        Intent intent = getIntent();
        checkIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        // update the activity launch intent
        setIntent(newIntent);

        // handle it
        checkIntent(newIntent);
    }

    private void checkIntent(Intent intent){
        String query = "";
        String intentAction = intent.getAction();
        if(Intent.ACTION_SEARCH.equals(intentAction)){
            query = intent.getStringExtra(SearchManager.QUERY);
        } else if (Intent.ACTION_VIEW.equals(intentAction)){

            Uri details = intent.getData();
            Intent detailsIntent = new Intent(Intent.ACTION_VIEW, details);
            startActivity(detailsIntent);
        }
        fillList(query);
    }

    private void fillList(String query){
        String wildcardQuery = "%" + query + "%";
        Cursor cursor = getContentResolver().query(
                ClientContract.ClientEntry.CONTENT_URI,
                null,
                ClientContract.ClientEntry.COLUMN_CLIENT_NAME + " LIKE ?  OR " + COLUMN_CLIENT_STYLE + " LIKE ?",
                new String[]{wildcardQuery, wildcardQuery},
                null);

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "NO SEARCH RESULT " , Toast.LENGTH_LONG).show();
            int timeout = 3000; // make the activity visible for 4 seconds

            Intent intent = new Intent(SearchableActivity.this, CatalogActivity.class);
            startActivity(intent);
        }

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{ClientContract.ClientEntry.COLUMN_CLIENT_NAME,COLUMN_CLIENT_STYLE},
                new int[]{android.R.id.text1,android.R.id.text2},
                0);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(SearchableActivity.this, EditorActivity.class);

        Uri details = Uri.withAppendedPath(ClientContract.ClientEntry.CONTENT_URI, "" + id);
        intent.setData(details);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            // This is called when the Home (up) button is pressed
            // in the action bar
            Intent parentActivityIntent = new Intent(this,CatalogActivity.class);
            parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
