package com.example.android.fashionhome;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.LoaderManager;

import com.example.android.fashionhome.R;
import com.example.android.fashionhome.data.ClientContract;

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
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.GENDER_MALE;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry._ID;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.CONTENT_URI;

/**
 * Created by AGUELE OSEKUEMEN JOE on 10/7/2017.
 */

public class ClientDetails  extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_CLIENT_LOADER = 0;
    private TextView cName;
    private TextView cStyle;
    private TextView cAddress;
    private TextView cPhone;
    private TextView cPhone2;
    private TextView cGender;
    private TextView cEmail;


    // female measurement
    private TextView cfboss;
    private TextView cfWaist;
    private TextView cHip;
    private TextView cShoulder;
    private TextView cShortSl;
    private TextView cLongSl;
    private TextView cTop;
    private TextView cSkirtLength;
    private TextView cBlouseLength;

    // MALE measurement
    private TextView cKaftanLength;
    private TextView cMaleTopLength;
    private TextView cMNeck;
    private TextView cMaleShoulder;
    private TextView cMaleShortSleeveLength;
    private TextView cMaleLongSleeveLength;
    private TextView cMaleChest;
    private TextView cMaleBelly;
    private TextView cMaleTrouserLength;
    private TextView cMaleThigh;
    private TextView cMaleWaist;
    private TextView cMaleCalf;
    private TextView cMaleAnkle;

    //Todo Complete all the remaining parts for measurement
    // Todo Add the Billing part which will contain a textview to show the balance
    // Todo Remove the delete all from the menu function
    



    private Uri currentCilentUri;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        currentCilentUri = intent.getData();

        if (currentCilentUri == null) {
            // This is a new client, so change
            Toast.makeText(getApplicationContext(), "No pet data here", Toast.LENGTH_LONG).show();

        } else {

            // launching intent for EditorActivity
            ImageButton sImageButton = (ImageButton)findViewById(R.id.img);
            sImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // the setData makes the EditorActivity to pick the data of the current pet so as to enable editing
                    Intent mIntent = new Intent(getApplicationContext(),EditorActivity.class).setData(currentCilentUri);
                    startActivity(mIntent);
                }
            });
            //Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_CLIENT_LOADER, null, this);
        }

        cName = (TextView)findViewById(R.id.c_name);
        cAddress = (TextView)findViewById(R.id.c_address);
        cStyle = (TextView)findViewById(R.id.c_style);
        cPhone = (TextView)findViewById(R.id.c_phone);
        cPhone2 = (TextView)findViewById(R.id.c_phone2);
        cEmail = (TextView)findViewById(R.id.c_email);
        cGender = (TextView)findViewById(R.id.c_gender);

        // Female measurement
        cfboss = (TextView) findViewById(R.id.c_fboss);
        cfWaist = (TextView) findViewById(R.id.c_fwaist);
        cHip = (TextView)findViewById(R.id.c_fhip);
        cShoulder = (TextView)findViewById(R.id.c_fshoulder);
        cShortSl = (TextView)findViewById(R.id.c_fshortsleevelength);
        cLongSl =(TextView)findViewById(R.id.c_flongsleevelength);
        cTop = (TextView)findViewById(R.id.c_ftop);
        cSkirtLength = (TextView)findViewById(R.id.c_fskirtlength);
        cBlouseLength=(TextView)findViewById(R.id.c_fblouselength);


        // Male measurement
        cKaftanLength = (TextView)findViewById(R.id.c_kaftanlength);
        cMaleTopLength = (TextView)findViewById(R.id.c_toplength);
        cMNeck = (TextView)findViewById(R.id.c_mNeck);
        cMaleShoulder = (TextView)findViewById(R.id.c_mShoulder);
        cMaleShortSleeveLength = (TextView)findViewById(R.id.c_mShortSleeve);
        cMaleLongSleeveLength = (TextView)findViewById(R.id.c_Mlongsleevelength);
        cMaleChest =(TextView)findViewById(R.id.c_mChest);
        cMaleBelly = (TextView)findViewById(R.id.c_mbelly);
        cMaleTrouserLength  = (TextView)findViewById(R.id.c_mTrouserLength);
        cMaleThigh = (TextView)findViewById(R.id.c_Mthigh);
        cMaleWaist = (TextView)findViewById(R.id.c_mWaist);
        cMaleCalf = (TextView)findViewById(R.id.c_MCalf);
        cMaleAnkle = (TextView)findViewById(R.id.c_MAnkle);






    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                ClientContract.ClientEntry._ID,
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
                currentCilentUri,    // Provider content URI to query
                projection,     // Columns to include in the resulting Cursor
                null,           // No selection clause
                null,           // No selection arguments
                null);           // Default sort order
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            //Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_NAME);
            int addressColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_ADDRESS);
            int genderColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_GENDER);
            int styleColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_STYLE);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_NUMBER);
            int phone2ColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_NUMBER2);

            // Female measurement
            int fbossColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_BOSS);
            int fwaistColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_WAIST);
            int fhipColumnIndex = cursor.getColumnIndex(COLUMN_HIP);
            int fshoulderColumnIndex = cursor.getColumnIndex(COLUMN_FEMALE_SHOULDER);
            int fShortSleeveColumnIndex = cursor.getColumnIndex(COLUMN_FEMALESHORTSLEEVE);
            int fLongSleeveColumnIndex = cursor.getColumnIndex(COLUMN_FEMALELONGSLEEVE);
            int topColumnIndex = cursor.getColumnIndex(COLUMN_TOP);
            int skirtColumnIndex = cursor.getColumnIndex(COLUMN_SKIRT_LENGTH);
            int blouseColumnIndex = cursor.getColumnIndex(COLUMN_BLOUSE_LENGTH);


            // Male measurement
            int maleKaftanLengthColumnIndex = cursor.getColumnIndex(COLUMN_CAFTAN_LENGTH);
            int maleTopLengthColumnIndex = cursor.getColumnIndex(COLUMN_TOP_LENGTH);
            int maleNeckColumnIndex = cursor.getColumnIndex(COLUMN_NECK);
            int maleShoulderColumnIndex = cursor.getColumnIndex(COLUMN_MALE_SHOULDER);
            int maleShortSleeveColumnIndex = cursor.getColumnIndex(COLUMN_MALE_SS);
            int maleLSCI = cursor.getColumnIndex(COLUMN_MALE_LS);
            int maleChestCI = cursor.getColumnIndex(COLUMN_CHEST);
            int maleBellyCI  = cursor.getColumnIndex(COLUMN_BELLY);
            int trouserLengthCI = cursor.getColumnIndex(COLUMN_TROUSER_LENGTH);
            int thighCI = cursor.getColumnIndex(COLUMN_THIGH);
            int waistCI = cursor.getColumnIndex(COLUMN_WAIST);
            int calfCI = cursor.getColumnIndex(COLUMN_CALF);
            int ankleCI = cursor.getColumnIndex(COLUMN_ANKLE);



            // eXTRACT OUT THE VALUE FROM THE cURSOR for the given column index
            String name = cursor.getString(nameColumnIndex);
            String address = cursor.getString(addressColumnIndex);
            String style = cursor.getString(styleColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
            String phone2 = cursor.getString(phone2ColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);  // GENDER

            // FEMALE MEASUSREMENT
            String boss = cursor.getString(fbossColumnIndex);
            String waist = cursor.getString(fwaistColumnIndex);
            String hip = cursor.getString(fhipColumnIndex);
            String femaleShoulder = cursor.getString(fshoulderColumnIndex);
            String femaleShortSleeve = cursor.getString(fShortSleeveColumnIndex);
            String femaleLongSleeve = cursor.getString(fLongSleeveColumnIndex);
            String femaleTop = cursor.getString(topColumnIndex);
            String skirtLength = cursor.getString(skirtColumnIndex);
            String blouse = cursor.getString(blouseColumnIndex);


            // mALE MEASUREMENT
            String maleKaftanLength = cursor.getString(maleKaftanLengthColumnIndex);
            String maleTopLength = cursor.getString(maleTopLengthColumnIndex);
            String maleNeck = cursor.getString(maleNeckColumnIndex);
            String maleShoulder = cursor.getString(maleShoulderColumnIndex);
            String maleShortSleeveLength = cursor.getString(maleShortSleeveColumnIndex);
            String maleLongSleeveLength = cursor.getString(maleLSCI);
            String maleChestLength = cursor.getString(maleChestCI);
            String mBellyLength = cursor.getString(maleBellyCI);
            String trouserLength = cursor.getString(trouserLengthCI);
            String maleThighLenght = cursor.getString(thighCI);
            String maleAnkleLength = cursor.getString(ankleCI);
            String maleCalfLenght = cursor.getString(calfCI);
            String malewaistLength = cursor.getString(waistCI);

            //Update the views on the screen with the values from the database
            cName.setText(name);
            cAddress.setText(address);
            cStyle.setText(style);
            cPhone.setText(phone);
            cPhone2.setText(phone2);
            cEmail.setText(email);

            switch (gender){
                case GENDER_MALE:
                    cGender.setText("Male");
                    cKaftanLength.setText(maleKaftanLength);
                    cMNeck.setText(maleNeck);
                    cMaleTopLength.setText(maleTopLength);
                    cMaleShoulder.setText(maleShoulder);
                    cMaleShortSleeveLength.setText(maleShortSleeveLength);
                    cMaleLongSleeveLength.setText(maleLongSleeveLength);
                    cMaleChest.setText(maleChestLength);
                    cMaleBelly.setText(mBellyLength);
                    cMaleTrouserLength.setText(trouserLength);
                    cMaleThigh.setText(maleThighLenght);
                    cMaleWaist.setText(malewaistLength);
                    cMaleCalf.setText(maleCalfLenght);
                    cMaleAnkle.setText(maleAnkleLength);
                    break;


                case GENDER_FEMALE:
                    cGender.setText("Female");
                    cfboss.setText(boss);
                    cfWaist.setText(waist);
                    cHip.setText(hip);
                    cShoulder.setText(femaleShoulder);
                    cShortSl.setText(femaleShortSleeve);
                    cLongSl.setText(femaleLongSleeve);
                    cTop.setText(femaleTop);
                    cSkirtLength.setText(skirtLength);
                    cBlouseLength.setText(blouse);

                    break;
                default:
                    cGender.setText("Unknown");
                    break;
            }

        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        cName.setText("");
        cAddress.setText("");
        cEmail.setText("");
        cGender.setText("");
        cPhone.setText("");
        cPhone2.setText("");
        cStyle.setText("");

        //female measurement
        cfboss.setText("");
        cfWaist.setText("");
        cHip.setText("");
        cShoulder.setText("");
        cShortSl.setText("");
        cLongSl.setText("");
        cTop.setText("");
        cSkirtLength.setText("");
        cBlouseLength.setText("");


        // Male measurement
        cKaftanLength.setText("");
        cMNeck.setText("");
        cMaleTopLength.setText("");
        cMaleShoulder.setText("");
        cMaleShortSleeveLength.setText("");
        cMaleLongSleeveLength.setText("");
        cMaleChest.setText("");
        cMaleBelly.setText("");
        cMaleTrouserLength.setText("");
        cMaleThigh.setText("");
        cMaleWaist.setText("");
        cMaleCalf.setText("");
        cMaleAnkle.setText("");


    }




}

