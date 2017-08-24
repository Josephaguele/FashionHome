package com.example.android.fashionhome.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.fashionhome.data.ClientContract.ClientEntry;

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/14/2017.
 */


/**
 * Database helper for Fashion app. Manages database creation and version management.
 */
public class ClientDbHelper extends SQLiteOpenHelper {

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Name of the database file
     */
    public static final String DATABASE_NAME = "fashion.db";

    /**
     * Constructs a new instance of {@link ClientDbHelper}.
     *
     * @param context of the app
     */
    public ClientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create a String that contains the SQL statement to create the clients table
        String SQL_CREATE_CLIENTS_TABLE = "CREATE TABLE " + ClientEntry.TABLE_NAME + "("
                + ClientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ClientEntry.COLUMN_CLIENT_NAME + " TEXT NOT NULL, "
                + ClientEntry.COLUMN_CLIENT_ADDRESS + " TEXT, "
                + ClientEntry.COLUMN_CLIENT_STYLE + " TEXT, "
                + ClientEntry.COLUMN_CLIENT_GENDER + " INTEGER NOT NULL, "
                + ClientEntry.COLUMN_CLIENT_NUMBER2 + " TEXT ,"
                + ClientEntry.COLUMN_EMAIL + " TEXT ,"
                + ClientEntry.COLUMN_CLIENT_BOSS + " TEXT ," // FROM HERE FEMALE MEASUREMENT BEGINS
                + ClientEntry.COLUMN_CLIENT_WAIST + " TEXT ,"
                + ClientEntry.COLUMN_HIP + " TEXT ,"
                + ClientEntry.COLUMN_FEMALE_SHOULDER + " TEXT ,"
                + ClientEntry.COLUMN_FEMALESHORTSLEEVE + " TEXT ,"
                + ClientEntry.COLUMN_FEMALELONGSLEEVE + " TEXT ,"
                + ClientEntry.COLUMN_TOP + " TEXT ,"
                + ClientEntry.COLUMN_SKIRT_LENGTH + " TEXT ,"
                + ClientEntry.COLUMN_BLOUSE_LENGTH + " TEXT ,"
                + ClientEntry.COLUMN_CAFTAN_LENGTH + " TEXT ," // FROM HERE MALE MEASUREMENT BEGINS
                + ClientEntry.COLUMN_TOP_LENGTH + " TEXT ,"
                + ClientEntry.COLUMN_NECK + " TEXT ,"
                + ClientEntry.COLUMN_MALE_SHOULDER + " TEXT ,"
                + ClientEntry.COLUMN_MALE_SS + " TEXT ,"
                + ClientEntry.COLUMN_MALE_LS + " TEXT ,"
                + ClientEntry.COLUMN_CHEST + " TEXT ,"
                + ClientEntry.COLUMN_BELLY + " TEXT ,"
                + ClientEntry.COLUMN_TROUSER_LENGTH + " TEXT ,"
                + ClientEntry.COLUMN_THIGH + " TEXT ,"
                + ClientEntry.COLUMN_WAIST + " TEXT ,"
                + ClientEntry.COLUMN_CALF + " TEXT ,"
                + ClientEntry.COLUMN_ANKLE + " TEXT ,"
                + ClientEntry.COLUMN_DATE + " TEXT ,"
                + ClientEntry.AMOUNT + " TEXT ,"
                + ClientEntry.ADVANCE + " TEXT ,"
                + ClientEntry.COLUMN_CLIENT_NUMBER + " TEXT NOT NULL );";

        // Execute the SQL statement
        database.execSQL(SQL_CREATE_CLIENTS_TABLE);

    }

    // This is called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.

    }
}
