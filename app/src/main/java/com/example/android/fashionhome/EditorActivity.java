package com.example.android.fashionhome;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.attr.button;
import static com.example.android.fashionhome.R.id.number1;
import static com.example.android.fashionhome.R.string.boss;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry;
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
import static com.example.android.fashionhome.data.ClientContract.ClientEntry.GENDER_UNKNOWN;
import static com.example.android.fashionhome.data.ClientContract.ClientEntry._ID;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    // Identifier for the client data loader
    private static final int EXISTING_PET_LOADER = 0;
    //
    private EditText mNameEditText;
    private EditText mAddressEditText;
    private EditText mNumberEditText;
    private EditText mNumber2EditText;
    private EditText mStyleEditText;
    private Spinner mGenderSpinner;
    private Uri mCurrentClientUri;
    private EditText mEmailAddress;
    private EditText mboss;
    private EditText mFemaleWaist;
    private EditText mHip;
    private EditText fShoulder;
    private EditText fShortSleeve;
    private EditText fLongSleeve;
    private EditText mfemaleTop;
    private EditText mSkirtLength;
    private EditText mBlouseLength;
    private EditText mKaftanLength;
    private EditText mMaleTop;
    private EditText mNeck;
    private EditText mMShoulder;
    private EditText mMaleShortSleeve;
    private EditText mMaleLongSleeve;
    private EditText mChest;
    private EditText mBelly;
    private EditText mTrouserLength;
    private EditText mThigh;
    private EditText mWaist;
    private EditText mCalf;
    private EditText mAnkle;
    private EditText mDate;
    private EditText mAmount;
    private EditText mAdvance;


    /**
     * Gender of the client. The possible values are in the ClientContract.java file:
     * {@link ClientEntry#GENDER_UNKNOWN}, {@Link ClientEntry#GENDER_MALE} or {@Link ClientEntry#GENDER_FEMALE}
     */
    private int mGender = GENDER_UNKNOWN;
    // Boolean flag that keeps track of whether the client has been edited (true) or not (false) */
    private boolean mClientHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying the view, and
     * we change the mClientHasChanged boolean to true
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mClientHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Capture our button from layout
        ImageButton button = (ImageButton) findViewById(R.id.dial1);
        ImageButton button2 = (ImageButton) findViewById(R.id.dial2);
        ImageButton button3 = (ImageButton) findViewById(R.id.mail1);
        Button button4 = (Button)findViewById(R.id.message);
        Button button5 = (Button)findViewById(R.id.msg);



        // first call button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number1TextField = (EditText) findViewById(number1);
                //Editable number1 = number1TextField.getText();
                String string = number1TextField.getText().toString().trim();
                String number1 = "tel:" + string;

                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(number1));
                    if (callIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(callIntent);
                    }
                } catch (ActivityNotFoundException activityException) {

                }
            }
        });

        // second call button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number2TextField = (EditText) findViewById(R.id.number2);
                String string = number2TextField.getText().toString().trim();
                String number2 = "tel:" + string;

                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData((Uri.parse(number2)));

                    if (callIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(callIntent);
                    }

                } catch (ActivityNotFoundException activityException) {

                }
            }
        });

        //button for email.
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mailTextField = (EditText) findViewById(R.id.edit_email);
                Editable mail = mailTextField.getText();

                EditText styleTextField = (EditText) findViewById(R.id.edit_style);
                Editable style = styleTextField.getText();

                EditText nameTextField = (EditText) findViewById(R.id.edit_name);
                Editable name = nameTextField.getText();


                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse(getString(R.string.mailto)));
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.your_cloth) + style);
                mailIntent.putExtra(Intent.EXTRA_TEXT, " Dear " + name + " Regarding your cloth: " + style + "style");
                // "We write to inform you that your dress is ready for pick up. You " +
                // "can come at your convenient time for pick up of your finished design."+
                // "You can also call us or reply this mail, if you would like home delivery of your dress"

                mailIntent.putExtra(Intent.EXTRA_TEXT, "Ajoke Couture\n 080555343422");

                if (mailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mailIntent);

                }

            }
        });

        // SMS MESSAGE INTENT
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText numberTextField = (EditText) findViewById(R.id.number1);
                String number = numberTextField.getText().toString();

                EditText styleTextField = (EditText) findViewById(R.id.edit_style);
                Editable style = styleTextField.getText();

                EditText nameTextField = (EditText) findViewById(R.id.edit_name);
                Editable name = nameTextField.getText();

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:" ));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", new String (number));
                smsIntent.putExtra(" sms_body ", " Dear " + name + ", your cloth is ready for collection.  Ajoke Couture");

                try{
                    startActivity(smsIntent);
                    finish();
                    Log.i("Finished sending SMS", "");
                } catch(android.content.ActivityNotFoundException ex){
                    Toast.makeText(EditorActivity.this, "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText numberTextField = (EditText) findViewById(R.id.number2);
                String number = numberTextField.getText().toString();

                EditText styleTextField = (EditText) findViewById(R.id.edit_style);
                Editable style = styleTextField.getText();

                EditText nameTextField = (EditText) findViewById(R.id.edit_name);
                Editable name = nameTextField.getText();

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:" ));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", new String (number));
                smsIntent.putExtra(" sms_body ", " Dear " + name + ", your cloth is ready for collection  Ajoke Couture");

                try{
                    startActivity(smsIntent);
                    finish();
                    Log.i("Finished sending SMS", "");
                } catch(android.content.ActivityNotFoundException ex){
                    Toast.makeText(EditorActivity.this, "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Examine the intent that was used to launch this activity
        // in order to figure otu if we're creating a new client or editing an existing one
        Intent intent = getIntent();
        mCurrentClientUri = intent.getData();

        // If the intent DOES NOT contain a client content URI, then we know that we are
        // creating a new client
        if (mCurrentClientUri == null) {
            // This is a new cleint, so change the app bar to say "Add a client"
            setTitle("Add a Client");

            //Invalidate the options menu, so the "Delete menu option can be hidden.
            // It doesn't make sense to delete a client that hasn't been created yet. }
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing client, so change app bar to say "Edit Client"
            setTitle("Edit Client");

            //Initialize a loader to read the client data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
        }


        // Locating the equivalent EditTextView in the activity_editor.xml
        mNameEditText = (EditText) findViewById(R.id.edit_name);

        mAddressEditText = (EditText) findViewById(R.id.address);

        mStyleEditText = (EditText) findViewById(R.id.edit_style);

        mNumberEditText = (EditText) findViewById(number1);

        mNumber2EditText = (EditText) findViewById(R.id.number2);

        mEmailAddress = (EditText) findViewById(R.id.edit_email);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        mboss = (EditText) findViewById(R.id.edit_boss);

        mFemaleWaist = (EditText) findViewById(R.id.edit_waist);

        mHip = (EditText) findViewById(R.id.edit_hip);

        fShoulder = (EditText) findViewById(R.id.edit_shoulder);

        fShortSleeve = (EditText) findViewById(R.id.edit_shortsleevelength);

        fLongSleeve = (EditText) findViewById(R.id.edit_longleevelength);

        mfemaleTop = (EditText) findViewById(R.id.edit_roundtop);

        mSkirtLength = (EditText) findViewById(R.id.edit_skirtlength);

        mBlouseLength = (EditText) findViewById(R.id.edit_blouselength);

        mKaftanLength = (EditText) findViewById(R.id.edit_kaftanlength);
        // Men measurement begins here

        mMaleTop = (EditText) findViewById(R.id.edit_maleTop);

        mNeck = (EditText) findViewById(R.id.edit_neck);

        mMShoulder = (EditText) findViewById(R.id.edit_maleshoulder);

        mMaleShortSleeve = (EditText) findViewById(R.id.edit_maleshortsleevelength);

        mMaleLongSleeve = (EditText) findViewById(R.id.edit_malelongleevelength);

        mChest = (EditText) findViewById(R.id.edit_chest);

        mBelly = (EditText) findViewById(R.id.edit_belly);

        mTrouserLength = (EditText) findViewById(R.id.edit_trouserlength);

        mThigh = (EditText) findViewById(R.id.edit_thigh);

        mWaist = (EditText) findViewById(R.id.edit_malewaist);

        mCalf = (EditText) findViewById(R.id.edit_calf);

        mAnkle = (EditText) findViewById(R.id.edit_ankle);

        mDate = (EditText) findViewById(R.id.edit_date);

        mAmount = (EditText)findViewById(R.id.amount);

        mAdvance = (EditText)findViewById(R.id.advance);




        //Setup onTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the  user tries to leave the editor without saving.
        mNameEditText.setOnTouchListener(mTouchListener);
        mAddressEditText.setOnTouchListener(mTouchListener);
        mStyleEditText.setOnTouchListener(mTouchListener);
        mNumberEditText.setOnTouchListener(mTouchListener);
        mNumber2EditText.setOnTouchListener(mTouchListener);
        mEmailAddress.setOnTouchListener(mTouchListener);
        mGenderSpinner.setOnTouchListener(mTouchListener);
        mboss.setOnTouchListener(mTouchListener);
        mFemaleWaist.setOnTouchListener(mTouchListener);
        mHip.setOnTouchListener(mTouchListener);
        fShoulder.setOnTouchListener(mTouchListener);
        fShortSleeve.setOnTouchListener(mTouchListener);
        fLongSleeve.setOnTouchListener(mTouchListener);
        mfemaleTop.setOnTouchListener(mTouchListener);
        mSkirtLength.setOnTouchListener(mTouchListener);
        mBlouseLength.setOnTouchListener(mTouchListener);


        mKaftanLength.setOnTouchListener(mTouchListener);
        mNeck.setOnTouchListener(mTouchListener);
        mMaleTop.setOnTouchListener(mTouchListener);
        mCalf.setOnTouchListener(mTouchListener);
        mChest.setOnTouchListener(mTouchListener);
        mMShoulder.setOnTouchListener(mTouchListener);
        mMaleShortSleeve.setOnTouchListener(mTouchListener);
        mMaleLongSleeve.setOnTouchListener(mTouchListener);
        mBelly.setOnTouchListener(mTouchListener);
        mAnkle.setOnTouchListener(mTouchListener);
        mDate.setOnTouchListener(mTouchListener);
        mThigh.setOnTouchListener(mTouchListener);
        mWaist.setOnTouchListener(mTouchListener);
        mTrouserLength.setOnTouchListener(mTouchListener);

        mAmount.setOnTouchListener(mTouchListener);
        mAdvance.setOnTouchListener(mTouchListener);
        mDate.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    // The share button that serves as an intent in sharing the details of each client

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE; // Female
                    } else {
                        mGender = GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = GENDER_UNKNOWN; // Unknown
            }
        });
    }


    private void share() {
        EditText mailTextField = (EditText) findViewById(R.id.edit_email);
        Editable mail = mailTextField.getText();

        EditText styleTextField = (EditText) findViewById(R.id.edit_style);
        Editable style = styleTextField.getText();

        EditText nameTextField = (EditText) findViewById(R.id.edit_name);
        Editable name = nameTextField.getText();

        EditText numberTextField = (EditText) findViewById(R.id.number1);
        Editable number = numberTextField.getText();

        EditText bossTextField = (EditText) findViewById(R.id.edit_boss);
        Editable boss = bossTextField.getText();

        EditText wasitTextField = (EditText) findViewById(R.id.edit_waist);
        Editable waist = wasitTextField.getText();

        EditText hipTextField = (EditText) findViewById(R.id.edit_hip);
        Editable hip = hipTextField.getText();

        EditText shoulderTextField = (EditText) findViewById(R.id.edit_shoulder);
        Editable shoulder = shoulderTextField.getText();

        EditText shortSleeveLenghtTextField = (EditText) findViewById(R.id.edit_shortsleevelength);
        Editable shortSleeveLength = shortSleeveLenghtTextField.getText();

        EditText fLongSleeveLenghtTextField = (EditText) findViewById(R.id.edit_longleevelength);
        Editable femaleLongSleeve = fLongSleeveLenghtTextField.getText();

        EditText skirtLengthTextField = (EditText) findViewById(R.id.edit_skirtlength);
        Editable skirtLength = skirtLengthTextField.getText();

        EditText blouseLengthTextField = (EditText)findViewById(R.id.edit_blouselength);
        Editable blouseLength = blouseLengthTextField.getText();

        EditText amountTextField = (EditText)findViewById(R.id.amount);
        Editable amount = amountTextField.getText();



        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.ajoke_ade_couture)
                + getString(R.string.client_name) + name + ",\n"
                + getString(R.string.style) + style + ",\n"
                + "Email: " + mail + ",\n"
                + "Phone: " + number + ",\n"
                + getString(R.string.measurement)
                + getString(R.string.boss) + boss + ",\t"
                + getString(R.string.waist) + waist + ",\t"
                + getString(R.string.hip) + hip + ",\t"
                + getString(R.string.female_shoulder) + shoulder + ",\t"
                + getString(R.string.female_short_sleevelength) + shortSleeveLength + ",\t"
                + getString(R.string.long_sleeve_length) + femaleLongSleeve + ",\t"
                + getString(R.string.skirt_length) + skirtLength + ",\t"
                + getString(R.string.blouse_length) + blouseLength + "\n"
                + "Amount:" + amount + "\n"
        );
        startActivity(shareIntent);
       /* if(mGender == GENDER_FEMALE){
            Intent detailsIntent = new Intent(Intent.ACTION_SEND);
            detailsIntent.setType("text/plain");
            detailsIntent.putExtra(Intent.EXTRA_TEXT, ""
                    + "Boss: " + boss);
            startActivity(detailsIntent);
        }*/

    }





    private void saveClient() {
        //Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();


        String addressString = mAddressEditText.getText().toString().trim();

        String styleString = mStyleEditText.getText().toString().trim();

        String numberString = mNumberEditText.getText().toString().trim();

        String number2String = mNumber2EditText.getText().toString().trim();

        String emailAddress = mEmailAddress.getText().toString().trim();

        String boss = mboss.getText().toString().trim();

        String femaleWaist = mFemaleWaist.getText().toString().trim();

        String hip = mHip.getText().toString().trim();

        String femaleShoulder = fShoulder.getText().toString().trim();

        String femaleShortSleeve = fShortSleeve.getText().toString().trim();

        String femaleLongSleeve = fLongSleeve.getText().toString().trim();

        String femaleTop = mfemaleTop.getText().toString().trim();

        String skirtLength = mSkirtLength.getText().toString().trim();

        String blouseLength = mBlouseLength.getText().toString().trim();

        String caftanLength = mKaftanLength.getText().toString().trim();

        String topLenght = mMaleTop.getText().toString().trim();

        String neck = mNeck.getText().toString().trim();

        String shoulder = mMShoulder.getText().toString().trim();

        String maleShortSleeve = mMaleShortSleeve.getText().toString().trim();

        String maleLongSleeve = mMaleLongSleeve.getText().toString().trim();

        String chest = mChest.getText().toString().trim();

        String belly = mBelly.getText().toString().trim();

        String trouserLength = mTrouserLength.getText().toString().trim();

        String thigh = mThigh.getText().toString().trim();

        String waist = mWaist.getText().toString().trim();

        String calf = mCalf.getText().toString().trim();

        String ankle = mAnkle.getText().toString().trim();

        String date = mDate.getText().toString().trim();

        String amount = mAmount.getText().toString().trim();

        String advance = mAdvance.getText().toString().trim();






        // Check if this is supposed to be a new pet
        // and check if all the editor are blank
        if (mCurrentClientUri == null
                && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(addressString)
                && TextUtils.isEmpty(styleString)
                && TextUtils.isEmpty(numberString)
                && mGender == GENDER_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new client.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        //Create a ContentValues object where column names are the keys,
        // and Client attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_NAME, nameString);
        values.put(COLUMN_CLIENT_ADDRESS, addressString);
        values.put(COLUMN_CLIENT_STYLE, styleString);
        values.put(COLUMN_CLIENT_NUMBER2, number2String);
        values.put(COLUMN_EMAIL, emailAddress);
        values.put(COLUMN_CLIENT_GENDER, mGender);
        values.put(COLUMN_CLIENT_BOSS, boss);
        values.put(COLUMN_CLIENT_WAIST, femaleWaist);
        values.put(COLUMN_HIP, hip);
        values.put(COLUMN_FEMALE_SHOULDER, femaleShoulder);
        values.put(COLUMN_FEMALESHORTSLEEVE, femaleShortSleeve);
        values.put(COLUMN_FEMALELONGSLEEVE, femaleLongSleeve);
        values.put(COLUMN_TOP, femaleTop);
        values.put(COLUMN_SKIRT_LENGTH, skirtLength);
        values.put(COLUMN_BLOUSE_LENGTH, blouseLength);
        values.put(COLUMN_CAFTAN_LENGTH, caftanLength);
        values.put(COLUMN_TOP_LENGTH, topLenght);
        values.put(COLUMN_NECK, neck);
        values.put(COLUMN_MALE_SHOULDER, shoulder);
        values.put(COLUMN_MALE_SS, maleShortSleeve);
        values.put(COLUMN_MALE_LS, maleLongSleeve);
        values.put(COLUMN_CHEST, chest);
        values.put(COLUMN_BELLY, belly);
        values.put(COLUMN_WAIST, waist);
        values.put(COLUMN_TROUSER_LENGTH, trouserLength);
        values.put(COLUMN_THIGH, thigh);
        values.put(COLUMN_CALF, calf);
        values.put(COLUMN_ANKLE, ankle);
        values.put(COLUMN_DATE, date);
        values.put(AMOUNT,amount);
        values.put(ADVANCE, advance);


        // IF THE WEIGHT IS NOT PROVIDED BY THE USER, DON'T TRY TO PARSE THE STRING INTO AN
        // INTEGER VALUE. USE 0 BY DEFAULT
      /*  int number = 0;
        if(!TextUtils.isEmpty(styleString)){
            number = Integer.parseInt(numberString);
        }*/
        values.put(COLUMN_CLIENT_NUMBER, numberString);

        // Determine if this is a new or existing client by checking if mCurrentClientUri is null or not
        if (mCurrentClientUri == null) {
            //This is a NEW pet, so insert a new pet into the provider.
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(ClientEntry.CONTENT_URI, values);

            //Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Error with saving client details ", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Client saved" + newUri, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify
            int rowsAffected = getContentResolver().update(mCurrentClientUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful
            if (rowsAffected == 0) {
                // if no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Error with updating client", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast
                Toast.makeText(this, "Client updated", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /*
    * This method is called after invalidateOptionsMenu(), so that the
    * menu can be updated ( some menu items can be hidden or made visible)
    * */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentClientUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //Save pet to the database
                saveClient();
                //Exit activity
                finish();
                return true;

            // RESPOND TO A CLICK ON THE "SHARE" MENU OPTION
            case R.id.action_share:
                share();
                return true;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                //If the pet hasn't changed, continue with navigating up to a parent activity
                // which is the {@link CatalogActivity}
                if (!mClientHasChanged) {
                    // Navigate back to parent activity (CatalogActivity)
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                //Otherwise if there are unsaved changes, set up a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };
                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // This method is called when the back button is pressed.
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling bakc button press
        if (!mClientHasChanged) {
            super.onBackPressed();
            return;
        }

        //Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns form the pet table

        String[] projection = {_ID,
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
        return new CursorLoader(this, // Parent activity context
                mCurrentClientUri,       // Query the content URI for the current pet
                projection,            // Columns to include in the resulting Cursor
                null,                   //No selection clause
                null,                   //No selection arguments
                null);                  //Default sort order
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

            int styleColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_STYLE);

            int genderColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_GENDER);

            int numberColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_NUMBER);

            int number2ColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_NUMBER2);

            int bossColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_BOSS);

            int emailAddressColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);

            int femaleWaistColumnIndex = cursor.getColumnIndex(COLUMN_CLIENT_WAIST);

            int hipColumnIndex = cursor.getColumnIndex(COLUMN_HIP);

            int fShoulderColumnIndex = cursor.getColumnIndex(COLUMN_FEMALE_SHOULDER);

            int fShortSleeveColumnIndex = cursor.getColumnIndex(COLUMN_FEMALESHORTSLEEVE);

            int fLongSleeveColumnIndex = cursor.getColumnIndex(COLUMN_FEMALELONGSLEEVE);

            int femaleTopColumnIndex = cursor.getColumnIndex(COLUMN_TOP);

            int skirtLengthColumnIndex = cursor.getColumnIndex(COLUMN_SKIRT_LENGTH);

            int blouseLengthColumnIndex = cursor.getColumnIndex(COLUMN_BLOUSE_LENGTH);

            int kaftanLengthColumnIndex = cursor.getColumnIndex(COLUMN_CAFTAN_LENGTH);

            int topLengthColumnIndex = cursor.getColumnIndex(COLUMN_TOP_LENGTH);

            int neckColumnIndex = cursor.getColumnIndex(COLUMN_NECK);

            int shoulderColumnIndex = cursor.getColumnIndex(COLUMN_MALE_SHOULDER);

            int maleShortSleeveLengthColumnIndex = cursor.getColumnIndex(COLUMN_MALE_SS);

            int maleLongSleeveLengthColumnIndex = cursor.getColumnIndex(COLUMN_MALE_LS);

            int chestColumnIndex = cursor.getColumnIndex(COLUMN_CHEST);

            int bellyColumnIndex = cursor.getColumnIndex(COLUMN_BELLY);

            int trouserlenghtColumnIndex = cursor.getColumnIndex(COLUMN_TROUSER_LENGTH);

            int thighColumnIndex = cursor.getColumnIndex(COLUMN_THIGH);

            int maleWaistColumnIndex = cursor.getColumnIndex(COLUMN_WAIST);

            int calfColumnIndex = cursor.getColumnIndex(COLUMN_CALF);

            int ankleColumnIndex = cursor.getColumnIndex(COLUMN_ANKLE);

            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);

            int amountColumnIndex = cursor.getColumnIndex(AMOUNT);

            int advanceColumnIndex = cursor.getColumnIndex(ADVANCE);



            // eXTRACT OUT THE VALUE FROM THE cURSOR for the given column index
            String name = cursor.getString(nameColumnIndex);

            String address = cursor.getString(addressColumnIndex);

            String style = cursor.getString(styleColumnIndex);

            int gender = cursor.getInt(genderColumnIndex);

            String number = cursor.getString(numberColumnIndex);

            String number2 = cursor.getString(number2ColumnIndex);

            String emailAddress = cursor.getString(emailAddressColumnIndex);

            String date = cursor.getString(dateColumnIndex);

            String amount = cursor.getString(amountColumnIndex);

            String advance = cursor.getString(advanceColumnIndex);


            String boss = cursor.getString(bossColumnIndex);

            String femaleWaist = cursor.getString(femaleWaistColumnIndex);

            String hip = cursor.getString(hipColumnIndex);

            String femaleShoulder = cursor.getString(fShoulderColumnIndex);

            String femaleShortSleeve = cursor.getString(fShortSleeveColumnIndex);

            String femaleLongSleeve = cursor.getString(fLongSleeveColumnIndex);

            String femaleTop = cursor.getString(femaleTopColumnIndex);

            String skirtLength = cursor.getString(skirtLengthColumnIndex);

            String blouseLength = cursor.getString(blouseLengthColumnIndex);


            String kaftan = cursor.getString(kaftanLengthColumnIndex);

            String topLength = cursor.getString(topLengthColumnIndex);

            String neck = cursor.getString(neckColumnIndex);

            String maleshoulder = cursor.getString(shoulderColumnIndex);

            String maleShortSleeve = cursor.getString(maleShortSleeveLengthColumnIndex);

            String maleLongSleeveLenght = cursor.getString(maleLongSleeveLengthColumnIndex);

            String chest = cursor.getString(chestColumnIndex);

            String belly = cursor.getString(bellyColumnIndex);

            String ankle = cursor.getString(ankleColumnIndex);

            String calf = cursor.getString(calfColumnIndex);

            String maleWaist = cursor.getString(maleWaistColumnIndex);

            String thigh = cursor.getString(thighColumnIndex);

            String trouserLength = cursor.getString(trouserlenghtColumnIndex);




            //Update the views on the screen with the values from the database
            mKaftanLength.setText(kaftan);
            mMaleTop.setText(topLength);
            mNeck.setText(neck);
            mMShoulder.setText(maleshoulder);
            mMaleShortSleeve.setText(maleShortSleeve);
            mMaleLongSleeve.setText(maleLongSleeveLenght);
            mChest.setText(chest);
            mBelly.setText(belly);
            mAnkle.setText(ankle);
            mCalf.setText(calf);
            mWaist.setText(maleWaist);
            mThigh.setText(thigh);
            mTrouserLength.setText(trouserLength);

            mNameEditText.setText(name);
            mAddressEditText.setText(address);
            mStyleEditText.setText(style);
            mNumberEditText.setText(number);
            mNumber2EditText.setText(number2);
            mEmailAddress.setText(emailAddress);
            mDate.setText(date);

            mboss.setText(boss);
            mFemaleWaist.setText(femaleWaist);
            mHip.setText(hip);
            fShoulder.setText(femaleShoulder);
            fShortSleeve.setText(femaleShortSleeve);
            fLongSleeve.setText(femaleLongSleeve);
            mfemaleTop.setText(femaleTop);
            mSkirtLength.setText(skirtLength);
            mBlouseLength.setText(blouseLength);


            mAmount.setText(amount);
            mAdvance.setText(advance);

            //Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options(0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection
            switch (gender) {
                case GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");

        mAddressEditText.setText("");

        mStyleEditText.setText("");

        mNumberEditText.setText("");

        mNumber2EditText.setText("");

        mEmailAddress.setText("");

        mboss.setText("");

        mFemaleWaist.setText("");

        mHip.setText("");

        fShoulder.setText("");

        fShortSleeve.setText("");

        fLongSleeve.setText("");

        mfemaleTop.setText("");

        mSkirtLength.setText("");

        mBlouseLength.setText("");

        mKaftanLength.setText("");

        mMaleTop.setText("");

        mNeck.setText("");

        mMShoulder.setText("");

        mMaleShortSleeve.setText("");

        mMaleLongSleeve.setText("");

        mChest.setText("");

        mBelly.setText("");

        mTrouserLength.setText("");

        mThigh.setText("");

        mWaist.setText("");

        mCalf.setText("");

        mAnkle.setText("");

        mDate.setText("");

        mAmount.setText("");

        mAdvance.setText("");

        mGenderSpinner.setSelection(0); // Select "Unknown" gender
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when the user
     *                                   confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog
    (DialogInterface.OnClickListener discardButtonClickListener) {
        //Create an ALertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // USER clicked the "Keep Editing" button, so dismiss this dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*
    * Prompt the user to confirm that they want to delete this pet
    * */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this client?");
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete button, so delete the pet.
                deleteClient();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the ALertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    /*
    * Perform the deletion of the pet in the database
    * */


    private void deleteClient() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentClientUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI
            // Pass in null for the selection adn selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want
            int rowsDeleted = getContentResolver().delete(mCurrentClientUri, null, null);

            // Show a toast message depending on whether or not the delete was successful
            if (rowsDeleted == 0) {
                // if no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "Error with deleting client", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "Client deleted", Toast.LENGTH_SHORT).show();
            }
        }

        // CLose the activity
        finish();
    }

}
