package com.example.android.fashionhome.data;

import android.content.Context;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.fashionhome.R;
import com.example.android.fashionhome.data.ClientContract.ClientEntry;

import org.w3c.dom.Text;

import static com.example.android.fashionhome.R.id.advance;

/**
 * Created by AGUELE OSEKUEMEN JOE on 6/13/2017.
 */

public class ClientCursorAdapter extends CursorAdapter {
    public ClientCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    // Makes a new blank list item view. No data is set (or bound) to the views yet
    // @param context app context
    // @param cursor The cursor from which to get the data. The cursor is already
    //  moved to the correct position
    // @param parent The parent to which the new view is attached to
    // @return the newly created list item view

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // This method binds the client data ( in the current row pointed to by cursor) to the given
    // list item layout. For example, the name for the current client can be set on the name TextView
    // in the list item layout
    // @param view Existing view, returned earlier by newView() method.
    // @param context app context
    // @param cursor The cursor from which to get the data. The cursor is already moved to the
    // correct row
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView

        /* Find fields to populate in inflated template*/
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView styleTextView = (TextView) view.findViewById(R.id.style);
        TextView numberTextView = (TextView) view.findViewById(R.id.number1);
        TextView addressTextView = (TextView) view.findViewById(R.id.address);
        TextView amountTextView = (TextView) view.findViewById(R.id.amount);
        TextView dateTextView = (TextView)view.findViewById(R.id.date);





        // Find the columns of CLIENT attributes we are interested in
        int nameColumnIndex = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_NAME);
        int styleColumnIndex = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_STYLE);
        int numberColumnIndex = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_NUMBER);
        int addressColumnIndex = cursor.getColumnIndex(ClientEntry.COLUMN_CLIENT_ADDRESS);
        int amountColumnIndex = cursor.getColumnIndex(ClientEntry.AMOUNT);
        int dateColumnIndex = cursor.getColumnIndex(ClientEntry.COLUMN_DATE);




        // Extract client attributes from the cursor for the current client
        String clientName = cursor.getString(nameColumnIndex);
        String clientStyle = cursor.getString(styleColumnIndex);
        String clientNumber = cursor.getString(numberColumnIndex);
        String address = cursor.getString(addressColumnIndex);
        String amount = cursor.getString(amountColumnIndex);
        String date = cursor.getString(dateColumnIndex);



        // Subtract advance from amount to show in the balance text view for the user
        // BALANCE = AMOUNT - ADVANCE
     /*   int iAmount = Integer.parseInt(amount);
        int iAdvance = Integer.parseInt(advance);
        int subtract = iAmount - iAdvance;
        String subtraction = Double.toString(subtract);
*/



        // Populate fields with extracted properties for the current client
        nameTextView.setText(clientName);
        styleTextView.setText(clientStyle);
        numberTextView.setText(clientNumber);
        addressTextView.setText(address);
        amountTextView.setText(amount);
        dateTextView.setText(date);

        //balanceTextView.setText(subtraction);




    }

}
