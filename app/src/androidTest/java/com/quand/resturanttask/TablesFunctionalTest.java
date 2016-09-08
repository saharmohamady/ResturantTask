package com.quand.resturanttask;

import android.os.Bundle;
import android.os.Parcel;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

import com.quand.resturanttask.activities.TablesActivity;
import com.quand.resturanttask.model.Customer;

/**
 * Created by salmohamady on 9/6/2016.
 */
public class TablesFunctionalTest extends ActivityInstrumentationTestCase2<TablesActivity> {
    public TablesFunctionalTest() {
        super(TablesActivity.class);
    }

    private static final String NEW_TEXT = "new text";

    public void testSetText() throws Exception {

        TablesActivity activity = getActivity();

        // search for the textView
        final TextView textView = (TextView) activity
                .findViewById(R.id.note);

        // set text
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                textView.setText(NEW_TEXT);
            }
        });

        getInstrumentation().waitForIdleSync();
        assertEquals("Text incorrect", NEW_TEXT, textView.getText().toString());

    }

    @UiThreadTest
    public void testSetTextWithAnnotation() throws Exception {

        TablesActivity activity = getActivity();

        // search for the textView
        final TextView textView = (TextView) activity
                .findViewById(R.id.note);

        textView.setText(NEW_TEXT);
        assertEquals("Text incorrect", NEW_TEXT, textView.getText().toString());

    }

    public void testTestClassParcelable() {

        //Create parcelable object and put to Bundle
        Customer q = new Customer(1, "sahar", "Almohamady");
        Bundle b = new Bundle();
        b.putParcelable("customer", q);

        //Save bundle to parcel
        Parcel parcel = Parcel.obtain();
        b.writeToParcel(parcel, 0);

        //Extract bundle from parcel
        parcel.setDataPosition(0);
        Bundle b2 = parcel.readBundle();
        b2.setClassLoader(Customer.class.getClassLoader());
        Customer q2 = b2.getParcelable("customer");

        //Check that objects are not same and test that objects are equal
        assertFalse("Bundle is the same", b2 == b);
        assertFalse("customer is the same", q2 == q);
        assertTrue("customer aren't equal", q2.equals(q));
    }

}
