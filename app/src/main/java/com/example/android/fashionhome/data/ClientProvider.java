package com.example.android.fashionhome.data;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.fashionhome.data.ClientContract.ClientEntry;

import java.util.HashMap;

import static android.app.SearchManager.SUGGEST_MIME_TYPE;
import static android.app.SearchManager.SUGGEST_URI_PATH_QUERY;
import static android.app.SearchManager.SUGGEST_URI_PATH_SHORTCUT;
import static com.example.android.fashionhome.data.ClientContract.CONTENT_AUTHORITY;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.ADVANCE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.AMOUNT;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_ANKLE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BELLY;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BLOUSE_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CAFTAN_LENGTH;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CALF;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CHEST;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_BOSS;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NAME;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_WAIST;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_DATE;
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

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/13/2017.
 * Content Provider for the FASHION APP
 */

public class ClientProvider extends ContentProvider {

    //Tag for the log messages
    public static final String LOG_TAG = ClientProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the clients table
     */
    private static final int CLIENTS = 100;

    /**
     * URI matcher code for the content URI for a single client in the clients table
     */
    private static final int CLIENT_ID = 101;

    private static final int SEARCH = 3;

    public static final String KEY_SEARCH_COLUMN =COLUMN_CLIENT_NAME;
    private static final HashMap<String, String> SEARCH_SUGGEST_PROJECTION_MAP;
    static {
        SEARCH_SUGGEST_PROJECTION_MAP = new HashMap<String, String>();
        SEARCH_SUGGEST_PROJECTION_MAP.put(ClientEntry._ID, ClientEntry._ID);
        SEARCH_SUGGEST_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1, KEY_SEARCH_COLUMN + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        SEARCH_SUGGEST_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_2, ClientEntry.COLUMN_CLIENT_STYLE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        SEARCH_SUGGEST_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, ClientEntry._ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
    }

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.fashionhome/clients" will map to the
        // integer code {@link #CLIENTS}. This URI is used to provide access to MULTIPLE rows
        // of the clients table.
        sUriMatcher.addURI(CONTENT_AUTHORITY, ClientContract.PATH_CLIENTS, CLIENTS);

        // The content URI of the form "content://com.example.android.fashionhome/clients/#" will map to the
        // integer code {@link #CLIENT_ID}. This URI is used to provide access to ONE single row
        // of the clients table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.fashionhome/clients/3" matches, but
        // "content://com.example.android.fashionhome/clients" (without a number at the end) doesn't match.
        sUriMatcher.addURI(CONTENT_AUTHORITY, ClientContract.PATH_CLIENTS + "/#", CLIENT_ID);
    }


    public static final Uri SEARCH_SUGGEST_URI = Uri.parse("content://" + ClientContract.CONTENT_AUTHORITY + "/" + CLIENT_ID);
    public ClientProvider(){
        // For searching
        sUriMatcher.addURI(CONTENT_AUTHORITY,SUGGEST_URI_PATH_QUERY,SEARCH);
        sUriMatcher.addURI(CONTENT_AUTHORITY,SUGGEST_URI_PATH_QUERY +"/*", SEARCH);
       // sUriMatcher.addURI(CONTENT_AUTHORITY,SUGGEST_URI_PATH_SHORTCUT,SEARCH);
       // sUriMatcher.addURI(CONTENT_AUTHORITY,SUGGEST_URI_PATH_SHORTCUT+"/*", SEARCH);
    }

    //Database helper object
    private ClientDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ClientDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor =null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ClientEntry.TABLE_NAME);

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case SEARCH:
                selectionArgs = new String[]{ "%" + selectionArgs[0] + "%", "%" + selectionArgs[0] +"%"};
                queryBuilder.setProjectionMap(SEARCH_SUGGEST_PROJECTION_MAP);
                cursor = queryBuilder.query(database,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case CLIENTS:
                // For the CLIENTS code, query the clients table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the clients table.
                cursor = database.query(ClientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
            case CLIENT_ID:
                // For the CLIENTS_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.fashionhome/clients/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ClientEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the clients table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ClientEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CLIENTS:
                return insertClient(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a client into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertClient(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(COLUMN_CLIENT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Client requires a name");
        }

        // Check that the address is not null
        String address = values.getAsString(ClientEntry.COLUMN_CLIENT_ADDRESS);
        if (address == null) {
            throw new IllegalArgumentException("Client requires an address");
        }
        // Check that the gender is valid
        Integer gender = values.getAsInteger(ClientEntry.COLUMN_CLIENT_GENDER);
        if (gender == null || !ClientEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Client requires valid gender");
        }

        // If the number is provided, check that it's greater than or equal to 0 kg
        String number = values.getAsString(ClientEntry.COLUMN_CLIENT_NUMBER);
        if (number == null) {
            throw new IllegalArgumentException("Client requires valid phone number");
        }

        String number2 = values.getAsString(ClientEntry.COLUMN_CLIENT_NUMBER2);
        String email = values.getAsString(ClientEntry.COLUMN_EMAIL);

        // fEMALE Measurement
        String boss = values.getAsString(COLUMN_CLIENT_BOSS);
        String waist = values.getAsString(COLUMN_CLIENT_WAIST);
        String hip = values.getAsString(COLUMN_HIP);
        String shoulder = values.getAsString(COLUMN_FEMALE_SHOULDER);
        String fShortSleeve = values.getAsString(COLUMN_FEMALESHORTSLEEVE);
        String fLongSleeve = values.getAsString(COLUMN_FEMALELONGSLEEVE);
        String top = values.getAsString(COLUMN_TOP);
        String skirtLength = values.getAsString(COLUMN_SKIRT_LENGTH);
        String blouseLength = values.getAsString(COLUMN_BLOUSE_LENGTH);

        // MALE Measurement
        String kaftanLength = values.getAsString(COLUMN_CAFTAN_LENGTH);
        String topLength = values.getAsString(COLUMN_TOP_LENGTH);
        String neck = values.getAsString(COLUMN_NECK);
        String maleShoulder = values.getAsString(COLUMN_MALE_SHOULDER);
        String shortSleeveLength = values.getAsString(COLUMN_MALE_SS);
        String longSleeveLength = values.getAsString(COLUMN_MALE_LS);
        String chest = values.getAsString(COLUMN_CHEST);
        String belly = values.getAsString(COLUMN_BELLY);
        String trouserLength = values.getAsString(COLUMN_TROUSER_LENGTH);
        String thigh = values.getAsString(COLUMN_THIGH);
        String maleWaist = values.getAsString(COLUMN_WAIST);
        String calf = values.getAsString(COLUMN_CALF);
        String ankle = values.getAsString(COLUMN_ANKLE);
        String date = values.getAsString(COLUMN_DATE);

        String amount = values.getAsString(AMOUNT);
        String advance = values.getAsString(ADVANCE);

        // No need to check the style, any value is valid (including null).

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new client with the given values
        long id = database.insert(ClientEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the client content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CLIENTS:
                return updateClient(uri, contentValues, selection, selectionArgs);
            case CLIENT_ID:
                // For the CLIENTS_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ClientEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateClient(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    /**
     * Update Clients in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more clients).
     * Return the number of rows that were successfully updated.
     */
    private int updateClient(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ClientEntry#COLUMN_CLIENT_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(COLUMN_CLIENT_NAME)) {
            String name = values.getAsString(COLUMN_CLIENT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Client requires a name");
            }
        }

        // check that the address is not  null
        if (values.containsKey(ClientEntry.COLUMN_CLIENT_ADDRESS)) {
            String address = values.getAsString(ClientEntry.COLUMN_CLIENT_ADDRESS);
            if (address == null) {
                throw new IllegalArgumentException("Address is important");
            }
        }


        // If the {@link ClientEntry#COLUMN_CLIENT_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(ClientEntry.COLUMN_CLIENT_GENDER)) {
            Integer gender = values.getAsInteger(ClientEntry.COLUMN_CLIENT_GENDER);
            if (gender == null || !ClientEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Client requires valid gender");
            }
        }

        if (values.containsKey(ClientEntry.COLUMN_CLIENT_STYLE)) {
            String name = values.getAsString(ClientEntry.COLUMN_CLIENT_STYLE);
            if (name == null) {
                throw new IllegalArgumentException("Client requires a style");
            }
        }
        // If the {@link ClientEntry#COLUMN_CLIENT_NUMBER} key is present,
        // check that the number value is valid.
        if (values.containsKey(ClientEntry.COLUMN_CLIENT_NUMBER)) {
            // Check that the number is greater than or equal to 0 kg
            String number = values.getAsString(ClientEntry.COLUMN_CLIENT_NUMBER);
            if (number == null) {
                throw new IllegalArgumentException("Client requires valid phone number");
            }
        }

        if (values.containsKey(ClientEntry.COLUMN_CLIENT_NUMBER2)) {
            String number2 = values.getAsString(ClientEntry.COLUMN_CLIENT_NUMBER2);
        }

        if (values.containsKey(ClientEntry.COLUMN_EMAIL)) {
            String email = values.getAsString(ClientEntry.COLUMN_EMAIL);
        }

        if (values.containsKey(COLUMN_CLIENT_BOSS)) {
            String boss = values.getAsString(COLUMN_CLIENT_BOSS);
        }

        if (values.containsKey(COLUMN_CLIENT_WAIST)) {
            String waist = values.getAsString(COLUMN_CLIENT_WAIST);
        }

        if (values.containsKey(COLUMN_HIP)) {
            String hip = values.getAsString(COLUMN_HIP);
        }

        if (values.containsKey(COLUMN_FEMALE_SHOULDER)) {
            String shoulder = values.getAsString(COLUMN_FEMALE_SHOULDER);
        }

        if (values.containsKey(COLUMN_FEMALESHORTSLEEVE)) {
            String fShortSleeve = values.getAsString(COLUMN_FEMALESHORTSLEEVE);
        }

        if (values.containsKey(COLUMN_FEMALELONGSLEEVE)) {
            String fLongSleeve = values.getAsString(COLUMN_FEMALELONGSLEEVE);
        }

        if (values.containsKey(COLUMN_TOP)) {
            String top = values.getAsString(COLUMN_TOP);
        }

        if (values.containsKey(COLUMN_SKIRT_LENGTH)) {
            String skirtLength = values.getAsString(COLUMN_SKIRT_LENGTH);
        }

        if (values.containsKey(COLUMN_BLOUSE_LENGTH)) {
            String blouseLength = values.getAsString(COLUMN_BLOUSE_LENGTH);
        }

        if (values.containsKey(COLUMN_CAFTAN_LENGTH)) {
            String kaftanLength = values.getAsString(COLUMN_CAFTAN_LENGTH);
        }

        if (values.containsKey(COLUMN_TOP_LENGTH)) {
            String top_length = values.getAsString(COLUMN_TOP_LENGTH);
        }

        if (values.containsKey(COLUMN_NECK)) {
            String neck = values.getAsString(COLUMN_NECK);
        }

        if (values.containsKey(COLUMN_MALE_SHOULDER)) {
            String shoulder = values.getAsString(COLUMN_MALE_SHOULDER);
        }

        if (values.containsKey(COLUMN_MALE_SS)) {
            String maleShortSleeve = values.getAsString(COLUMN_MALE_SS);
        }

        if (values.containsKey(COLUMN_MALE_LS)) {
            String maleLongSleeve = values.getAsString(COLUMN_MALE_LS);
        }

        if (values.containsKey(COLUMN_CHEST)) {
            String chest = values.getAsString(COLUMN_CHEST);
        }

        if (values.containsKey(COLUMN_BELLY)) {
            String belly = values.getAsString(COLUMN_BELLY);
        }

        if (values.containsKey(COLUMN_TROUSER_LENGTH)) {
            String trouserLength = values.getAsString(COLUMN_TROUSER_LENGTH);
        }

        if (values.containsKey(COLUMN_THIGH)) {
            String thigh = values.getAsString(COLUMN_THIGH);
        }

        if (values.containsKey(COLUMN_WAIST)) {
            String waist = values.getAsString(COLUMN_WAIST);
        }

        if (values.containsKey(COLUMN_CALF)) {
            String calf = values.getAsString(COLUMN_CALF);
        }

        if (values.containsKey(COLUMN_ANKLE)) {
            String ankle = values.getAsString(COLUMN_ANKLE);
        }

        if (values.containsKey(COLUMN_THIGH)) {
            String thigh = values.getAsString(COLUMN_THIGH);
        }

        if (values.containsKey(COLUMN_DATE)) {
            String date = values.getAsString(COLUMN_DATE);
        }

        if (values.containsKey(AMOUNT)) {
            String amount = values.getAsString(AMOUNT);
        }

        if (values.containsKey(ADVANCE)) {
            String advance = values.getAsString(ADVANCE);

        }


        // No need to check the style, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(ClientEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CLIENTS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ClientEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CLIENT_ID:
                // Delete a single row given by the ID in the URI
                selection = ClientEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ClientEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CLIENTS:
                return ClientEntry.CONTENT_LIST_TYPE;
            case CLIENT_ID:
                return ClientEntry.CONTENT_ITEM_TYPE;
            case SEARCH:
                return null;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}
