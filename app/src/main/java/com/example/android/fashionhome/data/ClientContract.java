package com.example.android.fashionhome.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/13/2017.
 */

public final class ClientContract {


    /**
     * The "Content Authority" is a name for the entire content provider similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.fashionhome";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /*Possible path (appended to base content URI for possible URI's)
    * For instance, content://com.android.fashionhome/client/ is a valid path for
    * looking at client data. content://com.android.fashionhome/staff/ will fail.
    * as the ContentProvider hasn't been given any information on what to do with staff
    * */
    public static final String PATH_CLIENTS = "clients";

    // To prevent someone from accidentally instantiating the contract class.
    // give it an empty constructor

    public ClientContract() {

    }

    public static final class ClientEntry implements BaseColumns {

        // The content URI TO access the client data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CLIENTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/" + CONTENT_AUTHORITY +"/" + PATH_CLIENTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_CLIENTS;


        // Name of database table for clients
        public static final String TABLE_NAME = "clients";
        // Unique ID number for the client ( only for use in the database table)
        // Type: INTEGER
        public static final String _ID = BaseColumns._ID;
        // name of the client
        // Type: TEXT
        public static final String COLUMN_CLIENT_NAME = "name";

        // address of the client
        // TYPE: TEXT
        public static final String COLUMN_CLIENT_ADDRESS = "address";

        // Style of the client
        // Type: TEXT
        public static final String COLUMN_CLIENT_STYLE = "style";
        // Gender of the client
        // The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE}
        // or [@link #GENDER_FEMALE}
        // Type: INTEGER
        public static final String COLUMN_CLIENT_GENDER = "gender";
        // Phone number of the client
        // Type: INTEGER
        public static final String COLUMN_CLIENT_NUMBER = "number";

        public static final String COLUMN_CLIENT_NUMBER2 = "phone";

        // email address of client
        public static final String COLUMN_EMAIL = "email";
        // Possible values of the gender of the client.
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final String AMOUNT ="amount";
        public static final String ADVANCE = "advance";

        public static final String COLUMN_DATE = "date";

        // Female measurement data

        public static final String COLUMN_CLIENT_BOSS = "boss";
        public static final String COLUMN_CLIENT_WAIST = "femalewaist";
        public static final String COLUMN_HIP = "hip";
        public static final String COLUMN_FEMALE_SHOULDER = "femaleshoulder";
        public static final String COLUMN_FEMALESHORTSLEEVE = "femaleshortsleeve";
        public static final String COLUMN_FEMALELONGSLEEVE = "femalelongsleeve";
        public static final String COLUMN_TOP = "top";
        public static final String COLUMN_SKIRT_LENGTH = "skirtlength";
        public static final String COLUMN_BLOUSE_LENGTH = "blouselength";


        // Male measurement data
        public static final String COLUMN_CAFTAN_LENGTH = "Caftanlength";
        public static final String COLUMN_TOP_LENGTH = "toplength";
        public static final String COLUMN_NECK = "Neck";
        public static final String COLUMN_MALE_SHOULDER = "maleshoulder";
        public static final String COLUMN_MALE_SS ="maleshortsleeve";
        public static final String COLUMN_MALE_LS = "malelongsleeve";
        public static final String COLUMN_CHEST = "chest";
        public static final String COLUMN_BELLY = "belly";
        public static final String COLUMN_TROUSER_LENGTH = "trouserlength";
        public static final String COLUMN_THIGH = "thigh";
        public static final String COLUMN_WAIST = "malewaist";
        public static final String COLUMN_CALF = "calf";
        public static final String COLUMN_ANKLE = "ankle";



        //Returns whether or not the given gender is {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
        // {@link #GENDER_FEMALE}
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;

        }
    }

}

