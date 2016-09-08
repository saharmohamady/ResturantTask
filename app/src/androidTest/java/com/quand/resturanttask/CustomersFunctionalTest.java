package com.quand.resturanttask;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.quand.resturanttask.activities.CustomersActivity;
import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.utilities.NetworkUtils;
import com.quand.resturanttask.utilities.Utility;

/**
 * Created by salmohamady on 9/6/2016.
 */
public class CustomersFunctionalTest extends ActivityInstrumentationTestCase2<CustomersActivity> {
    private CustomersActivity mMainActivity;
    private EditText inputEditText;
    private String expected;
    private String actual;
    private ListView listView;
    private View child0;

    public CustomersFunctionalTest() {
        super(CustomersActivity.class);
    }

    private static final String NEW_TEXT = "new text";

    public void testSetText() throws Exception {
        CustomersActivity activity = getActivity();
        // search for the inputSearch
        final EditText textView = (EditText) activity
                .findViewById(R.id.inputSearch);
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
        CustomersActivity activity = getActivity();
        // search for the inputSearch
        final TextView textView = (TextView) activity
                .findViewById(R.id.inputSearch);

        textView.setText(NEW_TEXT);
        assertEquals("Text incorrect", NEW_TEXT, textView.getText().toString());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mMainActivity = getActivity();
        inputEditText = (EditText) mMainActivity.findViewById(R.id.inputSearch);

        expected = mMainActivity.getString(R.string.searchCustomers);
        actual = inputEditText.getHint().toString();
        listView = (ListView) mMainActivity.findViewById(R.id.list_view);
        child0 = listView.getChildAt(0); // returns null!
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
        assertNotNull("inputEditText is null", inputEditText);
    }

    public void testMyFirstTestTextView_labelText() {
        assertEquals(expected, actual);
    }

    @UiThreadTest
    public void testParsing() {
        assertNotNull(Utility.loadJSONFromAsset(mMainActivity, "customer-list.json"));
        assertNotNull(Customer.loadAndStoreFromJson(mMainActivity, Utility.loadJSONFromAsset(mMainActivity, "customer-list.json")));
    }

    public void testWithPerformClick() {

        child0.performClick(); // throws a NullPointerException!

    }

    public void testNetwork()throws Exception{
        assertEquals(true, NetworkUtils.isConnectingToInternet(mMainActivity));
    }
}
